import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { BandiUpdateComponent } from './bandi-update.component';
import { BANDI_ROUTE } from './bandi.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { BandiComponent } from './bandi.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { DecimalPipe } from '@angular/common';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(BANDI_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [BandiComponent, BandiUpdateComponent],
  entryComponents: [],
  providers: [DecimalPipe],
})
export class BandiModule {
  static entry = BandiComponent;
}
