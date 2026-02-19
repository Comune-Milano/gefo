import { Routes } from '@angular/router';
import { GestProgComponent } from './gest-prog.component';
import { GestProgUpdateComponent } from './gest-prog-update.component';
import { UnSaveChangeGuard } from 'app/guards/un-save-change.guard';
import { GestProgAccImpComponent } from './gest-prog-acc-imp.component';
import { VisTotaliComponent } from 'app/entities/vis-totali/vis-totali.component';
import { DetConAccComponent } from '../det-con-acc/det-con-acc.component';
import { GestProgResComponent } from './gest-prog-res.component';
import { GestProgCapitoliComponent } from './gest-prog-capitoli.component';
import { DetConCapComponent } from '../det-con-cap/det-con-cap.component';
import { AvanzamentiComponent } from '../avanzamenti/avanzamenti.component';
import { DdrComponent } from '../ddr/ddr.component';
import { GestPrevisioniComponent } from '../previsioni/gest-previsioni.component';
import { AvanzamentiUpdateComponent } from '../avanzamenti/avanzamenti-update.component';
import { MilestoneComponent } from '../avanzamenti/milestone.component';
import { DdrUpdateComponent } from '../ddr/ddr-update.component';
import { GestPrevisioniUpdateComponent } from '../previsioni/gest-previsioni-update.component';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';
import { EndpointsGuard } from '../../guards/endpoints.guard';
import { GestCommentiRichiesteComponent } from '../gest-commenti-richieste/gest-commenti-richieste.component';
import { GestCommentiRichiesteUpdateComponent } from '../gest-commenti-richieste/gest-commenti-richieste-update.component';
import { GestAllegatiComponent } from '../gest-allegati/gest-allegati.component';

export const GEST_PROG_ROUTE: Routes = [
  {
    path: 'new',
    component: GestProgUpdateComponent,
    data: {
      pageTitle: 'gestProg.detail.titleNew',
      breadcrumb: 'Nuovo progetto',
    },
    canActivate: [EndpointsGuard.forEndpoint('/inserisciProgetto')],
    canDeactivate: [UnSaveChangeGuard],
  },
  {
    path: ':id/viewDetail',
    // component: GestProgUpdateComponent,
    data: {
      pageTitle: 'gestProg.detail.titleModify',
      breadcrumb: 'Scheda progetto',
    },
    // canDeactivate: [UnSaveChangeGuard],
    children: [
      {
        path: '',
        component: GestProgUpdateComponent,
        data: {
          breadcrumb: '',
        },
        canDeactivate: [UnSaveChangeGuard],
      },
      {
        path: 'capitoli',
        data: {
          pageTitle: 'DetCon.home.titleCap',
          breadcrumb: 'Capitoli',
        },
        children: [
          {
            path: '',
            component: GestProgCapitoliComponent,
            data: {
              breadcrumb: '',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
          {
            path: 'det-con-cape',
            component: DetConCapComponent,
            data: {
              pageTitle: 'DetCon.home.titleAcc',
              breadcrumb: 'Dettaglio capitoli di entrata',
            },
          },
          {
            path: 'det-con-capu',
            component: DetConCapComponent,
            data: {
              pageTitle: 'DetCon.home.titleImp',
              breadcrumb: 'Dettaglio capitoli di uscita',
            },
          },
          {
            path: ':entityType/totali',
            component: VisTotaliComponent,
            data: {
              pageTitle: 'gestProg.detail.titleTotali',
              breadcrumb: 'Totali',
            },
          },
        ],
      },
      {
        path: 'acc-imp',
        data: {
          pageTitle: 'gestProg.detail.titleAccImp',
          breadcrumb: 'Accertamenti/Impegni',
        },
        children: [
          {
            path: '',
            component: GestProgAccImpComponent,
            data: {
              breadcrumb: '',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
          {
            path: 'det-con-acc',
            component: DetConAccComponent,
            data: {
              pageTitle: 'DetCon.home.titleAcc',
              breadcrumb: 'Dettaglio contabile accertamenti',
            },
          },
          {
            path: 'det-con-imp',
            component: DetConAccComponent,
            data: {
              pageTitle: 'DetCon.home.titleImp',
              breadcrumb: 'Dettaglio contabile impegni',
            },
          },
          {
            path: ':entityType/totali',
            component: VisTotaliComponent,
            data: {
              pageTitle: 'gestProg.detail.titleTotali',
              breadcrumb: 'Totali',
            },
          },
        ],
      },
      {
        path: 'allegati/:tipEnt',
        data: {
          pageTitle: 'gestProg.detail.titleAllegati',
          breadcrumb: 'Allegati',
        },
        children: [
          {
            path: '',
            component: GestAllegatiComponent,
            data: {
              breadcrumb: '',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
        ],
      },
      {
        path: 'responsabili',
        data: {
          pageTitle: 'gestProg.detail.titleRes',
          breadcrumb: 'Responsabili',
        },
        children: [
          {
            path: '',
            component: GestProgResComponent,
            data: {
              breadcrumb: '',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
        ],
      },
      {
        path: 'gest-avanzamenti',
        data: {
          pageTitle: 'proAva.home.title',
          breadcrumb: 'Avanzamenti',
        },
        children: [
          {
            path: '',
            component: AvanzamentiComponent,
            data: {
              breadcrumb: '',
              previousPath: '/gest-progetti',
            },
          },
          {
            path: 'new',
            component: AvanzamentiUpdateComponent,
            data: {
              pageTitle: 'proAva.detail.titleNew',
              breadcrumb: 'Nuovo avanzamento',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
          {
            path: ':idAva/viewDetail',
            data: {
              pageTitle: 'proAva.detail.titleModify',
              breadcrumb: 'Scheda avanzamento',
            },
            children: [
              {
                path: '',
                component: AvanzamentiUpdateComponent,
                data: {
                  breadcrumb: '',
                },
                canDeactivate: [UnSaveChangeGuard],
              },
              {
                path: 'milestone/:idFas',
                data: {
                  pageTitle: 'bandi.detail.titleTotali',
                  breadcrumb: 'Milestone fase',
                },
                children: [
                  {
                    path: '',
                    component: MilestoneComponent,
                    data: {
                      breadcrumb: '',
                    },
                    // canDeactivate: [UnSaveChangeGuard],
                  },
                ],
              },
            ],
          },
        ],
      },
      {
        path: 'ddr',
        data: {
          pageTitle: 'gestProg.detail.titleDdr',
          breadcrumb: 'DDR',
        },
        children: [
          {
            path: '',
            component: DdrComponent,
            data: {
              breadcrumb: '',
              previousPath: '/gest-progetti',
            },
          },
          {
            path: 'new',
            component: DdrUpdateComponent,
            data: {
              pageTitle: 'ddr.detail.titleNew',
              breadcrumb: 'Nuovo Ddr',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
          {
            path: ':idDdr/viewDetail',
            data: {
              pageTitle: 'ddr.detail.titleModify',
              breadcrumb: 'Scheda Ddr',
            },
            children: [
              {
                path: '',
                component: DdrUpdateComponent,
                data: {
                  breadcrumb: '',
                },
                canDeactivate: [UnSaveChangeGuard],
              },
            ],
          },
        ],
      },
      {
        path: 'gest-previsioni',
        data: {
          pageTitle: 'gestProg.detail.titlePrevisioni',
          breadcrumb: 'Previsioni',
        },
        children: [
          {
            path: '',
            component: GestPrevisioniComponent,
            data: {
              breadcrumb: '',
              previousPath: '/gest-progetti',
            },
          },
          {
            path: 'new',
            component: GestPrevisioniUpdateComponent,
            data: {
              pageTitle: 'Prev.detail.titleNew',
              breadcrumb: 'Nuova previsione',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
          {
            path: ':idPre/viewDetail',
            component: GestPrevisioniUpdateComponent,
            data: {
              pageTitle: 'Prev.detail.titleModify',
              breadcrumb: 'Scheda previsione',
            },
            canDeactivate: [UnSaveChangeGuard],
          },
        ],
      },
      {
        path: 'gest-commenti-richieste',
        data: {
          pageTitle: 'gestProg.detail.titleCommRic',
          breadcrumb: 'Commenti / richieste',
        },
        children: [
          {
            path: '',
            component: GestCommentiRichiesteComponent,
            data: {
              breadcrumb: '',
              previousPath: '/gest-progetti',
            },
          },
          {
            path: 'new',
            component: GestCommentiRichiesteUpdateComponent,
            data: {
              pageTitle: 'commRic.detail.titleNew',
              breadcrumb: 'Nuovo commento / richiesta',
            },
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
        ],
      },
      {
        path: ':entityType/totali',
        data: {
          pageTitle: 'gestProg.detail.titleTotali',
          breadcrumb: 'Totali',
        },
        children: [
          {
            path: '',
            component: VisTotaliComponent,
            data: {
              breadcrumb: '',
            },
            // canDeactivate: [UnSaveChangeGuard],
          },
        ],
      },
    ],
  },
  //  {
  //    path: ':id/viewDetail/acc-imp',
  //    component: GestProgAccImpComponent,
  //    data: {
  //      pageTitle: 'gestProg.detail.titleAccImp',
  //      breadcrumb: 'Accertamenti/Impegni',
  //    },
  //    canDeactivate: [UnSaveChangeGuard],
  //  },
  //  {
  //    path: ':id/viewDetail/allegati',
  //    component: GestProgAllegatiComponent,
  //    data: {
  //      pageTitle: 'gestProg.detail.titleAllegati',
  //      breadcrumb: 'Allegati',
  //    },
  //    canDeactivate: [UnSaveChangeGuard],
  //  },
  {
    path: '',
    component: GestProgComponent,
    data: {
      pageTitle: 'gestProg.home.title',
      breadcrumb: '',
    },
  },
];
