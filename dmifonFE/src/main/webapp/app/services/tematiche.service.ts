import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ITematica } from 'app/models/tematica.model';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class TematicheService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  // Ricerca le tematiche presenti nel database
  ricercaTematica(filtri: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/protem/ricercaTematica?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  // Ritorna la tematica richiesta
  getTematica(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/protem/getTematica?idprotem=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  // Modifica di una tematica
  modificaTematica(tematica: ITematica) {
    return this.http
      .post<any>(`services/dmifonbe/api/protem/modificaTematica?idUteRuo=${this.accountService.getCurIdUteRuo()}`, tematica)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  // Inserimento di una tematica
  inserisciTematica(tematica: ITematica) {
    return this.http
      .post<any>(`services/dmifonbe/api/protem/inserisciTematica?idUteRuo=${this.accountService.getCurIdUteRuo()}`, tematica)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  // Eliminazione di una tematica
  cancellaTematica(id: number) {
    return this.http
      .delete<any>(`services/dmifonbe/api/protem/cancellaTematica?idprotem=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  // Ricerca tematica autocomplete
  ricercaTematicaAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/protem/ricercaTematicaAutocomplete?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
