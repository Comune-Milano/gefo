import { Component, OnDestroy } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

import { AlertError } from './alert-error.model';

import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { AlertService } from './alert.service';

@Component({
  selector: 'jhi-alert-error',
  templateUrl: './alert-error.component.html',
  styleUrls: ['./alert.component.scss'],
})
export class AlertErrorComponent {
  viewAlert: boolean = false;
  testo: string;

  constructor(private alertService: AlertService, private eventManager: EventManager, translateService: TranslateService) {
    this.testo = this.alertService.testoError;
  }
}
