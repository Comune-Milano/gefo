import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IBan } from 'app/models/bando.model';

import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class CapitoliService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //metodo che ritorna tutti i capitoli associati ad un progetto
  getCapitoliProgetto(idPro: number): Observable<any> {
    return this.http.get(
      `services/dmifonbe/api/capitoli/getCapitoliProgetto?idPro=${idPro}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
    );
  }

  //modifica di un capitolo
  modificaCapitoliProgetto(capitolo: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/capitoli/modificaCapitoliProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, capitolo)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione di un capitolo
  cancellaCapitoloProgetto(id: number): Observable<any> {
    return this.http
      .delete<any>(
        `services/dmifonbe/api/capitoli/cancellaCapitoliProgetto?idDetCon=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //recupero di un capitolo di entrata
  getCapitoloEntrata(codentcon: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/capitoli/getCapitoloEntrata?codentcon=${codentcon}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //recupero di un capitolo di uscita
  getCapitoloUscita(codentcon: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/capitoli/getCapitoloUscita?codentcon=${codentcon}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ricerca dei capitoli di entrata con filtri
  ricercaCapitoliEntrata(filtri: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/capitoli/ricercaCapitoliEntrata?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ricerca dei capitoli di uscita con filtri
  ricercaCapitoliUscita(filtri: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/capitoli/ricercaCapitoliUscita?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
