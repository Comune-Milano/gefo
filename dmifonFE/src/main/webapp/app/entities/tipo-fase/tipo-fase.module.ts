import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'app/shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { TipoFaseComponent } from './tipo-fase.component';
import { TipoFaseUpdateComponent } from './tipo-fase-update.component';
import { TIPOFASE_ROUTE } from './tipo-fase.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(TIPOFASE_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [TipoFaseComponent, TipoFaseUpdateComponent],
  entryComponents: [],
})
export class TipoFaseModule {
  static entry = TipoFaseComponent;
}
