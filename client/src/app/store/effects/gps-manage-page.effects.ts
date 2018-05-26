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
import {of} from 'rxjs/observable/of';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {ShowError} from '../actions/core';
import {gpsManagePageActions} from '../actions/index';

@Injectable()
export class GpsManagePageEffects {
  @Effect()
  init$: Observable<Action> = this.actions$
    .ofType(gpsManagePageActions.INIT)
    .exhaustMap((action: gpsManagePageActions.Init) => {
      const {date} = action.payload;
      const params = new HttpParams().set('date', moment(date).format('YYYY-MM-DD'));
      return this.apiService.listGpsYlips(params)
        .map(ylipses => {
          return new gpsManagePageActions.InitSuccess({ylipses});
        })
        .catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
