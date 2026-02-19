import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, Observable, of, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { ProgService } from '../../services/gest-progetti.service';
import { DdraService } from '../../services/ddra.service';
import { DdrService } from '../../services/ddr.service';
import { IDdr } from 'app/models/ddr.model';
import { IDdraResponse, ProDdraResponse } from '../../models/ddraResponse.model';
import { formatDate } from '@angular/common';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-ddra-update',
  templateUrl: './ddra-update.component.html',
  styleUrls: ['./ddra.component.scss'],
})
export class DdraUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curDdr!: ProDdraResponse;
  formDdr!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  progetti: Array<any> = [];
  ddra: Array<any> = [];
  isSaving = false;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private ddrService: DdrService,
    protected progService: ProgService,
    private ddraService: DdraService,
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

  //ricerca con autocomplete delle Ddra
  searchDdra: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.ddraService.ricercaDdraAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle Ddra
  formatterDdra = (x: { desddra: string; codddra: string; id: number }) => {
    if (x.id) {
      return `${x.codddra + ' - ' + x.desddra}`;
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
      this.ddraService
        .getDdra(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: ProDdraResponse) => {
            this.curDdr = res;
            this.formDdr = this._fb.group({
              id: [this.curDdr.ddra.id],
              codddra: [this.curDdr.ddra.codddra, [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
              desddra: [this.curDdr.ddra.desddra, [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()]],
              // proTipfinByIdTipfin: [this.curBando.proTipfinByIdTipfin, [Validators.required]],
              // proTemByIdTem: [this.curBando.proTemByIdTem],
              dtaddra: [formatDate(this.curDdr.ddra.dtaddra, 'yyyy-MM-dd', 'it'), Validators.required],
              importoDdra: [this.transformValueImporto(this.curDdr.importoDdra)],
              notddra: [this.curDdr.ddra.notddra, [Validators.maxLength(250), StringValidator.patternValidator()]],
            });
          },
          err => {
            this.checkErrorStatus(err);
            this.showAlertError(err);
          }
        );
    } else {
      this.stringSalva = this.translateService.instant('global.buttons.labelSaveInsert');
      this.formDdr = this._fb.group({
        codddra: ['', [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
        desddra: ['', [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()]],
        dtaddra: ['', Validators.required],
        notddra: ['', [Validators.maxLength(250), StringValidator.patternValidator()]],
      });
    }
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  createDdraObj(): void {
    this.curDdr = {
      ddra: {
        id: this.formDdr.get('id')?.value,
        codddra: this.formDdr.get('codddra')?.value,
        desddra: this.formDdr.get('desddra')?.value,
        dtaddra: this.formDdr.get('dtaddra')?.value,
        notddra: this.formDdr.get('notddra')?.value,
      },
      importoDdra: this.curDdr.importoDdra,
    };
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
    return this.formDdr.controls[elemento];
  }

  patchFormDdr(): void {
    this.formDdr.patchValue({
      idPro: this.formDdr.get('proByIdPro')?.value?.id,
      idDdra: this.formDdr.get('ddraByIdDdra')?.value?.id,
    });
    this.formDdr.removeControl('proByIdPro');
    this.formDdr.removeControl('ddraByIdDdra');
  }

  patchImporti(): void {
    this.formDdr.patchValue({
      impddr: this.patchValueImporto(this.formDdr.get('impddr')?.value),
      impamm: this.patchValueImporto(this.formDdr.get('impamm')?.value),
      imptra: this.patchValueImporto(this.formDdr.get('imptra')?.value),
      impinc: this.patchValueImporto(this.formDdr.get('impinc')?.value),
    });
  }

  patchError(): void {
    this.formDdr.patchValue({
      impddr: this.transformValueImporto(this.formDdr.get('impddr')?.value),
      impamm: this.transformValueImporto(this.formDdr.get('impamm')?.value),
      imptra: this.transformValueImporto(this.formDdr.get('imptra')?.value),
      impinc: this.transformValueImporto(this.formDdr.get('impinc')?.value),
    });
  }

  submitForm(): void {
    if (this.modifica) {
      this.createDdraObj();
      this.curDdr = this.formDdr.value;
      this.isSaving = true;
      this.ddraService
        .modificaDdra(this.curDdr)
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
      this.ddraService
        .inserisciDdra(this.formDdr.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
            this.router.navigate([`ddra/${res.id}/viewDetail`]);
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
        this.ddraService
          .cancellaDdra(this.curDdr.ddra.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              this.router.navigate([`/ddra`], { relativeTo: this.activatedRoute.parent });
            },
            err => {
              this.showAlertError(err);
            }
          );
      }
    });
  }

  goToElencoDdr(): void {
    this.router.navigate([`ddra/${this.id}/viewDetail/ElencoDdr`]);
  }

  goToAggiungiDdr(): void {
    this.router.navigate([`ddra/${this.id}/viewDetail/AggiungiDdr`]);
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formDdr) {
      return this.formDdr.pristine;
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
