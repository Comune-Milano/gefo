import { Component, Injector, OnInit } from '@angular/core';
import { UpdatePage } from '../../shared/baseclass/update-page';
import { MilestoneService } from '../../services/milestone.service';
import { IMilestoneFaseDetail } from '../../models/milestoneFaseDetail.model';
import { JsogService } from 'jsog-typescript';
import { ActivatedRoute } from '@angular/router';
import { AbstractControl, FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Dictionary } from '../../shared/utils/dictionary.class';
import { formatDate } from '@angular/common';
import { Observable, of, Subject, takeUntil } from 'rxjs';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomDialogsService } from '../../services/custom-dialogs.service';
import { TranslateService } from '@ngx-translate/core';
import { StringValidator } from '../../shared/string-validator/string-validator';

@Component({
  selector: 'jhi-milestone',
  templateUrl: './milestone.component.html',
  styleUrls: ['./avanzamenti.component.scss'],
})
export class MilestoneComponent extends UpdatePage implements OnInit {
  curMilFas!: IMilestoneFaseDetail;
  formMil: FormArray;
  id!: string;
  curIndex!: number;
  tempRow!: any;
  isSaving: boolean;
  dizionarioColori = new Dictionary();
  modalConfirmRef!: NgbModalRef;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    injector: Injector,
    private activatedRoute: ActivatedRoute,
    protected milestoneService: MilestoneService,
    private customDialogsService: CustomDialogsService,
    protected translateService: TranslateService,
    protected ngbModal: NgbModal,
    private _fb: FormBuilder
  ) {
    super(injector);
    this.formMil = this._fb.array([]);
    this.isSaving = false;
  }

  ngOnInit(): void {
    this.curIndex = 0;
    this.formMil.clear();
    this.viewAlert = this.alertService.showAlert;
    this.viewError = this.alertService.showError;
    this.id = this.activatedRoute.snapshot.paramMap.get('idFas') ?? '';
    const jsog = new JsogService();
    this.milestoneService
      .getMilestoneFase(this.id)
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((res: any) => {
        this.curMilFas = <IMilestoneFaseDetail>jsog.deserialize(res);
        //console.log(this.curMilFas);
        if (this.curMilFas.milestone?.length) {
          this.curMilFas.milestone.forEach(mil => {
            this.tempRow = this._fb.group({
              id: [mil.milestone.id],
              desmil: [mil.milestone.desmil, [Validators.required, StringValidator.patternValidator()]],
              dtaded: [mil.milestone.dtaded ? formatDate(mil.milestone.dtaded, 'yyyy-MM-dd', 'it') : null, [Validators.required]],
              dtaeff: [mil.milestone.dtaeff ? formatDate(mil.milestone.dtaeff, 'yyyy-MM-dd', 'it') : null],
              semaforoMil: [mil.semaforoMilestone.colore],
              semaforoMilDes: [mil.semaforoMilestone.descrizione],
              notmil: [mil.milestone.notmil, [StringValidator.patternValidator()]],
              isNew: false,
            });
            this.formMil.push(this.tempRow);
          });
        }
      });
  }

  convertToFormControl(absCtrl: AbstractControl | null): FormControl {
    const ctrl = absCtrl as FormControl;
    return ctrl;
  }

  addMil(): void {
    this.tempRow = this._fb.group({
      desmil: [''],
      dtaded: ['', [Validators.required]],
      dtaeff: [''],
      semaforoMil: [],
      semaforoMilDes: [],
      notmil: ['', [StringValidator.patternValidator()]],
      isNew: true,
    });
    this.formMil.push(this.tempRow);
  }

  createMilObj(): any[] {
    const Mils: Array<any> = [];
    this.formMil.controls.forEach(mil => {
      this.tempRow = {
        id: mil.get('id') ? mil.get('id')?.value : 0,
        idFas: this.id,
        desmil: mil.get('desmil')?.value,
        dtaded: mil.get('dtaded')?.value,
        dtaeff: mil.get('dtaeff')?.value,
        notmil: mil.get('notmil')?.value,
      };
      Mils.push(this.tempRow);
    });
    return Mils;
  }

  submitForm(): void {
    this.isSaving = true;
    this.milestoneService
      .modificaMilestoneFase(this.createMilObj())
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
        }
      );
  }

  onRemoveRow(rowIndex: number): void {
    if (!this.formMil.at(rowIndex).get('isNew')?.value) {
      this.milestoneService
        .cancellaMilestone(this.formMil.at(this.curIndex).get('id')?.value)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe(
          (res: any) => {
            this.showAlertMessage(res);
            this.formMil.removeAt(this.curIndex);
            this.reload();
          },
          (err: any) => {
            this.showAlertError(err);
          }
        );
    } else {
      this.formMil.removeAt(rowIndex);
    }
  }

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

  canDeactivate(): Observable<boolean> | boolean {
    if (this.checkFormToLeave) {
      return this.formMil.pristine;
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
