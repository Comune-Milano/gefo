import { Component, Injector, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
// import { AlertService } from 'app/shared/alert/alert.service';
import { TranslateService } from '@ngx-translate/core';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { TipFinService } from 'app/services/tip-fin.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, map, Observable, of, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { DizMacroProgService } from 'app/services/diz-macro-prog.service';
import { IMacroProg } from 'app/models/macro_prog.model';
import { ItipFin } from 'app/models/tip_fin.model';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { JsogService } from 'jsog-typescript';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './diz-macro-prog-update.component.html',
  styleUrls: ['./diz-macro-prog.component.scss'],
})
export class DizMacroProgUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curMacroProg!: IMacroProg;
  textTipFinA: string = '';
  textTipFinDa: string = '';
  idTipFinDa!: ItipFin;
  idTipFinA!: ItipFin;
  formMacroProg!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private macroProgService: DizMacroProgService,
    private tipFinService: TipFinService,
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
  searchTipFinA: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 2 ? [] : this.tipFinService.ricercaTipoFinanziamentoAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei tipiFinanziamento nell'autocomplete searchTipFinA
  formatterTipFinA = (x: { destipfin: string; codtipfin: string; id: number }) => {
    if (x.id) {
      return `${x.codtipfin + ' - ' + x.destipfin}`;
    }
    return '';
  };

  //ricerca con autocomplete dei tipi finanziamento
  searchTipFinDa: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 2 ? [] : this.tipFinService.ricercaTipoFinanziamentoAutocomplete(term)))
    );
  };

  //gestisce la formattazione dei tipiFinanziamento nell'autocomplete searchTipFin
  formatterTipFinDa = (x: { destipfin: string; codtipfin: string; id: number }) => {
    if (x.id) {
      return `${x.codtipfin + ' - ' + x.destipfin}`;
    }
    return '';
  };

  ngOnInit(): void {
    const jsog = new JsogService();
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    if (super.getOpInsert() === true) {
      this.viewAlert = super.getOpInsert();
      super.showAlertInsertMessage();
    }
    this.checkFormToLeave = true;
    if (this.activatedRoute.snapshot.url[0].path === 'new') {
      this.modifica = false;
      this.pagePath = this.activatedRoute.snapshot.url[0].path;
      this.id = '';
    } else {
      this.modifica = true;
      this.id = this.activatedRoute.snapshot.url[0].path;
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
      this.macroProgService
        .getMacroProgetto(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.curMacroProg = <IMacroProg>jsog.deserialize(res);
            this.formMacroProg = this._fb.group({
              id: [this.curMacroProg.id],
              codmacpro: [this.curMacroProg.codmacpro, [Validators.required, StringValidator.patternValidator()]],
              desmacpro: [this.curMacroProg.desmacpro, [Validators.required, StringValidator.patternValidator()]],
              idTipfinda: [this.curMacroProg.id_tipfinda],
              idTipfina: [this.curMacroProg.id_tipfina],
              proTipfinByIdTipfinda: [this.curMacroProg.proTipfinByIdTipfinda],
              proTipfinByIdTipfina: [this.curMacroProg.proTipfinByIdTipfina],
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
      this.formMacroProg = this._fb.group({
        codmacpro: ['', [Validators.required, StringValidator.patternValidator()]],
        desmacpro: ['', [Validators.required, StringValidator.patternValidator()]],
        idTipfinda: [null],
        idTipfina: [null],
        proTipfinByIdTipfinda: [null],
        proTipfinByIdTipfina: [null],
      });
    }
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

  getFormElement(elemento: string) {
    return this.formMacroProg.controls[elemento];
  }

  submitForm(): void {
    if (this.modifica) {
      const TipFinDa = this.getFormElement('proTipfinByIdTipfinda').value;
      const TipFinA = this.getFormElement('proTipfinByIdTipfina').value;
      if (TipFinDa != null) {
        this.getFormElement('idTipfinda').setValue(TipFinDa.id);
      }
      if (TipFinA != null) {
        this.getFormElement('idTipfina').setValue(TipFinA.id);
      }
      this.curMacroProg = this.formMacroProg.value;
      delete this.curMacroProg.proTipfinByIdTipfina;
      delete this.curMacroProg.proTipfinByIdTipfinda;
      this.macroProgService
        .modificaMacroProgetto(this.curMacroProg)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            this.showAlertMessage(res);
            this.reload();
          },
          err => {
            this.showAlertError(err);
          }
        );
    } else {
      const TipFinDa = this.getFormElement('proTipfinByIdTipfinda').value;
      const TipFinA = this.getFormElement('proTipfinByIdTipfina').value;
      if (TipFinDa != null) {
        this.getFormElement('idTipfinda').setValue(TipFinDa.id);
      }
      if (TipFinA != null) {
        this.getFormElement('idTipfina').setValue(TipFinA.id);
      }
      this.macroProgService
        .inserisciMacroProgetto(this.formMacroProg.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`diz-macro-prog/${res.id}/viewDetail`]);
          },
          err => {
            this.showAlertError(err);
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
        this.macroProgService
          .cancellaMacroProgetto(this.curMacroProg.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              //this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
              this.router.navigate([`/diz-macro-prog`], { relativeTo: this.activatedRoute.parent });
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formMacroProg) {
      return this.formMacroProg.pristine;
    } else {
      return of(true);
    }
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
