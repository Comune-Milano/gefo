import { IMacroProg } from './macro_prog.model';
import { Itotals } from './totals.model';

export interface IMacroProgetti {
  macroProgetti: ProMacroProgResponse[];
  warningMessage?: string;
}

export interface ProMacroProgResponse {
  macroProgetto: IMacroProg;
  totali: Itotals;
}
