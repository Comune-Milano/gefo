import { Routes } from '@angular/router';

import { DetConAccComponent } from './det-con-acc.component';

export const DET_CON_ACC: Routes = [
  {
    path: 'accertamenti',
    component: DetConAccComponent,
    data: {
      pageTitle: 'DetCon.home.title',
      breadcrumb: '',
    },
  },
  {
    path: 'impegni',
    component: DetConAccComponent,
    data: {
      pageTitle: 'DetCon.home.title',
      breadcrumb: '',
    },
  },
];
