import {createSelector} from '@ngrx/store';
import {Operator} from '../../models/operator';
import {OperatorPermission} from '../../models/operator-permission';
import {coreActions} from '../actions/index';

export interface State {
  authOperator: Operator;
  permission: OperatorPermission;
  isMobile: boolean;
  sidenavOpened: boolean;
  showSidenav: boolean;
  showToolbar: boolean;
}

const initialState: State = {
  authOperator: null,
  permission: null,
  isMobile: false,
  sidenavOpened: false,
  showSidenav: false,
  showToolbar: false,
};

export function reducer(state = initialState, action: coreActions.Actions): State {
  switch (action.type) {
    case coreActions.CLOSE_SIDENAV: {
      return {...state, sidenavOpened: false};
    }

    case coreActions.OPEN_SIDENAV: {
      return {...state, sidenavOpened: true};
    }

    case coreActions.SET_IS_MOBILE: {
      const isMobile = action.payload;
      return {...state, isMobile, sidenavOpened: !isMobile};
    }

    case coreActions.SET_SHOW_SIDENAV: {
      return {...state, showSidenav: action.payload};
    }

    case coreActions.SET_SHOW_TOOLBAR: {
      return {...state, showToolbar: action.payload};
    }

    case coreActions.FETCH_AUTH_SUCCESS: {
      const {operator, permission} = action.payload;
      return {...state, authOperator: operator, permission};
    }

    default:
      return state;
  }
}

export const getAuthOperator = (state: State) => state.authOperator;
export const getPermission = (state: State) => state.permission;
export const getIsAdmin = createSelector(getAuthOperator, it => it.admin);
export const getIsMobile = (state: State) => state.isMobile;
export const getSidenavOpened = (state: State) => state.sidenavOpened;
export const getShowSidenav = (state: State) => state.showSidenav;
export const getShowToolbar = (state: State) => state.showToolbar;
