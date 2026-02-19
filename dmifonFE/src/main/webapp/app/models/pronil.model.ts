import { Itotals } from './totals.model';
import { IProMun } from './promun.model';

export interface IProNil {
  id: number;
  codnil: string;
  desnil: string;
  idMun: number;
  proMunByIdMun: IProMun;
}

export interface INil {
  nil: Array<NilResponse>;
  warningMessage?: string;
}

export interface NilResponse {
  proNil: IProNil;
  totali: Itotals;
}
