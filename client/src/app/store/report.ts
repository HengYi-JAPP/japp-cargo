import {createFeatureSelector, createSelector} from '@ngrx/store';
import {DailyDetailPageEffects} from './effects/daily-detail-page.effects';
import * as dailyDetailPage from './reducers/daily-detail-page';

export interface ReportState {
  dailyDetailPage: dailyDetailPage.State;
}

export const reducers = {
  dailyDetailPage: dailyDetailPage.reducer,
};

export const featureName = 'report';
export const featureState = createFeatureSelector<ReportState>(featureName);
export const featureEffects = [
  DailyDetailPageEffects,
];
export const dailyDetailPageState = createSelector(featureState, state => state.dailyDetailPage);

export const dailyDetailPageStartDate = createSelector(dailyDetailPageState, dailyDetailPage.getStartDate);
export const dailyDetailPageEndDate = createSelector(dailyDetailPageState, dailyDetailPage.getEndDate);
export const dailyDetailPageSendInfos = createSelector(dailyDetailPageState, dailyDetailPage.getSendInfos);
