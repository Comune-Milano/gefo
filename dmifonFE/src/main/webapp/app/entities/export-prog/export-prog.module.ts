import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { ExportProgComponent } from './export-prog.component';
import { EXPORT_PROG_ROUTE } from './export-prog.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(EXPORT_PROG_ROUTE), ApplicationPipesModule],
  declarations: [ExportProgComponent],
  entryComponents: [],
})
export class ExportProgModule {
  static entry = ExportProgComponent;
}
