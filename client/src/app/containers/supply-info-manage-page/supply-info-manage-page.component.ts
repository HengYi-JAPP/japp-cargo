import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {MatDialog} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {SupplyInfoUpdateDialogComponent} from '../../components/supply-info-update-dialog/supply-info-update-dialog.component';
import {SupplyInfo} from '../../models/supply-info';
import {UtilService} from '../../services/util.service';
import {supplyInfoManagePageActions} from '../../store/actions/index';
import {supplyInfoManagePageSupplyInfos} from '../../store/config';

@Component({
  selector: 'jcargo-supply-info-manage-page',
  templateUrl: './supply-info-manage-page.component.html',
  styleUrls: ['./supply-info-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SupplyInfoManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-supply-info-manage-page') b2 = true;
  readonly displayedColumns = ['name', 't001s', 'btns'];
  readonly dataSource: SupplyInfoDataSource;

  constructor(private store: Store<any>,
              private dialog: MatDialog,
              private utilService: UtilService) {
    this.dataSource = new SupplyInfoDataSource(this.store);
    this.store.dispatch(new supplyInfoManagePageActions.Init());
  }

  create() {
    this.update(new SupplyInfo());
  }

  update(supplyInfo: SupplyInfo) {
    SupplyInfoUpdateDialogComponent.open(this.dialog, {supplyInfo})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => this.store.dispatch(new supplyInfoManagePageActions.Save({supplyInfo: res})));
  }

  delete(supplyInfo: SupplyInfo) {
    this.utilService.showConfirm()
      .subscribe(() => this.store.dispatch(new supplyInfoManagePageActions.Delete({id: supplyInfo.id})));
  }
}

class SupplyInfoDataSource extends DataSource<SupplyInfo> {
  readonly supplyInfo$: Observable<SupplyInfo[]>;

  constructor(private store: Store<any>) {
    super();
    this.supplyInfo$ = this.store.select(supplyInfoManagePageSupplyInfos);
  }

  connect(collectionViewer: CollectionViewer): Observable<SupplyInfo[]> {
    return this.supplyInfo$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
