import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { DizMacroProgUpdateComponent } from './diz-macro-prog-update.component';
import { DIZ_MACRO_PROG_ROUTE } from './diz-macro-prog.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { DizMacroProgComponent } from './diz-macro-prog.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(DIZ_MACRO_PROG_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [DizMacroProgComponent, DizMacroProgUpdateComponent],
  entryComponents: [],
})
export class DizMacroProgModule {
  static entry = DizMacroProgComponent;
}
