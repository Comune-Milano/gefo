export interface IVisTotali {
  nroprofon: number;
  imprisfon: number;
  impacc: number;
  impecoacc: number;
  imprev: number;
  impimp: number;
  impecoimp: number;
  impliq: number;
  impigv: number;
  impigvdel: number;
  impman: number;
  impdaacc: number;
  impdaimp: number;
  impddr: number;
  impamm: number;
  imptra: number;
  impinc: number;
  impprvese: number;
  impprvese1: number;
  impprvsuc: number;
  risorse: IRisorse[];
  risorseDiCui: IRisorse[];
  desTipEnt: string;
  desEnt: string;
}

export interface IRisorse {
  idTipImp: number;
  desTipImp: string;
  ordTipImp: number;
  sumImpPro: number;
  sumImpAcc: number;
  sumImpRev: number;
  sumImpImp: number;
  sumImpMan: number;
}
