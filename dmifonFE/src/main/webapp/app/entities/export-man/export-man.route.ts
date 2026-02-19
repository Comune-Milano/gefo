import { Routes } from '@angular/router';
import { ExportManComponent } from './export-man.component';

export const EXPORT_MAN_ROUTE: Routes = [
  {
    path: '',
    component: ExportManComponent,
    data: {
      pageTitle: 'export.home.titleMan',
      breadcrumb: '',
    },
  },
];
