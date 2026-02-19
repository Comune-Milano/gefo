import { Routes } from '@angular/router';

import { DdrComponent } from './ddr.component';
import { DdrUpdateComponent } from './ddr-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';
import { GestAllegatiComponent } from '../gest-allegati/gest-allegati.component';

export const DDR_ROUTE: Routes = [
  {
    path: 'new',
    component: DdrUpdateComponent,
    data: {
      pageTitle: 'ddr.detail.titleNew',
      breadcrumb: 'Nuovo Ddr',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciDdr')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':idDdr/viewDetail',
    data: {
      pageTitle: 'ddr.detail.titleModify',
      breadcrumb: 'Scheda Ddr',
    },
    children: [
      {
        path: 'allegati/:tipEnt',
        data: {
          pageTitle: 'gestProg.detail.titleAllegati',
          breadcrumb: 'Allegati',
        },
        children: [
          {
            path: '',
            component: GestAllegatiComponent,
            data: {
              breadcrumb: '',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
        ],
      },
      {
        path: '',
        component: DdrUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
    ],
  },
  {
    path: '',
    component: DdrComponent,
    data: {
      pageTitle: 'ddr.home.title',
      breadcrumb: '',
    },
  },
];
