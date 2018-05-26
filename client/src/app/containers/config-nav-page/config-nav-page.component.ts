import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {Store} from '@ngrx/store';
import {coreIsAdmin} from '../../store/core';

@Component({
  selector: 'jcargo-config-nav-page',
  templateUrl: './config-nav-page.component.html',
  styleUrls: ['./config-nav-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ConfigNavPageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-config-nav-page') b2 = true;

  constructor(private store: Store<any>) {
  }

  get navLinks$() {
    return this.store.select(coreIsAdmin)
      .map(isAdmin => {
        const navLinks = [
          {path: '/configs/receiveT001s', label: 'RECEIVET001'},
          {path: '/configs/receiveT001ls', label: 'RECEIVET001L'},
          {path: '/configs/wharfT001ls', label: 'WHARFT001L'},
          {path: '/configs/supplyInfos', label: 'SUPPLYINFO'},
          {path: '/configs/transCorps', label: 'TRANSCORP'},
          {path: '/configs/headInfos', label: 'HEADINFO'},
        ];
        if (isAdmin) {
          navLinks.push({path: '/configs/operators', label: '人员'});
        }
        return navLinks;
      });
  }

}
