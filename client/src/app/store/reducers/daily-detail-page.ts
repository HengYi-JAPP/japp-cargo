import {createSelector} from '@ngrx/store';
import {MegSendInfo} from '../../models/meg-send-info';
import {PtaSendInfo} from '../../models/pta-send-info';
import {SendInfo} from '../../models/send-info';
import {DefaultCompare} from '../../services/util.service';
import {dailyDetailPageActions} from '../actions/index';

export interface State {
  startDate: Date;
  endDate: Date;
  ptaEntities: { [id: string]: PtaSendInfo };
  megEntities: { [id: string]: MegSendInfo };
  carNoQ: string;
}

const initialState: State = {
  startDate: new Date(),
  endDate: new Date(),
  ptaEntities: {},
  megEntities: {},
  carNoQ: ''
};

export function reducer(state = initialState, action: dailyDetailPageActions.Actions): State {
  switch (action.type) {
    case dailyDetailPageActions.SEARCH_SUCCESS: {
      const {startDate, endDate, ptas, megs} = action.payload;
      const ptaEntities = PtaSendInfo.toEntities(ptas);
      const megEntities = MegSendInfo.toEntities(megs);
      return {...state, startDate, endDate, ptaEntities, megEntities};
    }
    case dailyDetailPageActions.SET_CARNO_Q: {
      const {carNoQ} = action.payload;
      return {...state, carNoQ};
    }

    default:
      return state;
  }
}

export const getStartDate = (state: State) => state.startDate;
export const getEndDate = (state: State) => state.endDate;
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
