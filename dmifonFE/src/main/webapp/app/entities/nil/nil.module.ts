import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { NilUpdateComponent } from './nil-update.component';
import { NIL_ROUTE } from './nil.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { NilComponent } from './nil.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(NIL_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [NilComponent, NilUpdateComponent],
  entryComponents: [],
})
export class NilModule {
  static entry = NilComponent;
}
