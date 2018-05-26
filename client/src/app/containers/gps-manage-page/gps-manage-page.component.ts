import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {GpsMapDialogComponent} from '../../components/gps-map-dialog/gps-map-dialog.component';
import {Ylips} from '../../models/ylips';
import {gpsManagePageYlipses} from '../../store/gps';
import {gpsManagePageActions} from '../../store/actions/index';

@Component({
  selector: 'jcargo-gps-manage-page',
  templateUrl: './gps-manage-page.component.html',
  styleUrls: ['./gps-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GpsManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-gps-manage-page') b2 = true;
  readonly maxSearchDate = new Date();
  readonly displayedColumns = ['date', 'carNo', 'arktx', 'lfimg', 'meins', 'btns'];
  readonly dataSource: YlipsDataSource;
  readonly searchForm: FormGroup;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private dialog: MatDialog) {
    this.searchForm = fb.group({
      'searchDate': [new Date(), Validators.required],
      'carNoQ': '',
    });
    this.dataSource = new YlipsDataSource(this.store);
    this.searchDate.valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .map(date => new gpsManagePageActions.Init({date}))
      .subscribe(it => this.store.dispatch(it));
    this.carNoQ.valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .map(carNoQ => new gpsManagePageActions.SetCarNoQ({carNoQ}))
      .subscribe(it => this.store.dispatch(it));
    this.store.dispatch(new gpsManagePageActions.Init({}));
  }

  get carNoQ() {
    return this.searchForm.get('carNoQ');
  }

  get searchDate() {
    return this.searchForm.get('searchDate');
  }

  gpsMap(ylips: Ylips) {
    GpsMapDialogComponent.open(this.dialog, {ylips});
  }
}

class YlipsDataSource extends DataSource<Ylips> {
  private readonly ylipses$: Observable<Ylips[]>;

  constructor(private store: Store<any>) {
    super();
    this.ylipses$ = this.store.select(gpsManagePageYlipses);
  }

  connect(collectionViewer: CollectionViewer): Observable<Ylips[]> {
    return this.ylipses$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
