import { ITematica } from './tematica.model';
import { Itotals } from './totals.model';

export interface ITematiche {
  tematiche: ProTematicheResponse[];
  warningMessage?: string;
}

export interface ProTematicheResponse {
  tematica: ITematica;
  totali: Itotals;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
