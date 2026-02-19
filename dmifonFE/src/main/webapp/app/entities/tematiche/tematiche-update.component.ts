import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AlertService } from 'app/shared/alert/alert.service';
import { TranslateService } from '@ngx-translate/core';
import { TematicheService } from 'app/services/tematiche.service';
import { ITematica } from 'app/models/tematica.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { Observable, of, Subject, takeUntil } from 'rxjs';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-tematiche',
  templateUrl: './tematiche-update.component.html',
  styleUrls: ['./tematiche.component.scss'],
})
export class TematicheUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curTematica!: ITematica;
  formTematiche!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private tematicheService: TematicheService,
    private _fb: FormBuilder,
    protected translateService: TranslateService,
    protected rolesCheckService: RolesCheckService,
    private customDialogsService: CustomDialogsService,
    protected ngbModal: NgbModal
  ) {
    super(injector);
    this.modifica = false;
    this.router = injector.get(Router);
  }

  ngOnInit(): void {
    this.checkFormToLeave = true;
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    if (super.getOpInsert() === true) {
      this.viewAlert = super.getOpInsert();
      super.showAlertInsertMessage();
    }
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
      this.tematicheService
        .getTematica(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: ITematica) => {
            this.curTematica = res;
            this.formTematiche = this._fb.group({
              id: [this.curTematica.id],
              livuno: [this.curTematica.livuno, [Validators.min(0), Validators.required]],
              livdue: [this.curTematica.livdue, [Validators.min(0), Validators.required]],
              codtem: [this.curTematica.codtem, [Validators.required, StringValidator.patternValidator()]],
              destem: [this.curTematica.destem, [Validators.required, StringValidator.patternValidator()]],
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
      this.formTematiche = this._fb.group({
        livuno: ['', [Validators.min(0), Validators.required]],
        livdue: ['', [Validators.min(0), Validators.required]],
        codtem: ['', [Validators.required, StringValidator.patternValidator()]],
        destem: ['', [Validators.required, StringValidator.patternValidator()]],
      });
    }
  }

  //Metodo richiamato al click su ricarica informazioni
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
    return this.formTematiche.controls[elemento];
  }

  submitForm(): void {
    if (this.modifica) {
      this.tematicheService
        .modificaTematica(this.formTematiche.value)
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
      this.tematicheService
        .inserisciTematica(this.formTematiche.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`tematiche/${res.id}/viewDetail`]);
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
        this.tematicheService
          .cancellaTematica(this.curTematica.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              this.router.navigate([`/tematiche`]);
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formTematiche) {
      return this.formTematiche.pristine;
    } else {
      return of(true);
    }
  }

  goToTotali(): void {
    this.router.navigate([`tematiche/${this.id}/viewDetail/ProTem/totali`]);
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
