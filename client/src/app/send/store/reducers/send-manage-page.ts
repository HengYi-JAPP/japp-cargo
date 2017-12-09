import {createSelector} from '@ngrx/store';
import {DefaultCompare} from '../../../core/services/util.service';
import {MegSendInfo} from '../../../shared/models/meg-send-info';
import {PtaSendInfo} from '../../../shared/models/pta-send-info';
import {SendInfo} from '../../../shared/models/send-info';
import {sendManagePageActions} from '../actions';

export interface State {
  ptaEntities: { [id: string]: PtaSendInfo };
  megEntities: { [id: string]: MegSendInfo };
  carNoQ: string;
}

const initialState: State = {
  ptaEntities: {},
  megEntities: {},
  carNoQ: ''
};

export function reducer(state = initialState, action: sendManagePageActions.Actions): State {
  switch (action.type) {
    case sendManagePageActions.INIT_SUCCESS: {
      const {ptas, megs} = action.payload;
      const ptaEntities = PtaSendInfo.toEntities(ptas);
      const megEntities = MegSendInfo.toEntities(megs);
      return {...state, ptaEntities, megEntities};
    }
    case sendManagePageActions.SET_CARNO_Q: {
      const {carNoQ} = action.payload;
      return {...state, carNoQ};
    }
    case sendManagePageActions.SAVE_PTA_SEND_SUCCESS: {
      const {sendInfo} = action.payload;
      const ptaEntities = PtaSendInfo.toEntities([sendInfo], state.ptaEntities);
      return {...state, ptaEntities};
    }
    case sendManagePageActions.SAVE_MEG_SEND_SUCCESS: {
      const {sendInfo} = action.payload;
      const megEntities = MegSendInfo.toEntities([sendInfo], state.megEntities);
      return {...state, megEntities};
    }
    case sendManagePageActions.DELETE_PTA_SEND_SUCCESS: {
      const {id} = action.payload;
      const ptaEntities = {...state.ptaEntities};
      delete ptaEntities[id];
      return {...state, ptaEntities};
    }
    case sendManagePageActions.DELETE_MEG_SEND_SUCCESS: {
      const {id} = action.payload;
      const megEntities = {...state.megEntities};
      delete megEntities[id];
      return {...state, megEntities};
    }

    default:
      return state;
  }
}

export const getPtaEntities = (state: State) => state.ptaEntities;
export const getMegEntities = (state: State) => state.megEntities;
export const getCarNoQ = (state: State) => state.carNoQ;
export const getPtaSendInfos = createSelector(getPtaEntities, getCarNoQ, (entities, carNoQ) =>
  Object.keys(entities)
    .map(id => entities[id])
    .filter(sendInfo => {
      if (carNoQ) {
        return sendInfo.carNo.toLowerCase().indexOf(carNoQ.toLowerCase()) >= 0;
      }
      return true;
    })
    .sort(DefaultCompare)
);
export const getMegSendInfos = createSelector(getMegEntities, getCarNoQ, (entities, carNoQ) =>
  Object.keys(entities)
    .map(id => entities[id])
    .filter(sendInfo => {
      if (carNoQ) {
        return sendInfo.carNo.toLowerCase().indexOf(carNoQ.toLowerCase()) >= 0;
      }
      return true;
    })
    .sort(DefaultCompare)
);
export const getSendInfos = createSelector(getPtaSendInfos, getMegSendInfos, (ptas, megs) => {
  const result: SendInfo[] = [...ptas, ...megs];
  return result;
});
