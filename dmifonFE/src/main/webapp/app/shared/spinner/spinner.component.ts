import { Component } from '@angular/core';
import { LoaderService } from 'app/services/loader.service';

@Component({
  selector: 'jhi-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss'],
})
export class SpinnerComponent {
  constructor(public loader: LoaderService) {}  
}
