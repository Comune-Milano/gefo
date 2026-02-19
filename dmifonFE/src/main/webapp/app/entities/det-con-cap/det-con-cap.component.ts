import { Component, OnInit, ViewChildren, QueryList, Injector, Input, Output, EventEmitter } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { map, Observable, of, Subject, takeUntil } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
// import { AlertService } from 'app/shared/alert/alert.service';
import { ItipiFinanziamento, ProTipFinResponse } from 'app/models/tipiFinanziamento.model';
import { TranslateService } from '@ngx-translate/core';
import { ProgService } from 'app/services/gest-progetti.service';
import { IGestioneProgetti, ProProgResponse } from 'app/models/gest_prog.model';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { StatProService } from '../../services/statpro.service';
import { StaFinService } from '../../services/stafin.service';
import { DetConService } from '../../services/detcon.service';
import { UpdatePage } from '../../shared/baseclass/update-page';
import { IDetCon } from '../../models/detcon.model';
import { IproEntcon } from '../../models/proEntcon.model';
import { CapitoliService } from '../../services/capitoli.service';
import { IDatiCapitolo } from '../../models/capitoli.model';

@Component({
  selector: 'jhi-det-con-cap',
  templateUrl: './det-con-cap.component.html',
  styleUrls: ['./det-con-cap.component.scss'],
})
export class DetConCapComponent extends SearchPage implements OnInit {
  @ViewChildren(NgbdSortableHeaderDirective) headers!: QueryList<NgbdSortableHeaderDirective>;
  _rolesObj!: IGestioneProgetti;
  viewAlert = true;
  viewDelete = true;
  viewError = true;
  countFiltri: number;
  capent = false;
  prog = false;
  entCon: IDatiCapitolo[] = [];
  entConNoSort: IDatiCapitolo[] = [];
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  id = 0;
  shPanCap = false;
  @Input() tipo?: string;
  @Output() codCap = new EventEmitter<string>();
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    protected router: Router,
    private activatedRoute: ActivatedRoute,
    protected progService: ProgService,
    protected detConService: DetConService,
    protected capitoliService: CapitoliService,
    protected statProService: StatProService,
    protected staFinService: StaFinService,
    protected exportTableCsvService: ExportTableCsvService,
    // private alertService: AlertService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/gest-progetti';
    this.countFiltri = 0;
  }

  ngOnInit(): void {
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    if (this.tipo === 'Capitoli entrata') {
      this.capent = true;
    } else if (this.tipo === 'Capitoli uscita') {
      this.capent = false;
    } else {
      if (this.activatedRoute.snapshot.data.tipo === 'Capitoli entrata') {
        this.capent = true;
        this.prog = false;
      } else {
        this.prog = false;
      }
      //console.log(this.activatedRoute.snapshot);
      if (this.activatedRoute.snapshot.url[0]?.path === 'det-con-cape') {
        this.capent = true;
        this.prog = true;
        this.id = this.activatedRoute.snapshot.params.id;
      } else if (this.activatedRoute.snapshot.url[0]?.path === 'det-con-capu') {
        this.capent = false;
        this.prog = true;
        this.id = this.activatedRoute.snapshot.params.id;
      }
    }
    this.warningMessage = '';
    const jsog = new JsogService();
    this.formFilter = this._fb.group({
      id: [null],
      descrizione: [null],
      titolo: [null],
      macro: [null],
      missione: [null],
      programma: [null],
    });
    super.initSearchFilterForm(); // Recupero i filtri dallo storage
    this.formFilter
      .get('descrizione')
      ?.valueChanges.pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(value => {
        this.highlightText = value;
      });
    this.formFilter
      .get('id')
      ?.valueChanges.pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        if (this.formFilter.get('id')?.value != null && this.formFilter.get('id')?.value !== '') {
          this.formFilter.get('descrizione')?.reset();
          this.formFilter.get('descrizione')?.disable();
          this.formFilter.get('titolo')?.reset();
          this.formFilter.get('titolo')?.disable();
          this.formFilter.get('macro')?.reset();
          this.formFilter.get('macro')?.disable();
          this.formFilter.get('missione')?.reset();
          this.formFilter.get('missione')?.disable();
          this.formFilter.get('programma')?.reset();
          this.formFilter.get('programma')?.disable();
        } else {
          this.formFilter.get('id')?.setValue('');
          this.formFilter.get('descrizione')?.enable();
          this.formFilter.get('titolo')?.enable();
          this.formFilter.get('macro')?.enable();
          this.formFilter.get('missione')?.enable();
          this.formFilter.get('programma')?.enable();
        }
      });
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  //ricerca degli impegni/accertamenti - impegni/accertamenti progetto
  ricercaCapitoli(): void {
    this.entCon = [];
    this.isFetching = true;
    if (this.capent) {
      if (this.formFilter.get('id')?.value !== '' && this.formFilter.get('id')?.value !== null) {
        this.capitoliService
          .getCapitoloEntrata(this.formFilter.get('id')?.value)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              this.entCon.push(res);
              this.entConNoSort = JSON.parse(JSON.stringify(res));
              this.isFetching = false;
            },
            err => {
              this.isFetching = false;
              this.checkErrorStatus(err);
              this.showAlertError(err);
            }
          );
      } else {
        this.capitoliService
          .ricercaCapitoliEntrata(this.formFilter.value)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              this.entCon = res;
              this.entConNoSort = JSON.parse(JSON.stringify(res));
              this.isFetching = false;
            },
            err => {
              this.isFetching = false;
              this.entCon = [];
              this.entConNoSort = [];
              this.showAlertError(err);
            }
          );
      }
    } else {
      if (this.formFilter.get('id')?.value !== '' && this.formFilter.get('id')?.value !== null) {
        this.capitoliService
          .getCapitoloUscita(this.formFilter.get('id')?.value)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              this.entCon.push(res);
              this.entConNoSort = JSON.parse(JSON.stringify(res));
              this.isFetching = false;
            },
            err => {
              this.isFetching = false;
              this.checkErrorStatus(err);
              this.showAlertError(err);
            }
          );
      } else {
        this.capitoliService
          .ricercaCapitoliUscita(this.formFilter.value)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              this.entCon = res;
              this.entConNoSort = JSON.parse(JSON.stringify(res));
              this.isFetching = false;
            },
            err => {
              this.isFetching = false;
              this.checkErrorStatus(err);
              this.showAlertError(err);
            }
          );
      }
    }
  }

  onSort({ column, direction }: SortEvent): void {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    if (direction === '' || column === '') {
      this.entCon = JSON.parse(JSON.stringify(this.entConNoSort));
    } else {
      this.entCon = this.entCon.sort((a: any, b: any) => {
        const res = compare(a[column], b[column]);
        return this.direction === 'asc' ? res : -res;
      });
    }

    this.direction = direction;
    this.columnSort = column;
  }

  showFilterCap(): void {
    this.shPanCap = !this.shPanCap;
  }

  //metodo che applica i filtri
  applicaFiltri(): void {
    // this.countFiltri = 0;
    this.shPanCap = false;
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.ricercaCapitoli();
  }

  countFiltriApplicati(): void {
    this.countFiltri = 0;
    const controlsFilter = this.formFilter.controls;
    for (const controlFilter in controlsFilter) {
      if (
        controlsFilter[controlFilter].value != null &&
        controlsFilter[controlFilter].value !== '' &&
        controlFilter !== 'autocomplete' &&
        controlFilter !== 'calcolaTotali'
      ) {
        this.countFiltri++;
      }
    }
  }

  initSearchFilterForm(): void {
    super.initSearchFilterForm();
  }

  getFormElement(elemento: string): any {
    return this.formFilter.controls[elemento];
  }

  nuovoUtente(): void {
    this.router.navigateByUrl('/gest-progetti/new');
  }

  setCodCap(codice: string): void {
    this.codCap.emit(codice);
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`gest-progetti/${id}/viewDetail`]);
  }

  exportToCSV(): void {
    if (this.capent) {
      this.exportTableCsvService.exportTableToCsv(this.translateService.instant('DetCon.csvFileNameAcc'), this.convertDataCSV(this.entCon));
    } else {
      this.exportTableCsvService.exportTableToCsv(this.translateService.instant('DetCon.csvFileNameImp'), this.convertDataCSV(this.entCon));
    }
  }

  resetFilter(): void {
    this.shPanCap = false;
    this.formFilter.patchValue({
      id: null,
      descrizione: null,
      id_capitolo: null,
      id_crono: null,
      cup: null,
      cig: null,
    });
    this.formFilter.enable();
    this.countFiltri = 0;
  }

  private convertDataCSV(queryResult?: IDatiCapitolo[]): any[] {
    const items: any[] = [];
    if (this.capent) {
      queryResult?.forEach(element => {
        const csvLine: any = {
          [this.translateService.instant('DetCon.id')]: element.id ? element.id : '0',
          [this.translateService.instant('DetCon.desimp')]: element.descrizione ? element.descrizione : '-',
          [this.translateService.instant('DetCon.codtit')]: element.titolo ? element.titolo : '-',
          [this.translateService.instant('DetCon.codmacMac')]: element.macro ? element.macro : '-',
          [this.translateService.instant('DetCon.codmis')]: element.missione ? element.missione : '0',
          [this.translateService.instant('DetCon.impprv')]: element.previsione ? element.previsione : '0',
          [this.translateService.instant('DetCon.impass')]: element.assestato ? element.assestato : '0',
          [this.translateService.instant('DetCon.impimpAcc')]: element.impegnato ? element.impegnato : '0',
          [this.translateService.instant('DetCon.impprv1')]: element.previsione_1 ? element.previsione_1 : '0',
          [this.translateService.instant('DetCon.impass1')]: element.assestato_1 ? element.assestato_1 : '0',
          [this.translateService.instant('DetCon.impimpAcc1')]: element.impegnato_1 ? element.impegnato_1 : '0',
          [this.translateService.instant('DetCon.impprv2')]: element.previsione_2 ? element.previsione_2 : '0',
          [this.translateService.instant('DetCon.impass2')]: element.assestato_2 ? element.assestato_2 : '0',
          [this.translateService.instant('DetCon.impimpAcc2')]: element.impegnato_2 ? element.impegnato_2 : '0',
        };
        items.push(csvLine);
      });
    } else {
      queryResult?.forEach(element => {
        const csvLine: any = {
          [this.translateService.instant('DetCon.id')]: element.id ? element.id : '0',
          [this.translateService.instant('DetCon.desimp')]: element.descrizione ? element.descrizione : '-',
          [this.translateService.instant('DetCon.codtit')]: element.titolo ? element.titolo : '-',
          [this.translateService.instant('DetCon.codmacTip')]: element.macro ? element.macro : '-',
          [this.translateService.instant('DetCon.codmis')]: element.missione ? element.missione : '-',
          [this.translateService.instant('DetCon.codprg')]: element.programma ? element.programma : '-',
          [this.translateService.instant('DetCon.codmacTip')]: element.macro ? element.macro : '-',
          [this.translateService.instant('DetCon.codmis')]: element.missione ? element.missione : '0',
          [this.translateService.instant('DetCon.impprv')]: element.previsione ? element.previsione : '0',
          [this.translateService.instant('DetCon.impass')]: element.assestato ? element.assestato : '0',
          [this.translateService.instant('DetCon.impimp')]: element.impegnato ? element.impegnato : '0',
          [this.translateService.instant('DetCon.impprv1')]: element.previsione_1 ? element.previsione_1 : '0',
          [this.translateService.instant('DetCon.impass1')]: element.assestato_1 ? element.assestato_1 : '0',
          [this.translateService.instant('DetCon.impimp1')]: element.impegnato_1 ? element.impegnato_1 : '0',
          [this.translateService.instant('DetCon.impprv2')]: element.previsione_2 ? element.previsione_2 : '0',
          [this.translateService.instant('DetCon.impass2')]: element.assestato_2 ? element.assestato_2 : '0',
          [this.translateService.instant('DetCon.impimpAcc2')]: element.impegnato_2 ? element.impegnato_2 : '0',
        };
        items.push(csvLine);
      });
    }
    return items;
  }
}
