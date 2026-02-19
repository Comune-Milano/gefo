import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { TipFinService } from 'app/services/tip-fin.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, map, Observable, of, OperatorFunction, startWith, switchMap } from 'rxjs';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { ProgService } from '../../services/gest-progetti.service';
import { IProgDetail, IRisorse, IRisorseBasso } from '../../models/progettoDetail.model';
import { DizMacroProgService } from '../../services/diz-macro-prog.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { StatProService } from '../../services/statpro.service';
import { IStaPro } from '../../models/stapro.model';
import { StaFinService } from '../../services/stafin.service';
import { IStaFin } from '../../models/stafin.model';
import { ProMunService } from '../../services/promun.service';
import { IProMun } from '../../models/promun.model';
import { IInfAgg } from '../../models/infagg.model';
import { DecimalPipe } from '@angular/common';
import { BandoService } from '../../services/bando.service';
import { Subject } from 'rxjs';
import { TematicheService } from 'app/services/tematiche.service';
import { JsogService } from 'jsog-typescript';
import { AssessoratiService } from '../../services/assessorati.service';
import { SettoriService } from '../../services/settori.service';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { IProg } from '../../models/progetto.model';
import { takeUntil } from 'rxjs/operators';
import { ProNilService } from '../../services/pronil.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-gest-prog',
  templateUrl: './gest-prog-update.component.html',
  styleUrls: ['./gest-prog.component.scss'],
})
export class GestProgUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curProg!: IProgDetail;
  autocompleteSett: FormControl; // autocomplete settori
  autocomplete: FormControl; // autocomplete assessorati
  formProg!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  bandi: Array<any> = [];
  statPro: IStaPro[] = [];
  statFin: IStaFin[] = [];
  municipi: IProMun[] = [];
  settori!: Array<any>;
  settoriFiltered!: Array<any>;
  assessorati!: Array<any>;
  assessoratiFiltered!: Array<any>;
  textAuto!: string;
  data!: any;
  isSaving = false;
  protected router: Router;
  showAssessorati: boolean;
  jsog1 = new JsogService();

  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private tipFinService: TipFinService,
    private macroProgService: DizMacroProgService,
    private bandoService: BandoService,
    private tematicheService: TematicheService,
    private statProService: StatProService,
    private staFinService: StaFinService,
    private munService: ProMunService,
    private dirService: DirCentraleService,
    protected proNilService: ProNilService,
    protected rolesCheckService: RolesCheckService,
    private assessoratiService: AssessoratiService,
    private settoriService: SettoriService,
    private progService: ProgService,
    private decimalPipe: DecimalPipe,
    private _fb: FormBuilder,
    private customDialogsService: CustomDialogsService,
    protected translateService: TranslateService,
    protected ngbModal: NgbModal
  ) {
    super(injector);
    this.modifica = false;
    this.showAssessorati = false;
    this.autocompleteSett = new FormControl('', { nonNullable: true });
    this.autocomplete = new FormControl('', { nonNullable: true });
    this.router = injector.get(Router);
  }

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

  //ricerca con autocomplete dei macro progetti
  searchMacPro: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
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

  //ricerca con autocomplete delle direzioni
  searchNil: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 0 ? [] : this.proNilService.ricercaNilAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle direzioni nell'autocomplete searchDir
  formatterNil = (x: { desnil: string; codnil: string; id: number }) => {
    if (x.id) {
      return `${x.codnil + ' - ' + x.desnil}`;
    }
    return '';
  };

  //ricerca con autocomplete dei bandi
  searchBan: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.bandi.filter(
              (v: { desban: string; codban: string }) =>
                v.desban.toLowerCase().includes(term.toLowerCase()) || v.codban.toLowerCase().includes(term.toLowerCase())
            )
      )
    );
  };

  //gestisce la formattazione delle tematiche nell'autocomplete searchTem
  formatterBan = (x: { desban: string; codban: string; id: number }) => {
    if (x.id) {
      return `${x.codban + ' - ' + x.desban}`;
    }
    return '';
  };

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

  //ricerca con autocomplete dei progetti
  searchSet: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.settoriService.ricercaSettoriAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei progetti nell'autocomplete searchPro
  formatterSet = (x: { codentcon: string; desimp: string; id: number }) => {
    if (x.id) {
      return `${x.codentcon + ' - ' + x.desimp}`;
    }
    return '';
  };

  //ricerca con autocomplete dei progetti
  searchAss: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.assessoratiService.ricercaAssessoratiAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei progetti nell'autocomplete searchPro
  formatterAss = (x: { codentcon: string; desimp: string; id: number }) => {
    if (x.id) {
      return `${x.codentcon + ' - ' + x.desimp}`;
    }
    return '';
  };

  //ricerca con autocomplete delle tematiche
  searchTem: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => this.tematicheService.ricercaTematicaAutocomplete(term))
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
    this.assessorati = [];
    this.settori = [];
    this.assessoratiFiltered = [];
    this.settoriFiltered = [];
    this.progService
      .getAllSettori()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.settori = res;
        this.settoriFiltered = this.settori;
      });
    this.progService
      .getAllAssessorati()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.assessorati = res;
        this.assessoratiFiltered = this.assessorati;
      });
    this.autocompleteSett.valueChanges.pipe(takeUntil(this.ngUnsubscribe)).subscribe(res => {
      this.settoriFiltered = this.settori.filter((sett: { id: string; descrizione: string }) => {
        return (
          sett.id.toLowerCase().includes(this.autocompleteSett.value.toLowerCase()) ||
          sett.descrizione.toLowerCase().includes(this.autocompleteSett.value.toLowerCase())
        );
      });
    });
    this.autocomplete.valueChanges.pipe(takeUntil(this.ngUnsubscribe)).subscribe(res => {
      this.assessoratiFiltered = this.assessorati.filter((ass: { id: string; descrizione: string }) => {
        return (
          ass.id.toLowerCase().includes(this.autocomplete.value.toLowerCase()) ||
          ass.descrizione.toLowerCase().includes(this.autocomplete.value.toLowerCase())
        );
      });
    });
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      // this.id = params.get('id') ?? '';
      this.viewAlert = this.alertService.showAlert;
      this.viewError = this.alertService.showError;
      if (super.getOpInsert() === true) {
        this.viewAlert = super.getOpInsert();
        super.showAlertInsertMessage();
      }
      this.checkFormToLeave = true;
      this.bandoService
        .ricercaBando({ calcolaTotali: false })
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(res => {
          res.bandi.forEach((element: any) => {
            this.bandi.push(element.bando);
          });
        });
      this.statProService
        .getAllStatiProgetto()
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe((res: any) => {
          this.statPro = res;
        });
      this.staFinService
        .getAllStatiFinanziamento()
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe((res: any) => {
          this.statFin = res;
        });
      this.munService
        .getAllMunicipi()
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe((res: any) => {
          this.municipi = res;
        });
      if (this.activatedRoute.snapshot.url.length > 0 && this.activatedRoute.snapshot.url[0].path === 'new') {
        this.modifica = false;
        this.pagePath = this.activatedRoute.snapshot.url[0].path;
        this.id = '';
      } else {
        this.modifica = true;
        this.id = this.activatedRoute.snapshot.paramMap.get('id') ?? '';
        this.pagePath = '';
      }
      if (this.viewAlert || this.viewError) {
        setTimeout(() => {
          this.viewError = false;
          this.alertService.showError = false;
          this.viewAlert = false;
          this.alertService.showAlert = false;
        }, 5000);
      }
      //se modifica è true, esegui chiamata dettaglio utente e inizializza il form con i dati ricevuti
      if (this.modifica) {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
        this.translateService.onLangChange.subscribe(lingua => {
          this.stringSalva = this.translateService.instant('global.buttons.labelSave');
        });
        this.progService
          .getProgetto(this.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            (res: IProgDetail) => {
              this.curProg = res;

              this.formProg = this._fb.group({
                id: [this.curProg.progetto.id],
                idStapro: [this.curProg.progetto.idStapro],
                idStafin: [this.curProg.progetto.idStafin],
                idTipfin: [this.curProg.progetto.idTipfin],
                idMacpro: [this.curProg.progetto.idMacpro],
                idNil: [this.curProg.progetto.idNil],
                idBan: [this.curProg.progetto.idBan],
                idTem: [this.curProg.progetto.idTem],
                idMun: [this.curProg.progetto.idMun],
                idDir: [this.curProg.progetto.idDir],
                idPropad: [this.curProg.progetto.idPropad],
                iddecpro: [this.curProg.progetto.iddecpro],
                impimppre: [this.curProg.progetto.impimppre],
                impimpric: [this.curProg.progetto.impimpric],
                impmanpre: [this.curProg.progetto.impmanpre],
                imprevpre: [this.curProg.progetto.imprevpre],
                impaccpre: [this.curProg.progetto.impaccpre],
                livpro: [this.curProg.progetto.livpro],
                cntlivinf: [this.curProg.progetto.cntlivinf],
                codpro: [this.curProg.progetto.codpro, [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
                despro: [
                  this.curProg.progetto.despro,
                  [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()],
                ],
                stapro: [this.curProg.progetto.idStapro, [Validators.required]],
                stafin: [this.curProg.progetto.idStafin, [Validators.required]],
                flgopeavv: [this.curProg.progetto.flgopeavv === 'S' ? true : false],
                proTipfinByIdTipfin: [this.curProg.progetto.proTipfinByIdTipfin, [Validators.required]],
                proBanByIdBan: [this.curProg.progetto.proBanByIdBan],
                proMacproByIdMacpro: [this.curProg.progetto.proMacproByIdMacpro, [Validators.required]],
                progettoPadre: [this.curProg.progettoPadre],
                proTemByIdTem: [this.curProg.progetto.proTemByIdTem],
                proNilByIdNil: [this.curProg.progetto.proNilByIdNil],
                progettoDirezione: [this.curProg.progettoDirezione, [Validators.required]],
                codappren: [this.curProg.progetto.codappren, [Validators.maxLength(25), StringValidator.patternValidator()]],
                codcup: [this.curProg.progetto.codcup, [Validators.maxLength(15), StringValidator.patternValidator()]],
                codcig: [this.curProg.progetto.codcig, [Validators.maxLength(10), StringValidator.patternValidator()]],
                codcui: [this.curProg.progetto.codcui, [Validators.maxLength(25), StringValidator.patternValidator()]],
                codset: [this.curProg.progetto.codset],
                codSetAutocomplete: [this.curProg.proEntconSettore],
                codass: [this.curProg.progetto.codass],
                codAssAutocomplete: [this.curProg.proEntconAssessorato],
                tippro: [this.curProg.progetto.tippro, [Validators.maxLength(3), StringValidator.patternValidator()]],
                codtippro: [this.curProg.progetto.codtippro, [Validators.maxLength(25), StringValidator.patternValidator()]],
                codgami: [this.curProg.progetto.codgami, [Validators.maxLength(20), StringValidator.patternValidator()]],
                veraff: [this.curProg.progetto.veraff, [Validators.maxLength(250), StringValidator.patternValidator()]],
                estaff: [this.curProg.progetto.estaff, [Validators.maxLength(250), StringValidator.patternValidator()]],
                notaff: [this.curProg.progetto.notaff, [Validators.maxLength(250), StringValidator.patternValidator()]],
                desaffhou: [this.curProg.progetto.desaffhou, [Validators.maxLength(250), StringValidator.patternValidator()]],
                delcan: [this.curProg.progetto.delcan, [Validators.maxLength(250), StringValidator.patternValidator()]],
                risorse: this._fb.array([]),
                risorseLivelloBasso: this._fb.array([]),
                risorseDiCui: this._fb.array([]),
                notpro: [this.curProg.progetto.notpro, [Validators.maxLength(500), StringValidator.patternValidator()]],
                notpre: [this.curProg.progetto.notpre, [Validators.maxLength(250), StringValidator.patternValidator()]],
                nil: [this.curProg.progetto.nil],
                impigv: [this.transformValueImporto(this.curProg.progetto.impigv)],
                rifigv: [this.curProg.progetto.rifigv, [Validators.maxLength(250), StringValidator.patternValidator()]],
                impigvdel: [this.transformValueImporto(this.curProg.progetto.impigvdel)],
                notigv: [this.curProg.progetto.notigv, [Validators.maxLength(250), StringValidator.patternValidator()]],
                infAgg: this._fb.array([]),
              });

              //aggiunta risorse
              this.curProg.risorse.forEach((element: IRisorse) => {
                this.addRisorseFormGroup(element);
              });
              //aggiunta risorse livello basso
              this.curProg.risorseLivelloBasso?.forEach((element: IRisorseBasso) => {
                this.addRisorseLivelloBassoFormGroup(element);
              });
              //aggiunta risorse di cui
              this.curProg.risorseDiCui.forEach((element: IRisorse) => {
                this.addRisorseDiCuiFormGroup(element);
              });
              //aggiunta informazioni aggiuntive
              this.curProg.infoAggiuntive.forEach((element: IInfAgg) => {
                this.addInfAggForm(element);
              });
              //console.log(this.curProg);
            },
            err => {
              this.showAlertError(err);
            }
          );
      } else {
        this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
        this.translateService.onLangChange.subscribe(lingua => {
          this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
        });
        this.formProg = this._fb.group({
          idStapro: [],
          idStafin: [],
          idTipfin: [],
          idDir: [],
          idMacpro: [],
          idBan: [],
          idMun: [],
          idPropad: [],
          idTem: [],
          idNil: [],
          cntlivinf: [0],
          codpro: [null, [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
          despro: [null, [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()]],
          stapro: [null, [Validators.required]],
          stafin: [null, [Validators.required]],
          flgopeavv: [false],
          proTipfinByIdTipfin: [null, [Validators.required]],
          proBanByIdBan: [null],
          proMacproByIdMacpro: [null, [Validators.required]],
          progettoPadre: [null],
          proTemByIdTem: [null],
          proNilByIdNil: [null],
          progettoDirezione: [null, [Validators.required]],
          codappren: [null, [Validators.maxLength(25), StringValidator.patternValidator()]],
          codcup: [null, [Validators.maxLength(15), StringValidator.patternValidator()]],
          codcig: [null, [Validators.maxLength(10), StringValidator.patternValidator()]],
          codcui: [null, [Validators.maxLength(25), StringValidator.patternValidator()]],
          codass: [null],
          codAssAutocomplete: [null],
          codSetAutocomplete: [null],
          codset: [null],
          tippro: [null, [Validators.maxLength(3), StringValidator.patternValidator()]],
          codtippro: [null, [Validators.maxLength(25), StringValidator.patternValidator()]],
          codgami: [null, [Validators.maxLength(20), StringValidator.patternValidator()]],
          veraff: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          estaff: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          notaff: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          desaffhou: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          delcan: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          risorse: this._fb.array([]),
          risorseLivelloBasso: this._fb.array([]),
          risorseDiCui: this._fb.array([]),
          notpro: [null, [Validators.maxLength(500), StringValidator.patternValidator()]],
          notpre: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          nil: [],
          impigv: [this.transformValueImporto(0.0)],
          rifigv: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          impigvdel: [this.transformValueImporto(0.0)],
          notigv: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
          infAgg: this._fb.array([]),
        });
      }
    });

    //    this.viewAlert = this.alertService.showAlert;
    //    this.viewError = this.alertService.showError;
    //    if (super.getOpInsert() === true) {
    //      this.viewAlert = super.getOpInsert();
    //      super.showAlertInsertMessage();
    //    }
    //    this.checkFormToLeave = true;
    //    this.bandoService.ricercaBando({ calcolaTotali: false }).subscribe(res => {
    //      res.bandi.forEach((element: any) => {
    //        this.bandi.push(element.bando);
    //      });
    //    });
    //    this.statProService.getAllStatiProgetto().subscribe((res: any) => {
    //      this.statPro = res;
    //    });
    //    this.staFinService.getAllStatiFinanziamento().subscribe((res: any) => {
    //      this.statFin = res;
    //    });
    //    this.munService.getAllMunicipi().subscribe((res: any) => {
    //      this.municipi = res;
    //    });
    //    if (this.activatedRoute.snapshot.url.length > 0 && this.activatedRoute.snapshot.url[0].path === 'new') {
    //      this.modifica = false;
    //      this.pagePath = this.activatedRoute.snapshot.url[0].path;
    //      this.id = '';
    //    } else {
    //      this.modifica = true;
    //      this.id = this.activatedRoute.snapshot.paramMap.get('id') ?? '';
    //      this.pagePath = '';
    //    }
    //    if (this.viewAlert || this.viewError) {
    //      setTimeout(() => {
    //        this.viewError = false;
    //        this.alertService.showError = false;
    //        this.viewAlert = false;
    //        this.alertService.showAlert = false;
    //      }, 5000);
    //    }
    //    //se modifica è true, esegui chiamata dettaglio utente e inizializza il form con i dati ricevuti
    //    if (this.modifica) {
    //      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
    //      this.progService.getProgetto(this.id).subscribe((res: IProgDetail) => {
    //        this.curProg = res;
    //
    //        this.formProg = this._fb.group({
    //          id: [this.curProg.progetto.id],
    //          idStapro: [this.curProg.progetto.idStapro],
    //          idStafin: [this.curProg.progetto.idStafin],
    //          idTipfin: [this.curProg.progetto.idTipfin],
    //          idMacpro: [this.curProg.progetto.idMacpro],
    //          idBan: [this.curProg.progetto.idBan],
    //          idTem: [this.curProg.progetto.idTem],
    //          idMun: [this.curProg.progetto.idMun],
    //          idDir: [this.curProg.progetto.idDir],
    //          idPropad: [this.curProg.progetto.idPropad],
    //          iddecpro: [this.curProg.progetto.iddecpro],
    //          impimppre: [this.curProg.progetto.impimppre],
    //          impimpric: [this.curProg.progetto.impimpric],
    //          impmanpre: [this.curProg.progetto.impmanpre],
    //          imprevpre: [this.curProg.progetto.imprevpre],
    //          impaccpre: [this.curProg.progetto.impaccpre],
    //          livpro: [this.curProg.progetto.livpro],
    //          codpro: [this.curProg.progetto.codpro, [Validators.maxLength(25), Validators.required]],
    //          despro: [this.curProg.progetto.despro, [Validators.maxLength(250), Validators.required]],
    //          stapro: [this.curProg.progetto.idStapro, [Validators.required]],
    //          stafin: [this.curProg.progetto.idStafin, [Validators.required]],
    //          flgopeavv: [this.curProg.progetto.flgopeavv === 'S' ? true : false],
    //          proTipfinByIdTipfin: [this.curProg.progetto.proTipfinByIdTipfin, [Validators.required]],
    //          proBanByIdBan: [this.curProg.progetto.proBanByIdBan],
    //          proMacproByIdMacpro: [this.curProg.progetto.proMacproByIdMacpro, [Validators.required]],
    //          progettoPadre: [this.curProg.progettoPadre],
    //          proTemByIdTem: [this.curProg.progetto.proTemByIdTem],
    //          progettoDirezione: [this.curProg.progettoDirezione, [Validators.required]],
    //          codappren: [this.curProg.progetto.codappren, [Validators.maxLength(25)]],
    //          codcup: [this.curProg.progetto.codcup, [Validators.maxLength(15)]],
    //          codcig: [this.curProg.progetto.codcig, [Validators.maxLength(10)]],
    //          codcui: [this.curProg.progetto.codcui, [Validators.maxLength(25)]],
    //          tippro: [this.curProg.progetto.tippro, [Validators.maxLength(3)]],
    //          codtippro: [this.curProg.progetto.codtippro, [Validators.maxLength(25)]],
    //          codgami: [this.curProg.progetto.codgami, [Validators.maxLength(20)]],
    //          veraff: [this.curProg.progetto.veraff, [Validators.maxLength(250)]],
    //          estaff: [this.curProg.progetto.estaff, [Validators.maxLength(250)]],
    //          notaff: [this.curProg.progetto.notaff, [Validators.maxLength(250)]],
    //          desaffhou: [this.curProg.progetto.desaffhou, [Validators.maxLength(250)]],
    //          delcan: [this.curProg.progetto.delcan, [Validators.maxLength(250)]],
    //          risorse: this._fb.array([]),
    //          risorseLivelloBasso: this._fb.array([]),
    //          risorseDiCui: this._fb.array([]),
    //          notpro: [this.curProg.progetto.notpro, [Validators.maxLength(500)]],
    //          notpre: [this.curProg.progetto.notpre, [Validators.maxLength(250)]],
    //          nil: [this.curProg.progetto.nil],
    //          impigv: [this.transformValueImporto(this.curProg.progetto.impigv)],
    //          rifigv: [this.curProg.progetto.rifigv, [Validators.maxLength(250)]],
    //          impigvdel: [this.transformValueImporto(this.curProg.progetto.impigvdel)],
    //          notigv: [this.curProg.progetto.notigv, [Validators.maxLength(250)]],
    //          infAgg: this._fb.array([]),
    //        });
    //
    //        //aggiunta risorse
    //        this.curProg.risorse.forEach((element: IRisorse) => {
    //          this.addRisorseFormGroup(element);
    //        });
    //        //aggiunta risorse livello basso
    //        this.curProg.risorseLivelloBasso?.forEach((element: IRisorseBasso) => {
    //          this.addRisorseLivelloBassoFormGroup(element);
    //        });
    //        //aggiunta risorse di cui
    //        this.curProg.risorseDiCui.forEach((element: IRisorse) => {
    //          this.addRisorseDiCuiFormGroup(element);
    //        });
    //        //aggiunta informazioni aggiuntive
    //        this.curProg.infoAggiuntive.forEach((element: IInfAgg) => {
    //          this.addInfAggForm(element);
    //        });
    //        //console.log(this.curProg);
    //      });
    //    } else {
    //      this.stringSalva = this.translateService.instant('global.buttons.labelNew');
    //      this.formProg = this._fb.group({
    //        idStapro: [],
    //        idStafin: [],
    //        idTipfin: [],
    //        idDir: [],
    //        idMacpro: [],
    //        idBan: [],
    //        idMun: [],
    //        idPropad: [],
    //        idTem: [],
    //        codpro: [null, [Validators.maxLength(25), Validators.required]],
    //        despro: [null, [Validators.maxLength(250), Validators.required]],
    //        stapro: [null, [Validators.required]],
    //        stafin: [null, [Validators.required]],
    //        flgopeavv: [false],
    //        proTipfinByIdTipfin: [null, [Validators.required]],
    //        proBanByIdBan: [],
    //        proMacproByIdMacpro: [null, [Validators.required]],
    //        progettoPadre: [],
    //        proTemByIdTem: [],
    //        progettoDirezione: [null, [Validators.required]],
    //        codappren: [null, [Validators.maxLength(25)]],
    //        codcup: [null, [Validators.maxLength(15)]],
    //        codcig: [null, [Validators.maxLength(10)]],
    //        codcui: [null, [Validators.maxLength(25)]],
    //        tippro: [null, [Validators.maxLength(3)]],
    //        codtippro: [null, [Validators.maxLength(25)]],
    //        codgami: [null, [Validators.maxLength(20)]],
    //        veraff: [null, [Validators.maxLength(250)]],
    //        estaff: [null, [Validators.maxLength(250)]],
    //        notaff: [null, [Validators.maxLength(250)]],
    //        desaffhou: [null, [Validators.maxLength(250)]],
    //        delcan: [null, [Validators.maxLength(250)]],
    //        risorse: this._fb.array([]),
    //        risorseLivelloBasso: this._fb.array([]),
    //        risorseDiCui: this._fb.array([]),
    //        notpro: [null, [Validators.maxLength(500)]],
    //        notpre: [null, [Validators.maxLength(250)]],
    //        nil: [],
    //        impigv: [this.transformValueImporto(0.0)],
    //        rifigv: [null, [Validators.maxLength(250)]],
    //        impigvdel: [this.transformValueImporto(0.0)],
    //        notigv: [null, [Validators.maxLength(250)]],
    //        infAgg: this._fb.array([]),
    //      });
    //    }
  }

  createRisorseFormGroup(risorsa: IRisorse): FormGroup {
    return new FormGroup({
      id: new FormControl(risorsa.id === 0 ? null : risorsa.id),
      idTipimp: new FormControl(risorsa.proTipimpByIdTipimp.destipimp),
      idPro: new FormControl(risorsa.idPro),
      idTipimpPatch: new FormControl(risorsa.idTipimp),
      imppro: new FormControl(this.transformValueImporto(risorsa.imppro)),
      notimp: new FormControl(risorsa.notimp, [Validators.maxLength(250), StringValidator.patternValidator()]),
    });
  }

  createRisorseLivelloBassoFormGroup(risorsa: IRisorseBasso): FormGroup {
    return new FormGroup({
      destipimp: new FormControl(risorsa.desTipImp),
      imppro: new FormControl(this.transformValueImporto(risorsa.sumImpPro)),
    });
  }

  createInfAggFormGroup(infAgg: IInfAgg): FormGroup {
    if (infAgg.id !== 0) {
      return new FormGroup({
        id: new FormControl(infAgg.id),
        idTipinfagg: new FormControl(infAgg.idTipinfagg),
        idPro: new FormControl(infAgg.idPro),
        destipinfagg: new FormControl(infAgg.proTipinfaggByIdTipinfagg.destipinfagg),
        infagg: new FormControl(infAgg.infagg, StringValidator.patternValidator()),
      });
    } else {
      return new FormGroup({
        idTipinfagg: new FormControl(infAgg.idTipinfagg),
        idPro: new FormControl(infAgg.idPro),
        destipinfagg: new FormControl(infAgg.proTipinfaggByIdTipinfagg.destipinfagg),
        infagg: new FormControl(infAgg.infagg, StringValidator.patternValidator()),
      });
    }
  }

  addRisorseFormGroup(risorsa: IRisorse): void {
    const risorse = this.formProg.get('risorse') as FormArray;
    risorse.push(this.createRisorseFormGroup(risorsa));
  }

  addRisorseLivelloBassoFormGroup(risorsa: IRisorseBasso): void {
    const risorse = this.formProg.get('risorseLivelloBasso') as FormArray;
    risorse.push(this.createRisorseLivelloBassoFormGroup(risorsa));
  }

  addRisorseDiCuiFormGroup(risorsaDiCui: IRisorse): void {
    const risorse = this.formProg.get('risorseDiCui') as FormArray;
    risorse.push(this.createRisorseFormGroup(risorsaDiCui));
  }

  addInfAggForm(infAgg: IInfAgg): void {
    const risorse = this.formProg.get('infAgg') as FormArray;
    risorse.push(this.createInfAggFormGroup(infAgg));
  }

  getRisorseFormArray(): FormArray<any> {
    return this.formProg.get('risorse') as FormArray;
  }

  getRisorseLivBaFormArray(): FormArray<any> {
    return this.formProg.get('risorseLivelloBasso') as FormArray;
  }

  getRisorseDiCuiFormArray(): FormArray<any> {
    return this.formProg.get('risorseDiCui') as FormArray;
  }

  getInfAggFormArray(): FormArray<any> {
    return this.formProg.get('infAgg') as FormArray;
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  setModalSett(): void {
    this.showAssessorati = false;
    this.autocompleteSett.setValue('');
  }

  setModalAss(): void {
    this.showAssessorati = true;
    this.autocomplete.setValue('');
  }

  //metodo che imposta il valore del settore dopo il click su una riga del modale
  setValueSettore(codset: any): void {
    this.formProg.get('codset')?.setValue(codset);
    this.formProg.markAsDirty();
  }

  //metodo che imposta il valore dell'assessorato dopo il click su una riga del modale
  setValueAssessorato(codass: any): void {
    this.formProg.get('codass')?.setValue(codass);
    this.formProg.markAsDirty();
  }

  //Ricarica dati
  reload(): void {
    if (!this.canDeactivate()) {
      const msg = this.translateService.instant('modals.refresh');
      this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
      this.modalConfirmRef.result.then(result => {
        if (result === true) {
          this.ngOnInit();
        }
      });
    } else {
      this.ngOnInit();
    }
  }

  showAlert(): boolean {
    return this.alertService.showAlert;
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formProg.controls[elemento];
  }

  patchFormProgetto(): void {
    this.formProg.patchValue({
      idStapro: this.formProg.get('stapro')?.value,
      idStafin: this.formProg.get('stafin')?.value,
      idTipfin: this.formProg.get('proTipfinByIdTipfin')?.value ? this.formProg.get('proTipfinByIdTipfin')?.value.id : null,
      idMacpro: this.formProg.get('proMacproByIdMacpro')?.value?.id,
      idBan: this.formProg.get('proBanByIdBan')?.value?.id,
      idPropad: this.formProg.get('progettoPadre')?.value?.id,
      idTem: this.formProg.get('proTemByIdTem')?.value?.id,
      idNil: this.formProg.get('proNilByIdNil')?.value?.id,
      idDir: this.formProg.get('progettoDirezione')?.value?.id,
      flgopeavv: this.formProg.get('flgopeavv')?.value == true ? 'S' : 'N',
      codset: this.formProg.get('codSetAutocomplete')?.value?.codentcon,
      codass: this.formProg.get('codAssAutocomplete')?.value?.codentcon,
    });
    /*this.formProg.removeControl('stapro');
    this.formProg.removeControl('stafin');
    this.formProg.removeControl('proTipfinByIdTipfin');
    this.formProg.removeControl('proMacproByIdMacpro');
    this.formProg.removeControl('progettoDirezione');
    this.formProg.removeControl('proBanByIdBan');
    this.formProg.removeControl('progettoPadre');
    this.formProg.removeControl('proTemByIdTem'); */
  }

  patchImporti(): void {
    this.getRisorseFormArray().controls.forEach(element => {
      console.log(element.value);
      element.patchValue({
        idTipimp: element.value.idTipimpPatch,
        imppro: this.patchValueImporto(element.value.imppro),
      });
    });
    this.getRisorseLivBaFormArray().controls.forEach(element => {
      element.patchValue({ imppro: this.patchValueImporto(element.value.imppro) });
    });
    this.getRisorseDiCuiFormArray().controls.forEach(element => {
      element.patchValue({
        imppro: this.patchValueImporto(element.value.imppro),
        idTipimp: element.value.idTipimpPatch,
      });
    });
    //console.log(this.getRisorseFormArray());
    this.formProg.patchValue({
      impigv: this.patchValueImporto(this.formProg.get('impigv')?.value),
      impigvdel: this.patchValueImporto(this.formProg.get('impigvdel')?.value),
    });
  }

  patchError(): void {
    this.getRisorseFormArray().controls.forEach(element => {
      try {
        element.patchValue({ imppro: this.transformValueImporto(element.value.imppro) });
      } catch (Exception) {
        console.error(Exception);
      }
    });
    this.getRisorseLivBaFormArray().controls.forEach(element => {
      try {
        element.patchValue({ imppro: this.transformValueImporto(element.value.imppro) });
      } catch (Exception) {
        console.error(Exception);
      }
    });
    this.getRisorseDiCuiFormArray().controls.forEach(element => {
      try {
        element.patchValue({
          imppro: this.transformValueImporto(element.value.imppro),
        });
      } catch (Exception) {
        console.error(Exception);
      }
    });
    this.formProg.patchValue({
      impigv: this.transformValueImporto(this.formProg.get('impigv')?.value),
      impigvdel: this.transformValueImporto(this.formProg.get('impigvdel')?.value),
      flgopeavv: this.formProg.get('flgopeavv')?.value == 'S' ? true : false,
    });
  }

  submitForm(): void {
    const jsog = new JsogService();
    this.patchFormProgetto();
    this.patchImporti();
    if (this.modifica) {
      this.curProg.progetto = this.formProg.value;
      this.curProg.risorse = this.formProg.get('risorse')?.value;
      this.curProg.risorseDiCui = this.formProg.get('risorseDiCui')?.value;
      this.curProg.infoAggiuntive = this.formProg.get('infAgg')?.value;
      delete this.curProg.progetto.risorse;
      delete this.curProg.progetto.risorseDiCui;
      delete this.curProg.progetto.infAgg;
      delete this.curProg.progetto.risorseLivelloBasso;
      delete this.curProg.progettoPadre;
      delete this.curProg.risorseLivelloBasso;
      //console.log(this.curProg);
      this.isSaving = true;
      // @ts-ignore
      this.progService.modificaProgetto(<IProgDetail>jsog.deserialize(this.curProg)).subscribe(
        res => {
          this.isSaving = false;
          this.showAlertMessage(res);
          this.reload();
        },
        err => {
          this.isSaving = false;
          this.showAlertError(err);
          this.patchError();
        }
      );
    } else {
      this.progService.inserisciProgetto(<IProg>jsog.deserialize(this.formProg.value)).subscribe(
        res => {
          super.setOpInsert(true);
          this.showAlertMessage(res);
          this.router.navigate([`gest-progetti/${res.id}/viewDetail`]);
        },
        err => {
          this.showAlertError(err);
          this.patchError();
        }
      );
    }
  }

  //Metodo richiamato dal pulsante di cancellazione
  eliminaProgetto(): void {
    const msg = this.translateService.instant('modals.canc');
    this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
    this.modalConfirmRef.result.then(result => {
      if (result === true) {
        this.progService.cancellaProgetto(this.curProg.progetto.id).subscribe(
          res => {
            super.setOpDelete(true);
            this.alertService.testo = res.userMessage;
            this.alertService.showAlert = true;
            this.router.navigate([`/gest-progetti`], { relativeTo: this.activatedRoute.parent });
          },
          err => {
            this.showAlertError(err);
          }
        );
      }
    });
  }

  //inserimento di un progetto figlio
  insProFiglio(): void {
    if (!this.formProg.pristine || !this.formProg.valid) {
      this.showAlertError({ error: { userMessage: 'Salvare le modifiche prima di inserire un progetto figlio' } });
    } else {
      this.progService.inserisciProgettoFiglio(Number(this.id)).subscribe(
        res => {
          this.showAlertMessage(res);
          super.setOpInsert(true);
          this.router.navigate([`gest-progetti/${res.id}/viewDetail`]);
        },
        err => {
          this.showAlertError(err);
          this.patchError();
        }
      );
    }
  }

  aggImpRisFon(): void {
    if (!this.formProg.pristine || !this.formProg.valid) {
      this.showAlertError({ error: { userMessage: 'Salvare le modifiche prima di procedere' } });
    } else {
      this.progService.modificaImportoRisorseFiglio(Number(this.id)).subscribe(
        res => {
          this.showAlertMessage(res);
          super.setOpInsert(true);
          this.reload();
        },
        err => {
          this.showAlertError(err);
          this.patchError();
        }
      );
    }
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formProg) {
      return this.formProg.pristine;
    } else {
      return of(true);
    }
  }

  //pulsante per andare agli allegati del progetto corrente
  goToAllegati(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/allegati/PRO`]);
  }

  //pulsante per andare agli accertamenti - impegni del progetto corrente
  goToAccImp(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/acc-imp`]);
  }

  //pulsante per andare ai capitoli del progetto corrente
  goToCap(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/capitoli`]);
  }

  //pulsante per andare ai responsabili del progetto corrente
  goToRes(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/responsabili`]);
  }

  //pulsante per andare agli avanzamenti del progetto corrente
  goToAva(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/gest-avanzamenti`]);
  }

  //pulsante per andare alle ddr del progetto corrente
  goToDdr(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/ddr`]);
  }

  //pulsante per andare ai commenti - richieste del progetto corrente
  goToCommRic(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/gest-commenti-richieste`]);
  }

  //pulsante per andare alle previsioni del progetto corrente
  goToPrev(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/gest-previsioni`]);
  }

  //pulsante per andare ai totali del progetto corrente
  goToTotali(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/ProPro/totali`]);
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
