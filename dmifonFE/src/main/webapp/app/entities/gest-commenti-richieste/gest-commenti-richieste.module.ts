import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';

import { GestCommentiRichiesteUpdateComponent } from './gest-commenti-richieste-update.component';
import { GEST_COMMENTI_ROUTE } from './gest-commenti-richieste.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { GestCommentiRichiesteComponent } from './gest-commenti-richieste.component';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(GEST_COMMENTI_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [GestCommentiRichiesteComponent, GestCommentiRichiesteUpdateComponent],
  entryComponents: [],
})
export class GestCommentiRichiesteModule {
  static entry = GestCommentiRichiesteComponent;
}
