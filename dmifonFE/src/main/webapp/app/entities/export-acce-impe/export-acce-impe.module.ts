import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { ExportAcceImpeComponent } from './export-acce-impe.component';
import { EXPORT_ACCE_IMPE_ROUTE } from './export-acce-impe.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(EXPORT_ACCE_IMPE_ROUTE), ApplicationPipesModule],
  declarations: [ExportAcceImpeComponent],
  entryComponents: [],
})
export class ExportAcceImpeModule {
  static entry = ExportAcceImpeComponent;
}
