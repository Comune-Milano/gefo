import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { ItipFin } from '../models/tip_fin.model';
import { IProg } from '../models/progetto.model';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class VisTotaliService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  // restituisce i totali
  getTotali(entityType: string, id: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/totali/getTotali?entityType=${entityType}&id=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
