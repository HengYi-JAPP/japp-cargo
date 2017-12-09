import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {Action} from '@ngrx/store';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/pluck';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {coreActions} from '../actions';

@Injectable()
export class CoreEffects {
  @Effect({dispatch: false})
  loginRedirect$ = this.actions$
    .ofType(coreActions.LOGIN_REDIRECT)
    .do(() => this.router.navigate(['login']));

  @Effect()
  showError$: Observable<Action> = this.actions$
    .ofType(coreActions.SHOW_ERROR)
    .exhaustMap((action: coreActions.ShowError) => {
      this.utilService.showError(action.payload);
      return of();
    });

  @Effect()
  fetchAuth$: Observable<Action> = this.actions$
    .ofType(coreActions.FETCH_AUTH)
    .exhaustMap(() => {
      return this.apiService.getAuth()
        .map(res => new coreActions.FetchAuthSuccess(res));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private apiService: ApiService,
              private utilService: UtilService) {
  }

}
