import {createFeatureSelector, createSelector} from '@ngrx/store';
import * as core from './reducers/core';

export * from './actions';

export interface State {
  core: core.State;
}

export const featureName = 'core';
export const reducers = {
  core: core.reducer,
};
export const featureState = createFeatureSelector<State>(featureName);
export const coreState = createSelector(featureState, (state: State) => state.core);

export const coreAuthOperator = createSelector(coreState, core.getAuthOperator);
export const corePermission = createSelector(coreState, core.getPermission);
export const coreIsAdmin = createSelector(coreState, core.getIsAdmin);
export const coreIsMobile = createSelector(coreState, core.getIsMobile);
export const coreSidenavOpened = createSelector(coreState, core.getSidenavOpened);
export const coreShowSidenav = createSelector(coreState, core.getShowSidenav);
export const coreShowToolbar = createSelector(coreState, core.getShowToolbar);
