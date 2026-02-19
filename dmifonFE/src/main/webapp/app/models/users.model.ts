import { Iamm_ruo } from './amm_ruo.model';
import { Iamm_dir } from './amm_dir.model';

export interface IUsers {
  id: number;
  username: string;
  nome: string;
  cognome: string;
  email: string;
  emailalt: string;
  abilitato: boolean;
  [key: string]: any; // TS error 7053, deve essere dichiararo nelle entity per l'ordinamento
  ammUteruosById: IammUteruosById[];
}

export interface IammUteruosById {
  id: number;
  idUte: string;
  idRuo: string;
  flgdef: string;
  tipcondat: string;
  idDir: string;
  ammRuoByIdRuo?: Iamm_ruo;
  ammDirByIdDir?: Iamm_dir;
}
