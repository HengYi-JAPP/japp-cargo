import {FocusMonitor} from '@angular/cdk/a11y';
import {coerceBooleanProperty} from '@angular/cdk/coercion';
import {
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  forwardRef,
  HostBinding,
  Input,
  OnDestroy,
  Optional,
  Renderer2,
  Self
} from '@angular/core';
import {ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR, NgControl} from '@angular/forms';
import {MatFormFieldControl} from '@angular/material';
import {Subject} from 'rxjs/Subject';
import {Lfa1} from '../../models/lfa1';

@Component({
  selector: 'jcargo-lfa1-input',
  templateUrl: './lfa1-input.component.html',
  styleUrls: ['./lfa1-input.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {provide: MatFormFieldControl, useExisting: Lfa1InputComponent},
    {provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => Lfa1InputComponent), multi: true}
  ],
})
export class Lfa1InputComponent implements MatFormFieldControl<Lfa1>, ControlValueAccessor, OnDestroy {
  static nextId = 0;
  lfa1Ctrl: FormControl;
  stateChanges = new Subject<void>();
  @HostBinding()
  id = `jcargo-lfa1-input-${Lfa1InputComponent.nextId++}`;
  @HostBinding('attr.aria-describedby')
  describedBy = '';
  focused = false;
  errorState = false;
  controlType = 'jcargo-lfa1-input';
  private _onChange: any;

  constructor(private fm: FocusMonitor,
              private elRef: ElementRef,
              renderer: Renderer2,
              @Optional() @Self() public ngControl: NgControl) {
    this.lfa1Ctrl = new FormControl();
    fm.monitor(elRef.nativeElement, renderer, true).subscribe((origin) => {
      this.focused = !!origin;
      this.stateChanges.next();
    });
  }

  private _placeholder: string;

  @Input()
  get placeholder() {
    return this._placeholder;
  }

  set placeholder(plh) {
    this._placeholder = plh;
    this.stateChanges.next();
  }

  private _required = false;

  @Input()
  get required() {
    return this._required;
  }

  set required(req) {
    this._required = coerceBooleanProperty(req);
    this.stateChanges.next();
  }

  private _disabled = false;

  @Input()
  get disabled() {
    return this._disabled;
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
  }

}
