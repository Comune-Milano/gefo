import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';
@Injectable({ providedIn: 'root' })
export class ParametroService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna le milestone di una fase
  getParametroByCodice(cod: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/parametro/getParametroByCodice?codice=${cod}&idUteRuo=${this.accountService.getCurIdUteRuo()}`, {
        responseType: 'text',
      })
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
