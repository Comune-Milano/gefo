import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, Subject, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class ExportImpegniService {
  private isExport = false;
  private countExport: number;
  constructor(private http: HttpClient, private accountService: AccountService) {
    this.isExport = false;
    this.countExport = 0;
  }

  setIsExport(value: boolean): void {
    this.isExport = value;
  }

  getIsExport(): boolean {
    return this.isExport;
  }

  incrementCountExport(): void {
    this.countExport++;
  }

  decrementCountExport(): void {
    this.countExport--;
  }

  getCountExport(): number {
    return this.countExport;
  }

  //ricerca ddr con filtri e autocomplete
  exportImpegni(param: any): Observable<any> {
    return this.http
      .post(`services/dmifonbe/api/exportImpegni/exportImpegni?idUteRuo=${this.accountService.getCurIdUteRuo()}`, param, {
        headers: { skip: 'true' },
      })
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }
}
