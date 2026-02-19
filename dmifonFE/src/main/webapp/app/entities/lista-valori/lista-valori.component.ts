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
import { ILisVal, LisValResponse } from '../../models/lisVal.model';
import { ListaValoriService } from '../../services/listaValori.service';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './lista-valori.component.html',
  styleUrls: ['./lista-valori.component.scss'],
})
export class ListaValoriComponent extends SearchPage implements OnInit {
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: ILisVal[];
  countFiltri: number;
  lisVal!: ILisVal[];
  lisValNoSort!: ILisVal[];
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
    protected lisValService: ListaValoriService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/lista-valori';
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
        switchMap((value: any) => this.lisValService.ricercaListaValori(this.formFilter.value))
      )
      .subscribe(
        (res: any) => {
          this.lisVal = <ILisVal[]>jsog.deserialize(res.proLisval);
          this.lisValNoSort = JSON.parse(JSON.stringify(this.lisVal)); // Clone per ordinamento iniziale
          this._exportObj = res.proLisval;
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
    this.lisValService
      .ricercaListaValori(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.lisVal = <ILisVal[]>jsog.deserialize(res.proLisval);
          this.lisValNoSort = JSON.parse(JSON.stringify(this.lisVal)); // Clone per ordinamento iniziale
          this._exportObj = res.proLisval;
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
      this.lisVal = JSON.parse(JSON.stringify(this.lisValNoSort));
    } else {
      this.lisVal = this.lisVal.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'imprisfon':
            res = compare(a.proLisval[column], b.proLisval[column]);
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

  nuovaListaValori(): void {
    this.router.navigateByUrl('/lista-valori/new');
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`lista-valori/${id}/viewDetail`]);
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('lisVal.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  private convertDataCSV(queryResult?: ILisVal[]): any[] {
    const items: any[] = [];

    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('lisVal.descrizione')]: element.vallis ? element.vallis : '',
        [this.translateService.instant('lisVal.tipoLista')]: element.tiplis ? element.tiplis : '',
      };
      items.push(csvLine);
    });
    return items;
  }
}
