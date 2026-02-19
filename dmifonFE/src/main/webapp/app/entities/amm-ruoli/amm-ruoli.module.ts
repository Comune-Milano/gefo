import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { AmmRuoliUpdateComponent } from './amm-ruoli-update.component';
import { AMM_PROFILI_ROUTE } from './amm-ruoli.route';
import { AmmRuoliComponent } from './amm-ruoli.component';
import { ApplicationPipesModule } from '../../shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(AMM_PROFILI_ROUTE), ApplicationPipesModule],
  declarations: [AmmRuoliComponent, AmmRuoliUpdateComponent],
  entryComponents: [],
})
export class AmmRuoliModule {
  static entry = AmmRuoliComponent;
}
