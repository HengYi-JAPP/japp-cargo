import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material';
import {Router} from '@angular/router';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {merge} from 'rxjs/observable/merge';
import {Subscription} from 'rxjs/Subscription';
import {DailyDetailExportDialogComponent} from '../../components/daily-detail-export-dialog/daily-detail-export-dialog.component';
import {SendInfo} from '../../models/send-info';
import {calcDate, minDate, UtilService} from '../../services/util.service';
import {coreActions, dailyDetailPageActions} from '../../store/actions/index';
import {dailyDetailPageEndDate, dailyDetailPageSendInfos, dailyDetailPageStartDate} from '../../store/report';

@Component({
  selector: 'jcargo-daily-detail-page',
  templateUrl: './daily-detail-page.component.html',
  styleUrls: ['./daily-detail-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DailyDetailPageComponent implements OnDestroy {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-daily-detail-page') b2 = true;
  readonly minDate = minDate;
  readonly maxDate = new Date();
  readonly displayedColumns = ['receiveDate', 'carNo', 'sendLfimg', 'receiveLfimg', 'diffLfimg'];
  readonly dataSource: SendInfoDataSource;
  readonly searchForm: FormGroup;
  private readonly _subscriptions: Subscription[] = [];

  constructor(private store: Store<any>,
              private router: Router,
              private dialog: MatDialog,
              private fb: FormBuilder,
              private utilService: UtilService) {
    this.store.dispatch(new coreActions.FetchAuth());
    this.dataSource = new SendInfoDataSource(this.store);
    this.searchForm = fb.group({
      'startDate': [new Date(), Validators.required],
      'endDate': [new Date(), Validators.required],
      'carNoQ': '',
    });
    this._subscriptions.push(
      this.store.select(dailyDetailPageStartDate)
        .map(it => calcDate(it))
        .subscribe(it => this.startDate.setValue(it)),

      this.store.select(dailyDetailPageEndDate)
        .map(it => calcDate(it))
        .subscribe(it => this.endDate.setValue(it)),

      merge(this.startDate.valueChanges, this.endDate.valueChanges)
        .subscribe(() => {
          let startDate = moment(this.startDate.value);
          let endDate = moment(this.endDate.value);
          if (startDate.isAfter(endDate) || endDate.isBefore(startDate)) {
            startDate = moment.min(startDate, endDate);
            endDate = moment.max(startDate, endDate);
            this.startDate.setValue(startDate.toDate());
            this.endDate.setValue(endDate.toDate());
          }
        }),

      this.carNoQ.valueChanges
        .debounceTime(300)
        .distinctUntilChanged()
        .map(carNoQ => new dailyDetailPageActions.SetCarNoQ({carNoQ}))
        .subscribe(it => this.store.dispatch(it))
    );
  }

  get startDate() {
    return this.searchForm.get('startDate');
  }

  get endDate() {
    return this.searchForm.get('endDate');
  }

  get carNoQ() {
    return this.searchForm.get('carNoQ');
  }

  search() {
    const queryParams = {
      startDate: moment(this.startDate.value).format('YYYY-MM-DD'),
      endDate: moment(this.endDate.value).format('YYYY-MM-DD'),
    };
    this.router.navigate(['reports', 'dailyDetail'], {queryParams});
  }

  export() {
    DailyDetailExportDialogComponent.open(this.dialog, this.searchForm.value);
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(it => it.unsubscribe());
  }
}

class SendInfoDataSource extends DataSource<SendInfo> {
  readonly sendInfos$: Observable<SendInfo[]>;

  constructor(private store: Store<any>) {
    super();
    this.sendInfos$ = this.store.select(dailyDetailPageSendInfos);
  }

  connect(collectionViewer: CollectionViewer): Observable<SendInfo[]> {
    return this.sendInfos$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
