import { Routes } from '@angular/router';

import { BandiComponent } from './bandi.component';
import { BandiUpdateComponent } from './bandi-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { VisTotaliComponent } from 'app/entities/vis-totali/vis-totali.component';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const BANDI_ROUTE: Routes = [
  {
    path: 'new',
    component: BandiUpdateComponent,
    data: {
      pageTitle: 'bandi.detail.titleNew',
      breadcrumb: 'Nuovo bando',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciBando')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    data: {
      pageTitle: 'bandi.detail.titleModify',
      breadcrumb: 'Scheda bando',
    },
    children: [
      {
        path: '',
        component: BandiUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
      {
        path: ':entityType/totali',
        data: {
          pageTitle: 'bandi.detail.titleTotali',
          breadcrumb: 'Totali',
        },
        children: [
          {
            path: '',
            component: VisTotaliComponent,
            data: {
              breadcrumb: '',
            },
            // canDeactivate: [UnSaveChangeGuard],
          },
        ],
      },
    ],
  },
  {
    path: '',
    component: BandiComponent,
    data: {
      pageTitle: 'bandi.home.title',
      breadcrumb: '',
    },
  },
];
