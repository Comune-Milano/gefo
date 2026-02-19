import { Component, Injector, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, switchMap } from 'rxjs';
import { ProgService } from '../../services/gest-progetti.service';
import { DirCentraleService } from '../../services/dir-centrale.service';
import { TipFinService } from '../../services/tip-fin.service';
import { ExportProgettiService } from '../../services/export-progetti.service';
import { DizMacroProgService } from '../../services/diz-macro-prog.service';
import { BandoService } from '../../services/bando.service';
import { TematicheService } from '../../services/tematiche.service';
import { StatProService } from '../../services/statpro.service';
import { StaFinService } from '../../services/stafin.service';
import { UpdatePage } from '../../shared/baseclass/update-page';
import { ExportComponent } from '../../layouts/export/export.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'jhi-export-prog',
  templateUrl: './export-prog.component.html',
  styleUrls: ['./export-prog.component.scss'],
})
export class ExportProgComponent extends UpdatePage implements OnInit {
  formExport!: FormGroup;
  statPro!: Array<any>;
  statFin!: Array<any>;
  constructor(
    injector: Injector,
    private progService: ProgService,
    private tipFinService: TipFinService,
    private dirService: DirCentraleService,
    private macroProgService: DizMacroProgService,
    private bandoService: BandoService,
    private temService: TematicheService,
    private statProService: StatProService,
    private staFinService: StaFinService,
    protected exportService: ExportProgettiService,
    private exportComponent: ExportComponent,
    private _fb: FormBuilder
  ) {
    super(injector);
  }

  //ricerca con autocomplete dei progetti
  searchPro: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
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

  //ricerca con autocomplete delle direzioni
  searchDir: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
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

  //ricerca con autocomplete dei macro progetti
  searchMacPro: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => (term.length < 1 ? [] : this.macroProgService.ricercaMacroProgettiAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei macro progetti nell'autocomplete searchMacPro
  formatterMacPro = (x: { desmacpro: string; codmacpro: string; id: number }) => {
    if (x.id) {
      return `${x.codmacpro + ' - ' + x.desmacpro}`;
    }
    return '';
  };

  //ricerca con autocomplete dei bandi
  searchBan: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => (term.length < 1 ? [] : this.bandoService.ricercaBandoAutocomplete(term)))
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
    this.formExport = this._fb.group({
      ProProByIdPro: [null],
      tipLiv: ['PB'],
      ProTipFinByIdTipFin: [null],
      dir: [null],
      ProMacProByIdMacPro: [null],
      ProBanByIdBan: [null],
      ProTemByIdTem: [null],
      idStaPro: [null],
      idStaFin: [null],
      flgDatAnaPro: [false],
      flgIgv: [false],
      flgInfAgg: [false],
      flgRis: [false],
      flgRisDiCui: [false],
      flgRes: [false],
      flgAva: [false],
      flgPre: [false],
      tipPer: [''],
      annPreDa: [new Date().getFullYear()],
      annPreA: [new Date().getFullYear()],
    });
  }

  createExportObj(): any {
    const exportObj = {
      tipLiv: this.formExport.get('tipLiv')?.value,
      idPro: this.formExport.get('ProProByIdPro')?.value ? this.formExport.get('ProProByIdPro')?.value.id : null,
      idTipFin: this.formExport.get('ProTipFinByIdTipFin')?.value ? this.formExport.get('ProTipFinByIdTipFin')?.value.id : null,
      idDir: this.formExport.get('dir')?.value ? this.formExport.get('dir')?.value.id : null,
      idMacPro: this.formExport.get('ProMacProByIdMacPro')?.value ? this.formExport.get('ProMacProByIdMacPro')?.value.id : null,
      idBan: this.formExport.get('ProBanByIdBan')?.value ? this.formExport.get('ProBanByIdBan')?.value.id : null,
      idTem: this.formExport.get('ProTemByIdTem')?.value ? this.formExport.get('ProTemByIdTem')?.value.id : null,
      idStaPro: this.formExport.get('idStaPro')?.value ? Number(this.formExport.get('idStaPro')?.value) : null,
      idStaFin: this.formExport.get('idStaFin')?.value ? Number(this.formExport.get('idStaFin')?.value) : null,
      flgDatAnaPro: this.formExport.get('flgDatAnaPro')?.value,
      flgIgv: this.formExport.get('flgIgv')?.value,
      flgInfAgg: this.formExport.get('flgInfAgg')?.value,
      flgRis: this.formExport.get('flgRis')?.value,
      flgRisDiCui: this.formExport.get('flgRisDiCui')?.value,
      flgRes: this.formExport.get('flgRes')?.value,
      flgAva: this.formExport.get('flgAva')?.value,
      flgPre: this.formExport.get('flgPre')?.value,
      tipPer: this.formExport.get('tipPer')?.value,
      annPreDa: this.formExport.get('annPreDa')?.value,
      annPreA: this.formExport.get('annPreA')?.value,
    };
    return exportObj;
  }

  onCheckboxPreChange(): void {
    if (this.formExport.get('flgPre')?.value) {
      this.formExport.controls['tipPer'].setValidators([Validators.required]);
      this.formExport.controls['annPreDa'].setValidators([Validators.required]);
      this.formExport.controls['annPreA'].setValidators([Validators.required]);
    } else {
      this.formExport.get('tipPer')?.clearValidators();
      this.formExport.get('annPreDa')?.clearValidators();
      this.formExport.get('annPreA')?.clearValidators();
    }
    this.formExport.controls['tipPer'].updateValueAndValidity();
    this.formExport.controls['annPreDa'].updateValueAndValidity();
    this.formExport.controls['annPreA'].updateValueAndValidity();
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formExport.controls[elemento];
  }

  checkCampiObbligatori(): void {
    if (!this.getFormElement('tipPer').valid || !this.getFormElement('annPreDa').valid || !this.getFormElement('annPreA').valid) {
      this.showAlertError({ error: { userMessage: 'I campi Tipo periodo previsione, Anno previsione da/a sono obbligatori ' } });
    }
  }

  sendExport(): void {
    if (this.formExport.valid) {
      this.exportService.incrementCountExport();
      this.exportService.setIsExport(true);
      this.exportComponent.exportEntity = 'PRO';
      this.exportComponent.exportObj = this.createExportObj();
      this.exportComponent.startExport();
    } else {
      this.checkCampiObbligatori();
    }
  }
}
