import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { AMM_ELA_ROUTE } from './amm-ela.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { AmmElaComponent } from './amm-ela.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(AMM_ELA_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [AmmElaComponent],
  entryComponents: [],
  exports: [AmmElaComponent],
})
export class AmmElaModule {
  static entry = AmmElaModule;
}
