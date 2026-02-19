import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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
import { GestCommRicService } from '../../services/gest-comm-ric.service';
import { ICommRic } from '../../models/comm-ric.model';
import { UsersService } from '../../services/users.service';
import { IUsers } from '../../models/usersModel';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-diz-macro-prog',
  templateUrl: './gest-commenti-richieste-update.component.html',
  styleUrls: ['./gest-commenti-richieste.component.scss'],
})
export class GestCommentiRichiesteUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  curCommRic!: ICommRic;
  formRicUte!: FormArray;
  curIndex = 0;
  users!: IUsers[];
  textTipFinA = '';
  textTipFinDa = '';
  formPrev!: FormGroup;
  modalConfirmRef!: NgbModalRef;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    protected progService: ProgService,
    private previsioniService: GestPrevisioniService,
    private commRicService: GestCommRicService,
    protected userService: UsersService,
    private tipFinService: TipFinService,
    private _fb: FormBuilder,
    private customDialogsService: CustomDialogsService,
    protected translateService: TranslateService,
    protected ngbModal: NgbModal
  ) {
    super(injector);
    this.modifica = false;
    this.router = injector.get(Router);
    this.formRicUte = new FormArray<any>([]);
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

  //ricerca con autocomplete delle richieste
  searchRic: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.commRicService.ricercaRichiestaAutocomplete(term)))
    );
  };

  //gestisce la formattazione degli utenti nell'autocomplete searchRic
  formatterRic = (x: { id: number; desric: string }) => {
    if (x.id) {
      return `${x.desric}`;
    }
    return '';
  };

  //ricerca con autocomplete degli utenti
  searchUte: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.userService.getUtentiAutocomplete(term)))
    );
  };

  //gestisce la formattazione degli utenti nell'autocomplete searchUte
  formatterUte = (x: { username: string; nome: string; cognome: string; id: number }) => {
    if (x.id) {
      return `${x.username + ' - ' + x.cognome + ' ' + x.nome}`;
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
    this.formRicUte.clear();
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
      this.id = this.activatedRoute.snapshot.paramMap.get('idRic') ?? '';
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
      this.commRicService
        .getRichiesta(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.curCommRic = <ICommRic>jsog.deserialize(res.proRic);
            this.users = <IUsers[]>jsog.deserialize(res.users);
            console.log(this.users);
            this.formPrev = this._fb.group({
              id: [this.curCommRic.id],
              proProByIdPro: [this.curCommRic.proProByIdPro, [Validators.required]],
              proRicPadByIdRicPad: [res?.proRicPad],
              idPro: [this.curCommRic.idPro],
              tipric: [this.curCommRic.tipric],
              desric: [this.curCommRic.desric, [StringValidator.patternValidator()]],
              staric: [this.curCommRic.staric],
              dtasca: [formatDate(this.curCommRic.dtasca, 'yyyy-MM-dd', 'it')],
              dtainv: [this.curCommRic.dtainv],
              risric: [this.curCommRic.risric, [StringValidator.patternValidator()]],
              idRicpad: [this.curCommRic.idRicpad],
              proRicutesById: [this.curCommRic.proRicutesById],
            });
            this.curCommRic.proRicutesById.forEach(ric => {
              this.formRicUte.push(
                this._fb.group({
                  id: [ric.id],
                  idRic: [ric.idRic],
                  idUte: [ric.idUte],
                  user: [this.users.find(i => i.id === ric.idUte)],
                  isNew: [false],
                })
              );
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
        proRicPadByIdRicPad: [''],
        idPro: [null],
        tipric: ['R'],
        desric: ['', [StringValidator.patternValidator()]],
        staric: ['INI'],
        dtasca: [''],
        dtainv: [''],
        risric: ['', [StringValidator.patternValidator()]],
        idRicpad: [''],
        proRicutesById: [''],
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

  onAddRow(): void {
    if (this.modifica) {
      this.formRicUte.push(
        this._fb.group({
          id: [],
          idRic: [this.curCommRic.id],
          idUte: [],
          user: [],
          isNew: [true],
        })
      );
    } else {
      this.formRicUte.push(
        this._fb.group({
          idUte: [],
          user: [],
          isNew: [true],
        })
      );
    }
  }

  onRemoveRowLocal(rowIndex: number): void {
    this.formRicUte.removeAt(rowIndex);
  }

  onRemoveRow(): void {
    this.commRicService
      .cancellaRichiestaUtente(<number>this.formRicUte.at(this.curIndex).value.id)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.formRicUte.removeAt(this.curIndex);
          this.showAlertMessage(res);
          this.viewAlert = true;
        },
        err => {
          this.showAlertError(err);
        }
      );
  }

  submitForm(): void {
    const jsog = new JsogService();
    this.formRicUte.controls.forEach(ricute => {
      ricute.get('idUte')?.setValue(ricute.get('user')?.value.id);
    });
    this.formPrev.patchValue({
      idPro: this.getFormElement('proProByIdPro').value ? this.getFormElement('proProByIdPro').value.id : null,
      idRicpad: this.getFormElement('proRicPadByIdRicPad').value ? this.getFormElement('proRicPadByIdRicPad').value.id : null,
      proRicutesById: this.formRicUte.value,
    });
    console.log(this.formPrev);
    if (this.modifica) {
      this.commRicService
        .modificaRichiesta(this.formPrev.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            console.log(res);
            if (res?.status === 201) {
              this.alertService.showWarning = true;
              this.showAlertWarning(res?.body);
              this.reload();
            } else {
              this.showAlertMessage(res?.body);
              this.reload();
            }
          },
          err => {
            this.showAlertError(err);
          }
        );
    } else {
      this.commRicService
        .inserisciRichiesta(jsog.deserialize(this.formPrev.value))
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.showAlertMessage(res);
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
        this.commRicService
          .cancellaRichiesta(this.curCommRic.id)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(
            res => {
              super.setOpDelete(true);
              this.alertService.testo = res.userMessage;
              this.alertService.showAlert = true;
              //this.router.navigate([`./`], { relativeTo: this.activatedRoute.parent });
              if (this.activatedRoute.snapshot.params.id) {
                this.router.navigate([`/gest-progetti/${this.activatedRoute.snapshot.params.id}/viewDetail/gest-commenti-richieste`]);
              } else {
                this.router.navigate([`/gest-commenti-richieste`]);
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

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
