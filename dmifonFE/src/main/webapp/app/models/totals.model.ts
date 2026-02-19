export interface Itotals {
  imprisfon: number;
  nroprofon: number;
  imprisfonpro?: number;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}

export interface ITotalsImp {
  impacc: number;
  impecoacc: number;
  impecoimp: number;
  impigv: number;
  impigvdel: number;
  impimp: number;
  impliq: number;
  impman: number;
  imprev: number;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}

export interface ITotalsDdr {
  impamm: number;
  impddr: number;
  impinc: number;
  imptra: number;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
