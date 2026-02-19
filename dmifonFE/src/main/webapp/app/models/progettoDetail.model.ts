import { IProg } from './progetto.model';
import { IInfAgg } from './infagg.model';
import { Iamm_dir } from './amm_dir.model';

export interface IProgDetail {
  progetto: IProg;
  progettoPadre?: IProg;
  progettoDirezione?: Iamm_dir;
  proEntconSettore?: any;
  proEntconAssessorato?: any;
  risorse: IRisorse[];
  risorseDiCui: IRisorse[];
  risorseLivelloBasso?: IRisorseBasso[];
  infoAggiuntive: IInfAgg[];
  totaliFondi?: ItotRis;
}

export interface IRisorse {
  id: number;
  idPro: number;
  proTipimpByIdTipimp: IProTipimpByIdTipimp;
  idTipimp: number;
  imppro: number;
  notimp: string;
}

export interface IProTipimpByIdTipimp {
  id: number;
  destipimp: string;
}

export interface IRisorseBasso {
  desTipImp: string;
  idTipImp: number;
  ordTipImp: number;
  sumImpPro: number;
}

export interface ItotRis {
  impristotpro: number;
}
