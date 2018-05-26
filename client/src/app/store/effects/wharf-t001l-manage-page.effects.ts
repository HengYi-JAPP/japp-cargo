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
import {of} from 'rxjs/observable/of';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {ShowError} from '../actions/core';
import {wharfT001lManagePageActions} from '../actions/index';

@Injectable()
export class WharfT001lManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(wharfT001lManagePageActions.INIT)
    .exhaustMap(() => {
      return this.apiService.listWharfT001l()
        .map(wharfT001ls => new wharfT001lManagePageActions.InitSuccess({wharfT001ls}))
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType(wharfT001lManagePageActions.SAVE)
    .mergeMap((action: wharfT001lManagePageActions.Save) => {
      const {wharfT001l} = action.payload;
      return this.apiService.saveWharfT001l(wharfT001l)
        .map(res => {
          this.utilService.showSuccess();
          return new wharfT001lManagePageActions.SaveSuccess({wharfT001l: res});
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  delete$: Observable<Action> = this.actions$
    .ofType(wharfT001lManagePageActions.DELETE)
    .exhaustMap((action: wharfT001lManagePageActions.Delete) => {
      const {pk} = action.payload;
      return this.apiService.deleteWharfT001l(pk)
        .map(() => {
          this.utilService.showSuccess();
          return new wharfT001lManagePageActions.DeleteSuccess({pk});
        }).catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
