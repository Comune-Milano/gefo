import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { ExportDdrComponent } from './export-ddr.component';
import { EXPORT_ACCE_IMPE_ROUTE } from './export-ddr.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(EXPORT_ACCE_IMPE_ROUTE), ApplicationPipesModule],
  declarations: [ExportDdrComponent],
  entryComponents: [],
})
export class ExportDdrModule {
  static entry = ExportDdrComponent;
}
