import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class GestPrevisioniService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca previsioni con filtri
  ricercaPrevisione(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/prev/ricercaPrevisione?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ritorna la previsione richiesta
  getPrevisione(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/prev/getPrevisione?idPrev=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica della previsione dal dettaglio previsione
  modificaPrevisione(prev: any): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/prev/modificaPrevisione?idUteRuo=${this.accountService.getCurIdUteRuo()}`, prev).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //inserimento di una nuova previsione nel db
  inserisciPrevisione(prev: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/prev/inserisciPrevisione?idUteRuo=${this.accountService.getCurIdUteRuo()}`, prev)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione della previsione dal dettaglio previsione
  cancellaPrevisione(idPre: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/prev/cancellaPrevisione?idPre=${idPre}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getBloccaPrevisioni(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/prev/getBloccaPrevisioni?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  bloccaSbloccaPrevisioni(par: boolean): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/prev/bloccaSbloccaPrevisioni?param=${par}&idUteRuo=${this.accountService.getCurIdUteRuo()}`, {})
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
