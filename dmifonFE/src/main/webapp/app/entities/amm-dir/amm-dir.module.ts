import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { AmmDirUpdateComponent } from './amm-dir-update.component';
import { AMM_DIR_ROUTE } from './amm-dir.route';
import { AmmDirComponent } from './amm-dir.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(AMM_DIR_ROUTE), ApplicationPipesModule],
  declarations: [AmmDirComponent, AmmDirUpdateComponent],
  entryComponents: [],
})
export class AmmDirModule {
  static entry = AmmDirComponent;
}
