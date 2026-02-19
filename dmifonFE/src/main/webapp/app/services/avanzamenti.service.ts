import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IAvanzamentoDetail } from 'app/models/avaDetail.model';
import { catchError, Observable, throwError } from 'rxjs';
import { IDdr } from '../models/ddr.model';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class AvaService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca ddr con filtri e autocomplete
  ricericercaAvanzamento(filtri: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/avanzamento/ricercaAvanzamento?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna l'avanzamento richiesto
  getAvanzamento(id: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/avanzamento/getAvanzamento?idava=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica dell'avanzamento dal dettaglio avanzamento
  modificaAvanzamento(ava: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/avanzamento/modificaAvanzamento?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ava)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un nuovo avanzamento nel db
  inserisciAvanzamento(ava: any): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/avanzamento/inserisciAvanzamento?idUteRuo=${this.accountService.getCurIdUteRuo()}`, ava)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione dell'vanzamento dal dettaglio avanzamento
  cancellaAvanzamento(id: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/avanzamento/cancellaAvanzamento?idAva=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna l'elenco dei tipi fase
  getTipoFase(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/avanzamento/getTipoFase?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //eliminazione della fase
  cancellaFase(id: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/avanzamento/cancellaFase?idFase=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //crea le fasi dell'avanzamento
  creaFasiAvanzamento(id: number): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/avanzamento/creaFasiAvanzamento?idAva=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`, {})
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
