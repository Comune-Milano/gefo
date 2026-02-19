import { NgModule } from '@angular/core';
import { HighlightPipe } from './highlight-text.pipe';

@NgModule({
  imports: [
    // dep modules
  ],
  declarations: [HighlightPipe],
  exports: [HighlightPipe],
})
export class ApplicationPipesModule {}
