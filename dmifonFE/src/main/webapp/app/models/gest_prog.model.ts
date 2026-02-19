import { IProg } from './progetto.model';
import { Itotals } from './totals.model';

export interface IGestioneProgetti {
  progetti: ProProgResponse[];
  warningMessage?: string;
}

export interface ProProgResponse {
  progetto: IProg;
  totali: Itotals;
}
