import { Directive, Injectable, Injector, Input, OnDestroy } from '@angular/core';
import { DecimalPipe } from '@angular/common';
// import { FormControl, FormGroup } from '@angular/forms';
//import { ITEMS_PER_PAGE, TABLE_MAX_SIZE } from 'app/shared/constants/constants';
//import { PaginationInstance } from 'ngx-pagination';
//import { SessionStorageService } from 'ngx-webstorage';
//import { SortDirection } from 'app/shared/sort/sortable.directive';
//import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'app/shared/alert/alert.service';
import { ErrorService } from 'app/services/error.service';
import { Router } from '@angular/router';
import { SessionStorageService } from 'ngx-webstorage';
import { LoginService } from '../../login/login.service';

@Directive()
export class CommonPage {
  dotChar = new RegExp('[.]', 'g'); // Carattere punto
  checkFormToLeave = true;
  viewAlert = false;
  viewError = false;
  viewWarning = false;
  viewDelete = false;
  testoAlert!: string;
  timer: any;
  protected alertService: AlertService;
  protected errorService: ErrorService;
  protected _decimalPipe: DecimalPipe;
  protected $sessionStorage: SessionStorageService;
  protected router: Router;
  private accountService: LoginService;

  constructor(injector: Injector) {
    this.router = injector.get(Router);
    this.alertService = injector.get(AlertService);
    this.errorService = injector.get(ErrorService);
    this._decimalPipe = injector.get(DecimalPipe);
    this.$sessionStorage = injector.get(SessionStorageService);
  }

  showAlertMessage(res: any): void {
    this.alertService.testo = res.userMessage;
    this.viewError = false;
    this.alertService.showError = false;
    this.alertService.showAlert = true;
    this.checkFormToLeave = false;
    if (this.timer) {
      this.viewAlert = false;
      this.alertService.showAlert = false;
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        this.viewAlert = true;
        this.alertService.showAlert = true;
      }, 200);
    }
    this.timer = setTimeout(() => {
      this.timer = undefined;
      this.viewAlert = false;
      this.alertService.showAlert = false;
    }, 5000);
  }

  showAlertWarning(res: any): void {
    this.alertService.testo = res.userMessage;
    this.viewError = false;
    this.viewAlert = false;
    this.viewWarning = true;
    this.alertService.showError = false;
    this.alertService.showAlert = false;
    this.alertService.showWarning = true;
    this.checkFormToLeave = false;
    if (this.timer) {
      this.viewWarning = false;
      this.alertService.showWarning = false;
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        this.viewWarning = true;
        this.alertService.showWarning = true;
      }, 200);
    }
    this.timer = setTimeout(() => {
      this.timer = undefined;
      this.viewWarning = false;
      this.alertService.showWarning = false;
    }, 5000);
  }

  showAlertInsertMessage(): void {
    this.viewError = false;
    this.alertService.showError = false;
    this.alertService.showAlert = true;
    setTimeout(() => {
      this.viewAlert = false;
      this.alertService.showAlert = false;
      this.setOpInsert(false);
    }, 5000);
  }

  showAlertDeleteMessage(): void {
    this.viewError = false;
    this.alertService.showError = false;
    this.alertService.showAlert = true;
    setTimeout(() => {
      this.viewAlert = false;
      this.viewDelete = false;
      this.alertService.showAlert = false;
      this.setOpDelete(false);
    }, 5000);
  }

  showAlertError(err: any): void {
    this.alertService.testoError = this.errorService.getErrorDescription(err);
    this.viewAlert = false;
    this.alertService.showAlert = false;
    this.alertService.showError = true;
    this.viewError = true;
    setTimeout(() => {
      this.viewError = false;
      this.alertService.showError = false;
    }, 5000);
  }

  closeAlert(): void {
    this.viewError = false;
    this.alertService.showError = false;
    this.viewAlert = false;
    this.alertService.showAlert = false;
    this.viewWarning = false;
    this.alertService.showWarning = false;
  }

  getOpInsert(): boolean {
    return Boolean(this.$sessionStorage.retrieve('insertOp')); // Inserimento
  }

  setOpInsert(insertOp: boolean): void {
    this.$sessionStorage.store('insertOp', insertOp); // Inserimento
  }

  setOpDelete(deleteOp: boolean): void {
    this.$sessionStorage.store('deleteOp', deleteOp); // Cancellazione
  }

  getOpDelete(): boolean {
    return Boolean(this.$sessionStorage.retrieve('deleteOp')); // memorizzo il filtro nel session storage
  }

  //  patchValueImportoFormGroup(form: any): void {
  //	Object.keys(form.controls).forEach(key => {
  //		if (form.controls[key]) {
  //	    	console.log(form.controls[key]["importo"]);
  //	    }
  //	});
  //  }

  //  patchValueImporto(value: any): any {
  //	if (typeof(value) === 'number' && value > 0) {
  //		return this._decimalPipe.transform(value, '1.2-2') ?? ''; // Trasformo
  //	} else {
  //		return Number(value.toString().replace(this.dotChar, '').replace(/,/g, '.'));
  //	}
  //  }

  patchValueImporto(value: string): number {
    if (value === '') {
      value = '0';
    }
    if (value.includes(',')) {
      value = value.toString().substring(0, value.toString().indexOf(',') + 3); // Seleziono solo due decimali
    }
    value = value
      .toString()
      .replace(/[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ€!$(){}[\]:;<+?\\>]/g, '')
      .replace(/-/g, '');
    return Number(value.toString().replace(this.dotChar, '').replace(/,/g, '.'));
  }

  patchValueImportoNegativo(value: string): number {
    if (value === '') {
      value = '0';
    }
    if (value.includes(',')) {
      value = value.toString().substring(0, value.toString().indexOf(',') + 3); // Seleziono solo due decimali
    }
    value = value.toString().replace(/[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ€!$(){}[\]:;<+?\\>]/g, '');
    if (value.includes('-')) {
      value = value.toString().replace(/-/g, '');
      value = '-' + value.toString();
      console.log(value.toString());
    }
    return Number(value.toString().replace(this.dotChar, '').replace(/,/g, '.'));
  }

  transformValueImporto(value: number): string {
    return this._decimalPipe.transform(value, '1.2-2') ?? ''; // Trasformo
  }

  transformPerc(value: number): string {
    return String(value) + '%';
  }

  checkErrorStatus(err: any): void {
    if (err.status === 401) {
      this.accountService.login();
    }
  }
}
