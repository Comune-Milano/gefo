import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, map, Observable, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { TranslateService } from '@ngx-translate/core';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { ProNilService } from '../../services/pronil.service';
import { INil, NilResponse } from '../../models/pronil.model';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './nil.component.html',
  styleUrls: ['./nil.component.scss'],
})
export class NilComponent extends SearchPage implements OnInit {
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: INil;
  countFiltri: number;
  nil!: Array<NilResponse>;
  nilNoSort!: Array<NilResponse>;
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    protected router: Router,
    protected dirService: DirCentraleService,
    protected tipFinService: TipFinService,
    protected nilService: ProNilService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/municipi';
    this.countFiltri = 0;
  }

  ngOnInit(): void {
    const jsog = new JsogService();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.formFilter = this._fb.group({
      autocomplete: [''],
      tipFinDa: [null, [Validators.min(1)]],
      tipFinA: [null, [Validators.min(1)]],
      calcolaTotali: true,
    });
    super.initSearchFilterForm(); // Recupero i filtri dallo storage
    this.highlightText = this.formFilter.get('autocomplete')?.value;
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        takeUntil(this.ngUnsubscribe),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap((value: any) => this.nilService.ricercaNil(this.formFilter.value))
      )
      .subscribe(
        (res: any) => {
          this.nil = <NilResponse[]>jsog.deserialize(res.nil);
          this.nilNoSort = JSON.parse(JSON.stringify(this.nil)); // Clone per ordinamento iniziale
          this._exportObj = res;
          this.isFetching = false;
          if (res.warningMessage) {
            this.warningMessage = res.warningMessage;
          } else {
            this.warningMessage = '';
          }
        },
        err => {
          this.checkErrorStatus(err);
          this.showAlertError(err);
          this.isFetching = false;
        }
      );

    this.isFetching = true;
    this.nilService
      .ricercaNil(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.nil = <NilResponse[]>jsog.deserialize(res.nil);
          this.nilNoSort = JSON.parse(JSON.stringify(this.nil)); // Clone per ordinamento iniziale
          this._exportObj = res;
          this.isFetching = false;
          if (res.warningMessage) {
            this.warningMessage = res.warningMessage;
          } else {
            this.warningMessage = '';
          }
        },
        err => {
          this.checkErrorStatus(err);
          this.showAlertError(err);
          this.isFetching = false;
        }
      );
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  onSort({ column, direction }: SortEvent): void {
    // resetting other headers

    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    // sorting
    if (direction === '' || column === '') {
      this.nil = JSON.parse(JSON.stringify(this.nilNoSort));
    } else {
      this.nil = this.nil.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'nroprofon':
          case 'imprisfon':
            res = compare(a.totali[column], b.totali[column]);
            break;
          default:
            res = compare(a.proNil[column], b.proNil[column]);
            break;
        }

        return this.direction === 'asc' ? res : -res;
      });

      this.direction = direction;
      this.columnSort = column;
    }
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formFilter.controls[elemento];
  }

  nuovoMunicipio(): void {
    this.router.navigateByUrl('/nil/new');
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`nil/${id}/viewDetail`]);
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('nil.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  private convertDataCSV(queryResult?: INil): any[] {
    const items: any[] = [];

    queryResult?.nil.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('nil.codnil')]: element.proNil.codnil ? element.proNil.codnil : '',
        [this.translateService.instant('nil.desnil')]: element.proNil.desnil ? element.proNil.desnil : '',
        [this.translateService.instant('nil.resources')]: element.totali.imprisfon ? element.totali.imprisfon : '0',
        [this.translateService.instant('nil.num_progetti')]: element.totali.nroprofon ? element.totali.nroprofon : '0',
      };
      items.push(csvLine);
    });
    return items;
  }
}
