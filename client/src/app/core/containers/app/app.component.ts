import {Component, HostBinding, OnDestroy} from '@angular/core';
import {Title} from '@angular/platform-browser';
import {Store} from '@ngrx/store';
import {TranslateService} from '@ngx-translate/core';
import {Subscription} from 'rxjs/Subscription';
import {coreActions} from '../../store';

@Component({
  selector: 'jcargo-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnDestroy {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-root') b2 = true;
  private _subscriptions: Subscription[] = [];

  constructor(private store: Store<any>,
              private title: Title,
              private translate: TranslateService) {
    this.store.dispatch(new coreActions.FetchAuth());

    this.translate.setDefaultLang('zh_CN');
    this.translate.use('zh_CN');

    this._subscriptions.push(
      this.translate.get('TITLE').subscribe(it => title.setTitle(it)),
    );
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(it => it.unsubscribe());
  }
}
