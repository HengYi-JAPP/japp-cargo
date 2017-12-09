import {DecimalPipe} from '@angular/common';
import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatAutocompleteSelectedEvent, MatDialog, MatDialogRef} from '@angular/material';
import {Store} from '@ngrx/store';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/take';
import {Observable} from 'rxjs/Observable';
import {merge} from 'rxjs/observable/merge';
import {of} from 'rxjs/observable/of';
import {Subject} from 'rxjs/Subject';
import {isNullOrUndefined} from 'util';
import {ApiService} from '../../../core/services/api.service';
import {UtilService} from '../../../core/services/util.service';
import {corePermission} from '../../../core/store';
import {CarNoInfoLog} from '../../../shared/models/car-no-info-log';
import {Lfa1} from '../../../shared/models/lfa1';
import {MegSendInfo} from '../../../shared/models/meg-send-info';
import {PtaSendInfo} from '../../../shared/models/pta-send-info';
import {SapReceiveInfo} from '../../../shared/models/sap-receive-info';
import {T001l} from '../../../shared/models/t001l';
import {TransCorp} from '../../../shared/models/trans-corp';
import {Ylips} from '../../../shared/models/ylips';

const NINGBO_LFA1 = Lfa1.assign({lifnr: '0000011002', 'name1': '宁波逸盛'});

export enum ModeType {
  PTA, MEG
}

@Component({
  selector: 'jcargo-send-update-dialog',
  templateUrl: './send-update-dialog.component.html',
  styleUrls: ['./send-update-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [DecimalPipe],
})
export class SendUpdateDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-send-update-dialog') b2 = true;
  readonly PACK_TYPES = ['1', '2', '5', '4', '3'];
  readonly TRANS_TYPES = ['1', '2', '3'];
  readonly sapReceiveInfosChange$ = new Subject();
  readonly dialogTitle: string;
  readonly modeType: ModeType;
  readonly ylips: Ylips;
  readonly sendInfoForm: FormGroup;
  readonly carNoInfoLogs$: Observable<CarNoInfoLog[]>;
  readonly lfa1s$: Observable<Lfa1[]>;
  readonly transCorps$: Observable<TransCorp[]>;
  readonly wharfs$: Observable<T001l[]>;
  readonly receiveT001ls$: Observable<T001l[]>;

  constructor(private store: Store<any>,
              private decimalPipe: DecimalPipe,
              private fb: FormBuilder,
              private utilService: UtilService,
              private apiService: ApiService,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<SendUpdateDialogComponent>,
              @Inject(MAT_DIALOG_DATA)  data: { sendInfo: MegSendInfo | PtaSendInfo, modeType: ModeType, ylips: Ylips }) {
    this.modeType = data.modeType;
    this.ylips = data.ylips;
    const {sendInfo} = data;
    this.sendInfoForm = this.fb.group({
      'id': sendInfo.id,
      'packType': [sendInfo['packType'] || '1', Validators.required],
      'transType': [sendInfo['transType'] || '1', [Validators.required]],
      'lfa1': [sendInfo.lfa1],
      'sendDate': [moment(sendInfo.sendDate).toDate(), [Validators.required]],
      'receiveDate': [moment(sendInfo.receiveInfo.receiveDate).toDate(), [Validators.required]],
      'batchNo': [sendInfo['batchNo']],
      'carNo': [sendInfo.carNo, [Validators.required]],
      'carDriver': sendInfo.carDriver,
      'sendLfimg1': [sendInfo.lfimg1, [Validators.required, Validators.min(0)]],
      'sendLfimg2': [sendInfo.lfimg2, [Validators.required, Validators.min(0)]],
      'sendLfimg': [sendInfo.lfimg],
      'receiveLfimg1': [sendInfo.receiveInfo.lfimg1, [Validators.required, Validators.min(0)]],
      'receiveLfimg2': [sendInfo.receiveInfo.lfimg2, [Validators.required, Validators.min(0)]],
      'receiveLfimg': [sendInfo.receiveInfo.lfimg],
      'diffLfimg1': [sendInfo.receiveInfo.diffLfimg1, [Validators.required]],
      'diffLfimg2': [sendInfo.receiveInfo.diffLfimg2, [Validators.required]],
      'finalLfimg': null,
      'sendNote': sendInfo.note,
      'receiveNote': sendInfo.receiveInfo.note,
      'pickPoundNo': sendInfo.receiveInfo['pickPoundNo'],
      'wharf': [sendInfo['wharf'], [Validators.required]],
      'receiveT001l': [sendInfo.receiveInfo.t001l, [Validators.required]],
      'transCorp': [sendInfo.transCorp, [Validators.required]],
      'sapReceiveInfos': this.fb.array([]),
      'packNo': [sendInfo['packNo'], [Validators.required, Validators.min(1)]],
      'littleLfimgPerPack': [sendInfo.receiveInfo['littleLfimgPerPack'], [Validators.required]],
      'sendLfimgPerPack': null,
      'receiveLfimgPerPack': null,
    });
    this.setYlips();
    this.setSapReceiveInfos(sendInfo.receiveInfo.sapReceiveInfos);
    this.transCorps$ = this.apiService.listTransCorp();
    this.wharfs$ = this.store.select(corePermission)
      .switchMap(it => this.apiService.listWharfT001l(it.defaultReceiveT001.bukrs))
      .map(res => res.map(it => it.t001l));
    this.receiveT001ls$ = this.store.select(corePermission)
      .switchMap(it => this.apiService.listReceiveT001l(it.defaultReceiveT001.bukrs))
      .map(res => res.map(it => it.t001l));
    this.carNoInfoLogs$ = this.carNo.valueChanges
      .switchMap(q => {
        return q ? this.apiService.autocompleteCarNoInfoLog(q) : of([]);
      });
    this.lfa1s$ = this.lfa1.valueChanges
      .startWith(null)
      .debounceTime(300)
      .distinctUntilChanged()
      .switchMap(q => {
        return q ? this.apiService.autocompleteLfa1(q) : of([]);
      });

    this.packType.valueChanges
      .startWith(null)
      .subscribe(() => {
        const [packType] = this.packType.value;
        if (packType === '2' || packType === '3') {
          this.packNo.enable();
          this.littleLfimgPerPack.enable();
        } else {
          this.packNo.disable();
          this.littleLfimgPerPack.disable();
        }
      });

    this.sendLfimg.disable();
    merge(this.packType.valueChanges, this.sendLfimg2.valueChanges, this.sendLfimg1.valueChanges)
      .startWith(null)
      .subscribe(() => {
        const [lfimg2, lfimg1] = [this.sendLfimg2.value, this.sendLfimg1.value];
        if (!isNullOrUndefined(lfimg2) && !isNullOrUndefined(lfimg1)) {
          this.sendLfimg.setValue(lfimg2 - lfimg1);
        }
      });

    this.receiveLfimg.disable();
    merge(this.receiveLfimg2.valueChanges, this.receiveLfimg1.valueChanges)
      .startWith(null)
      .subscribe(() => {
        const [lfimg2, lfimg1] = [this.receiveLfimg2.value, this.receiveLfimg1.value];
        if (!isNullOrUndefined(lfimg2) && !isNullOrUndefined(lfimg1)) {
          this.receiveLfimg.setValue(lfimg2 - lfimg1);
        }
      });

    this.finalLfimg.disable();
    merge(this.diffLfimg2.valueChanges, this.diffLfimg1.valueChanges, this.receiveLfimg.valueChanges)
      .startWith(null)
      .subscribe(() => {
        const [diffLfimg2, diffLfimg1, receiveLfimg] = [this.diffLfimg2.value, this.diffLfimg1.value, this.receiveLfimg.value];
        if (!isNullOrUndefined(diffLfimg2) && !isNullOrUndefined(diffLfimg1) && !isNullOrUndefined(receiveLfimg)) {
          this.finalLfimg.setValue(receiveLfimg + diffLfimg1 + diffLfimg2);
        }
      });

    this.sendLfimgPerPack.disable();
    merge(this.sendLfimg.valueChanges, this.packNo.valueChanges)
      .startWith(null)
      .subscribe(() => {
        const [lfimg, packNo] = [this.sendLfimg.value, this.packNo.value];
        if (isNullOrUndefined(lfimg) || isNullOrUndefined(packNo) || packNo === 0) {
          this.sendLfimgPerPack.setValue(null);
        } else {
          const value = lfimg / packNo;
          const s = decimalPipe.transform(value, '0.2-2');
          this.sendLfimgPerPack.setValue(parseFloat(s));
        }
      });

    this.receiveLfimgPerPack.disable();
    merge(this.receiveLfimg.valueChanges, this.packNo.valueChanges)
      .startWith(null)
      .subscribe(() => {
        const [lfimg, packNo] = [this.receiveLfimg.value, this.packNo.value];
        if (isNullOrUndefined(lfimg) || isNullOrUndefined(packNo) || packNo === 0) {
          this.receiveLfimgPerPack.setValue(null);
        } else {
          const value = lfimg / packNo;
          const s = decimalPipe.transform(value, '0.2-2');
          this.receiveLfimgPerPack.setValue(parseFloat(s));
        }
      });

    switch (this.modeType) {
      case ModeType.PTA: {
        this.pickPoundNo.disable();
        this.wharf.disable();
        this.dialogTitle = 'PTA收货—' + (sendInfo.id ? '修改' : '新增');
        break;
      }
      case ModeType.MEG: {
        this.packType.disable();
        this.transType.disable();
        this.batchNo.disable();
        this.dialogTitle = 'MEG收货—' + (sendInfo.id ? '修改' : '新增');
        break;
      }
    }
  }

  get littleLfimgPerPack() {
    return this.sendInfoForm.get('littleLfimgPerPack');
  }

  get receiveLfimgPerPack() {
    return this.sendInfoForm.get('receiveLfimgPerPack');
  }

  get sendLfimgPerPack() {
    return this.sendInfoForm.get('sendLfimgPerPack');
  }

  get finalLfimg() {
    return this.sendInfoForm.get('finalLfimg');
  }

  get batchNo() {
    return this.sendInfoForm.get('batchNo');
  }

  get diffLfimg2() {
    return this.sendInfoForm.get('diffLfimg2');
  }

  get diffLfimg1() {
    return this.sendInfoForm.get('diffLfimg1');
  }

  get packNo() {
    return this.sendInfoForm.get('packNo');
  }

  get receiveT001l() {
    return this.sendInfoForm.get('receiveT001l');
  }

  get pickPoundNo() {
    return this.sendInfoForm.get('pickPoundNo');
  }

  get receiveLfimg() {
    return this.sendInfoForm.get('receiveLfimg');
  }

  get receiveLfimg1() {
    return this.sendInfoForm.get('receiveLfimg1');
  }

  get receiveLfimg2() {
    return this.sendInfoForm.get('receiveLfimg2');
  }

  get sendLfimg1() {
    return this.sendInfoForm.get('sendLfimg1');
  }

  get sendLfimg() {
    return this.sendInfoForm.get('sendLfimg');
  }

  get sendLfimg2() {
    return this.sendInfoForm.get('sendLfimg2');
  }

  get receiveNote() {
    return this.sendInfoForm.get('receiveNote');
  }

  get sendNote() {
    return this.sendInfoForm.get('sendNote');
  }

  get receiveDate() {
    return this.sendInfoForm.get('receiveDate');
  }

  get wharf() {
    return this.sendInfoForm.get('wharf');
  }

  get transCorp() {
    return this.sendInfoForm.get('transCorp');
  }

  get lfa1() {
    return this.sendInfoForm.get('lfa1');
  }

  get sendDate() {
    return this.sendInfoForm.get('sendDate');
  }

  get carNo() {
    return this.sendInfoForm.get('carNo');
  }

  get packType() {
    return this.sendInfoForm.get('packType');
  }

  get carDriver() {
    return this.sendInfoForm.get('carDriver');
  }

  get transType() {
    return this.sendInfoForm.get('transType');
  }

  get receiveInfo() {
    return this.sendInfoForm.get('receiveInfo');
  }

  get sapReceiveInfos(): FormArray {
    return this.sendInfoForm.get('sapReceiveInfos') as FormArray;
  }

  static updatePta(dialog: MatDialog, data: { ylips?: Ylips, sendInfo?: PtaSendInfo }): MatDialogRef<SendUpdateDialogComponent> {
    let {ylips, sendInfo} = data;
    sendInfo = sendInfo || new PtaSendInfo();
    ylips = ylips || sendInfo.ylips;
    return dialog.open(SendUpdateDialogComponent, {
      disableClose: true,
      panelClass: 'my-dialog',
      data: {modeType: ModeType.PTA, ylips, sendInfo}
    });
  }

  static updateMeg(dialog: MatDialog, data: { sendInfo?: MegSendInfo }): MatDialogRef<SendUpdateDialogComponent> {
    let {sendInfo} = data;
    sendInfo = sendInfo || new MegSendInfo();
    return dialog.open(SendUpdateDialogComponent, {
      disableClose: true,
      panelClass: 'my-dialog',
      data: {modeType: ModeType.MEG, sendInfo}
    });
  }

  submit() {
    const result = this.sendInfoForm.getRawValue();
    result.ylips = this.ylips;
    this.dialogRef.close(result);
  }

  carNoSelected(ev: MatAutocompleteSelectedEvent) {
    this.apiService.getCarNoInfoLog(ev.option.value)
      .filter(it => !isNullOrUndefined(it) && !!it)
      .subscribe(carNoInfoLog => {
        this.carDriver.setValue(carNoInfoLog.carDriver);
        this.transCorp.setValue(carNoInfoLog.transCorp);
      });
  }

  addSapReceiveInfo() {
    this.sapReceiveInfos.push(this.fb.group({
      'sapNo': [null, [Validators.required]],
      'amount': [this.finalLfimg.value, [Validators.required, Validators.min(0)]],
      // 't001l': [null, [Validators.required]],
      't001l': null,
    }));
    this.sapReceiveInfosChange$.next();
  }

  removeSapReceiveInfo(i: number) {
    this.utilService.showConfirm()
      .subscribe(() => {
        this.sapReceiveInfos.removeAt(i);
        this.sapReceiveInfosChange$.next();
      });
  }

  displayLfa1Fn(lfa1: Lfa1): string {
    return lfa1 ? lfa1.name1 : '';
  }

  compareWithTransCorp(o1: TransCorp, o2: TransCorp): boolean {
    return o1 && o2 && o1.id === o2.id;
  }

  compareWithT001l(o1: T001l, o2: T001l): boolean {
    return o1 && o2 && o1.werks === o2.werks && o1.lgort === o2.lgort;
  }

  private setYlips() {
    if (!this.ylips) {
      return;
    }
    this.sendDate.setValue(moment(this.ylips.erdat).toDate());
    this.sendDate.disable();
    this.sendLfimg1.setValue(this.ylips.lfimg1);
    this.sendLfimg1.disable();
    this.sendLfimg2.setValue(this.ylips.lfimg2);
    this.sendLfimg2.disable();
    this.sendLfimg.setValue(this.ylips.lfimg);
    this.sendLfimg.disable();
    this.packType.setValue(this.ylips.packType);
    this.packType.disable();
    this.transType.setValue(this.ylips.transType);
    this.transType.disable();
    this.carNo.setValue(this.ylips.carNo);
    this.carNo.disable();
    this.batchNo.setValue(this.ylips.batchNo);
    this.batchNo.disable();
    this.lfa1.setValue(NINGBO_LFA1);
    this.lfa1.disable();
  }

  private setSapReceiveInfos(sapReceiveInfos: SapReceiveInfo[]) {
    const formGroups = (sapReceiveInfos || []).map(sapReceiveInfo => this.fb.group({
      'sapNo': [sapReceiveInfo.sapNo, [Validators.required]],
      'amount': [sapReceiveInfo.amount, [Validators.required, Validators.min(0)]],
      't001l': [sapReceiveInfo.t001l, [Validators.required]],
    }));
    const formArray = this.fb.array(formGroups);
    this.sendInfoForm.setControl('sapReceiveInfos', formArray);
    // this.sapReceiveInfosChange$.subscribe(() => {
    //   if (this.sapReceiveInfos.length === 1) {
    //     this.sapReceiveInfos.controls.forEach(it => {
    //       it.get('amount').disable();
    //     });
    //   } else {
    //     this.sapReceiveInfos.controls.forEach(it => {
    //       it.get('amount').enable();
    //     });
    //   }
    // });
  }
}
