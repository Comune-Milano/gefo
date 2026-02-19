import { Component, HostListener, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { TipFinService } from 'app/services/tip-fin.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, Observable, of, OperatorFunction, Subject, switchMap, take, takeUntil } from 'rxjs';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { ProTemService } from '../../services/protem.service';
import { ProgService } from '../../services/gest-progetti.service';
import { Dictionary } from '../../shared/utils/dictionary.class';
import { AvaService } from 'app/services/avanzamenti.service';
import { IAvanzamentoDetail } from 'app/models/avaDetail.model';
import { formatDate, formatNumber } from '@angular/common';
import { ListaValoriService } from 'app/services/listaValori.service';
import { ILisVal } from 'app/models/lisVal.model';
import { JsogService } from 'jsog-typescript';
import { IProTipfas } from 'app/models/proFas.model';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { relative } from '@angular/compiler-cli';
import { ParametroService } from '../../services/parametro.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-bandi',
  templateUrl: './avanzamenti-update.component.html',
  styleUrls: ['./avanzamenti.component.scss'],
})
export class AvanzamentiUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curAva!: IAvanzamentoDetail;
  formAva!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  tipiFin: Array<any> = [];
  tematiche: Array<any> = [];
  codiceFase = '';
  lisValFasInt: Array<ILisVal> = [];
  lisValLivCri: Array<ILisVal> = [];
  lisValTipApp: Array<ILisVal> = [];
  lisValStaAnt: Array<ILisVal> = [];
  lisValTipFas: Array<ILisVal> = [];
  tipiFas: Array<IProTipfas> = [];
  formFasi: FormArray;
  curIndex: number;
  tempRow!: any;
  columns;
  dizionarioColori = new Dictionary();
  isSaving = false;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private tipFinService: TipFinService,
    private progService: ProgService,
    protected avanzamentiService: AvaService,
    protected rolesCheckService: RolesCheckService,
    protected parametroService: ParametroService,
    protected lisValService: ListaValoriService,
    private proTemService: ProTemService,
    private _fb: FormBuilder,
    private customDialogsService: CustomDialogsService,
    protected translateService: TranslateService,
    protected ngbModal: NgbModal
  ) {
    super(injector);
    this.formFasi = this._fb.array([]);
    this.modifica = false;
    this.curIndex = 0;
    this.router = injector.get(Router);
    this.columns = new Map([
      ['tipfas', true],
      ['dtaoriini', true],
      ['dtaorifin', true],
      ['dtapreini', true],
      ['dtaprefin', true],
      ['dtaeffini', true],
      ['dtaefffin', true],
      ['fas', true],
      ['perfas', true],
      ['mil', true],
      ['permil', true],
      ['notfas', true],
    ]);
  }

  //ricerca con autocomplete dei progetti
  searchPro: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
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
  searchTipFin: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
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

  //ricerca con autocomplete delle tematiche
  searchTem: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.proTemService.ricercaTematica({ autocomplete: term })))
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
    this.initColori();
    this.formFasi.clear();
    const jsog = new JsogService();
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    if (super.getOpInsert() === true) {
      this.viewAlert = super.getOpInsert();
      super.showAlertInsertMessage();
    }
    this.checkFormToLeave = true;
    if (this.activatedRoute.snapshot.url.length > 0 && this.activatedRoute.snapshot.url[0].path === 'new') {
      this.modifica = false;
      this.pagePath = this.activatedRoute.snapshot.url[0].path;
      this.id = '';
    } else {
      this.modifica = true;
      this.id = this.activatedRoute.snapshot.paramMap.get('idAva') ?? '';
      this.pagePath = '';
    }
    //recupero liste valori
    this.lisValService
      .getListaValoriFaseIntervento()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.lisValFasInt = res;
      });
    this.lisValService
      .getListaValoriLivelloCriticita()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.lisValLivCri = res;
      });
    this.lisValService
      .getListaValoriTipoAppalto()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.lisValTipApp = res;
      });
    this.lisValService
      .getListaValoriStatoAnticipazione()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.lisValStaAnt = res;
      });
    this.lisValService
      .getListaValoriTipologiaFasi()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.lisValTipFas = res;
      });
    //recupero tipi fase
    this.avanzamentiService
      .getTipoFase()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.tipiFas = res;
      });
    //se modifica è true, esegui chiamata dettaglio utente e inizializza il form con i dati ricevuti
    if (this.modifica) {
      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      });
      this.avanzamentiService
        .getAvanzamento(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.curAva = <IAvanzamentoDetail>jsog.deserialize(res);
            console.log(this.curAva);
            this.formAva = this._fb.group({
              id: [res.avanzamento.id],
              proProByIdPro: [res.avanzamento.proProByIdPro],
              progettoPadreLabel: [res.progettoPadreLabel],
              dtarilava: [formatDate(res.avanzamento.dtarilava, 'yyyy-MM-dd', 'it')],
              usrCreate: [res.avanzamento.usrCreate],
              usrLstupd: [res.avanzamento.usrLstupd],
              desstaava: [res.avanzamento.desstaava, [Validators.required, StringValidator.patternValidator()]],
              prcava: [res.avanzamento.prcava, [Validators.required]],
              dtainlav: [res.datinilav ? formatDate(res.datinilav, 'yyyy-MM-dd', 'it') : null],
              dtafinlav: [res.datfinlav ? formatDate(res.datfinlav, 'yyyy-MM-dd', 'it') : null],
              id_lisvalfasint: [res.avanzamento.idLisvalfasint, [Validators.required]],
              id_lisvallivcri: [res.avanzamento.idLisvallivcri, [Validators.required]],
              notcri: [res.avanzamento.notcri, [StringValidator.patternValidator()]],
              id_lisvaltipapp: [res.avanzamento.idLisvaltipapp, [Validators.required]],
              desapp: [res.avanzamento.desapp, [StringValidator.patternValidator()]],
              id_lisvalstaant: [res.avanzamento.idLisvalstaant, [Validators.required]],
              impant: [this.transformValueImporto(res.avanzamento.impant), [Validators.required]],
              id_lisvaltipolfas: [res.avanzamento.idlisvaltipolfas],
            });
            this.curAva.fasi.forEach(fas => {
              this.tempRow = this._fb.group({
                id: [fas.fase.id],
                idAva: [fas.fase.idAva],
                idFase: [fas.tipfas.id],
                idTipfas: [fas.fase.idTipfas],
                ordfas: [fas.tipfas.ordfas],
                desfas: [fas.tipfas.desfas],
                tipcon: [fas.tipfas.tipcon],
                tipfas: [fas.tipfas.tipfas],
                dtaoriini: [fas.fase.dtainiori ? formatDate(fas.fase.dtainiori, 'yyyy-MM-dd', 'it') : null, [Validators.required]],
                dtaorifin: [fas.fase.dtafinori ? formatDate(fas.fase.dtafinori, 'yyyy-MM-dd', 'it') : null, [Validators.required]],
                dtapreini: [fas.fase.dtainipre ? formatDate(fas.fase.dtainipre, 'yyyy-MM-dd', 'it') : null],
                dtaprefin: [fas.fase.dtafinpre ? formatDate(fas.fase.dtafinpre, 'yyyy-MM-dd', 'it') : null],
                dtaeffini: [fas.fase.dtainieff ? formatDate(fas.fase.dtainieff, 'yyyy-MM-dd', 'it') : null],
                dtaefffin: [fas.fase.dtafineff ? formatDate(fas.fase.dtafineff, 'yyyy-MM-dd', 'it') : null],
                semaforoFase: [fas.semaforoFase.colore],
                semaforoFaseDes: [fas.semaforoFase.descrizione],
                perfas: [this.transformPerc(fas.semaforoFase.percentuale)],
                semaforoMil: [fas.semaforoMilestone.colore],
                semaforoMilDes: [fas.semaforoMilestone.descrizione],
                permil: [this.transformPerc(fas.semaforoMilestone.percentuale)],
                notfas: [fas.fase.notfas, [StringValidator.patternValidator()]],
                isNew: false,
              });
              this.formFasi.push(this.tempRow);
            });
          },
          (error: any) => {
            console.log(error);
          },
          () => {
            this.parametroService
              .getParametroByCodice('FaseDateDisabilitatePerControllati')
              .pipe(takeUntil(this.ngUnsubscribe))
              .subscribe(
                resPar => {
                  console.log(resPar);
                  if (resPar === 'S' && this.$sessionStorage.retrieve('tipcondat') === 'C') {
                    this.formFasi.controls.forEach(fas => {
                      fas.get('dtaoriini')?.disable();
                      fas.get('dtaorifin')?.disable();
                    });
                  }
                },
                error => {
                  console.log(error);
                }
              );
          }
        );
    } else {
      this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
      });
      this.formAva = this._fb.group({
        proProByIdPro: ['', [Validators.required]],
        desstaava: ['', [Validators.required, StringValidator.patternValidator()]],
        prcava: [0, [Validators.required]],
        id_lisvalfasint: ['', [Validators.required]],
        id_lisvallivcri: [''],
        notcri: ['', [StringValidator.patternValidator()]],
        id_lisvaltipapp: ['', [Validators.required]],
        desapp: [null, [StringValidator.patternValidator()]],
        id_lisvalstaant: ['', [Validators.required]],
        impant: [this.transformValueImporto(0), [Validators.required]],
        id_lisvaltipolfas: [''],
      });
      if (this.activatedRoute.snapshot.paramMap.get('id')) {
        this.progService
          .getProgetto(<string>this.activatedRoute.snapshot.paramMap.get('id'))
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(res => {
            this.formAva.get('proProByIdPro')?.setValue(res.progetto);
          });
      }
    }
  }

  initColori(): void {
    this.dizionarioColori.set('rosso', 'red');
    this.dizionarioColori.set('giallo', '#ffdf00');
    this.dizionarioColori.set('verde', 'green');
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
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

  addFase(): void {
    this.tempRow = this._fb.group({
      idAva: [this.curAva.avanzamento.id ? this.curAva.avanzamento.id : null],
      idTipfas: ['', [Validators.required]],
      dtaoriini: ['', [Validators.required]],
      dtaorifin: ['', [Validators.required]],
      dtapreini: [],
      dtaprefin: [],
      dtaeffini: [],
      dtaefffin: [],
      semaforoFase: [],
      semaforoFaseDes: [null],
      perfas: [null],
      semaforoMil: [null],
      semaforoMilDes: [],
      permil: [],
      notfas: [],
      isNew: true,
    });
    this.formFasi.push(this.tempRow);
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formAva.controls[elemento];
  }

  patchImporti(): void {
    this.formAva.patchValue({
      impant: this.patchValueImporto(this.formAva.get('impant')?.value),
    });
  }

  patchError(): void {
    this.formAva.patchValue({
      impant: this.transformValueImporto(this.formAva.get('impant')?.value),
    });
  }

  getColumnView(key: string): boolean {
    return <boolean>this.columns.get(key);
  }

  setColumnView(key: string, value: boolean): void {
    this.columns.set(key, value);
  }

  enableAllColumns(): void {
    for (const key of this.columns.keys()) {
      this.columns.set(key, true);
    }
  }

  //metodo che restituisce un oggetto di tipo AvanzamentoDetail
  createAvaObj(): any {
    this.patchImporti();
    if (this.modifica) {
      const fasi: Array<any> = [];
      this.formFasi.controls.forEach(fas => {
        fasi.push({
          fase: {
            id: fas.get('id')?.value,
            idAva: fas.get('idAva')?.value,
            idTipfas: Number(fas.get('idTipfas')?.value),
            dtainieff: fas.get('dtaeffini')?.value,
            dtafineff: fas.get('dtaefffin')?.value,
            dtainiori: fas.get('dtaoriini')?.value,
            dtafinori: fas.get('dtaorifin')?.value,
            dtainipre: fas.get('dtapreini')?.value,
            dtafinpre: fas.get('dtaprefin')?.value,
            notfas: fas.get('notfas')?.value,
          },
          tipfas: {
            id: fas.get('idFase')?.value,
            desfas: fas.get('desfas')?.value,
            ordfas: fas.get('ordfas')?.value,
            tipcon: fas.get('tipcon')?.value,
            tipfas: fas.get('tipfas')?.value,
          },
        });
      });
      const Ava: Object = {
        avanzamento: {
          id: this.curAva.avanzamento.id,
          idPro: this.curAva.avanzamento.idPro,
          nrover: this.curAva.avanzamento.nrover,
          dtarilava: this.curAva.avanzamento.dtarilava,
          desstaava: this.formAva.get('desstaava') ? this.formAva.get('desstaava')?.value : null,
          prcava: this.formAva.get('prcava') ? this.formAva.get('prcava')?.value : null,
          idLisvalfasint: this.formAva.get('id_lisvalfasint') ? this.formAva.get('id_lisvalfasint')?.value : null,
          idlisvaltipolfas: this.formAva.get('id_lisvaltipolfas') ? this.formAva.get('id_lisvaltipolfas')?.value : null,
          idLisvallivcri: this.formAva.get('id_lisvallivcri') ? this.formAva.get('id_lisvallivcri')?.value : null,
          notcri: this.formAva.get('notcri') ? this.formAva.get('notcri')?.value : null,
          idLisvaltipapp: this.formAva.get('id_lisvaltipapp') ? this.formAva.get('id_lisvaltipapp')?.value : null,
          desapp: this.formAva.get('desapp') ? this.formAva.get('desapp')?.value : null,
          idLisvalstaant: this.formAva.get('id_lisvalstaant') ? this.formAva.get('id_lisvalstaant')?.value : null,
          impant: this.formAva.get('impant') ? this.formAva.get('impant')?.value : null,
          usrCreate: this.formAva.get('usrCreate') ? this.formAva.get('usrCreate')?.value : null,
          usrLstupd: this.formAva.get('usrLstupd') ? this.formAva.get('usrLstupd')?.value : null,
        },
        progettoPadreLabel: this.curAva.progettoPadreLabel,
        datinilav: this.formAva.get('dtainlav') ? this.formAva.get('dtainlav')?.value : null,
        datfinlav: this.formAva.get('dtafinlav') ? this.formAva.get('dtafinlav')?.value : null,
        fasi: fasi,
        //fasi: []
      };
      return Ava;
    } else {
      this.patchLisval();
      const Ava: Object = {
        idPro: this.formAva.get('proProByIdPro')?.value?.id ? (this.formAva.get('proProByIdPro')?.value).id : null,
        nrover: 0,
        desstaava: this.formAva.get('desstaava') ? this.formAva.get('desstaava')?.value : null,
        prcava: this.formAva.get('prcava') ? this.formAva.get('prcava')?.value : null,
        idLisvalfasint: this.formAva.get('id_lisvalfasint') ? this.formAva.get('id_lisvalfasint')?.value : null,
        idlisvaltipolfas: this.formAva.get('id_lisvaltipolfas') ? this.formAva.get('id_lisvaltipolfas')?.value : null,
        idLisvallivcri: this.formAva.get('id_lisvallivcri') ? this.formAva.get('id_lisvallivcri')?.value : null,
        notcri: this.formAva.get('notcri') ? this.formAva.get('notcri')?.value : null,
        idLisvaltipapp: this.formAva.get('id_lisvaltipapp') ? this.formAva.get('id_lisvaltipapp')?.value : null,
        desapp: this.formAva.get('desapp') ? this.formAva.get('desapp')?.value : null,
        idLisvalstaant: this.formAva.get('id_lisvalstaant') ? this.formAva.get('id_lisvalstaant')?.value : null,
        impant: this.formAva.get('impant') ? this.formAva.get('impant')?.value : null,
      };
      return Ava;
    }
  }

  patchLisval(): void {
    this.formAva.patchValue({
      id_lisvalfasint: Number.parseInt(this.formAva.get('id_lisvalfasint')?.value, 10),
      id_lisvaltipolfas: Number.parseInt(this.formAva.get('id_lisvaltipolfas')?.value, 10),
      id_lisvallivcri: Number.parseInt(this.formAva.get('id_lisvallivcri')?.value, 10),
      id_lisvaltipapp: Number.parseInt(this.formAva.get('id_lisvaltipapp')?.value, 10),
      id_lisvalstaant: Number.parseInt(this.formAva.get('id_lisvalstaant')?.value, 10),
    });
  }

  submitForm(): void {
    if (this.modifica) {
      this.isSaving = true;
      this.avanzamentiService
        .modificaAvanzamento(this.createAvaObj())
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
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
      this.avanzamentiService
        .inserisciAvanzamento(this.createAvaObj())
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            const redirectUrl = this.activatedRoute.snapshot.url;
            this.router.navigate([`${res.id}/viewDetail`], { relativeTo: this.activatedRoute.parent });
          },
          err => {
            this.showAlertError(err);
            this.patchError();
          }
        );
    }
  }

  //Metodo richiamato dal pulsante di cancellazione
  submitDelete(): void {
    const msg = this.translateService.instant('modals.canc');
    this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
    this.modalConfirmRef.result.then(result => {
      if (result === true) {
        console.log(this.activatedRoute.fragment);
        /*this.avanzamentiService.cancellaAvanzamento(this.curAva.avanzamento.id).subscribe(
          res => {
            super.setOpDelete(true);
            this.alertService.testo = res.userMessage;
            this.alertService.showAlert = true;
            this.router.navigate([`/`], { relativeTo: this.activatedRoute.parent });
          },
          err => {
            this.showAlertError(err);
          }
        );*/
      }
    });
  }

  // metodo che viene richiamato quando si clicca sul cestino di fianco alla riga
  onRemoveRow(rowIndex: number): void {
    if (this.formFasi.at(rowIndex).get('isNew')?.value == false) {
      this.avanzamentiService
        .cancellaFase(this.formFasi.at(this.curIndex).get('id')?.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.showAlertMessage(res);
            this.formFasi.removeAt(this.curIndex);
            this.reload();
          },
          (err: any) => {
            this.showAlertError(err);
          }
        );
    } else {
      this.formFasi.removeAt(rowIndex);
    }
  }

  //metodo che elimina un avanzamento dal dettaglio avanzamento
  eliminaAvanzamento(): void {
    const msg = this.translateService.instant('modals.canc');
    this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
    this.modalConfirmRef.result.then(result => {
      if (result === true) {
        this.avanzamentiService
          .cancellaAvanzamento(this.curAva.avanzamento.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              if (this.activatedRoute.snapshot.params.id) {
                this.router.navigate([`/gest-progetti/${this.activatedRoute.snapshot.params.id}/viewDetail/gest-avanzamenti`]);
              } else {
                this.router.navigate([`/gest-avanzamenti`]);
              }
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  creaFasi(): void {
    this.avanzamentiService
      .creaFasiAvanzamento(this.curAva.avanzamento.id)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.showAlertMessage(res);
          this.reload();
        },
        (err: any) => {
          this.showAlertError(err);
        }
      );
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave) {
      return this.formAva.pristine;
    } else {
      return of(true);
    }
  }

  goToAllegati(): void {
    this.router.navigate([`allegati/AVA`], { relativeTo: this.activatedRoute });
  }

  goToMilestone(idFas: number): void {
    this.router.navigate([`milestone/${idFas}`], { relativeTo: this.activatedRoute });
  }

  showTabFas(): void {
    const tab1Lbl = document.getElementById('data-ex-tab1-tab');
    tab1Lbl.classList.remove('active');
    const tab1 = document.getElementById('tab1');
    tab1.classList.remove('active');
    const tab2Lbl = document.getElementById('data-ex-tab2-tab');
    tab2Lbl.classList.add('active');
    const tab2 = document.getElementById('tab2');
    tab2.classList.add('active');
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
