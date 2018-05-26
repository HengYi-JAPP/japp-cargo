import {createSelector} from '@ngrx/store';
import {Ylips} from '../../models/ylips';
import {DefaultCompare} from '../../services/util.service';
import {gpsManagePageActions} from '../actions/index';

export interface State {
  ylipsEntities: { [id: string]: Ylips };
  carNoQ: string;
}

const initialState: State = {
  ylipsEntities: {},
  carNoQ: ''
};

export function reducer(state = initialState, action: gpsManagePageActions.Actions): State {
  switch (action.type) {
    case gpsManagePageActions.INIT_SUCCESS: {
      const {ylipses} = action.payload;
      const ylipsEntities = Ylips.toEntities(ylipses);
      return {...state, ylipsEntities};
    }
    case gpsManagePageActions.SET_CARNO_Q: {
      const {carNoQ} = action.payload;
      return {...state, carNoQ};
    }

    default:
      return state;
  }
}

export const getYlipsEntities = (state: State) => state.ylipsEntities;
export const getCarNoQ = (state: State) => state.carNoQ;
export const getYlipses = createSelector(getYlipsEntities, getCarNoQ, (entities, carNoQ) =>
  Object.keys(entities)
    .map(id => entities[id])
    .filter(ylips => {
      if (carNoQ) {
        return ylips.carNo.toLowerCase().indexOf(carNoQ.toLowerCase()) >= 0;
      }
      return true;
    })
    .sort(DefaultCompare)
);
