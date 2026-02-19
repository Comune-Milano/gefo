import { Component, HostListener, Injector, OnInit, QueryList, ViewChildren } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { ItipiFinanziamento } from 'app/models/tipiFinanziamento.model';
import { TranslateService } from '@ngx-translate/core';
import { ProgService } from 'app/services/gest-progetti.service';
import { IGestioneProgetti, ProProgResponse } from 'app/models/gest_prog.model';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { DizMacroProgService } from '../../services/diz-macro-prog.service';
import { BandoService } from '../../services/bando.service';
import { StatProService } from '../../services/statpro.service';
import { StaFinService } from '../../services/stafin.service';
import { TematicheService } from 'app/services/tematiche.service';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { takeUntil } from 'rxjs/operators';
import { ProNilService } from '../../services/pronil.service';

@Component({
  selector: 'jhi-gest-prog',
  templateUrl: './gest-prog.component.html',
  styleUrls: ['./gest-prog.component.scss'],
})
export class GestProgComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: IGestioneProgetti;
  viewDelete: boolean = true;
  countFiltri: number;
  tipFinanziamenti$: Observable<ItipiFinanziamento[]> | undefined;
  progetti!: Array<ProProgResponse>;
  progettiNoSort!: Array<ProProgResponse>;
  highlightText: string = '';
  warningMessage?: string;
  isFetching: boolean = false;
  isFetching1: boolean = false;
  tipiFin: Array<any> = [];
  macroProgetti: Array<any> = [];
  bandi: Array<any> = [];
  tematiche: Array<any> = [];
  direzioni: Array<any> = [];
  innerWidth!: any;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    protected router: Router,
    protected dirService: DirCentraleService,
    protected tipFinService: TipFinService,
    protected macProService: DizMacroProgService,
    protected bandoService: BandoService,
    protected rolesCheckService: RolesCheckService,
    protected temService: TematicheService,
    protected progService: ProgService,
    protected statProService: StatProService,
    protected staFinService: StaFinService,
    protected exportTableCsvService: ExportTableCsvService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/gest-progetti';
    this.countFiltri = 0;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): any {
    this.innerWidth = window.innerWidth;
  }

  //ricerca con autocomplete dei tipi finanziamento
  searchTipFin: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 0 ? [] : this.tipFinService.ricercaTipoFinanziamentoAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei ruoli nell'autocomplete searchRuo
  formatterTipFin = (x: { destipfin: string; codtipfin: string; id: number }) => {
    if (x.id) {
      return `${x.codtipfin + ' - ' + x.destipfin}`;
    }
    return '';
  };

  //ricerca con autocomplete dei macro progetti
  searchMacPro: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 0 ? [] : this.macProService.ricercaMacroProgettiAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei macro progetti nell'autocomplete searchMacPro
  formatterMacPro = (x: { desmacpro: string; codmacpro: string; id: number }) => {
    if (x.id) {
      return `${x.codmacpro + ' - ' + x.desmacpro}`;
    }
    return '';
  };

  //ricerca con autocomplete delle direzioni
  searchDir: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 0 ? [] : this.dirService.getDirezioniAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle direzioni nell'autocomplete searchDir
  formatterDir = (x: { desdir: string; coddir: string; id: number }) => {
    if (x.id) {
      return `${x.coddir + ' - ' + x.desdir}`;
    }
    return '';
  };

  //ricerca con autocomplete dei bandi
  searchBan: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      map(term =>
        term.length < 0
          ? []
          : this.bandi.filter(
              (v: { desban: string; codban: string }) =>
                v.desban.toLowerCase().includes(term.toLowerCase()) || v.codban.toLowerCase().includes(term.toLowerCase())
            )
      )
    );
  };

  //gestisce la formattazione delle tematiche nell'autocomplete searchTem
  formatterBan = (x: { desban: string; codban: string; id: number }) => {
    if (x.id) {
      return `${x.codban + ' - ' + x.desban}`;
    }
    return '';
  };

  //ricerca con autocomplete delle tematiche
  searchTem: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => this.temService.ricercaTematicaAutocomplete(term))
    );
  };

  //gestisce la formattazione delle tematiche nell'autocomplete searchTem
  formatterTem = (x: { destem: string; id: number }) => {
    if (x.id) {
      return `${x.destem}`;
    }
    return '';
  };

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.warningMessage = '';
    const jsog = new JsogService();
    this.dirService.getDirezioni().subscribe(
      res => {
        this.direzioni = res;
      },
      err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
      }
    );
    this.macProService.ricercaMacroProgetto({ calcolaTotali: false }).subscribe(
      res => {
        res.macroProgetti.forEach((element: any) => {
          this.macroProgetti.push(element.macroProgetto);
        });
      },
      err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
      }
    );
    this.bandoService.ricercaBando({ calcolaTotali: false }).subscribe(
      res => {
        res.bandi.forEach((element: any) => {
          this.bandi.push(element.bando);
        });
      },
      err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
      }
    );
    this.temService.ricercaTematica({ autocomplete: '' }).subscribe(
      res => {
        this.tematiche = res;
      },
      err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
      }
    );
    this.statProService.getAllStatiProgetto().subscribe(
      (res: any) => {
        this.statPro = res;
      },
      err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
      }
    );
    this.staFinService.getAllStatiFinanziamento().subscribe(
      (res: any) => {
        this.statFin = res;
      },
      err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
      }
    );
    this.formFilter = this._fb.group({
      autocomplete: [''],
      tipoFinanziamento: [null],
      macroProgetto: [null],
      direzione: [null],
      bando: [null],
      tematica: [null],
      tipLiv: ['PB'],
      statoProgetto: [],
      statoFinanziario: [],
      calcolaTotali: true,
    });
    this.highlightText = this.formFilter.get('autocomplete')?.value;
    if (this.$activatedRoute.snapshot.params.idTipFin) {
      if (history.state.id) {
        this.formFilter
          .get('tipoFinanziamento')
          ?.setValue({ id: history.state.id, destipfin: history.state.destipfin, codtipfin: history.state.codtipfin });
      }
      this.formFilter.get('tipLiv')?.setValue('PB');
      super.initSearchFilterForm(); // Recupero i filtri dallo storage
      console.log(this.formFilter.value);
      this.applicaFiltri();
    } else {
      super.initSearchFilterForm(); // Recupero i filtri dallo storage
      console.log(this.formFilter.value);
      this.applicaFiltri();
    }
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap((value: any) => this.progService.ricercaProgetto(this.creaFormFilterObj())),
        takeUntil(this.ngUnsubscribe)
      )
      .subscribe(
        (res: any) => {
          //console.log(res.progetti.length);
          this.progetti = <ProProgResponse[]>jsog.deserialize(res.progetti);
          this.progettiNoSort = JSON.parse(JSON.stringify(this.progetti));
          this._exportObj = <IGestioneProgetti>jsog.deserialize(res);
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
    super.closeAlert(); //chiusura degli alert se aperti
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  creaFormFilterObj(): any {
    return {
      autocomplete: this.formFilter.get('autocomplete').value,
      tipoFinanziamento: this.formFilter.get('tipoFinanziamento').value?.id,
      macroProgetto: this.formFilter.get('macroProgetto').value?.id,
      direzione: this.formFilter.get('direzione').value?.id,
      bando: this.formFilter.get('bando').value?.id,
      tematica: this.formFilter.get('tematica').value?.id,
      tipLiv: this.formFilter.get('tipLiv').value,
      statoProgetto: this.formFilter.get('statoProgetto').value,
      statoFinanziario: this.formFilter.get('statoFinanziario').value,
      calcolaTotali: this.formFilter.get('calcolaTotali').value,
    };
  }

  ricercaProgetto(): void {
    const jsog = new JsogService();
    this.isFetching = true;
    this.progService
      .ricercaProgetto(this.creaFormFilterObj())
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.progetti = <ProProgResponse[]>jsog.deserialize(res.progetti);
          this.progettiNoSort = JSON.parse(JSON.stringify(this.progetti));
          this._exportObj = <IGestioneProgetti>jsog.deserialize(res);
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

  vaiAProcedura(codpro: string): void {
    this.formFilter.get('autocomplete').setValue(codpro);
    this.formFilter.get('tipLiv').setValue('BA');
    this.countFiltriApplicati();
    this.applicaFiltri();
  }

  tornaIndietroProcedura(): void {
    this.formFilter.get('autocomplete').setValue('');
    this.formFilter.get('tipLiv').setValue('PB');
    this.countFiltriApplicati();
    this.applicaFiltri();
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
      this.progetti = JSON.parse(JSON.stringify(this.progettiNoSort));
    } else {
      this.progetti = this.progetti.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'tipfin':
            if (a.progetto.proTipfinByIdTipfin != null && b.progetto.proTipfinByIdTipfin != null) {
              res = compare(a.progetto.proTipfinByIdTipfin['destipfin'], b.progetto.proTipfinByIdTipfin['destipfin']);
            } else {
              res = a.progetto.proTipfinByIdTipfin == null ? 1 : -1;
            }
            break;
          case 'imprisfon':
            res = compare(a.totali[column], b.totali[column]);
            break;
          case 'staprodessta':
            if (a.progetto.proStaproByIdStapro != null && b.progetto.proStaproByIdStapro != null) {
              res = compare(a.progetto.proStaproByIdStapro['dessta'], b.progetto.proStaproByIdStapro['dessta']);
            } else {
              res = a.progetto.proStaproByIdStapro == null ? 1 : -1;
            }
            break;
          case 'stafindessta':
            if (a.progetto.proStafinByIdStafin != null && b.progetto.proStafinByIdStafin != null) {
              res = compare(a.progetto.proStafinByIdStafin['dessta'], b.progetto.proStafinByIdStafin['dessta']);
            } else {
              res = a.progetto.proStafinByIdStafin == null ? 1 : -1;
            }
            break;
          default:
            res = compare(a.progetto[column], b.progetto[column]);
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
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.ricercaProgetto();
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

  getFormElement(elemento: string) {
    return this.formFilter.controls[elemento];
  }

  nuovoUtente(): void {
    this.router.navigateByUrl('/gest-progetti/new');
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`${id}/viewDetail`], { relativeTo: this.$activatedRoute });
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(
      this.translateService.instant('gestProg.csvFileName'),
      this.convertDataCSV(this._exportObj)
    );
  }

  resetFilter(): void {
    this.formFilter.patchValue({
      tipoFinanziamento: null,
      macroProgetto: null,
      direzione: null,
      bando: null,
      tematica: null,
      tipLiv: '',
      statoProgetto: '',
      statoFinanziario: '',
      calcolaTotali: true,
    });
    this.formFilter.enable();
    this.countFiltri = 0;
    this.ricercaProgetto();
  }

  private convertDataCSV(queryResult?: IGestioneProgetti): any[] {
    const items: any[] = [];
    queryResult?.progetti.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('gestProg.codpro')]: element.progetto.codpro ? element.progetto.codpro : '',
        [this.translateService.instant('gestProg.despro')]: element.progetto.despro ? element.progetto.despro : '',
        [this.translateService.instant('gestProg.tipfin')]: element.progetto.proTipfinByIdTipfin.destipfin
          ? element.progetto.proTipfinByIdTipfin.destipfin
          : '',
        [this.translateService.instant('gestProg.imprisfonpro')]: element.totali.imprisfonpro
          ? this.transformValueImporto(element.totali.imprisfonpro)
          : '0,00',
        [this.translateService.instant('gestProg.imprisfon')]: element.totali.imprisfon
          ? this.transformValueImporto(element.totali.imprisfon)
          : '0,00',
        [this.translateService.instant('gestProg.stateprog')]: element.progetto.proStaproByIdStapro.dessta
          ? element.progetto.proStaproByIdStapro.dessta
          : '-',
        [this.translateService.instant('gestProg.statefin')]: element.progetto.proStafinByIdStafin.dessta
          ? element.progetto.proStafinByIdStafin.dessta
          : '-',
      };
      items.push(csvLine);
    });
    return items;
  }
}
