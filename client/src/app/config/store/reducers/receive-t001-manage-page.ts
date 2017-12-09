import {createSelector} from '@ngrx/store';
import {ReceiveT001} from '../../../shared/models/receive-t001';
import {receiveT001ManagePageActions} from '../actions';

export interface State {
  receiveT001Entities: { [id: string]: ReceiveT001 };
}

const initialState: State = {
  receiveT001Entities: {},
};

export function reducer(state = initialState, action: receiveT001ManagePageActions.Actions): State {
  switch (action.type) {
    case receiveT001ManagePageActions.INIT_SUCCESS: {
      const {receiveT001s} = action.payload;
      const receiveT001Entities = ReceiveT001.toEntities(receiveT001s);
      return {...state, receiveT001Entities};
    }
    case receiveT001ManagePageActions.SAVE_SUCCESS: {
      const {receiveT001} = action.payload;
      const receiveT001Entities = ReceiveT001.toEntities([receiveT001], state.receiveT001Entities);
      return {...state, receiveT001Entities};
    }
    case receiveT001ManagePageActions.DELETE_SUCCESS: {
      const {bukrs} = action.payload;
      const receiveT001Entities = {...state.receiveT001Entities};
      delete receiveT001Entities[bukrs];
      return {...state, receiveT001Entities};
    }

    default:
      return state;
  }
}

export const getReceiveT001Entities = (state: State) => state.receiveT001Entities;
export const getReceiveT001s = createSelector(getReceiveT001Entities, entities => Object.keys(entities).map(it => entities[it]));
