import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Iamm_ruo } from 'app/models/amm_ruo.model';
import { ItipFin } from 'app/models/tip_fin.model';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class TipFinService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti i ruoli presenti nel database
  ricercaTipoFinanziamento(filtri: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/tipfin/ricercaTipoFinanziamento?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna il ruolo richiesto
  getTipoFinanziamento(id: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/tipfin/getTipoFinanziamento?idtipfin=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica del ruolo dal dettaglio ruolo
  modificaTipoFinanziamento(tipfin: ItipFin) {
    return this.http
      .post<any>(`services/dmifonbe/api/tipfin/modificaTipoFinanziamento?idUteRuo=${this.accountService.getCurIdUteRuo()}`, tipfin)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un nuovo ruolo nel db
  inserisciTipoFinanziamento(tipfin: ItipFin) {
    return this.http
      .post<any>(`services/dmifonbe/api/tipfin/inserisciTipoFinanziamento?idUteRuo=${this.accountService.getCurIdUteRuo()}`, tipfin)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione di un ruolo dal db
  cancellaTipoFinanziamento(id: number) {
    return this.http
      .delete<any>(`services/dmifonbe/api/tipfin/cancellaTipoFinanziamento?idtipfin=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ricerca tipi finanziamento autocomplete
  ricercaTipoFinanziamentoAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/tipfin/ricercaTipoFinanziamentoAutocomplete?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
