import { NgModule } from '@angular/core';
import { SharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { TranslateDirective } from './language/translate.directive';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { DurationPipe } from './date/duration.pipe';
import { FormatMediumDatetimePipe } from './date/format-medium-datetime.pipe';
import { FormatMediumDatePipe } from './date/format-medium-date.pipe';
import { SortByDirective } from './sort/sort-by.directive';
import { SortDirective } from './sort/sort.directive';
import { ItemCountComponent } from './pagination/item-count.component';
import { FilterComponent } from './filter/filter.component';
import { NgbdSortableHeaderDirective } from 'app/shared/sort/sortable.directive';
import { BackButtonDirective } from 'app/shared/backbutton/back-button.directive';
import { ImportoDirective } from 'app/shared/utils/importo.directive';
import { NgxPaginationModule } from 'ngx-pagination';
import { SpinnerComponent } from './spinner/spinner.component';
import { SearchSpinnerComponent } from './search-spinner/search-spinner.component';
import { DragDropDirective } from './drag-drop/drag-drop.directive';
import { AlertWarningComponent } from './alert/alert-warning.component';
import { StringValidator } from './string-validator/string-validator';

@NgModule({
  imports: [SharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    TranslateDirective,
    AlertComponent,
    AlertWarningComponent,
    SpinnerComponent,
    SearchSpinnerComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    DragDropDirective,
    ItemCountComponent,
    FilterComponent,
    NgbdSortableHeaderDirective,
    BackButtonDirective,
    ImportoDirective,
  ],
  exports: [
    SharedLibsModule,
    FindLanguageFromKeyPipe,
    TranslateDirective,
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
    FilterComponent,
    SpinnerComponent,
    NgbdSortableHeaderDirective,
    BackButtonDirective,
    ImportoDirective,
    NgxPaginationModule,
    SearchSpinnerComponent,
    DragDropDirective,
    AlertWarningComponent,
  ],
})
export class SharedModule {}
