import { Itotals } from './totals.model';
import { IBan } from './bando.model';

export interface IBandi {
  bandi: ProBanResponse[];
  warningMessage?: string;
}

export interface ProBanResponse {
  bando: IBan;
  totali: Itotals;
}
