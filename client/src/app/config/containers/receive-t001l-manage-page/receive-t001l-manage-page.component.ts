import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatSort} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/take';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {UtilService} from '../../../core/services/util.service';
import {PickT001lDialogComponent} from '../../../shared/components/pick-t001l-dialog/pick-t001l-dialog.component';
import {ReceiveT001l} from '../../../shared/models/receive-t001l';
import {receiveT001lManagePageActions, receiveT001lManagePageReceiveT001ls} from '../../store/index';

@Component({
  selector: 'jcargo-receive-t001l-manage-page',
  templateUrl: './receive-t001l-manage-page.component.html',
  styleUrls: ['./receive-t001l-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReceiveT001lManagePageComponent implements OnInit {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-receive-t001l-manage-page') b2 = true;
  @ViewChild(MatSort) sort: MatSort;
  readonly displayedColumns = ['lgobe', 'lgort', 'werks', 'btns'];
  readonly dataSource: ReceiveT001lDataSource;

  constructor(private store: Store<any>,
              private dialog: MatDialog,
              private utilService: UtilService) {
    this.dataSource = new ReceiveT001lDataSource(this.store);
    this.store.dispatch(new receiveT001lManagePageActions.Init());
  }

  ngOnInit() {
    this.sort.sortChange
      .subscribe(sort => this.store.dispatch(new receiveT001lManagePageActions.SetSort({sort})));
  }

  create() {
    this.dataSource.receiveT001ls$.take(1)
      .switchMap(receiveT001ls => {
        const filteredT001ls = receiveT001ls.map(it => it.t001l);
        return PickT001lDialogComponent.open(this.dialog, {filteredT001ls, title: '新增卸货地', multi: true}).afterClosed();
      })
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(receiveT001ls => {
        receiveT001ls.forEach(receiveT001l => {
          this.store.dispatch(new receiveT001lManagePageActions.Save({receiveT001l}));
        });
      });
  }

  delete(pk: ReceiveT001l) {
    this.utilService.showConfirm()
      .subscribe(() => this.store.dispatch(new receiveT001lManagePageActions.Delete({pk})));
  }
}

class ReceiveT001lDataSource extends DataSource<ReceiveT001l> {
  readonly receiveT001ls$: Observable<ReceiveT001l[]>;

  constructor(private store: Store<any>) {
    super();
    this.receiveT001ls$ = this.store.select(receiveT001lManagePageReceiveT001ls);
  }

  connect(collectionViewer: CollectionViewer): Observable<ReceiveT001l[]> {
    return this.receiveT001ls$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
