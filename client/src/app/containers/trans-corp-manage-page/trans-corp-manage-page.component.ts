import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {MatDialog} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {filter, map, switchMap} from 'rxjs/operators';
import {isNullOrUndefined} from 'util';
import {TransCorpUpdateDialogComponent} from '../../components/trans-corp-update-dialog/trans-corp-update-dialog.component';
import {TransCorp} from '../../models/trans-corp';
import {ApiService} from '../../services/api.service';
import {UtilService} from '../../services/util.service';
import {transCorpManagePageActions} from '../../store/actions/index';
import {transCorpManagePageTransCorps} from '../../store/config';

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
              private apiService: ApiService,
              private utilService: UtilService) {
    this.dataSource = new TransCorpDataSource(this.store);
  }

  create() {
    this.update(new TransCorp());
  }

  update(transCorp: TransCorp) {
    TransCorpUpdateDialogComponent.open(this.dialog, {transCorp})
      .afterClosed()
      .pipe(
        filter(it => !isNullOrUndefined(it) && it),
        switchMap(it => this.apiService.saveTransCorp(it))
      )
      .subscribe(it => {
        const action1 = new transCorpManagePageActions.SaveSuccess({transCorp: it});
        this.store.dispatch(action1);
        const action2 = new transCorpManagePageActions.FetchT001s({transCorpId: it.id});
        this.store.dispatch(action2);
        this.utilService.showSuccess();
      });
  }

  delete(transCorp: TransCorp) {
    const {id} = transCorp;
    this.utilService.showConfirm()
      .pipe(
        switchMap(() => this.apiService.deleteTransCorp(id)),
        map(() => new transCorpManagePageActions.DeleteSuccess({id})),
      )
      .subscribe(it => {
        this.store.dispatch(it);
        this.utilService.showSuccess();
      });
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
