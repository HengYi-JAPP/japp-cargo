import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SendManagePageComponent} from './containers/send-manage-page/send-manage-page.component';

const routes: Routes = [
  {
    path: '',
    component: SendManagePageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SendRoutingModule {
}
