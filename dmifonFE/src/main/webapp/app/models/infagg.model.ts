export interface IInfAgg {
  id: number;
  idTipinfagg: number;
  idPro: number;
  infagg: string;
  proTipinfaggByIdTipinfagg: IproTipinfaggByIdTipinfagg;
}

export interface IproTipinfaggByIdTipinfagg {
  id: number;
  destipinfagg: string;
  ordtipinfagg: number;
}
