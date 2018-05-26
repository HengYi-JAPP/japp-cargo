import {createSelector} from '@ngrx/store';
import {TransCorp} from '../../models/trans-corp';
import {transCorpManagePageActions} from '../actions';

export interface State {
  transCorpEntities: { [id: string]: TransCorp };
}

const initialState: State = {
  transCorpEntities: {},
};

export function reducer(state = initialState, action: transCorpManagePageActions.Actions): State {
  switch (action.type) {
    case transCorpManagePageActions.INIT_SUCCESS: {
      const {transCorps} = action.payload;
      const transCorpEntities = TransCorp.toEntities(transCorps);
      return {...state, transCorpEntities};
    }
    case transCorpManagePageActions.FETCH_T001S_SUCCESS: {
      const {transCorpId, t001s} = action.payload;
      let transCorp = state.transCorpEntities[transCorpId];
      if (!transCorp) {
        return state;
      }
      transCorp = TransCorp.assign(transCorp, {t001s});
      const transCorpEntities = {...state.transCorpEntities};
      transCorpEntities[transCorpId] = transCorp;
      return {...state, transCorpEntities};
    }
    case transCorpManagePageActions.SAVE_SUCCESS: {
      const {transCorp} = action.payload;
      const transCorpEntities = TransCorp.toEntities([transCorp], state.transCorpEntities);
      return {...state, transCorpEntities};
    }
    case transCorpManagePageActions.DELETE_SUCCESS: {
      const {id} = action.payload;
      const transCorpEntities = {...state.transCorpEntities};
      delete transCorpEntities[id];
      return {...state, transCorpEntities};
    }

    default:
      return state;
  }
}

export const getTransCorpEntities = (state: State) => state.transCorpEntities;
export const getTransCorps = createSelector(getTransCorpEntities, entities => Object.keys(entities).map(it => entities[it]));
