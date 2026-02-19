export interface Iamm_dir {
  id: number;
  coddir: string;
  desdir: string;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
}
