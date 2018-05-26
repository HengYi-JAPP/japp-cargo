import {createSelector} from '@ngrx/store';
import {isNullOrUndefined} from 'util';
import {operatorManagePageActions} from '../actions';
import { Operator } from '../../models/operator';

export interface State {
  operatorEntities: { [id: string]: Operator };
  first: number;
  count: number;
  pageSize: number;
}

const initialState: State = {
  operatorEntities: {},
  first: 0,
  count: null,
  pageSize: null,
};

export function reducer(state = initialState, action: operatorManagePageActions.Actions): State {
  switch (action.type) {
    case operatorManagePageActions.INIT_SUCCESS: {
      const {first, count, pageSize, operators} = action.payload;
      const operatorEntities = Operator.toEntities(operators);
      return {...state, first, count, pageSize, operatorEntities};
    }
    case operatorManagePageActions.FETCH_PERMISSION_SUCCESS: {
      const {operatorId, permission, admin} = action.payload;
      let operator = state.operatorEntities[operatorId];
      if (!operator) {
        return state;
      }
      operator = Operator.assign(operator, {permission});
      if (!isNullOrUndefined(admin)) {
        operator.admin = admin;
      }
      const operatorEntities = {...state.operatorEntities};
      operatorEntities[operatorId] = operator;
      return {...state, operatorEntities};
    }

    default:
      return state;
  }
}

export const getOperatorEntities = (state: State) => state.operatorEntities;
export const getCount = (state: State) => state.count;
export const getPageSize = (state: State) => state.pageSize;
export const getOperators = createSelector(getOperatorEntities, entities => Object.keys(entities).map(it => entities[it]));
