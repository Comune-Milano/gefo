import { Routes } from '@angular/router';
import { ExportProgComponent } from './export-prog.component';

export const EXPORT_PROG_ROUTE: Routes = [
  {
    path: '',
    component: ExportProgComponent,
    data: {
      pageTitle: 'export.home.titleProg',
      breadcrumb: '',
    },
  },
];
