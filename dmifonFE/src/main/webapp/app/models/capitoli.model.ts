import { IProg } from './progetto.model';
import { IproDetcon } from './proDetcon.model';

export interface ICapitoli {
  progetto: IProg;
  progettoPadreLabel: string;
  capitoliUscita: ICapitoliResponse[];
  capitoliEntrata: ICapitoliResponse[];
}

export interface ICapitoliResponse {
  datiCapitolo: IDatiCapitolo;
  proDetcon: IproDetcon;
}

export interface IDatiCapitolo {
  id: string;
  assestato: string;
  assestato_1: string;
  assestato_2: string;
  descrizione: string;
  impegnato: string;
  impegnato_1: string;
  impegnato_2: string;
  macro: string;
  missione: string;
  previsione: string;
  previsione_1: string;
  previsione_2: string;
  programma: string;
  titolo: string;
}
