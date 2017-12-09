import {NgModule, Optional, SkipSelf} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {SharedModule} from '../shared/shared.module';
import {ConfirmDialogComponent} from './components/confirm-dialog/confirm-dialog.component';
import {AppComponent} from './containers/app/app.component';
import {CoreRoutingModule} from './core-routing.module';
import {ApiService} from './services/api.service';
import {UtilService} from './services/util.service';
import {featureName, reducers} from './store';
import {featureEffects} from './store/effects';

@NgModule({
  imports: [
    SharedModule,
    CoreRoutingModule,
    StoreModule.forFeature(featureName, reducers),
    EffectsModule.forFeature(featureEffects),
  ],
  entryComponents: [
    ConfirmDialogComponent,
  ],
  declarations: [
    AppComponent,
    ConfirmDialogComponent,
  ],
  exports: [
    AppComponent,
  ],
  providers: [
    UtilService,
    ApiService,
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(
        'CoreModule is already loaded. Import it in the AppModule only');
    }
  }
}
