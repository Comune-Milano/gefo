import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { TipFinService } from 'app/services/tip-fin.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, Observable, of, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { ProTemService } from '../../services/protem.service';
import { BandoService } from '../../services/bando.service';
import { StaBanService } from '../../services/staban.service';
import { IBan } from 'app/models/bando.model';
import { IStaBan } from 'app/models/staban.model';
import { formatDate } from '@angular/common';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-bandi',
  templateUrl: './bandi-update.component.html',
  styleUrls: ['./bandi.component.scss'],
})
export class BandiUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curBando!: IBan;
  formBando!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  tipiFin: Array<any> = [];
  tematiche: Array<any> = [];
  statBan: IStaBan[] = [];
  isSaving: boolean = false;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private tipFinService: TipFinService,
    private bandoService: BandoService,
    protected stabanService: StaBanService,
    private proTemService: ProTemService,
    private _fb: FormBuilder,
    private customDialogsService: CustomDialogsService,
    protected translateService: TranslateService,
    protected rolesCheckService: RolesCheckService,
    protected ngbModal: NgbModal
  ) {
    super(injector);
    this.modifica = false;
    this.router = injector.get(Router);
  }

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
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    if (super.getOpInsert() === true) {
      this.viewAlert = super.getOpInsert();
      super.showAlertInsertMessage();
    }
    this.checkFormToLeave = true;
    this.stabanService
      .getAllStatiBando()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((res: any) => {
        this.statBan = res;
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
    //se modifica è true, esegui chiamata dettaglio utente e inizializza il form con i dati ricevuti
    if (this.modifica) {
      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      });
      this.bandoService
        .getBando(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: IBan) => {
            this.curBando = res;
            this.formBando = this._fb.group({
              id: [this.curBando.id],
              idTipfin: [this.curBando.idTipfin],
              idTem: [this.curBando.idTem],
              idStaban: [this.curBando.idStaban],
              codban: [this.curBando.codban, [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
              desban: [this.curBando.desban, [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()]],
              staban: [this.curBando.idStaban],
              proTipfinByIdTipfin: [this.curBando.proTipfinByIdTipfin, [Validators.required]],
              proTemByIdTem: [this.curBando.proTemByIdTem],
              desent: [this.curBando.desent, [Validators.maxLength(250), StringValidator.patternValidator()]],
              desprvent: [this.curBando.desprvent, [Validators.maxLength(250), StringValidator.patternValidator()]],
              refmin: [this.curBando.refmin, [Validators.maxLength(250), StringValidator.patternValidator()]],
              desben: [this.curBando.desben, [Validators.maxLength(250), StringValidator.patternValidator()]],
              impinv: [this.transformValueImporto(this.curBando.impinv), [Validators.required]],
              impban: [this.transformValueImporto(this.curBando.impban), [Validators.required]],
              impmaspro: [this.transformValueImporto(this.curBando.impmaspro), [Validators.required]],
              impmaspar: [this.transformValueImporto(this.curBando.impmaspar), [Validators.required]],
              dtapubban: [this.curBando.dtapubban ? formatDate(this.curBando.dtapubban, 'yyyy-MM-dd', 'it') : null],
              dtachiatt: [this.curBando.dtachiatt ? formatDate(this.curBando.dtachiatt, 'yyyy-MM-dd', 'it') : null],
              dtascaban: [this.curBando.dtascaban ? formatDate(this.curBando.dtascaban, 'yyyy-MM-dd', 'it') : null],
              desdecfin: [this.curBando.desdecfin, [Validators.maxLength(250), StringValidator.patternValidator()]],
            });
          },
          err => {
            this.checkErrorStatus(err);
            this.showAlertError(err);
          }
        );
    } else {
      this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
      });
      this.formBando = this._fb.group({
        idTipfin: [],
        idTem: [],
        idStaban: [],
        codban: [null, [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
        desban: [null, [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()]],
        staban: [],
        proTipfinByIdTipfin: [null, [Validators.required]],
        proTemByIdTem: [],
        desent: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
        desprvent: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
        refmin: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
        desben: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
        impinv: [this.transformValueImporto(0), [Validators.required, StringValidator.patternValidator()]],
        impban: [this.transformValueImporto(0), [Validators.required]],
        impmaspro: [this.transformValueImporto(0), [Validators.required]],
        impmaspar: [this.transformValueImporto(0), [Validators.required]],
        dtapubban: [],
        dtachiatt: [],
        dtascaban: [],
        desdecfin: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
      });
    }
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

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formBando.controls[elemento];
  }

  patchFormBando(): void {
    this.formBando.patchValue({
      idStaban: this.formBando.get('staban')?.value,
      idTipfin: this.formBando.get('proTipfinByIdTipfin')?.value?.id,
      idTem: this.formBando.get('proTemByIdTem')?.value?.id,
    });
    this.formBando.removeControl('staban');
    this.formBando.removeControl('proTipfinByIdTipfin');
    this.formBando.removeControl('proTemByIdTem');
  }

  patchImporti(): void {
    this.formBando.patchValue({
      impinv: this.patchValueImporto(this.formBando.get('impinv')?.value),
      impban: this.patchValueImporto(this.formBando.get('impban')?.value),
      impmaspro: this.patchValueImporto(this.formBando.get('impmaspro')?.value),
      impmaspar: this.patchValueImporto(this.formBando.get('impmaspar')?.value),
    });
  }

  patchError(): void {
    this.formBando.patchValue({
      impinv: this.transformValueImporto(this.formBando.get('impinv')?.value),
      impban: this.transformValueImporto(this.formBando.get('impban')?.value),
      impmaspro: this.transformValueImporto(this.formBando.get('impmaspro')?.value),
      impmaspar: this.transformValueImporto(this.formBando.get('impmaspar')?.value),
    });
  }

  submitForm(): void {
    this.patchFormBando();
    this.patchImporti();
    if (this.modifica) {
      this.curBando = this.formBando.value;
      this.isSaving = true;
      this.bandoService
        .modificaBando(this.curBando)
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
      this.bandoService
        .inserisciBando(this.formBando.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`bandi/${res.id}/viewDetail`]);
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
        this.bandoService
          .cancellaBando(this.curBando.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              //this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
              this.router.navigate([`/bandi`], { relativeTo: this.activatedRoute.parent });
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formBando) {
      return this.formBando.pristine;
    } else {
      return of(true);
    }
  }

  goToTotali(): void {
    this.router.navigate([`bandi/${this.id}/viewDetail/ProBan/totali`]);
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
