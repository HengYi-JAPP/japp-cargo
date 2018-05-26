import {CdkTableModule} from '@angular/cdk/table';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  DateAdapter,
  MAT_DATE_FORMATS,
  MAT_DATE_LOCALE,
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorIntl,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
} from '@angular/material';
import {TranslateModule} from '@ngx-translate/core';
import {DailyDetailExportDialogComponent} from './components/daily-detail-export-dialog/daily-detail-export-dialog.component';
import {Lfa1InputComponent} from './components/lfa1-input/lfa1-input.component';
import {PickT001lDialogComponent} from './components/pick-t001l-dialog/pick-t001l-dialog.component';
import {PickYlipsDialogComponent} from './components/pick-ylips-dialog/pick-ylips-dialog.component';
import {DelPreZeroPipe} from './services/del-pre-zero.pipe';
import {MyPaginatorIntl} from './services/my-paginator-intl';
import {PackTypePipe} from './services/pack-type.pipe';
import {TransTypePipe} from './services/trans-type.pipe';
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";

const ENTRYCOMPONENTS = [
  PickT001lDialogComponent,
  PickYlipsDialogComponent,
  DailyDetailExportDialogComponent,
];
const DECLARATIONS = [
  PackTypePipe,
  DelPreZeroPipe,
  TransTypePipe,
  Lfa1InputComponent,
  ...ENTRYCOMPONENTS
];
const COMMONS = [
  CommonModule,
  HttpClientModule,
  FormsModule,
  ReactiveFormsModule,
  TranslateModule,
  FlexLayoutModule,
  CdkTableModule,
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatAutocompleteModule,
  MatSortModule,
];

@NgModule({
  imports: [...COMMONS],
  entryComponents: ENTRYCOMPONENTS,
  declarations: DECLARATIONS,
  exports: [...DECLARATIONS, ...COMMONS],
  providers: [
    // {provide: DateAdapter, useClass: MyDateAdapter},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
    {provide: MAT_DATE_LOCALE, useValue: 'zh-CN'},
    {provide: MatPaginatorIntl, useClass: MyPaginatorIntl},
  ]
})
export class SharedModule {
}
