import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {ChangeDetectionStrategy, Component, HostBinding} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material';
import {Router} from '@angular/router';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {PickYlipsDialogComponent} from '../../components/pick-ylips-dialog/pick-ylips-dialog.component';
import {SendUpdateDialogComponent} from '../../components/send-update-dialog/send-update-dialog.component';
import {MegSendInfo} from '../../models/meg-send-info';
import {PtaSendInfo} from '../../models/pta-send-info';
import {SendInfo} from '../../models/send-info';
import {UtilService} from '../../services/util.service';
import {coreActions, sendManagePageActions} from '../../store/actions/index';
import {sendManagePageDate, sendManagePageSendInfos} from '../../store/send';

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
  readonly displayedColumns = ['receiveDate', 'carNo', 'sendLfimg', 'receiveLfimg', 'diffLfimg', 'btns'];
  readonly dataSource: SendInfoDataSource;
  readonly searchForm: FormGroup;

  constructor(private store: Store<any>,
              private router: Router,
              private dialog: MatDialog,
              private fb: FormBuilder,
              private utilService: UtilService) {
    this.store.dispatch(new coreActions.FetchAuth());
    this.searchForm = fb.group({
      'searchDate': [new Date(), Validators.required],
      'carNoQ': '',
    });

    this.store.select(sendManagePageDate)
      .subscribe(it => this.searchDate.setValue(it));

    this.dataSource = new SendInfoDataSource(this.store);
    this.searchDate.valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .map(date => moment(date).format('YYYY-MM-DD'))
      .subscribe(date => {
        this.router.navigate(['sends'], {queryParams: {date}});
      });
    this.carNoQ.valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .map(carNoQ => new sendManagePageActions.SetCarNoQ({carNoQ}))
      .subscribe(it => this.store.dispatch(it));
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

  updateMeg1(sendInfo?: MegSendInfo) {
    SendUpdateDialogComponent.updateMeg(this.dialog, {sendInfo, megType: 1})
      .afterClosed()
      .filter(it => !isNullOrUndefined(it) && it)
      .subscribe(res => {
        this.store.dispatch(new sendManagePageActions.SaveMegSend({sendInfo: res}));
      });
  }

  updateSendInfo(sendInfo: PtaSendInfo | MegSendInfo) {
    if (sendInfo instanceof PtaSendInfo) {
      this.updatePta(sendInfo as PtaSendInfo);
    } else if (sendInfo instanceof MegSendInfo) {
      this.updateMeg(sendInfo as MegSendInfo);
    }
  }

  delete(sendInfo: PtaSendInfo | MegSendInfo) {
    this.utilService.showConfirm()
      .subscribe(() => {
        if (sendInfo instanceof PtaSendInfo) {
          this.store.dispatch(new sendManagePageActions.DeletePtaSend({id: sendInfo.id}));
        } else if (sendInfo instanceof MegSendInfo) {
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
