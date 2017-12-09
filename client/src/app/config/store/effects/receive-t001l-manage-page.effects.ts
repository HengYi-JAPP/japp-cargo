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
import {receiveT001lManagePageActions} from '../';
import {ApiService} from '../../../core/services/api.service';
import {UtilService} from '../../../core/services/util.service';
import {ShowError} from '../../../core/store/actions/core';

@Injectable()
export class ReceiveT001lManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(receiveT001lManagePageActions.INIT)
    .exhaustMap(() => {
      return this.apiService.listReceiveT001l()
        .map(receiveT001ls => new receiveT001lManagePageActions.InitSuccess({receiveT001ls}))
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType(receiveT001lManagePageActions.SAVE)
    .mergeMap((action: receiveT001lManagePageActions.Save) => {
      const {receiveT001l} = action.payload;
      return this.apiService.saveReceiveT001l(receiveT001l)
        .map(res => {
          this.utilService.showSuccess();
          return new receiveT001lManagePageActions.SaveSuccess({receiveT001l: res});
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  delete$: Observable<Action> = this.actions$
    .ofType(receiveT001lManagePageActions.DELETE)
    .exhaustMap((action: receiveT001lManagePageActions.Delete) => {
      const {pk} = action.payload;
      return this.apiService.deleteReceiveT001l(pk)
        .map(() => {
          this.utilService.showSuccess();
          return new receiveT001lManagePageActions.DeleteSuccess({pk});
        })
        .catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
