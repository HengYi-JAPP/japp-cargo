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
import {transCorpManagePageActions} from '../';
import {ApiService} from '../../../core/services/api.service';
import {UtilService} from '../../../core/services/util.service';
import {ShowError} from '../../../core/store/actions/core';

@Injectable()
export class TransCorpManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(transCorpManagePageActions.INIT)
    .exhaustMap(() => {
      return this.apiService.listTransCorp()
        .switchMap(transCorps => {
          const initAction: Action = new transCorpManagePageActions.InitSuccess({transCorps});
          const fetchT001sActions: Action[] = (transCorps || []).map(it => new transCorpManagePageActions.FetchT001s({transCorpId: it.id}));
          return from([initAction].concat(fetchT001sActions));
        })
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  fetchT001s$: Observable<Action> = this.actions$
    .ofType(transCorpManagePageActions.FETCH_T001S)
    .mergeMap((action: transCorpManagePageActions.FetchT001s) => {
      const {transCorpId} = action.payload;
      return this.apiService.getTransCorp_T001s(transCorpId)
        .map(t001s => new transCorpManagePageActions.FetchT001sSuccess({transCorpId, t001s}))
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType(transCorpManagePageActions.SAVE)
    .exhaustMap((action: transCorpManagePageActions.Save) => {
      const {transCorp} = action.payload;
      return this.apiService.saveTransCorp(transCorp)
        .switchMap(res => {
          this.utilService.showSuccess();
          const action1 = new transCorpManagePageActions.SaveSuccess({transCorp: res});
          const action2 = new transCorpManagePageActions.FetchT001s({transCorpId: res.id});
          return from([action1, action2]);
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  delete$: Observable<Action> = this.actions$
    .ofType(transCorpManagePageActions.DELETE)
    .exhaustMap((action: transCorpManagePageActions.Delete) => {
      const {id} = action.payload;
      return this.apiService.deleteTransCorp(id)
        .map(() => {
          this.utilService.showSuccess();
          return new transCorpManagePageActions.DeleteSuccess({id});
        }).catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
