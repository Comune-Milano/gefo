import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AlertService } from 'app/shared/alert/alert.service';
import { TranslateService } from '@ngx-translate/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { debounceTime, distinctUntilChanged, map, Observable, OperatorFunction, Subject, switchMap, takeUntil } from 'rxjs';
import { CapitoliService } from '../../services/capitoli.service';
import { ICapitoli, ICapitoliResponse } from '../../models/capitoli.model';
import { JsogService } from 'jsog-typescript';
import { UpdatePage } from '../../shared/baseclass/update-page';
import { CustomDialogsService } from '../../services/custom-dialogs.service';
import { ProgService } from '../../services/gest-progetti.service';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-gest-prog-capitoli',
  templateUrl: './gest-prog-capitoli.component.html',
  styleUrls: ['./gest-prog.component.scss'],
})
export class GestProgCapitoliComponent extends UpdatePage implements OnInit, IComponentCanDeactivate {
  idPro = 0;
  capitoli!: ICapitoli;
  formCapitoliEnt: FormArray;
  formCapitoliUsc: FormArray;
  modalConfirmRef!: NgbModalRef;
  codiceCapitolo = '';
  curIndex = -1;
  showModalCape = false;
  showModalCapu = false;
  capitoliObj!: any;
  tempRow!: any;
  showModal = false;
  tipo!: any;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    protected activatedRoute: ActivatedRoute,
    protected capitoliService: CapitoliService,
    protected translateService: TranslateService,
    protected customDialogsService: CustomDialogsService,
    protected router: Router,
    private _fb: FormBuilder,
    protected rolesCheckService: RolesCheckService
  ) {
    super(injector);
    this.formCapitoliEnt = new FormArray<any>([]);
    this.formCapitoliUsc = new FormArray<any>([]);
  }

  ngOnInit(): void {
    const jsog = new JsogService();
    this.idPro = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.capitoliService
      .getCapitoliProgetto(this.idPro)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.capitoli = <ICapitoli>jsog.deserialize(res);
          //console.log(this.capitoli);
          this.capitoli.capitoliEntrata.forEach(capitolo => {
            const tempRow = this._fb.group({
              id: capitolo.proDetcon.id,
              codentcon: capitolo.proDetcon.codentcon,
              descrizione: capitolo.datiCapitolo.descrizione,
              titolo: capitolo.datiCapitolo.titolo,
              macro: capitolo.datiCapitolo.macro,
              previsione: this.transformValueImporto(Number(capitolo.datiCapitolo.previsione)),
              assestato: this.transformValueImporto(Number(capitolo.datiCapitolo.assestato)),
              impegnato: this.transformValueImporto(Number(capitolo.datiCapitolo.impegnato)),
              previsione_1: this.transformValueImporto(Number(capitolo.datiCapitolo.previsione_1)),
              assestato_1: this.transformValueImporto(Number(capitolo.datiCapitolo.assestato_1)),
              impegnato_1: this.transformValueImporto(Number(capitolo.datiCapitolo.impegnato_1)),
              previsione_2: this.transformValueImporto(Number(capitolo.datiCapitolo.previsione_2)),
              assestato_2: this.transformValueImporto(Number(capitolo.datiCapitolo.assestato_2)),
              impegnato_2: this.transformValueImporto(Number(capitolo.datiCapitolo.impegnato_2)),
              notent: [capitolo.proDetcon.notent, StringValidator.patternValidator()],
              isNew: false,
            });
            this.formCapitoliEnt.push(tempRow);
          });
          this.capitoli.capitoliUscita.forEach(capitolo => {
            const tempRow = this._fb.group({
              id: capitolo.proDetcon.id,
              codentcon: capitolo.proDetcon.codentcon,
              codentcol: capitolo.proDetcon.codentcol,
              descrizione: capitolo.datiCapitolo ? capitolo.datiCapitolo.descrizione : null,
              titolo: capitolo.datiCapitolo ? capitolo.datiCapitolo.titolo : null,
              macro: capitolo.datiCapitolo ? capitolo.datiCapitolo.macro : null,
              missione: capitolo.datiCapitolo ? capitolo.datiCapitolo.missione : null,
              programma: capitolo.datiCapitolo ? capitolo.datiCapitolo.programma : null,
              previsione: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.previsione)) : null,
              assestato: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.assestato)) : null,
              impegnato: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.impegnato)) : null,
              previsione_1: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.previsione_1)) : null,
              assestato_1: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.assestato_1)) : null,
              impegnato_1: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.impegnato_1)) : null,
              previsione_2: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.previsione_2)) : null,
              assestato_2: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.assestato_2)) : null,
              impegnato_2: capitolo.datiCapitolo ? this.transformValueImporto(Number(capitolo.datiCapitolo.impegnato_2)) : null,
              notent: [capitolo.proDetcon.notent, StringValidator.patternValidator()],
              isNew: false,
            });
            this.formCapitoliUsc.push(tempRow);
          });
        },
        err => {
          this.checkErrorStatus(err);
          this.showAlertError(err);
        }
      );
  }

  createCapitoliObj(): any {
    const cape: Array<any> = [];
    const capu: Array<any> = [];
    this.formCapitoliEnt.controls.forEach((cap: any) => {
      cape.push({
        proDetcon: {
          id: cap.value.id,
          idPro: this.idPro,
          codentcon: cap.value.codentcon,
          codentcol: cap.value.codentcol ? cap.value.codentcol : null,
          tipent: 'CAPE',
          notent: cap.value.notent,
        },
      });
    });
    this.formCapitoliUsc.controls.forEach((cap: any) => {
      capu.push({
        proDetcon: {
          id: cap.value.id,
          idPro: this.idPro,
          codentcon: cap.value.codentcon,
          codentcol: cap.value.codentcol ? cap.value.codentcol : null,
          tipent: 'CAPU',
          notent: cap.value.notent,
        },
      });
    });
    delete this.capitoli.progetto.proDetconsById;
    this.capitoliObj = {
      progetto: this.capitoli.progetto,
      capitoliEntrata: cape,
      capitoliUscita: capu,
    };
    return this.capitoliObj;
  }

  submitModifiche(): void {
    this.capitoliService
      .modificaCapitoliProgetto(this.createCapitoliObj())
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.showAlertMessage(res);
          this.reload();
        },
        (err: any) => {
          this.showAlertError(err);
        }
      );
  }

  setModalPage(tipo: string): void {
    this.tipo = tipo;
    this.showModal = true;
  }

  setModalCape(): void {
    this.codiceCapitolo = '';
    this.showModalCape = true;
  }

  setModalCapu(): void {
    this.codiceCapitolo = '';
    this.showModalCapu = true;
  }

  updateCodCap(value: any): void {
    this.codiceCapitolo = value;
  }

  //Ricarica dati
  reload(): void {
    if (!this.canDeactivate()) {
      const msg = this.translateService.instant('modals.refresh');
      this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
      this.modalConfirmRef.result.then((result: any) => {
        if (result === true) {
          this.refresh(this.formCapitoliEnt);
          this.refresh(this.formCapitoliUsc);
          this.ngOnInit();
        }
      });
    } else {
      this.refresh(this.formCapitoliEnt);
      this.refresh(this.formCapitoliUsc);
      this.ngOnInit();
    }
  }

  inserisciCapitoloEntrata(): void {
    this.capitoliService
      .getCapitoloEntrata(this.codiceCapitolo)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.onAddRow(this.formCapitoliEnt, res);
          this.showModalCape = false;
        },
        (err: any) => {
          this.showModalCape = true;
          this.showAlertError(err);
        }
      );
  }

  inserisciCapitoloUscita(): void {
    this.capitoliService
      .getCapitoloUscita(this.codiceCapitolo)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.onAddRow(this.formCapitoliUsc, res);
          this.showModalCapu = false;
        },
        (err: any) => {
          this.showModalCapu = true;
          this.showAlertError(err);
        }
      );
  }

  //metodo che gestisce l'aggiunta di una nuova riga alle tabelle capitoli di entrata / uscita
  onAddRow(form: FormArray, element: any): void {
    if (form === this.formCapitoliEnt) {
      this.tempRow = this._fb.group({
        codentcon: element.id,
        descrizione: element.descrizione,
        titolo: element.titolo,
        macro: element.macro,
        missione: element.missione,
        programma: element.programma,
        previsione: this.transformValueImporto(element.previsione),
        assestato: this.transformValueImporto(element.assestato),
        impegnato: this.transformValueImporto(element.impegnato),
        previsione_1: this.transformValueImporto(element.previsione_1),
        assestato_1: this.transformValueImporto(element.assestato_1),
        impegnato_1: this.transformValueImporto(element.impegnato_1),
        previsione_2: this.transformValueImporto(element.previsione_2),
        assestato_2: this.transformValueImporto(element.assestato_2),
        impegnato_2: this.transformValueImporto(element.impegnato_2),
        notent: [element.notent, StringValidator.patternValidator()],
        isNew: true,
      });
      form.push(this.tempRow);
    } else {
      this.tempRow = this._fb.group({
        codentcon: element.id,
        codentcol: null,
        descrizione: element.descrizione,
        titolo: element.titolo,
        macro: element.macro,
        missione: element.missione,
        programma: element.programma,
        previsione: this.transformValueImporto(element.previsione),
        assestato: this.transformValueImporto(element.assestato),
        impegnato: this.transformValueImporto(element.impegnato),
        previsione_1: this.transformValueImporto(element.previsione_1),
        assestato_1: this.transformValueImporto(element.assestato_1),
        impegnato_1: this.transformValueImporto(element.impegnato_1),
        previsione_2: this.transformValueImporto(element.previsione_2),
        assestato_2: this.transformValueImporto(element.assestato_2),
        impegnato_2: this.transformValueImporto(element.impegnato_2),
        notent: [element.notent, StringValidator.patternValidator()],
        isNew: true,
      });
      form.push(this.tempRow);
    }
    this.codiceCapitolo = '';
  }

  // metodo che viene richiamato quando si clicca sul cestino di fianco alla riga
  onRemoveRow(form: FormArray): void {
    this.capitoliService
      .cancellaCapitoloProgetto(form.at(this.curIndex).get('id')?.value)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.showAlertMessage(res);
          this.reload();
          form.removeAt(this.curIndex);
        },
        (err: any) => {
          this.showAlertError(err);
        }
      );
  }

  refresh(form: FormArray): void {
    while (form.length !== 0) {
      form.removeAt(0);
    }
    form.markAsPristine();
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  canDeactivate(): Observable<boolean> | boolean {
    return true;
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
