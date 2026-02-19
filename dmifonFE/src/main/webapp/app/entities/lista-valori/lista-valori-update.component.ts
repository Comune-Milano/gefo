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
import { ILisVal } from '../../models/lisVal.model';
import { ListaValoriService } from '../../services/listaValori.service';
import { IProLisval } from '../../models/proLisval.model';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './lista-valori-update.component.html',
  styleUrls: ['./lista-valori.component.scss'],
})
export class ListaValoriUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curLis!: ILisVal;
  formLis!: FormGroup;
  nomeCompleto: string;
  modalConfirmRef!: NgbModalRef;
  tipiListaValore: IProLisval[];
  isRequestInProgress = false;
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
    private lisValService: ListaValoriService
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
    if (this.modifica) {
      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      });

      this.lisValService
        .getListaValori(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.curLis = <ILisVal>jsog.deserialize(res);
            this.getTipiListaValore();
            this.formLis = this._fb.group({
              id: [this.curLis.id],
              vallis: [this.curLis.vallis, [Validators.required]],
              tiplis: [this.curLis.tiplis, [Validators.required]],
            });
          },
          err => {
            this.checkErrorStatus(err);
            this.showAlertError(err);
          }
        );
    } else {
      this.getTipiListaValore();
      this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
      });
      this.formLis = this._fb.group({
        vallis: ['', Validators.required],
        tiplis: ['', Validators.required],
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

  getTipiListaValore(): void {
    this.lisValService.getTipiListaValori().subscribe({
      next: res => {
        this.tipiListaValore = res;
      },
    });
  }

  getFormElement(elemento: string) {
    return this.formLis.controls[elemento];
  }

  submitForm(): void {
    this.isRequestInProgress = true; // Set to true before making the API request

    if (this.modifica) {
      this.lisValService
        .modificaListaValore(this.formLis.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            this.showAlertMessage(res);
            this.reload();
          },
          err => {
            this.showAlertError(err);
          }
        )
        .add(() => {
          this.isRequestInProgress = false; // Set to false after the API request is complete
        });
    } else {
      this.lisValService
        .inserisciListaValori(this.formLis.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`lista-valori/${res.id}/viewDetail`]);
          },
          err => {
            this.showAlertError(err);
          }
        )
        .add(() => {
          this.isRequestInProgress = false; // Set to false after the API request is complete
        });
    }
  }

  //Metodo richiamato dal pulsante di cancellazione
  submitDelete(): void {
    const msg = this.translateService.instant('modals.canc');
    this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
    this.modalConfirmRef.result.then(result => {
      if (result === true) {
        this.lisValService
          .cancellaListaValori(String(this.curLis.id))
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              //this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
              this.router.navigate([`/lista-valori`], { relativeTo: this.activatedRoute.parent });
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formLis) {
      return this.formLis.pristine;
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
