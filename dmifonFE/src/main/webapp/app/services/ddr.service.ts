import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { IDdr } from '../models/ddr.model';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class DdrService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca ddr con filtri e autocomplete
  ricercaDdr(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/ddr/ricercaDdr?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ricerca ddr autocomplete
  ricercaDdrAutocomplete(filtri: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/ddr/ricercaDdrAutocomplete?autocomplete=${filtri}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna il ddr richiesto
  getDdr(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/ddr/getDdr?idDdr=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica del ddr dal dettaglio ddr
  modificaDdr(ddr: IDdr): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/ddr/modificaDdr?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ddr).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //inserimento di un nuovo ddr nel db
  inserisciDdr(ddr: IDdr): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/ddr/inserisciDdr?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ddr).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //eliminazione del ddr dal dettaglio ddr
  cancellaDdr(idDdr: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/ddr/cancellaDdr?idDdr=${idDdr}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
