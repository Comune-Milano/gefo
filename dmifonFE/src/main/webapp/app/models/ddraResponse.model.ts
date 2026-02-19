import { IDdr } from './ddr.model';
import { IDdra } from './ddra.model';

export interface IDdraResponse {
  ddra: ProDdraResponse[];
  warningMessage?: string;
}

export interface ProDdraResponse {
  ddra: IDdra;
  importoDdra: number;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
