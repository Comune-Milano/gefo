import { IDdr } from './ddr.model';

export interface IDdrResponse {
  ddr: ProDdrResponse[];
  warningMessage?: string;
}

export interface ProDdrResponse {
  ddr: IDdr;  
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
