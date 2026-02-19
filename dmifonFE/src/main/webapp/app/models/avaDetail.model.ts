import { IAvanzamento } from './avanzamento.model';
import { IProFas, IProTipfas } from './proFas.model';
import { ISemaforo } from './semaforo.model';

export interface IAvanzamentoDetail {
  avanzamento: IAvanzamento;
  progettoPadreLabel: string;
  fasi: IFasiDetail[];
  datinilav: any;
  datfinlav: any;
}

export interface IFasiDetail {
  fase: IProFas;
  tipfas: IProTipfas;
  semaforoFase: ISemaforo;
  semaforoMilestone: ISemaforo;
}
