import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { DdrUpdateComponent } from './ddr-update.component';
import { DDR_ROUTE } from './ddr.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { DdrComponent } from './ddr.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { DecimalPipe } from '@angular/common';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(DDR_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [DdrComponent, DdrUpdateComponent],
  entryComponents: [],
  providers: [DecimalPipe],
})
export class DdrModule {
  static entry = DdrComponent;
}
