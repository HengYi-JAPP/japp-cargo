import {HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {ROUTER_NAVIGATION, RouterNavigationAction} from '@ngrx/router-store';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/withLatestFrom';
import {of} from 'rxjs/observable/of';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {ShowError} from '../actions/core';
import {dailyDetailPageState} from '../report';
import { dailyDetailPageActions } from '../actions';

@Injectable()
export class DailyDetailPageEffects {
  @Effect() navigate$ = this.actions$
    .ofType<RouterNavigationAction>(ROUTER_NAVIGATION)
    .filter(action => {
      const {event} = action.payload;
      return event.url.startsWith('/reports/dailyDetail');
    })
    .withLatestFrom(this.store.select(dailyDetailPageState))
    .switchMap(a => {
      const [action, state] = a;
      const startDate = moment(action.payload.event.state.root.queryParams.startDate);
      const endDate = moment(action.payload.event.state.root.queryParams.endDate);
      const params = new HttpParams()
        .set('startDate', startDate.format('YYYY-MM-DD'))
        .set('endDate', endDate.format('YYYY-MM-DD'));
      return this.apiService.dailyDetailReport(params)
        .map(res => {
          const {ptas, megs} = res;
          return new dailyDetailPageActions.SearchSuccess({
            startDate: startDate.toDate(),
            endDate: endDate.toDate(),
            ptas,
            megs
          });
        })
        .catch(error => of(new ShowError(error)));
    });

  constructor(private actions$: Actions,
              private store: Store<any>,
              private router: Router,
              private utilService: UtilService,
              private apiService: ApiService) {
  }
}
