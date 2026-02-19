import { Component, OnInit, ViewChildren, QueryList, Injector } from '@angular/core';
import { compare, NgbdSortableHeaderDirective, SortEvent } from 'app/shared/sort/sortable.directive';
import { debounceTime, map, Observable, Subject, switchMap, takeUntil } from 'rxjs';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { TipFinService } from 'app/services/tip-fin.service';
import { ItipiFinanziamento, ProTipFinResponse } from 'app/models/tipiFinanziamento.model';
import { TranslateService } from '@ngx-translate/core';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { ItipFin } from '../../models/tip_fin.model';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-tip-fin',
  templateUrl: './tip-fin.component.html',
  styleUrls: ['./tip-fin.component.scss'],
})
export class TipFinComponent extends SearchPage implements OnInit {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _exportObj!: ItipiFinanziamento;
  countFiltri: number;
  tipFinanziamenti$: Observable<ItipiFinanziamento[]> | undefined;
  tipFinanziamenti!: Array<ProTipFinResponse>;
  tipFinanziamentiNoSort!: Array<ProTipFinResponse>;
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
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    private _fb: FormBuilder
  ) {
    super(injector, '');
    this.path = '/tip-fin';
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
      liv3: [null, [Validators.min(1)]],
      liv4: [null, [Validators.min(1)]],
      liv5: [null, [Validators.min(1)]],
      tipLiv: ['01'],
      calcolaTotali: true,
    });
    this.formFilter.get('liv2')?.disable();
    this.formFilter.get('liv3')?.disable();
    this.formFilter.get('liv4')?.disable();
    this.formFilter.get('liv5')?.disable();
    super.initSearchFilterForm(); // Recupero i filtri dallo storage
    this.checkTipLivValue(this.formFilter.get('tipLiv').value);
    this.highlightText = this.formFilter.get('autocomplete')?.value;
    this.countFiltriApplicati(); // Numero di filtri applicati
    //ricerca be autocomplete con annullamento ricerche precedenti
    this.formFilter
      .get('autocomplete')
      ?.valueChanges.pipe(
        debounceTime(100),
        takeUntil(this.ngUnsubscribe),
        map(value => {
          this.highlightText = value;
          this.isFetching = true;
        }),
        switchMap((value: any) => this.tipFinService.ricercaTipoFinanziamento(this.formFilter.getRawValue()))
      )
      .subscribe(
        (res: ItipiFinanziamento) => {
          //console.log(res.tipiFinanziamento.length);
          this.tipFinanziamenti = res.tipiFinanziamento;
          this.tipFinanziamentiNoSort = JSON.parse(JSON.stringify(this.tipFinanziamenti));
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
    this.formFilter
      .get('tipLiv')
      ?.valueChanges.pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(value => {
        //console.log(value);
        this.checkTipLivValue(value);
      });
    this.isFetching = true;
    this.tipFinService.ricercaTipoFinanziamento(this.formFilter.getRawValue()).subscribe(
      (res: ItipiFinanziamento) => {
        this.tipFinanziamenti = res.tipiFinanziamento;
        this.tipFinanziamentiNoSort = JSON.parse(JSON.stringify(this.tipFinanziamenti)); // res.tipiFinanziamento;
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

  checkTipLivValue(value: string): void {
    switch (value) {
      case '04':
        this.getFormElement('liv5').reset();
        this.getFormElement('liv5').disable();
        this.getFormElement('liv4').enable();
        this.getFormElement('liv3').enable();
        this.getFormElement('liv2').enable();
        break;
      case '03':
        this.getFormElement('liv4').disable();
        this.getFormElement('liv4').reset();
        this.getFormElement('liv3').enable();
        this.getFormElement('liv2').enable();
        break;
      case '02':
        this.getFormElement('liv4').disable();
        this.getFormElement('liv4').reset();
        this.getFormElement('liv3').disable();
        this.getFormElement('liv3').reset();
        this.getFormElement('liv2').enable();
        this.getFormElement('liv1').enable();
        break;
      case '01':
        this.getFormElement('liv5').disable();
        this.getFormElement('liv5').reset();
        this.getFormElement('liv4').disable();
        this.getFormElement('liv4').reset();
        this.getFormElement('liv3').disable();
        this.getFormElement('liv3').reset();
        this.getFormElement('liv2').disable();
        this.getFormElement('liv2').reset();
        break;
      default:
        this.getFormElement('liv1').enable();
        this.getFormElement('liv2').enable();
        this.getFormElement('liv3').enable();
        this.getFormElement('liv4').enable();
        this.getFormElement('liv5').enable();
    }
  }

  ricercaTipoFinanziamento(): void {
    this.isFetching = true;
    this.tipFinService
      .ricercaTipoFinanziamento(this.formFilter.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: ItipiFinanziamento) => {
          //console.log(res.tipiFinanziamento.length);
          this.tipFinanziamenti = res.tipiFinanziamento;
          this.tipFinanziamentiNoSort = JSON.parse(JSON.stringify(this.tipFinanziamenti));
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
      this.tipFinanziamenti = JSON.parse(JSON.stringify(this.tipFinanziamentiNoSort));
    } else {
      this.tipFinanziamenti = this.tipFinanziamenti.sort((a: any, b: any) => {
        let res = 0;
        switch (column) {
          case 'nroprofon':
          case 'imprisfon':
            res = compare(a.totali[column], b.totali[column]);
            break;
          case 'impimp':
            res = compare(a.totaliImpegni[column], b.totaliImpegni[column]);
            break;
          case 'impigv':
            res = compare(a.totaliImpegni[column], b.totaliImpegni[column]);
            break;
          case 'impddr':
            res = compare(a.totaliDdr[column], b.totaliDdr[column]);
            break;
          default:
            res = compare(a.tipoFinanziamento[column], b.tipoFinanziamento[column]);
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
    this.ricercaTipoFinanziamento();
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
    /* if (this.getFormElement('liv1').value != null) {
      this.countFiltri++;
    }
    if (this.getFormElement('liv2').value != null) {
      this.countFiltri++;
    }
    if (this.getFormElement('liv3').value != null) {
      this.countFiltri++;
    }
    if (this.getFormElement('liv4').value != null) {
      this.countFiltri++;
    }
    if (this.getFormElement('tipLiv').value != null && this.getFormElement('tipLiv').value !== '') {
      this.countFiltri++;
    } */
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formFilter.controls[elemento];
  }

  contaLivRiga(riga: ItipFin): number {
    let counter = 0;
    if (riga.livuno != 0) {
      counter++;
      this.formFilter.get('liv1')?.setValue(riga.livuno);
    }
    if (riga.livdue != 0) {
      counter++;
      this.formFilter.get('liv2')?.setValue(riga.livdue);
    }
    if (riga.livtre != 0) {
      counter++;
      this.formFilter.get('liv3')?.setValue(riga.livtre);
    }
    if (riga.livqua != 0) {
      counter++;
      this.formFilter.get('liv4')?.setValue(riga.livqua);
    }
    if (riga.livcin != 0) {
      counter++;
    }
    if (riga.livsei != 0) {
      counter++;
    }
    return counter;
  }

  vaiAvanti(tipFin: ItipFin): void {
    const totLiv = this.contaLivRiga(tipFin);
    //console.log(totLiv);
    if (totLiv != 6) {
      const curLiv = 1 + totLiv;
      this.formFilter.get('tipLiv')?.setValue('0' + curLiv.toString());
      this.applicaFiltri();
    }
  }

  tornaIndietro(tipFin: ItipFin): void {
    const totLiv = this.contaLivRiga(tipFin);
    if (totLiv != 1) {
      const curLiv = totLiv - 1;
      if (curLiv <= 1) {
        this.formFilter.get('liv1')?.setValue(0);
      }
      if (curLiv <= 2) {
        this.formFilter.get('liv2')?.setValue(0);
      }
      if (curLiv <= 3) {
        this.formFilter.get('liv3')?.setValue(0);
      }
      if (curLiv <= 4) {
        this.formFilter.get('liv4')?.setValue(0);
      }
      this.formFilter.get('tipLiv')?.setValue('0' + curLiv.toString());
      this.applicaFiltri();
    }
  }
  /*ricercaTipFin(): void {
    if (this.getFormElement('autocomplete').value.length > 1 || this.getFormElement('autocomplete').value.length === 0) {
      this.ricercaTipoFinanziamento();
    }
  } */

  nuovoTipFin(): void {
    this.router.navigateByUrl('/tip-fin/new');
  }

  apriDettaglio(id: number): void {
    const route = id + '/viewDetail';
    this.router.navigate([route], { relativeTo: this.$activatedRoute });
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('tipFin.csvFileName'), this.convertDataCSV(this._exportObj));
  }

  resetFilter(): void {
    this.getFormElement('liv1').reset();
    this.getFormElement('liv2').reset();
    this.getFormElement('liv3').reset();
    this.getFormElement('liv4').reset();
    this.getFormElement('liv5').reset();
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

  goToProg(tipFin: ItipFin): void {
    this.router.navigateByUrl(`tip-fin/${tipFin.id}/gest-progetti`, { state: tipFin });
  }

  private convertDataCSV(queryResult?: ItipiFinanziamento): any[] {
    const items: any[] = [];

    queryResult?.tipiFinanziamento.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('tipFin.codtipfin')]: element.tipoFinanziamento.codtipfin ? element.tipoFinanziamento.codtipfin : '',
        [this.translateService.instant('tipFin.destipfin')]: element.tipoFinanziamento.destipfin ? element.tipoFinanziamento.destipfin : '',
        [this.translateService.instant('tipFin.resources')]: element.totali.imprisfon ? element.totali.imprisfon : '0',
        [this.translateService.instant('tipFin.num_progetti')]: element.totali.nroprofon ? element.totali.nroprofon : '0',
        [this.translateService.instant('tipFin.impimp')]: element.totaliImpegni.impimp ? element.totaliImpegni.impimp : '0',
        [this.translateService.instant('tipFin.impigv')]: element.totaliImpegni.impigvdel ? element.totaliImpegni.impigvdel : '0',
        [this.translateService.instant('tipFin.impddr')]: element.totaliDdr.impddr ? element.totaliDdr.impddr : '0',
      };
      items.push(csvLine);
    });
    return items;
  }
}
