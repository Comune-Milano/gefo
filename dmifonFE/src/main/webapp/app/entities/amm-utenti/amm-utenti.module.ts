import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { AmmUtentiComponent } from './amm-utenti.component';
import { AmmUtentiUpdateComponent } from './amm-utenti-update.component';
import { AMM_UTENTI_ROUTE } from './amm-utenti.route';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(AMM_UTENTI_ROUTE), ApplicationPipesModule],
  declarations: [AmmUtentiComponent, AmmUtentiUpdateComponent],
  entryComponents: [],
})
export class AmmUtentiModule {
  static entry = AmmUtentiComponent;
}
