import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  public showAlert: boolean;
  public showError: boolean;
  public showWarning: boolean;
  testo: string = '';
  testoError: string = '';

  constructor() {
    this.showAlert = false;
    this.showError = false;
    this.showWarning = false;
  }
}
