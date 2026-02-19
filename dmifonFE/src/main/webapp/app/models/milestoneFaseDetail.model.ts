import { IProFas, IProTipfas } from './proFas.model';
import { IProg } from './progetto.model';
import { IMilestoneDetail } from './milestone.model';

export interface IMilestoneFaseDetail {
  fase: IProFas;
  proTipfas: IProTipfas;
  progetto: IProg;
  progettoPadreLabel: string;
  milestone: IMilestoneDetail[];
}
