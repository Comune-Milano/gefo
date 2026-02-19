import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
import { IDdr } from '../../models/ddr.model';
import { ProBanResponse } from '../../models/bandi.model';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-ddr',
  templateUrl: './ddr.component.html',
  styleUrls: ['./ddr.component.scss'],
})
export class DdrComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: IDdr[];
  pathDdra!: string;
  viewDelete = true;
  countFiltri: number;
  ddr!: IDdr[];
  ddrNoSort!: Array<ProDdrResponse>;
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  progetti: Array<any> = [];
  tipiFin: Array<any> = [];
  direzioni: Array<any> = [];
  ddra: Array<any> = [];
  ddrSelezionati: Array<number> = [];
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    protected router: Router,
    protected ddrService: DdrService,
    protected dirService: DirCentraleService,
    protected tipFinService: TipFinService,
    protected temService: ProTemService,
    protected rolesCheckService: RolesCheckService,
    protected progService: ProgService,
    private ddraService: DdraService,
    protected exportTableCsvService: ExportTableCsvService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/ddr';
    this.countFiltri = 0;
  }

  //ricerca con autocomplete dei progetti
  searchPro: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 2 ? [] : this.progService.ricercaProgettiAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei progetti nell'autocomplete searchPro
  formatterPro = (x: { codpro: string; despro: string; id: number }) => {
    if (x.id) {
      return `${x.codpro + ' - ' + x.despro}`;
    }
    return '';
  };

  //ricerca con autocomplete dei tipi finanziamento
  searchTipFin: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 2 ? [] : this.tipFinService.ricercaTipoFinanziamentoAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei tipiFinanziamento nell'autocomplete searchTipFin
  formatterTipFin = (x: { destipfin: string; codtipfin: string; id: number }) => {
    if (x.id) {
      return `${x.codtipfin + ' - ' + x.destipfin}`;
    }
    return '';
  };

  //ricerca con autocomplete delle direzioni
  searchDir: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 2 ? [] : this.dirService.getDirezioniAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle direzioni nell'autocomplete searchDir
  formatterDir = (x: { desdir: string; coddir: string; id: number }) => {
    if (x.id) {
      return `${x.coddir + ' - ' + x.desdir}`;
    }
    return '';
  };

  //ricerca con autocomplete delle Ddra
  searchDdra: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 2 ? [] : this.ddraService.ricercaDdraAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle Ddra
  formatterDdra = (x: { desddra: string; codddra: string; id: number }) => {
    if (x.id) {
      return `${x.codddra + ' - ' + x.desddra}`;
    }
    return '';
  };

  ngOnInit(): void {
    const jsog = new JsogService();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.pathDdra = this.activatedRoute.snapshot.routeConfig?.path ? this.activatedRoute.snapshot.routeConfig.path : '';
    this.warningMessage = '';
    this.formFilter = this._fb.group({
      autocomplete: [''],
      progetto: [null],
      idProgetto: [null],
      tipLiv: [''],
      tipoFinanziamento: [null],
      tipFin: [null],
      idDir: [null],
      direzione: [null],
      DdraObj: [null],
      ddra: [null],
      flgNoDdra: ['N'],
      flgControl: [false],
    });

    console.log(this.activatedRoute.snapshot.params);
    if (this.pathDdra === 'ElencoDdr') {
      this.ddraService
        .getDdra(this.activatedRoute.snapshot.params.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(res => {
          this.formFilter.get('ddra')?.setValue(res.ddra.id);
          this.formFilter.get('DdraObj')?.setValue(res.ddra);
          this.countFiltriApplicati();
          this.ricercaServiceDdr();
        });
    } else if (this.pathDdra === 'AggiungiDdr') {
      this.formFilter.get('flgNoDdra')?.setValue('S');
      this.formFilter.get('flgControl')?.setValue(true);
      this.countFiltriApplicati();
      this.ricercaServiceDdr();
    } else {
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
          switchMap(value => this.ddrService.ricercaDdr(this.formFilter.value))
        )
        .subscribe(
          (res: any) => {
            this.ddr = <IDdr[]>jsog.deserialize(res);
            //console.log(this.ddr);
            this.ddrNoSort = JSON.parse(JSON.stringify(this.ddr));
            this._exportObj = <IDdr[]>jsog.deserialize(res);
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
      if (this.activatedRoute.snapshot.data.previousPath === '/gest-progetti') {
        this.isFetching = true;
        this.progService
          .getProgetto(this.activatedRoute.snapshot.params.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(res => {
            this.formFilter.get('progetto')?.setValue(res.progetto);
            this.applicaFiltri();
          });
      } else {
        this.isFetching = true;
        this.applicaFiltri();
      }
    }
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  ricercaServiceDdr(): void {
    const jsog = new JsogService();
    this.isFetching = true;
    this.ddrService
      .ricercaDdr(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.ddr = <IDdr[]>jsog.deserialize(res);
          //console.log(this.ddr);
          this.ddrNoSort = JSON.parse(JSON.stringify(this.ddr));
          this._exportObj = <IDdr[]>jsog.deserialize(res);
          this.isFetching = false;
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
      this.ddr = JSON.parse(JSON.stringify(this.ddrNoSort));
    } else {
      this.ddr = this.ddr.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'prog':
            if (a.proProByIdPro.codpro != null && b.proProByIdPro.codpro != null) {
              res = compare(a.proProByIdPro['codpro'], b.proProByIdPro['codpro']);
            } else {
              res = a.proProByIdPro.codpro == null ? 1 : -1;
            }
            break;
          case 'ddra':
            if (a.proDdraByIdDdra?.codddra != null && b.proDdraByIdDdra?.codddra != null) {
              res = compare(a.proDdraByIdDdra['codddra'], b.proDdraByIdDdra['codddra']);
            } else {
              res = a.proDdraByIdDdra?.codddra == null ? 1 : -1;
            }
            break;
          default:
            res = compare(a[column], b[column]);
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
    const tipFinObj = this.formFilter.get('tipFin')?.value;
    const progettoObj = this.formFilter.get('progetto')?.value;
    const direzioneObj = this.formFilter.get('direzione')?.value;
    const ddraObj = this.formFilter.get('ddra')?.value;
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.formFilter.patchValue({
      tipoFinanziamento: this.formFilter.get('tipFin')?.value != null ? (this.formFilter.get('tipFin')?.value).id : null,
      idProgetto: this.formFilter.get('progetto')?.value != null ? (this.formFilter.get('progetto')?.value).id : null,
      direzione: this.formFilter.get('direzione')?.value != null ? (this.formFilter.get('direzione')?.value).id : null,
      ddra: this.formFilter.get('DdraObj')?.value != null ? (this.formFilter.get('DdraObj')?.value).id : null,
    });
    this.ricercaServiceDdr();
    this.formFilter.patchValue({
      tipFin: tipFinObj,
      progetto: progettoObj,
      direzione: direzioneObj,
      ddra: ddraObj,
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
        controlFilter !== 'flgControl' &&
        controlFilter !== 'idProgetto' &&
        controlFilter !== 'ddra'
      ) {
        this.countFiltri++;
      }
    }
  }

  initSearchFilterForm(): void {
    super.initSearchFilterForm();
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formFilter.controls[elemento];
  }

  ricercaDdr(): void {
    if (this.getFormElement('autocomplete').value.length > 1 || this.getFormElement('autocomplete').value.length === 0) {
      this.applicaFiltri();
    }
  }

  onFlgControlChange(e: any): void {
    if (e.target.checked) {
      this.formFilter.get('flgNoDdra')?.setValue('S');
    } else {
      this.formFilter.get('flgNoDdra')?.setValue('N');
    }
  }
  onCheckboxChange(e: any): void {
    if (e.target.checked) {
      this.ddrSelezionati.push(Number(e.target.value));
    } else {
      const index = this.ddrSelezionati.findIndex(x => x.valueOf === e.target.value);
      this.ddrSelezionati.splice(index, 1);
    }
  }

  nuovoDdr(): void {
    this.router.navigate(['new'], { relativeTo: this.$activatedRoute });
  }

  createDdrToAddObj(): any {
    return {
      ddra: this.activatedRoute.snapshot.params.id,
      ddrs: this.ddrSelezionati,
    };
  }

  aggiungiDdr(): void {
    this.ddraService
      .associaDdrADdra(this.createDdrToAddObj())
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          super.setOpDelete(true);
          this.alertService.testo = res.userMessage;
          this.alertService.showAlert = true;
          this.ngOnInit();
        },
        err => {
          this.showAlertError(err);
        }
      );
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`${id}/viewDetail`], { relativeTo: this.$activatedRoute });
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('ddr.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  resetFilter(): void {
    this.formFilter.patchValue({
      progetto: null,
      tipLiv: '',
      tipFin: null,
      direzione: null,
      ddra: null,
      flgnoddra: 'N',
    });
    this.formFilter.enable();
    this.countFiltri = 1;
  }

  private convertDataCSV(queryResult?: IDdr[]): any[] {
    const items: any[] = [];
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('ddr.codddr')]: element.codddr ? element.codddr : '',
        [this.translateService.instant('ddr.desddr')]: element.desddr ? element.desddr : '',
        [this.translateService.instant('ddr.id_prog')]: element.proProByIdPro.codpro
          ? element.proProByIdPro.codpro + ' - ' + element.proProByIdPro.despro
          : '',
        [this.translateService.instant('ddr.dtaddr')]: element.dtaddr,
        [this.translateService.instant('ddr.impddr')]: element.impddr ? this.transformValueImporto(element.impddr) : '0,00',
        [this.translateService.instant('ddr.id_ddra')]: element.proDdraByIdDdra
          ? element.proDdraByIdDdra.codddra + ' - ' + element.proDdraByIdDdra.desddra
          : '-',
      };
      items.push(csvLine);
    });
    return items;
  }
}
