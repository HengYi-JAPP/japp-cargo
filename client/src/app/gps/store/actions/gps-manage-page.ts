/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {Ylips} from '../../../shared/models/ylips';

export const INIT = '[GpsManagePage] INIT';

export class Init implements Action {
  readonly type = INIT;

  constructor(public payload: { date?: Date }) {
  }
}

export const INIT_SUCCESS = '[GpsManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { ylipses: Ylips[] }) {
  }
}

export const SET_CARNO_Q = '[GpsManagePage] SET_CARNO_Q';

export class SetCarNoQ implements Action {
  readonly type = SET_CARNO_Q;

  constructor(public payload: { carNoQ: string }) {
  }
}

export type Actions
  = Init
  | SetCarNoQ
  | InitSuccess;
