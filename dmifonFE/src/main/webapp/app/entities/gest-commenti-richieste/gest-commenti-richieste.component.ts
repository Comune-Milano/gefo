import { Component, OnInit, ViewChildren, QueryList, Injector, HostListener } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, take, takeUntil } from 'rxjs';
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
import { GestCommRicService } from '../../services/gest-comm-ric.service';
import { CommRicResponse, ICommRic } from '../../models/comm-ric.model';
import { IGestioneProgetti, ProProgResponse } from '../../models/gest_prog.model';
import { UsersService } from '../../services/users.service';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-gest-comm-ric',
  templateUrl: './gest-commenti-richieste.component.html',
  styleUrls: ['./gest-commenti-richieste.component.scss'],
})
export class GestCommentiRichiesteComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: Array<CommRicResponse>;
  countFiltri: number;
  statoPrev: boolean;
  tipFinanziamenti$: Observable<ItipiFinanziamento[]> | undefined;
  previsioni!: Array<IPrevisioni>;
  richieste!: Array<CommRicResponse>;
  richiesteNoSort!: Array<CommRicResponse>;
  staricVal;
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  innerWidth!: any;
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
    protected usersService: UsersService,
    protected previsioniService: GestPrevisioniService,
    protected commRicService: GestCommRicService,
    protected exportTableCsvService: ExportTableCsvService,
    protected translateService: TranslateService,
    protected rolesCheckService: RolesCheckService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/diz-macro-prog';
    this.countFiltri = 0;
    this.statoPrev = false;
    this.staricVal = new Map([
      ['INI', 'Iniziale'],
      ['INV', 'Richiesta inviata'],
      ['ESE', 'Richiesta eseguita'],
      ['RES', 'Richiesta respinta'],
      ['ANN', 'Richiesta annullata'],
    ]);
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

  //ricerca con autocomplete degli utenti
  searchUte: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.usersService.getUtentiAutocomplete(term)))
    );
  };

  //gestisce la formattazione degli utenti nell'autocomplete searchUte
  formatterUte = (x: { username: string; nome: string; cognome: string; id: number }) => {
    if (x.id) {
      return `${x.username + ' - ' + x.cognome + ' ' + x.nome}`;
    }
    return '';
  };

  //ricerca con autocomplete delle richieste
  searchRic: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.commRicService.ricercaRichiestaAutocomplete(term)))
    );
  };

  //gestisce la formattazione degli utenti nell'autocomplete searchRic
  formatterRic = (x: { id: number; desric: string }) => {
    if (x.id) {
      return `${x.desric}`;
    }
    return '';
  };

  @HostListener('window:resize', ['$event'])
  onResize(event: any): any {
    this.innerWidth = window.innerWidth;
  }

  ngOnInit(): void {
    const jsog = new JsogService();
    this.innerWidth = window.innerWidth;
    this.warningMessage = '';
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.formFilter = this._fb.group({
      autocomplete: [''],
      tipo: ['R'],
      stato: ['INV'],
      idProgetto: [''],
      progetto: [null],
      tipoFinanziamento: [null],
      tipFin: [null],
      utenteAuto: [null],
      utente: [null],
      proRicPadre: [null],
      proRicPad: [null],
      dataPreDa: [null],
      dataPreA: [null],
      tipLiv: [''],
    });
    if (this.activatedRoute.snapshot.data.previousPath === '/gest-progetti') {
      this.isFetching = true;
      this.progService
        .getProgetto(<string>this.activatedRoute.snapshot.paramMap.get('id'))
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          resPro => {
            this.formFilter.get('stato')?.setValue('');
            this.formFilter.get('idProgetto')?.setValue(resPro.progetto.id);
            this.formFilter.get('progetto')?.setValue(resPro.progetto);
            this.formFilter.get('tipric')?.setValue('C');
            this.formFilter.get('utente')?.setValue(this.formFilter.get('utenteAuto')?.value?.id);
            this.formFilter.get('proRicPad')?.setValue(this.formFilter.get('ProRicPadre')?.value?.id);
            this.countFiltriApplicati();
            this.commRicService.ricercaRichiesta(this.formFilter.value).subscribe(
              res => {
                this.richieste = <CommRicResponse[]>jsog.deserialize(res);
                this._exportObj = this.richieste;
                this.isFetching = false;
              },
              err => {
                this.checkErrorStatus(err);
                this.showAlertError(err);
                this.isFetching = false;
              }
            );
          },
          error => {
            this.checkErrorStatus(error);
            this.showAlertError(error);
            this.isFetching = false;
          }
        );
    } else {
      this.isFetching = true;
      this.formFilter.get('utenteAuto')?.setValue(JSON.parse(localStorage.getItem('curUte')));
      this.formFilter.get('utente')?.setValue(this.formFilter.get('utenteAuto')?.value?.id);
      this.countFiltriApplicati();
      this.commRicService
        .ricercaRichiesta(this.formFilter.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            this.richieste = <CommRicResponse[]>jsog.deserialize(res);
            this.richiesteNoSort = this.richieste;
            this._exportObj = this.richieste;
            this.isFetching = false;
          },
          error => {
            this.checkErrorStatus(error);
            this.showAlertError(error);
            this.isFetching = false;
          }
        );
    }
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        takeUntil(this.ngUnsubscribe),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap((value: any) => this.commRicService.ricercaRichiesta(this.formFilter.value))
      )
      .subscribe(
        (res: any) => {
          //console.log(res.progetti.length);
          this.richieste = <CommRicResponse[]>jsog.deserialize(res);
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

  ricercaRichiesta(): void {
    const jsog = new JsogService();
    this.isFetching = true;
    this.commRicService.ricercaRichiesta(this.formFilter.value).subscribe(
      res => {
        this.richieste = <CommRicResponse[]>jsog.deserialize(res);
        this.richiesteNoSort = this.richieste;
        this._exportObj = this.richieste;
        this.isFetching = false;
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
      this.richieste = this.richiesteNoSort;
    } else {
      this.richieste = this.richieste.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'prog':
            if (a.proRic.proProByIdPro != null && b.proRic.proProByIdPro != null) {
              res = compare(a.proRic.proProByIdPro['codpro'], b.proRic.proProByIdPro['codpro']);
            } else {
              res = a.proRic.proProByIdPro.codpro == null ? 1 : -1;
            }
            break;
          case 'username':
            if (a.username != null && b.username != null) {
              res = compare(a.username, b.username);
            } else {
              res = a.username == null ? 1 : -1;
            }
            break;
          default:
            res = compare(a.proRic[column], b.proRic[column]);
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

  nuovoCommRic(): void {
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
        controlFilter !== 'utente' &&
        controlFilter !== 'idProgetto'
      ) {
        this.countFiltri++;
      }
    }
  }

  getStaRicValue(key: string): string {
    return <string>this.staricVal.get(key);
  }

  resetFilter(): void {
    this.formFilter.reset();
  }

  applicaFiltri(): void {
    this.formFilter.get('utente')?.setValue(this.formFilter.get('utenteAuto')?.value?.id);
    this.formFilter.get('tipoFinanziamento')?.setValue(this.formFilter.get('tipFin')?.value?.id);
    this.formFilter.get('idProgetto')?.setValue(this.formFilter.get('progetto')?.value?.id);
    this.formFilter.get('proRicPad')?.setValue(this.formFilter.get('ProRicPadre')?.value?.id);
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.ricercaRichiesta();
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('commRic.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  private convertDataCSV(queryResult?: CommRicResponse[]): any[] {
    const items: any[] = [];
    console.log(this.exportObj);
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('commRic.idPro')]: element.proRic.proProByIdPro
          ? element.proRic.proProByIdPro.codpro + ' - ' + element.proRic.proProByIdPro.despro
          : '-',
        [this.translateService.instant(this.formFilter.get('tipric')?.value === 'R' ? 'commRic.tipRicR' : 'commRic.tipRicC')]: element
          .proRic.tipric
          ? element.proRic.tipric
          : ' - ',
        [this.translateService.instant('commRic.desric')]: element.proRic.desric ? element.proRic.desric : ' - ',
        [this.translateService.instant('commRic.user')]: element.username ? element.username : ' - ',
        [this.translateService.instant('commRic.staric')]: element.proRic.staric ? element.proRic.staric : ' - ',
        [this.translateService.instant('commRic.dtasca')]: element.proRic.dtasca ? element.proRic.dtasca : ' - ',
        [this.translateService.instant('commRic.risric')]: element.proRic.risric ? element.proRic.risric : ' - ',
      };
      items.push(csvLine);
    });
    return items;
  }
}
