import { Component, Injector, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AlertService } from 'app/shared/alert/alert.service';
import { TranslateService } from '@ngx-translate/core';
import { TipFinService } from 'app/services/tip-fin.service';
import { ItipFin } from 'app/models/tip_fin.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { ProgService } from '../../services/gest-progetti.service';
import { ProTemService } from '../../services/protem.service';
import { IAvanzamento, IAvaResponse } from 'app/models/avanzamento.model';
import { JsogService } from 'jsog-typescript';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { AvaService } from 'app/services/avanzamenti.service';
import { Dictionary } from '../../shared/utils/dictionary.class';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-avanzamenti',
  templateUrl: './avanzamenti.component.html',
  styleUrls: ['./avanzamenti.component.scss'],
})
export class AvanzamentiComponent extends SearchPage implements OnInit, IComponentCanDeactivate {
  mostraCard = false;
  allegato: any = null;
  formFilter!: FormGroup;
  countFiltri!: number;
  AvaPro!: any;
  warningMessage = '';
  isFetching = false;
  avanzamenti!: Array<IAvaResponse>;
  dizionarioColori = new Dictionary();
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private _fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    protected router: Router,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected dirService: DirCentraleService,
    protected progService: ProgService,
    protected tipFinService: TipFinService,
    protected avanzamentiService: AvaService,
    protected translateService: TranslateService
  ) {
    super(injector, '');
    this.countFiltri = 0;
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

  //gestisce la formattazione dei tipiFinanziamento nell'autocomplete searchTipFin
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
    this.initColori();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.formFilter = this._fb.group({
      idPro: [null],
      progetto: [null],
      tipliv: [''],
      idTipfin: [null],
      tipFin: [null],
      idDir: [null],
      direzione: [null],
      tipava: ['A'],
    });
    if (this.activatedRoute.snapshot.data.previousPath === '/gest-progetti') {
      this.isFetching = true;
      this.progService
        .getProgetto(this.activatedRoute.snapshot.params.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(res => {
          this.formFilter.get('progetto')?.setValue(res.progetto);
          this.applicaFiltri();
        });
    } else {
      super.initSearchFilterForm(); // Recupero i filtri dallo storage
      this.isFetching = true;
      this.applicaFiltri();
    }
  }

  //metodo che inizializza i colori del dizionario
  initColori(): void {
    this.dizionarioColori.set('rosso', 'red');
    this.dizionarioColori.set('giallo', '#ffdf00');
    this.dizionarioColori.set('verde', 'green');
  }

  createAvaFilterObj(): any {
    this.formFilter.patchValue({
      idPro: this.formFilter.get('progetto') ? this.formFilter.get('progetto')?.value?.id : null,
      idTipfin: this.formFilter.get('tipFin') ? this.formFilter.get('tipFin')?.value?.id : null,
      idDir: this.formFilter.get('direzione') ? this.formFilter.get('direzione')?.value?.id : null,
    });
    return {
      idProgetto: this.formFilter.get('idPro') ? this.formFilter.get('idPro')?.value : null,
      tipoFinanziamento: this.formFilter.get('idTipfin') ? this.formFilter.get('idTipfin')?.value : null,
      direzione: this.formFilter.get('idDir') ? this.formFilter.get('idDir')?.value : null,
      tipoAvanzamento: this.formFilter.get('tipava') ? this.formFilter.get('tipava')?.value : null,
      tipLiv: this.formFilter.get('tipliv') ? this.formFilter.get('tipliv')?.value : null,
    };
  }

  ricercaAvanzamenti(): void {
    const jsog = new JsogService();
    this.isFetching = true;
    this.avanzamentiService
      .ricericercaAvanzamento(this.createAvaFilterObj())
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.avanzamenti = <IAvaResponse[]>jsog.deserialize(res.avanzamenti);
          //this.avanzamentiNoSort = JSON.parse(JSON.stringify(this.avanzamenti)); Clone per ordinamento iniziale
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

  //metodo che applica i filtri
  applicaFiltri(): void {
    this.formFilter.patchValue({
      idPro: this.formFilter.get('progetto') ? this.formFilter.get('progetto')?.value?.id : null,
    });
    this.countFiltriApplicati();
    this.config.currentPage = 1;
    this.ricercaAvanzamenti();
  }

  countFiltriApplicati(): void {
    this.countFiltri = 0;
    const controlsFilter = this.formFilter.controls;
    for (const controlFilter in controlsFilter) {
      if (
        controlsFilter[controlFilter].value != null &&
        controlsFilter[controlFilter].value !== '' &&
        controlFilter !== 'idPro' &&
        controlFilter !== 'idTipfin' &&
        controlFilter !== 'idDir'
      ) {
        this.countFiltri++;
      }
    }
  }

  nuovoAvanzamento(): void {
    this.router.navigate(['new'], { relativeTo: this.$activatedRoute });
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('proAva.csvFileName'), this.convertDataCSV(this.avanzamenti));
  }

  apriDettaglio(id: number): void {
    this.router.navigate([`${id}/viewDetail`], { relativeTo: this.$activatedRoute });
  }

  canDeactivate(): Observable<boolean> | boolean {
    return true;
  }

  ngOnDestroy(): void {
    super.cacheFilterForm(); // Cache dei filtri nello storage
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private convertDataCSV(queryResult?: IAvaResponse[]): any[] {
    const items: any[] = [];
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('proAva.prog')]: element.codDesProgetto ? element.codDesProgetto : '-',
        [this.translateService.instant('proAva.dtarilava')]: element.avanzamento.dtarilava ? element.avanzamento.dtarilava : '00/00/0000',
        [this.translateService.instant('proAva.desstaava')]: element.avanzamento.desstaava ? element.avanzamento.desstaava : '-',
        [this.translateService.instant('proAva.vallisfasint')]: element.faseIntervento.vallis ? element.faseIntervento.vallis : '-',
        [this.translateService.instant('proAva.vallislivcri')]: element.livelloCriticita.vallis ? element.livelloCriticita.vallis : '-',
        [this.translateService.instant('proAva.semaforo')]: element.semaforoAvanzamento.descrizione
          ? element.semaforoAvanzamento.descrizione
          : '-',
      };
      items.push(csvLine);
    });
    return items;
  }
}
