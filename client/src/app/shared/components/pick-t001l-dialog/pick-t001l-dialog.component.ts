import {ChangeDetectionStrategy, Component, HostBinding, Inject, OnDestroy, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatSelectionList} from '@angular/material';
import {createSelector, Store} from '@ngrx/store';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';
import {Subscription} from 'rxjs/Subscription';
import {ApiService} from '../../../core/services/api.service';
import {coreIsMobile} from '../../../core/store';
import {T001l} from '../../models/t001l';

class State {
  t001lsEntities: { [id: string]: T001l } = {};
  filteredT001ls: T001l[] = [];
  searchQ: string;
  title: string;
}

const getT001lsEntities = (state: State) => state.t001lsEntities;
const getFilteredT001ls = (state: State) => state.filteredT001ls || [];
const getSearchQ = (state: State) => state.searchQ;
const getTitle = (state: State) => state.title || '库存地选择';
const getT001ls = createSelector(getT001lsEntities, getFilteredT001ls, getSearchQ, (entities, filtereds, searchQ) => {
  return Object.keys(entities).map(it => entities[it])
    .filter(t001l => {
      const find = filtereds.find(it => it.werks === t001l.werks && it.lgort === t001l.lgort);
      if (find) {
        return false;
      }
      if (searchQ) {
        if (t001l.werks.indexOf(searchQ) === 0 || t001l.lgort.indexOf(searchQ) === 0 || t001l.lgobe.indexOf(searchQ) >= 0) {
          return true;
        }
        return false;
      }
      return true;
    })
    .sort((a, b) => {
      const [pa, pb] = [a.werks + a.lgort, b.werks + b.lgort];
      return pa.localeCompare(pb);
    });
});

@Component({
  selector: 'jcargo-pick-t001l-dialog',
  templateUrl: './pick-t001l-dialog.component.html',
  styleUrls: ['./pick-t001l-dialog.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PickT001lDialogComponent implements OnDestroy {
  @HostBinding('class.jcargo-dialog-comp') b1 = true;
  @HostBinding('class.jcargo-pick-t001l-dialog') b2 = true;
  readonly isMobile$: Observable<boolean>;
  readonly multi: boolean;
  readonly searchQCtrl = new FormControl();
  @ViewChild('t001ls') t001lSelectionList: MatSelectionList;
  private readonly _subscriptions: Subscription[] = [];
  private readonly state$ = new BehaviorSubject(new State());
  readonly title$ = this.state$.map(getTitle);
  readonly t001ls$ = this.state$.map(getT001ls);

  constructor(private store: Store<any>,
              private api: ApiService,
              private dialogRef: MatDialogRef<PickT001lDialogComponent>,
              @Inject(MAT_DIALOG_DATA)  data: { filteredT001ls?: T001l[], multi?: boolean, title?: string }) {
    this.multi = data.multi;
    this.isMobile$ = this.store.select(coreIsMobile);
    api.listT001l().subscribe(t001ls => {
      const t001lsEntities = T001l.toEntities(t001ls, this.state$.value.t001lsEntities);
      const next = {...this.state$.value, ...data, t001lsEntities};
      this.state$.next(next);
    });

    this._subscriptions.push(
      this.searchQCtrl.valueChanges
        .debounceTime(300)
        .distinctUntilChanged()
        .subscribe(searchQ => {
          const next = {...this.state$.value, searchQ};
          this.state$.next(next);
        })
    );
  }

  static open(dialog: MatDialog, data?: { filteredT001ls?: T001l[], multi?: boolean, title?: string }): MatDialogRef<PickT001lDialogComponent> {
    return dialog.open(PickT001lDialogComponent, {
      panelClass: 'my-dialog',
      data: data || {}
    });
  }

  pick(t001l: T001l) {
    this.dialogRef.close(t001l);
  }

  pickMulti() {
    const selecteds = this.t001lSelectionList.selectedOptions.selected;
    if (selecteds.length > 0) {
      const t001ls = selecteds.map(it => it.value);
      this.dialogRef.close(t001ls);
    } else {
      this.dialogRef.close();
    }
  }

  ngOnDestroy(): void {
    this._subscriptions.forEach(it => it.unsubscribe());
    this.state$.complete();
  }
}
