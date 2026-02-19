import { IProTipfas } from './proFas.model';
import { IProLisval } from './proLisval.model';

export interface IProDettipolfas {
  id: number;
  idLisvaltipolfas: number;
  proLisvalByIdLisvaltipolfas: IProLisval;
  proTipfasByIdTipfas: IProTipfas;
}
