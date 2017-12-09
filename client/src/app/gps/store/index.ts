import {createFeatureSelector, createSelector} from '@ngrx/store';
import * as gpsManagePage from './reducers/gps-manage-page';

export * from './actions';

export interface PtaState {
  gpsManagePage: gpsManagePage.State;
}

export const reducers = {
  gpsManagePage: gpsManagePage.reducer,
};

export const featureName = 'gps';
export const featureState = createFeatureSelector<PtaState>(featureName);
export const gpsManagePageState = createSelector(featureState, state => state.gpsManagePage);

export const gpsManagePageYlipses = createSelector(gpsManagePageState, gpsManagePage.getYlipses);
