import {Action} from '@ngrx/store';
import {Operator} from '../../models/operator';
import {OperatorPermission} from '../../models/operator-permission';

export const LOGIN_REDIRECT = '[Core] Login Redirect';

export class LoginRedirect implements Action {
  readonly type = LOGIN_REDIRECT;
}

export const IMAGE_PREVIEW = '[Core] IMAGE_PREVIEW';

export class ImagePreview implements Action {
  readonly type = IMAGE_PREVIEW;

  constructor(public payload: { current: string, urls?: string[] }) {
  }
}

export const SHOW_ERROR = '[Core] SHOW_ERROR';

export class ShowError implements Action {
  readonly type = SHOW_ERROR;

  constructor(public payload: any) {
  }
}

export const SHOW_WX_QRCOODE = '[Core] SHOW_WX_QRCOODE';

export class ShowWxQrcoode implements Action {
  readonly type = SHOW_WX_QRCOODE;

  constructor(public ticket: string, public title: string) {
  }
}

export const OPEN_SIDENAV = '[Core] OPEN_SIDENAV';

export class OpenSidenavAction implements Action {
  readonly type = OPEN_SIDENAV;
}

export const CLOSE_SIDENAV = '[Core] CLOSE_SIDENAV';

export class CloseSidenavAction implements Action {
  readonly type = CLOSE_SIDENAV;
}

export const SET_IS_MOBILE = '[Core] SET_IS_MOBILE';

export class SetIsMobile implements Action {
  readonly type = SET_IS_MOBILE;

  constructor(public payload: boolean) {
  }
}

export const SET_SHOW_SIDENAV = '[Core] SET_SHOW_SIDENAV';

export class SetShowSidenav implements Action {
  readonly type = SET_SHOW_SIDENAV;

  constructor(public payload: boolean) {
  }
}

export const SET_SHOW_TOOLBAR = '[Core] SET_SHOW_TOOLBAR';

export class SetShowToolbar implements Action {
  readonly type = SET_SHOW_TOOLBAR;

  constructor(public payload: boolean) {
  }
}

export const FETCH_AUTH = '[Core] FETCH_AUTH';

export class FetchAuth implements Action {
  readonly type = FETCH_AUTH;
}

export const FETCH_AUTH_SUCCESS = '[Core] FETCH_AUTH_SUCCESS';

export class FetchAuthSuccess implements Action {
  readonly type = FETCH_AUTH_SUCCESS;

  constructor(public payload: { operator: Operator, permission: OperatorPermission }) {
  }
}

export type Actions
  = LoginRedirect
  | FetchAuth
  | FetchAuthSuccess
  | ImagePreview
  | SetShowSidenav
  | SetShowToolbar
  | ShowError
  | ShowWxQrcoode
  | OpenSidenavAction
  | CloseSidenavAction
  | SetIsMobile;
