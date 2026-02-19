export interface IAmmAll {
  id: number;
  tipent: string;
  ident: number;
  nomfil: string;
  notfil: string;
  idFil: number;
  dtLstupd?: Date;
}

export interface IAllegatoResponse {
  codice: string;
  descrizione: string;
  allegati: IAmmAll[];
}
