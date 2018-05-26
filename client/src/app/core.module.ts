import {NgModule, Optional, SkipSelf} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {ConfirmDialogComponent} from './components/confirm-dialog/confirm-dialog.component';
import {AppComponent} from './containers/app/app.component';
import {CoreRoutingModule} from './core-routing.module';
import {featureEffects, featureName, reducers} from './store/core';
import {AdminGuard} from './services/admin.guard';
import {ApiService} from './services/api.service';
import {SendRoleGuard} from './services/send-role.guard';
import {UtilService} from './services/util.service';
import {SharedModule} from './shared.module';

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
    AdminGuard,
    SendRoleGuard,
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
