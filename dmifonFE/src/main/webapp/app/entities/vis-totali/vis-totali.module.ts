import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { VisTotaliComponent } from './vis-totali.component';
// import { GEST_PROG_ROUTE } from './gest-prog.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, NgxPaginationModule, ApplicationPipesModule],
  declarations: [VisTotaliComponent],
  entryComponents: [],
  providers: [],
})
export class VisTotaliModule {
  static entry = VisTotaliComponent;
}
