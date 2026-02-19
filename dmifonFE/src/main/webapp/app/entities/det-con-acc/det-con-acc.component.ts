import { Component, OnInit, ViewChildren, QueryList, Injector, Input } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { map, Observable, of, Subject, takeUntil } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';

import { TranslateService } from '@ngx-translate/core';
import { ProgService } from 'app/services/gest-progetti.service';
import { IGestioneProgetti, ProProgResponse } from 'app/models/gest_prog.model';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { StatProService } from '../../services/statpro.service';
import { StaFinService } from '../../services/stafin.service';
import { DetConService } from '../../services/detcon.service';
import { IproEntcon } from '../../models/proEntcon.model';
import { Output, EventEmitter } from '@angular/core';
import { BatchService } from '../../services/batch.service';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-det-con-acc',
  templateUrl: './det-con-acc.component.html',
  styleUrls: ['./det-con-acc.component.scss'],
})
export class DetConAccComponent extends SearchPage implements OnInit {
  @ViewChildren(NgbdSortableHeaderDirective) headers!: QueryList<NgbdSortableHeaderDirective>;
  _rolesObj!: IGestioneProgetti;
  viewAlert = true;
  viewDelete = true;
  viewError = true;
  countFiltri: number;
  accertamenti = false;
  prog = false;
  entCon: IproEntcon[] = [];
  entConNoSort: IproEntcon[] = [];
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  id = 0;
  @Input() tipo?: string;
  @Output() codAccImp = new EventEmitter<string>();
  shPanAcc = false;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    protected router: Router,
    private activatedRoute: ActivatedRoute,
    protected progService: ProgService,
    protected detConService: DetConService,
    protected statProService: StatProService,
    protected staFinService: StaFinService,
    protected batchService: BatchService,
    protected exportTableCsvService: ExportTableCsvService,
    // private alertService: AlertService,
    protected translateService: TranslateService,
    protected rolesCheckService: RolesCheckService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/gest-progetti';
    this.countFiltri = 0;
  }

  ngOnInit(): void {
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    if (this.tipo === 'Accertamenti') {
      this.accertamenti = true;
    } else if (this.tipo === 'Impegni') {
      this.accertamenti = false;
    } else {
      if (this.activatedRoute.snapshot.data.tipo === 'Accertamenti') {
        this.accertamenti = true;
        this.prog = false;
      } else {
        this.prog = false;
      }
      if (this.activatedRoute.snapshot.url[0]?.path === 'det-con-acc') {
        this.accertamenti = true;
        this.prog = true;
        this.id = this.activatedRoute.snapshot.params.id;
      } else if (this.activatedRoute.snapshot.url[0]?.path === 'det-con-imp') {
        this.accertamenti = false;
        this.prog = true;
        this.id = this.activatedRoute.snapshot.params.id;
      }
    }
    this.warningMessage = '';
    const jsog = new JsogService();
    this.formFilter = this._fb.group({
      id: [null],
      descrizione: [null],
      id_capitolo: [null],
      id_crono: [null],
      cup: [null],
      cig: [null],
      anno: [null],
    });
    if (this.prog) {
      this.ricercaImpegniAccertamenti();
    } else {
      this.formFilter.get('anno')?.setValue(new Date().getFullYear().toString());
    }
    super.initSearchFilterForm();
    this.formFilter.get('id')?.valueChanges.subscribe(res => {
      if (this.formFilter.get('id')?.value != null && this.formFilter.get('id')?.value !== '') {
        this.formFilter.get('descrizione')?.reset();
        this.formFilter.get('descrizione')?.disable();
        this.formFilter.get('id_capitolo')?.reset();
        this.formFilter.get('id_capitolo')?.disable();
        this.formFilter.get('id_crono')?.reset();
        this.formFilter.get('id_crono')?.disable();
        this.formFilter.get('cup')?.reset();
        this.formFilter.get('cup')?.disable();
        this.formFilter.get('cig')?.reset();
        this.formFilter.get('cig')?.disable();
        this.formFilter.get('anno')?.reset();
        this.formFilter.get('anno')?.disable();
      } else {
        this.formFilter.get('id')?.setValue('');
        this.formFilter.get('descrizione')?.enable();
        this.formFilter.get('id_capitolo')?.enable();
        this.formFilter.get('id_crono')?.enable();
        this.formFilter.get('cup')?.enable();
        this.formFilter.get('cig')?.enable();
        this.formFilter.get('anno')?.enable();
      }
    });
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  //ricerca degli impegni/accertamenti - impegni/accertamenti progetto
  ricercaImpegniAccertamenti(): void {
    this.isFetching = true;
    this.entCon = [];
    if (this.accertamenti) {
      if (this.prog) {
        this.detConService
          .ricercaAccertamentiProgetto(this.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              this.entCon = res;
              this.entConNoSort = JSON.parse(JSON.stringify(res));
              this.isFetching = false;
            },
            err => {
              this.checkErrorStatus(err);
              this.showAlertError(err);
              this.isFetching = false;
            }
          );
      } else {
        if (this.formFilter.get('id')?.value !== '' && this.formFilter.get('id')?.value !== null) {
          this.detConService
            .getAccertamento(this.formFilter.get('id')?.value)
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(
              res => {
                this.entCon.push(res);
                this.entConNoSort = JSON.parse(JSON.stringify(res));
                this.isFetching = false;
              },
              err => {
                this.checkErrorStatus(err);
                this.showAlertError(err);
                this.isFetching = false;
              }
            );
        } else {
          this.detConService
            .ricercaAccertamenti(this.formFilter.value)
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(
              res => {
                this.entCon = res;
                this.entConNoSort = JSON.parse(JSON.stringify(res));
                this.isFetching = false;
              },
              err => {
                this.checkErrorStatus(err);
                this.showAlertError(err);
                this.isFetching = false;
              }
            );
        }
      }
    } else {
      if (this.prog) {
        this.detConService
          .ricercaImpegniProgetto(this.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              this.entCon = res;
              this.entConNoSort = JSON.parse(JSON.stringify(res));
              this.isFetching = false;
            },
            err => {
              this.checkErrorStatus(err);
              this.showAlertError(err);
              this.isFetching = false;
            }
          );
      } else {
        if (this.formFilter.get('id')?.value !== '' && this.formFilter.get('id')?.value !== null) {
          this.detConService
            .getImpegno(this.formFilter.get('id')?.value)
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(res => {
              this.entCon.push(res);
              this.entConNoSort = JSON.parse(JSON.stringify(res));
              this.isFetching = false;
            });
        } else {
          this.detConService
            .ricercaImpegni(this.formFilter.value)
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(
              res => {
                this.entCon = res;
                this.entConNoSort = JSON.parse(JSON.stringify(res));
                this.isFetching = false;
              },
              err => {
                this.checkErrorStatus(err);
                this.showAlertError(err);
                this.isFetching = false;
              }
            );
        }
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

  //metodo che applica i filtri
  applicaFiltri(): void {
    // this.countFiltri = 0;
    this.shPanAcc = false;
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.ricercaImpegniAccertamenti();
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

  apriDettaglio(id: number): void {
    this.router.navigate([`gest-progetti/${id}/viewDetail`]);
  }

  setCodiceAccImp(codice: string): void {
    this.codAccImp.emit(codice);
  }

  exportToCSV(): void {
    if (this.accertamenti) {
      this.exportTableCsvService.exportTableToCsv(this.translateService.instant('DetCon.csvFileNameAcc'), this.convertDataCSV(this.entCon));
    } else {
      this.exportTableCsvService.exportTableToCsv(this.translateService.instant('DetCon.csvFileNameImp'), this.convertDataCSV(this.entCon));
    }
  }

  aggiornamentoEntitaContabili(): void {
    this.batchService.setIsUpdating(true);
    this.alertService.showWarning = true;
    this.showAlertWarning({ userMessage: "Aggiornamento entità contabili in corso. L'operazione richiederà qualche minuto." });
    this.batchService
      .aggiornamentoEntitaContabili()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => this.batchService.setIsUpdating(false),
        error => {
          this.checkErrorStatus(error);
          this.showAlertError(error);
          this.batchService.setIsUpdating(false);
        }
      );
  }

  resetFilter(): void {
    this.formFilter.patchValue({
      id: null,
      descrizione: null,
      id_capitolo: null,
      id_crono: null,
      cup: null,
      cig: null,
    });
    this.shPanAcc = false;
    this.formFilter.enable();
    this.countFiltri = 0;
  }

  showFilterAcc(): void {
    this.shPanAcc = !this.shPanAcc;
  }

  private convertDataCSV(queryResult?: IproEntcon[]): any[] {
    const items: any[] = [];
    if (this.accertamenti) {
      queryResult?.forEach(element => {
        const csvLine: any = {
          [this.translateService.instant('DetCon.id')]: element.codentcon ? element.codentcon : '0',
          [this.translateService.instant('DetCon.desimp')]: element.desimp ? element.desimp : '-',
          [this.translateService.instant('DetCon.idcap')]: element.idcap ? element.idcap : '-',
          [this.translateService.instant('DetCon.impass')]: element.impass ? element.impass : '0',
          [this.translateService.instant('DetCon.impeco')]: element.impeco ? element.impeco : '0',
          [this.translateService.instant('DetCon.impmanMan')]: element.impman ? element.impman : '0',
          [this.translateService.instant('DetCon.despdd')]: element.despdd ? element.despdd : '-',
          [this.translateService.instant('DetCon.idcro')]: element.idcro ? element.idcro : '-',
          [this.translateService.instant('DetCon.cup')]: element.codcup ? element.codcup : '-',
          [this.translateService.instant('DetCon.cig')]: element.codcig ? element.codcig : '-',
          [this.translateService.instant('DetCon.nroconapp')]: element.nroconapp ? element.nroconapp : '0',
          [this.translateService.instant('DetCon.codgami')]: element.codgami ? element.codgami : '-',
        };
        items.push(csvLine);
      });
    } else {
      queryResult?.forEach(element => {
        const csvLine: any = {
          [this.translateService.instant('DetCon.id')]: element.codentcon ? element.codentcon : '0',
          [this.translateService.instant('DetCon.desimp')]: element.desimp ? element.desimp : '-',
          [this.translateService.instant('DetCon.idcap')]: element.idcap ? element.idcap : '-',
          [this.translateService.instant('DetCon.impass')]: element.impass ? element.impass : '0',
          [this.translateService.instant('DetCon.impeco')]: element.impeco ? element.impeco : '0',
          [this.translateService.instant('DetCon.impliq')]: element.impliq ? element.impliq : '0',
          [this.translateService.instant('DetCon.impmanRev')]: element.impman ? element.impman : '0',
          [this.translateService.instant('DetCon.despdd')]: element.despdd ? element.despdd : '-',
          [this.translateService.instant('DetCon.idcro')]: element.idcro ? element.idcro : '-',
          [this.translateService.instant('DetCon.codcup')]: element.codcup ? element.codcup : '-',
          [this.translateService.instant('DetCon.codcig')]: element.codcig ? element.codcig : '-',
          [this.translateService.instant('DetCon.nroconapp')]: element.nroconapp ? element.nroconapp : '0',
          [this.translateService.instant('DetCon.codgami')]: element.codgami ? element.codgami : '-',
        };
        items.push(csvLine);
      });
    }
    return items;
  }
}
