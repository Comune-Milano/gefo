import { ItipFin } from './tip_fin.model';

export interface IMacroProg {
  id: number;
  codmacpro: string;
  desmacpro: string;
  id_tipfinda: number;
  id_tipfina: number;
  proTipfinByIdTipfinda?: ItipFin;
  proTipfinByIdTipfina?: ItipFin;
  proProsById: any; //todo: da mappare
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
