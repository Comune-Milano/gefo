import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { IDdr } from '../models/ddr.model';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class DdraService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca ddra con autocomplete
  ricercaDdraAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/ddra/ricercaDdraAutocomplete?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  ricercaDdra(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/ddra/ricercaDdra?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  inserisciDdra(ddra: any): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/ddra/inserisciDdra?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ddra).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  modificaDdra(ddra: any): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/ddra/modificaDdra?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ddra).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  cancellaDdra(idDdra: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/ddra/cancellaDdra?idDdra=${idDdra}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getDdra(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/ddra/getDdra?idDdra=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  associaDdrADdra(ddrDaAssociare: {} & Array<number>): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/ddra/associaDdrADdra?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ddrDaAssociare)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
