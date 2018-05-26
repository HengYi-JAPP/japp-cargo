import {Sort} from '@angular/material';
/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {ReceiveT001l} from '../../models/receive-t001l';
import {T001lPk} from '../../models/t001l-pk';

export const INIT = '[ReceiveT001lManagePage] INIT';

export class Init implements Action {
  readonly type = INIT;
}

export const INIT_SUCCESS = '[ReceiveT001lManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { receiveT001ls: ReceiveT001l[] }) {
  }
}

export const SET_SORT = '[ReceiveT001lManagePage] SET_SORT';

export class SetSort implements Action {
  readonly type = SET_SORT;

  constructor(public payload: { sort: Sort }) {
  }
}

export const SAVE = '[ReceiveT001lManagePage] SAVE';

export class Save implements Action {
  readonly type = SAVE;

  constructor(public payload: { receiveT001l: ReceiveT001l }) {
  }
}

export const SAVE_SUCCESS = '[ReceiveT001lManagePage] SAVE_SUCCESS';

export class SaveSuccess implements Action {
  readonly type = SAVE_SUCCESS;

  constructor(public payload: { receiveT001l: ReceiveT001l }) {
  }
}

export const DELETE = '[ReceiveT001lManagePage] DELETE';

export class Delete implements Action {
  readonly type = DELETE;

  constructor(public payload: { pk: T001lPk }) {
  }
}

export const DELETE_SUCCESS = '[ReceiveT001lManagePage] DELETE_SUCCESS';

export class DeleteSuccess implements Action {
  readonly type = DELETE_SUCCESS;

  constructor(public payload: { pk: T001lPk }) {
  }
}


export type Actions
  = Init
  | InitSuccess
  | SetSort
  | Delete
  | DeleteSuccess
  | Save
  | SaveSuccess;
