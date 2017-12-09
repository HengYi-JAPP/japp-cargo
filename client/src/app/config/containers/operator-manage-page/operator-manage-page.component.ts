import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {MatDialog, PageEvent} from '@angular/material';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {UtilService} from '../../../core/services/util.service';
import {Operator} from '../../../shared/models/operator';
import {OperatorUpdateDialogComponent} from '../../components/operator-update-dialog/operator-update-dialog.component';
import {
  operatorManagePageActions,
  operatorManagePageCount,
  operatorManagePageOperators,
  operatorManagePagePageSize
} from '../../store';

@Component({
  selector: 'jcargo-operator-manage-page',
  templateUrl: './operator-manage-page.component.html',
  styleUrls: ['./operator-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class OperatorManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-operator-manage-page') b2 = true;
  readonly displayedColumns = ['name', 'defaultReceiveT001', 'allT001s', 't001s', 'btns'];
  readonly dataSource: OperatorDataSource;
  readonly count$: Observable<number>;
  readonly pageSize$: Observable<number>;

  constructor(private store: Store<any>,
              private router: Router,
              private route: ActivatedRoute,
              private dialog: MatDialog,
              private utilService: UtilService) {
    this.dataSource = new OperatorDataSource(this.store);
    this.count$ = this.store.select(operatorManagePageCount);
    this.pageSize$ = this.store.select(operatorManagePagePageSize);
    route.queryParams.subscribe(queryParams => {
      this.store.dispatch(new operatorManagePageActions.Init({queryParams}));
    });
  }

  update(operator: Operator) {
    OperatorUpdateDialogComponent.open(this.dialog, {operator, permission: operator.permission})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(permission => {
        this.store.dispatch(new operatorManagePageActions.SavePermission({operatorId: operator.id, permission}));
      });
  }

  page(ev: PageEvent) {
    const first = ev.pageSize * ev.pageIndex;
    const queryParams = {first, pageSize: ev.pageSize};
    this.store.dispatch(new operatorManagePageActions.Init({queryParams}));
  }
}

class OperatorDataSource extends DataSource<Operator> {
  readonly Operators$: Observable<Operator[]>;

  constructor(private store: Store<any>) {
    super();
    this.Operators$ = this.store.select(operatorManagePageOperators);
  }

  connect(collectionViewer: CollectionViewer): Observable<Operator[]> {
    return this.Operators$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
