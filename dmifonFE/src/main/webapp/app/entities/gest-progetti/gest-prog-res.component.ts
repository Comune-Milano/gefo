import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, map, Observable, of, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { CustomDialogsService } from '../../services/custom-dialogs.service';
import { UpdatePage } from '../../shared/baseclass/update-page';
import { UsersService } from '../../services/users.service';
import { ProResService } from '../../services/prores.service';
import { IProRes, IResponsabile } from '../../models/prores.model';
import { JsogService } from 'jsog-typescript';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-gest-prog-res',
  templateUrl: './gest-prog-res.component.html',
  styleUrls: ['./gest-prog.component.scss'],
})
export class GestProgResComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  viewAlert = false;
  viewError = false;
  idProg = 0;
  responsabili!: IProRes;
  modalConfirmRef!: NgbModalRef;
  formRes!: FormArray;
  resObj!: Array<IResponsabile>;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    protected userService: UsersService,
    protected proresService: ProResService,
    private activatedRoute: ActivatedRoute,
    private _fb: FormBuilder,
    private customDialogsService: CustomDialogsService,
    protected translateService: TranslateService,
    protected ngbModal: NgbModal,
    protected rolesCheckService: RolesCheckService
  ) {
    super(injector);
    this.formRes = this._fb.array([]);
    this.resObj = [];
  }

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

  ngOnInit(): void {
    const jsog = new JsogService();
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    this.idProg = this.activatedRoute.snapshot.params.id;
    this.proresService
      .getResponsabiliProgetto(this.idProg)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(res => {
        this.responsabili = <IProRes>jsog.deserialize(res);
        this.responsabili.responsabili.forEach(resp => {
          const tempRow = this._fb.group({
            id: [resp.responsabile.id],
            destipres: [resp.responsabile.proTipresByIdTipres?.destipres],
            utente: [resp.utente],
            idUte: [resp.utente?.id],
            idPro: [resp.responsabile.idPro],
            notres: [resp.responsabile.notres, [StringValidator.patternValidator()]],
            idTipres: [resp.responsabile.idTipres],
          });
          this.formRes.push(tempRow);
        });
      });
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  getFormElement(elemento: any): any {
    return this.formRes.controls[elemento];
  }

  convertToProResObject(): void {
    this.formRes.controls.forEach(resp => {
      resp.patchValue({
        idUte: resp.get('utente')?.value ? (resp.get('utente')?.value).id : 0,
      });
      this.resObj.push({
        id: resp.get('id')?.value === 0 ? null : resp.get('id')?.value,
        idPro: resp.get('idPro')?.value,
        idTipres: resp.get('idTipres')?.value,
        idUte: resp.get('idUte')?.value,
        notres: resp.get('notres')?.value,
      });
    });
  }

  submitModifica(): void {
    this.convertToProResObject();
    this.proresService
      .modificaResponsabiliProgetto(this.resObj)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.resObj = [];
          this.formRes.clear();
          this.showAlertMessage(res);
          this.reload();
        },
        err => {
          this.resObj = [];
          this.showAlertError(err);
        }
      );
  }

  //metodo richiamato al click su ricarica informazioni
  reload(): void {
    if (!this.canDeactivate()) {
      const msg = this.translateService.instant('modals.refresh');
      this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
      this.modalConfirmRef.result.then(result => {
        if (result === true) {
          this.refresh(this.formRes);
          this.ngOnInit();
        }
      });
    } else {
      this.refresh(this.formRes);
      this.ngOnInit();
    }
  }

  //ricarica i dati dal server
  refresh(form: FormArray): void {
    while (form.length !== 0) {
      form.removeAt(0);
    }
    form.markAsPristine();
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave) {
      return this.formRes.pristine;
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
