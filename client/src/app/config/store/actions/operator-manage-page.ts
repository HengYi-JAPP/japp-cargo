import {Params} from '@angular/router';
import {Action} from '@ngrx/store';
import {Operator} from '../../../shared/models/operator';
import {OperatorPermission} from '../../../shared/models/operator-permission';

export const INIT = '[OperatorManagePage] INIT';

export class Init implements Action {
  readonly type = INIT;

  constructor(public payload: { queryParams: Params }) {
  }
}

export const INIT_SUCCESS = '[OperatorManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { first: number, pageSize: number, count: number, operators: Operator[] }) {
  }
}

export const FETCH_PERMISSION = '[OperatorManagePage] FETCH_PERMISSION';

export class FetchPermission implements Action {
  readonly type = FETCH_PERMISSION;

  constructor(public payload: { operatorId: string }) {
  }
}

export const FETCH_PERMISSION_SUCCESS = '[OperatorManagePage] FETCH_PERMISSION_SUCCESS';

export class FetchPermissionSuccess implements Action {
  readonly type = FETCH_PERMISSION_SUCCESS;

  constructor(public payload: { operatorId: string, permission: OperatorPermission }) {
  }
}

export const SAVE_PERMISSION = '[OperatorManagePage] SAVE_PERMISSION';

export class SavePermission implements Action {
  readonly type = SAVE_PERMISSION;

  constructor(public payload: { operatorId: string, permission: OperatorPermission }) {
  }
}

export type Actions
  = Init
  | InitSuccess
  | FetchPermission
  | FetchPermissionSuccess
  | SavePermission;
