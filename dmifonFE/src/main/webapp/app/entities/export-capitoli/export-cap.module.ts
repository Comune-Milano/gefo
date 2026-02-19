import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { ExportCapComponent } from './export-cap.component';
import { EXPORT_ACCE_IMPE_ROUTE } from './export-cap.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(EXPORT_ACCE_IMPE_ROUTE), ApplicationPipesModule],
  declarations: [ExportCapComponent],
  entryComponents: [],
})
export class ExportCapModule {
  static entry = ExportCapComponent;
}
