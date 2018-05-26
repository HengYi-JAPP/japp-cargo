/**
 * Created by jzb on 17-6-6.
 */
import {Action} from '@ngrx/store';
import {MegSendInfo} from '../../models/meg-send-info';
import {PtaSendInfo} from '../../models/pta-send-info';

export const INIT_SUCCESS = '[SendManagePage] INIT_SUCCESS';

export class InitSuccess implements Action {
  readonly type = INIT_SUCCESS;

  constructor(public payload: { date: Date, ptas: PtaSendInfo[], megs: MegSendInfo[] }) {
  }
}

export const SET_CARNO_Q = '[SendManagePage] SET_CARNO_Q';

export class SetCarNoQ implements Action {
  readonly type = SET_CARNO_Q;

  constructor(public payload: { carNoQ: string }) {
  }
}

export const SAVE_PTA_SEND = '[SendManagePage] SAVE_PTA_SEND';

export class SavePtaSend implements Action {
  readonly type = SAVE_PTA_SEND;

  constructor(public payload: { sendInfo: PtaSendInfo }) {
  }
}

export const SAVE_PTA_SEND_SUCCESS = '[SendManagePage] SAVE_PTA_SEND_SUCCESS';

export class SavePtaSendSuccess implements Action {
  readonly type = SAVE_PTA_SEND_SUCCESS;

  constructor(public payload: { sendInfo: PtaSendInfo }) {
  }
}

export const SAVE_MEG_SEND = '[SendManagePage] SAVE_MEG_SEND';

export class SaveMegSend implements Action {
  readonly type = SAVE_MEG_SEND;

  constructor(public payload: { sendInfo: MegSendInfo }) {
  }
}

export const SAVE_MEG_SEND_SUCCESS = '[SendManagePage] SAVE_MEG_SEND_SUCCESS';

export class SaveMegSendSuccess implements Action {
  readonly type = SAVE_MEG_SEND_SUCCESS;

  constructor(public payload: { sendInfo: MegSendInfo }) {
  }
}

export const DELETE_PTA_SEND = '[SendManagePage] DELETE_PTA_SEND';

export class DeletePtaSend implements Action {
  readonly type = DELETE_PTA_SEND;

  constructor(public payload: { id: string }) {
  }
}

export const DELETE_PTA_SEND_SUCCESS = '[SendManagePage] DELETE_PTA_SEND_SUCCESS';

export class DeletePtaSendSuccess implements Action {
  readonly type = DELETE_PTA_SEND_SUCCESS;

  constructor(public payload: { id: string }) {
  }
}

export const DELETE_MEG_SEND = '[SendManagePage] DELETE_MEG_SEND';

export class DeleteMegSend implements Action {
  readonly type = DELETE_MEG_SEND;

  constructor(public payload: { id: string }) {
  }
}

export const DELETE_MEG_SEND_SUCCESS = '[SendManagePage] DELETE_MEG_SEND_SUCCESS';

export class DeleteMegSendSuccess implements Action {
  readonly type = DELETE_MEG_SEND_SUCCESS;

  constructor(public payload: { id: string }) {
  }
}


export type Actions
  = InitSuccess
  | SetCarNoQ
  | DeletePtaSend
  | DeletePtaSendSuccess
  | DeleteMegSend
  | DeleteMegSendSuccess
  | SavePtaSend
  | SavePtaSendSuccess
  | SaveMegSend
  | SaveMegSendSuccess;
