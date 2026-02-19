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
import { ProMunService } from '../../services/promun.service';
import { IProMun } from '../../models/promun.model';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './municipi-update.component.html',
  styleUrls: ['./municipi.component.scss'],
})
export class MunicipiUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curMun!: IProMun;
  formMun!: FormGroup;
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
    protected ngbModal: NgbModal,
    private municipioService: ProMunService
  ) {
    super(injector);
    this.modifica = false;
    this.router = injector.get(Router);
  }

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
      this.municipioService
        .getMunicipio(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.curMun = <IProMun>jsog.deserialize(res);
            this.formMun = this._fb.group({
              id: [this.curMun.id],
              desmun: [this.curMun.desmun, [Validators.required]],
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
      this.formMun = this._fb.group({
        desmun: ['', Validators.required],
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
    return this.formMun.controls[elemento];
  }

  submitForm(): void {
    if (this.modifica) {
      this.municipioService
        .modificaMunicipio(this.formMun.value)
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
      this.municipioService
        .inserisciMunicipio(this.formMun.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`municipi/${res.id}/viewDetail`]);
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
        this.municipioService
          .cancellaMunicipio(String(this.curMun.id))
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              //this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
              this.router.navigate([`/municipi`], { relativeTo: this.activatedRoute.parent });
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formMun) {
      return this.formMun.pristine;
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
