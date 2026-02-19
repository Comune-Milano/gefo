import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, map, Observable, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TematicheService } from 'app/services/tematiche.service';
import { ITematiche, ProTematicheResponse } from 'app/models/tematiche.model';
import { TranslateService } from '@ngx-translate/core';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-tip-fin',
  templateUrl: './tematiche.component.html',
  styleUrls: ['./tematiche.component.scss'],
})
export class TematicheComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: ITematiche;
  countFiltri: number;
  tematiche$: Observable<ITematiche[]> | undefined;
  tematiche!: Array<ProTematicheResponse>;
  tematicheNoSort!: Array<ProTematicheResponse>;
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
    protected tematicheService: TematicheService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/tematiche';
    this.countFiltri = 0;
  }

  ngOnInit(): void {
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.formFilter = this._fb.group({
      autocomplete: [''],
      liv1: [null, [Validators.min(1)]],
      liv2: [null, [Validators.min(1)]],
      //tipLiv: [''],
      calcolaTotali: true,
    });
    super.initSearchFilterForm(); // Recupero i filtri dallo storage
    this.highlightText = this.formFilter.get('autocomplete')?.value;
    this.countFiltriApplicati(); // Numero di filtri applicati
    this.ricercaTematiche();
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        takeUntil(this.ngUnsubscribe),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap(value => this.tematicheService.ricercaTematica(this.formFilter.value))
      )
      .subscribe(
        (res: ITematiche) => {
          this.tematiche = res.tematiche;
          this.tematicheNoSort = JSON.parse(JSON.stringify(this.tematiche));
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
          this.warningMessage = '';
          this.isFetching = false;
        }
      );
  }

  ricercaTematiche(): void {
    this.isFetching = true;
    this.tematicheService
      .ricercaTematica(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: ITematiche) => {
          this.tematiche = res.tematiche;
          this.tematicheNoSort = JSON.parse(JSON.stringify(this.tematiche));
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

  onSort({ column, direction }: SortEvent): void {
    // resetting other headers

    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    // sorting
    if (direction === '' || column === '') {
      this.tematiche = JSON.parse(JSON.stringify(this.tematicheNoSort));
    } else {
      this.tematiche = this.tematiche.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'nroprofon':
          case 'imprisfon':
            res = compare(a.totali[column], b.totali[column]);
            break;
          default:
            res = compare(a.tematica[column], b.tematica[column]);
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
    this.ricercaTematiche();
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

  nuovaTematica(): void {
    this.router.navigateByUrl('/tematiche/new');
  }

  apriDettaglio(id: number): void {
    const route = id + '/viewDetail';
    this.router.navigate([route], { relativeTo: this.$activatedRoute });
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(
      this.translateService.instant('tematiche.csvFileName'),
      this.convertDataCSV(this._exportObj)
    );
  }

  resetFilter(): void {
    this.getFormElement('liv1').reset();
    this.getFormElement('tipLiv').reset();
    this.formFilter.enable();
    this.countFiltri = 0;
    this.ricercaTipoFinanziamento();
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private convertDataCSV(queryResult?: ITematiche): any[] {
    const items: any[] = [];

    queryResult?.tematiche.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('tematiche.livuno')]: element.tematica.livuno ? element.tematica.livuno : '0',
        [this.translateService.instant('tematiche.livdue')]: element.tematica.livdue ? element.tematica.livdue : '0',
        [this.translateService.instant('tematiche.codtipfin')]: element.tematica.codtipfin ? element.tematica.codtipfin : '',
        [this.translateService.instant('tematiche.destipfin')]: element.tematica.destipfin ? element.tematica.destipfin : '',
        [this.translateService.instant('tematiche.resources')]: element.totali.imprisfon ? element.totali.imprisfon : '0',
        [this.translateService.instant('tematiche.num_progetti')]: element.totali.nroprofon ? element.totali.nroprofon : '0',
      };
      items.push(csvLine);
    });
    return items;
  }
}
