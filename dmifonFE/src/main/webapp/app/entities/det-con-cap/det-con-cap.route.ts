import { Routes } from '@angular/router';

import { DetConCapComponent } from './det-con-cap.component';

export const DET_CON_CAP: Routes = [
  {
    path: 'capitoli-entrata',
    component: DetConCapComponent,
    data: {
      pageTitle: 'DetCon.home.titleCap',
      breadcrumb: '',
    },
  },
  {
    path: 'capitoli-uscita',
    component: DetConCapComponent,
    data: {
      pageTitle: 'DetCon.home.titleCap',
      breadcrumb: '',
    },
  },
];
