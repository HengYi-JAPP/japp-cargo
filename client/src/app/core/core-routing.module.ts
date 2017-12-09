import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: '/sends', pathMatch: 'full'},
  {path: 'sends', loadChildren: '../send/send.module#SendModule'},
  {path: 'reports', loadChildren: '../report/report.module#ReportModule'},
  {path: 'configs', loadChildren: '../config/config.module#ConfigModule'},
  {path: 'gps', loadChildren: '../gps/gps.module#GpsModule'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule {
}
