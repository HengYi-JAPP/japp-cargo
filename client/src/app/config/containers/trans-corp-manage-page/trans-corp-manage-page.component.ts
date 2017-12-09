import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {MatDialog} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {UtilService} from '../../../core/services/util.service';
import {TransCorp} from '../../../shared/models/trans-corp';
import {TransCorpUpdateDialogComponent} from '../../components/trans-corp-update-dialog/trans-corp-update-dialog.component';
import {transCorpManagePageActions, transCorpManagePageTransCorps} from '../../store';

@Component({
  selector: 'jcargo-trans-corp-manage-page',
  templateUrl: './trans-corp-manage-page.component.html',
  styleUrls: ['./trans-corp-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TransCorpManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-trans-corp-manage-page') b2 = true;
  readonly displayedColumns = ['name', 't001s', 'btns'];
  readonly dataSource: TransCorpDataSource;

  constructor(private store: Store<any>,
              private dialog: MatDialog,
              private utilService: UtilService) {
    this.dataSource = new TransCorpDataSource(this.store);
    this.store.dispatch(new transCorpManagePageActions.Init());
  }

  create() {
    this.update(new TransCorp());
  }

  update(transCorp: TransCorp) {
    TransCorpUpdateDialogComponent.open(this.dialog, {transCorp})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => this.store.dispatch(new transCorpManagePageActions.Save({transCorp: res})));
  }

  delete(transCorp: TransCorp) {
    this.utilService.showConfirm()
      .subscribe(() => this.store.dispatch(new transCorpManagePageActions.Delete({id: transCorp.id})));
  }
}

class TransCorpDataSource extends DataSource<TransCorp> {
  readonly transCorps$: Observable<TransCorp[]>;

  constructor(private store: Store<any>) {
    super();
    this.transCorps$ = this.store.select(transCorpManagePageTransCorps);
  }

  connect(collectionViewer: CollectionViewer): Observable<TransCorp[]> {
    return this.transCorps$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
