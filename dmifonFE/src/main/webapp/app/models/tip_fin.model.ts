export interface ItipFin {
  id: number;
  livuno: number;
  livdue: number;
  livtre: number;
  livqua: number;
  livcin: number;
  livsei: number;
  codtipfin: string;
  destipfin: string;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
