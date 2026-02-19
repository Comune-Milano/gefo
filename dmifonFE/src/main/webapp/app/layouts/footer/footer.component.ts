import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { version } from 'app/app.constants';
import { ParametroService } from '../../services/parametro.service';
@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.scss'],
})
export class FooterComponent implements OnInit {
  version = version;
  year = new Date().getFullYear();
  mostraLogo: boolean;

  constructor(private translate: TranslateService, private parametroService: ParametroService) {
    this.mostraLogo = false;
  }

  ngOnInit(): void {
    this.parametroService.getParametroByCodice('LogoFooter').subscribe(
      res => {
        if (res === 'S') {
          this.mostraLogo = true;
        } else {
          this.mostraLogo = false;
        }
      },
      error => {
        this.mostraLogo = true;
        console.error('Errore nel recupero parametro LogoFooter. Il logo del footer verrà mostrato');
      }
    );
  }
}
