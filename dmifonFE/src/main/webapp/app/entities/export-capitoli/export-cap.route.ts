import { Routes } from '@angular/router';
import { ExportCapComponent } from './export-cap.component';

export const EXPORT_ACCE_IMPE_ROUTE: Routes = [
  {
    path: '',
    component: ExportCapComponent,
    data: {
      pageTitle: 'export.home.titleCap',
      breadcrumb: '',
    },
  },
];
