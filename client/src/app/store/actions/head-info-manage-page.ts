/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {HeadInfo} from '../../models/head-info';
import {T001} from '../../models/t001';

export const INIT = '[HeadInfoManagePage] INIT';

export class Init implements Action {
  readonly type = INIT;
}

export const INIT_SUCCESS = '[HeadInfoManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { headInfos: HeadInfo[] }) {
  }
}

export const FETCH_T001S = '[HeadInfoManagePage] FETCH_T001S';

export class FetchT001s implements Action {
  readonly type = FETCH_T001S;

  constructor(public payload: { headInfoId: string }) {
  }
}

export const FETCH_T001S_SUCCESS = '[HeadInfoManagePage] FETCH_T001S_SUCCESS';

export class FetchT001sSuccess implements Action {
  readonly type = FETCH_T001S_SUCCESS;

  constructor(public payload: { headInfoId: string, t001s: T001[] }) {
  }
}

export const SAVE = '[HeadInfoManagePage] SAVE';

export class Save implements Action {
  readonly type = SAVE;

  constructor(public payload: { headInfo: HeadInfo }) {
  }
}

export const SAVE_SUCCESS = '[HeadInfoManagePage] SAVE_SUCCESS';

export class SaveSuccess implements Action {
  readonly type = SAVE_SUCCESS;

  constructor(public payload: { headInfo: HeadInfo }) {
  }
}

export const DELETE = '[HeadInfoManagePage] DELETE';

export class Delete implements Action {
  readonly type = DELETE;

  constructor(public payload: { id: string }) {
  }
}

export const DELETE_SUCCESS = '[HeadInfoManagePage] DELETE_SUCCESS';

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
