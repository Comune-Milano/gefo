import { Routes } from '@angular/router';

import { DizMacroProgComponent } from './diz-macro-prog.component';
import { DizMacroProgUpdateComponent } from './diz-macro-prog-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const DIZ_MACRO_PROG_ROUTE: Routes = [
  {
    path: 'new',
    component: DizMacroProgUpdateComponent,
    data: {
      pageTitle: 'dizMacroProg.detail.titleNew',
      breadcrumb: 'Nuovo macro progetto',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciMacroProgetto')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    component: DizMacroProgUpdateComponent,
    data: {
      pageTitle: 'dizMacroProg.detail.titleModify',
      breadcrumb: 'Scheda macro progetto',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: '',
    component: DizMacroProgComponent,
    data: {
      pageTitle: 'dizMacroProg.home.title',
      breadcrumb: '',
    },
  },
];
