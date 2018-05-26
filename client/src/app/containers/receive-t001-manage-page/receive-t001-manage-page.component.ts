import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {MatDialog} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {ReceiveT001UpdateDialogComponent} from '../../components/receive-t001-update-dialog/receive-t001-update-dialog.component';
import {ReceiveT001} from '../../models/receive-t001';
import {UtilService} from '../../services/util.service';
import {receiveT001ManagePageActions} from '../../store/actions/index';
import {receiveT001ManagePageReceiveT001s} from '../../store/config';
import {coreIsMobile} from '../../store/core';

@Component({
  selector: 'jcargo-receive-t001-manage-page',
  templateUrl: './receive-t001-manage-page.component.html',
  styleUrls: ['./receive-t001-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReceiveT001ManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-receive-t001-manage-page') b2 = true;
  readonly isMobile$: Observable<boolean>;
  readonly displayedColumns = ['butxt', 'bukrs', 'kunnr', 'lifnr', 'btns'];
  readonly dataSource: ReceiveT001DataSource;

  constructor(private store: Store<any>,
              private dialog: MatDialog,
              private utilService: UtilService) {
    this.isMobile$ = this.store.select(coreIsMobile);
    this.dataSource = new ReceiveT001DataSource(this.store);
    this.store.dispatch(new receiveT001ManagePageActions.Init());
  }

  create() {
    this.update(new ReceiveT001());
  }

  update(receiveT001: ReceiveT001) {
    ReceiveT001UpdateDialogComponent.open(this.dialog, {receiveT001})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => this.store.dispatch(new receiveT001ManagePageActions.Save({receiveT001: res})));
  }

  delete(receiveT001: ReceiveT001) {
    this.utilService.showConfirm()
      .subscribe(() => this.store.dispatch(new receiveT001ManagePageActions.Delete({bukrs: receiveT001.bukrs})));
  }

}

class ReceiveT001DataSource extends DataSource<ReceiveT001> {
  private readonly receiveT001s$: Observable<ReceiveT001[]>;

  constructor(private store: Store<any>) {
    super();
    this.receiveT001s$ = this.store.select(receiveT001ManagePageReceiveT001s);
  }

  connect(collectionViewer: CollectionViewer): Observable<ReceiveT001[]> {
    return this.receiveT001s$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
