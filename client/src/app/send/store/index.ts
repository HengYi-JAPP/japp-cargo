import {createFeatureSelector, createSelector} from '@ngrx/store';
import * as sendManagePage from './reducers/send-manage-page';

export * from './actions';

export interface PtaState {
  sendManagePage: sendManagePage.State;
}

export const reducers = {
  sendManagePage: sendManagePage.reducer,
};

export const featureName = 'send';
export const featureState = createFeatureSelector<PtaState>(featureName);
export const sendManagePageState = createSelector(featureState, state => state.sendManagePage);

export const sendManagePageSendInfos = createSelector(sendManagePageState, sendManagePage.getSendInfos);
