import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';
import { IProMun } from '../models/promun.model';

@Injectable({ providedIn: 'root' })
export class ProMunService {
  constructor(private http: HttpClient, private accountService: AccountService) {}

  //ritorna tutti i municipi
  getAllMunicipi() {
    return this.http.get(`services/dmifonbe/api/Municipio/getAllMunicipi?idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ricerca dei municipi con autocomplete e calcolaTotali
  ricercaMunicipio(filtri: any) {
    return this.http.post(`services/dmifonbe/api/Municipio/ricercaMunicipio?idUteRuo=${this.accountService.getCurIdUteRuo()}`, filtri).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  //ricerca dei municipi con autocomplete e calcolaTotali
  ricercaMunicipioAutocomplete(autocomplete: string) {
    return this.http
      .get(
        `services/dmifonbe/api/Municipio/ricercaMunicipioAutocomplete?idUteRuo=${this.accountService.getCurIdUteRuo()}&autocomplete=${autocomplete}`
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //metodo che restituisce un municipio dato l'id
  getMunicipio(id: string): Observable<IProMun> {
    return this.http
      .get<IProMun>(`services/dmifonbe/api/Municipio/getMunicipio?idMun=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //inserimento di un municipio
  inserisciMunicipio(municipio: IProMun): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/Municipio/inserisciMunicipio?idUteRuo=${this.accountService.getCurIdUteRuo()}`, municipio)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //cancellazione di un municipio
  cancellaMunicipio(id: string): Observable<any> {
    return this.http
      .delete(`services/dmifonbe/api/Municipio/cancellaMunicipio?idProMun=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  //modifica di un municipio
  modificaMunicipio(municipio: IProMun): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/Municipio/modificaMunicipio?idUteRuo=${this.accountService.getCurIdUteRuo()}`, municipio)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
