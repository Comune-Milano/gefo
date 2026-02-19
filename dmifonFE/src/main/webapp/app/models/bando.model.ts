import { ItipFin } from './tip_fin.model';
import { IProtem } from './protem.model';
import { IStaBan } from './staban.model';

export interface IBan {
  id: number;
  codban: string;
  desban: string;
  desben: string;
  desdecfin: string;
  desent: string;
  desprvent: string;
  refmin: string;
  idTem: number;
  idTipfin: number;
  idStaban?: number;
  impban: number;
  impinv: number;
  impmaspar: number;
  impmaspro: number;
  dtapubban?: Date;
  dtachiatt?: Date;
  dtascaban?: Date;
  proTipfinByIdTipfin: ItipFin;
  proTemByIdTem: IProtem;
  proStabanByIdStaban: IStaBan;
}
