import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {Operator} from '../../models/operator';
import {OperatorPermission} from '../../models/operator-permission';
import {T001} from '../../models/t001';
import {ApiService} from '../../services/api.service';
import {coreIsMobile} from '../../store/core';

@Component({
  selector: 'jcargo-operator-update-dialog',
  templateUrl: './operator-update-dialog.component.html',
  styleUrls: ['./operator-update-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class OperatorUpdateDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-operator-update-dialog') b2 = true;
  readonly isMobile$: Observable<boolean>;
  readonly dialogTitle: string;
  readonly permissionForm: FormGroup;
  readonly t001s$: Observable<T001[]>;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private apiService: ApiService,
              private dialogRef: MatDialogRef<OperatorUpdateDialogComponent>,
              @Inject(MAT_DIALOG_DATA)  data: { operator: Operator, permission: OperatorPermission }) {
    this.isMobile$ = this.store.select(coreIsMobile);
    const {operator, permission} = data;
    this.dialogTitle = operator.name;
    this.permissionForm = fb.group({
      'admin': [operator.admin, Validators.required],
      'allT001s': [permission.allT001s, Validators.required],
      'defaultReceiveT001': permission.defaultReceiveT001,
      't001s': permission.t001s,
    });
    this.allT001s.valueChanges
      .startWith(null)
      .subscribe(it => {
        if (it) {
          this.t001s.disable();
        } else {
          this.t001s.enable();
        }
      });
    this.t001s$ = this.apiService.listReceiveT001()
      .map(receiveT001s => receiveT001s.map(it => it.t001));
  }

  get defaultReceiveT001() {
    return this.permissionForm.get('defaultReceiveT001');
  }

  get allT001s() {
    return this.permissionForm.get('allT001s');
  }

  get admin() {
    return this.permissionForm.get('admin');
  }

  get t001s() {
    return this.permissionForm.get('t001s');
  }

  static open(dialog: MatDialog, data: { operator: Operator, permission?: OperatorPermission }): MatDialogRef<OperatorUpdateDialogComponent> {
    data.permission = data.permission || new OperatorPermission();
    return dialog.open(OperatorUpdateDialogComponent, {panelClass: 'my-dialog', data});
  }

  submit() {
    this.dialogRef.close(this.permissionForm.value);
  }

  compareWithT001(o1: T001, o2: T001): boolean {
    return o1 && o2 && o1.bukrs === o2.bukrs;
  }
}
