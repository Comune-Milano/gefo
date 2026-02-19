import { Routes } from '@angular/router';

import { GestCommentiRichiesteComponent } from './gest-commenti-richieste.component';
import { GestCommentiRichiesteUpdateComponent } from './gest-commenti-richieste-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';

export const GEST_COMMENTI_ROUTE: Routes = [
  {
    path: 'new',
    component: GestCommentiRichiesteUpdateComponent,
    data: {
      pageTitle: 'commRic.detail.titleNew',
      breadcrumb: 'Nuovo commento / richiesta',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciRichiesta')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':idRic/viewDetail',
    component: GestCommentiRichiesteUpdateComponent,
    data: {
      pageTitle: 'commRic.detail.titleModify',
      breadcrumb: 'Scheda commento / richiesta',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: '',
    component: GestCommentiRichiesteComponent,
    data: {
      pageTitle: 'commRic.home.title',
      breadcrumb: '',
    },
  },
];
