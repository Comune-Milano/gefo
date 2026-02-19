import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class ProNilService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca dei nil con autocomplete
  ricercaNilAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/pronil/ricercaNilAutocomplete?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ricerca dei nil con autocomplete e calcola totali
  ricercaNil(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/pronil/ricercaNil?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ricerca dei nil con autocomplete e calcola totali
  getNil(idNil: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/pronil/getNil?idNil=${idNil}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica del nil
  modificaNil(nil: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/pronil/modificaNil?idUteRuo=${this.accountService.getCurIdUteRuo()}`, nil).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica del nil
  inserisciNil(nil: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/pronil/inserisciNil?idUteRuo=${this.accountService.getCurIdUteRuo()}`, nil).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //cancellazione di un municipio
  cancellaNil(id: string): Observable<any> {
    return this.http.delete(`services/dmifonbe/api/pronil/cancellaNil?idNil=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
