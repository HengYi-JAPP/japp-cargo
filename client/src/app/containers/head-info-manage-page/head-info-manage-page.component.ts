import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {MatDialog} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {HeadInfoUpdateDialogComponent} from '../../components/head-info-update-dialog/head-info-update-dialog.component';
import {HeadInfo} from '../../models/head-info';
import {headInfoManagePageActions} from '../../store/actions/index';
import {UtilService} from '../../services/util.service';
import {headInfoManagePageHeadInfos} from '../../store/config';

@Component({
  selector: 'jcargo-head-info-manage-page',
  templateUrl: './head-info-manage-page.component.html',
  styleUrls: ['./head-info-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeadInfoManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-head-info-manage-page') b2 = true;
  readonly displayedColumns = ['name', 't001s', 'btns'];
  readonly dataSource: HeadInfoDataSource;

  constructor(private store: Store<any>,
              private dialog: MatDialog,
              private utilService: UtilService) {
    this.dataSource = new HeadInfoDataSource(this.store);
    this.store.dispatch(new headInfoManagePageActions.Init());
  }

  create() {
    this.update(new HeadInfo());
  }

  update(headInfo: HeadInfo) {
    HeadInfoUpdateDialogComponent.open(this.dialog, {headInfo})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => this.store.dispatch(new headInfoManagePageActions.Save({headInfo: res})));
  }

  delete(headInfo: HeadInfo) {
    this.utilService.showConfirm()
      .subscribe(() => this.store.dispatch(new headInfoManagePageActions.Delete({id: headInfo.id})));
  }
}

class HeadInfoDataSource extends DataSource<HeadInfo> {
  readonly headInfo$: Observable<HeadInfo[]>;

  constructor(private store: Store<any>) {
    super();
    this.headInfo$ = this.store.select(headInfoManagePageHeadInfos);
  }

  connect(collectionViewer: CollectionViewer): Observable<HeadInfo[]> {
    return this.headInfo$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
