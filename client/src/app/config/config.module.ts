import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {SharedModule} from '../shared/shared.module';
import {OperatorUpdateDialogComponent} from './components/operator-update-dialog/operator-update-dialog.component';
import {ReceiveT001UpdateDialogComponent} from './components/receive-t001-update-dialog/receive-t001-update-dialog.component';
import {TransCorpUpdateDialogComponent} from './components/trans-corp-update-dialog/trans-corp-update-dialog.component';
import {ConfigRoutingModule} from './config-routing.module';
import {ConfigNavPageComponent} from './containers/config-nav-page/config-nav-page.component';
import {OperatorManagePageComponent} from './containers/operator-manage-page/operator-manage-page.component';
import {ReceiveT001ManagePageComponent} from './containers/receive-t001-manage-page/receive-t001-manage-page.component';
import {ReceiveT001lManagePageComponent} from './containers/receive-t001l-manage-page/receive-t001l-manage-page.component';
import {TransCorpManagePageComponent} from './containers/trans-corp-manage-page/trans-corp-manage-page.component';
import {WharfT001lManagePageComponent} from './containers/wharf-t001l-manage-page/wharf-t001l-manage-page.component';
import {featureName, reducers} from './store';
import {featureEffects} from './store/effects';

const ENTRYCOMPONENTS = [
  ReceiveT001UpdateDialogComponent,
  TransCorpUpdateDialogComponent,
  OperatorUpdateDialogComponent,
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
    ...ENTRYCOMPONENTS,
  ]
})
export class ConfigModule {
}
