export interface ITematica {
  id: number;
  livuno: number;
  livdue: number;  
  codtem: string;
  destem: string;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
