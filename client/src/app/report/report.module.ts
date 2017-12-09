import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {ReportRoutingModule} from './report-routing.module';

@NgModule({
  imports: [
    SharedModule,
    ReportRoutingModule
  ],
  declarations: []
})
export class ReportModule {
}
