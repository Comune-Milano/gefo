import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
// import { AlertService } from 'app/shared/alert/alert.service';
import { TranslateService } from '@ngx-translate/core';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { TipFinService } from 'app/services/tip-fin.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, map, Observable, of, OperatorFunction, Subject, switchMap, take, takeUntil } from 'rxjs';
import { DizMacroProgService } from 'app/services/diz-macro-prog.service';
import { IMacroProg } from 'app/models/macro_prog.model';
import { ItipFin } from 'app/models/tip_fin.model';
import { ItipiFinanziamento } from 'app/models/tipiFinanziamento.model';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
// import { ErrorService } from 'app/services/error.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { JsogService } from 'jsog-typescript';
import { GestPrevisioniService } from '../../services/gest-previsioni.service';
import { IPrevisioni } from '../../models/previsioni.model';
import { ProgService } from '../../services/gest-progetti.service';
import { formatDate } from '@angular/common';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './gest-previsioni-update.component.html',
  styleUrls: ['./gest-previsioni.component.scss'],
})
export class GestPrevisioniUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curPrev!: IPrevisioni;
  textTipFinA = '';
  textTipFinDa = '';
  idTipFinDa!: ItipFin;
  idTipFinA!: ItipFin;
  formPrev!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    protected progService: ProgService,
    private previsioniService: GestPrevisioniService,
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

  //ricerca con autocomplete dei tipi finanziamento
  searchTipFinA: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
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
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
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
      this.id = this.activatedRoute.snapshot.paramMap.get('idPre') ?? '';
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
      this.previsioniService
        .getPrevisione(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.curPrev = <IPrevisioni>jsog.deserialize(res);
            this.formPrev = this._fb.group({
              id: [this.curPrev.id],
              proProByIdPro: [this.curPrev.proProByIdPro, [Validators.required]],
              idPro: [this.curPrev.idPro],
              dtapre: [formatDate(this.curPrev.dtapre, 'yyyy-MM-dd', 'it'), [Validators.required]],
              imppre: [this.transformValueImporto(this.curPrev.imppre), [Validators.required]],
              notpre: [this.curPrev.notpre, [StringValidator.patternValidator()]],
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
      this.formPrev = this._fb.group({
        proProByIdPro: ['', [Validators.required]],
        idPro: [''],
        dtapre: [null, [Validators.required]],
        imppre: [this.transformValueImporto(0), [Validators.required]],
        notpre: [null, [StringValidator.patternValidator()]],
      });
      if (this.activatedRoute.snapshot.paramMap.get('id')) {
        this.progService
          .getProgetto(<string>this.activatedRoute.snapshot.paramMap.get('id'))
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(res => {
            this.formPrev.get('idPro')?.setValue(res.progetto.id);
            this.formPrev.get('proProByIdPro')?.setValue(res.progetto);
          });
      }
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

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formPrev.controls[elemento];
  }

  patchImportoPrevisione(): void {
    this.formPrev
      .get('imppre')
      .setValue(this.transformValueImporto(this.formPrev.get('imppre').value ? this.formPrev.get('imppre').value : 0));
  }

  submitForm(): void {
    this.formPrev.patchValue({
      idPro: this.getFormElement('proProByIdPro').value ? this.getFormElement('proProByIdPro').value.id : null,
      imppre: this.patchValueImporto(this.getFormElement('imppre').value),
    });
    if (this.modifica) {
      this.previsioniService
        .modificaPrevisione(this.formPrev.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            this.showAlertMessage(res);
            this.reload();
          },
          err => {
            this.patchImportoPrevisione();
            this.showAlertError(err);
          }
        );
    } else {
      this.previsioniService
        .inserisciPrevisione(this.formPrev.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`${res.id}/viewDetail`], { relativeTo: this.activatedRoute.parent });
          },
          err => {
            this.patchImportoPrevisione();
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
        this.previsioniService
          .cancellaPrevisione(this.curPrev.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              //this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
              if (this.activatedRoute.snapshot.params.id) {
                this.router.navigate([`/gest-progetti/${this.activatedRoute.snapshot.params.id}/viewDetail/gest-previsioni`]);
              } else {
                this.router.navigate([`/gest-previsioni`]);
              }
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formPrev) {
      return this.formPrev.pristine;
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
