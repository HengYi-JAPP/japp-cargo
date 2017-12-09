/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {ReceiveT001} from '../../../shared/models/receive-t001';

export const INIT = '[ReceiveT001ManagePage] INIT';

export class Init implements Action {
  readonly type = INIT;
}

export const INIT_SUCCESS = '[ReceiveT001ManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { receiveT001s: ReceiveT001[] }) {
  }
}

export const SAVE = '[ReceiveT001ManagePage] SAVE';

export class Save implements Action {
  readonly type = SAVE;

  constructor(public payload: { receiveT001: ReceiveT001 }) {
  }
}

export const SAVE_SUCCESS = '[ReceiveT001ManagePage] SAVE_SUCCESS';

export class SaveSuccess implements Action {
  readonly type = SAVE_SUCCESS;

  constructor(public payload: { receiveT001: ReceiveT001 }) {
  }
}

export const DELETE = '[ReceiveT001ManagePage] DELETE';

export class Delete implements Action {
  readonly type = DELETE;

  constructor(public payload: { bukrs: string }) {
  }
}

export const DELETE_SUCCESS = '[ReceiveT001ManagePage] DELETE_SUCCESS';

export class DeleteSuccess implements Action {
  readonly type = DELETE_SUCCESS;

  constructor(public payload: { bukrs: string }) {
  }
}


export type Actions
  = Init
  | InitSuccess
  | Delete
  | DeleteSuccess
  | Save
  | SaveSuccess;
