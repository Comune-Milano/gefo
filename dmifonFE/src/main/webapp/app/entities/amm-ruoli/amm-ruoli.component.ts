import { Component, OnInit, OnDestroy, ViewChildren, QueryList, Injector } from '@angular/core';
import { NgbdSortableHeaderDirective, SortEvent, compare } from 'app/shared/sort/sortable.directive';
import { delay, map, Observable, startWith, withLatestFrom, of, Subject, takeUntil } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { Iamm_ruo } from 'app/models/amm_ruo.model';
import { ProfilesService } from 'app/services/profiles.service';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { combineLatestWith, catchError } from 'rxjs/operators';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-amm-ruoli',
  templateUrl: './amm-ruoli.component.html',
  styleUrls: ['./amm-ruoli.component.scss'],
})
export class AmmRuoliComponent extends SearchPage implements OnInit, OnDestroy {
  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;
  _rolesObj: Iamm_ruo[] = [];
  profiliObs$!: Observable<Iamm_ruo[]>;
  profiliFiltered$: Observable<Iamm_ruo[]> | undefined;
  private ngUnsubscribe = new Subject<void>();

  constructor(
    private formBuilder: FormBuilder,
    protected router: Router,
    protected profiliService: ProfilesService,
    protected exportTableCsvService: ExportTableCsvService,
    protected rolesCheckService: RolesCheckService,
    protected translateService: TranslateService,
    // private alertService: AlertService,
    protected injector: Injector
  ) {
    super(injector, '');
    this.profiliObs$ = this.profiliService.getRuoli().pipe(
      takeUntil(this.ngUnsubscribe),
      catchError(err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
        return of([]);
      })
    );
    this.profiliFiltered$ = this.profiliObs$;
    this.formGroup = this.formBuilder.group({ filter: [''] });
  }

  ngOnInit(): void {
    super.initSearchFilterString();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.profiliFiltered$ = this.filter.valueChanges.pipe(
      takeUntil(this.ngUnsubscribe),
      startWith(this.filterString),
      combineLatestWith(this.profiliObs$),
      map(([val, user]) =>
        !val
          ? user
          : user.filter(
              (x: { codruo: string; desruo: string }) =>
                x.codruo.toLowerCase().includes(val.toLowerCase()) || x.desruo.toLowerCase().includes(val.toLowerCase())
            )
      )
    );
    this.profiliFiltered$.forEach(element => {
      this._rolesObj = element;
      this.onSort({ column: this.columnSort, direction: this.direction });
    });
  }

  nuovoRuolo(): void {
    // this.router.navigateByUrl('/amm-ruoli/new');
    this.router.navigate([`new`], { relativeTo: this.$activatedRoute });
  }

  apriDettaglio(id: number): void {
    // this.router.navigate([`amm-ruoli/${id}/viewDetail`]);
    this.router.navigate([`${id}/viewDetail`], { relativeTo: this.$activatedRoute });
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

    this.profiliFiltered$ = of(this._rolesObj).pipe(
      takeUntil(this.ngUnsubscribe),
      map(data => {
        data.sort((a, b) => {
          // return a.value < b.value ? -1 : 1;
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
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(this.translateService.instant('ammRuoli.csvFileName'), this.convertDataCSV(this._rolesObj));
  }

  private convertDataCSV(queryResult?: Iamm_ruo[]): any[] {
    const items: any[] = [];
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('ammRuoli.codruo')]: element.codruo ? element.codruo : '',
        [this.translateService.instant('ammRuoli.desruo')]: element.desruo ? element.desruo : '',
      };
      items.push(csvLine);
    });
    return items;
  }
}
