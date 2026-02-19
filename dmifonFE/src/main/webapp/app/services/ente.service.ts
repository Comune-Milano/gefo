import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IMacroProg } from 'app/models/macro_prog.model';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class EnteService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca macroprogetti con autocomplete
  getEnte(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/ente/getEnte?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
