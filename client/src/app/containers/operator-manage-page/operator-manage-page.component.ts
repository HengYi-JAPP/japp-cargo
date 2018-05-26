import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {HttpParams} from '@angular/common/http';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MatDialog, PageEvent} from '@angular/material';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {OperatorUpdateDialogComponent} from '../../components/operator-update-dialog/operator-update-dialog.component';
import {Operator} from '../../models/operator';
import {ApiService} from '../../services/api.service';
import {operatorManagePageActions} from '../../store/actions/index';
import {operatorManagePageCount, operatorManagePageOperators, operatorManagePagePageSize} from '../../store/config';

@Component({
  selector: 'jcargo-operator-manage-page',
  templateUrl: './operator-manage-page.component.html',
  styleUrls: ['./operator-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class OperatorManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-operator-manage-page') b2 = true;
  readonly displayedColumns = ['name', 'admin', 'defaultReceiveT001', 't001s', 'btns'];
  readonly dataSource: OperatorDataSource;
  readonly count$: Observable<number>;
  readonly pageSize$: Observable<number>;
  readonly searchForm: FormGroup;

  constructor(private store: Store<any>,
              private router: Router,
              private route: ActivatedRoute,
              private dialog: MatDialog,
              private fb: FormBuilder,
              private apiService: ApiService) {
    this.searchForm = fb.group({
      'q': '',
      'defaultReceiveT001s': null,
    });
    this.dataSource = new OperatorDataSource(this.store);
    this.count$ = this.store.select(operatorManagePageCount);
    this.pageSize$ = this.store.select(operatorManagePagePageSize);
    route.queryParams.subscribe(queryParams => {
      this.store.dispatch(new operatorManagePageActions.Init({queryParams}));
    });

    this.searchForm.valueChanges
      .debounceTime(300)
      .subscribe(() => {
        let params = new HttpParams().set('first', '0').set('pageSize', '10');
        (this.defaultReceiveT001s.value || []).forEach(it => {
          params = params.set('defaultReceiveBukrs', it.bukrs);
        });
        if (this.q.value) {
          params = params.set('q', this.q.value);
        }
        this.apiService.listOperator(params)
          .subscribe(res => {
            const {count, operators} = res;
            this.store.dispatch(new operatorManagePageActions.InitSuccess({first: 0, pageSize: 10, count, operators}));
            (operators || []).forEach(it => {
              this.store.dispatch(new operatorManagePageActions.FetchPermission({operatorId: it.id}));
            });
          });
      });
  }

  get q() {
    return this.searchForm.get('q');
  }

  get defaultReceiveT001s() {
    return this.searchForm.get('defaultReceiveT001s');
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
