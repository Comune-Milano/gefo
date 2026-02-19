import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { GestPrevisioniUpdateComponent } from './gest-previsioni-update.component';
import { GEST_PREVISIONI_ROUTE } from './gest-previsioni.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { GestPrevisioniComponent } from './gest-previsioni.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { JoyrideModule } from 'ngx-joyride';

@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild(GEST_PREVISIONI_ROUTE),
    JoyrideModule.forChild(),
    NgxPaginationModule,
    ApplicationPipesModule,
  ],
  declarations: [GestPrevisioniComponent, GestPrevisioniUpdateComponent],
  entryComponents: [],
})
export class GestPrevisioniModule {
  static entry = GestPrevisioniComponent;
}
