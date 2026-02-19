import { Directive, Injectable, Injector, Input, OnDestroy } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ITEMS_PER_PAGE, TABLE_MAX_SIZE } from 'app/shared/constants/constants';
import { PaginationInstance } from 'ngx-pagination';

import { SortDirection } from 'app/shared/sort/sortable.directive';
import { ActivatedRoute, Router } from '@angular/router';
import { DecimalPipe } from '@angular/common';
import { CommonPage } from './common-page';

@Directive()
export class SearchPage extends CommonPage {
  [x: string]: any;
  filter = new FormControl('', { nonNullable: true });
  filterString = '';
  previousUrl = '';
  path = '';
  page = 1;
  pageSize: number = ITEMS_PER_PAGE;
  maxSize: number = TABLE_MAX_SIZE;
  isInitSearchFilterForm = false;
  public direction: SortDirection = '';
  public columnSort = '';
  public config: PaginationInstance = {
    // id: 'custom',
    itemsPerPage: this.pageSize,
    currentPage: 1,
  };
  formFilter!: FormGroup;
  protected $router: Router;
  protected $activatedRoute: ActivatedRoute;
  protected _decimalPipe: DecimalPipe;

  constructor(injector: Injector, defaultColumnSort: string) {
    super(injector);
    this.$router = injector.get(Router);
    this.$activatedRoute = injector.get(ActivatedRoute);
    this.previousUrl = this.$router.getCurrentNavigation()?.previousNavigation?.finalUrl?.toString() ?? ''; // Url precedente
    this.columnSort = defaultColumnSort; // Nome della colonna di ordinamento di default ('') per nessuna
    this._decimalPipe = injector.get(DecimalPipe);
  }

  initSearchFilterString(): void {
    let currentUrl = this.$activatedRoute.snapshot.parent?.routeConfig?.path ?? ''; // path url corrente
    currentUrl = '/' + currentUrl;

    if (this.previousUrl.includes(currentUrl)) {
      // verifico se l'url corrente è presente nella route precedente
      currentUrl = this.previousUrl.split(currentUrl)[0] + currentUrl;
      const searchFilterString = this.$sessionStorage.retrieve('searchFilterString_' + currentUrl); // Recupero i filtri
      if (searchFilterString) {
        this.filterString = searchFilterString;
      } else {
        this.filterString = '';
      }
    }
    this.filter.setValue(this.filterString);
  }

  initSearchFilterForm(): void {
    let currentUrl = this.$activatedRoute.snapshot.parent?.routeConfig?.path ?? ''; // path url corrente
    currentUrl = '/' + currentUrl;
    if (currentUrl.includes(':idTipFin')) {
      console.log(this.$activatedRoute.snapshot.parent);
      currentUrl = currentUrl.replace(':idTipFin', this.$activatedRoute.snapshot.parent.params.idTipFin);
    }
    console.log(this.previousUrl);
    if (this.previousUrl.includes(currentUrl)) {
      // verifico se l'url corrente è presente nella route precedente
      currentUrl = this.previousUrl.split(currentUrl)[0] + currentUrl;
      console.log(currentUrl);
      let searchFilterForm = this.$sessionStorage.retrieve('searchFilterForm_' + currentUrl); // Recupero i filtri
      if (searchFilterForm) {
        this.isInitSearchFilterForm = true;
        searchFilterForm = JSON.parse(searchFilterForm);
        // eslint-disable-next-line guard-for-in
        for (const filterKey in searchFilterForm) {
          Object.keys(this.formFilter['controls']).forEach(searchFormKey => {
            if (filterKey === searchFormKey) {
              this.formFilter.get(searchFormKey)?.setValue(searchFilterForm[filterKey]);
            }
          });
        }
      } else {
        this.isInitSearchFilterForm = false;
      }
    }
  }

  cacheFilterString(): void {
    const previousUrl = this.$router.getCurrentNavigation()?.previousNavigation?.finalUrl?.toString() ?? ''; // url precedente
    this.$sessionStorage.store('searchFilterString_' + previousUrl, this.filter.value); // memorizzo il filtro nel session storage
  }

  cacheFilterForm(): void {
    let previousUrl = this.$router.getCurrentNavigation()?.previousNavigation?.finalUrl?.toString() ?? ''; // url precedente
    previousUrl = previousUrl.split('?')[0];
    this.$sessionStorage.store('searchFilterForm_' + previousUrl, JSON.stringify(this.formFilter.value)); // memorizzo il filtro nel session storage
  }

  //  transformValueImporto(value: number): string {
  //	return this._decimalPipe.transform(value, '1.2-2') ?? ''; // Trasformo
  //  }
}
