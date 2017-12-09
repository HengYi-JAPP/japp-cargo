import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatSort} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {UtilService} from '../../../core/services/util.service';
import {PickT001lDialogComponent} from '../../../shared/components/pick-t001l-dialog/pick-t001l-dialog.component';
import {WharfT001l} from '../../../shared/models/wharf-t001l';
import {wharfT001lManagePageActions, wharfT001lManagePageWharfT001ls} from '../../store';

@Component({
  selector: 'jcargo-wharf-t001l-manage-page',
  templateUrl: './wharf-t001l-manage-page.component.html',
  styleUrls: ['./wharf-t001l-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WharfT001lManagePageComponent implements OnInit {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-wharf-t001l-manage-page') b2 = true;
  @ViewChild(MatSort) sort: MatSort;
  readonly displayedColumns = ['lgobe', 'lgort', 'werks', 'btns'];
  readonly dataSource: WharfT001lDataSource;

  constructor(private store: Store<any>,
              private dialog: MatDialog,
              private utilService: UtilService) {
    this.dataSource = new WharfT001lDataSource(this.store);
    this.store.dispatch(new wharfT001lManagePageActions.Init());
  }

  ngOnInit() {
    this.sort.sortChange
      .subscribe(sort => this.store.dispatch(new wharfT001lManagePageActions.SetSort({sort})));
  }

  create() {
    this.dataSource.wharfT001ls$.take(1)
      .switchMap(wharfT001ls => {
        const filteredT001ls = wharfT001ls.map(it => it.t001l);
        return PickT001lDialogComponent.open(this.dialog, {filteredT001ls, title: '新增码头', multi: true}).afterClosed();
      })
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(wharfT001ls => {
        wharfT001ls.forEach(wharfT001l => {
          this.store.dispatch(new wharfT001lManagePageActions.Save({wharfT001l}));
        });
      });
  }

  delete(pk: WharfT001l) {
    this.utilService.showConfirm().subscribe(() => {
      this.store.dispatch(new wharfT001lManagePageActions.Delete({pk}));
    });
  }
}

class WharfT001lDataSource extends DataSource<WharfT001l> {
  readonly wharfT001ls$: Observable<WharfT001l[]>;

  constructor(private store: Store<any>) {
    super();
    this.wharfT001ls$ = this.store.select(wharfT001lManagePageWharfT001ls);
  }

  connect(collectionViewer: CollectionViewer): Observable<WharfT001l[]> {
    return this.wharfT001ls$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
