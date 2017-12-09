import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {Action} from '@ngrx/store';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {receiveT001ManagePageActions} from '../';
import {ApiService} from '../../../core/services/api.service';
import {UtilService} from '../../../core/services/util.service';
import {ShowError} from '../../../core/store/actions/core';

@Injectable()
export class ReceiveT001ManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(receiveT001ManagePageActions.INIT)
    .exhaustMap((action: receiveT001ManagePageActions.Init) => {
      return this.apiService.listReceiveT001()
        .map(receiveT001s => new receiveT001ManagePageActions.InitSuccess({receiveT001s}))
        .catch(error => of(new ShowError(error)));
    });

  @Effect()
  save$: Observable<Action> = this.actions$
    .ofType(receiveT001ManagePageActions.SAVE)
    .exhaustMap((action: receiveT001ManagePageActions.Save) => {
      const {receiveT001} = action.payload;
      return this.apiService.saveReceiveT001(receiveT001)
        .map(res => {
          this.utilService.showSuccess();
          return new receiveT001ManagePageActions.SaveSuccess({receiveT001: res});
        }).catch(error => of(new ShowError(error)));
    });

  @Effect()
  delete$: Observable<Action> = this.actions$
    .ofType(receiveT001ManagePageActions.DELETE)
    .exhaustMap((action: receiveT001ManagePageActions.Delete) => {
      const {bukrs} = action.payload;
      return this.apiService.deleteReceiveT001(bukrs)
        .map(() => {
          this.utilService.showSuccess();
          return new receiveT001ManagePageActions.DeleteSuccess({bukrs});
        })
        .catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
