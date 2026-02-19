import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'app/shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { ListaValoriComponent } from './lista-valori.component';
import { LISTAVALORI_ROUTE } from './lista-valori.route';
import { ListaValoriUpdateComponent } from './lista-valori-update.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(LISTAVALORI_ROUTE), NgxPaginationModule, ApplicationPipesModule],
  declarations: [ListaValoriComponent, ListaValoriUpdateComponent],
  entryComponents: [],
})
export class ListaValoriModule {
  static entry = ListaValoriComponent;
}
