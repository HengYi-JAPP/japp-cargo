import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {map, startWith, switchMap} from 'rxjs/operators';
import {baseApiUrl} from '../../../environments/environment';
import {ApiService} from '../../services/api.service';
import {ShowError} from '../../store/actions/core';
import {corePermission} from '../../store/core';
import {T001} from '../../models/t001';

@Component({
  selector: 'jcargo-daily-detail-export-dialog',
  templateUrl: './daily-detail-export-dialog.component.html',
  styleUrls: ['./daily-detail-export-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DailyDetailExportDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-daily-detail-export-dialog') b2 = true;
  readonly minStartDate = moment([2018, 0, 1]).toDate();
  readonly maxStartDate$: Observable<Date>;
  readonly minEndDate$: Observable<Date>;
  readonly maxEndDate = new Date();
  readonly t001s$: Observable<T001[]>;
  readonly form: FormGroup;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private apiService: ApiService,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<DailyDetailExportDialogComponent>,
              @Inject(MAT_DIALOG_DATA)  data: { startDate?: Date, endDate?: Date }) {
    this.t001s$ = this.store.select(corePermission).pipe(
      switchMap(permission =>
        permission.allT001s ? this.apiService.listReceiveT001().map(receiveT001s => receiveT001s.map(it => it.t001)) : of(permission.t001s)
      )
    );
    const {startDate, endDate} = data;
    this.form = this.fb.group({
      'startDate': [startDate || moment().startOf('month').toDate(), Validators.required],
      'endDate': [endDate || new Date(), Validators.required],
      'allT001s': true,
      't001s': null,
    });
    this.maxStartDate$ = this.endDate.valueChanges.pipe(
      startWith(null),
      map(it => it || this.maxEndDate)
    );
  }

  get startDate() {
    return this.form.get('startDate');
  }

  get endDate() {
    return this.form.get('endDate');
  }

  get allT001s() {
    return this.form.get('allT001s');
  }

  get t001s() {
    return this.form.get('t001s');
  }

  static open(dialog: MatDialog, data?: { startDate?: Date, endDate?: Date }): MatDialogRef<DailyDetailExportDialogComponent> {
    data = data || {};
    return dialog.open(DailyDetailExportDialogComponent, {disableClose: true, panelClass: 'my-dialog', data});
  }

  submit() {
    this.apiService.dailyDetailExportToken(this.form.value)
      .subscribe(
        token => {
          this.dialogRef.close(this.form.value);
          window.location.href = `${baseApiUrl}/opens/reports/dailyDetailExport?token=${token}`;
        },
        error => this.store.dispatch(new ShowError(error))
      );
  }

  compareWithT001(o1: T001, o2: T001): boolean {
    return o1 && o2 && o1.bukrs === o2.bukrs;
  }
}
