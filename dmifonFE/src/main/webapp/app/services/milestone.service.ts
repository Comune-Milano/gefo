import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';
@Injectable({ providedIn: 'root' })
export class MilestoneService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna le milestone di una fase
  getMilestoneFase(id: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/milestone/getMilestoneFase?idFas=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica delle milestone dal dettaglio milestone
  modificaMilestoneFase(mil: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/milestone/modificaMilestoneFase?idUteRuo=${this.accountService.getCurIdUteRuo()}`, mil)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione della milestone dal dettaglio milestone
  cancellaMilestone(id: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/milestone/cancellaMilestone?idMil=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
