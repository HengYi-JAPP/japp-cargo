import {HttpParams} from '@angular/common/http';
import {ChangeDetectionStrategy, Component, HostBinding, Inject} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {createSelector, Store} from '@ngrx/store';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/switchMap';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {ApiService} from '../../../core/services/api.service';
import {Ylips} from '../../models/ylips';

export enum YlipsSendType {
  RECEIVED, UNRECEIVED
}

class State {
  ylipsEntities: { [id: string]: Ylips } = {};
  carNoQ: string;
  title = '宁波逸盛发货';
  multi: boolean;
}

const getYlipsEntities = (state: State) => state.ylipsEntities;
const getCarNoQ = (state: State) => state.carNoQ;
const getTitle = (state: State) => state.title;
const getYlipses = createSelector(getYlipsEntities, getCarNoQ, (entities, carNoQ) => Object.keys(entities)
  .map(it => entities[it])
  .filter(ylips => {
    if (carNoQ) {
      if (ylips.carNo.toLowerCase().indexOf(carNoQ.toLowerCase()) >= 0) {
        return true;
      }
      return false;
    }
    return true;
  })
);

@Component({
  selector: 'jcargo-pick-ylips-dialog',
  templateUrl: './pick-ylips-dialog.component.html',
  styleUrls: ['./pick-ylips-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PickYlipsDialogComponent {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-pick-ylips-dialog') b2 = true;
  readonly maxSearchDate = new Date();
  readonly searchForm: FormGroup;
  private readonly state$ = new BehaviorSubject(new State());
  readonly ylipses$ = this.state$.map(getYlipses);
  readonly title$ = this.state$.map(getTitle);

  constructor(private store: Store<any>,
              private fb: FormBuilder,
              private apiService: ApiService,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<PickYlipsDialogComponent>,
              @Inject(MAT_DIALOG_DATA)  data: { date: Date, sendType: YlipsSendType, multi: boolean }) {
    const {date, sendType, multi} = data;
    this.searchForm = this.fb.group({
      'searchDate': moment(date).toDate(),
      'carNoQ': '',
    });
    this.searchDate.valueChanges
      .startWith(date)
      .map(it => moment(it).format('YYYY-MM-DD'))
      .map(it => new HttpParams().set('date', it))
      .switchMap(params => {
        switch (sendType) {
          case YlipsSendType.RECEIVED: {
            return this.apiService.listYlips(params);
          }
          default: {
            return this.apiService.listSapYlips(params);
          }
        }
      })
      .subscribe(ylipses => {
        const ylipsEntities = Ylips.toEntities(ylipses);
        const next = {...this.state$.value, ylipsEntities};
        this.state$.next(next);
      });
    this.carNoQ.valueChanges
      .debounceTime(300)
      .distinctUntilChanged()
      .subscribe(carNoQ => {
        const next = {...this.state$.value, carNoQ};
        this.state$.next(next);
      });
  }

  get searchDate() {
    return this.searchForm.get('searchDate');
  }

  get carNoQ() {
    return this.searchForm.get('carNoQ');
  }

  static open(dialog: MatDialog, data?: { date?: Date }): MatDialogRef<PickYlipsDialogComponent> {
    return dialog.open(PickYlipsDialogComponent, {panelClass: 'my-dialog', data: {...data}});
  }

  pick(ylips: Ylips) {
    this.dialogRef.close(ylips);
  }
}
