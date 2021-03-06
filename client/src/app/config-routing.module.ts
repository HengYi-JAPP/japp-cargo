import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminGuard} from './services/admin.guard';
import {ConfigNavPageComponent} from './containers/config-nav-page/config-nav-page.component';
import {HeadInfoManagePageComponent} from './containers/head-info-manage-page/head-info-manage-page.component';
import {OperatorManagePageComponent} from './containers/operator-manage-page/operator-manage-page.component';
import {ReceiveT001ManagePageComponent} from './containers/receive-t001-manage-page/receive-t001-manage-page.component';
import {ReceiveT001lManagePageComponent} from './containers/receive-t001l-manage-page/receive-t001l-manage-page.component';
import {SupplyInfoManagePageComponent} from './containers/supply-info-manage-page/supply-info-manage-page.component';
import {TransCorpManagePageComponent} from './containers/trans-corp-manage-page/trans-corp-manage-page.component';
import {WharfT001lManagePageComponent} from './containers/wharf-t001l-manage-page/wharf-t001l-manage-page.component';

const routes: Routes = [
  {
    path: '',
    component: ConfigNavPageComponent,
    children: [
      {
        path: 'receiveT001s',
        component: ReceiveT001ManagePageComponent,
      },
      {
        path: 'wharfT001ls',
        component: WharfT001lManagePageComponent,
      },
      {
        path: 'receiveT001ls',
        component: ReceiveT001lManagePageComponent,
      },
      {
        path: 'transCorps',
        component: TransCorpManagePageComponent,
      },
      {
        path: 'headInfos',
        component: HeadInfoManagePageComponent,
      },
      {
        path: 'supplyInfos',
        component: SupplyInfoManagePageComponent,
      },
      {
        path: 'operators',
        component: OperatorManagePageComponent,
        canActivate: [AdminGuard]
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigRoutingModule {
}
