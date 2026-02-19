import { IDdr } from './ddr.model';

export interface IDdra {
  id: number;
  codddra: string;
  desddra: string;
  idPro?: number;
  dtaddra: Date;
  notddra?: string;
  proDdrsById?: IDdr[];
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
