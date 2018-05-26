/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {SupplyInfo} from '../../models/supply-info';
import {T001} from '../../models/t001';

export const INIT = '[SupplyInfoManagePage] INIT';

export class Init implements Action {
  readonly type = INIT;
}

export const INIT_SUCCESS = '[SupplyInfoManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { supplyInfos: SupplyInfo[] }) {
  }
}

export const FETCH_T001S = '[SupplyInfoManagePage] FETCH_T001S';

export class FetchT001s implements Action {
  readonly type = FETCH_T001S;

  constructor(public payload: { supplyInfoId: string }) {
  }
}

export const FETCH_T001S_SUCCESS = '[SupplyInfoManagePage] FETCH_T001S_SUCCESS';

export class FetchT001sSuccess implements Action {
  readonly type = FETCH_T001S_SUCCESS;

  constructor(public payload: { supplyInfoId: string, t001s: T001[] }) {
  }
}

export const SAVE = '[SupplyInfoManagePage] SAVE';

export class Save implements Action {
  readonly type = SAVE;

  constructor(public payload: { supplyInfo: SupplyInfo }) {
  }
}

export const SAVE_SUCCESS = '[SupplyInfoManagePage] SAVE_SUCCESS';

export class SaveSuccess implements Action {
  readonly type = SAVE_SUCCESS;

  constructor(public payload: { supplyInfo: SupplyInfo }) {
  }
}

export const DELETE = '[SupplyInfoManagePage] DELETE';

export class Delete implements Action {
  readonly type = DELETE;

  constructor(public payload: { id: string }) {
  }
}

export const DELETE_SUCCESS = '[SupplyInfoManagePage] DELETE_SUCCESS';

export class DeleteSuccess implements Action {
  readonly type = DELETE_SUCCESS;

  constructor(public payload: { id: string }) {
  }
}


export type Actions
  = Init
  | InitSuccess
  | FetchT001s
  | FetchT001sSuccess
  | Delete
  | DeleteSuccess
  | Save
  | SaveSuccess;
