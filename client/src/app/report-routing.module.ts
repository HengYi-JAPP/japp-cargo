import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DailyDetailPageComponent} from './containers/daily-detail-page/daily-detail-page.component';

const routes: Routes = [
  {
    path: 'dailyDetail',
    component: DailyDetailPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportRoutingModule {
}
