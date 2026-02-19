import { Component, Injector, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AlertService } from 'app/shared/alert/alert.service';
import { TranslateService } from '@ngx-translate/core';
import { TipFinService } from 'app/services/tip-fin.service';
import { ItipFin } from 'app/models/tip_fin.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IComponentCanDeactivate } from 'app/guards/un-save-change.guard';
import { Observable, of, Subject, takeUntil } from 'rxjs';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { DetConService } from '../../services/detcon.service';
import { IDetCon } from '../../models/detcon.model';
import { CustomDialogsService } from '../../services/custom-dialogs.service';
import { ErrorService } from '../../services/error.service';
import { UpdatePage } from '../../shared/baseclass/update-page';
import { DecimalPipe } from '@angular/common';
import { ITipImp } from '../../models/tipImp.model';
import { DetConAccComponent } from '../det-con-acc/det-con-acc.component';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-gest-prog-acc-imp',
  templateUrl: './gest-prog-acc-imp.component.html',
  styleUrls: ['./gest-prog.component.scss'],
})
export class GestProgAccImpComponent extends UpdatePage implements OnInit, IComponentCanDeactivate, OnDestroy {
  @ViewChild(DetConAccComponent, { static: false }) detConAccCmp!: DetConAccComponent;
  accertamenti: Array<any> = [];
  codiceAccertamento = '';
  progettoPadreLabel = '';
  AccImpPro!: IDetCon;
  formAcc!: FormArray;
  formImp!: FormArray;
  formCro!: FormArray;
  formNote!: FormGroup;
  showModalAcc = false;
  showModalImp = false;
  showModalCro = false;
  modalConfirmRef!: NgbModalRef;
  curIndex = -1;
  tempRow: any;
  tipo = '';
  tipiImporto!: ITipImp[];
  showModal = false;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    protected alertService: AlertService,
    protected errorService: ErrorService,
    private _fb: FormBuilder,
    private detConService: DetConService,
    private activatedRoute: ActivatedRoute,
    private customDialogsService: CustomDialogsService,
    protected translateService: TranslateService,
    protected router: Router,
    protected decimalPipe: DecimalPipe,
    protected rolesCheckService: RolesCheckService
  ) {
    super(injector);
    this.formAcc = this._fb.array([]);
    this.formImp = this._fb.array([]);
    this.formCro = this._fb.array([]);
  }

  ngOnInit(): void {
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    this.id = this.activatedRoute.snapshot.paramMap.get('id') ?? '';
    this.detConService
      .getTipoImportoRisorsa()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((res: ITipImp[]) => {
        this.tipiImporto = res;
      });
    this.progettoPadreLabel = '';
    this.checkFormToLeave = true;
    this.detConService
      .getImpegniProgetto(this.id)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: IDetCon) => {
          this.AccImpPro = res;
          this.progettoPadreLabel = this.AccImpPro.progettoPadreLabel ? this.AccImpPro.progettoPadreLabel : '';
          //console.log(this.AccImpPro);
          res.accertamenti.forEach((element: any) => {
            //tabella accertamenti
            this.tempRow = this._fb.group({
              idDetcon: [element.proDetcon.id],
              idEntcon: [element.proEntcon.id],
              codentcon: [element.proDetcon.codentcon],
              idPro: [element.proDetcon.idPro],
              notent: [element.proDetcon.notent, StringValidator.patternValidator()],
              desimp: [element.proEntcon.desimp],
              idcap: [element.proEntcon.idcap],
              idcro: [element.proEntcon.idcro],
              impass: [this.transformValueImporto(element.proEntcon.impass)],
              impeco: [this.transformValueImporto(element.proEntcon.impeco)],
              impman: [this.transformValueImporto(element.proEntcon.impman)],
              despdd: [element.proEntcon.despdd],
              nroconapp: [element.proEntcon.nroconapp],
              isNew: false,
            });
            this.formAcc.push(this.tempRow);
          });
          res.impegni.forEach((element: any) => {
            //tabella impegni
            this.tempRow = this._fb.group({
              idDetcon: [element.proDetcon.id],
              idEntcon: [element.proEntcon.id],
              idPro: [element.proDetcon.idPro],
              codentcon: [element.proDetcon.codentcon],
              codentcol: [element.proDetcon.codentcol],
              desimp: [element.proEntcon.desimp],
              idcap: [element.proEntcon.idcap],
              idcro: [element.proEntcon.idcro],
              impass: [this.transformValueImporto(element.proEntcon.impass)],
              impeco: [this.transformValueImporto(element.proEntcon.impeco)],
              impliq: [this.transformValueImporto(element.proEntcon.impliq)],
              impman: [this.transformValueImporto(element.proEntcon.impman)],
              despdd: [element.proEntcon.despdd],
              nroconapp: [element.proEntcon.nroconapp],
              codgami: [element.proEntcon.codgami],
              notent: [element.proDetcon.notent, StringValidator.patternValidator()],
              idTipimp: [element.proDetcon.idTipimp],
              isNew: false,
            });
            this.formImp.push(this.tempRow);
          });
          res.crono.forEach((element: any) => {
            //tabella crono
            this.tempRow = this._fb.group({
              idDetcon: [element.proDetcon.id],
              idEntcon: [element.proEntcon.id],
              codentcon: [element.proDetcon.codentcon],
              desimp: [element.proEntcon.desimp],
              idPro: [element.proDetcon.idPro],
              notent: [element.proDetcon.notent, StringValidator.patternValidator()],
              idTipimp: [element.proDetcon.idTipimp],
              isNew: false,
            });
            this.formCro.push(this.tempRow);
          });
          this.formNote = this._fb.group({
            id: [res.progetto.id],
            codpro: [res.progetto.codpro],
            despro: [res.progetto.despro],
            idTipfin: [res.progetto.idTipfin],
            idMacpro: [res.progetto.idMacpro],
            idBan: [res.progetto.idBan],
            idPropad: [res.progetto.idPropad],
            iddecpro: [res.progetto.iddecpro],
            livpro: [res.progetto.livpro],
            cntlivinf: [res.progetto.cntlivinf],
            codappren: [res.progetto.codappren],
            codcup: [res.progetto.codcup],
            codcig: [res.progetto.codcig],
            tippro: [res.progetto.tippro],
            codtippro: [res.progetto.codtippro],
            desaffhou: [res.progetto.desaffhou],
            flgopeavv: [res.progetto.flgopeavv],
            notpro: [res.progetto.notpro],
            impigv: [res.progetto.impigv],
            rifigv: [res.progetto.rifigv],
            impigvdel: [res.progetto.impigvdel],
            notigv: [res.progetto.notigv],
            notaff: [res.progetto.notaff],
            impimppre: [this.transformValueImporto(res.progetto.impimppre)],
            impaccpre: [this.transformValueImporto(res.progetto.impaccpre)],
            impmanpre: [this.transformValueImporto(res.progetto.impmanpre)],
            imprevpre: [this.transformValueImporto(res.progetto.imprevpre)],
            impimpric: [this.transformValueImporto(res.progetto.impimpric)],
            codgami: [res.progetto.codgami],
            idDir: [res.progetto.idDir],
            codset: [res.progetto.codset],
            codass: [res.progetto.codass],
            veraff: [res.progetto.veraff],
            estaff: [res.progetto.estaff],
            codcui: [res.progetto.codcui],
            idStafin: [res.progetto.idStafin],
            idStapro: [res.progetto.idStapro],
            idTem: [res.progetto.idTem],
            delcan: [res.progetto.delcan],
            notimp: [res.progetto.notimp],
            notpre: [res.progetto.notpre],
            idMun: [res.progetto.idMun],
            nil: [res.progetto.nil],
          });
        },
        error => {
          this.checkErrorStatus(error);
          this.showAlertError(error);
        }
      );
  }

  initAcc(): void {
    this.codiceAccertamento = '';
    this.showModalAcc = true;
  }

  initImp(): void {
    this.codiceAccertamento = '';
    this.showModalImp = true;
  }

  initCro(): void {
    this.codiceAccertamento = '';
    this.showModalCro = true;
  }

  createAccObject(form: FormArray): Array<any> {
    const accertamenti: Array<any> = [];
    form.controls.forEach(acc => {
      if (acc.value.idDetcon) {
        accertamenti.push({
          proDetcon: {
            id: acc.value.idDetcon ? acc.value.idDetcon : null,
            codentcon: acc.value.codentcon ? acc.value.codentcon : null,
            codentcol: acc.value.codentcol ? acc.value.codentcol : null,
            idPro: acc.value.idPro ? acc.value.idPro : null,
            tipentcol: acc.value.codentcol ? 'ACCE' : null,
            idTipimp: acc.value.idTipimp ? acc.value.idTipimp : null,
            notent: acc.value.notent ? acc.value.notent : '',
            tipent: form === this.formAcc ? 'ACCE' : form === this.formImp ? 'IMPE' : 'CRON',
          },
          proEntcon: {
            id: acc.value.idEntcon ? acc.value.idEntcon : null,
            anncmp: acc.value.anncmp ? acc.value.anncmp : null,
            codentcon: acc.value.codentcon ? acc.value.codentcon : null,
            codgami: acc.value.codgami ? acc.value.codgami : null,
            codcig: acc.value.codcig ? acc.value.codcig : null,
            codcup: acc.value.codcup ? acc.value.codcup : null,
            desimp: acc.value.desimp ? acc.value.desimp : null,
            despdd: acc.value.despdd ? acc.value.despdd : null,
            idcap: acc.value.idcap ? acc.value.idcap : null,
            idcro: acc.value.idcro ? acc.value.idcro : null,
            impass: acc.value.impass ? this.patchValueImporto(acc.value.impass) : 0,
            impeco: acc.value.impeco ? this.patchValueImporto(acc.value.impeco) : 0,
            impliq: acc.value.impliq ? this.patchValueImporto(acc.value.impliq) : 0,
            impman: acc.value.impman ? this.patchValueImporto(acc.value.impman) : 0,
            impmaneme: acc.value.impmaneme ? acc.value.impmaneme : 0,
            nroconapp: acc.value.nroconapp ? acc.value.nroconapp : 0,
            tipent: form === this.formAcc ? 'ACCE' : form === this.formImp ? 'IMPE' : 'CRON',
          },
        });
      } else {
        accertamenti.push({
          proDetcon: {
            codentcon: acc.value.codentcon ? acc.value.codentcon : null,
            codentcol: acc.value.codentcol ? acc.value.codentcol : null,
            idPro: acc.value.idPro ? acc.value.idPro : null,
            notent: acc.value.notent ? acc.value.notent : '',
            tipent: form === this.formAcc ? 'ACCE' : form === this.formImp ? 'IMPE' : 'CRON',
          },
          proEntcon: {
            anncmp: acc.value.anncmp ? acc.value.anncmp : null,
            codentcon: acc.value.codentcon ? acc.value.codentcon : null,
            codgami: acc.value.codgami ? acc.value.codgami : null,
            codcig: acc.value.codcig ? acc.value.codcig : null,
            codcup: acc.value.codcup ? acc.value.codcup : null,
            desimp: acc.value.desimp ? acc.value.desimp : null,
            despdd: acc.value.despdd ? acc.value.despdd : null,
            idcap: acc.value.idcap ? acc.value.idcap : null,
            idcro: acc.value.idcro ? acc.value.idcro : null,
            impass: acc.value.impass ? this.patchValueImporto(acc.value.impass) : 0,
            impeco: acc.value.impeco ? this.patchValueImporto(acc.value.impeco) : 0,
            impliq: acc.value.impliq ? this.patchValueImporto(acc.value.impliq) : 0,
            impman: acc.value.impman ? this.patchValueImporto(acc.value.impman) : 0,
            impmaneme: acc.value.impmaneme ? acc.value.impmaneme : 0,
            nroconapp: acc.value.nroconapp ? acc.value.nroconapp : 0,
            tipent: form === this.formAcc ? 'ACCE' : form === this.formImp ? 'IMPE' : 'CRON',
          },
        });
      }
    });
    return accertamenti;
  }

  patchImportiNote(): void {
    this.formNote.patchValue({
      impimpric: this.patchValueImporto(this.formNote.value.impimpric),
      impimppre: this.patchValueImportoNegativo(this.formNote.value.impimppre),
      impmanpre: this.patchValueImportoNegativo(this.formNote.value.impmanpre),
      impaccpre: this.patchValueImportoNegativo(this.formNote.value.impaccpre),
      imprevpre: this.patchValueImportoNegativo(this.formNote.value.imprevpre),
    });
  }

  patchError(): void {
    this.formNote.patchValue({
      impimpric: this.transformValueImporto(this.formNote.value.impimpric),
      impimppre: this.transformValueImporto(this.formNote.value.impimppre),
      impmanpre: this.transformValueImporto(this.formNote.value.impmanpre),
      impaccpre: this.transformValueImporto(this.formNote.value.impaccpre),
      imprevpre: this.transformValueImporto(this.formNote.value.imprevpre),
    });
  }

  inserisciAccertamento(): void {
    this.detConService
      .getAccertamento(this.codiceAccertamento)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe({
        next: res => {
          this.showModalAcc = false;
          this.onAddRow(this.formAcc, res);
        },
        error: (err: any) => {
          this.showAlertError(err);
          this.showModalAcc = true;
        },
      });
  }

  inserisciImpegno(): void {
    this.detConService
      .getImpegno(this.codiceAccertamento)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.showModalImp = false;
          this.onAddRow(this.formImp, res);
        },
        (err: any) => {
          this.showModalImp = true;
          this.showAlertError(err);
        }
      );
  }

  inserisciCrono(): void {
    this.detConService
      .getCrono(this.codiceAccertamento)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        res => {
          this.onAddRow(this.formCro, res);
          this.showModalCro = false;
        },
        (err: any) => {
          this.showAlertError(err);
          this.showModalCro = true;
        }
      );
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  getFormElement(elemento: string): any {
    return this.formNote.controls[elemento];
  }

  updateCodAccImp(value: any): void {
    this.codiceAccertamento = value;
  }

  //metodo che gestisce l'aggiunta di una nuova riga alle tabelle accertamenti, impegni e crono
  onAddRow(form: FormArray, element: any): void {
    if (form === this.formAcc) {
      this.tempRow = this._fb.group({
        codentcon: [element.codentcon],
        idPro: [Number(this.id)],
        notent: [element.notent],
        desimp: [element.desimp],
        idcap: [element.idcap],
        idcro: [element.idcro],
        impass: [this.transformValueImporto(element.impass)],
        impeco: [this.transformValueImporto(element.impeco)],
        impman: [this.transformValueImporto(element.impman)],
        despdd: [element.despdd],
        isNew: true,
      });
      form.push(this.tempRow);
    } else if (form === this.formImp) {
      this.tempRow = this._fb.group({
        codentcon: [element.codentcon],
        codentcol: [''],
        idPro: [Number(this.id)],
        desimp: [element.desimp],
        idcap: [element.idcap],
        idcro: [element.idcro],
        impass: [this.transformValueImporto(element.impass)],
        impeco: [this.transformValueImporto(element.impeco)],
        impliq: [this.transformValueImporto(element.impliq)],
        impman: [this.transformValueImporto(element.impman)],
        despdd: [element.despdd],
        nroconapp: [element.nroconapp],
        codgami: [element.codgami],
        notent: [''],
        idTipimp: [''],
        isNew: true,
      });
      form.push(this.tempRow);
    } else if (form === this.formCro) {
      this.tempRow = this._fb.group({
        codentcon: [element.codentcon],
        idPro: [Number(this.id)],
        desimp: [element.desimp],
        notent: [''],
        idcap: [''],
        idcro: [element.idcro],
        idTipimp: [''],
        isNew: true,
      });
      form.push(this.tempRow);
    }
    this.codiceAccertamento = '';
  }

  goToAccertamenti(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/acc-imp/det-con-acc`]);
  }

  goToImpegni(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/acc-imp/det-con-imp`]);
  }

  goToTotali(): void {
    this.router.navigate([`gest-progetti/${this.id}/viewDetail/acc-imp/ProPro/totali`]);
  }

  // metodo che viene richiamato quando si clicca sul cestino di fianco alla riga
  onRemoveRow(form: FormArray, rowIndex: number): void {
    this.detConService
      .cancellaDettaglioContabile(form.at(this.curIndex).get('idDetcon')?.value)
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

  //submit form di modifica / inserimento nuovi accertamenti-impegni-crono
  submitForm(): void {
    this.patchImportiNote();
    this.AccImpPro = {
      accertamenti: this.createAccObject(this.formAcc),
      impegni: this.createAccObject(this.formImp),
      crono: this.createAccObject(this.formCro),
      progetto: this.formNote.value,
    };
    this.detConService
      .modificaImpegniProgetto(this.AccImpPro)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: any) => {
          this.showAlertMessage(res);
          this.reload();
        },
        (err: any) => {
          this.patchError();
          this.showAlertError(err);
        }
      );
  }

  //Ricarica dati
  reload(): void {
    if (!this.canDeactivate()) {
      const msg = this.translateService.instant('modals.refresh');
      this.modalConfirmRef = this.customDialogsService.openCustomConfirm(msg);
      this.modalConfirmRef.result.then(result => {
        if (result === true) {
          this.refresh(this.formAcc);
          this.refresh(this.formImp);
          this.refresh(this.formCro);
          this.ngOnInit();
        }
      });
    } else {
      this.refresh(this.formAcc);
      this.refresh(this.formImp);
      this.refresh(this.formCro);
      this.ngOnInit();
    }
  }

  refresh(form: FormArray): void {
    while (form.length !== 0) {
      form.removeAt(0);
    }
    form.markAsPristine();
  }

  setModalPage(tipo: string): void {
    this.tipo = tipo;
    this.showModal = true;
  }

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave) {
      if (this.formAcc.pristine && this.formImp.pristine && this.formCro.pristine && this.formNote.pristine) {
        return of(true);
      } else {
        return false;
      }
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
