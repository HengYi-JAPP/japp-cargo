import {Component, HostBinding, OnDestroy} from '@angular/core';
import {MatDialog} from '@angular/material';
import {Title} from '@angular/platform-browser';
import {Store} from '@ngrx/store';
import {TranslateService} from '@ngx-translate/core';
import {Observable} from 'rxjs/Observable';
import {Subscription} from 'rxjs/Subscription';
import {DailyDetailExportDialogComponent} from '../../components/daily-detail-export-dialog/daily-detail-export-dialog.component';
import {OperatorPermission} from '../../models/operator-permission';
import {corePermission} from '../../store/core';
import {coreActions} from '../../store/actions/index';

@Component({
  selector: 'jcargo-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnDestroy {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-root') b2 = true;
  private readonly _subscriptions: Subscription[] = [];
  private operatorPermission$: Observable<OperatorPermission>;

  constructor(private store: Store<any>,
              private title: Title,
              private dialog: MatDialog,
              private translate: TranslateService) {
    this.translate.setDefaultLang('zh_CN');
    this.translate.use('zh_CN');
    this._subscriptions.push(
      this.translate.get('TITLE').subscribe(it => title.setTitle(it)),
    );

    this.operatorPermission$ = this.store.select(corePermission);
    this.store.dispatch(new coreActions.FetchAuth());
  }

  get showSends$(): Observable<boolean> {
    return this.operatorPermission$.map(it => !!(it && it.defaultReceiveT001));
  }

  export() {
    DailyDetailExportDialogComponent.open(this.dialog);
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(it => it.unsubscribe());
  }
}
