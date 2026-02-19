import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { ItipFin } from '../models/tip_fin.model';
import { IProg } from '../models/progetto.model';
import { IProgDetail } from '../models/progettoDetail.model';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class ProgService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca progetti con filtri e autocomplete
  ricercaProgetto(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/progetto/ricercaProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  ricercaProgettiAutocomplete(filtri: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/progetto/ricercaProgettiAutocomplete?autocomplete=${filtri}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna il progetto richiesto
  getProgetto(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/progetto/getProgetto?idpro=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //modifica del progetto dal dettaglio progetto
  modificaProgetto(prog: IProgDetail): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/progetto/modificaProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, prog)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un nuovo progetto nel db
  inserisciProgetto(prog: IProg): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/progetto/inserisciProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, prog)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un progetto figlio
  inserisciProgettoFiglio(idPro: number): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/progetto/inserisciProgettoFiglio?idpro=${idPro}&idUteRuo=${this.accountService.getCurIdUteRuo()}`, {})
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un progetto figlio
  modificaImportoRisorseFiglio(idPro: number): Observable<any> {
    return this.http
      .post(
        `services/dmifonbe/api/progetto/modificaImportoRisorseFiglio?idpro=${idPro}&idUteRuo=${this.accountService.getCurIdUteRuo()}`,
        {}
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione del progetto dal dettaglio progetto
  cancellaProgetto(idPro: number): Observable<any> {
    return this.http
      .delete<any>(`services/dmifonbe/api/progetto/cancellaProgetto?idpro=${idPro}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //metodo che ritorna la lista degli assessorati
  getAllAssessorati(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/progetto/getAllAssessorati?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //metodo che ritorna la lista di tutti i settori
  getAllSettori(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/progetto/getAllSettori?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
