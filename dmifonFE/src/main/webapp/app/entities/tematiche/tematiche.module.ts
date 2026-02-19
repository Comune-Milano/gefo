import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { TematicheUpdateComponent } from './tematiche-update.component';
import { TEMATICHE_ROUTE } from './tematiche.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { TematicheComponent } from './tematiche.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(TEMATICHE_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [TematicheComponent, TematicheUpdateComponent],
  entryComponents: [],
})
export class TematicheModule {
  static entry = TematicheComponent;
}
