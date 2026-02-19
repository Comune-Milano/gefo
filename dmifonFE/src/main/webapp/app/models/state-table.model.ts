import {SortDirection} from 'app/shared/sort/sortable.directive';

export interface IStateTable {
  page: number;
  pageSize: number;
  searchTerm: string;
  sortColumn: string;
  sortDirection: SortDirection;
}