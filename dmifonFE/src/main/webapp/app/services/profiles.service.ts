import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Iamm_ruo } from 'app/models/amm_ruo.model';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class ProfilesService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti i ruoli presenti nel database
  getRuoli(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/ruolo/ricercaRuolo?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ritorna il ruolo richiesto
  getRuolo(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/ruolo/getRuolo?idRuolo=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica del ruolo dal dettaglio ruolo
  modificaRuolo(ruolo: Iamm_ruo): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/ruolo/modificaRuolo?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ruolo).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //inserimento di un nuovo ruolo nel db
  inserisciRuolo(ruolo: Iamm_ruo): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/ruolo/inserisciRuolo?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ruolo).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //eliminazione di un ruolo dal db
  cancellaRuolo(id: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/ruolo/cancellaRuolo?idRuolo=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna il ruolo richiesto
  getPermessiRuolo(): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/ruolo/getPermessiRuolo?idRuolo=${this.accountService.getCurIdRuo()}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
