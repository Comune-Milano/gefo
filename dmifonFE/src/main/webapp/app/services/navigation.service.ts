import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { SessionStorageService } from 'ngx-webstorage';

@Injectable()
export class NavigationService {
  private history: string[] = [];

  constructor(private router: Router, private location: Location, private sessionStorage: SessionStorageService) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Se sono nella home o se cambio route
        if (
          event.urlAfterRedirects === '/' ||
          (this.history.length > 1 &&
            !event.urlAfterRedirects.split('/')[1].startsWith(this.history[this.history.length - 1].split('/')[1]))
        ) {
          // Azzero la history e il sessioon storage
          this.history = this.history.slice(0, 1);
          this.sessionStorage.clear();
        }
        // Se le url sono uguali e il penultimo elemento corrisponde alla url vuol dire che sto arrivando da una cancellazione, rimuovo gli ultimi due elementi della history
        if ((event.url === event.urlAfterRedirects) && ((this.history.length > 2 && this.history[this.history.length - 2] === event.urlAfterRedirects)
        || (this.history.length > 2 && this.history[this.history.length - 2].includes('/new')))) {
			this.history.pop();
			this.history.pop();
		}
        // verifico che la route non sia presente nella history
		if (!this.history.includes(event.urlAfterRedirects)) {
          this.history.push(event.urlAfterRedirects); // aggiungo la route alla history
        }
      }
    });
  }

  back(): void {
    let goBack2 = false;
    if (this.history.length > 2 && this.history[this.history.length - 2].includes('/new')) {
	  goBack2 = true;
	}
    this.history.pop();    
    if (this.history.length > 1) {     
      if (!goBack2) {
      	this.router.navigate([this.history[this.history.length - 1]]);
      } else {
		this.router.navigate([this.history[this.history.length - 2]]);
	  }
    } else {
      this.router.navigateByUrl('/');
    }
  }
}
