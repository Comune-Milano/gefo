import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class StaBanService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti gli stati bando
  getAllStatiBando() {
    return this.http.get(`services/dmifonbe/api/staban/getAllStatiBando?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
