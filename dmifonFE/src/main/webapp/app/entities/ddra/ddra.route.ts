import { Routes } from '@angular/router';

import { DdraComponent } from './ddra.component';
import { DdraUpdateComponent } from './ddra-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { DdrComponent } from '../ddr/ddr.component';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const DDRA_ROUTE: Routes = [
  {
    path: 'new',
    component: DdraUpdateComponent,
    data: {
      pageTitle: 'ddra.detail.titleNew',
      breadcrumb: 'Nuovo Ddra',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciDdra')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    data: {
      pageTitle: 'ddra.detail.titleModify',
      breadcrumb: 'Scheda Ddra',
    },
    children: [
      {
        path: 'ElencoDdr',
        component: DdrComponent,
        data: {
          breadcrumb: 'Elenco DDR',
        },
      },
      {
        path: 'AggiungiDdr',
        component: DdrComponent,
        data: {
          breadcrumb: 'Aggiungi DDR',
        },
      },
      {
        path: '',
        component: DdraUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
    ],
  },
  {
    path: '',
    component: DdraComponent,
    data: {
      pageTitle: 'ddra.home.title',
      breadcrumb: '',
    },
  },
];
