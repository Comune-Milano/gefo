import { Component, OnInit, OnDestroy, ViewChildren, QueryList, Injector } from '@angular/core';
import { NgbdSortableHeaderDirective, SortEvent, compare } from 'app/shared/sort/sortable.directive';
import { delay, map, Observable, startWith, withLatestFrom, of, Subject, takeUntil } from 'rxjs';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SearchPage } from 'app/shared/baseclass/search-page';
import { ExportTableCsvService } from 'app/services/export-table-csv.service';
import { Iamm_dir } from 'app/models/amm_dir.model';
import { DirCentraleService } from 'app/services/dir-centrale.service';
import { combineLatestWith, catchError } from 'rxjs/operators';
import { RolesCheckService } from '../../core/roles-check/rolesCheck.service';

@Component({
  selector: 'jhi-amm-dir',
  templateUrl: './amm-dir.component.html',
  styleUrls: ['./amm-dir.component.scss'],
})
export class AmmDirComponent extends SearchPage implements OnInit, OnDestroy {
  _direzioniObj: Iamm_dir[] = [];
  direzioniObs$!: Observable<Iamm_dir[]>;
  direzioniFiltered$: Observable<Iamm_dir[]> | undefined;
  private ngUnsubscribe = new Subject<void>();

  /* @ts-ignore */
  @ViewChildren(NgbdSortableHeaderDirective) headers: QueryList<NgbdSortableHeaderDirective>;

  constructor(
    private formBuilder: FormBuilder,
    protected router: Router,
    protected dirService: DirCentraleService,
    protected exportTableCsvService: ExportTableCsvService,
    protected translateService: TranslateService,
    protected rolesCheckService: RolesCheckService,
    // private alertService: AlertService,
    protected injector: Injector
  ) {
    super(injector, '');
    this.direzioniObs$ = this.dirService.getDirezioni().pipe(
      takeUntil(this.ngUnsubscribe),
      catchError(err => {
        this.checkErrorStatus(err);
        this.showAlertError(err);
        return of([]);
      })
    );
    this.direzioniFiltered$ = this.direzioniObs$;
    this.formGroup = this.formBuilder.group({ filter: [''] });
  }

  ngOnInit(): void {
    super.initSearchFilterString();
    this.viewDelete = super.getOpDelete();
    if (this.viewDelete === true) {
      super.showAlertDeleteMessage();
    }
    this.direzioniFiltered$ = this.filter.valueChanges.pipe(
      startWith(this.filterString),
      takeUntil(this.ngUnsubscribe),
      combineLatestWith(this.direzioniObs$),
      map(([val, dir]) =>
        !val
          ? dir
          : dir.filter(
              (x: { coddir: string; desdir: string }) =>
                x.coddir.toLowerCase().includes(val.toLowerCase()) || x.desdir.toUpperCase().includes(val.toUpperCase())
            )
      )
    );
    this.direzioniFiltered$.forEach(element => {
      this._direzioniObj = element;
      this.onSort({ column: this.columnSort, direction: this.direction });
    });
  }

  nuovaDirezione(): void {
    // this.router.navigateByUrl('/amm-dir/new');
    this.router.navigate([`new`], { relativeTo: this.$activatedRoute });
  }

  apriDettaglio(id: number): void {
    // this.router.navigate([`amm-dir/${id}/viewDetail`]);
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

    this.direzioniFiltered$ = of(this._direzioniObj).pipe(
      takeUntil(this.ngUnsubscribe),
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
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  exportToCSV(): void {
    this.exportTableCsvService.exportTableToCsv(
      this.translateService.instant('ammDir.csvFileName'),
      this.convertDataCSV(this._direzioniObj)
    );
  }

  private convertDataCSV(queryResult?: Iamm_dir[]): any[] {
    const items: any[] = [];
    queryResult?.forEach(element => {
      const csvLine: any = {
        [this.translateService.instant('ammDir.coddir')]: element.coddir ? element.coddir : '',
        [this.translateService.instant('ammDir.desdir')]: element.desdir ? element.desdir : '',
      };
      items.push(csvLine);
    });
    return items;
  }
}
