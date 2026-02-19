import { IDdra } from './ddra.model';
import { IProg } from './progetto.model';

export interface IDdr {
  id: number;
  codddr: string;
  desddr: string;
  idPro: number;
  dtaddr: Date;
  idDdra?: number;
  impddr: number;
  impamm: number;
  imptra: number;
  impinc: number;
  prgrev?: string;
  notddr?: string;
  proDdraByIdDdra?: IDdra;
  proProByIdPro: IProg;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
