import { Routes } from '@angular/router';
import { ExportDdrComponent } from './export-ddr.component';

export const EXPORT_ACCE_IMPE_ROUTE: Routes = [
  {
    path: '',
    component: ExportDdrComponent,
    data: {
      pageTitle: 'export.home.titleCap',
      breadcrumb: '',
    },
  },
];
