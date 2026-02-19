import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { AlertService } from './alert.service';
import { Alert } from '../../core/util/alert.service';

@Component({
  selector: 'jhi-alert-warning',
  templateUrl: './alert-warning.component.html',
  styleUrls: ['./alert.component.scss'],
})
export class AlertWarningComponent {
  viewAlert = false;
  testo: string;
  alerts: Alert[] = [];

  constructor(private alertService: AlertService, private eventManager: EventManager, translateService: TranslateService) {
    this.testo = this.alertService.testo;
  }

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
