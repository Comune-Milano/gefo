import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class ListaValoriService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna la lista valori
  ricercaListaValori(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/lisval/ricercaListaValore?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  getListaValori(idLisVal: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/lisval/getListaValore?idLisVal=${idLisVal}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getTipiListaValori(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/lisval/getListaValoriTipoLista?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  modificaListaValore(lisVal: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/lisval/modificaListaValore?idUteRuo=${this.accountService.getCurIdUteRuo()}`, lisVal).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  inserisciListaValori(lisVal: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/lisval/inserisciListaValore?idUteRuo=${this.accountService.getCurIdUteRuo()}`, lisVal)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  cancellaListaValori(id: string): Observable<any> {
    return this.http
      .delete(`services/dmifonbe/api/lisval/cancellaListaValore?idLisVal=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna la lista valori fase intervento
  getListaValoriFaseIntervento(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/lisval/getListaValoriFaseIntervento?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ritorna la lista valori livello criticità
  getListaValoriLivelloCriticita(): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/lisval/getListaValoriLivelloCriticita?idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna la lista valori tipo appalto
  getListaValoriTipoAppalto(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/lisval/getListaValoriTipoAppalto?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ritorna la lista valori stato anticipazione
  getListaValoriStatoAnticipazione(): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/lisval/getListaValoriStatoAnticipazione?idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna la lista valori tipologia fasi
  getListaValoriTipologiaFasi(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/lisval/getListaValoriTipologiaFasi?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
