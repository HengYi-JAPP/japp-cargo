import {HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {Action} from '@ngrx/store';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {sendManagePageActions} from '../';
import {ApiService} from '../../../core/services/api.service';
import {UtilService} from '../../../core/services/util.service';
import {ShowError} from '../../../core/store/actions/core';

@Injectable()
export class SendManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(sendManagePageActions.INIT)
    .exhaustMap((action: sendManagePageActions.Init) => {
      const {date} = action.payload;
      const params = new HttpParams().set('date', moment(date).format('YYYY-MM-DD'));
      return this.apiService.listSendInfo(params)
        .map(res => {
          const {ptas, megs} = res;
          return new sendManagePageActions.InitSuccess({ptas, megs});
        })
        .catch(error => of(new ShowError(error)));
    });

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
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
