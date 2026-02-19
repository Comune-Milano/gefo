import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { GestProgUpdateComponent } from './gest-prog-update.component';
import { GEST_PROG_ROUTE } from './gest-prog.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { GestProgComponent } from './gest-prog.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { GestProgAccImpComponent } from './gest-prog-acc-imp.component';
import { DecimalPipe } from '@angular/common';
import { GestProgResComponent } from './gest-prog-res.component';
import { GestProgCapitoliComponent } from './gest-prog-capitoli.component';
import { DetConAccModule } from '../det-con-acc/det-con-acc.module';
import { DetConCapModule } from '../det-con-cap/det-con-cap.module';
import { JoyrideModule } from 'ngx-joyride';

@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild(GEST_PROG_ROUTE),
    JoyrideModule.forChild(),
    NgxPaginationModule,
    ApplicationPipesModule,
    DetConAccModule,
    DetConCapModule,
  ],
  declarations: [GestProgComponent, GestProgUpdateComponent, GestProgAccImpComponent, GestProgResComponent, GestProgCapitoliComponent],
  entryComponents: [],
  providers: [DecimalPipe],
})
export class GestProgModule {
  static entry = GestProgComponent;
}
