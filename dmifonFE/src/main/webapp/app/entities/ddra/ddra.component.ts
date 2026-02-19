import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DdrService } from 'app/services/ddr.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { TranslateService } from '@ngx-translate/core';
import { ProgService } from 'app/services/gest-progetti.service';
import { IDdrResponse, ProDdrResponse } from 'app/models/ddrResponse.model';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { ProTemService } from '../../services/protem.service';
import { DdraService } from '../../services/ddra.service';
import { IDdraResponse, ProDdraResponse } from '../../models/ddraResponse.model';
import { IDdr } from '../../models/ddr.model';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-ddra',
  templateUrl: './ddra.component.html',
  styleUrls: ['./ddra.component.scss'],
})
export class DdraComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: ProDdraResponse[];
  viewDelete = true;
  countFiltri: number;
  ddra!: Array<ProDdraResponse>;
  ddraNoSort!: Array<ProDdraResponse>;
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  progetti: Array<any> = [];
  tipiFin: Array<any> = [];
  direzioni: Array<any> = [];
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    protected router: Router,
    protected ddrService: DdrService,
    protected dirService: DirCentraleService,
    protected tipFinService: TipFinService,
    protected temService: ProTemService,
    protected progService: ProgService,
    private ddraService: DdraService,
    protected exportTableCsvService: ExportTableCsvService,
    protected translateService: TranslateService,
    protected rolesCheckService: RolesCheckService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/ddra';
    this.countFiltri = 0;
  }

  //ricerca con autocomplete delle Ddra
  searchDdr: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.ddrService.ricercaDdrAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle Ddra
  formatterDdr = (x: { desddr: string; codddr: string; id: number }) => {
    if (x.id) {
      return `${x.codddr + ' - ' + x.desddr}`;
    }
    return '';
  };

  ngOnInit(): void {
    const jsog = new JsogService();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.warningMessage = '';
    this.formFilter = this._fb.group({
      autocomplete: [''],
      ddr: [null],
      idDdr: [null],
    });
    super.initSearchFilterForm(); // Recupero i filtri dallo storage
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        takeUntil(this.ngUnsubscribe),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap(value => this.ddraService.ricercaDdra(this.formFilter.value))
      )
      .subscribe(
        (res: any) => {
          this.isFetching = false;
          this.ddra = <ProDdraResponse[]>jsog.deserialize(res);
          console.log(this.ddra);
          this.ddraNoSort = this.ddra;
          this._exportObj = <ProDdraResponse[]>jsog.deserialize(res);
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
    this.applicaFiltri();
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  ricercaServiceDdra(): void {
    const jsog = new JsogService();
    this.isFetching = true;
    this.ddraService
      .ricercaDdra(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.isFetching = false;
          this.ddra = <ProDdraResponse[]>jsog.deserialize(res);
          console.log(this.ddra);
          this.ddraNoSort = this.ddra;
          this._exportObj = <ProDdraResponse[]>jsog.deserialize(res);
          if (res.warningMessage) {
            this.warningMessage = res.warningMessage;
          } else {
            this.warningMessage = '';
          }
        },
        err => {
          this.showAlertError(err);
          this.isFetching = false;
        }
      );
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
      this.ddra = this.ddraNoSort;
    } else {
      this.ddra = this.ddra.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'codddra':
            if (a.ddra.codddra != null && b.ddra.codddra != null) {
              res = compare(a.ddra.codddra, b.ddra.codddra);
            } else {
              res = a.ddra.codddra == null ? 1 : -1;
            }
            break;
          case 'desddra':
            if (a.ddra.desddra != null && b.ddra.desddra != null) {
              res = compare(a.ddra.desddra, b.ddra.desddra);
            } else {
              res = a.ddra.desddra == null ? 1 : -1;
            }
            break;
          case 'dtaddra':
            if (a.ddra.dtaddra != null && b.ddra.dtaddra != null) {
              res = compare(a.ddra.dtaddra, b.ddra.dtaddra);
            } else {
              res = a.ddra.dtaddra == null ? 1 : -1;
            }
            break;
          case 'impddra':
            if (a.importoDdra != null && b.importoDdra != null) {
              res = compare(a.importoDdra, b.importoDdra);
            } else {
              res = a.importoDdra == null ? 1 : -1;
            }
            break;
          default:
            res = compare(a.ddra[column], b.ddra[column]);
            break;
        }

        return this.direction === 'asc' ? res : -res;
      });

      this.direction = direction;
      this.columnSort = column;
    }
  }

  //metodo che applica i filtri
  applicaFiltri(): void {
    // this.countFiltri = 0;
    const ddrObj = this.formFilter.get('ddr')?.value;
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.formFilter.patchValue({
      idDdr: this.formFilter.get('ddr')?.value != null ? (this.formFilter.get('ddr')?.value).id : null,
    });
    this.ricercaServiceDdra();
    this.formFilter.patchValue({
      ddr: ddrObj,
    });
  }

  countFiltriApplicati(): void {
    this.countFiltri = 0;
    const controlsFilter = this.formFilter.controls;
    for (const controlFilter in controlsFilter) {
      if (
        controlsFilter[controlFilter].value != null &&
        controlsFilter[controlFilter].value !== '' &&
        controlFilter !== 'autocomplete' &&
        controlFilter !== 'idDdr' &&
        controlFilter !== 'calcolaTotali'
      ) {
        this.countFiltri++;
      }
    }
  }

  initSearchFilterForm(): void {
    super.initSearchFilterForm();
  }

  getFormElement(elemento: string) {
    return this.formFilter.controls[elemento];
  }

  ricercaDdr(): void {
    if (this.getFormElement('autocomplete').value.length > 1 || this.getFormElement('autocomplete').value.length === 0) {
      this.applicaFiltri();
    }
  }

  nuovoDdra(): void {
    this.router.navigateByUrl('/ddra/new');
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`ddra/${id}/viewDetail`]);
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('ddra.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  resetFilter(): void {
    this.formFilter.patchValue({
      ddr: null,
      idDdr: null,
    });
    this.formFilter.enable();
    this.countFiltri = 0;
  }

  private convertDataCSV(queryResult?: ProDdraResponse[]): any[] {
    const items: any[] = [];
    console.log(queryResult);
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('ddr.codddr')]: element.ddra.codddra ? element.ddra.codddra : '',
        [this.translateService.instant('ddr.desddr')]: element.ddra.desddra ? element.ddra.desddra : '',
        [this.translateService.instant('ddr.dtaddr')]: element.ddra.dtaddra,
        [this.translateService.instant('ddr.impddr')]: element.importoDdra ? this.transformValueImporto(element.importoDdra) : '0,00',
      };
      items.push(csvLine);
    });
    return items;
  }
}
