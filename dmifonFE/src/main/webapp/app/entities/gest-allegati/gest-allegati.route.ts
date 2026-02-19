import { Routes } from '@angular/router';
import { GestAllegatiComponent } from './gest-allegati.component';

export const GEST_ALL_ROUTE: Routes = [
  {
    path: '',
    component: GestAllegatiComponent,
    data: {
      pageTitle: 'gestProg.detail.titleAllegati',
      breadcrumb: '',
    },
  },
];
