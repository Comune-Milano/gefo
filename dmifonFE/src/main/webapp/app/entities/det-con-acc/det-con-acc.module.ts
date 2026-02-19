import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { DET_CON_ACC } from './det-con-acc.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { DetConAccComponent } from './det-con-acc.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(DET_CON_ACC), NgxPaginationModule, ApplicationPipesModule],
  declarations: [DetConAccComponent],
  entryComponents: [],
  exports: [DetConAccComponent],
})
export class DetConAccModule {
  static entry = DetConAccModule;
}
