import { IproDetcon } from './proDetcon.model';
import { IproEntcon } from './proEntcon.model';
import { IProg } from './progetto.model';

export interface IDetCon {
  accertamenti: ProAccResponse[];
  impegni: ProAccResponse[];
  crono: ProAccResponse[];
  progetto: IProg;
  progettoPadreLabel?: string;
}

export interface ProAccResponse {
  proDetcon: IproDetcon;
  proEntcon: IproEntcon;
}
