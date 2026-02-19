import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class StaFinService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti gli stati finanziamento
  getAllStatiFinanziamento(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/stafin/getAllStatiFinanziamento?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
