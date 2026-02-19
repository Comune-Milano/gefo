import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, map, Observable, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { ItipiFinanziamento } from 'app/models/tipiFinanziamento.model';
import { TranslateService } from '@ngx-translate/core';
import { IMacroProgetti, ProMacroProgResponse } from 'app/models/diz_macro_prog.model';
import { DizMacroProgService } from 'app/services/diz-macro-prog.service';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { JsogService } from 'jsog-typescript';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { IMunicipi, MunicipioResponse } from '../../models/promun.model';
import { ProMunService } from '../../services/promun.service';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './municipi.component.html',
  styleUrls: ['./municipi.component.scss'],
})
export class MunicipiComponent extends SearchPage implements OnInit {
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: IMunicipi;
  countFiltri: number;
  municipi!: Array<MunicipioResponse>;
  municipiNoSort!: Array<MunicipioResponse>;
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
    protected municipioService: ProMunService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/municipi';
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
        switchMap((value: any) => this.municipioService.ricercaMunicipio(this.formFilter.value))
      )
      .subscribe(
        (res: any) => {
          this.municipi = <MunicipioResponse[]>jsog.deserialize(res.proMun);
          this.municipiNoSort = JSON.parse(JSON.stringify(this.municipi)); // Clone per ordinamento iniziale
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

    this.isFetching = true;
    this.municipioService
      .ricercaMunicipio(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.municipi = <MunicipioResponse[]>jsog.deserialize(res.proMun);
          this.municipiNoSort = JSON.parse(JSON.stringify(this.municipi)); // Clone per ordinamento iniziale
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

  ricercaMacroProgetti(): void {
    const jsog = new JsogService();
    this.isFetching1 = true;
    this.macroProgService
      .ricercaMacroProgetto(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          //console.log(res.macroProgetti.length);
          this.macroProgetti = <ProMacroProgResponse[]>jsog.deserialize(res.macroProgetti);
          this.macroProgettiNoSort = JSON.parse(JSON.stringify(this.macroProgetti)); // Clone per ordinamento iniziale
          this._exportObj = res;
          this.isFetching1 = false;
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
      this.municipi = JSON.parse(JSON.stringify(this.municipiNoSort));
    } else {
      this.municipi = this.municipi.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'nroprofon':
          case 'imprisfon':
            res = compare(a.totali[column], b.totali[column]);
            break;
          default:
            res = compare(a.proMun[column], b.proMun[column]);
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

  nuovoMunicipio(): void {
    this.router.navigateByUrl('/municipi/new');
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`municipi/${id}/viewDetail`]);
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('mun.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  private convertDataCSV(queryResult?: IMunicipi): any[] {
    const items: any[] = [];

    queryResult?.proMun.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('dizMacroProg.codmacpro')]: element.proMun.desmun ? element.proMun.desmun : '',
        [this.translateService.instant('tipFin.resources')]: element.totali.imprisfon ? element.totali.imprisfon : '0',
        [this.translateService.instant('tipFin.num_progetti')]: element.totali.nroprofon ? element.totali.nroprofon : '0',
      };
      items.push(csvLine);
    });
    return items;
  }
}
