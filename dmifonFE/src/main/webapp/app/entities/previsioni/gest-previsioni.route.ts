import { Routes } from '@angular/router';

import { GestPrevisioniComponent } from './gest-previsioni.component';
import { GestPrevisioniUpdateComponent } from './gest-previsioni-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const GEST_PREVISIONI_ROUTE: Routes = [
  {
    path: 'new',
    component: GestPrevisioniUpdateComponent,
    data: {
      pageTitle: 'Prev.detail.titleNew',
      breadcrumb: 'Nuova previsione',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciPrevisione')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':idPre/viewDetail',
    component: GestPrevisioniUpdateComponent,
    data: {
      pageTitle: 'Prev.detail.titleModify',
      breadcrumb: 'Scheda previsione',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: '',
    component: GestPrevisioniComponent,
    data: {
      pageTitle: 'Prev.home.title',
      breadcrumb: '',
    },
  },
];
