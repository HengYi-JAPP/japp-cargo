import {createFeatureSelector, createSelector} from '@ngrx/store';
import {HeadInfoManagePageEffects} from './effects/head-info-manage-page.effects';
import {OperatorManagePageEffects} from './effects/operator-manage-page.effects';
import {ReceiveT001ManagePageEffects} from './effects/receive-t001-manage-page.effects';
import {ReceiveT001lManagePageEffects} from './effects/receive-t001l-manage-page.effects';
import {SupplyInfoManagePageEffects} from './effects/supply-info-manage-page.effects';
import {TransCorpManagePageEffects} from './effects/trans-corp-manage-page.effects';
import {WharfT001lManagePageEffects} from './effects/wharf-t001l-manage-page.effects';
import * as headInfoManagePage from './reducers/head-info-manage-page';
import * as operatorManagePage from './reducers/operator-manage-page';
import * as receiveT001ManagePage from './reducers/receive-t001-manage-page';
import * as receiveT001lManagePage from './reducers/receive-t001l-manage-page';
import * as supplyInfoManagePage from './reducers/supply-info-manage-page';
import * as transCorpManagePage from './reducers/trans-corp-manage-page';
import * as wharfT001lManagePage from './reducers/wharf-t001l-manage-page';

export interface PtaState {
  receiveT001ManagePage: receiveT001ManagePage.State;
  receiveT001lManagePage: receiveT001lManagePage.State;
  wharfT001lManagePage: wharfT001lManagePage.State;
  transCorpManagePage: transCorpManagePage.State;
  operatorManagePage: operatorManagePage.State;
  headInfoManagePage: headInfoManagePage.State;
  supplyInfoManagePage: supplyInfoManagePage.State;
}

export const reducers = {
  receiveT001ManagePage: receiveT001ManagePage.reducer,
  receiveT001lManagePage: receiveT001lManagePage.reducer,
  wharfT001lManagePage: wharfT001lManagePage.reducer,
  transCorpManagePage: transCorpManagePage.reducer,
  operatorManagePage: operatorManagePage.reducer,
  headInfoManagePage: headInfoManagePage.reducer,
  supplyInfoManagePage: supplyInfoManagePage.reducer,
};

export const featureName = 'config';
export const featureState = createFeatureSelector<PtaState>(featureName);
export const featureEffects = [
  ReceiveT001ManagePageEffects,
  ReceiveT001lManagePageEffects,
  WharfT001lManagePageEffects,
  TransCorpManagePageEffects,
  OperatorManagePageEffects,
  HeadInfoManagePageEffects,
  SupplyInfoManagePageEffects,
];
export const receiveT001ManagePageState = createSelector(featureState, state => state.receiveT001ManagePage);
export const receiveT001lManagePageState = createSelector(featureState, state => state.receiveT001lManagePage);
export const wharfT001lManagePageState = createSelector(featureState, state => state.wharfT001lManagePage);
export const transCorpManagePageState = createSelector(featureState, state => state.transCorpManagePage);
export const operatorManagePageState = createSelector(featureState, state => state.operatorManagePage);
export const headInfoManagePageState = createSelector(featureState, state => state.headInfoManagePage);
export const supplyInfoManagePageState = createSelector(featureState, state => state.supplyInfoManagePage);

export const receiveT001ManagePageReceiveT001s = createSelector(receiveT001ManagePageState, receiveT001ManagePage.getReceiveT001s);
export const receiveT001lManagePageReceiveT001ls = createSelector(receiveT001lManagePageState, receiveT001lManagePage.getReceiveT001ls);
export const wharfT001lManagePageWharfT001ls = createSelector(wharfT001lManagePageState, wharfT001lManagePage.getWharfT001ls);
export const transCorpManagePageTransCorps = createSelector(transCorpManagePageState, transCorpManagePage.getTransCorps);
export const headInfoManagePageHeadInfos = createSelector(headInfoManagePageState, headInfoManagePage.getHeadInfos);
export const supplyInfoManagePageSupplyInfos = createSelector(supplyInfoManagePageState, supplyInfoManagePage.getSupplyInfos);
export const operatorManagePageOperators = createSelector(operatorManagePageState, operatorManagePage.getOperators);
export const operatorManagePageCount = createSelector(operatorManagePageState, operatorManagePage.getCount);
export const operatorManagePagePageSize = createSelector(operatorManagePageState, operatorManagePage.getPageSize);
