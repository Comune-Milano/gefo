import { Component, Injector, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { TipFinService } from 'app/services/tip-fin.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of, Subject, takeUntil } from 'rxjs';
import { DizMacroProgService } from 'app/services/diz-macro-prog.service';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { JsogService } from 'jsog-typescript';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { ProMunService } from '../../services/promun.service';
import { IProMun } from '../../models/promun.model';
import { IProNil } from '../../models/pronil.model';
import { ProNilService } from '../../services/pronil.service';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './nil-update.component.html',
  styleUrls: ['./nil.component.scss'],
})
export class NilUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curNil!: IProNil;
  formNil!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();
  municipi: IProMun[] = [];

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
    private nilService: ProNilService,
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
    this.municipioService
      .getAllMunicipi()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((res: IProMun[]) => {
        this.municipi = res;
      });
    if (this.modifica) {
      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      });
      this.nilService
        .getNil(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.curNil = <IProNil>jsog.deserialize(res);
            this.formNil = this._fb.group({
              id: [this.curNil.id],
              codnil: [this.curNil.codnil, [Validators.required]],
              desnil: [this.curNil.desnil, [Validators.required]],
              proMunByIdMun: [null],
              idMun: [this.curNil.idMun],
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
      this.formNil = this._fb.group({
        codnil: ['', Validators.required],
        desnil: ['', Validators.required],
        proMunByIdMun: [null], // Inizializza come null invece di una stringa vuota
        idMun: [null],
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
    return this.formNil.controls[elemento];
  }

  submitForm(): void {
    const curMun = this.formNil.get('idMun').value;
    if (this.modifica) {
      this.nilService
        .modificaNil(this.formNil.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            this.showAlertMessage(res);
            this.formNil.get('idMun').setValue(curMun);
            this.reload();
          },
          err => {
            this.showAlertError(err);
            this.formNil.get('idMun').setValue(curMun);
          }
        );
    } else {
      this.nilService
        .inserisciNil(this.formNil.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`nil/${res.id}/viewDetail`]);
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
        this.nilService
          .cancellaNil(String(this.curNil.id))
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              //this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
              this.router.navigate([`/nil`], { relativeTo: this.activatedRoute.parent });
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formNil) {
      return this.formNil.pristine;
    } else {
      return of(true);
    }
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  //pulsante per andare ai totali del progetto corrente
  goToTotali(): void {
    this.router.navigate([`nil/${this.id}/viewDetail/ProNil/totali`]);
  }
}
