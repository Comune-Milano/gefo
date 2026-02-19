import { IProg } from './progetto.model';
import { IUser } from '../entities/user/user.model';
import { IUsers } from './usersModel';

export interface ICommRic {
  id: number;
  idPro: number;
  tipric: string;
  desric: string;
  staric: string;
  dtasca: Date;
  dtainv: Date;
  risric: string;
  idRicpad: number;
  proProByIdPro: IProg;
  proRicutesById: IProRicute[];
}

export interface CommRicResponse {
  proRic: ICommRic;
  username: string;
}

export interface CommRicUsersResponse {
  proRic: ICommRic;
  proRicPad: ICommRic;
  users: IUsers;
}

export interface IProRicute {
  id: number;
  idRic: number;
  idUte: number;
  dtainv: Date;
}
