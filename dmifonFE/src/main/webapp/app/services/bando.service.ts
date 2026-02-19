import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IBan } from 'app/models/bando.model';

import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class BandoService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti i bandi presenti nel database (oppure i bandi dati i filtri)
  ricercaBando(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/bando/ricercaBando?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ricerca dei bandi con Autocomplete
  ricercaBandoAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/bando/ricercaBandoAutocomplete?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna il bando richiesto
  getBando(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/bando/getBando?idban=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica del bando dal dettaglio bando
  modificaBando(bando: IBan): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/bando/modificaBando?idUteRuo=${this.accountService.getCurIdUteRuo()}`, bando).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //inserimento di un nuovo bando nel db
  inserisciBando(bando: IBan) {
    return this.http.post<any>(`services/dmifonbe/api/bando/inserisciBando?idUteRuo=${this.accountService.getCurIdUteRuo()}`, bando).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //eliminazione di un bando dal db
  cancellaBando(id: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/bando/cancellaBando?idban=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
