import { Injectable, PipeTransform } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IUsers } from 'app/models/users.model';
import { BehaviorSubject, catchError, Observable, of, Subject, throwError } from 'rxjs';
import { AccountService } from '../core/auth/account.service';
// import { DecimalPipe } from '@angular/common';
// import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
// import { SortDirection } from 'app/shared/sort/sortable.directive';
// import { IStateTable } from 'app/models/state-table.model';

/* interface SearchResult {
  users: IUsers[];
  total: number;
}

function compare(v1: number, v2: number): number {
  return v1 < v2 ? -1 : v1 > v2 ? 1 : 0;
}

function sort(users: IUsers[], column: string, direction: string): IUsers[] {
  if (direction === '') {
    return users;
  } else {
    return [...users].sort((a, b) => {
      const res = compare(a[column], b[column]);
      return direction === 'asc' ? res : -res;
    });
  }
}

function matches(user: IUsers, term: string, pipe: PipeTransform): boolean {
  return (
    user.nome.toLowerCase().includes(term) ||
    user.cognome.toLowerCase().includes(term) ||
    user.email.toLowerCase().includes(term) ||
    user.emailalt.toLowerCase().includes(term) ||
    user.username.toLowerCase().includes(term)
  );
} */

@Injectable({ providedIn: 'root' })
export class UsersService {
  // private _users$ = new BehaviorSubject<IUsers[]>([]);
  /* private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _users$ = new BehaviorSubject<IUsers[]>([]);
  private _total$ = new BehaviorSubject<number>(0);
  private _usersAll$: IUsers[] = [];

  private _state: IStateTable = {
    page: 1,
    pageSize: 2,
    searchTerm: '',
    sortColumn: '',
    sortDirection: '',
  }; */

  constructor(private http: HttpClient, private accountService: AccountService) {}

  /* constructor(private http: HttpClient, private pipe: DecimalPipe) {
    this._search$
      .pipe(
        tap(() => this._loading$.next(true)),
        debounceTime(500),
        switchMap(() => this._search()),
        tap(() => this._loading$.next(false))
      )
      .subscribe(result => {
        this._users$.next(result.users);
        this._total$.next(result.total);
      });

    this._search$.next();
  } */

  /* get users$(): Observable<IUsers[]> {
    return this._users$.asObservable();
  } */

  /*
  get total$(): Observable<number> {
    return this._total$.asObservable();
  }

  get loading$(): Observable<boolean> {
    return this._loading$.asObservable();
  }

  get page(): number {
    return this._state.page;
  }

  get pageSize(): number {
    return this._state.pageSize;
  }

  get searchTerm(): string {
    return this._state.searchTerm;
  }

  set page(page: number) {
    this._set({ page });
  }

  set pageSize(pageSize: number) {
    this._set({ pageSize });
  }

  set searchTerm(searchTerm: string) {
    this._set({ searchTerm });
  }

  set sortColumn(sortColumn: string) {
    this._set({ sortColumn });
  }

  set sortDirection(sortDirection: SortDirection) {
    this._set({ sortDirection });
  } */

  getUtenti(): Observable<any> {
    return this.http.get(`services/dmifonbe/api/utente/ricercaUtente?autocomplete=&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  getUtentiAutocomplete(autocomplete: string): Observable<any> {
    return this.http
      .get(`services/dmifonbe/api/utente/ricercaUtente?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  autoCompleteRuolo(autocomplete: string) {
    return this.http
      .get(`services/dmifonbe/api/ruolo/ricercaRuolo?autocomplete=${autocomplete}&idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  autocompleteDirezione(autocomplete: string) {
    return this.http
      .get(`services/dmifonbe/api/direzione/ricercaDirezione?autocomplete=${autocomplete}?idUteRuo=${this.accountService.getCurIdUteRuo()}`)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  getUtente(id: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/utente/getUtente?idUtente=${id}&idUteRuo=${this.accountService.getCurIdUteRuo()}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  abilitazioneUtente(id: string, abilitato: boolean) {
    return this.http
      .post(
        `services/dmifonbe/api/utente/abilitazioneUtente?idUtente=${id}&abilitato=${abilitato}&idUteRuo=${this.accountService.getCurIdUteRuo()}`,
        {}
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  modificaUtente(utente: IUsers): Observable<any> {
    return this.http.post<any>(`services/dmifonbe/api/utente/modificaUtente?idUteRuo=${this.accountService.getCurIdUteRuo()}`, utente).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  inserisciUtente(utente: IUsers): Observable<any> {
    return this.http
      .post<any>(`services/dmifonbe/api/utente/inserisciUtente?idUteRuo=${this.accountService.getCurIdUteRuo()}`, utente)
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  cancellaRuoloDirezione(idUtente: number, idAmmUteruo: number): Observable<any> {
    return this.http
      .post<any>(
        `services/dmifonbe/api/utente/cancellaRuoloDirezione?idUtente=${idUtente}&idAmmUteruo=${idAmmUteruo}&idUteRuo=${this.accountService.getCurIdUteRuo()}`,
        []
      )
      .pipe(
        catchError(err => {
          return throwError(err);
        })
      );
  }

  ricercaUtenteRuolo(user: string): Observable<any> {
    return this.http.get(`services/dmifonbe/api/utente/ricercaUtenteRuolo?username=${user}`).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  /* private _set(patch: Partial<IStateTable>) {
    Object.assign(this._state, patch);
    this._search$.next();
  }

  private _search(): Observable<SearchResult> {
    const { sortColumn, sortDirection, pageSize, page, searchTerm } = this._state;

    // 1. sort
    this.http.get<IUsers[]>('services/dmifonbe/api/utente/ricercaUtente').subscribe(data => {
      this._usersAll$ = data;
    });
    let users = sort(this._usersAll$, sortColumn, sortDirection);

    // 2. filter
    users = users.filter(country => matches(country, searchTerm, this.pipe));
    const total = users.length;

    // 3. paginate
    users = users.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({ users, total });
  } */

  /*constructor(private http: HttpClient) {} */
}
