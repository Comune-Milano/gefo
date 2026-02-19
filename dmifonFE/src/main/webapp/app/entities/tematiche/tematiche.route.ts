import { Routes } from '@angular/router';

import { TematicheComponent } from './tematiche.component';
import { TematicheUpdateComponent } from './tematiche-update.component';
import { VisTotaliComponent } from 'app/entities/vis-totali/vis-totali.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const TEMATICHE_ROUTE: Routes = [
  {
    path: 'new',
    component: TematicheUpdateComponent,
    data: {
      pageTitle: 'tematiche.detail.titleNew',
      breadcrumb: 'Nuova tematica',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciTematica')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    data: {
      pageTitle: 'tematiche.detail.titleModify',
      breadcrumb: 'Scheda tematica',
    },
    children: [
      {
        path: '',
        component: TematicheUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
      {
        path: ':entityType/totali',
        data: {
          pageTitle: 'tematiche.detail.titleAllegati',
          breadcrumb: 'Totali',
        },
        children: [
          {
            path: '',
            component: VisTotaliComponent,
            data: {
              breadcrumb: '',
            },
          },
        ],
      },
    ],
  },
  {
    path: '',
    component: TematicheComponent,
    data: {
      pageTitle: 'tematiche.home.title',
      breadcrumb: '',
    },
  },
];
