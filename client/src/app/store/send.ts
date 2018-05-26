import {createFeatureSelector, createSelector} from '@ngrx/store';
import {SendManagePageEffects} from './effects/send-manage-page.effects';
import * as sendManagePage from './reducers/send-manage-page';

export interface SendState {
  sendManagePage: sendManagePage.State;
}

export const reducers = {
  sendManagePage: sendManagePage.reducer,
};

export const featureEffects = [
  SendManagePageEffects,
];
export const featureName = 'send';
export const featureState = createFeatureSelector<SendState>(featureName);
export const sendManagePageState = createSelector(featureState, state => state.sendManagePage);

export const sendManagePageDate = createSelector(sendManagePageState, sendManagePage.getDate);
export const sendManagePageSendInfos = createSelector(sendManagePageState, sendManagePage.getSendInfos);
