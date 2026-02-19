import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { IammUteruosById, IUsers } from 'app/models/users.model';
import { UsersService } from 'app/services/users.service';
import { TranslateService } from '@ngx-translate/core';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { Observable, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { ProfilesService } from 'app/services/profiles.service';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { of } from 'rxjs';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomDialogsService } from 'app/services/custom-dialogs.service';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-amm-utenti',
  templateUrl: './amm-utenti-update.component.html',
  styleUrls: ['./amm-utenti.component.scss'],
})
export class AmmUtentiUpdateComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  abilitato = true;
  curUser!: IUsers;
  formUtente!: FormGroup;
  showFormInsert!: boolean;
  formAssociazione!: FormGroup;
  itemForm!: FormGroup;
  rows: FormArray;
  ruoli: any;
  direzioni: any;
  accessibleAutocomplete!: any;
  tempRow: any;
  ammUte_ruo!: IUsers;
  length!: number;
  modalConfirmRef!: NgbModalRef;
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private usersService: UsersService,
    protected translateService: TranslateService,
    protected rolesService: ProfilesService,
    protected dirService: DirCentraleService,
    protected rolesCheckService: RolesCheckService,
    protected sessionStorage: SessionStorageService,
    private modalService: NgbModal,
    private _fb: FormBuilder,
    private customDialogsService: CustomDialogsService,
    protected sessionStorageService: SessionStorageService
  ) {
    super(injector);
    this.modifica = false;
    this.router = injector.get(Router);
    this.formAssociazione = this._fb.group({
      items: [null, Validators.required],
      items_value: ['no', Validators.required],
    });
    this.rows = this._fb.array([]);
  }

  //ricerca con autocomplete dei ruoli
  searchRuo: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.ruoli.filter(
              (v: { desruo: string; codruo: string }) =>
                v.desruo.toLowerCase().includes(term.toLowerCase()) || v.codruo.toLowerCase().includes(term.toLowerCase())
            )
      )
    );
  };

  //gestisce la formattazione dei ruoli nell'autocomplete searchRuo
  formatterRuo = (x: { desruo: string; codruo: string; id: number }) => {
    if (x.id) {
      return `${x.codruo + ' - ' + x.desruo}`;
    }
    return '';
  };

  //ricerca con autocomplete delle direzioni
  searchDir: OperatorFunction<string, any> = (text$: Observable<string>) => {
    return text$.pipe(
      debounceTime(200),
      takeUntil(this.ngUnsubscribe),
      distinctUntilChanged(),
      switchMap(term => (term.length < 2 ? [] : this.dirService.getDirezioniAutocomplete(term)))
    );
  };

  //gestisce la formattazione delle direzioni nell'autocomplete searchDir
  formatterDir = (x: { desdir: string; coddir: string; id: number }) => {
    if (x.id) {
      return `${x.coddir + ' - ' + x.desdir}`;
    }
    return '';
  };

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave && this.formUtente && this.rows) {
      return this.formUtente.pristine && this.rows.pristine;
    } else {
      return of(true);
    }
  }

  ngOnInit(): void {
    this.checkFormToLeave = true;
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    if (super.getOpInsert() === true) {
      this.viewAlert = super.getOpInsert();
      super.showAlertInsertMessage();
    }
    this.showFormInsert = false;
    this.length = 0;
    this.usersService
      .autoCompleteRuolo('')
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.ruoli = res;
      });
    this.usersService
      .autocompleteDirezione('')
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.direzioni = res;
      });
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
    //se modifica è true, esegui chiamata dettaglio utente e inizializza il form con i dati ricevuti
    if (this.modifica) {
      this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      this.translateService.onLangChange.subscribe(lingua => {
        this.stringSalva = this.translateService.instant('global.buttons.labelSave');
      });
      this.usersService
        .getUtente(this.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: IUsers) => {
            res.ammUteruosById.forEach(ruo => {
              this.length++;
              this.onAddRowRequest(ruo);
            });
            this.curUser = res;
            this.abilitato = this.curUser.abilitato;
            this.formUtente = this._fb.group({
              id: [this.curUser.id],
              username: [this.curUser.username, [Validators.minLength(4), Validators.required, StringValidator.patternValidator()]],
              nome: [this.curUser.nome, [Validators.minLength(2), Validators.required, StringValidator.patternValidator()]],
              cognome: [this.curUser.cognome, [Validators.minLength(2), Validators.required, StringValidator.patternValidator()]],
              email: [this.curUser.email, [Validators.email, StringValidator.patternValidator()]],
              emailalt: [this.curUser.emailalt, [Validators.email, StringValidator.patternValidator()]],
              abilitato: [this.abilitato],
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
      this.formUtente = this._fb.group({
        id: [''],
        username: ['', [Validators.minLength(4), Validators.required, StringValidator.patternValidator()]],
        nome: ['', [Validators.minLength(2), Validators.required, StringValidator.patternValidator()]],
        cognome: ['', [Validators.minLength(2), Validators.required, StringValidator.patternValidator()]],
        email: ['', [Validators.email, StringValidator.patternValidator()]],
        emailalt: ['', [Validators.email, StringValidator.patternValidator()]],
        abilitato: [true],
      });
    }
  }

  get addDynamicRow() {
    return this.formAssociazione.get('rows') as FormArray;
  }

  //metodo che aggiunge le righe alla tabella dei ruoli dell'utente dopo aver recuperato i dati dal be
  onAddRowRequest(curAmmUteRuo: IammUteruosById): void {
    let curRuo: any;
    let curDir;
    this.rolesService
      .getRuolo(curAmmUteRuo.idRuo)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        curRuo = res;
        this.dirService
          .getDirezione(curAmmUteRuo.idDir)
          .pipe(takeUntil(this.ngUnsubscribe))
          .subscribe(res => {
            curDir = res;
            this.tempRow = this._fb.group({
              id: [curAmmUteRuo.id],
              id_ruo: [{ value: curRuo, disabled: false }, Validators.required],
              flgdef: [{ value: curAmmUteRuo.flgdef, disabled: false }, Validators.required],
              tipcondat: [{ value: curAmmUteRuo.tipcondat, disabled: false }, Validators.required],
              id_dir: [{ value: curDir, disabled: false }, Validators.required],
              isNew: [false],
            });
            this.rows.push(this.tempRow);
          });
      });
  }

  onAddRow(): void {
    this.length++;
    this.rows.push(this.createItemFormGroup());
  }

  onEdit(index: number): void {
    this.rows.at(index).patchValue({ edit: true });
    this.rows.at(index).enable();
  }

  //creazione del form di una nuova riga
  createItemFormGroup(): FormGroup {
    return this._fb.group({
      id_ruo: ['', Validators.required],
      flgdef: ['', Validators.required],
      tipcondat: ['', Validators.required],
      id_dir: ['', Validators.required],
      isNew: [true],
    });
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  // metodo che viene richiamato quando si clicca sul cestino di fianco alla riga
  onRemoveRow(rowIndex: number): void {
    if (this.rows.at(rowIndex).get('isNew')?.value == false) {
      this.usersService
        .cancellaRuoloDirezione(this.curUser.id, this.rows.at(rowIndex).get('id')?.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            this.length--;
            this.rows.removeAt(rowIndex);
            this.alertService.testo = res.userMessage;
            this.alertService.showAlert = true;
            this.viewAlert = true;
            setTimeout(() => {
              this.viewAlert = false;
              this.alertService.showAlert = false;
            }, 5000);
          },
          err => {
            this.showAlertError(err);
          }
        );
    } else {
      this.length--;
      this.rows.removeAt(rowIndex);
    }
  }

  //metodo richiamato al click del flag abilitato
  changeAbilitato(v: any): void {
    this.usersService
      .abilitazioneUtente(this.id, this.abilitato)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.showAlertMessage(res);
          this.viewAlert = true;
        },
        err => {
          this.showAlertError(err);
        }
      );
  }

  //modifica dello stato precedente di abilitato
  resetAbilitato(): void {
    this.abilitato = !this.abilitato;
  }

  //metodo richiamato al click su ricarica informazioni
  reload(): void {
    if (!this.canDeactivate()) {
      const msg = this.translateService.instant('modals.refresh');
      this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
      this.modalConfirmRef.result.then(result => {
        if (result === true) {
          this.refresh();
        }
      });
    } else {
      this.refresh();
    }
  }

  //ricarica i dati dal seerver
  refresh(): void {
    while (this.rows.length !== 0) {
      this.rows.removeAt(0);
    }
    this.rows.markAsPristine();
    this.ngOnInit();
  }

  showAlert(): boolean {
    return this.alertService.showAlert;
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formUtente.controls[elemento];
  }

  //conversione dei due form Utente e Rows in un oggetto Utente (contenente le informazioni sull'utente e i ruoli associati ad esso)
  convertToAmmUteRuo(): IUsers {
    this.ammUte_ruo = this.formUtente.value;
    let ruos: any[] = [];

    this.rows.value.forEach((ruo: any) => {
      ruos.push({
        id: ruo.id ? ruo.id : null,
        idRuo: ruo.id_ruo.id,
        idUte: this.curUser.id,
        idDir: ruo.id_dir.id,
        flgdef: ruo.flgdef,
        tipcondat: ruo.tipcondat,
      });
    });
    this.ammUte_ruo.ammUteruosById = ruos;
    return this.ammUte_ruo;
  }

  //submit dei form di inserimento / modifica dell'utente
  submitForm(): void {
    if (this.modifica) {
      this.formUtente.patchValue({
        abilitato: this.abilitato,
      });
      this.usersService
        .modificaUtente(this.convertToAmmUteRuo())
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
      this.usersService
        .inserisciUtente(this.formUtente.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          res => {
            super.setOpInsert(true);
            this.alertService.testo = res.userMessage;
            this.formUtente.patchValue({ id: res.id });
            this.usersService
              .getUtente(res.id)
              .pipe(takeUntil(this.ngUnsubscribe))
              .subscribe(res => {
                this.curUser = res;
                this.usersService
                  .modificaUtente(this.convertToAmmUteRuo())
                  .pipe(takeUntil(this.ngUnsubscribe))
                  .subscribe(res => {
                    this.alertService.showAlert = true;
                    this.checkFormToLeave = false;
                    this.router.navigate([`amm-utenti/${this.curUser.id}/viewDetail`]);
                  });
              });
          },
          err => {
            this.showAlertError(err);
          }
        );
    }
  }

  goToRuoli(): void {
    this.router.navigate([`amm-utenti/${this.id}/viewDetail/amm-ruoli`]);
  }

  goToDirezioniCentrali(): void {
    this.router.navigate([`amm-utenti/${this.id}/viewDetail/amm-dir`]);
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
