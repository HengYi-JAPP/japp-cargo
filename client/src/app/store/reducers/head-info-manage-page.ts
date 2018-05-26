import {createSelector} from '@ngrx/store';
import {headInfoManagePageActions} from '../actions';
import { HeadInfo } from '../../models/head-info';

export interface State {
  headInfoEntities: { [id: string]: HeadInfo };
}

const initialState: State = {
  headInfoEntities: {},
};

export function reducer(state = initialState, action: headInfoManagePageActions.Actions): State {
  switch (action.type) {
    case headInfoManagePageActions.INIT_SUCCESS: {
      const {headInfos} = action.payload;
      const headInfoEntities = HeadInfo.toEntities(headInfos);
      return {...state, headInfoEntities};
    }
    case headInfoManagePageActions.FETCH_T001S_SUCCESS: {
      const {headInfoId, t001s} = action.payload;
      let headInfo = state.headInfoEntities[headInfoId];
      if (!headInfo) {
        return state;
      }
      headInfo = HeadInfo.assign(headInfo, {t001s});
      const headInfoEntities = {...state.headInfoEntities};
      headInfoEntities[headInfoId] = headInfo;
      return {...state, headInfoEntities};
    }
    case headInfoManagePageActions.SAVE_SUCCESS: {
      const {headInfo} = action.payload;
      const headInfoEntities = HeadInfo.toEntities([headInfo], state.headInfoEntities);
      return {...state, headInfoEntities};
    }
    case headInfoManagePageActions.DELETE_SUCCESS: {
      const {id} = action.payload;
      const headInfoEntities = {...state.headInfoEntities};
      delete headInfoEntities[id];
      return {...state, headInfoEntities};
    }

    default:
      return state;
  }
}

export const getHeadInfoEntities = (state: State) => state.headInfoEntities;
export const getHeadInfos = createSelector(getHeadInfoEntities, entities => Object.keys(entities).map(it => entities[it]));
