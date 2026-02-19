import { Routes } from '@angular/router';

import { MunicipiComponent } from './municipi.component';
import { MunicipiUpdateComponent } from './municipi-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const MUNICIPI_ROUTE: Routes = [
  {
    path: 'new',
    component: MunicipiUpdateComponent,
    data: {
      pageTitle: 'mun.detail.titleNew',
      breadcrumb: 'Nuovo municipio',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciMunicipio')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    component: MunicipiUpdateComponent,
    data: {
      pageTitle: 'mun.detail.titleModify',
      breadcrumb: 'Scheda municipio',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: '',
    component: MunicipiComponent,
    data: {
      pageTitle: 'mun.home.title',
      breadcrumb: '',
    },
  },
];
