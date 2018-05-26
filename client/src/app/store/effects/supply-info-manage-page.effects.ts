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
import {supplyInfoManagePageActions} from '../actions/index';

@Injectable()
export class SupplyInfoManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(supplyInfoManagePageActions.INIT)
    .exhaustMap(() => {
      return this.apiService.listSupplyInfo()
        .switchMap(supplyInfos => {
          const initAction: Action = new supplyInfoManagePageActions.InitSuccess({supplyInfos});
          const fetchT001sActions: Action[] = (supplyInfos || []).map(it => new supplyInfoManagePageActions.FetchT001s({supplyInfoId: it.id}));
          return from([initAction].concat(fetchT001sActions));
        })
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  fetchT001s$: Observable<Action> = this.actions$
    .ofType(supplyInfoManagePageActions.FETCH_T001S)
    .mergeMap((action: supplyInfoManagePageActions.FetchT001s) => {
      const {supplyInfoId} = action.payload;
      return this.apiService.getSupplyInfo_T001s(supplyInfoId)
        .map(t001s => new supplyInfoManagePageActions.FetchT001sSuccess({supplyInfoId, t001s}))
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType(supplyInfoManagePageActions.SAVE)
    .exhaustMap((action: supplyInfoManagePageActions.Save) => {
      const {supplyInfo} = action.payload;
      return this.apiService.saveSupplyInfo(supplyInfo)
        .switchMap(res => {
          this.utilService.showSuccess();
          const action1 = new supplyInfoManagePageActions.SaveSuccess({supplyInfo: res});
          const action2 = new supplyInfoManagePageActions.FetchT001s({supplyInfoId: res.id});
          return from([action1, action2]);
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  delete$: Observable<Action> = this.actions$
    .ofType(supplyInfoManagePageActions.DELETE)
    .exhaustMap((action: supplyInfoManagePageActions.Delete) => {
      const {id} = action.payload;
      return this.apiService.deleteSupplyInfo(id)
        .map(() => {
          this.utilService.showSuccess();
          return new supplyInfoManagePageActions.DeleteSuccess({id});
        }).catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
