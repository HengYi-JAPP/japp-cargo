import {Sort} from '@angular/material';
import {createSelector} from '@ngrx/store';
import {ReceiveT001l} from '../../../shared/models/receive-t001l';
import {receiveT001lManagePageActions} from '../actions';

export interface State {
  receiveT001lEntities: { [id: string]: ReceiveT001l };
  sort: Sort;
}

const initialState: State = {
  receiveT001lEntities: {},
  sort: null,
};

export function reducer(state = initialState, action: receiveT001lManagePageActions.Actions): State {
  switch (action.type) {
    case receiveT001lManagePageActions.INIT_SUCCESS: {
      const {receiveT001ls} = action.payload;
      const receiveT001lEntities = ReceiveT001l.toEntities(receiveT001ls);
      return {...state, receiveT001lEntities};
    }
    case receiveT001lManagePageActions.SET_SORT: {
      const {sort} = action.payload;
      return {...state, sort};
    }
    case receiveT001lManagePageActions.SAVE_SUCCESS: {
      const {receiveT001l} = action.payload;
      const receiveT001lEntities = ReceiveT001l.toEntities([receiveT001l], state.receiveT001lEntities);
      return {...state, receiveT001lEntities};
    }
    case receiveT001lManagePageActions.DELETE_SUCCESS: {
      const {pk} = action.payload;
      const wharfT001ls = Object.keys(state.receiveT001lEntities)
        .map(it => state.receiveT001lEntities[it])
        .filter(it => !(it.werks === pk.werks && it.lgort === pk.lgort));
      const receiveT001lEntities = ReceiveT001l.toEntities(wharfT001ls);
      return {...state, receiveT001lEntities};
    }

    default:
      return state;
  }
}

export const getReceiveT001lEntities = (state: State) => state.receiveT001lEntities;
export const getSort = (state: State) => state.sort;
export const getReceiveT001ls = createSelector(getReceiveT001lEntities, getSort, (entities, sort) => {
  const result = Object.keys(entities).map(it => entities[it]);
  if (!sort || !sort.active || sort.direction === '') {
    return result;
  }
  return result.sort((a, b) => {
    let propertyA: number | string = '';
    let propertyB: number | string = '';
    switch (sort.active) {
      case 'lgort': {
        [propertyA, propertyB] = [a.lgort, b.lgort];
        break;
      }
      case 'werks': {
        [propertyA, propertyB] = [a.werks, b.werks];
        break;
      }
    }
    const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
    const valueB = isNaN(+propertyB) ? propertyB : +propertyB;
    return (valueA < valueB ? -1 : 1) * (sort.direction === 'asc' ? 1 : -1);
  });
});
