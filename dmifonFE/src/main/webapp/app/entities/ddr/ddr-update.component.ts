import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, Observable, of, OperatorFunction, Subject, switchMap, take } from 'rxjs';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { ProgService } from '../../services/gest-progetti.service';
import { DdraService } from '../../services/ddra.service';
import { DdrService } from '../../services/ddr.service';
import { IDdr } from 'app/models/ddr.model';
import { formatDate } from '@angular/common';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { takeUntil } from 'rxjs/operators';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-ddr',
  templateUrl: './ddr-update.component.html',
  styleUrls: ['./ddr.component.scss'],
})
export class DdrUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curDdr!: IDdr;
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
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
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

  //ricerca con autocomplete delle Ddra
  searchDdra: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      takeUntil(this.ngUnsubscribe),
      switchMap(term => (term.length < 2 ? [] : this.ddraService.ricercaDdraAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle Ddra
  formatterDdra = (x: any) => {
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
      this.id = this.activatedRoute.snapshot.paramMap.get('idDdr') ?? '';
      this.pagePath = '';
    }
    //se modifica è true, esegui chiamata dettaglio utente e inizializza il form con i dati ricevuti
    if (this.modifica) {
      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      });
      this.ddrService
        .getDdr(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: IDdr) => {
            this.curDdr = res;
            this.formDdr = this._fb.group({
              id: [this.curDdr.id],
              idPro: [this.curDdr.idPro],
              idDdra: [this.curDdr.idDdra],
              proProByIdPro: [this.curDdr.proProByIdPro],
              ddra: [this.curDdr.proDdraByIdDdra],
              codddr: [this.curDdr.codddr, [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
              desddr: [this.curDdr.desddr, [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()]],
              // proTipfinByIdTipfin: [this.curBando.proTipfinByIdTipfin, [Validators.required]],
              // proTemByIdTem: [this.curBando.proTemByIdTem],
              dtaddr: [formatDate(this.curDdr.dtaddr, 'yyyy-MM-dd', 'it')],
              impddr: [this.transformValueImporto(this.curDdr.impddr), [Validators.required]],
              impamm: [this.transformValueImporto(this.curDdr.impamm), [Validators.required]],
              imptra: [this.transformValueImporto(this.curDdr.imptra), [Validators.required]],
              impinc: [this.transformValueImporto(this.curDdr.impinc), [Validators.required]],
              prgrev: [this.curDdr.prgrev, [Validators.maxLength(250), StringValidator.patternValidator()]],
              notddr: [this.curDdr.notddr, [Validators.maxLength(250), StringValidator.patternValidator()]],
            });
            this.formDdr.get('proProByIdPro').disable();
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
      this.formDdr = this._fb.group({
        idPro: [null],
        idDdra: [null],
        codddr: [null, [Validators.maxLength(25), Validators.required, StringValidator.patternValidator()]],
        desddr: ['.', [Validators.maxLength(250), Validators.required, StringValidator.patternValidator()]],
        proProByIdPro: [null, [Validators.required]],
        ddra: [null],
        dtaddr: [null, [Validators.required]],
        impddr: [this.transformValueImporto(0), [Validators.required]],
        impamm: [this.transformValueImporto(0), [Validators.required]],
        imptra: [this.transformValueImporto(0), [Validators.required]],
        impinc: [this.transformValueImporto(0), [Validators.required]],
        prgrev: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
        notddr: [null, [Validators.maxLength(250), StringValidator.patternValidator()]],
      });
      if (this.activatedRoute.snapshot.paramMap.get('id')) {
        this.progService
          .getProgetto(<string>this.activatedRoute.snapshot.paramMap.get('id'))
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(res => {
            this.formDdr.get('idPro')?.setValue(res.progetto.id);
            this.formDdr.get('proProByIdPro')?.setValue(res.progetto);
          });
      }
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
    return this.formDdr.controls[elemento];
  }

  patchFormDdr(): void {
    this.formDdr.patchValue({
      idPro: this.formDdr.get('proProByIdPro')?.value?.id,
      idDdra: this.formDdr.get('ddra')?.value?.id,
    });
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
    this.patchFormDdr();
    this.patchImporti();
    if (this.modifica) {
      this.curDdr = this.formDdr.value;
      this.isSaving = true;
      this.ddrService
        .modificaDdr(this.curDdr)
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
      this.ddrService
        .inserisciDdr(this.formDdr.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
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
        this.ddrService
          .cancellaDdr(this.curDdr.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              if (this.activatedRoute.snapshot.params.id) {
                this.router.navigate([`/gest-progetti/${this.activatedRoute.snapshot.params.id}/viewDetail/ddr`]);
              } else {
                this.router.navigate([`/ddr`]);
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
    if (this.checkFormToLeave && this.formDdr) {
      return this.formDdr.pristine;
    } else {
      return of(true);
    }
  }

  goToAllegati(): void {
    this.router.navigate([`allegati/DDR`], { relativeTo: this.activatedRoute });
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
