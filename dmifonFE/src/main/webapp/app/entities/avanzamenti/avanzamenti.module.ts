import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { AvanzamentiUpdateComponent } from './avanzamenti-update.component';
import { AVANZAMENTI_ROUTE } from './avanzamenti.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { AvanzamentiComponent } from './avanzamenti.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { DecimalPipe } from '@angular/common';
import { MilestoneComponent } from './milestone.component';
import { JoyrideModule } from 'ngx-joyride';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(AVANZAMENTI_ROUTE), JoyrideModule.forChild(), NgxPaginationModule, ApplicationPipesModule],
  declarations: [AvanzamentiComponent, AvanzamentiUpdateComponent, MilestoneComponent],
  entryComponents: [],
  providers: [DecimalPipe],
})
export class AvanzamentiModule {
  static entry = AvanzamentiComponent;
}
