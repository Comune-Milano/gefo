// import { Component, OnInit } from '@angular/core';
import { Component, Injector, OnInit, OnDestroy, QueryList, ViewChildren, HostListener } from '@angular/core';
import { UsersService } from 'app/services/users.service';
import { IUsers } from 'app/models/users.model';
import { NgbdSortableHeaderDirective, SortEvent, compare } from 'app/shared/sort/sortable.directive';
import { delay, map, Observable, startWith, withLatestFrom, of } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { combineLatestWith, catchError } from 'rxjs/operators';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-amm-utenti',
  templateUrl: './amm-utenti.component.html',
  styleUrls: ['./amm-utenti.component.scss'],
})
export class AmmUtentiComponent extends SearchPage implements OnInit, OnDestroy {
  usersObs$!: Observable<IUsers[]>;
  _usersObj: IUsers[] = [];
  usersFiltered$: Observable<IUsers[]> | undefined;
  innerWidth: any;

  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;

  constructor(
    private formBuilder: FormBuilder,
    protected router: Router,
    protected userService: UsersService,
    protected exportTableCsvService: ExportTableCsvService,
    protected translateService: TranslateService,

    protected rolesCheckService: RolesCheckService,
    protected injector: Injector
  ) {
    super(injector, '');
    this.path = '/amm-utenti';
    this.usersObs$ = this.userService.getUtenti().pipe(
      catchError(err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
        return of([]);
      })
    );
    this.formFilter = this.formBuilder.group({ filter: [''] });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): any {
    this.innerWidth = window.innerWidth;
  }

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    super.initSearchFilterString();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.usersFiltered$ = this.filter.valueChanges.pipe(
      startWith(this.filterString),
      // delay(300),
      // withLatestFrom(this.usersObs$),
      combineLatestWith(this.usersObs$),
      map(([val, user]) =>
        !val
          ? user
          : user.filter(
              (x: { cognome: string; nome: string; username: string }) =>
                x.cognome.toLowerCase().includes(val.toLowerCase()) ||
                x.nome.toLowerCase().includes(val.toLowerCase()) ||
                x.username.toLowerCase().includes(val.toLowerCase())
            )
      )
    );
    this.usersFiltered$.forEach(element => {
      this._usersObj = element;
      this.onSort({ column: this.columnSort, direction: this.direction });
    });
  }

  nuovoUtente(): void {
    // this.router.navigate(['amm-utenti/new']);
    this.router.navigateByUrl('/amm-utenti/new');
  }

  apriDettaglio(id: number): void {
    // this.router.navigate([`amm-utenti/${id}/viewDetail`]);
    const route = id + '/viewDetail';
    // this.router.navigate(['${id}/viewDetail'], {relativeTo: this.$activatedRoute});
    this.router.navigate([route], { relativeTo: this.$activatedRoute });
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(
      this.translateService.instant('ammUtenti.csvFileName'),
      this.convertDataCSV(this._usersObj)
    );
  }

  onSort({ column, direction }: SortEvent): void {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    // sorting
    if (direction === '' || column === '') {
      column = 'id';
      direction = 'asc';
    }

    this.usersFiltered$ = of(this._usersObj).pipe(
      map(data => {
        data.sort((a, b) => {
          const res = compare(a[column], b[column]);
          return this.direction === 'asc' ? res : -res;
        });
        return data;
      })
    );

    this.direction = direction;
    this.columnSort = column;
  }

  ngOnDestroy(): void {
    super.cacheFilterString(); // Cache dei filtri nello storage
  }

  private convertDataCSV(queryResult?: IUsers[]): any[] {
    const items: any[] = [];
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('ammUtenti.username')]: element.username ? element.username : '',
        [this.translateService.instant('ammUtenti.nome')]: element.nome ? element.nome : '',
        [this.translateService.instant('ammUtenti.cognome')]: element.cognome ? element.cognome : '',
        [this.translateService.instant('ammUtenti.email')]: element.email ? element.email : '',
        [this.translateService.instant('ammUtenti.emailAlt')]: element.emailalt ? element.emailalt : '',
        [this.translateService.instant('ammUtenti.abilitato')]: element.abilitato ? '[V]' : '[]',
      };
      items.push(csvLine);
    });
    return items;
  }
}
