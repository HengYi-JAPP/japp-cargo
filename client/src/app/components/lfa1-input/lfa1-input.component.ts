import {FocusMonitor} from '@angular/cdk/a11y';
import {coerceBooleanProperty} from '@angular/cdk/coercion';
import {
  ChangeDetectionStrategy,
  Component,
  DoCheck,
  ElementRef,
  HostBinding,
  Input,
  OnDestroy,
  Optional,
  Renderer2,
  Self
} from '@angular/core';
import {ControlValueAccessor, FormControl, FormGroupDirective, NgControl, NgForm} from '@angular/forms';
import {ErrorStateMatcher, MatFormField, MatFormFieldControl} from '@angular/material';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {Subject} from 'rxjs/Subject';
import {ApiService} from '../../services/api.service';
import {Lfa1} from '../../models/lfa1';

@Component({
  moduleId: module.id,
  selector: 'jcargo-lfa1-input',
  templateUrl: './lfa1-input.component.html',
  styleUrls: ['./lfa1-input.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {provide: MatFormFieldControl, useExisting: Lfa1InputComponent},
  ],
})
export class Lfa1InputComponent implements MatFormFieldControl<Lfa1>, ControlValueAccessor, DoCheck, OnDestroy {
  static nextId = 0;
  private _placeholder: string;
  private _required = false;
  private _readonly = false;
  private _disabled = false;
  lfa1Ctrl: FormControl;
  stateChanges = new Subject<void>();
  @HostBinding()
  id = `jcargo-lfa1-input-${Lfa1InputComponent.nextId++}`;
  @HostBinding('attr.aria-describedby')
  describedBy = '';
  focused = false;
  errorState = false;
  controlType = 'jcargo-lfa1-input';
  @Input()
  bukrs: string;
  @Input()
  errorStateMatcher: ErrorStateMatcher;
  readonly lfa1s$: Observable<Lfa1[]>;
  private _onChange: (value: any) => void = () => {
  }
  private _onTouched = () => {
  }

  constructor(private fm: FocusMonitor,
              private elRef: ElementRef,
              renderer: Renderer2,
              @Optional() private _parentForm: NgForm,
              @Optional() private _parentFormGroup: FormGroupDirective,
              @Optional() private _parentFormField: MatFormField,
              @Optional() @Self() public ngControl: NgControl,
              private apiService: ApiService) {
    this.lfa1Ctrl = new FormControl();
    fm.monitor(elRef.nativeElement, renderer, true).subscribe((origin) => {
      this.focused = !!origin;
      this.stateChanges.next();
    });
    if (this.ngControl) {
      this.ngControl.valueAccessor = this;
    }

    this.lfa1s$ = this.lfa1Ctrl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(q => {
        this._onChange(q);
        if (typeof q === 'object') {
          return of([]);
        }
        return q ? this.apiService.autocompleteLfa1(q, this.bukrs) : of([]);
      })
    );
  }


  @Input()
  get placeholder() {
    return this._placeholder;
  }

  set placeholder(plh) {
    this._placeholder = plh;
    this.stateChanges.next();
  }


  @Input()
  get required() {
    return this._required;
  }

  set required(req) {
    this._required = coerceBooleanProperty(req);
    this.stateChanges.next();
  }


  @Input()
  get readonly() {
    return this._readonly;
  }

  set readonly(value) {
    this._readonly = coerceBooleanProperty(value);
    this.stateChanges.next();
  }

  @Input()
  get disabled() {
    const result = this.ngControl ? this.ngControl.disabled : this._disabled;
    if (result) {
      this.lfa1Ctrl.disable();
    } else {
      this.lfa1Ctrl.enable();
    }
    return result;
  }

  set disabled(dis) {
    this._disabled = coerceBooleanProperty(dis);
    this.stateChanges.next();
  }

  get empty(): boolean {
    return !this.value || !this.value.lifnr;
  }

  @HostBinding('class.floating')
  get shouldPlaceholderFloat(): boolean {
    return this.focused || !this.empty;
  }

  @Input()
  get value(): Lfa1 | null {
    return this.lfa1Ctrl.value;
  }

  set value(value: Lfa1 | null) {
    this.lfa1Ctrl.setValue(value);
    this.stateChanges.next();
  }

  displayLfa1Fn(lfa1: Lfa1): string {
    return lfa1 ? lfa1.name1 : '';
  }

  ngDoCheck() {
    if (this.ngControl) {
      const control = this.ngControl ? (this.ngControl.control) : null;
      if (this.lfa1Ctrl.pristine) {
        this.errorState = false;
      } else if (control.value && control.value.lifnr) {
        this.errorState = false;
      } else {
        this.errorState = true;
      }
      console.log('this.lfa1Ctrl', this.lfa1Ctrl);
      console.log('this.ngControl.control', control);
      this.stateChanges.next();
    }
  }

  setDescribedByIds(ids: string[]): void {
    this.describedBy = ids.join(' ');
  }

  onContainerClick(event: MouseEvent): void {
    if ((event.target as Element).tagName.toLowerCase() !== 'input') {
      this.elRef.nativeElement.querySelector('input').focus();
    }
  }

  ngOnDestroy() {
    this.stateChanges.complete();
    this.fm.stopMonitoring(this.elRef.nativeElement);
  }

  writeValue(obj: any): void {
    this.lfa1Ctrl.setValue(obj);
    this.stateChanges.next();
  }

  registerOnChange(fn: any): void {
    this._onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this._onTouched = fn;
  }

}
