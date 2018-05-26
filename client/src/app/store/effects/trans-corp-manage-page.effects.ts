import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {ROUTER_NAVIGATION, RouterNavigationAction} from '@ngrx/router-store';
import {Action, Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {from} from 'rxjs/observable/from';
import {of} from 'rxjs/observable/of';
import {catchError, filter, mergeMap, switchMap} from 'rxjs/operators';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {ShowError} from '../actions/core';
import {transCorpManagePageActions} from '../actions/index';

@Injectable()
export class TransCorpManagePageEffects {
  @Effect() navigate$ = this.actions$
    .ofType<RouterNavigationAction>(ROUTER_NAVIGATION)
    .pipe(
      filter(action => {
        const {event} = action.payload;
        return event.url.startsWith('/transCorps');
      }),
      switchMap(() => {
        return this.apiService.listTransCorp()
          .pipe(
            switchMap(transCorps => {
              const initAction: Action = new transCorpManagePageActions.InitSuccess({transCorps});
              const fetchT001sActions: Action[] = (transCorps || []).map(it => new transCorpManagePageActions.FetchT001s({transCorpId: it.id}));
              return from([initAction].concat(fetchT001sActions));
            }),
            catchError(error => of(new ShowError(error)))
          );
      })
    );

  @Effect()
  fetchT001s$: Observable<Action> = this.actions$
    .ofType(transCorpManagePageActions.FETCH_T001S)
    .pipe(
      mergeMap((action: transCorpManagePageActions.FetchT001s) => {
        const {transCorpId} = action.payload;
        return this.apiService.getTransCorp_T001s(transCorpId)
          .map(t001s => new transCorpManagePageActions.FetchT001sSuccess({transCorpId, t001s}))
          .catch(error => of(new ShowError(error)));
      })
    );

  constructor(private actions$: Actions,
              private store: Store<any>,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
