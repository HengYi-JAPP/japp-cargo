import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {HeadInfoUpdateDialogComponent} from './components/head-info-update-dialog/head-info-update-dialog.component';
import {OperatorUpdateDialogComponent} from './components/operator-update-dialog/operator-update-dialog.component';
import {ReceiveT001UpdateDialogComponent} from './components/receive-t001-update-dialog/receive-t001-update-dialog.component';
import {SupplyInfoUpdateDialogComponent} from './components/supply-info-update-dialog/supply-info-update-dialog.component';
import {TransCorpUpdateDialogComponent} from './components/trans-corp-update-dialog/trans-corp-update-dialog.component';
import {ConfigRoutingModule} from './config-routing.module';
import {ConfigNavPageComponent} from './containers/config-nav-page/config-nav-page.component';
import {HeadInfoManagePageComponent} from './containers/head-info-manage-page/head-info-manage-page.component';
import {OperatorManagePageComponent} from './containers/operator-manage-page/operator-manage-page.component';
import {ReceiveT001ManagePageComponent} from './containers/receive-t001-manage-page/receive-t001-manage-page.component';
import {ReceiveT001lManagePageComponent} from './containers/receive-t001l-manage-page/receive-t001l-manage-page.component';
import {SupplyInfoManagePageComponent} from './containers/supply-info-manage-page/supply-info-manage-page.component';
import {TransCorpManagePageComponent} from './containers/trans-corp-manage-page/trans-corp-manage-page.component';
import {WharfT001lManagePageComponent} from './containers/wharf-t001l-manage-page/wharf-t001l-manage-page.component';
import {SharedModule} from './shared.module';
import {featureEffects, featureName, reducers} from './store/config';

const ENTRYCOMPONENTS = [
  ReceiveT001UpdateDialogComponent,
  TransCorpUpdateDialogComponent,
  OperatorUpdateDialogComponent,
  HeadInfoUpdateDialogComponent,
  SupplyInfoUpdateDialogComponent,
];

@NgModule({
  imports: [
    ConfigRoutingModule,
    SharedModule,
    StoreModule.forFeature(featureName, reducers),
    EffectsModule.forFeature(featureEffects),
  ],
  entryComponents: ENTRYCOMPONENTS,
  declarations: [
    ConfigNavPageComponent,
    ReceiveT001ManagePageComponent,
    ReceiveT001lManagePageComponent,
    WharfT001lManagePageComponent,
    TransCorpManagePageComponent,
    OperatorManagePageComponent,
    HeadInfoManagePageComponent,
    SupplyInfoManagePageComponent,
    ...ENTRYCOMPONENTS,
  ]
})
export class ConfigModule {
}
