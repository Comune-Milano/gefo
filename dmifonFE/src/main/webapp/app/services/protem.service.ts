import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class ProTemService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca tematiche con autocomplete
  ricercaTematica(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/protem/ricercaTematica?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
