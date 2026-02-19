import { Routes } from '@angular/router';

import { AmmElaComponent } from './amm-ela.component';

export const AMM_ELA_ROUTE: Routes = [
  {
    path: '',
    component: AmmElaComponent,
    data: {
      pageTitle: 'ammEla.home.title',
      breadcrumb: '',
    },
  },
];
