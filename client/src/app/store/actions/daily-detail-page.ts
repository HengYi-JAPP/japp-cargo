/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {MegSendInfo} from '../../models/meg-send-info';
import {PtaSendInfo} from '../../models/pta-send-info';

export const SEARCH = '[DailyDetailPage] SEARCH';

export class Search implements Action {
  readonly type = SEARCH;

  constructor(public payload: { startDate: Date, endDate: Date }) {
  }
}

export const SEARCH_SUCCESS = '[DailyDetailPage] SEARCH_SUCCESS';

export class SearchSuccess implements Action {
  readonly type = SEARCH_SUCCESS;

  constructor(public payload: { startDate: Date, endDate: Date, ptas: PtaSendInfo[], megs: MegSendInfo[] }) {
  }
}

export const SET_CARNO_Q = '[DailyDetailPage] SET_CARNO_Q';

export class SetCarNoQ implements Action {
  readonly type = SET_CARNO_Q;

  constructor(public payload: { carNoQ: string }) {
  }
}

export type Actions =
  | SearchSuccess
  | SetCarNoQ;
