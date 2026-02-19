import { ItipFin } from './tip_fin.model';
import { Itotals, ITotalsDdr, ITotalsImp } from './totals.model';

export interface ItipiFinanziamento {
  tipiFinanziamento: ProTipFinResponse[];
  warningMessage?: string;
}

export interface ProTipFinResponse {
  tipoFinanziamento: ItipFin;
  totali: Itotals;
  hasChild: boolean;
  totaliImpegni: ITotalsImp;
  totaliDdr: ITotalsDdr;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
