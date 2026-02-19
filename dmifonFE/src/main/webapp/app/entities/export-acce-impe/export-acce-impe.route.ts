import { Routes } from '@angular/router';
import { ExportAcceImpeComponent } from './export-acce-impe.component';

export const EXPORT_ACCE_IMPE_ROUTE: Routes = [
  {
    path: '',
    component: ExportAcceImpeComponent,
    data: {
      pageTitle: 'export.home.titleImp',
      breadcrumb: '',
    },
  },
];
