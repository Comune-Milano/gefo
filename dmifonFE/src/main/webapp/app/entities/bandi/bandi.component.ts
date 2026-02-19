import { Component, OnInit, ViewChildren, QueryList, Injector, HostListener } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { TranslateService } from '@ngx-translate/core';
import { ProgService } from 'app/services/gest-progetti.service';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { IBandi, ProBanResponse } from '../../models/bandi.model';
import { DizMacroProgService } from '../../services/diz-macro-prog.service';
import { BandoService } from '../../services/bando.service';
import { StaBanService } from '../../services/staban.service';
import { ProTemService } from '../../services/protem.service';
import { StaFinService } from '../../services/stafin.service';
import { IStaBan } from 'app/models/staban.model';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-bandi',
  templateUrl: './bandi.component.html',
  styleUrls: ['./bandi.component.scss'],
})
export class BandiComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: IBandi;
  countFiltri: number;
  bandi!: Array<ProBanResponse>;
  bandiNoSort!: Array<ProBanResponse>;
  highlightText = '';
  warningMessage?: string;
  innerWidth = 0;
  isFetching = false;
  isFetching1 = false;
  tipiFin: Array<any> = [];
  statBan: IStaBan[] = [];
  tematiche: Array<any> = [];
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private formBuilder: FormBuilder,
    protected router: Router,
    protected dirService: DirCentraleService,
    protected tipFinService: TipFinService,
    protected macProService: DizMacroProgService,
    protected bandoService: BandoService,
    protected stabanService: StaBanService,
    protected temService: ProTemService,
    protected progService: ProgService,
    protected staFinService: StaFinService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/bandi';
    this.countFiltri = 0;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): any {
    this.innerWidth = window.innerWidth;
  }

  //ricerca con autocomplete dei tipi finanziamento
  searchTipFin: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.tipiFin.filter(
              (v: { destipfin: string; codtipfin: string }) =>
                v.destipfin.toLowerCase().includes(term.toLowerCase()) || v.codtipfin.toLowerCase().includes(term.toLowerCase())
            )
      )
    );
  };

  //gestisce la formattazione dei ruoli nell'autocomplete searchRuo
  formatterTipFin = (x: { destipfin: string; codtipfin: string; id: number }) => {
    if (x.id) {
      return `${x.codtipfin + ' - ' + x.destipfin}`;
    }
    return '';
  };

  //ricerca con autocomplete delle tematiche
  searchTem: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      map(term =>
        term.length < 2 ? [] : this.tematiche.filter((v: { destem: string }) => v.destem.toLowerCase().includes(term.toLowerCase()))
      )
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
    this.viewDelete = super.getOpDelete();
    this.innerWidth = window.innerWidth;
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    const jsog = new JsogService();
    this.tipFinService
      .ricercaTipoFinanziamento({ calcolaTotali: false })
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          res.tipiFinanziamento.forEach((element: any) => {
            this.tipiFin.push(element.tipoFinanziamento);
          });
        },
        err => {
          this.showAlertError(err);
        }
      );
    this.temService
      .ricercaTematica({ autocomplete: '' })
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.tematiche = res;
        },
        err => {
          this.showAlertError(err);
        }
      );
    this.stabanService
      .getAllStatiBando()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.statBan = res;
        },
        err => {
          this.showAlertError(err);
        }
      );
    this.formFilter = this._fb.group({
      autocomplete: [''],
      tipFin: [null],
      tem: [null],
      staBan: [],
      calcolaTotali: true,
    });
    super.initSearchFilterForm(); // Recupero i filtri dallo storage
    this.highlightText = this.formFilter.get('autocomplete')?.value;
    this.ricercaBando();
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        takeUntil(this.ngUnsubscribe),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap(value => this.bandoService.ricercaBando(this.formFilter.value))
      )
      .subscribe(
        (res: any) => {
          this.bandi = <ProBanResponse[]>jsog.deserialize(res.bandi);
          this.bandiNoSort = JSON.parse(JSON.stringify(this.bandi));
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

  ricercaBando(): void {
    const jsog = new JsogService();
    this.isFetching = true;
    this.bandoService
      .ricercaBando(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          //console.log(res.progetti.length);
          this.bandi = <ProBanResponse[]>jsog.deserialize(res.bandi);
          this.bandiNoSort = JSON.parse(JSON.stringify(this.bandi));
          this._exportObj = res;
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
      this.bandi = JSON.parse(JSON.stringify(this.bandiNoSort));
    } else {
      this.bandi = this.bandi.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'tipfin':
            if (a.bando.proTipfinByIdTipfin != null && b.bando.proTipfinByIdTipfin != null) {
              res = compare(a.bando.proTipfinByIdTipfin['destipfin'], b.bando.proTipfinByIdTipfin['destipfin']);
            } else {
              res = a.bando.proTipfinByIdTipfin == null ? 1 : -1;
            }
            break;
          case 'nroprofon':
          case 'imprisfon':
            res = compare(a.totali[column], b.totali[column]);
            break;
          default:
            res = compare(a.bando[column], b.bando[column]);
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
    const tipoFinanziamento = this.formFilter.get('tipFin')?.value;
    const tematica = this.formFilter.get('tem')?.value;

    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.formFilter.patchValue({
      tipFin: this.formFilter.get('tipFin')?.value != null ? (this.formFilter.get('tipFin')?.value).id : null,
      tem: this.formFilter.get('tem')?.value != null ? (this.formFilter.get('tem')?.value).id : null,
    });
    this.ricercaBando();
    this.formFilter.patchValue({
      tipFin: tipoFinanziamento,
      tem: tematica,
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
        controlFilter !== 'calcolaTotali'
      ) {
        this.countFiltri++;
      }
    }
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formFilter.controls[elemento];
  }

  ricercaBandi(): void {
    if (this.getFormElement('autocomplete').value.length > 1 || this.getFormElement('autocomplete').value.length === 0) {
      this.applicaFiltri();
    }
  }

  nuovoBando(): void {
    this.router.navigateByUrl('/bandi/new');
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`bandi/${id}/viewDetail`]);
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('bandi.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  resetFilter(): void {
    this.formFilter.patchValue({
      tipFin: null,
      tem: null,
      staBan: '',
      calcolaTotali: true,
    });
    this.formFilter.enable();
    this.countFiltri = 0;
    this.ricercaBando();
  }

  private convertDataCSV(queryResult?: IBandi): any[] {
    const items: any[] = [];
    queryResult?.bandi.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('bandi.codban')]: element.bando.codban ? element.bando.codban : '',
        [this.translateService.instant('bandi.desban')]: element.bando.desban ? element.bando.desban : '',
        [this.translateService.instant('bandi.tipfin')]: element.bando.proTipfinByIdTipfin.destipfin
          ? element.bando.proTipfinByIdTipfin.destipfin
          : '',
        [this.translateService.instant('bandi.num_progetti')]: element.totali.nroprofon ? element.totali.nroprofon : '0',
        [this.translateService.instant('bandi.resources')]: element.totali.imprisfon
          ? this.transformValueImporto(element.totali.imprisfon)
          : '0,00',
      };
      items.push(csvLine);
    });
    return items;
  }
}
