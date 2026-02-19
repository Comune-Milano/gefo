import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { DET_CON_CAP } from './det-con-cap.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { DetConCapComponent } from './det-con-cap.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(DET_CON_CAP), NgxPaginationModule, ApplicationPipesModule],
  declarations: [DetConCapComponent],
  entryComponents: [],
  exports: [DetConCapComponent],
})
export class DetConCapModule {
  static entry = DetConCapModule;
}
