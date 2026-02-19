import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthGuardService } from '../guards/auth-guard.guard';
import { TipoFaseModule } from './tipo-fase/tipo-fase.module';

@NgModule({
  imports: [
    RouterModule.forChild([
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
      {
        path: 'amm-utenti',
        loadChildren: () => import('./amm-utenti/amm-utenti.module').then(m => m.AmmUtentiModule),
        data: {
          pageTitle: 'ammUtenti.home.title',
          breadcrumb: 'Utenti',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'amm-ruoli',
        loadChildren: () => import('./amm-ruoli/amm-ruoli.module').then(m => m.AmmRuoliModule),
        data: {
          pageTitle: 'ammRuoli.home.title',
          breadcrumb: 'Ruoli',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'amm-dir',
        loadChildren: () => import('./amm-dir/amm-dir.module').then(m => m.AmmDirModule),
        data: {
          pageTitle: 'ammDir.home.title',
          breadcrumb: 'Direzioni',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'tip-fin',
        loadChildren: () => import('./tip-fin/tip-fin.module').then(m => m.TipFinModule),
        data: {
          pageTitle: 'tipFin.home.title',
          breadcrumb: 'Tipi finanziamento',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'diz-macro-prog',
        loadChildren: () => import('./diz-macro-prog/diz-macro-prog.module').then(m => m.DizMacroProgModule),
        data: {
          pageTitle: 'dizMacroProg.home.title',
          breadcrumb: 'Macro Progetti',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'gest-progetti',
        loadChildren: () => import('./gest-progetti/gest-prog.module').then(m => m.GestProgModule),
        data: {
          pageTitle: 'ammDir.home.title',
          breadcrumb: 'Progetti',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'gest-avanzamenti',
        loadChildren: () => import('./avanzamenti/avanzamenti.module').then(m => m.AvanzamentiModule),
        data: {
          pageTitle: 'ammDir.home.title',
          breadcrumb: 'Avanzamenti',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'vis-totali',
        loadChildren: () => import('./vis-totali/vis-totali.module').then(m => m.VisTotaliModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Totali',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'allegati',
        loadChildren: () => import('./gest-allegati/gest-allegati.module').then(m => m.GestAllegatiModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Allegati',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'det-con-cape',
        loadChildren: () => import('./det-con-cap/det-con-cap.module').then(m => m.DetConCapModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Dettaglio contabile capitoli entrata',
          tipo: 'Capitoli entrata',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'det-con-capu',
        loadChildren: () => import('./det-con-cap/det-con-cap.module').then(m => m.DetConCapModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Dettaglio contabile capitoli uscita',
          tipo: 'Capitoli uscita',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'det-con-acc',
        loadChildren: () => import('./det-con-acc/det-con-acc.module').then(m => m.DetConAccModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Dettaglio contabile accertamenti',
          tipo: 'Accertamenti',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'det-con-imp',
        loadChildren: () => import('./det-con-acc/det-con-acc.module').then(m => m.DetConAccModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Dettaglio contabile impegni',
          tipo: 'Impegni',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'bandi',
        loadChildren: () => import('./bandi/bandi.module').then(m => m.BandiModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Bandi',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'tematiche',
        loadChildren: () => import('./tematiche/tematiche.module').then(m => m.TematicheModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Tematiche',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'municipi',
        loadChildren: () => import('./municipi/municipi.module').then(m => m.MunicipiModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Municipi',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'nil',
        loadChildren: () => import('./nil/nil.module').then(m => m.NilModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Nil',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'lista-valori',
        loadChildren: () => import('./lista-valori/lista-valori.module').then(m => m.ListaValoriModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Lista Valori',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'tipo-fase',
        loadChildren: () => import('./tipo-fase/tipo-fase.module').then(m => m.TipoFaseModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Tipo Fase',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'ddr',
        loadChildren: () => import('./ddr/ddr.module').then(m => m.DdrModule),
        data: {
          pageTitle: '',
          breadcrumb: 'DDR',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'ddra',
        loadChildren: () => import('./ddra/ddra.module').then(m => m.DdraModule),
        data: {
          pageTitle: '',
          breadcrumb: 'DDRA',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'gest-previsioni',
        loadChildren: () => import('./previsioni/gest-previsioni.module').then(m => m.GestPrevisioniModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Previsioni',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'gest-commenti-richieste',
        loadChildren: () => import('./gest-commenti-richieste/gest-commenti-richieste.module').then(m => m.GestCommentiRichiesteModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Commenti - Richieste',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'export-progetti',
        loadChildren: () => import('./export-prog/export-prog.module').then(m => m.ExportProgModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Export progetti',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'export-impe-acce',
        loadChildren: () => import('./export-acce-impe/export-acce-impe.module').then(m => m.ExportAcceImpeModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Export impegni',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'export-cap',
        loadChildren: () => import('./export-capitoli/export-cap.module').then(m => m.ExportCapModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Export capitoli',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'export-ddr',
        loadChildren: () => import('./export-ddr/export-ddr.module').then(m => m.ExportDdrModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Export DDR',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'export-ava-fas',
        loadChildren: () => import('./export-ava-fas/export-ava-fas.module').then(m => m.ExportAvaFasModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Export Avanzamenti - Fasi',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'export-man',
        loadChildren: () => import('./export-man/export-man.module').then(m => m.ExportManModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Export Mandati',
        },
        canActivate: [AuthGuardService],
      },
      {
        path: 'ela',
        loadChildren: () => import('./amm-ela/amm-ela.module').then(m => m.AmmElaModule),
        data: {
          pageTitle: '',
          breadcrumb: 'Elaborazioni',
        },
        canActivate: [AuthGuardService],
      },
    ]),
  ],
})
export class EntityRoutingModule {}
