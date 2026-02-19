import { IProFas } from './proFas.model';
import { IProg } from './progetto.model';
import { IProLisval } from './proLisval.model';
import { ISemaforo } from './semaforo.model';

export interface IAvanzamento {
  id: number;
  idPro: number;
  nrover: number;
  dtarilava: any;
  desstaava: string;
  prcava: number;
  idLisvalfasint: number;
  idlisvaltipolfas?: number;
  idLisvallivcri: number;
  notcri: string;
  idLisvaltipapp: number;
  desapp: string;
  idLisvalstaant: number;
  impant: number;
  proProByIdPro?: IProg;
  proLisvalByIdLisvalfasint?: IProLisval;
  proLisvalByIdLisvallivcri?: IProLisval;
  proLisvalByIdLisvaltipapp?: IProLisval;
  proLisvalByIdLisvalstaant?: IProLisval;
  proFasById?: IProFas[];
  usrLstupd?: string;
}

export interface IAvanzamentoResponse {
  avanzamenti: IAvaResponse[];
  warningMessage?: string;
}

export interface IAvaResponse {
  avanzamento: IAvanzamento;
  faseIntervento: IProLisval;
  livelloCriticita: IProLisval;
  semaforoAvanzamento: ISemaforo;
  codDesProgetto: string;
}
