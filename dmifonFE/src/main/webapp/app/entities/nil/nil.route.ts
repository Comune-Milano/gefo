import { Routes } from '@angular/router';

import { NilComponent } from './nil.component';
import { NilUpdateComponent } from './nil-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';
import { VisTotaliComponent } from '../vis-totali/vis-totali.component';
import { TipFinUpdateComponent } from '../tip-fin/tip-fin-update.component';

export const NIL_ROUTE: Routes = [
  {
    path: '',
    component: NilComponent,
    data: {
      pageTitle: 'nil.home.title',
      breadcrumb: '',
    },
  },
  {
    path: 'new',
    component: NilUpdateComponent,
    data: {
      pageTitle: 'nil.detail.titleNew',
      breadcrumb: 'Nuovo nil',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciNil')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    data: {
      pageTitle: 'nil.detail.titleModify',
      breadcrumb: 'Scheda Nil',
    },
    children: [
      {
        path: '',
        component: NilUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
      {
        path: ':entityType/totali',
        data: {
          pageTitle: 'nil.detail.titleAllegati',
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
];
