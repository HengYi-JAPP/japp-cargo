import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {SharedModule} from './shared.module';
import {DailyDetailPageComponent} from './containers/daily-detail-page/daily-detail-page.component';
import {ReportRoutingModule} from './report-routing.module';
import {featureName, reducers,featureEffects} from './store/report';

const ENTRYCOMPONENTS = [];

@NgModule({
  imports: [
    SharedModule,
    ReportRoutingModule,
    StoreModule.forFeature(featureName, reducers),
    EffectsModule.forFeature(featureEffects),
  ],
  entryComponents: ENTRYCOMPONENTS,
  declarations: [
    DailyDetailPageComponent,
    ...ENTRYCOMPONENTS
  ]
})
export class ReportModule {
}
