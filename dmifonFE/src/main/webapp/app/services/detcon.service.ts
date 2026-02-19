import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IMacroProg } from 'app/models/macro_prog.model';
import { catchError, Observable, throwError } from 'rxjs';
import { IDetCon } from '../models/detcon.model';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class DetConService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //metodo che ritorna tutti gli accertamenti / impegni / crono del progetto corrente
  getImpegniProgetto(id: any): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/detcon/getImpegniProgetto?idpro=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getAccertamento(codentcon: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/detcon/getAccertamento?codentcon=${codentcon}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getImpegno(codentcon: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/detcon/getImpegno?codentcon=${codentcon}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getCrono(codentcon: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/detcon/getCrono?codentcon=${codentcon}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica degli imegni / accertamenti / crono
  modificaImpegniProgetto(AccImpCro: IDetCon): any {
    return this.http
      .post<any>(`services/dmifonbe/api/detcon/modificaImpegniProgetto?idUteRuo=${this.accountService.getCurIdUteRuo()}`, AccImpCro)
      .pipe(
        catchError(err => {
          console.error(err.error.userMessage);
          return throwError(err);
        })
      );
  }

  cancellaDettaglioContabile(idDetCon: number): any {
    return this.http
      .delete(
        `services/dmifonbe/api/detcon/cancellaDettaglioContabile?idDetCon=${idDetCon}&idUteRuo=${this.accountService.getCurIdUteRuo()}`
      )
      .pipe(
        catchError(err => {
          console.error(err.error.userMessage);
          return throwError(err);
        })
      );
  }

  //ricerche dati contabili

  //ricerca degli accertamenti con filtri
  ricercaAccertamenti(filtri?: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/detcon/ricercaAccertamenti?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        console.error(err.error.userMessage);
        return throwError(err);
      })
    );
  }

  //ricerca degli impegni con filtri
  ricercaImpegni(filtri?: any): Observable<any> {
    return this.http.post(`services/dmifonbe/api/detcon/ricercaImpegni?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        console.error(err.error.userMessage);
        return throwError(err);
      })
    );
  }

  //ricerca degli accertamenti del progetto
  ricercaAccertamentiProgetto(id: number): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/detcon/ricercaAccertamentiProgetto?id=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          console.error(err.error.userMessage);
          return throwError(err);
        })
      );
  }

  //ricerca degli accertamenti del progetto
  ricercaImpegniProgetto(id: number): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/detcon/ricercaImpegniProgetto?id=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          console.error(err.error.userMessage);
          return throwError(err);
        })
      );
  }

  getTipoImportoRisorsa(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/detcon/getTipoImportoRisorsa?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }
}
