import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class BatchService {
  private isUpdating = false;
  constructor(private http: HttpClient, private accountService: AccountService) {}

  getIsUpdating(): boolean {
    return this.isUpdating;
  }

  setIsUpdating(val: boolean): void {
    this.isUpdating = val;
  }

  //ritorna tutti i municipi
  aggiornamentoEntitaContabili(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/batch/aggiornamentoEntitaContabili?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
