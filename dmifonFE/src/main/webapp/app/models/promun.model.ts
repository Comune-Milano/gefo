import { Itotals } from './totals.model';

export interface IProMun {
  id: number;
  desmun: string;
}

export interface IMunicipi {
  proMun: Array<MunicipioResponse>;
  warningMessage?: string;
}

export interface MunicipioResponse {
  proMun: IProMun;
  totali: Itotals;
}
