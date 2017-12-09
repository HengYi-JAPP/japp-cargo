import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {SharedModule} from '../shared/shared.module';
import {GpsMapDialogComponent} from './components/gps-map-dialog/gps-map-dialog.component';
import {GpsManagePageComponent} from './containers/gps-manage-page/gps-manage-page.component';
import {GpsRoutingModule} from './gps-routing.module';
import {featureName, reducers} from './store';
import {featureEffects} from './store/effects';

const ENTRYCOMPONENTS = [
  GpsMapDialogComponent,
];

@NgModule({
  imports: [
    GpsRoutingModule,
    SharedModule,
    StoreModule.forFeature(featureName, reducers),
    EffectsModule.forFeature(featureEffects),
  ],
  entryComponents: ENTRYCOMPONENTS,
  declarations: [
    GpsManagePageComponent,
    ...ENTRYCOMPONENTS
  ]
})
export class GpsModule {
}
