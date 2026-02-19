import { Component, OnInit, ViewChildren, QueryList, Injector, Input, Output, EventEmitter } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, distinctUntilChanged, map, Observable, of, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
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
import { UsersService } from '../../services/users.service';
import { formatDate } from '@angular/common';
import { SessionStorageService } from 'ngx-webstorage';
import { AmmElaService } from '../../services/amm-ela.service';
import { IAmmEla } from '../../models/amm_ela.model';

@Component({
  selector: 'jhi-det-con-cap',
  templateUrl: './amm-ela.component.html',
  styleUrls: ['./amm-ela.component.scss'],
})
export class AmmElaComponent extends SearchPage implements OnInit {
  @ViewChildren(NgbdSortableHeaderDirective) headers!: QueryList<NgbdSortableHeaderDirective>;
  _rolesObj!: IGestioneProgetti;
  viewAlert = true;
  viewDelete = true;
  viewError = true;
  countFiltri: number;
  capent = false;
  prog = false;
  elaborazioni!: IAmmEla[];
  elaborazioniNoSort!: IAmmEla[];
  highlightText = '';
  warningMessage?: string;
  isFetching = false;
  isFetching1 = false;
  id = 0;
  shPanCap = false;
  private ngUnsubscribe = new Subject<void>();

  //ricerca con autocomplete degli utenti
  searchUte: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.userService.getUtentiAutocomplete(term)))
    );
  };

  //gestisce la formattazione degli utenti nell'autocomplete searchUte
  formatterUte = (x: { username: string; nome: string; cognome: string; id: number }) => {
    if (x.id) {
      return `${x.username + ' - ' + x.cognome + ' ' + x.nome}`;
    }
    return '';
  };

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
    protected userService: UsersService,
    protected ammElaService: AmmElaService,
    private sessionStorageService: SessionStorageService,
    // private alertService: AlertService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/ela';
    this.countFiltri = 0;
  }

  ngOnInit(): void {
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    this.warningMessage = '';
    this.formFilter = this.formBuilder.group({
      username: [''],
      usernameAutocomplete: [''],
      dtainida: [formatDate(new Date(), 'yyyy-MM-dd', 'it')],
      dtainia: [formatDate(new Date(), 'yyyy-MM-dd', 'it')],
    });
    const jsog = new JsogService();
    this.countFiltri = 0;
    this.isFetching = true;
    let idUte = localStorage.getItem('idUte') ? localStorage.getItem('idUte') : '0';
    this.userService
      .getUtente(<string>idUte)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.formFilter.patchValue({
          username: res.username ? res.username : '',
          usernameAutocomplete: res,
        });
        super.initSearchFilterForm(); // Recupero i filtri dallo storage
        this.countFiltriApplicati();
        this.ammElaService
          .ricercaElaborazione(this.formFilter.value)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(res => {
            this.elaborazioni = res;
            this.elaborazioniNoSort = res;
            this.isFetching = false;
          });
      });
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

    if (direction === '' || column === '') {
      this.elaborazioni = JSON.parse(JSON.stringify(this.elaborazioniNoSort));
    } else {
      this.elaborazioni = this.elaborazioni.sort((a: any, b: any) => {
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
    this.formFilter
      .get('username')
      ?.setValue(this.formFilter.get('usernameAutocomplete')?.value ? this.formFilter.get('usernameAutocomplete')?.value.id : '');
    this.countFiltriApplicati();
    this.isFetching = true;
    this.ammElaService
      .ricercaElaborazione(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.elaborazioni = res;
        this.config.currentPage = 1;
        this.elaborazioniNoSort = res;
        this.isFetching = false;
      });
  }

  countFiltriApplicati(): void {
    this.countFiltri = 0;
    const controlsFilter = this.formFilter.controls;
    for (const controlFilter in controlsFilter) {
      if (
        controlsFilter[controlFilter].value != null &&
        controlsFilter[controlFilter].value !== '' &&
        controlFilter !== 'usernameAutocomplete'
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

  setCodCap(codice: string): void {
    this.codCap.emit(codice);
  }

  resetFilter(): void {
    this.shPanCap = false;
    this.formFilter.patchValue({
      username: null,
      usernameAutocomplete: null,
      dtainida: null,
      dtainia: null,
    });
    this.formFilter.enable();
    this.countFiltri = 0;
  }
}
