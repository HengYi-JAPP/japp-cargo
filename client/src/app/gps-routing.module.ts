import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GpsManagePageComponent} from './containers/gps-manage-page/gps-manage-page.component';

const routes: Routes = [
  {
    path: '',
    component: GpsManagePageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GpsRoutingModule {
}
