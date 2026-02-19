import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { ExportAvaFasComponent } from './export-ava-fas.component';
import { EXPORT_AVA_FAS_ROUTE } from './export-ava-fas.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(EXPORT_AVA_FAS_ROUTE), ApplicationPipesModule],
  declarations: [ExportAvaFasComponent],
  entryComponents: [],
})
export class ExportAvaFasModule {
  static entry = ExportAvaFasComponent;
}
