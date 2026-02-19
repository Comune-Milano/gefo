import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class AllegatoService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna gli allegati
  getAllegato(idEnt: number, tipEnt: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/allegato/getAllegato?idUteRuo=${this.accountService.getCurIdUteRuo()}&tipEnt=${tipEnt}&idEnt=${idEnt}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //ritorna gli allegati
  scaricaAllegato(idFile: number): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/allegato/scaricaAllegato?idUteRuo=${this.accountService.getCurIdUteRuo()}&idFile=${idFile}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //invio di un allegato al be
  caricaFileAllegato(allegato: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/allegato/caricaFileAllegato?idUteRuo=${this.accountService.getCurIdUteRuo()}`, allegato)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica di un allegato
  modificaAllegato(allegatoDaModificare: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/allegato/modificaAllegato?idUteRuo=${this.accountService.getCurIdUteRuo()}`, allegatoDaModificare)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //cancellazione di un allegato
  cancellaAllegato(idFile: number): Observable<any> {
    return this.http
      .delete(`services/dmifonbe/api/allegato/cancellaAllegato?idUteRuo=${this.accountService.getCurIdUteRuo()}&idAll=${idFile}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
