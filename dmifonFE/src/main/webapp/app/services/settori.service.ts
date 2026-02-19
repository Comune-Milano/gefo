import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class SettoriService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti i settori
  ricercaSettoriAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/progetto/ricercaSettoriAutocomplete?idUteRuo=${this.accountService.getCurIdUteRuo()}&autocomplete=${autocomplete}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
