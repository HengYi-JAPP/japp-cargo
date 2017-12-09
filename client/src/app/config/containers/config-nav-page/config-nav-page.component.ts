import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';

@Component({
  selector: 'jcargo-config-nav-page',
  templateUrl: './config-nav-page.component.html',
  styleUrls: ['./config-nav-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ConfigNavPageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-config-nav-page') b2 = true;
  readonly navLinks = [
    {path: '/configs/receiveT001s', label: '收货公司'},
    {path: '/configs/receiveT001ls', label: '卸货地'},
    {path: '/configs/wharfT001ls', label: '码头'},
    {path: '/configs/transCorps', label: '物流公司'},
    {path: '/configs/operators', label: '人员'},
  ];

  constructor() {
  }

}
