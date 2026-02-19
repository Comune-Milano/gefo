import { Routes } from '@angular/router';

import { AmmUtentiComponent } from './amm-utenti.component';
import { AmmUtentiUpdateComponent } from './amm-utenti-update.component';
import { AmmRuoliComponent } from 'app/entities/amm-ruoli/amm-ruoli.component';
import { AmmRuoliUpdateComponent } from 'app/entities/amm-ruoli/amm-ruoli-update.component';
import { AmmDirComponent } from 'app/entities/amm-dir/amm-dir.component';
import { AmmDirUpdateComponent } from 'app/entities/amm-dir/amm-dir-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const AMM_UTENTI_ROUTE: Routes = [
  {
    path: '',
    component: AmmUtentiComponent,
    data: {
      pageTitle: 'ammUtenti.home.title',
      breadcrumb: '',
    },
  },
  {
    path: 'new',
    component: AmmUtentiUpdateComponent,
    data: {
      pageTitle: 'ammUtenti.detail.title',
      breadcrumb: 'Nuovo utente',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciUtente')],
  },
  {
    path: ':id/viewDetail',
    data: {
      pageTitle: 'ammUtenti.detail.title',
      breadcrumb: 'Scheda utente',
    },
    children: [
      {
        path: '',
        component: AmmUtentiUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
      {
        path: 'amm-ruoli',
        data: {
          pageTitle: 'ammRuoli.home.title',
          breadcrumb: 'Ruoli',
        },
        children: [
          {
            path: '',
            component: AmmRuoliComponent,
            data: {
              breadcrumb: '',
            },
          },
          {
            path: 'new',
            component: AmmRuoliUpdateComponent,
            data: {
              pageTitle: 'ammRuoli.detail.titleNew',
              breadcrumb: 'Nuovo ruolo',
            },
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
        ],
      },
      {
        path: 'amm-dir',
        data: {
          pageTitle: 'ammDir.home.title',
          breadcrumb: 'Direzioni centrali',
        },
        children: [
          {
            path: '',
            component: AmmDirComponent,
            data: {
              breadcrumb: '',
            },
          },
          {
            path: 'new',
            component: AmmDirUpdateComponent,
            data: {
              pageTitle: 'ammDir.detail.titleNew',
              breadcrumb: 'Nuova direzione centrale',
            },
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
        ],
      },
    ],
  },
];
