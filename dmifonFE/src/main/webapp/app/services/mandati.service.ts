import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class MandatiService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna il mandato richiesto
  getMandato(codentcon: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/mandato/getMandato?codentcon=${codentcon}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ricerca mandati con filtri
  ricercaMandati(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/mandato/ricercaMandati?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
