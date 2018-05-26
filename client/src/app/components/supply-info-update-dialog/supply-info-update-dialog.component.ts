import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {SupplyInfo} from '../../models/supply-info';
import {T001} from '../../models/t001';
import {ApiService} from '../../services/api.service';
import {coreIsMobile} from '../../store/core';

@Component({
  selector: 'jcargo-supply-info-update-dialog',
  templateUrl: './supply-info-update-dialog.component.html',
  styleUrls: ['./supply-info-update-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SupplyInfoUpdateDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-supply-info-update-dialog') b2 = true;
  readonly isMobile$: Observable<boolean>;
  readonly supplyInfoForm: FormGroup;
  readonly t001s$: Observable<T001[]>;
  readonly dialogTitle: string;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private apiService: ApiService,
              private dialogRef: MatDialogRef<SupplyInfoUpdateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { supplyInfo: SupplyInfo }) {
    this.isMobile$ = this.store.select(coreIsMobile);
    const {supplyInfo} = data;
    this.dialogTitle = supplyInfo.id ? '更新' : '新增';
    this.supplyInfoForm = fb.group({
      id: supplyInfo.id,
      t001s: [supplyInfo.t001s, [Validators.required]],
      name: [supplyInfo.name, [Validators.required]],
    });
    this.t001s$ = this.apiService.listReceiveT001()
      .map(receiveT001s => receiveT001s.map(it => it.t001));
  }

  get t001s() {
    return this.supplyInfoForm.get('t001s');
  }

  get name() {
    return this.supplyInfoForm.get('name');
  }

  static open(dialog: MatDialog, data: { supplyInfo: SupplyInfo }): MatDialogRef<SupplyInfoUpdateDialogComponent> {
    return dialog.open(SupplyInfoUpdateDialogComponent, {panelClass: 'my-dialog', data});
  }

  submit() {
    this.dialogRef.close(this.supplyInfoForm.value);
  }

  compareWithT001(o1: T001, o2: T001): boolean {
    return o1 && o2 && o1.bukrs === o2.bukrs;
  }
}
