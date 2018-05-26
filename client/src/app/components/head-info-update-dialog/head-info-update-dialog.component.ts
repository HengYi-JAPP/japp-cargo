import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {HeadInfo} from '../../models/head-info';
import {T001} from '../../models/t001';
import {ApiService} from '../../services/api.service';
import {coreIsMobile} from '../../store/core';

@Component({
  selector: 'jcargo-head-info-update-dialog',
  templateUrl: './head-info-update-dialog.component.html',
  styleUrls: ['./head-info-update-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeadInfoUpdateDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-head-info-update-dialog') b2 = true;
  readonly isMobile$: Observable<boolean>;
  readonly headInfoForm: FormGroup;
  readonly t001s$: Observable<T001[]>;
  readonly dialogTitle: string;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private apiService: ApiService,
              private dialogRef: MatDialogRef<HeadInfoUpdateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { headInfo: HeadInfo }) {
    this.isMobile$ = this.store.select(coreIsMobile);
    const {headInfo} = data;
    this.dialogTitle = headInfo.id ? '更新' : '新增';
    this.headInfoForm = fb.group({
      id: headInfo.id,
      allT001s: headInfo.allT001s,
      t001s: headInfo.t001s,
      name: [headInfo.name, [Validators.required]],
    });
    this.t001s$ = this.apiService.listReceiveT001()
      .map(receiveT001s => receiveT001s.map(it => it.t001));
  }

  get allT001s() {
    return this.headInfoForm.get('allT001s');
  }

  get t001s() {
    return this.headInfoForm.get('t001s');
  }

  get name() {
    return this.headInfoForm.get('name');
  }

  static open(dialog: MatDialog, data: { headInfo: HeadInfo }): MatDialogRef<HeadInfoUpdateDialogComponent> {
    return dialog.open(HeadInfoUpdateDialogComponent, {panelClass: 'my-dialog', data});
  }

  submit() {
    this.dialogRef.close(this.headInfoForm.value);
  }

  compareWithT001(o1: T001, o2: T001): boolean {
    return o1 && o2 && o1.bukrs === o2.bukrs;
  }
}
