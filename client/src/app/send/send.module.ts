import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {SharedModule} from '../shared/shared.module';
import {SendUpdateDialogComponent} from './components/send-update-dialog/send-update-dialog.component';
import {SendManagePageComponent} from './containers/send-manage-page/send-manage-page.component';
import {SendRoutingModule} from './send-routing.module';
import {featureName, reducers} from './store';
import {featureEffects} from './store/effects';

const ENTRYCOMPONENTS = [
  SendUpdateDialogComponent,
];

@NgModule({
  imports: [
    SharedModule,
    SendRoutingModule,
    StoreModule.forFeature(featureName, reducers),
    EffectsModule.forFeature(featureEffects),
  ],
  entryComponents: ENTRYCOMPONENTS,
  declarations: [
    SendManagePageComponent,
    ...ENTRYCOMPONENTS
  ]
})
export class SendModule {
}
