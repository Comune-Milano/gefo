import { Routes } from '@angular/router';
import { ExportAvaFasComponent } from './export-ava-fas.component';

export const EXPORT_AVA_FAS_ROUTE: Routes = [
  {
    path: '',
    component: ExportAvaFasComponent,
    data: {
      pageTitle: 'export.home.titleAvaFas',
      breadcrumb: '',
    },
  },
];
