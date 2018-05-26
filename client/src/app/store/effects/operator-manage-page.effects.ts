import {HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {Action, Store} from '@ngrx/store';
import 'rxjs/add/operator/catch';
import {Observable} from 'rxjs/Observable';
import {from} from 'rxjs/observable/from';
import {of} from 'rxjs/observable/of';
import {catchError, exhaustMap, map, mergeMap, switchMap, withLatestFrom} from 'rxjs/operators';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {ShowError} from '../actions/core';
import {coreActions, operatorManagePageActions} from '../actions/index';
import {coreAuthOperator} from '../core';

@Injectable()
export class OperatorManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType<operatorManagePageActions.Init>(operatorManagePageActions.INIT)
    .pipe(
      exhaustMap(action => {
        const {first, pageSize} = action.payload.queryParams;
        const params = new HttpParams().set('first', first || '0').set('pageSize', pageSize || '10');
        return this.apiService.listOperator(params)
          .pipe(
            switchMap(res => {
              const {count, operators} = res;
              const initAction: Action = new operatorManagePageActions.InitSuccess({first, pageSize, count, operators});
              const fetchActions: Action[] = (operators || []).map(it => new operatorManagePageActions.FetchPermission({operatorId: it.id}));
              return from([initAction].concat(fetchActions));
            }),
            catchError(error => of(new ShowError(error)))
          );
      })
    );

  @Effect()
  fetchPermission$: Observable<Action> = this.actions$
    .ofType(operatorManagePageActions.FETCH_PERMISSION)
    .pipe(
      mergeMap((action: operatorManagePageActions.FetchPermission) => {
        const {operatorId} = action.payload;
        return this.apiService.getOperator_Permission(operatorId)
          .pipe(
            map(permission => new operatorManagePageActions.FetchPermissionSuccess({operatorId, permission})),
            catchError(error => of(new ShowError(error)))
          );
      })
    );

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType<operatorManagePageActions.SavePermission>(operatorManagePageActions.SAVE_PERMISSION)
    .pipe(
      withLatestFrom(this.store.select(coreAuthOperator)),
      exhaustMap(a => {
        const [action, authOperator] = a;
        const {operatorId, permission} = action.payload;
        return this.apiService.saveOperatorPermission(operatorId, permission)
          .pipe(
            map(res => {
              this.utilService.showSuccess();
              if (operatorId === authOperator.id) {
                this.store.dispatch(new coreActions.FetchAuth());
              }
              return new operatorManagePageActions.FetchPermissionSuccess({
                operatorId,
                admin: permission['admin'],
                permission: res
              });
            }),
            catchError(error => of(new ShowError(error)))
          );
      })
    );

  constructor(private actions$: Actions,
              private store: Store<any>,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
