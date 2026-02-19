import { Component, Input, OnDestroy, OnInit } from '@angular/core';

import { Alert } from 'app/core/util/alert.service';
import { AlertService } from './alert.service';

@Component({
  selector: 'jhi-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
})
export class AlertComponent implements OnInit, OnDestroy {
  alerts: Alert[] = [];
  viewAlert: boolean = false;
  testo: string;

  constructor(private alertService: AlertService) {
    this.testo = this.alertService.testo;
  }

  ngOnInit(): void {}

  setClasses(alert: Alert): { [key: string]: boolean } {
    const classes = { 'jhi-toast': Boolean(alert.toast) };
    if (alert.position) {
      return { ...classes, [alert.position]: true };
    }
    return classes;
  }

  ngOnDestroy(): void {}

  close(alert: Alert): void {
    alert.close?.(this.alerts);
  }
}
