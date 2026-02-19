import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Iamm_dir } from 'app/models/amm_dir.model';

import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class DirCentraleService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //
  getDirezioni(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/direzione/ricercaDirezione?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ricerca direzioni con autocomplete
  getDirezioniAutocomplete(autocomplete: string): Observable<any> {
    let parameters = '';
    if (autocomplete) {
      parameters = '&autocomplete=' + autocomplete;
    }
    return this.http
      .get(`services/dmifonbe/api/direzione/ricercaDirezione?idUteRuo=${this.accountService.getCurIdUteRuo()}${parameters}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna il ruolo richiesto
  getDirezione(id: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/direzione/getDirezione?idDirezione=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica del ruolo dal dettaglio ruolo
  modificaDirezione(direzione: Iamm_dir) {
    return this.http
      .post<any>(`services/dmifonbe/api/direzione/modificaDirezione?idUteRuo=${this.accountService.getCurIdUteRuo()}`, direzione)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un nuovo ruolo nel db
  inserisciDirezione(direzione: Iamm_dir) {
    return this.http
      .post<any>(`services/dmifonbe/api/direzione/inserisciDirezione?idUteRuo=${this.accountService.getCurIdUteRuo()}`, direzione)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione di un ruolo dal db
  cancellaDirezione(id: number) {
    return this.http
      .delete<any>(`services/dmifonbe/api/direzione/cancellaDirezione?idDirezione=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
