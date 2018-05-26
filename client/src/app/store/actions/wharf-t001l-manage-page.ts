import {Sort} from '@angular/material';
/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {T001lPk} from '../../models/t001l-pk';
import {WharfT001l} from '../../models/wharf-t001l';

export const INIT = '[WharfT001lManagePage] INIT';

export class Init implements Action {
  readonly type = INIT;
}

export const INIT_SUCCESS = '[WharfT001lManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { wharfT001ls: WharfT001l[] }) {
  }
}

export const SET_SORT = '[WharfT001lManagePage] SET_SORT';

export class SetSort implements Action {
  readonly type = SET_SORT;

  constructor(public payload: { sort: Sort }) {
  }
}

export const SAVE = '[WharfT001lManagePage] SAVE';

export class Save implements Action {
  readonly type = SAVE;

  constructor(public payload: { wharfT001l: WharfT001l }) {
  }
}

export const SAVE_SUCCESS = '[WharfT001lManagePage] SAVE_SUCCESS';

export class SaveSuccess implements Action {
  readonly type = SAVE_SUCCESS;

  constructor(public payload: { wharfT001l: WharfT001l }) {
  }
}

export const DELETE = '[WharfT001lManagePage] DELETE';

export class Delete implements Action {
  readonly type = DELETE;

  constructor(public payload: { pk: T001lPk }) {
  }
}

export const DELETE_SUCCESS = '[WharfT001lManagePage] DELETE_SUCCESS';

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
