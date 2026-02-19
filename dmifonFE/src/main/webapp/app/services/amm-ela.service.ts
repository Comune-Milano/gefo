import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class AmmElaService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti i municipi
  ricercaElaborazione(filtri: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/elaborazione/ricercaElaborazione?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
