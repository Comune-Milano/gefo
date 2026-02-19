import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { ExportManComponent } from './export-man.component';
import { EXPORT_MAN_ROUTE } from './export-man.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(EXPORT_MAN_ROUTE), ApplicationPipesModule],
  declarations: [ExportManComponent],
  entryComponents: [],
})
export class ExportManModule {
  static entry = ExportManComponent;
}
