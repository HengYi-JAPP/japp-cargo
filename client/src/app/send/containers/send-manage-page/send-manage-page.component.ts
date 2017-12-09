import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {UtilService} from '../../../core/services/util.service';
import {coreActions} from '../../../core/store';
import {PickYlipsDialogComponent} from '../../../shared/components/pick-ylips-dialog/pick-ylips-dialog.component';
import {MegSendInfo} from '../../../shared/models/meg-send-info';
import {PtaSendInfo} from '../../../shared/models/pta-send-info';
import {SendInfo} from '../../../shared/models/send-info';
import {SendUpdateDialogComponent} from '../../components/send-update-dialog/send-update-dialog.component';
import {sendManagePageActions, sendManagePageSendInfos} from '../../store';

@Component({
  selector: 'jcargo-send-manage-page',
  templateUrl: './send-manage-page.component.html',
  styleUrls: ['./send-manage-page.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SendManagePageComponent {
  @HostBinding('class.jcargo-page') b1 = true;
  @HostBinding('class.jcargo-send-manage-page') b2 = true;
  readonly maxSearchDate = new Date();
  readonly displayedColumns = ['sendDate', 'receiveDate', 'carNo', 'sendLfimg', 'receiveLfimg', 'diffLfimg', 'btns'];
  readonly dataSource: SendInfoDataSource;
  readonly searchForm: FormGroup;

  constructor(private store: Store<any>,
              private dialog: MatDialog,
              private fb: FormBuilder,
              private utilService: UtilService) {
    this.store.dispatch(new coreActions.FetchAuth());
    this.searchForm = fb.group({
      'searchDate': [new Date(), Validators.required],
      'carNoQ': '',
    });
    this.dataSource = new SendInfoDataSource(this.store);
    this.searchDate.valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .map(date => new sendManagePageActions.Init({date}))
      .subscribe(it => this.store.dispatch(it));
    this.carNoQ.valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .map(carNoQ => new sendManagePageActions.SetCarNoQ({carNoQ}))
      .subscribe(it => this.store.dispatch(it));
    this.store.dispatch(new sendManagePageActions.Init({}));
  }

  get carNoQ() {
    return this.searchForm.get('carNoQ');
  }

  get searchDate() {
    return this.searchForm.get('searchDate');
  }

  pickYlips() {
    PickYlipsDialogComponent.open(this.dialog)
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .switchMap(ylips => SendUpdateDialogComponent.updatePta(this.dialog, {ylips}).afterClosed())
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => {
        this.store.dispatch(new sendManagePageActions.SavePtaSend({sendInfo: res}));
      });
  }

  updatePta(sendInfo?: PtaSendInfo) {
    SendUpdateDialogComponent.updatePta(this.dialog, {sendInfo})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => {
        this.store.dispatch(new sendManagePageActions.SavePtaSend({sendInfo: res}));
      });
  }

  updateMeg(sendInfo?: MegSendInfo) {
    SendUpdateDialogComponent.updateMeg(this.dialog, {sendInfo})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => {
        this.store.dispatch(new sendManagePageActions.SaveMegSend({sendInfo: res}));
      });
  }

  updateSendInfo(sendInfo: PtaSendInfo | MegSendInfo) {
    if (sendInfo._class_ === 'PtaSendInfo') {
      this.updatePta(sendInfo as PtaSendInfo);
    } else if (sendInfo._class_ === 'MegSendInfo') {
      this.updateMeg(sendInfo as MegSendInfo);
    }
  }

  delete(sendInfo: PtaSendInfo | MegSendInfo) {
    this.utilService.showConfirm()
      .subscribe(() => {
        if (sendInfo._class_ === 'PtaSendInfo') {
          this.store.dispatch(new sendManagePageActions.DeletePtaSend({id: sendInfo.id}));
        } else if (sendInfo._class_ === 'MegSendInfo') {
          this.store.dispatch(new sendManagePageActions.DeleteMegSend({id: sendInfo.id}));
        }
      });
  }
}

class SendInfoDataSource extends DataSource<SendInfo> {
  readonly sendInfos$: Observable<SendInfo[]>;

  constructor(private store: Store<any>) {
    super();
    this.sendInfos$ = this.store.select(sendManagePageSendInfos);
  }

  connect(collectionViewer: CollectionViewer): Observable<SendInfo[]> {
    return this.sendInfos$;
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
