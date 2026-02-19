import { Routes } from '@angular/router';

import { AmmRuoliComponent } from './amm-ruoli.component';
import { AmmRuoliUpdateComponent } from './amm-ruoli-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const AMM_PROFILI_ROUTE: Routes = [
  {
    path: 'new',
    component: AmmRuoliUpdateComponent,
    data: {
      pageTitle: 'ammRuoli.detail.titleNew',
      breadcrumb: 'Nuovo ruolo',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciRuolo')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    component: AmmRuoliUpdateComponent,
    data: {
      pageTitle: 'ammRuoli.detail.titleModify',
      breadcrumb: 'Scheda ruolo',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: '',
    component: AmmRuoliComponent,
    data: {
      pageTitle: 'ammRuoli.home.title',
      breadcrumb: '',
    },
  },
];
