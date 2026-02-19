import { Component, Injector, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Iamm_ruo } from 'app/models/amm_ruo.model';
import { ProfilesService } from 'app/services/profiles.service';
import { TranslateService } from '@ngx-translate/core';
import { Observable, of, Subject, takeUntil } from 'rxjs';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-amm-utenti',
  templateUrl: './amm-ruoli-update.component.html',
  styleUrls: ['./amm-ruoli.component.scss'],
})
export class AmmRuoliUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curRuolo!: Iamm_ruo;
  formRuolo!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    protected translateService: TranslateService,
    private activatedRoute: ActivatedRoute,
    private profiliService: ProfilesService,
    private customDialogsService: CustomDialogsService,
    protected rolesCheckService: RolesCheckService,
    private _fb: FormBuilder,
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
    if (this.activatedRoute.snapshot.url[0].path === 'new') {
      this.modifica = false;
      this.pagePath = this.activatedRoute.snapshot.url[0].path;
      this.id = '';
    } else {
      this.modifica = true;
      this.id = this.activatedRoute.snapshot.url[0].path;
      this.pagePath = '';
    }
    //se modifica è true, esegui chiamata dettaglio utente e inizializza il form con i dati ricevuti
    if (this.modifica) {
      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      });
      this.profiliService
        .getRuolo(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: Iamm_ruo) => {
            this.curRuolo = res;
            this.formRuolo = this._fb.group({
              id: [this.curRuolo.id],
              codruo: [this.curRuolo.codruo, [Validators.required, StringValidator.patternValidator()]],
              desruo: [this.curRuolo.desruo, [Validators.required, StringValidator.patternValidator()]],
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
      this.formRuolo = this._fb.group({
        codruo: ['', [Validators.required, StringValidator.patternValidator()]],
        desruo: ['', [Validators.required, StringValidator.patternValidator()]],
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
    return this.formRuolo.controls[elemento];
  }

  submitForm(): void {
    if (this.modifica) {
      this.profiliService
        .modificaRuolo(this.formRuolo.value)
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
      this.profiliService
        .inserisciRuolo(this.formRuolo.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            // this.router.navigate([`amm-ruoli/${res.id}/viewDetail`]);
            this.router.navigate([`${res.id}/viewDetail`], { relativeTo: this.activatedRoute.parent });
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
        this.profiliService
          .cancellaRuolo(this.curRuolo.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (this.checkFormToLeave && this.formRuolo) {
      return this.formRuolo.pristine;
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
