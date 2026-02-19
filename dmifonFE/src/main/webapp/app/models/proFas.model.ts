import { IProDettipolfas } from './proDettipolfas.model';
import { IProMil } from './proMil.model';
import { ILisVal } from './lisVal.model';

export interface IProFas {
  id: number;
  idAva: number;
  idTipfas: number;
  dtainiori: any;
  dtafinori: any;
  dtainipre: any;
  dtafinpre: any;
  dtainieff: any;
  dtafineff: any;
  notfas: string;
  //proAvaByIdAva: any,
  proTipfasByIdTipfas: any;
  proMilsById: IProMil[];
}

export interface IProTipfas {
  id: number;
  desfas: string;
  tipcon: string;
  ordfas: number;
  tipfas: string;
  proFasById: IProFas[];
  proDettipolfasById: IProDettipolfas[];
}

export interface tipFasResponse {
  proTipfas: IProTipfas[];
}
