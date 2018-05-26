import {HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {ROUTER_NAVIGATION, RouterNavigationAction} from '@ngrx/router-store';
import {Action, Store} from '@ngrx/store';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/withLatestFrom';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {catchError, filter, map, switchMap, withLatestFrom} from 'rxjs/operators';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {ShowError} from '../actions/core';
import {sendManagePageActions} from '../actions/index';
import {sendManagePageState} from '../send';

@Injectable()
export class SendManagePageEffects {
  @Effect() navigate$ = this.actions$
    .ofType<RouterNavigationAction>(ROUTER_NAVIGATION)
    .pipe(
      filter(action => {
        const {event} = action.payload;
        return event.url.startsWith('/sends');
      }),
      withLatestFrom(this.store.map(sendManagePageState)),
      switchMap(a => {
        const [action, state] = a;
        const date = moment(action.payload.event.state.root.queryParams.date);
        const params = new HttpParams().set('date', date.format('YYYY-MM-DD'));
        return this.apiService.listSendInfo(params)
          .pipe(
            map(res => {
              const {ptas, megs} = res;
              return new sendManagePageActions.InitSuccess({date: date.toDate(), ptas, megs});
            }),
            catchError(error => of(new ShowError(error)))
          );
      })
    );

  @Effect()
  savePta$: Observable<Action> = this.actions$
    .ofType(sendManagePageActions.SAVE_PTA_SEND)
    .exhaustMap((action: sendManagePageActions.SavePtaSend) => {
      const {sendInfo} = action.payload;
      const ylipsId = sendInfo.ylips && sendInfo.ylips.id;
      return this.apiService.savePtaSendInfo(ylipsId, sendInfo)
        .map(res => {
          this.utilService.showSuccess();
          return new sendManagePageActions.SavePtaSendSuccess({sendInfo: res});
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  deletePta$: Observable<Action> = this.actions$
    .ofType(sendManagePageActions.DELETE_PTA_SEND)
    .exhaustMap((action: sendManagePageActions.DeletePtaSend) => {
      const {id} = action.payload;
      return this.apiService.deletePtaSendInfo(id)
        .map(res => {
          this.utilService.showSuccess();
          return new sendManagePageActions.DeletePtaSendSuccess({id});
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  saveMeg$: Observable<Action> = this.actions$
    .ofType(sendManagePageActions.SAVE_MEG_SEND)
    .exhaustMap((action: sendManagePageActions.SaveMegSend) => {
      const {sendInfo} = action.payload;
      return this.apiService.saveMegSendInfo(sendInfo)
        .map(res => {
          this.utilService.showSuccess();
          return new sendManagePageActions.SaveMegSendSuccess({sendInfo: res});
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  deleteMeg$: Observable<Action> = this.actions$
    .ofType(sendManagePageActions.DELETE_MEG_SEND)
    .exhaustMap((action: sendManagePageActions.DeleteMegSend) => {
      const {id} = action.payload;
      return this.apiService.deleteMegSendInfo(id)
        .map(res => {
          this.utilService.showSuccess();
          return new sendManagePageActions.DeleteMegSendSuccess({id});
        }).catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private store: Store<any>,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }

}
