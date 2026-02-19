import { ISemaforo } from './semaforo.model';
import { IProFas } from './proFas.model';

export interface IMilestone {
  id: number;
  idFas: number;
  desmil: string;
  dtaded: any;
  dtaeff: any;
  notmil: string;
  proFasByIdFas: IProFas;
}
export interface IMilestoneDetail {
  milestone: IMilestone;
  semaforoMilestone: ISemaforo;
}
