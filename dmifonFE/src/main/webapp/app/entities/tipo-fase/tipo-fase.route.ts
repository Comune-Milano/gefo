import { Routes } from '@angular/router';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';
import { TipoFaseComponent } from './tipo-fase.component';
import { TipoFaseUpdateComponent } from './tipo-fase-update.component';

export const TIPOFASE_ROUTE: Routes = [
  {
    path: '',
    component: TipoFaseComponent,
    data: {
      pageTitle: 'tipFas.home.title',
      breadcrumb: '',
    },
  },
  {
    path: 'new',
    component: TipoFaseUpdateComponent,
    data: {
      pageTitle: 'tipFas.detail.titleNew',
      breadcrumb: 'Nuovo tipo fase',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciTipoFase')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    component: TipoFaseUpdateComponent,
    data: {
      pageTitle: 'tipFas.detail.titleModify',
      breadcrumb: 'Scheda tipi fase',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
];
