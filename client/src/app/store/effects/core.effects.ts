import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, Effect } from '@ngrx/effects';
import { Action } from '@ngrx/store';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { ApiService } from '../../services/api.service';
import { UtilService } from '../../services/util.service';
import { coreActions } from '../actions/index';
import { exhaustMap, map } from 'rxjs/operators';

@Injectable()
export class CoreEffects {
  @Effect({ dispatch: false })
  loginRedirect$ = this.actions$
    .ofType<coreActions.LoginRedirect>(coreActions.LOGIN_REDIRECT)
    .do(() => this.router.navigate(['login']));

  @Effect()
  showError$: Observable<Action> = this.actions$
    .ofType<coreActions.ShowError>(coreActions.SHOW_ERROR)
    .pipe(
      exhaustMap(action => {
        this.utilService.showError(action.payload);
        return of();
      })
    );

  @Effect()
  fetchAuth$: Observable<Action> = this.actions$
    .ofType<coreActions.FetchAuth>(coreActions.FETCH_AUTH)
    .pipe(
      exhaustMap(() => {
        return this.apiService.getAuth()
          .pipe(
            map(res => new coreActions.FetchAuthSuccess(res))
          );
      })
    );

  constructor(private actions$: Actions,
    private router: Router,
    private apiService: ApiService,
    private utilService: UtilService) {
  }

}
