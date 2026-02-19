import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { MunicipiUpdateComponent } from './municipi-update.component';
import { MUNICIPI_ROUTE } from './municipi.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { MunicipiComponent } from './municipi.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(MUNICIPI_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [MunicipiComponent, MunicipiUpdateComponent],
  entryComponents: [],
})
export class MunicipiModule {
  static entry = MunicipiComponent;
}
