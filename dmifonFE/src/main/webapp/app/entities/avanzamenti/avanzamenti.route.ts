import { Routes } from '@angular/router';

import { AvanzamentiComponent } from './avanzamenti.component';
import { AvanzamentiUpdateComponent } from './avanzamenti-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { VisTotaliComponent } from 'app/entities/vis-totali/vis-totali.component';
import { MilestoneComponent } from './milestone.component';
import { EndpointsGuard } from '../../guards/endpoints.guard';
import { GestAllegatiComponent } from '../gest-allegati/gest-allegati.component';

export const AVANZAMENTI_ROUTE: Routes = [
  {
    path: 'new',
    component: AvanzamentiUpdateComponent,
    data: {
      pageTitle: 'proAva.detail.titleNew',
      breadcrumb: 'Nuovo avanzamento',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciAvanzamento')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':idAva/viewDetail',
    data: {
      pageTitle: 'proAva.detail.titleModify',
      breadcrumb: 'Scheda avanzamento',
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
        component: AvanzamentiUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
      {
        path: 'milestone/:idFas',
        data: {
          pageTitle: 'bandi.detail.titleTotali',
          breadcrumb: 'Milestone fase',
        },
        children: [
          {
            path: '',
            component: MilestoneComponent,
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
    component: AvanzamentiComponent,
    data: {
      pageTitle: 'proAva.home.title',
      breadcrumb: '',
    },
  },
];
