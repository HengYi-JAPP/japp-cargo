import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {T001} from '../../models/t001';
import {TransCorp} from '../../models/trans-corp';
import {coreIsMobile} from '../../store/core';
import {ApiService} from '../../services/api.service';

@Component({
  selector: 'jcargo-trans-corp-update-dialog',
  templateUrl: './trans-corp-update-dialog.component.html',
  styleUrls: ['./trans-corp-update-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TransCorpUpdateDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-trans-corp-update-dialog') b2 = true;
  readonly isMobile$: Observable<boolean>;
  readonly transCorpForm: FormGroup;
  readonly t001s$: Observable<T001[]>;
  readonly dialogTitle: string;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private apiService: ApiService,
              private dialogRef: MatDialogRef<TransCorpUpdateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { transCorp: TransCorp }) {
    this.isMobile$ = this.store.select(coreIsMobile);
    const {transCorp} = data;
    this.dialogTitle = transCorp.id ? '更新' : '新增';
    this.transCorpForm = fb.group({
      id: transCorp.id,
      t001s: [transCorp.t001s, [Validators.required]],
      name: [transCorp.name, [Validators.required]],
    });
    this.t001s$ = this.apiService.listReceiveT001()
      .map(receiveT001s => receiveT001s.map(it => it.t001));
  }

  get t001s() {
    return this.transCorpForm.get('t001s');
  }

  get name() {
    return this.transCorpForm.get('name');
  }

  static open(dialog: MatDialog, data: { transCorp: TransCorp }): MatDialogRef<TransCorpUpdateDialogComponent> {
    return dialog.open(TransCorpUpdateDialogComponent, {panelClass: 'my-dialog', data});
  }

  submit() {
    this.dialogRef.close(this.transCorpForm.value);
  }

  compareWithT001(o1: T001, o2: T001): boolean {
    return o1 && o2 && o1.bukrs === o2.bukrs;
  }
}
