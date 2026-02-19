import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IMacroProg } from 'app/models/macro_prog.model';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class DizMacroProgService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ricerca macroprogetti con autocomplete
  ricercaMacroProgetto(filtri: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/macpro/ricercaMacroProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ricerca macro progetti autocomplete senza totali
  ricercaMacroProgettiAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(
        `services/dmifonbe/api/macpro/ricercaMacroProgettiAutocomplete?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna il macro progetto richiesto
  getMacroProgetto(id: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/macpro/getMacroProgetto?idmacpro=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un nuovo macro progetto nel db
  inserisciMacroProgetto(macroProg: IMacroProg) {
    return this.http
      .post<any>(`services/dmifonbe/api/macpro/inserisciMacroProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, macroProg)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica del macro progetto dal dettaglio macro progetto
  modificaMacroProgetto(macroProg: IMacroProg) {
    return this.http
      .post<any>(`services/dmifonbe/api/macpro/modificaMacroProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, macroProg)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //eliminazione di un macro progetto dal db
  cancellaMacroProgetto(id: number) {
    return this.http
      .delete<any>(`services/dmifonbe/api/macpro/cancellaMacroProgetto?idmacpro=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
