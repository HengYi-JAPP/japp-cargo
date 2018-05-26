import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {Action} from '@ngrx/store';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {from} from 'rxjs/observable/from';
import {of} from 'rxjs/observable/of';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {ShowError} from '../actions/core';
import {headInfoManagePageActions} from '../actions/index';

@Injectable()
export class HeadInfoManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(headInfoManagePageActions.INIT)
    .exhaustMap(() => {
      return this.apiService.listHeadInfo()
        .switchMap(headInfos => {
          const initAction: Action = new headInfoManagePageActions.InitSuccess({headInfos});
          const fetchT001sActions: Action[] = (headInfos || []).map(it => new headInfoManagePageActions.FetchT001s({headInfoId: it.id}));
          return from([initAction].concat(fetchT001sActions));
        })
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  fetchT001s$: Observable<Action> = this.actions$
    .ofType(headInfoManagePageActions.FETCH_T001S)
    .mergeMap((action: headInfoManagePageActions.FetchT001s) => {
      const {headInfoId} = action.payload;
      return this.apiService.getHeadInfo_T001s(headInfoId)
        .map(t001s => new headInfoManagePageActions.FetchT001sSuccess({headInfoId, t001s}))
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType(headInfoManagePageActions.SAVE)
    .exhaustMap((action: headInfoManagePageActions.Save) => {
      const {headInfo} = action.payload;
      return this.apiService.saveHeadInfo(headInfo)
        .switchMap(res => {
          this.utilService.showSuccess();
          const action1 = new headInfoManagePageActions.SaveSuccess({headInfo: res});
          const action2 = new headInfoManagePageActions.FetchT001s({headInfoId: res.id});
          return from([action1, action2]);
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  delete$: Observable<Action> = this.actions$
    .ofType(headInfoManagePageActions.DELETE)
    .exhaustMap((action: headInfoManagePageActions.Delete) => {
      const {id} = action.payload;
      return this.apiService.deleteHeadInfo(id)
        .map(() => {
          this.utilService.showSuccess();
          return new headInfoManagePageActions.DeleteSuccess({id});
        }).catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
