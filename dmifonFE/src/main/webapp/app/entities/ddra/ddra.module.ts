import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { DdraUpdateComponent } from './ddra-update.component';
import { DDRA_ROUTE } from './ddra.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { DdraComponent } from './ddra.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { DecimalPipe } from '@angular/common';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(DDRA_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [DdraComponent, DdraUpdateComponent],
  entryComponents: [],
  providers: [DecimalPipe],
})
export class DdraModule {
  static entry = DdraComponent;
}
