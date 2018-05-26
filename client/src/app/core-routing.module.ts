import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SendRoleGuard} from './services/send-role.guard';

const routes: Routes = [
  {path: '', redirectTo: '/sends', pathMatch: 'full'},
  {path: 'sends', loadChildren: './send.module#SendModule', canActivate: [SendRoleGuard]},
  {path: 'reports', loadChildren: './report.module#ReportModule'},
  {path: 'configs', loadChildren: './config.module#ConfigModule'},
  {path: 'gps', loadChildren: './gps.module#GpsModule'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule {
}
