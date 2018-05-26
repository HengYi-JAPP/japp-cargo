import {Sort} from '@angular/material';
import {createSelector} from '@ngrx/store';
import {ReceiveT001l} from '../../models/receive-t001l';
import {WharfT001l} from '../../models/wharf-t001l';
import {wharfT001lManagePageActions} from '../actions';

export interface State {
  wharfT001lEntities: { [id: string]: WharfT001l };
  sort: Sort;
}

const initialState: State = {
  wharfT001lEntities: {},
  sort: null,
};

export function reducer(state = initialState, action: wharfT001lManagePageActions.Actions): State {
  switch (action.type) {
    case wharfT001lManagePageActions.INIT_SUCCESS: {
      const {wharfT001ls} = action.payload;
      const wharfT001lEntities = WharfT001l.toEntities(wharfT001ls);
      return {...state, wharfT001lEntities};
    }
    case wharfT001lManagePageActions.SET_SORT: {
      const {sort} = action.payload;
      return {...state, sort};
    }
    case wharfT001lManagePageActions.SAVE_SUCCESS: {
      const {wharfT001l} = action.payload;
      const wharfT001lEntities = ReceiveT001l.toEntities([wharfT001l], state.wharfT001lEntities);
      return {...state, wharfT001lEntities};
    }
    case wharfT001lManagePageActions.DELETE_SUCCESS: {
      const {pk} = action.payload;
      const wharfT001ls = Object.keys(state.wharfT001lEntities)
        .map(it => state.wharfT001lEntities[it])
        .filter(it => !(it.werks === pk.werks && it.lgort === pk.lgort));
      const wharfT001lEntities = ReceiveT001l.toEntities(wharfT001ls);
      return {...state, wharfT001lEntities};
    }

    default:
      return state;
  }
}

export const getWharfT001lEntities = (state: State) => state.wharfT001lEntities;
export const getSort = (state: State) => state.sort;
export const getWharfT001ls = createSelector(getWharfT001lEntities, getSort, (entities, sort) => {
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
