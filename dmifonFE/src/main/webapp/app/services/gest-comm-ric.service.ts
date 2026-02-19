import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class GestCommRicService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca richieste con filtri
  ricercaRichiesta(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/richiesta/ricercaRichiesta?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ricerca richieste con filtri
  ricercaRichiestaAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/richiesta/ricercaRichiestaAutocomplete?idUteRuo=${this.accountService.getCurIdUteRuo()}&autocomplete=${autocomplete}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna la richiesta dato l'id
  getRichiesta(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/richiesta/getRichiesta?idRic=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica della richiesta dal dettaglio richiesta
  modificaRichiesta(proric: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/richiesta/modificaRichiesta?idUteRuo=${this.accountService.getCurIdUteRuo()}`, proric, {
        observe: 'response',
      })
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di una nuova richiesta nel db
  inserisciRichiesta(prev: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/richiesta/inserisciRichiesta?idUteRuo=${this.accountService.getCurIdUteRuo()}`, prev)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione della previsione dal dettaglio previsione
  cancellaRichiestaUtente(idRicUte: number): Observable<any> {
    return this.http
      .delete<any>(
        `services/dmifonbe/api/richiesta/cancellaRichiestaUtente?idRicUte=${idRicUte}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione della previsione dal dettaglio previsione
  cancellaRichiesta(idRic: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/richiesta/cancellaRichiesta?idRic=${idRic}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
