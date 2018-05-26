import {createSelector} from '@ngrx/store';
import {SupplyInfo} from '../../models/supply-info';
import {supplyInfoManagePageActions} from '../actions';

export interface State {
  supplyInfoEntities: { [id: string]: SupplyInfo };
}

const initialState: State = {
  supplyInfoEntities: {},
};

export function reducer(state = initialState, action: supplyInfoManagePageActions.Actions): State {
  switch (action.type) {
    case supplyInfoManagePageActions.INIT_SUCCESS: {
      const {supplyInfos} = action.payload;
      const supplyInfoEntities = SupplyInfo.toEntities(supplyInfos);
      return {...state, supplyInfoEntities};
    }
    case supplyInfoManagePageActions.FETCH_T001S_SUCCESS: {
      const {supplyInfoId, t001s} = action.payload;
      let supplyInfo = state.supplyInfoEntities[supplyInfoId];
      if (!supplyInfo) {
        return state;
      }
      supplyInfo = SupplyInfo.assign(supplyInfo, {t001s});
      const supplyInfoEntities = {...state.supplyInfoEntities};
      supplyInfoEntities[supplyInfoId] = supplyInfo;
      return {...state, supplyInfoEntities};
    }
    case supplyInfoManagePageActions.SAVE_SUCCESS: {
      const {supplyInfo} = action.payload;
      const supplyInfoEntities = SupplyInfo.toEntities([supplyInfo], state.supplyInfoEntities);
      return {...state, supplyInfoEntities};
    }
    case supplyInfoManagePageActions.DELETE_SUCCESS: {
      const {id} = action.payload;
      const supplyInfoEntities = {...state.supplyInfoEntities};
      delete supplyInfoEntities[id];
      return {...state, supplyInfoEntities};
    }

    default:
      return state;
  }
}

export const getSupplyInfoEntities = (state: State) => state.supplyInfoEntities;
export const getSupplyInfos = createSelector(getSupplyInfoEntities, entities => Object.keys(entities).map(it => entities[it]));
