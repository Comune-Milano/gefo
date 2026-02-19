import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'app/shared/shared.module';
import { GEST_ALL_ROUTE } from './gest-allegati.route';
import { NgxPaginationModule } from 'ngx-pagination';
import { ApplicationPipesModule } from 'app/shared/pipes/application-pipes.module';
import { DecimalPipe } from '@angular/common';
import { JoyrideModule } from 'ngx-joyride';
import { GestAllegatiComponent } from './gest-allegati.component';

@NgModule({
  imports: [SharedModule, NgxPaginationModule, ApplicationPipesModule],
  declarations: [GestAllegatiComponent],
  entryComponents: [],
  providers: [DecimalPipe],
})
export class GestAllegatiModule {
  static entry = GestAllegatiComponent;
}
