import { Component, Injector, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VisTotaliService } from '../../services/vis-totali.service';
import { IVisTotali, IRisorse } from '../../models/vis_totali.model';
import { UpdatePage } from 'app/shared/baseclass/update-page';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'jhi-vis-totali',
  templateUrl: './vis-totali.component.html',
  styleUrls: ['./vis-totali.component.scss'],
})
export class VisTotaliComponent extends UpdatePage implements OnInit {
  curVisTotali!: IVisTotali;
  formVisTotali!: FormGroup;
  formDesc!: FormGroup;
  entityType = '';
  public titlePage = '';
  protected router: Router;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    private visTotaliService: VisTotaliService,
    private _fb: FormBuilder,
    protected translateService: TranslateService,
    protected ngbModal: NgbModal
  ) {
    super(injector);
    this.modifica = false;
    this.router = injector.get(Router);
  }

  ngOnInit(): void {
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    this.id = this.activatedRoute.snapshot.paramMap.get('id') ?? '';
    this.entityType = this.activatedRoute.snapshot.paramMap.get('entityType') ?? '';
    // recupero i totali
    this.visTotaliService
      .getTotali(this.entityType, this.id)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(
        (res: IVisTotali) => {
          this.curVisTotali = res;
          this.titlePage = ' ' + this.curVisTotali.desTipEnt + '   ' + this.curVisTotali.desEnt;
          this.formVisTotali = this._fb.group({
            nroprofon: [this.curVisTotali.nroprofon],
            imprisfon: [this.transformValueImporto(this.curVisTotali.imprisfon)],
            impacc: [this.transformValueImporto(this.curVisTotali.impacc)],
            impecoacc: [this.transformValueImporto(this.curVisTotali.impecoacc)],
            imprev: [this.transformValueImporto(this.curVisTotali.imprev)],
            impimp: [this.transformValueImporto(this.curVisTotali.impimp)],
            impecoimp: [this.transformValueImporto(this.curVisTotali.impecoimp)],
            impliq: [this.transformValueImporto(this.curVisTotali.impliq)],
            impigv: [this.transformValueImporto(this.curVisTotali.impigv)],
            impigvdel: [this.transformValueImporto(this.curVisTotali.impigvdel)],
            impman: [this.transformValueImporto(this.curVisTotali.impman)],
            impdaacc: [this.transformValueImporto(this.curVisTotali.impdaacc)],
            impdaimp: [this.transformValueImporto(this.curVisTotali.impdaimp)],
            imprisfonDDR: [this.transformValueImporto(this.curVisTotali.imprisfon)],
            impddr: [this.transformValueImporto(this.curVisTotali.impddr)],
            impamm: [this.transformValueImporto(this.curVisTotali.impamm)],
            imptra: [this.transformValueImporto(this.curVisTotali.imptra)],
            impinc: [this.transformValueImporto(this.curVisTotali.impinc)],
            impprvese: [this.transformValueImporto(this.curVisTotali.impprvese)],
            impprvese1: [this.transformValueImporto(this.curVisTotali.impprvese1)],
            impprvsuc: [this.transformValueImporto(this.curVisTotali.impprvsuc)],
            risorse: this._fb.array([]),
            risorseDiCui: this._fb.array([]),
            desTipEnt: [this.curVisTotali.desTipEnt],
            desEnt: [this.curVisTotali.desEnt],
          });

          this.formDesc = this._fb.group({
            entityType: [this.entityType],
          });

          this.formVisTotali.disable();
          //aggiunta risorse
          this.curVisTotali.risorse.forEach((element: IRisorse) => {
            this.addRisorseFormGroup(element);
          });
          //aggiunta risorse di cui
          if (this.curVisTotali.risorseDiCui) {
            this.curVisTotali.risorseDiCui.forEach((element: IRisorse) => {
              this.addRisorseDiCuiFormGroup(element);
            });
          }
        },
        err => {
          this.checkErrorStatus(err);
          this.showAlertError(err);
        }
      );
  }

  createRisorseFormGroup(risorsa: IRisorse): FormGroup {
    return new FormGroup({
      destipimp: new FormControl(risorsa.desTipImp),
      imppro: new FormControl(this.transformValueImporto(risorsa.sumImpPro)),
      sumImpAcc: new FormControl(this.transformValueImporto(risorsa.sumImpAcc)),
      sumImpRev: new FormControl(this.transformValueImporto(risorsa.sumImpRev)),
      sumImpImp: new FormControl(this.transformValueImporto(risorsa.sumImpImp)),
      sumImpMan: new FormControl(this.transformValueImporto(risorsa.sumImpMan)),
    });
  }

  addRisorseFormGroup(risorsa: IRisorse): void {
    const risorse = this.formVisTotali.get('risorse') as FormArray;
    risorse.push(this.createRisorseFormGroup(risorsa));
  }

  addRisorseDiCuiFormGroup(risorsaDiCui: IRisorse): void {
    const risorse = this.formVisTotali.get('risorseDiCui') as FormArray;
    risorse.push(this.createRisorseFormGroup(risorsaDiCui));
  }

  getRisorseFormArray(): FormArray<any> {
    return this.formVisTotali.get('risorse') as FormArray;
  }

  getRisorseDiCuiFormArray(): FormArray<any> {
    return this.formVisTotali.get('risorseDiCui') as FormArray;
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  //Ricarica dati
  reload(): void {
    this.ngOnInit();
  }

  getFormElement(elemento: string): AbstractControl<any> {
    return this.formVisTotali.controls[elemento];
  }

  ngOnDestroy(): void {
    super.closeAlert(); // Chiudo gli alert
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
