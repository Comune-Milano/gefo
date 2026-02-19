import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class TipFasService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  ricercaTipiFase(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/tipofase/ricercaTipoFase?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  getTipoFase(idTipFas: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/tipofase/getTipoFase?idTipFas=${idTipFas}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  modificaTipoFase(tipFas: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/tipofase/modificaTipoFase?idUteRuo=${this.accountService.getCurIdUteRuo()}`, tipFas).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  inserisciTipoFase(tipFas: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/tipofase/inserisciTipoFase?idUteRuo=${this.accountService.getCurIdUteRuo()}`, tipFas).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  cancellaTipoFas(id: string): Observable<any> {
    return this.http
      .delete(`services/dmifonbe/api/tipofase/cancellaTipoFase?idTipFas=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
