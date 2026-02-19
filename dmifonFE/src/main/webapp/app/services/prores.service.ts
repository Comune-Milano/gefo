import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class ProResService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti i responsabili progetto
  getResponsabiliProgetto(idPro: number): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/prores/getResponsabiliProgetto?idPro=${idPro}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica dei responsabili di un progetto
  modificaResponsabiliProgetto(responsabili: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/prores/modificaResponsabiliProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, responsabili)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
