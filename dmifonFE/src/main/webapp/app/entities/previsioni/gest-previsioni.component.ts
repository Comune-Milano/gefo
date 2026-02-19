import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { ItipiFinanziamento } from 'app/models/tipiFinanziamento.model';
import { TranslateService } from '@ngx-translate/core';
import { IMacroProgetti, ProMacroProgResponse } from 'app/models/diz_macro_prog.model';
import { DizMacroProgService } from 'app/services/diz-macro-prog.service';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { GestPrevisioniService } from '../../services/gest-previsioni.service';
import { ProgService } from '../../services/gest-progetti.service';
import { IPrevisioni } from '../../models/previsioni.model';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './gest-previsioni.component.html',
  styleUrls: ['./gest-previsioni.component.scss'],
})
export class GestPrevisioniComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: Array<IPrevisioni>;
  countFiltri: number;
  statoPrev: boolean;
  tipFinanziamenti$: Observable<ItipiFinanziamento[]> | undefined;
  macroProgetti!: Array<ProMacroProgResponse>;
  previsioni!: Array<IPrevisioni>;
  previsioniNoSort!: Array<IPrevisioni>;
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    protected router: Router,
    private activatedRoute: ActivatedRoute,
    protected dirService: DirCentraleService,
    protected tipFinService: TipFinService,
    protected macroProgService: DizMacroProgService,
    protected progService: ProgService,
    protected previsioniService: GestPrevisioniService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/diz-macro-prog';
    this.countFiltri = 0;
    this.statoPrev = false;
  }

  //ricerca con autocomplete dei progetti
  searchPro: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
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
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.tipFinService.ricercaTipoFinanziamentoAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei ruoli nell'autocomplete searchRuo
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
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
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

  ngOnInit(): void {
    const jsog = new JsogService();
    this.warningMessage = '';
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.formFilter = this._fb.group({
      idProgetto: [''],
      progetto: [null],
      tipoFinanziamento: [null],
      tipFin: [null],
      direzione: [null],
      dir: [null],
      dataPreDa: [null],
      dataPreA: [null],
      tipLiv: [''],
    });
    this.isFetching = true;
    if (this.activatedRoute.snapshot.data.previousPath === '/gest-progetti') {
      this.formFilter.get('idProgetto')?.setValue(Number(this.activatedRoute.snapshot.params.id));
      this.progService
        .getProgetto(this.activatedRoute.snapshot.params.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(res => {
          this.formFilter.get('progetto')?.setValue(res.progetto);
          this.previsioniService.getBloccaPrevisioni().subscribe(res => {
            this.statoPrev = res;
          });
          super.initSearchFilterForm(); // Recupero i filtri dallo storage
          this.countFiltriApplicati();
          this.previsioniService.ricercaPrevisione(this.formFilter.value).subscribe(res => {
            this.previsioni = res;
            this.previsioniNoSort = res;
            this._exportObj = res;
            this.isFetching = false;
          });
        });
    } else {
      this.previsioniService
        .getBloccaPrevisioni()
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(res => {
          this.statoPrev = res;
        });
      super.initSearchFilterForm(); // Recupero i filtri dallo storage
      this.previsioniService
        .ricercaPrevisione(this.formFilter.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(res => {
          this.previsioni = <IPrevisioni[]>jsog.deserialize(res);
          this.previsioniNoSort = <IPrevisioni[]>jsog.deserialize(res);
          this._exportObj = res;
          this.isFetching = false;
        });
    }
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    super.closeAlert();
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  ricercaPrevisioni(): void {
    const jsog = new JsogService();
    this.isFetching = true;
    this.previsioniService
      .ricercaPrevisione(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.previsioni = <IPrevisioni[]>jsog.deserialize(res);
          this.previsioniNoSort = this.previsioni; // Clone per ordinamento iniziale
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

  resetFiltri(): void {
    this.formFilter.patchValue({
      idProgetto: '',
      progetto: null,
      tipoFinanziamento: null,
      tipFin: null,
      direzione: null,
      dir: null,
      dataPreDa: null,
      dataPreA: null,
      tipLiv: '',
    });
    this.countFiltriApplicati();
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
      this.previsioni = this.previsioniNoSort;
    } else {
      this.previsioni = this.previsioni.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'prog':
            if (a.proProByIdPro != null && b.proProByIdPro != null) {
              res = compare(a.proProByIdPro['codpro'], b.proProByIdPro['codpro']);
            } else {
              res = a.proProByIdPro.codpro == null ? 1 : -1;
            }
            break;
          case 'codtipfinA':
            if (a.macroProgetto.proTipfinByIdTipfina != null && b.macroProgetto.proTipfinByIdTipfina != null) {
              res = compare(a.macroProgetto.proTipfinByIdTipfina['codtipfin'], b.macroProgetto.proTipfinByIdTipfina['codtipfin']);
            } else {
              res = a.macroProgetto.proTipfinByIdTipfina == null ? 1 : -1;
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

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formFilter.controls[elemento];
  }

  nuovaPrevisione(): void {
    this.router.navigate(['new'], { relativeTo: this.$activatedRoute });
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`${id}/viewDetail`], { relativeTo: this.$activatedRoute });
  }

  countFiltriApplicati(): void {
    this.countFiltri = 0;
    const controlsFilter = this.formFilter.controls;
    for (const controlFilter in controlsFilter) {
      if (
        controlsFilter[controlFilter].value != null &&
        controlsFilter[controlFilter].value !== '' &&
        controlFilter !== 'tipoFinanziamento' &&
        controlFilter !== 'direzione' &&
        controlFilter !== 'idProgetto'
      ) {
        this.countFiltri++;
      }
    }
  }

  applicaFiltri(): void {
    const tipFinObj = this.formFilter.get('tipFin')?.value;
    const progettoObj = this.formFilter.get('progetto')?.value;
    const direzioneObj = this.formFilter.get('dir')?.value;
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.formFilter.patchValue({
      tipoFinanziamento: this.formFilter.get('tipFin')?.value != null ? (this.formFilter.get('tipFin')?.value).id : null,
      idProgetto: this.formFilter.get('progetto')?.value != null ? (this.formFilter.get('progetto')?.value).id : null,
      direzione: this.formFilter.get('dir')?.value != null ? (this.formFilter.get('dir')?.value).id : null,
    });
    this.ricercaPrevisioni();
    this.formFilter.patchValue({
      tipFin: tipFinObj,
      progetto: progettoObj,
      dir: direzioneObj,
    });
  }

  onCheckboxChange(event: any): void {
    this.statoPrev = event.target.checked;
    this.previsioniService
      .bloccaSbloccaPrevisioni(this.statoPrev)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.testoAlert = res.userMessage;
          this.viewAlert = true;
          this.showAlertMessage(res);
        },
        err => {
          this.checkErrorStatus(err);
          this.showAlertError(err);
        }
      );
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('Prev.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  private convertDataCSV(queryResult?: IPrevisioni[]): any[] {
    const items: any[] = [];
    console.log(this.exportObj);
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('Prev.idProg')]: element.proProByIdPro.codpro
          ? element.proProByIdPro.codpro + ' - ' + element.proProByIdPro.despro
          : '-',
        [this.translateService.instant('Prev.dtapre')]: element.dtapre ? element.dtapre : ' - ',
        [this.translateService.instant('Prev.imppre')]: element.imppre ? element.imppre : '0',
        [this.translateService.instant('Prev.notpre')]: element.notpre ? element.notpre : ' - ',
      };
      items.push(csvLine);
    });
    return items;
  }
}
