import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import {Observable} from 'rxjs/Observable';
import {forkJoin} from 'rxjs/observable/forkJoin';
import {of} from 'rxjs/observable/of';
import {isString} from 'util';
import {ApiService} from '../../../core/services/api.service';
import {coreIsMobile} from '../../../core/store';
import {Kna1} from '../../../shared/models/kna1';
import {Lfa1} from '../../../shared/models/lfa1';
import {ReceiveT001} from '../../../shared/models/receive-t001';
import {T001} from '../../../shared/models/t001';
import {delPreZeroFn} from '../../../shared/services/del-pre-zero.pipe';

@Component({
  selector: 'jcargo-receive-t001-update-dialog',
  templateUrl: './receive-t001-update-dialog.component.html',
  styleUrls: ['./receive-t001-update-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReceiveT001UpdateDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-receive-t001-update-dialog') b2 = true;
  readonly isMobile$: Observable<boolean>;
  readonly receiveT001Form: FormGroup;
  readonly t001s$: Observable<T001[]>;
  readonly dialogTitle: string;
  readonly kna1s$: Observable<Kna1[]>;
  readonly lfa1s$: Observable<Lfa1[]>;

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private apiService: ApiService,
              private dialogRef: MatDialogRef<ReceiveT001UpdateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.isMobile$ = this.store.select(coreIsMobile);
    const {receiveT001} = data;
    this.dialogTitle = receiveT001.bukrs ? '更新' : '新增';
    this.receiveT001Form = fb.group({
      t001: [receiveT001.t001, [Validators.required]],
      kna1: [receiveT001.kna1],
      lfa1: [receiveT001.lfa1],
    });

    this.t001s$ = forkJoin(
      this.apiService.listT001(),
      this.apiService.listReceiveT001(),
      (t001s, receiveT001s) => {
        const filtered = t001s.filter(t001 => {
          const find = receiveT001s.find(it => it.bukrs === t001.bukrs);
          return !find;
        });
        return filtered.concat(receiveT001s.map(it => it.t001));
      }
    );

    this.lfa1s$ = this.lfa1.valueChanges
      .startWith(null)
      .debounceTime(300)
      .distinctUntilChanged()
      .switchMap(q => q ? this.apiService.autocompleteLfa1(q) : of([]));

    this.kna1s$ = this.kna1.valueChanges
      .startWith(null)
      .debounceTime(300)
      .distinctUntilChanged()
      .switchMap(q => q ? this.apiService.autocompleteKna1(q) : of([]));
  }

  get t001() {
    return this.receiveT001Form.get('t001');
  }

  get kna1() {
    return this.receiveT001Form.get('kna1');
  }

  get lfa1() {
    return this.receiveT001Form.get('lfa1');
  }

  static open(dialog: MatDialog, data: { receiveT001: ReceiveT001 }): MatDialogRef<ReceiveT001UpdateDialogComponent> {
    return dialog.open(ReceiveT001UpdateDialogComponent, {panelClass: 'my-dialog', data});
  }

  submit() {
    const result = {...this.receiveT001Form.value};
    if (isString(result['kna1'])) {
      delete result['kna1'];
    }
    if (isString(result['lfa1'])) {
      delete result['lfa1'];
    }
    this.dialogRef.close(result);
  }

  compareWithT001(o1: T001, o2: T001): boolean {
    return o1 && o2 && o1.bukrs === o2.bukrs;
  }

  displayLfa1Fn(lfa1: Lfa1): string {
    return lfa1 ? delPreZeroFn(lfa1.lifnr) : '';
  }

  displayKna1Fn(kna1: Kna1): string {
    return kna1 ? delPreZeroFn(kna1.kunnr) : '';
  }
}
