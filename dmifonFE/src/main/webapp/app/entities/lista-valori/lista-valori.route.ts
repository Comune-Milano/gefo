import { Routes } from '@angular/router';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { EndpointsGuard } from '../../guards/endpoints.guard';
import { ListaValoriComponent } from './lista-valori.component';
import { ListaValoriUpdateComponent } from './lista-valori-update.component';

export const LISTAVALORI_ROUTE: Routes = [
  {
    path: 'new',
    component: ListaValoriUpdateComponent,
    data: {
      pageTitle: 'lisVal.detail.titleNew',
      breadcrumb: 'Nuova Lista Valori',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciListaValore')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    component: ListaValoriUpdateComponent,
    data: {
      pageTitle: 'lisVal.detail.titleModify',
      breadcrumb: 'Scheda Lista Valori',
    },
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: '',
    component: ListaValoriComponent,
    data: {
      pageTitle: 'lisVal.home.title',
      breadcrumb: '',
    },
  },
];
