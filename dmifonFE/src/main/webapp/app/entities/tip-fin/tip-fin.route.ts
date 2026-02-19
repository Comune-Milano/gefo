import { Routes } from '@angular/router';

import { TipFinComponent } from './tip-fin.component';
import { TipFinUpdateComponent } from './tip-fin-update.component';
import { VisTotaliComponent } from 'app/entities/vis-totali/vis-totali.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { GestProgComponent } from '../gest-progetti/gest-prog.component';
import { GestProgUpdateComponent } from '../gest-progetti/gest-prog-update.component';
import { GestProgAccImpComponent } from '../gest-progetti/gest-prog-acc-imp.component';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const TIP_FIN_ROUTE: Routes = [
  {
    path: 'new',
    component: TipFinUpdateComponent,
    data: {
      pageTitle: 'tipFin.detail.titleNew',
      breadcrumb: 'Nuovo tipo finanziamento',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciTipoFinanziamento')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    data: {
      pageTitle: 'tipFin.detail.titleModify',
      breadcrumb: 'Scheda tipo finanziamento',
    },
    children: [
      {
        path: '',
        component: TipFinUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
      {
        path: ':entityType/totali',
        data: {
          pageTitle: 'gestProg.detail.titleAllegati',
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
    path: ':idTipFin/gest-progetti',
    data: {
      pageTitle: 'gestProg.home.title',
      breadcrumb: 'Gestione Progetti',
    },
    children: [
      {
        path: '',
        component: GestProgComponent,
        data: {
          breadcrumb: '',
        },
      },
      {
        path: ':id/viewDetail',
        component: GestProgUpdateComponent,
        data: {
          pageTitle: 'gestProg.detail.titleModify',
          breadcrumb: 'Scheda progetto',
        },
      },
      {
        path: ':new',
        component: GestProgUpdateComponent,
        data: {
          pageTitle: 'gestProg.detail.titleNew',
          breadcrumb: 'Nuovo progetto',
        },
      },
    ],
  },
  {
    path: '',
    component: TipFinComponent,
    data: {
      pageTitle: 'tipFin.home.title',
      breadcrumb: '',
    },
  },
];
