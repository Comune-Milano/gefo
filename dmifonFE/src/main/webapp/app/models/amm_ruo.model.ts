export interface Iamm_ruo {
  id: number;
  codruo: string;
  desruo: string;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
