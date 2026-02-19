import { Routes } from '@angular/router';

import { AmmDirComponent } from './amm-dir.component';
import { AmmDirUpdateComponent } from './amm-dir-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const AMM_DIR_ROUTE: Routes = [
  {
    path: 'new',
    component: AmmDirUpdateComponent,
    data: {
      pageTitle: 'ammDir.detail.titleNew',
      breadcrumb: 'Nuova direzione centrale',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciDirezione')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    component: AmmDirUpdateComponent,
    data: {
      pageTitle: 'ammDir.detail.titleModify',
      breadcrumb: 'Scheda direzione centrale',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: '',
    component: AmmDirComponent,
    data: {
      pageTitle: 'ammDir.home.title',
      breadcrumb: '',
    },
  },
];
