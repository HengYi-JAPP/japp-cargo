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
import {from} from 'rxjs/observable/from';
import {of} from 'rxjs/observable/of';
import {operatorManagePageActions} from '../';
import {ApiService} from '../../../core/services/api.service';
import {UtilService} from '../../../core/services/util.service';
import {ShowError} from '../../../core/store/actions/core';

@Injectable()
export class OperatorManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(operatorManagePageActions.INIT)
    .exhaustMap((action: operatorManagePageActions.Init) => {
      const {first, pageSize} = action.payload.queryParams;
      const params = new HttpParams().set('first', first || '0').set('pageSize', pageSize || '10');
      return this.apiService.listOperator(params)
        .switchMap(res => {
          const {count, operators} = res;
          const initAction: Action = new operatorManagePageActions.InitSuccess({first, pageSize, count, operators});
          const fetchActions: Action[] = (operators || []).map(it => new operatorManagePageActions.FetchPermission({operatorId: it.id}));
          return from([initAction].concat(fetchActions));
        })
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  fetchPermission$: Observable<Action> = this.actions$
    .ofType(operatorManagePageActions.FETCH_PERMISSION)
    .mergeMap((action: operatorManagePageActions.FetchPermission) => {
      const {operatorId} = action.payload;
      return this.apiService.getOperator_Permission(operatorId)
        .map(permission => new operatorManagePageActions.FetchPermissionSuccess({operatorId, permission}))
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType(operatorManagePageActions.SAVE_PERMISSION)
    .exhaustMap((action: operatorManagePageActions.SavePermission) => {
      const {operatorId, permission} = action.payload;
      return this.apiService.saveOperatorPermission(operatorId, permission)
        .map(res => {
          this.utilService.showSuccess();
          return new operatorManagePageActions.FetchPermissionSuccess({operatorId, permission: res});
        }).catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
