import { Directive, Injectable, Injector, Input, OnDestroy } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { FormControl, FormGroup } from '@angular/forms';
import { ITEMS_PER_PAGE, TABLE_MAX_SIZE } from 'app/shared/constants/constants';
import { PaginationInstance } from 'ngx-pagination';
import { SessionStorageService } from 'ngx-webstorage';
import { SortDirection } from 'app/shared/sort/sortable.directive';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'app/shared/alert/alert.service';
import { ErrorService } from 'app/services/error.service';
import { CommonPage } from './common-page';

@Directive()
export class UpdatePage extends CommonPage {
  id: string = '';
  pagePath: string = '';
  modifica = false;
  stringSalva!: string;
  pageSize: number = ITEMS_PER_PAGE;
  public config: PaginationInstance = {
    // id: 'custom',
    itemsPerPage: this.pageSize,
    currentPage: 1,
  };

  public config1: PaginationInstance = {
    // id: 'custom',
    itemsPerPage: this.pageSize,
    currentPage: 1,
  };

  constructor(injector: Injector) {
    super(injector);
  }
}
