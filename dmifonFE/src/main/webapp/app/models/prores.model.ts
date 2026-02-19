import { IProg } from './progetto.model';
import { IUsers } from './users.model';

export interface IProRes {
  progetto: IProg;
  progettoPadreLabel: string;
  responsabili: ProResResponse[];
}

export interface ProResResponse {
  responsabile: IResponsabile;
  utente?: IUsers;
}

export interface IResponsabile {
  id: number;
  idPro: number;
  idTipres: number;
  idUte: number;
  notres: string;
  proTipresByIdTipres?: ProTipresByIdTipres;
  proProByIdPro?: IProg;
}

export interface ProTipresByIdTipres {
  id: number;
  ordtipres: number;
  destipres: string;
}
