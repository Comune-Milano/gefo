import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, map, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { TranslateService } from '@ngx-translate/core';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { ILisVal } from '../../models/lisVal.model';
import { IProTipfas } from '../../models/proFas.model';
import { TipFasService } from '../../services/tipfas.service';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './tipo-fase.component.html',
  styleUrls: ['./tipo-fase.component.scss'],
})
export class TipoFaseComponent extends SearchPage implements OnInit {
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: IProTipfas[];
  countFiltri: number;
  tipFas!: IProTipfas[];
  tipFaslNoSort!: IProTipfas[];
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
    protected tipFinService: TipFinService,
    protected tipFasService: TipFasService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/tipo-fase';
    this.countFiltri = 0;
  }

  ngOnInit(): void {
    const jsog = new JsogService();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.formFilter = this._fb.group({
      autocomplete: [''],
      tipFinDa: [null, [Validators.min(1)]],
      tipFinA: [null, [Validators.min(1)]],
      calcolaTotali: true,
    });
    super.initSearchFilterForm(); // Recupero i filtri dallo storage
    this.highlightText = this.formFilter.get('autocomplete')?.value;
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        takeUntil(this.ngUnsubscribe),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap((value: any) => this.tipFasService.ricercaTipiFase(this.formFilter.value))
      )
      .subscribe(
        (res: any) => {
          this.tipFas = <IProTipfas[]>jsog.deserialize(res.proTipfas);
          this.tipFaslNoSort = JSON.parse(JSON.stringify(this.tipFas)); // Clone per ordinamento iniziale
          this._exportObj = res.proTipFas;
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

    this.isFetching = true;
    this.tipFasService
      .ricercaTipiFase(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.tipFas = <IProTipfas[]>jsog.deserialize(res.proTipfas);
          this.tipFaslNoSort = JSON.parse(JSON.stringify(this.tipFas)); // Clone per ordinamento iniziale
          this._exportObj = res.proTipFas;
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

  onSort({ column, direction }: SortEvent): void {
    // resetting other headers

    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    // sorting
    if (direction === '' || column === '') {
      this.tipFas = JSON.parse(JSON.stringify(this.tipFaslNoSort));
    } else {
      this.tipFas = this.tipFas.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'imprisfon':
            res = compare(a.proTipFas[column], b.proTipFas[column]);
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

  nuovoTipoFase(): void {
    this.router.navigateByUrl('/tipo-fase/new');
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`tipo-fase/${id}/viewDetail`]);
  }

  exportToCSV(): void {
    if (!this.tipFas || this.tipFas.length === 0) {
      return;
    }

    const csvData = this.convertDataCSV(this.tipFas);

    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('tipFas.csvFileName'), csvData);
  }

  private convertDataCSV(queryResult?: IProTipfas[]): any[] {
    const items: any[] = [];

    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('tipFas.descrizione')]: element.desfas ? element.desfas : '',
        [this.translateService.instant('tipFas.ordinamento')]: element.ordfas ? element.ordfas : 0,
      };
      items.push(csvLine);
    });
    return items;
  }
}
