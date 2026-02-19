import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { TipFinUpdateComponent } from './tip-fin-update.component';
import { TIP_FIN_ROUTE } from './tip-fin.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { TipFinComponent } from './tip-fin.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(TIP_FIN_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [TipFinComponent, TipFinUpdateComponent],
  entryComponents: [],
})
export class TipFinModule {
  static entry = TipFinComponent;
}
