import { IProg } from './progetto.model';

export interface IPrevisioni {
  id: number;
  idPro: number;
  dtapre: Date;
  imppre: number;
  notpre: string;
  proProByIdPro: IProg;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
