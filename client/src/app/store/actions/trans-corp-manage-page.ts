/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {T001} from '../../models/t001';
import {TransCorp} from '../../models/trans-corp';

export const INIT_SUCCESS = '[TransCorpManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { transCorps: TransCorp[] }) {
  }
}

export const FETCH_T001S = '[TransCorpManagePage] FETCH_T001S';

export class FetchT001s implements Action {
  readonly type = FETCH_T001S;

  constructor(public payload: { transCorpId: string }) {
  }
}

export const FETCH_T001S_SUCCESS = '[TransCorpManagePage] FETCH_T001S_SUCCESS';

export class FetchT001sSuccess implements Action {
  readonly type = FETCH_T001S_SUCCESS;

  constructor(public payload: { transCorpId: string, t001s: T001[] }) {
  }
}

export const SAVE_SUCCESS = '[TransCorpManagePage] SAVE_SUCCESS';

export class SaveSuccess implements Action {
  readonly type = SAVE_SUCCESS;

  constructor(public payload: { transCorp: TransCorp }) {
  }
}

export const DELETE_SUCCESS = '[TransCorpManagePage] DELETE_SUCCESS';

export class DeleteSuccess implements Action {
  readonly type = DELETE_SUCCESS;

  constructor(public payload: { id: string }) {
  }
}


export type Actions
  = InitSuccess
  | FetchT001s
  | FetchT001sSuccess
  | DeleteSuccess
  | SaveSuccess;
