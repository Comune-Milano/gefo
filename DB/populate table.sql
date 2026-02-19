INSERT INTO dmifonamm.amm_par (codice,valore,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('FileSystemAllegati','\\xxxxxx\xxxx',NULL,NULL,NULL,NULL),
	 ('APIEndPoint','https://xx.xxx.it/',NULL,NULL,NULL,NULL),
	 ('APIPrecedenteClientId','xxxxxxx',NULL,NULL,NULL,NULL),
	 ('APIPrecedenteUltimoAnno','1900',NULL,NULL,NULL,NULL),
	 ('WSEnte','001',NULL,NULL,NULL,NULL),
	 ('WSCodiceUtente','xxxx',NULL,NULL,NULL,NULL),
	 ('APIClientSecret','xxxxxxx',NULL,NULL,NULL,NULL),
	 ('SemaforoGialloFasePercentuale','60',NULL,NULL,NULL,NULL),
	 ('SemaforoGialloMilestoneGiorni','30',NULL,NULL,NULL,NULL),
	 ('APIClientId','xxxxxxxx',NULL,NULL,NULL,NULL);
INSERT INTO dmifonamm.amm_par (codice,valore,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('RicercaRowsOnly','20',NULL,NULL,NULL,NULL),
	 ('LogoFooter','N',NULL,NULL,NULL,NULL),
	 ('TipdetconPrepon','20',NULL,NULL,NULL,NULL),
	 ('SoloSettoriDellaDirezione','N',NULL,NULL,NULL,NULL),
	 ('FaseDateDisabilitatePerControllati','S',NULL,NULL,NULL,NULL),
	 ('LogoNavbar','N',NULL,NULL,NULL,NULL),
	 ('ExportCapitoliColonnaPrePon','S',NULL,NULL,NULL,NULL),
	 ('MAIL_PROTOCOL','smtp',NULL,NULL,NULL,NULL),
	 ('RicercaAsse','API',NULL,NULL,NULL,NULL),
	 ('APIToponomasticaEndPoint','https://xxx.xxx.it/',NULL,NULL,NULL,NULL);
INSERT INTO dmifonamm.amm_par (codice,valore,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('RicercaSett','API',NULL,NULL,NULL,NULL),
	 ('MAIL_PROTOCOL_AUTH','T',NULL,NULL,NULL,NULL),
	 ('MAIL_HOST','smtp.libero.it',NULL,NULL,NULL,NULL),
	 ('MAIL_PORT','25',NULL,NULL,NULL,NULL),
	 ('MAIL_USERNAME','',NULL,NULL,NULL,NULL),
	 ('MAIL_PASSWORD','',NULL,NULL,NULL,NULL),
	 ('MAIL_FROM_ADDRESS','',NULL,NULL,NULL,NULL),
	 ('AggiornaDettagliControllaMandatoDuplicato','N',NULL,NULL,NULL,NULL),
	 ('RicercaDise','API',NULL,NULL,NULL,NULL),
	 ('WSEndPoint','http://xxxxxxx/',NULL,NULL,NULL,NULL);
INSERT INTO dmifonamm.amm_par (codice,valore,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('RicercaCapeCapu','API',NULL,NULL,NULL,NULL),
	 ('AggiornaDettagliContabiliNoMandatiReversali','N',NULL,NULL,NULL,NULL),
	 ('RicercaMand','API',NULL,NULL,NULL,NULL),
	 ('APIToponomasticaBearer','xxxxxxxx',NULL,NULL,NULL,NULL),
	 ('RicercaReve','API',NULL,NULL,NULL,NULL),
	 ('AggiornaDettagliContabiliSoloTipoFin','0',NULL,NULL,NULL,NULL),
	 ('APIToponomasticaPToken','xxxxx',NULL,NULL,NULL,NULL),
	 ('MandReveTuttiAnni','S',NULL,NULL,NULL,NULL),
	 ('AllegatiMaxSizeMB','5',NULL,NULL,NULL,NULL),
	 ('DocumentoFaq','0',NULL,NULL,NULL,NULL);
INSERT INTO dmifonamm.amm_par (codice,valore,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('SemaforoGialloBandoGiorni','30',NULL,NULL,NULL,NULL),
	 ('ToponomasticaIntegrata','S',NULL,NULL,NULL,NULL),
	 ('APIPrecedenteEndPoint','https://xxxx.it/',NULL,NULL,NULL,NULL),
	 ('APIPrecedenteUriToken','https://xxxxx.it/',NULL,NULL,NULL,NULL),
	 ('RicercaTifi','API',NULL,NULL,NULL,NULL),
	 ('SemaforoGialloTargetGiorni','30',NULL,NULL,NULL,NULL),
	 ('AllegatiContrattoMaxSizeMB','2',NULL,NULL,NULL,NULL),
	 ('AnticorruzioneLink','https://dati.anticorruzione.it/superset/dashboard/dettaglio_cig/?cig=',NULL,NULL,NULL,NULL),
	 ('RicercaRowsOnlyFrontOffice','500',NULL,NULL,NULL,NULL),
	 ('APIUriToken','https://xxxxx.it/',NULL,NULL,NULL,NULL);
INSERT INTO dmifonamm.amm_par (codice,valore,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('RicercaImpeAcce','API',NULL,NULL,NULL,NULL),
	 ('PrevisioneBloccata','false',NULL,NULL,'davide.dellagodenza','2025-11-25 12:03:55.66'),
	 ('APIPrecedenteClientSecret','xxxxxxx',NULL,NULL,NULL,NULL);
	 
INSERT INTO dmifonamm.amm_ent (codent,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('1','COMUNE DI xxxx','Comune di xxxx',NULL,NULL,NULL,NULL);
	 
INSERT INTO dmifonamm.amm_dir (coddir,desdir,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 ('D','DIREZIONE XXXX','ADMIN   ','2022-08-23 10:42:42.721923','davide.dellagodenza','2023-08-30 09:48:54.477');
	 
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (1,'/ricercaDirezione','Direzione elenco','ADMIN   ','2026-01-23 16:03:11.059239','ADMIN   ','2026-01-23 16:03:11.059239'),
	 (2,'/getDirezione','Direzione leggi','ADMIN   ','2026-01-23 16:03:11.09821','ADMIN   ','2026-01-23 16:03:11.09821'),
	 (3,'/inserisciDirezione','Direzione inserisci','ADMIN   ','2026-01-23 16:03:11.130161','ADMIN   ','2026-01-23 16:03:11.130161'),
	 (4,'/modificaDirezione','Direzione modifica','ADMIN   ','2026-01-23 16:03:11.164895','ADMIN   ','2026-01-23 16:03:11.164895'),
	 (5,'/cancellaDirezione','Direzione cancella','ADMIN   ','2026-01-23 16:03:11.201092','ADMIN   ','2026-01-23 16:03:11.201092'),
	 (6,'/ricercaFunzione','Funzione elenco','ADMIN   ','2026-01-23 16:03:11.24329','ADMIN   ','2026-01-23 16:03:11.24329'),
	 (7,'/getFunzione','Funzione leggi','ADMIN   ','2026-01-23 16:03:11.274166','ADMIN   ','2026-01-23 16:03:11.274166'),
	 (8,'/inserisciFunzione','Funzione inserisci','ADMIN   ','2026-01-23 16:03:11.305124','ADMIN   ','2026-01-23 16:03:11.305124'),
	 (9,'/modificaFunzione','Funzione modifica','ADMIN   ','2026-01-23 16:03:11.335312','ADMIN   ','2026-01-23 16:03:11.335312'),
	 (10,'/cancellaFunzione','Funzione cancella','ADMIN   ','2026-01-23 16:03:11.371291','ADMIN   ','2026-01-23 16:03:11.371291');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (11,'/ricercaRuolo','Ruolo elenco','ADMIN   ','2026-01-23 16:03:11.414994','ADMIN   ','2026-01-23 16:03:11.414994'),
	 (12,'/getRuolo','Ruolo leggi','ADMIN   ','2026-01-23 16:03:11.446867','ADMIN   ','2026-01-23 16:03:11.446867'),
	 (13,'/inserisciRuolo','Ruolo inserisci','ADMIN   ','2026-01-23 16:03:11.478573','ADMIN   ','2026-01-23 16:03:11.478573'),
	 (14,'/modificaRuolo','Ruolo modifica','ADMIN   ','2026-01-23 16:03:11.544198','ADMIN   ','2026-01-23 16:03:11.544198'),
	 (15,'/cancellaRuolo','Ruolo cancella','ADMIN   ','2026-01-23 16:03:11.587158','ADMIN   ','2026-01-23 16:03:11.587158'),
	 (16,'/ricercaUtente','Utente elenco','ADMIN   ','2026-01-23 16:03:11.618988','ADMIN   ','2026-01-23 16:03:11.618988'),
	 (17,'/getUtente','Utente leggi','ADMIN   ','2026-01-23 16:03:11.651163','ADMIN   ','2026-01-23 16:03:11.651163'),
	 (18,'/inserisciUtente','Utente inserisci','ADMIN   ','2026-01-23 16:03:11.683133','ADMIN   ','2026-01-23 16:03:11.683133'),
	 (19,'/modificaUtente','Utente modifica','ADMIN   ','2026-01-23 16:03:11.731147','ADMIN   ','2026-01-23 16:03:11.731147'),
	 (20,'/cancellaUtente','Utente cancella','ADMIN   ','2026-01-23 16:03:11.765388','ADMIN   ','2026-01-23 16:03:11.765388');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (21,'/ricercaAvanzamento','Avanzamento elenco','ADMIN   ','2026-01-23 16:03:11.794084','ADMIN   ','2026-01-23 16:03:11.794084'),
	 (22,'/getAvanzamento','Avanzamento leggi','ADMIN   ','2026-01-23 16:03:11.828239','ADMIN   ','2026-01-23 16:03:11.828239'),
	 (23,'/inserisciAvanzamento','Avanzamento inserisci','ADMIN   ','2026-01-23 16:03:11.858891','ADMIN   ','2026-01-23 16:03:11.858891'),
	 (24,'/modificaAvanzamento','Avanzamento modifica','ADMIN   ','2026-01-23 16:03:11.891077','ADMIN   ','2026-01-23 16:03:11.891077'),
	 (25,'/cancellaAvanzamento','Avanzamento cancella','ADMIN   ','2026-01-23 16:03:11.922899','ADMIN   ','2026-01-23 16:03:11.922899'),
	 (26,'/ricercaBando','Bando elenco','ADMIN   ','2026-01-23 16:03:11.956381','ADMIN   ','2026-01-23 16:03:11.956381'),
	 (27,'/getBando','Bando leggi','ADMIN   ','2026-01-23 16:03:11.985849','ADMIN   ','2026-01-23 16:03:11.985849'),
	 (28,'/inserisciBando','Bando inserisci','ADMIN   ','2026-01-23 16:03:12.015452','ADMIN   ','2026-01-23 16:03:12.015452'),
	 (29,'/modificaBando','Bando modifica','ADMIN   ','2026-01-23 16:03:12.048933','ADMIN   ','2026-01-23 16:03:12.048933'),
	 (30,'/cancellaBando','Bando cancella','ADMIN   ','2026-01-23 16:03:12.082102','ADMIN   ','2026-01-23 16:03:12.082102');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (31,'/ricercaCapitoliProgetto','CapitoliProgetto elenco','ADMIN   ','2026-01-23 16:03:12.11485','ADMIN   ','2026-01-23 16:03:12.11485'),
	 (32,'/getCapitoliProgetto','CapitoliProgetto leggi','ADMIN   ','2026-01-23 16:03:12.147597','ADMIN   ','2026-01-23 16:03:12.147597'),
	 (33,'/inserisciCapitoliProgetto','CapitoliProgetto inserisci','ADMIN   ','2026-01-23 16:03:12.209977','ADMIN   ','2026-01-23 16:03:12.209977'),
	 (34,'/modificaCapitoliProgetto','CapitoliProgetto modifica','ADMIN   ','2026-01-23 16:03:12.239531','ADMIN   ','2026-01-23 16:03:12.239531'),
	 (35,'/cancellaCapitoliProgetto','CapitoliProgetto cancella','ADMIN   ','2026-01-23 16:03:12.26923','ADMIN   ','2026-01-23 16:03:12.26923'),
	 (36,'/ricercaDdra','Ddra elenco','ADMIN   ','2026-01-23 16:03:12.300892','ADMIN   ','2026-01-23 16:03:12.300892'),
	 (37,'/getDdra','Ddra leggi','ADMIN   ','2026-01-23 16:03:12.333487','ADMIN   ','2026-01-23 16:03:12.333487'),
	 (38,'/inserisciDdra','Ddra inserisci','ADMIN   ','2026-01-23 16:03:12.366347','ADMIN   ','2026-01-23 16:03:12.366347'),
	 (39,'/modificaDdra','Ddra modifica','ADMIN   ','2026-01-23 16:03:12.395521','ADMIN   ','2026-01-23 16:03:12.395521'),
	 (40,'/cancellaDdra','Ddra cancella','ADMIN   ','2026-01-23 16:03:12.427153','ADMIN   ','2026-01-23 16:03:12.427153');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (41,'/ricercaDdr','Ddr elenco','ADMIN   ','2026-01-23 16:03:12.457733','ADMIN   ','2026-01-23 16:03:12.457733'),
	 (42,'/getDdr','Ddr leggi','ADMIN   ','2026-01-23 16:03:12.48962','ADMIN   ','2026-01-23 16:03:12.48962'),
	 (43,'/inserisciDdr','Ddr inserisci','ADMIN   ','2026-01-23 16:03:12.522113','ADMIN   ','2026-01-23 16:03:12.522113'),
	 (44,'/modificaDdr','Ddr modifica','ADMIN   ','2026-01-23 16:03:12.549909','ADMIN   ','2026-01-23 16:03:12.549909'),
	 (45,'/cancellaDdr','Ddr cancella','ADMIN   ','2026-01-23 16:03:12.581663','ADMIN   ','2026-01-23 16:03:12.581663'),
	 (46,'/ricercaMacroProgetto','MacroProgetto elenco','ADMIN   ','2026-01-23 16:03:12.60988','ADMIN   ','2026-01-23 16:03:12.60988'),
	 (47,'/getMacroProgetto','MacroProgetto leggi','ADMIN   ','2026-01-23 16:03:12.638382','ADMIN   ','2026-01-23 16:03:12.638382'),
	 (48,'/inserisciMacroProgetto','MacroProgetto inserisci','ADMIN   ','2026-01-23 16:03:12.675148','ADMIN   ','2026-01-23 16:03:12.675148'),
	 (49,'/modificaMacroProgetto','MacroProgetto modifica','ADMIN   ','2026-01-23 16:03:12.708434','ADMIN   ','2026-01-23 16:03:12.708434'),
	 (50,'/cancellaMacroProgetto','MacroProgetto cancella','ADMIN   ','2026-01-23 16:03:12.740441','ADMIN   ','2026-01-23 16:03:12.740441');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (51,'/ricercaPrevisione','Previsione elenco','ADMIN   ','2026-01-23 16:03:12.772255','ADMIN   ','2026-01-23 16:03:12.772255'),
	 (52,'/getPrevisione','Previsione leggi','ADMIN   ','2026-01-23 16:03:12.804969','ADMIN   ','2026-01-23 16:03:12.804969'),
	 (53,'/inserisciPrevisione','Previsione inserisci','ADMIN   ','2026-01-23 16:03:12.848063','ADMIN   ','2026-01-23 16:03:12.848063'),
	 (54,'/modificaPrevisione','Previsione modifica','ADMIN   ','2026-01-23 16:03:12.878871','ADMIN   ','2026-01-23 16:03:12.878871'),
	 (55,'/cancellaPrevisione','Previsione cancella','ADMIN   ','2026-01-23 16:03:12.911035','ADMIN   ','2026-01-23 16:03:12.911035'),
	 (56,'/ricercaProgetto','Progetto elenco','ADMIN   ','2026-01-23 16:03:12.942824','ADMIN   ','2026-01-23 16:03:12.942824'),
	 (57,'/getProgetto','Progetto leggi','ADMIN   ','2026-01-23 16:03:12.976555','ADMIN   ','2026-01-23 16:03:12.976555'),
	 (58,'/inserisciProgetto','Progetto inserisci','ADMIN   ','2026-01-23 16:03:13.003389','ADMIN   ','2026-01-23 16:03:13.003389'),
	 (59,'/modificaProgetto','Progetto modifica','ADMIN   ','2026-01-23 16:03:13.044381','ADMIN   ','2026-01-23 16:03:13.044381'),
	 (60,'/cancellaProgetto','Progetto cancella','ADMIN   ','2026-01-23 16:03:13.077846','ADMIN   ','2026-01-23 16:03:13.077846');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (61,'/ricercaResponsabiliProgetto','ResponsabiliProgetto elenco','ADMIN   ','2026-01-23 16:03:13.113316','ADMIN   ','2026-01-23 16:03:13.113316'),
	 (62,'/getResponsabiliProgetto','ResponsabiliProgetto leggi','ADMIN   ','2026-01-23 16:03:13.142143','ADMIN   ','2026-01-23 16:03:13.142143'),
	 (63,'/inserisciResponsabiliProgetto','ResponsabiliProgetto inserisci','ADMIN   ','2026-01-23 16:03:13.174144','ADMIN   ','2026-01-23 16:03:13.174144'),
	 (64,'/modificaResponsabiliProgetto','ResponsabiliProgetto modifica','ADMIN   ','2026-01-23 16:03:13.204976','ADMIN   ','2026-01-23 16:03:13.204976'),
	 (65,'/cancellaResponsabiliProgetto','ResponsabiliProgetto cancella','ADMIN   ','2026-01-23 16:03:13.238967','ADMIN   ','2026-01-23 16:03:13.238967'),
	 (66,'/ricercaTematica','Tematica elenco','ADMIN   ','2026-01-23 16:03:13.269141','ADMIN   ','2026-01-23 16:03:13.269141'),
	 (67,'/getTematica','Tematica leggi','ADMIN   ','2026-01-23 16:03:13.299953','ADMIN   ','2026-01-23 16:03:13.299953'),
	 (68,'/inserisciTematica','Tematica inserisci','ADMIN   ','2026-01-23 16:03:13.33072','ADMIN   ','2026-01-23 16:03:13.33072'),
	 (69,'/modificaTematica','Tematica modifica','ADMIN   ','2026-01-23 16:03:13.3623','ADMIN   ','2026-01-23 16:03:13.3623'),
	 (70,'/cancellaTematica','Tematica cancella','ADMIN   ','2026-01-23 16:03:13.394304','ADMIN   ','2026-01-23 16:03:13.394304');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (71,'/ricercaTipoFinanziamento','TipoFinanziamento elenco','ADMIN   ','2026-01-23 16:03:13.433509','ADMIN   ','2026-01-23 16:03:13.433509'),
	 (72,'/getTipoFinanziamento','TipoFinanziamento leggi','ADMIN   ','2026-01-23 16:03:13.474115','ADMIN   ','2026-01-23 16:03:13.474115'),
	 (73,'/inserisciTipoFinanziamento','TipoFinanziamento inserisci','ADMIN   ','2026-01-23 16:03:13.506421','ADMIN   ','2026-01-23 16:03:13.506421'),
	 (74,'/modificaTipoFinanziamento','TipoFinanziamento modifica','ADMIN   ','2026-01-23 16:03:13.537688','ADMIN   ','2026-01-23 16:03:13.537688'),
	 (75,'/cancellaTipoFinanziamento','TipoFinanziamento cancella','ADMIN   ','2026-01-23 16:03:13.567166','ADMIN   ','2026-01-23 16:03:13.567166'),
	 (76,'/ricercaEnte','Ente elenco','ADMIN   ','2026-01-23 16:03:13.599346','ADMIN   ','2026-01-23 16:03:13.599346'),
	 (77,'/getEnte','Ente leggi','ADMIN   ','2026-01-23 16:03:13.631164','ADMIN   ','2026-01-23 16:03:13.631164'),
	 (78,'/inserisciEnte','Ente inserisci','ADMIN   ','2026-01-23 16:03:13.658944','ADMIN   ','2026-01-23 16:03:13.658944'),
	 (79,'/modificaEnte','Ente modifica','ADMIN   ','2026-01-23 16:03:13.695015','ADMIN   ','2026-01-23 16:03:13.695015'),
	 (80,'/cancellaEnte','Ente cancella','ADMIN   ','2026-01-23 16:03:13.727941','ADMIN   ','2026-01-23 16:03:13.727941');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (81,'/ricercaRichiesta','Richiesta elenco','ADMIN   ','2026-01-23 16:03:13.758516','ADMIN   ','2026-01-23 16:03:13.758516'),
	 (82,'/getRichiesta','Richiesta leggi','ADMIN   ','2026-01-23 16:03:13.790948','ADMIN   ','2026-01-23 16:03:13.790948'),
	 (83,'/inserisciRichiesta','Richiesta inserisci','ADMIN   ','2026-01-23 16:03:13.821154','ADMIN   ','2026-01-23 16:03:13.821154'),
	 (84,'/modificaRichiesta','Richiesta modifica','ADMIN   ','2026-01-23 16:03:13.849944','ADMIN   ','2026-01-23 16:03:13.849944'),
	 (85,'/cancellaRichiesta','Richiesta cancella','ADMIN   ','2026-01-23 16:03:13.886861','ADMIN   ','2026-01-23 16:03:13.886861'),
	 (86,'/ricercaAllegato','Allegato elenco','ADMIN   ','2026-01-23 16:03:13.915122','ADMIN   ','2026-01-23 16:03:13.915122'),
	 (87,'/getAllegato','Allegato leggi','ADMIN   ','2026-01-23 16:03:13.948272','ADMIN   ','2026-01-23 16:03:13.948272'),
	 (88,'/inserisciAllegato','Allegato inserisci','ADMIN   ','2026-01-23 16:03:13.978811','ADMIN   ','2026-01-23 16:03:13.978811'),
	 (89,'/modificaAllegato','Allegato modifica','ADMIN   ','2026-01-23 16:03:14.015797','ADMIN   ','2026-01-23 16:03:14.015797'),
	 (90,'/cancellaAllegato','Allegato cancella','ADMIN   ','2026-01-23 16:03:14.057925','ADMIN   ','2026-01-23 16:03:14.057925');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (91,'/ricercaElaborazione','Elaborazione elenco','ADMIN   ','2026-01-23 16:03:14.089927','ADMIN   ','2026-01-23 16:03:14.089927'),
	 (92,'/getElaborazione','Elaborazione leggi','ADMIN   ','2026-01-23 16:03:14.120896','ADMIN   ','2026-01-23 16:03:14.120896'),
	 (93,'/inserisciElaborazione','Elaborazione inserisci','ADMIN   ','2026-01-23 16:03:14.152064','ADMIN   ','2026-01-23 16:03:14.152064'),
	 (94,'/modificaElaborazione','Elaborazione modifica','ADMIN   ','2026-01-23 16:03:14.193467','ADMIN   ','2026-01-23 16:03:14.193467'),
	 (95,'/cancellaElaborazione','Elaborazione cancella','ADMIN   ','2026-01-23 16:03:14.230482','ADMIN   ','2026-01-23 16:03:14.230482'),
	 (96,'/ricercaParametro','Parametro elenco','ADMIN   ','2026-01-23 16:03:14.257197','ADMIN   ','2026-01-23 16:03:14.257197'),
	 (97,'/getParametro','Parametro leggi','ADMIN   ','2026-01-23 16:03:14.286948','ADMIN   ','2026-01-23 16:03:14.286948'),
	 (98,'/inserisciParametro','Parametro inserisci','ADMIN   ','2026-01-23 16:03:14.325897','ADMIN   ','2026-01-23 16:03:14.325897'),
	 (99,'/modificaParametro','Parametro modifica','ADMIN   ','2026-01-23 16:03:14.355703','ADMIN   ','2026-01-23 16:03:14.355703'),
	 (100,'/cancellaParametro','Parametro cancella','ADMIN   ','2026-01-23 16:03:14.386825','ADMIN   ','2026-01-23 16:03:14.386825');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (101,'/ricercaMunicipio','Municipio elenco','ADMIN   ','2026-01-23 16:03:14.418972','ADMIN   ','2026-01-23 16:03:14.418972'),
	 (102,'/getMunicipio','Municipio leggi','ADMIN   ','2026-01-23 16:03:14.448836','ADMIN   ','2026-01-23 16:03:14.448836'),
	 (103,'/inserisciMunicipio','Municipio inserisci','ADMIN   ','2026-01-23 16:03:14.478258','ADMIN   ','2026-01-23 16:03:14.478258'),
	 (104,'/modificaMunicipio','Municipio modifica','ADMIN   ','2026-01-23 16:03:14.507033','ADMIN   ','2026-01-23 16:03:14.507033'),
	 (105,'/cancellaMunicipio','Municipio cancella','ADMIN   ','2026-01-23 16:03:14.537607','ADMIN   ','2026-01-23 16:03:14.537607'),
	 (106,'/ricercaNil','Nil elenco','ADMIN   ','2026-01-23 16:03:14.56932','ADMIN   ','2026-01-23 16:03:14.56932'),
	 (107,'/getNil','Nil leggi','ADMIN   ','2026-01-23 16:03:14.599447','ADMIN   ','2026-01-23 16:03:14.599447'),
	 (108,'/inserisciNil','Nil inserisci','ADMIN   ','2026-01-23 16:03:14.627921','ADMIN   ','2026-01-23 16:03:14.627921'),
	 (109,'/modificaNil','Nil modifica','ADMIN   ','2026-01-23 16:03:14.663359','ADMIN   ','2026-01-23 16:03:14.663359'),
	 (110,'/cancellaNil','Nil cancella','ADMIN   ','2026-01-23 16:03:14.699698','ADMIN   ','2026-01-23 16:03:14.699698');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (111,'/ricercaListaValore','ListaValore elenco','ADMIN   ','2026-01-23 16:03:14.734699','ADMIN   ','2026-01-23 16:03:14.734699'),
	 (112,'/getListaValore','ListaValore leggi','ADMIN   ','2026-01-23 16:03:14.771303','ADMIN   ','2026-01-23 16:03:14.771303'),
	 (113,'/inserisciListaValore','ListaValore inserisci','ADMIN   ','2026-01-23 16:03:14.804104','ADMIN   ','2026-01-23 16:03:14.804104'),
	 (114,'/modificaListaValore','ListaValore modifica','ADMIN   ','2026-01-23 16:03:14.832716','ADMIN   ','2026-01-23 16:03:14.832716'),
	 (115,'/cancellaListaValore','ListaValore cancella','ADMIN   ','2026-01-23 16:03:14.861279','ADMIN   ','2026-01-23 16:03:14.861279'),
	 (116,'/ricercaTipoFase','TipoFase elenco','ADMIN   ','2026-01-23 16:03:14.889153','ADMIN   ','2026-01-23 16:03:14.889153'),
	 (117,'/getTipoFase','TipoFase leggi','ADMIN   ','2026-01-23 16:03:14.919211','ADMIN   ','2026-01-23 16:03:14.919211'),
	 (118,'/inserisciTipoFase','TipoFase inserisci','ADMIN   ','2026-01-23 16:03:14.944827','ADMIN   ','2026-01-23 16:03:14.944827'),
	 (119,'/modificaTipoFase','TipoFase modifica','ADMIN   ','2026-01-23 16:03:14.971804','ADMIN   ','2026-01-23 16:03:14.971804'),
	 (120,'/cancellaTipoFase','TipoFase cancella','ADMIN   ','2026-01-23 16:03:15.006919','ADMIN   ','2026-01-23 16:03:15.006919');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (121,'/ricercaTipoImporto','TipoImporto elenco','ADMIN   ','2026-01-23 16:03:15.033679','ADMIN   ','2026-01-23 16:03:15.033679'),
	 (122,'/getTipoImporto','TipoImporto leggi','ADMIN   ','2026-01-23 16:03:15.060791','ADMIN   ','2026-01-23 16:03:15.060791'),
	 (123,'/inserisciTipoImporto','TipoImporto inserisci','ADMIN   ','2026-01-23 16:03:15.091768','ADMIN   ','2026-01-23 16:03:15.091768'),
	 (124,'/modificaTipoImporto','TipoImporto modifica','ADMIN   ','2026-01-23 16:03:15.123508','ADMIN   ','2026-01-23 16:03:15.123508'),
	 (125,'/cancellaTipoImporto','TipoImporto cancella','ADMIN   ','2026-01-23 16:03:15.153627','ADMIN   ','2026-01-23 16:03:15.153627'),
	 (126,'/ricercaInformazioneAggiuntiva','InformazioneAggiuntiva elenco','ADMIN   ','2026-01-23 16:03:15.181679','ADMIN   ','2026-01-23 16:03:15.181679'),
	 (127,'/getInformazioneAggiuntiva','InformazioneAggiuntiva leggi','ADMIN   ','2026-01-23 16:03:15.216805','ADMIN   ','2026-01-23 16:03:15.216805'),
	 (128,'/inserisciInformazioneAggiuntiva','InformazioneAggiuntiva inserisci','ADMIN   ','2026-01-23 16:03:15.25476','ADMIN   ','2026-01-23 16:03:15.25476'),
	 (129,'/modificaInformazioneAggiuntiva','InformazioneAggiuntiva modifica','ADMIN   ','2026-01-23 16:03:15.291869','ADMIN   ','2026-01-23 16:03:15.291869'),
	 (130,'/cancellaInformazioneAggiuntiva','InformazioneAggiuntiva cancella','ADMIN   ','2026-01-23 16:03:15.319786','ADMIN   ','2026-01-23 16:03:15.319786');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (131,'/ricercaTipoIndicatore','TipoIndicatore elenco','ADMIN   ','2026-01-23 16:03:15.370778','ADMIN   ','2026-01-23 16:03:15.370778'),
	 (132,'/getTipoIndicatore','TipoIndicatore leggi','ADMIN   ','2026-01-23 16:03:15.401016','ADMIN   ','2026-01-23 16:03:15.401016'),
	 (133,'/inserisciTipoIndicatore','TipoIndicatore inserisci','ADMIN   ','2026-01-23 16:03:15.428979','ADMIN   ','2026-01-23 16:03:15.428979'),
	 (134,'/modificaTipoIndicatore','TipoIndicatore modifica','ADMIN   ','2026-01-23 16:03:15.465321','ADMIN   ','2026-01-23 16:03:15.465321'),
	 (135,'/cancellaTipoIndicatore','TipoIndicatore cancella','ADMIN   ','2026-01-23 16:03:15.493046','ADMIN   ','2026-01-23 16:03:15.493046'),
	 (136,'/ricercaIndicatore','Indicatore elenco','ADMIN   ','2026-01-23 16:03:15.521331','ADMIN   ','2026-01-23 16:03:15.521331'),
	 (137,'/getIndicatore','Indicatore leggi','ADMIN   ','2026-01-23 16:03:15.549392','ADMIN   ','2026-01-23 16:03:15.549392'),
	 (138,'/inserisciIndicatore','Indicatore inserisci','ADMIN   ','2026-01-23 16:03:15.578812','ADMIN   ','2026-01-23 16:03:15.578812'),
	 (139,'/modificaIndicatore','Indicatore modifica','ADMIN   ','2026-01-23 16:03:15.606817','ADMIN   ','2026-01-23 16:03:15.606817'),
	 (140,'/cancellaIndicatore','Indicatore cancella','ADMIN   ','2026-01-23 16:03:15.636814','ADMIN   ','2026-01-23 16:03:15.636814');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (141,'/ricercaResponsabile','Responsabile elenco','ADMIN   ','2026-01-23 16:03:15.680379','ADMIN   ','2026-01-23 16:03:15.680379'),
	 (142,'/getResponsabile','Responsabile leggi','ADMIN   ','2026-01-23 16:03:15.716118','ADMIN   ','2026-01-23 16:03:15.716118'),
	 (143,'/inserisciResponsabile','Responsabile inserisci','ADMIN   ','2026-01-23 16:03:15.75062','ADMIN   ','2026-01-23 16:03:15.75062'),
	 (144,'/modificaResponsabile','Responsabile modifica','ADMIN   ','2026-01-23 16:03:15.78385','ADMIN   ','2026-01-23 16:03:15.78385'),
	 (145,'/cancellaResponsabile','Responsabile cancella','ADMIN   ','2026-01-23 16:03:15.813556','ADMIN   ','2026-01-23 16:03:15.813556'),
	 (146,'/ricercaTipoResponsabile','TipoResponsabile elenco','ADMIN   ','2026-01-23 16:03:15.843422','ADMIN   ','2026-01-23 16:03:15.843422'),
	 (147,'/getTipoResponsabile','TipoResponsabile leggi','ADMIN   ','2026-01-23 16:03:15.876113','ADMIN   ','2026-01-23 16:03:15.876113'),
	 (148,'/inserisciTipoResponsabile','TipoResponsabile inserisci','ADMIN   ','2026-01-23 16:03:15.909301','ADMIN   ','2026-01-23 16:03:15.909301'),
	 (149,'/modificaTipoResponsabile','TipoResponsabile modifica','ADMIN   ','2026-01-23 16:03:15.941366','ADMIN   ','2026-01-23 16:03:15.941366'),
	 (150,'/cancellaTipoResponsabile','TipoResponsabile cancella','ADMIN   ','2026-01-23 16:03:15.983108','ADMIN   ','2026-01-23 16:03:15.983108');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (151,'/ricercaMunicipioProgetto','MunicipioProgetto elenco','ADMIN   ','2026-01-23 16:03:16.013749','ADMIN   ','2026-01-23 16:03:16.013749'),
	 (152,'/getMunicipioProgetto','MunicipioProgetto leggi','ADMIN   ','2026-01-23 16:03:16.045218','ADMIN   ','2026-01-23 16:03:16.045218'),
	 (153,'/inserisciMunicipioProgetto','MunicipioProgetto inserisci','ADMIN   ','2026-01-23 16:03:16.073726','ADMIN   ','2026-01-23 16:03:16.073726'),
	 (154,'/modificaMunicipioProgetto','MunicipioProgetto modifica','ADMIN   ','2026-01-23 16:03:16.108221','ADMIN   ','2026-01-23 16:03:16.108221'),
	 (155,'/cancellaMunicipioProgetto','MunicipioProgetto cancella','ADMIN   ','2026-01-23 16:03:16.139997','ADMIN   ','2026-01-23 16:03:16.139997'),
	 (156,'/ricercaNilProgetto','NilProgetto elenco','ADMIN   ','2026-01-23 16:03:16.171006','ADMIN   ','2026-01-23 16:03:16.171006'),
	 (157,'/getNilProgetto','NilProgetto leggi','ADMIN   ','2026-01-23 16:03:16.20348','ADMIN   ','2026-01-23 16:03:16.20348'),
	 (158,'/inserisciNilProgetto','NilProgetto inserisci','ADMIN   ','2026-01-23 16:03:16.231266','ADMIN   ','2026-01-23 16:03:16.231266'),
	 (159,'/modificaNilProgetto','NilProgetto modifica','ADMIN   ','2026-01-23 16:03:16.267233','ADMIN   ','2026-01-23 16:03:16.267233'),
	 (160,'/cancellaNilProgetto','NilProgetto cancella','ADMIN   ','2026-01-23 16:03:16.294933','ADMIN   ','2026-01-23 16:03:16.294933');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (161,'/ricercaStradaProgetto','StradaProgetto elenco','ADMIN   ','2026-01-23 16:03:16.322752','ADMIN   ','2026-01-23 16:03:16.322752'),
	 (162,'/getStradaProgetto','StradaProgetto leggi','ADMIN   ','2026-01-23 16:03:16.34904','ADMIN   ','2026-01-23 16:03:16.34904'),
	 (163,'/inserisciStradaProgetto','StradaProgetto inserisci','ADMIN   ','2026-01-23 16:03:16.376648','ADMIN   ','2026-01-23 16:03:16.376648'),
	 (164,'/modificaStradaProgetto','StradaProgetto modifica','ADMIN   ','2026-01-23 16:03:16.415245','ADMIN   ','2026-01-23 16:03:16.415245'),
	 (165,'/cancellaStradaProgetto','StradaProgetto cancella','ADMIN   ','2026-01-23 16:03:16.450972','ADMIN   ','2026-01-23 16:03:16.450972'),
	 (166,'/ricercaCumulativo','Cumulativo elenco','ADMIN   ','2026-01-23 16:03:16.482102','ADMIN   ','2026-01-23 16:03:16.482102'),
	 (167,'/getCumulativo','Cumulativo leggi','ADMIN   ','2026-01-23 16:03:16.515998','ADMIN   ','2026-01-23 16:03:16.515998'),
	 (168,'/inserisciCumulativo','Cumulativo inserisci','ADMIN   ','2026-01-23 16:03:16.564795','ADMIN   ','2026-01-23 16:03:16.564795'),
	 (169,'/modificaCumulativo','Cumulativo modifica','ADMIN   ','2026-01-23 16:03:16.597487','ADMIN   ','2026-01-23 16:03:16.597487'),
	 (170,'/cancellaCumulativo','Cumulativo cancella','ADMIN   ','2026-01-23 16:03:16.623772','ADMIN   ','2026-01-23 16:03:16.623772');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (171,'/associaFunzione','Funzione associa','ADMIN   ','2026-01-23 16:03:16.658539','ADMIN   ','2026-01-23 16:03:16.658539'),
	 (172,'/dissociaFunzione','Funzione disassocia','ADMIN   ','2026-01-23 16:03:16.691223','ADMIN   ','2026-01-23 16:03:16.691223'),
	 (173,'/ricercaUtenteRuolo','Utente con tutti i ruoli associati','ADMIN   ','2026-01-23 16:03:16.722557','ADMIN   ','2026-01-23 16:03:16.722557'),
	 (174,'/abilitazioneUtente','Utente abilita/disabilita','ADMIN   ','2026-01-23 16:03:16.755077','ADMIN   ','2026-01-23 16:03:16.755077'),
	 (175,'/cancellaRuoloDirezione','UtenteRuolo cancella','ADMIN   ','2026-01-23 16:03:16.784101','ADMIN   ','2026-01-23 16:03:16.784101'),
	 (176,'/creaFasiAvanzamento','Avanzamento creazione fasi','ADMIN   ','2026-01-23 16:03:16.811419','ADMIN   ','2026-01-23 16:03:16.811419'),
	 (177,'/cancellaFase','Avanzamento elimina fase','ADMIN   ','2026-01-23 16:03:16.839034','ADMIN   ','2026-01-23 16:03:16.839034'),
	 (178,'/ricercaBandoAutocomplete','Bandi ricerca con autocomplete','ADMIN   ','2026-01-23 16:03:16.867139','ADMIN   ','2026-01-23 16:03:16.867139'),
	 (179,'/aggiornamentoEntitaContabili','Entita contabile aggiornamento','ADMIN   ','2026-01-23 16:03:16.895836','ADMIN   ','2026-01-23 16:03:16.895836'),
	 (180,'/getCapitoloEntrata','Capitolo di entrata leggi','ADMIN   ','2026-01-23 16:03:16.923778','ADMIN   ','2026-01-23 16:03:16.923778');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (181,'/getCapitoloUscita','Capitolo di uscita leggi','ADMIN   ','2026-01-23 16:03:16.956379','ADMIN   ','2026-01-23 16:03:16.956379'),
	 (182,'/ricercaCapitoliEntrata','Capitoli di entrata elenco','ADMIN   ','2026-01-23 16:03:16.987352','ADMIN   ','2026-01-23 16:03:16.987352'),
	 (183,'/ricercaCapitoliUscita','Capitoli di uscita elenco','ADMIN   ','2026-01-23 16:03:17.01911','ADMIN   ','2026-01-23 16:03:17.01911'),
	 (184,'/associaDdrADdra','Ddra associa ddr','ADMIN   ','2026-01-23 16:03:17.058319','ADMIN   ','2026-01-23 16:03:17.058319'),
	 (185,'/ricercaDdraAutocomplete','Ddra ricerca con autocomplete','ADMIN   ','2026-01-23 16:03:17.088843','ADMIN   ','2026-01-23 16:03:17.088843'),
	 (186,'/ricercaDdrAutocomplete','Ddr ricerca con autocomplete','ADMIN   ','2026-01-23 16:03:17.117491','ADMIN   ','2026-01-23 16:03:17.117491'),
	 (187,'/exportProgetti','Export dei progetti','ADMIN   ','2026-01-23 16:03:17.145075','ADMIN   ','2026-01-23 16:03:17.145075'),
	 (188,'/exportImpegni','Export degli impegni','ADMIN   ','2026-01-23 16:03:17.174007','ADMIN   ','2026-01-23 16:03:17.174007'),
	 (189,'/exportCapitoli','Export dei capitoli','ADMIN   ','2026-01-23 16:03:17.201331','ADMIN   ','2026-01-23 16:03:17.201331'),
	 (190,'/exportAvanzamenti','Export degli avanzamenti','ADMIN   ','2026-01-23 16:03:17.229999','ADMIN   ','2026-01-23 16:03:17.229999');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (191,'/exportMandati','Export dei mandati','ADMIN   ','2026-01-23 16:03:17.256381','ADMIN   ','2026-01-23 16:03:17.256381'),
	 (192,'/exportReversali','Export delle reversali','ADMIN   ','2026-01-23 16:03:17.286006','ADMIN   ','2026-01-23 16:03:17.286006'),
	 (193,'/exportDdr','Export delle ddr','ADMIN   ','2026-01-23 16:03:17.318068','ADMIN   ','2026-01-23 16:03:17.318068'),
	 (194,'/getImpegniProgetto','Impegni progetto elenco','ADMIN   ','2026-01-23 16:03:17.359654','ADMIN   ','2026-01-23 16:03:17.359654'),
	 (195,'/modificaImpegniProgetto','Impegni progetto modifica','ADMIN   ','2026-01-23 16:03:17.391253','ADMIN   ','2026-01-23 16:03:17.391253'),
	 (196,'/cancellaDettaglioContabile','Dettaglio contabile eliminazione','ADMIN   ','2026-01-23 16:03:17.423065','ADMIN   ','2026-01-23 16:03:17.423065'),
	 (197,'/getAccertamento','Accertamento leggi','ADMIN   ','2026-01-23 16:03:17.454009','ADMIN   ','2026-01-23 16:03:17.454009'),
	 (198,'/getImpegno','Impegno leggi','ADMIN   ','2026-01-23 16:03:17.486217','ADMIN   ','2026-01-23 16:03:17.486217'),
	 (199,'/getCrono','Crono leggi','ADMIN   ','2026-01-23 16:03:17.517','ADMIN   ','2026-01-23 16:03:17.517'),
	 (200,'/ricercaAccertamenti','Accertamenti elenco','ADMIN   ','2026-01-23 16:03:17.55026','ADMIN   ','2026-01-23 16:03:17.55026');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (201,'/ricercaImpegni','Impegni elenco','ADMIN   ','2026-01-23 16:03:17.582552','ADMIN   ','2026-01-23 16:03:17.582552'),
	 (202,'/ricercaAccertamentiProgetto','Accertamenti progetto elenco','ADMIN   ','2026-01-23 16:03:17.613067','ADMIN   ','2026-01-23 16:03:17.613067'),
	 (203,'/ricercaImpegniProgetto','Impegni  progetto elenco','ADMIN   ','2026-01-23 16:03:17.645069','ADMIN   ','2026-01-23 16:03:17.645069'),
	 (204,'/getTipoImportoRisorsa','Tipo importo elenco','ADMIN   ','2026-01-23 16:03:17.680343','ADMIN   ','2026-01-23 16:03:17.680343'),
	 (205,'/getListaValoriFaseIntervento','Lista valori fase intervento elenco','ADMIN   ','2026-01-23 16:03:17.728198','ADMIN   ','2026-01-23 16:03:17.728198'),
	 (206,'/getListaValoriLivelloCriticita','Lista valori livello criticita elenco','ADMIN   ','2026-01-23 16:03:17.766256','ADMIN   ','2026-01-23 16:03:17.766256'),
	 (207,'/getListaValoriTipoAppalto','Lista valori tipo appalto elenco','ADMIN   ','2026-01-23 16:03:17.80363','ADMIN   ','2026-01-23 16:03:17.80363'),
	 (208,'/getListaValoriStatoAnticipazione','Lista valori stato anticipazione elenco','ADMIN   ','2026-01-23 16:03:17.833199','ADMIN   ','2026-01-23 16:03:17.833199'),
	 (209,'/getListaValoriTipologiaFasi','Lista valori tipologia fasi elenco','ADMIN   ','2026-01-23 16:03:17.863873','ADMIN   ','2026-01-23 16:03:17.863873'),
	 (210,'/ricercaMacroProgettiAutocomplete','Macro progetti ricerca  con autocomplete','ADMIN   ','2026-01-23 16:03:17.898123','ADMIN   ','2026-01-23 16:03:17.898123');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (211,'/getMandato','Mandato leggi','ADMIN   ','2026-01-23 16:03:17.928161','ADMIN   ','2026-01-23 16:03:17.928161'),
	 (212,'/ricercaMandato','Mandato elenco','ADMIN   ','2026-01-23 16:03:17.958296','ADMIN   ','2026-01-23 16:03:17.958296'),
	 (213,'/getMilestoneFase','Milestone fase elenco','ADMIN   ','2026-01-23 16:03:18.000293','ADMIN   ','2026-01-23 16:03:18.000293'),
	 (214,'/modificaMilestoneFase','Milestone fase modifica','ADMIN   ','2026-01-23 16:03:18.030208','ADMIN   ','2026-01-23 16:03:18.030208'),
	 (215,'/cancellaMilestone','Milestone eliminazione','ADMIN   ','2026-01-23 16:03:18.063285','ADMIN   ','2026-01-23 16:03:18.063285'),
	 (216,'/getAllMunicipi','Municipi elenco','ADMIN   ','2026-01-23 16:03:18.093599','ADMIN   ','2026-01-23 16:03:18.093599'),
	 (217,'/getBloccaPrevisioni','Previsione bloccate lettura','ADMIN   ','2026-01-23 16:03:18.125214','ADMIN   ','2026-01-23 16:03:18.125214'),
	 (218,'/bloccaSbloccaPrevisioni','Previsioni blocca/sblocca','ADMIN   ','2026-01-23 16:03:18.154119','ADMIN   ','2026-01-23 16:03:18.154119'),
	 (219,'/inserisciProgettoFiglio','Progetto inserimentro progetto figlio','ADMIN   ','2026-01-23 16:03:18.188312','ADMIN   ','2026-01-23 16:03:18.188312'),
	 (220,'/ricercaProgettiAutocomplete','Progetti ricerca  con autocomplete','ADMIN   ','2026-01-23 16:03:18.219119','ADMIN   ','2026-01-23 16:03:18.219119');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (221,'/getAllAssessorati','Assessorati elenco','ADMIN   ','2026-01-23 16:03:18.246993','ADMIN   ','2026-01-23 16:03:18.246993'),
	 (222,'/getAllSettori','Settori elenco','ADMIN   ','2026-01-23 16:03:18.274096','ADMIN   ','2026-01-23 16:03:18.274096'),
	 (223,'/getAllStatiBando','Stati bando elenco','ADMIN   ','2026-01-23 16:03:18.303107','ADMIN   ','2026-01-23 16:03:18.303107'),
	 (224,'/getAllStatiFinanziamento','Stati finanziamento elenco','ADMIN   ','2026-01-23 16:03:18.345712','ADMIN   ','2026-01-23 16:03:18.345712'),
	 (225,'/getAllStatiProgetto','Stati progetto elenco','ADMIN   ','2026-01-23 16:03:18.376288','ADMIN   ','2026-01-23 16:03:18.376288'),
	 (226,'/ricercaTematicaAutocomplete','Tematiche ricerca  con autocomplete','ADMIN   ','2026-01-23 16:03:18.407953','ADMIN   ','2026-01-23 16:03:18.407953'),
	 (227,'/ricercaTipoFinanziamentoAutocomplete','Tipo finanziamento ricerca  con autocomplete','ADMIN   ','2026-01-23 16:03:18.438161','ADMIN   ','2026-01-23 16:03:18.438161'),
	 (228,'/getTotali','Totali elenco','ADMIN   ','2026-01-23 16:03:18.471046','ADMIN   ','2026-01-23 16:03:18.471046'),
	 (229,'/getFunzioni','Funzioni elenco','ADMIN   ','2026-01-23 16:03:18.522308','ADMIN   ','2026-01-23 16:03:18.522308'),
	 (230,'/ricercaSettoriAutocomplete','Settori elenco autocomplete','ADMIN   ','2026-01-23 16:03:18.558758','ADMIN   ','2026-01-23 16:03:18.558758');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (231,'/ricercaAssessoratiAutocomplete','Assessoratii elenco autocomplete','ADMIN   ','2026-01-23 16:03:18.595153','ADMIN   ','2026-01-23 16:03:18.595153'),
	 (232,'/getPermessiRuolo','Ruolo elenco permessi','ADMIN   ','2026-01-23 16:03:18.627021','ADMIN   ','2026-01-23 16:03:18.627021'),
	 (233,'/cancellaRichiestaUtente','Cancella riga con utente della richiesta','ADMIN   ','2026-01-23 16:03:18.655862','ADMIN   ','2026-01-23 16:03:18.655862'),
	 (234,'/getOutputElaborazione','Output elaborazione','ADMIN   ','2026-01-23 16:03:18.690458','ADMIN   ','2026-01-23 16:03:18.690458'),
	 (235,'/caricaFileAllegato','Allegato carica','ADMIN   ','2026-01-23 16:03:18.720178','ADMIN   ','2026-01-23 16:03:18.720178'),
	 (236,'/scaricaAllegato','Allegato scarica','ADMIN   ','2026-01-23 16:03:18.749894','ADMIN   ','2026-01-23 16:03:18.749894'),
	 (237,'/getParametroByCodice','Parametro per codice','ADMIN   ','2026-01-23 16:03:18.781279','ADMIN   ','2026-01-23 16:03:18.781279'),
	 (238,'/ricercaRichiestaAutocomplete','Richiesta ricerca','ADMIN   ','2026-01-23 16:03:18.812442','ADMIN   ','2026-01-23 16:03:18.812442'),
	 (239,'/ricercaNilAutocomplete','NIL ricerca','ADMIN   ','2026-01-23 16:03:18.843972','ADMIN   ','2026-01-23 16:03:18.843972'),
	 (240,'/modificaImportoRisorseFiglio','Modifica Importo Risorse Figlio','ADMIN   ','2026-01-23 16:03:18.876211','ADMIN   ','2026-01-23 16:03:18.876211');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (241,'/getListaValoriTipoLista','Tipo lista valori','ADMIN   ','2026-01-23 16:03:18.907777','ADMIN   ','2026-01-23 16:03:18.907777'),
	 (242,'/ricercaReversale','Ricerca reversale','ADMIN   ','2026-01-23 16:03:18.940236','ADMIN   ','2026-01-23 16:03:18.940236'),
	 (243,'/getReversale','Get reversale','ADMIN   ','2026-01-23 16:03:18.972316','ADMIN   ','2026-01-23 16:03:18.972316'),
	 (244,'/ricercaMandatiProgetto','Ricerca mandati progetto','ADMIN   ','2026-01-23 16:03:19.005795','ADMIN   ','2026-01-23 16:03:19.005795'),
	 (245,'/ricercaReversaliProgetto','Ricerca reversali progetto','ADMIN   ','2026-01-23 16:03:19.036075','ADMIN   ','2026-01-23 16:03:19.036075'),
	 (246,'/associaMandatiADdr','Associa i mandati alla Ddr','ADMIN   ','2026-01-23 16:03:19.068075','ADMIN   ','2026-01-23 16:03:19.068075'),
	 (247,'/associaReversaliADdr','Associa le reversali alla Ddr','ADMIN   ','2026-01-23 16:03:19.099935','ADMIN   ','2026-01-23 16:03:19.099935'),
	 (248,'/cancellaDettaglioContabileDdr','Cancella riga dettaglio contabile della ddr','ADMIN   ','2026-01-23 16:03:19.13181','ADMIN   ','2026-01-23 16:03:19.13181'),
	 (249,'/getUtenteRuoloTipoFinanziamento','Utente Ruolo Tipo Finanziamento elenco','ADMIN   ','2026-01-23 16:03:19.186892','ADMIN   ','2026-01-23 16:03:19.186892'),
	 (250,'/modificaUtenteRuoloTipoFinanziamento','Utente Ruolo Tipo Finanziamento modifica','ADMIN   ','2026-01-23 16:03:19.226588','ADMIN   ','2026-01-23 16:03:19.226588');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (251,'/cancellaUtenteRuoloTipoFinanziamento','Utente Ruolo Tipo Finanziamento eliminazione','ADMIN   ','2026-01-23 16:03:19.258378','ADMIN   ','2026-01-23 16:03:19.258378'),
	 (252,'/getListaValoriTipoDettaglioContabile','Lista valori tipo dettaglio contabile','ADMIN   ','2026-01-23 16:03:19.292154','ADMIN   ','2026-01-23 16:03:19.292154'),
	 (253,'/ricercaDirezioneAutocomplete','Direzione ricerca','ADMIN   ','2026-01-23 16:03:19.335397','ADMIN   ','2026-01-23 16:03:19.335397'),
	 (254,'/ricercaStradaAutocomplete','Ricerca strada','ADMIN   ','2026-01-23 16:03:19.369531','ADMIN   ','2026-01-23 16:03:19.369531'),
	 (255,'/ricercaCivicoAutocomplete','Ricerca Civico','ADMIN   ','2026-01-23 16:03:19.400228','ADMIN   ','2026-01-23 16:03:19.400228'),
	 (256,'/getStrada','Get strada','ADMIN   ','2026-01-23 16:03:19.432415','ADMIN   ','2026-01-23 16:03:19.432415'),
	 (257,'/getCivico','Get Civico','ADMIN   ','2026-01-23 16:03:19.461643','ADMIN   ','2026-01-23 16:03:19.461643'),
	 (258,'/getListaValoriTipoStatoFinanziamento','Get ListaValoriTipoStatoFinanziamento','ADMIN   ','2026-01-23 16:03:19.493703','ADMIN   ','2026-01-23 16:03:19.493703'),
	 (259,'/getListaValoriDefault','Get ListaValoriDefault','ADMIN   ','2026-01-23 16:03:19.523359','ADMIN   ','2026-01-23 16:03:19.523359'),
	 (260,'/getAllStatiBandoENonChiusi','Get AllStatiBandoENonChiusi','ADMIN   ','2026-01-23 16:03:19.555919','ADMIN   ','2026-01-23 16:03:19.555919');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (261,'/getAllTipoFinanziamentoContabile','Get AllTipoFinanziamentoContabile','ADMIN   ','2026-01-23 16:03:19.586827','ADMIN   ','2026-01-23 16:03:19.586827'),
	 (262,'/ricercaTipoFinanziamentoContabileAutocomplete','Ricerca TipoFinanziamentoContabileAutocomplete','ADMIN   ','2026-01-23 16:03:19.616858','ADMIN   ','2026-01-23 16:03:19.616858'),
	 (263,'/getListaValoriTipologiaIndicatore','Lista Valori TipologiaIndicatore','ADMIN   ','2026-01-23 16:03:19.6509','ADMIN   ','2026-01-23 16:03:19.6509'),
	 (264,'/getListaValoriUnitaDiMisura','Lista ValoriUnitaDiMisura','ADMIN   ','2026-01-23 16:03:19.681832','ADMIN   ','2026-01-23 16:03:19.681832'),
	 (265,'/getListaValoriFrequenzaMisurazione','Lista ValoriFrequenzaMisurazione','ADMIN   ','2026-01-23 16:03:19.728515','ADMIN   ','2026-01-23 16:03:19.728515'),
	 (266,'/getListaValoriTipoControlloTarget','Lista Valori TipoControlloTarget','ADMIN   ','2026-01-23 16:03:19.764736','ADMIN   ','2026-01-23 16:03:19.764736'),
	 (267,'/ricercaTipoIndicatoreAutocomplete','Ricerca Tipo indicatore','ADMIN   ','2026-01-23 16:03:19.801105','ADMIN   ','2026-01-23 16:03:19.801105'),
	 (268,'/cancellaTarget','Cancella Target','ADMIN   ','2026-01-23 16:03:19.834942','ADMIN   ','2026-01-23 16:03:19.834942'),
	 (269,'/cancellaMisurazione','Cancella Misurazione','ADMIN   ','2026-01-23 16:03:19.866073','ADMIN   ','2026-01-23 16:03:19.866073'),
	 (270,'/exportIndicatori','Export Indicatori','ADMIN   ','2026-01-23 16:03:19.895782','ADMIN   ','2026-01-23 16:03:19.895782');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (271,'/ricercaScadenza','ricerca Scadenza','ADMIN   ','2026-01-23 16:03:19.927762','ADMIN   ','2026-01-23 16:03:19.927762'),
	 (272,'/getListaValoriTipologiaContratto','Ricerca TipologiaContratto','ADMIN   ','2026-01-23 16:03:19.957238','ADMIN   ','2026-01-23 16:03:19.957238'),
	 (273,'/getListaValoriFasiContratto','Ricerca FasiContratto','ADMIN   ','2026-01-23 16:03:19.984972','ADMIN   ','2026-01-23 16:03:19.984972'),
	 (274,'/getListaValoriFasiContrattoDettaglio','Ricerca FasiContrattoDettaglio','ADMIN   ','2026-01-23 16:03:20.020774','ADMIN   ','2026-01-23 16:03:20.020774'),
	 (275,'/getListaValoriTipoDibattitoPubblico','Ricerca TipoDibattitoPubblico','ADMIN   ','2026-01-23 16:03:20.051145','ADMIN   ','2026-01-23 16:03:20.051145'),
	 (276,'/moduloProgetti','modulo Progetti','ADMIN   ','2026-01-23 16:03:20.082377','ADMIN   ','2026-01-23 16:03:20.082377'),
	 (277,'/moduloAmministrazione','modulo Amministrazione','ADMIN   ','2026-01-23 16:03:20.108902','ADMIN   ','2026-01-23 16:03:20.108902'),
	 (278,'/moduloTrasparenza','modulo Trasparenza','ADMIN   ','2026-01-23 16:03:20.144901','ADMIN   ','2026-01-23 16:03:20.144901'),
	 (279,'/caricaDocumento','carica Documento Contratto','ADMIN   ','2026-01-23 16:03:20.17739','ADMIN   ','2026-01-23 16:03:20.17739'),
	 (280,'/scaricaDocumento','scarica Documento Contratto','ADMIN   ','2026-01-23 16:03:20.206017','ADMIN   ','2026-01-23 16:03:20.206017');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (281,'/getTipoDocumentoPubTut','TipoDocumentoPubTut','ADMIN   ','2026-01-23 16:03:20.236933','ADMIN   ','2026-01-23 16:03:20.236933'),
	 (282,'/getTipoDocumentoAffTut','TipoDocumentoAffTut','ADMIN   ','2026-01-23 16:03:20.264675','ADMIN   ','2026-01-23 16:03:20.264675'),
	 (283,'/getTipoDocumentoEseTut','TipoDocumentoEseTut','ADMIN   ','2026-01-23 16:03:20.294139','ADMIN   ','2026-01-23 16:03:20.294139'),
	 (284,'/getTipoDocumentoTipPar','TipoDocumentoTipPar','ADMIN   ','2026-01-23 16:03:20.326151','ADMIN   ','2026-01-23 16:03:20.326151'),
	 (285,'/exportContratti','Export dei Contratti','ADMIN   ','2026-01-23 16:03:20.3901','ADMIN   ','2026-01-23 16:03:20.3901'),
	 (286,'/getDatiBdncp','Get dati da Bdncp','ADMIN   ','2026-01-23 16:03:20.425165','ADMIN   ','2026-01-23 16:03:20.425165'),
	 (287,'/ricercaContrattoAutocomplete','Contratto ricerca con autocomplete','ADMIN   ','2026-01-23 16:03:20.452274','ADMIN   ','2026-01-23 16:03:20.452274'),
	 (288,'/getDocumentoFaq','Documento Faq','ADMIN   ','2026-01-23 16:03:20.482024','ADMIN   ','2026-01-23 16:03:20.482024'),
	 (289,'/getDirezioneDefault','Direzione Default','ADMIN   ','2026-01-23 16:03:20.509734','ADMIN   ','2026-01-23 16:03:20.509734'),
	 (290,'/getListaValoriRuoloEnte','Ricerca Ruolo Ente','ADMIN   ','2026-01-23 16:03:20.548879','ADMIN   ','2026-01-23 16:03:20.548879');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (291,'/cancellaProgettoDdr','Cancella Progetto Ddr','ADMIN   ','2026-01-23 16:03:20.576949','ADMIN   ','2026-01-23 16:03:20.576949'),
	 (292,'/getModuliRuolo','get Moduli Ruolo','ADMIN   ','2026-01-23 16:03:20.605887','ADMIN   ','2026-01-23 16:03:20.605887'),
	 (293,'/aggiornamentoContratti','aggiornamento Contratti','ADMIN   ','2026-01-23 16:03:20.645118','ADMIN   ','2026-01-23 16:03:20.645118'),
	 (294,'/ricercaBeneficiarioAutocomplete','ricerca Beneficiario Autocomplete','ADMIN   ','2026-01-23 16:03:20.675811','ADMIN   ','2026-01-23 16:03:20.675811'),
	 (295,'/ricercaSussidioAutocomplete','ricerca Sussidio Autocomplete','ADMIN   ','2026-01-23 16:03:20.702452','ADMIN   ','2026-01-23 16:03:20.702452'),
	 (296,'/getListaValoriModalitaAssegnazione','ListaValori ModalitaAssegnazione','ADMIN   ','2026-01-23 16:03:20.734946','ADMIN   ','2026-01-23 16:03:20.734946'),
	 (297,'/getListaValoriTitoloAttribuzione','ListaValori TitoloAttribuzione','ADMIN   ','2026-01-23 16:03:20.77073','ADMIN   ','2026-01-23 16:03:20.77073'),
	 (298,'/getTipoDocumentoSussid','Tipo Documento Sussidi','ADMIN   ','2026-01-23 16:03:20.803957','ADMIN   ','2026-01-23 16:03:20.803957'),
	 (299,'/exportSussidi','Export dei Sussidi','ADMIN   ','2026-01-23 16:03:20.835993','ADMIN   ','2026-01-23 16:03:20.835993'),
	 (300,'/cancellaDirezioneBando','Cancella Direzione Bando','ADMIN   ','2026-01-23 16:03:20.871006','ADMIN   ','2026-01-23 16:03:20.871006');
INSERT INTO dmifonamm.amm_fun (id,nome,descrizione,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (301,'/exportBandi','export Bandi','ADMIN   ','2026-01-23 16:03:20.898915','ADMIN   ','2026-01-23 16:03:20.898915'),
	 (302,'/exportUtenti','export Utenti','ADMIN   ','2026-01-23 16:03:20.929284','ADMIN   ','2026-01-23 16:03:20.929284'),
	 (303,'/getListaValoriStatoTipoFinanziamento','Lista Valori Stato Tipo Finanziamento','ADMIN   ','2026-01-23 16:03:20.956614','ADMIN   ','2026-01-23 16:03:20.956614'),
	 (304,'/getListaValoriTipoDdr','Lista Valori Tipo Ddr','ADMIN   ','2026-01-23 16:03:20.984706','ADMIN   ','2026-01-23 16:03:20.984706'),
	 (305,'/ricercaMunicipiAutocomplete','','ADMIN   ','2026-01-23 16:03:21.014337','ADMIN   ','2026-01-23 16:03:21.014337'),
	 (306,'/getListaValoriStatusIndicatore','','ADMIN   ','2026-01-23 16:03:21.042076','ADMIN   ','2026-01-23 16:03:21.042076'),
	 (307,'/cancellaProgettoCumulativo','','ADMIN   ','2026-01-23 16:03:21.072045','ADMIN   ','2026-01-23 16:03:21.072045');

INSERT INTO dmifonamm.amm_ruo (id,codruo,desruo,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (32,'profiloOI','Profilo O. I.','davide.dellagodenza','2023-02-08 10:49:10.321','davide.dellagodenza','2023-02-08 10:49:10.321'),
	 (2,'admin','Amministratori',NULL,NULL,'vitopio.prete','2022-10-21 15:05:48.76');

INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (308,2,1,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (309,2,2,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (310,2,3,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (311,2,4,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (312,2,5,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (313,2,6,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (314,2,7,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (315,2,8,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (316,2,9,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (317,2,10,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (318,2,11,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (319,2,12,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (320,2,13,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (321,2,14,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (322,2,15,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (323,2,16,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (324,2,17,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (325,2,18,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (326,2,19,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (327,2,20,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (328,2,21,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (329,2,22,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (330,2,23,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (331,2,24,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (332,2,25,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (333,2,26,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (334,2,27,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (335,2,28,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (336,2,29,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (337,2,30,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (338,2,31,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (339,2,32,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (340,2,33,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (341,2,34,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (342,2,35,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (343,2,36,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (344,2,37,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (345,2,38,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (346,2,39,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (347,2,40,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (348,2,41,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (349,2,42,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (350,2,43,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (351,2,44,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (352,2,45,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (353,2,46,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (354,2,47,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (355,2,48,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (356,2,49,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (357,2,50,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (358,2,51,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (359,2,52,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (360,2,53,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (361,2,54,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (362,2,55,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (363,2,56,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (364,2,57,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (365,2,58,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (366,2,59,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (367,2,60,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (368,2,61,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (369,2,62,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (370,2,63,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (371,2,64,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (372,2,65,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (373,2,66,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (374,2,67,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (375,2,68,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (376,2,69,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (377,2,70,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (378,2,71,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (379,2,72,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (380,2,73,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (381,2,74,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (382,2,75,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (383,2,76,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (384,2,77,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (385,2,78,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (386,2,79,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (387,2,80,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (388,2,81,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (389,2,82,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (390,2,83,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (391,2,84,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (392,2,85,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (393,2,86,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (394,2,87,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (395,2,88,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (396,2,89,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (397,2,90,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (398,2,91,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (399,2,92,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (400,2,93,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (401,2,94,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (402,2,95,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (403,2,96,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (404,2,97,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (405,2,98,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (406,2,99,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (407,2,100,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (408,2,101,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (409,2,102,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (410,2,103,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (411,2,104,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (412,2,105,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (413,2,106,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (414,2,107,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (415,2,108,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (416,2,109,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (417,2,110,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (418,2,111,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (419,2,112,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (420,2,113,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (421,2,114,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (422,2,115,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (423,2,116,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (424,2,117,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (425,2,118,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (426,2,119,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (427,2,120,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (428,2,121,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (429,2,122,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (430,2,123,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (431,2,124,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (432,2,125,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (433,2,126,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (434,2,127,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (435,2,128,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (436,2,129,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (437,2,130,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (438,2,131,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (439,2,132,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (440,2,133,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (441,2,134,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (442,2,135,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (443,2,136,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (444,2,137,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (445,2,138,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (446,2,139,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (447,2,140,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (448,2,141,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (449,2,142,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (450,2,143,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (451,2,144,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (452,2,145,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (453,2,146,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (454,2,147,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (455,2,148,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (456,2,149,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (457,2,150,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (458,2,151,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (459,2,152,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (460,2,153,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (461,2,154,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (462,2,155,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (463,2,156,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (464,2,157,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (465,2,158,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (466,2,159,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (467,2,160,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (468,2,161,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (469,2,162,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (470,2,163,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (471,2,164,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (472,2,165,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (473,2,166,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (474,2,167,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (475,2,168,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (476,2,169,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (477,2,170,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (478,2,171,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (479,2,172,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (480,2,173,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (481,2,174,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (482,2,175,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (483,2,176,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (484,2,177,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (485,2,178,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (486,2,179,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (487,2,180,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (488,2,181,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (489,2,182,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (490,2,183,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (491,2,184,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (492,2,185,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (493,2,186,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (494,2,187,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (495,2,188,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (496,2,189,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (497,2,190,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (498,2,191,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (499,2,192,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (500,2,193,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (501,2,194,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (502,2,195,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (503,2,196,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (504,2,197,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (505,2,198,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (506,2,199,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (507,2,200,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (508,2,201,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (509,2,202,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (510,2,203,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (511,2,204,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (512,2,205,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (513,2,206,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (514,2,207,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (515,2,208,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (516,2,209,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (517,2,210,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (518,2,211,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (519,2,212,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (520,2,213,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (521,2,214,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (522,2,215,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (523,2,216,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (524,2,217,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (525,2,218,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (526,2,219,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (527,2,220,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (528,2,221,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (529,2,222,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (530,2,223,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (531,2,224,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (532,2,225,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (533,2,226,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (534,2,227,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (535,2,228,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (536,2,229,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (537,2,230,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (538,2,231,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (539,2,232,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (540,2,233,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (541,2,234,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (542,2,235,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (543,2,236,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (544,2,237,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (545,2,238,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (546,2,239,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (547,2,240,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (548,2,241,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (549,2,242,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (550,2,243,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (551,2,244,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (552,2,245,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (553,2,246,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (554,2,247,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (555,2,248,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (556,2,249,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (557,2,250,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (558,2,251,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (559,2,252,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (560,2,253,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (561,2,254,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (562,2,255,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (563,2,256,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (564,2,257,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (565,2,258,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (566,2,259,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (567,2,260,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (568,2,261,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (569,2,262,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (570,2,263,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (571,2,264,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (572,2,265,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (573,2,266,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (574,2,267,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (575,2,268,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (576,2,269,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (577,2,270,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (578,2,271,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (579,2,272,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (580,2,273,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (581,2,274,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (582,2,275,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (583,2,276,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (584,2,277,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (585,2,278,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (586,2,279,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (587,2,280,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (588,2,281,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (589,2,282,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (590,2,283,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (591,2,284,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (592,2,285,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (593,2,286,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (594,2,287,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (595,2,288,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (596,2,289,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (597,2,290,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (598,2,291,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (599,2,292,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (600,2,293,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (601,2,294,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (602,2,295,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (603,2,296,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (604,2,297,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (605,2,298,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (606,2,299,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (607,2,300,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (608,2,301,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (609,2,302,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (610,2,303,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (611,2,304,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (612,2,305,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (613,2,306,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (614,2,307,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (615,32,1,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (616,32,2,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (617,32,3,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (618,32,4,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (619,32,5,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (620,32,6,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (621,32,7,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (622,32,8,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (623,32,9,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (624,32,10,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (625,32,11,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (626,32,12,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (627,32,13,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (628,32,14,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (629,32,15,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (630,32,16,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (631,32,17,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (635,32,21,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (636,32,22,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (637,32,23,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (638,32,24,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (639,32,25,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (640,32,26,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (641,32,27,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (642,32,28,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (643,32,29,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (644,32,30,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (645,32,31,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (646,32,32,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (647,32,33,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (648,32,34,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (649,32,35,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (650,32,36,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (651,32,37,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (652,32,38,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (653,32,39,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (654,32,40,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (655,32,41,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (656,32,42,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (657,32,43,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (658,32,44,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (659,32,45,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (660,32,46,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (661,32,47,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (662,32,48,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (663,32,49,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (664,32,50,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (665,32,51,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (666,32,52,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (667,32,53,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (668,32,54,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (669,32,55,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (670,32,56,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (671,32,57,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (672,32,58,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (673,32,59,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (674,32,60,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (675,32,61,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (676,32,62,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (677,32,63,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (678,32,64,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (679,32,65,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (680,32,66,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (681,32,67,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (682,32,68,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (683,32,69,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (684,32,70,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (685,32,71,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (686,32,72,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (687,32,73,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (688,32,74,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (689,32,75,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (690,32,76,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (691,32,77,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (692,32,78,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (693,32,79,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (694,32,80,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (695,32,81,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (696,32,82,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (697,32,83,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (698,32,84,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (699,32,85,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (700,32,86,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (701,32,87,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (702,32,88,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (703,32,89,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (704,32,90,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (705,32,91,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (706,32,92,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (707,32,93,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (708,32,94,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (709,32,95,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (710,32,96,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (711,32,97,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (712,32,98,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (713,32,99,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (714,32,100,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (715,32,101,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (716,32,102,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (717,32,103,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (718,32,104,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (719,32,105,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (720,32,106,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (721,32,107,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (722,32,108,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (723,32,109,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (724,32,110,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (725,32,111,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (726,32,112,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (727,32,113,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (728,32,114,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (729,32,115,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (730,32,116,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (731,32,117,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (732,32,118,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (733,32,119,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (734,32,120,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (735,32,121,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (736,32,122,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (737,32,123,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (738,32,124,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (739,32,125,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (740,32,126,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (741,32,127,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (742,32,128,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (743,32,129,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (744,32,130,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (745,32,131,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (746,32,132,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (747,32,133,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (748,32,134,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (749,32,135,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (750,32,136,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (751,32,137,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (752,32,138,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (753,32,139,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (754,32,140,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (755,32,141,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (756,32,142,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (757,32,143,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (758,32,144,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (759,32,145,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (760,32,146,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (761,32,147,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (762,32,148,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (763,32,149,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (764,32,150,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (765,32,151,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (766,32,152,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (767,32,153,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (768,32,154,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (769,32,155,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (770,32,156,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (771,32,157,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (772,32,158,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (773,32,159,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (774,32,160,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (775,32,161,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (776,32,162,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (777,32,163,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (778,32,164,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (779,32,165,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (780,32,166,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (781,32,167,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (782,32,168,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (783,32,169,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (784,32,170,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (785,32,171,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (786,32,172,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (787,32,173,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (790,32,176,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (791,32,177,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (792,32,178,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (793,32,179,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (794,32,180,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (795,32,181,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (796,32,182,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (797,32,183,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (798,32,184,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (799,32,185,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (800,32,186,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (801,32,187,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (802,32,188,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (803,32,189,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (804,32,190,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (805,32,191,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (806,32,192,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (807,32,193,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (808,32,194,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (809,32,195,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (810,32,196,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (811,32,197,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (812,32,198,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (813,32,199,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (814,32,200,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (815,32,201,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (816,32,202,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (817,32,203,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (818,32,204,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (819,32,205,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (820,32,206,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (821,32,207,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (822,32,208,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (823,32,209,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (824,32,210,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (825,32,211,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (826,32,212,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (827,32,213,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (828,32,214,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (829,32,215,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (830,32,216,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (831,32,217,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (832,32,218,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (833,32,219,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (834,32,220,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (835,32,221,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (836,32,222,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (837,32,223,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (838,32,224,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (839,32,225,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (840,32,226,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (841,32,227,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (842,32,228,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (843,32,229,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (844,32,230,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (845,32,231,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (846,32,232,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (847,32,233,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (848,32,234,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (849,32,235,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (850,32,236,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (851,32,237,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (852,32,238,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (853,32,239,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (854,32,240,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (855,32,241,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (856,32,242,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (857,32,243,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (858,32,244,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (859,32,245,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (860,32,246,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (861,32,247,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (862,32,248,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (863,32,249,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (866,32,252,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (867,32,253,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (868,32,254,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (869,32,255,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (870,32,256,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (871,32,257,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (872,32,258,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (873,32,259,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (874,32,260,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (875,32,261,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (876,32,262,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (877,32,263,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (878,32,264,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (879,32,265,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (880,32,266,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (881,32,267,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (882,32,268,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (883,32,269,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (884,32,270,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (885,32,271,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (886,32,272,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (887,32,273,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (888,32,274,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (889,32,275,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (890,32,276,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (891,32,277,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (893,32,279,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (894,32,280,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (895,32,281,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (896,32,282,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (897,32,283,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (898,32,284,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (899,32,285,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (900,32,286,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (901,32,287,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (902,32,288,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (903,32,289,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (904,32,290,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (905,32,291,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (906,32,292,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (907,32,293,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (908,32,294,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (909,32,295,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (910,32,296,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (911,32,297,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (912,32,298,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (913,32,299,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (914,32,300,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (915,32,301,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');
INSERT INTO dmifonamm.amm_per (id,id_ruo,id_fun,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (916,32,302,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (917,32,303,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (918,32,304,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (919,32,305,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (920,32,306,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224'),
	 (921,32,307,'ADMIN   ','2026-01-23 16:03:21.100224','ADMIN   ','2026-01-23 16:03:21.100224');

INSERT INTO dmifonamm.amm_ute (id,username,nome,cognome,email,emailalt,usr_create,dt_create,usr_lstupd,dt_lstupd,abilitato) VALUES
	 (108,'achille.rossi','Achille','Rossi','','',NULL,NULL,NULL,NULL,true);

INSERT INTO dmifonamm.amm_uteruo (id,id_ute,id_ruo,flgdef,tipcondat,id_dir,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (93,108,32,'S','C',2,NULL,NULL,'davide.dellagodenza','2023-11-30 07:58:38.115'),
	 (132,108,2,'N','C',1,NULL,NULL,'davide.dellagodenza','2023-11-30 07:58:38.115');

INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (1,'AVAFASINT','In progettazione','ADMIN   ','2022-10-07 16:50:01.328696','ADMIN   ','2022-10-07 16:50:01.328696',NULL,0,NULL,NULL),
	 (2,'AVAFASINT','Gara','ADMIN   ','2022-10-07 16:50:01.383832','ADMIN   ','2022-10-07 16:50:01.383832',NULL,0,NULL,NULL),
	 (3,'AVAFASINT','Lavori in corso','ADMIN   ','2022-10-07 16:50:01.421655','ADMIN   ','2022-10-07 16:50:01.421655',NULL,0,NULL,NULL),
	 (4,'AVAFASINT','Concluso','ADMIN   ','2022-10-07 16:50:01.541964','ADMIN   ','2022-10-07 16:50:01.541964',NULL,0,NULL,NULL),
	 (160,'RUOENT','Lead','ADMIN   ','2024-05-13 15:40:38.973114','ADMIN   ','2024-05-13 15:40:38.973114','',0,NULL,NULL),
	 (161,'RUOENT','Partner','ADMIN   ','2024-05-13 15:40:39.057206','ADMIN   ','2024-05-13 15:40:39.057206','',0,NULL,NULL),
	 (16,'AVASTAANT','Non prevista','ADMIN   ','2023-01-04 11:58:23.59181','ADMIN   ','2023-01-04 11:58:23.59181',NULL,0,NULL,NULL),
	 (17,'AVASTAANT','Da richiedere','ADMIN   ','2023-01-04 11:58:23.695533','ADMIN   ','2023-01-04 11:58:23.695533',NULL,0,NULL,NULL),
	 (18,'AVASTAANT','Richiesta','ADMIN   ','2023-01-04 11:58:23.735614','ADMIN   ','2023-01-04 11:58:23.735614',NULL,0,NULL,NULL),
	 (19,'AVASTAANT','Incassata','ADMIN   ','2023-01-04 11:58:23.843735','ADMIN   ','2023-01-04 11:58:23.843735',NULL,0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (41,'AVAFASINT','prova','davide.dellagodenza','2023-07-28 07:34:28.978','davide.dellagodenza','2023-07-28 07:34:28.978',NULL,0,NULL,NULL),
	 (42,'TIPSTAFIN','Finanziato','ADMIN   ','2023-10-02 09:34:37.720623','ADMIN   ','2023-10-02 09:34:37.720623','A',0,NULL,NULL),
	 (43,'TIPSTAFIN','Candidato','ADMIN   ','2023-10-02 09:34:37.769585','ADMIN   ','2023-10-02 09:34:37.769585','P',0,NULL,NULL),
	 (44,'TIPSTAFIN','Non finanziato','ADMIN   ','2023-10-02 09:34:37.796966','ADMIN   ','2023-10-02 09:34:37.796966','N',0,NULL,NULL),
	 (45,'TIPSTAFIN','Candidato/Non finanziato','ADMIN   ','2023-10-02 09:34:37.830707','ADMIN   ','2023-10-02 09:34:37.830707','X',0,NULL,NULL),
	 (46,'TIPSTAFIN','Tutti','ADMIN   ','2023-10-02 09:34:37.863112','ADMIN   ','2023-10-02 09:34:37.863112','T',0,NULL,NULL),
	 (20,'TIPDETCON','Pre-Finanziamento','ADMIN   ','2023-08-17 15:24:38.764127','ADMIN   ','2023-08-17 15:24:38.764127',NULL,0,NULL,NULL),
	 (47,'TIPOLIND','Output','ADMIN   ','2023-10-25 10:28:06.322551','ADMIN   ','2023-10-25 10:28:06.322551','',0,NULL,NULL),
	 (48,'TIPOLIND','Risultato','ADMIN   ','2023-10-25 10:28:06.36547','ADMIN   ','2023-10-25 10:28:06.36547','',0,NULL,NULL),
	 (49,'TIPOLIND','Economico','ADMIN   ','2023-10-25 10:28:06.390614','ADMIN   ','2023-10-25 10:28:06.390614','',0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (50,'TIPOLIND','Qualita''','ADMIN   ','2023-10-25 10:28:06.414509','ADMIN   ','2023-10-25 10:28:06.414509','',0,NULL,NULL),
	 (51,'UNIMIS','Numero','ADMIN   ','2023-10-25 10:28:06.439663','ADMIN   ','2023-10-25 10:28:06.439663','',0,NULL,NULL),
	 (52,'UNIMIS','Importo','ADMIN   ','2023-10-25 10:28:06.463761','ADMIN   ','2023-10-25 10:28:06.463761','',0,NULL,NULL),
	 (53,'UNIMIS','Percentuale','ADMIN   ','2023-10-25 10:28:06.487577','ADMIN   ','2023-10-25 10:28:06.487577','',0,NULL,NULL),
	 (54,'UNIMIS','MW','ADMIN   ','2023-10-25 10:28:06.511667','ADMIN   ','2023-10-25 10:28:06.511667','',0,NULL,NULL),
	 (55,'UNIMIS','MWh/anno','ADMIN   ','2023-10-25 10:28:06.537866','ADMIN   ','2023-10-25 10:28:06.537866','',0,NULL,NULL),
	 (56,'UNIMIS','Ha','ADMIN   ','2023-10-25 10:28:06.563732','ADMIN   ','2023-10-25 10:28:06.563732','',0,NULL,NULL),
	 (57,'UNIMIS','Km','ADMIN   ','2023-10-25 10:28:06.597776','ADMIN   ','2023-10-25 10:28:06.597776','',0,NULL,NULL),
	 (58,'UNIMIS','Kmq','ADMIN   ','2023-10-25 10:28:06.628535','ADMIN   ','2023-10-25 10:28:06.628535','',0,NULL,NULL),
	 (59,'UNIMIS','Mq','ADMIN   ','2023-10-25 10:28:06.65564','ADMIN   ','2023-10-25 10:28:06.65564','',0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (60,'UNIMIS','Passeggeri','ADMIN   ','2023-10-25 10:28:06.687671','ADMIN   ','2023-10-25 10:28:06.687671','',0,NULL,NULL),
	 (61,'UNIMIS','Tonn./anno','ADMIN   ','2023-10-25 10:28:06.719762','ADMIN   ','2023-10-25 10:28:06.719762','',0,NULL,NULL),
	 (62,'UNIMIS','Utenti/anno','ADMIN   ','2023-10-25 10:28:06.755596','ADMIN   ','2023-10-25 10:28:06.755596','',0,NULL,NULL),
	 (63,'FREMIS','Settimanale','ADMIN   ','2023-10-25 10:28:06.782699','ADMIN   ','2023-10-25 10:28:06.782699','',0,NULL,NULL),
	 (64,'FREMIS','Mensile','ADMIN   ','2023-10-25 10:28:06.813663','ADMIN   ','2023-10-25 10:28:06.813663','',0,NULL,NULL),
	 (65,'FREMIS','Trimestrale','ADMIN   ','2023-10-25 10:28:06.845748','ADMIN   ','2023-10-25 10:28:06.845748','',0,NULL,NULL),
	 (66,'FREMIS','Semestrale','ADMIN   ','2023-10-25 10:28:06.877738','ADMIN   ','2023-10-25 10:28:06.877738','',0,NULL,NULL),
	 (67,'FREMIS','Annuale','ADMIN   ','2023-10-25 10:28:06.905859','ADMIN   ','2023-10-25 10:28:06.905859','',0,NULL,NULL),
	 (68,'TIPCONTAR','Misur. >= Target','ADMIN   ','2023-10-25 10:28:06.939832','ADMIN   ','2023-10-25 10:28:06.939832','>=',0,NULL,NULL),
	 (69,'TIPCONTAR','Misur. <= Target','ADMIN   ','2023-10-25 10:28:06.970868','ADMIN   ','2023-10-25 10:28:06.970868','<=',0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (70,'TIPCONTAR','Misur. = Target','ADMIN   ','2023-10-25 10:28:07.001592','ADMIN   ','2023-10-25 10:28:07.001592','=',0,NULL,NULL),
	 (14,'TIPOLFAS','1. Lavori pubblici','ADMIN   ','2022-12-05 11:18:01.071535','ADMIN   ','2022-12-05 11:18:01.071535',NULL,0,NULL,NULL),
	 (15,'TIPOLFAS','2. Acquisto e realizzazione di servizi','ADMIN   ','2022-12-05 11:18:01.159714','ADMIN   ','2022-12-05 11:18:01.159714',NULL,0,NULL,NULL),
	 (76,'TIPOLFAS','3. Acquisto beni','ADMIN   ','2024-01-26 10:24:59.305615','ADMIN   ','2024-01-26 10:24:59.305615','',0,NULL,NULL),
	 (77,'TIPOLFAS','4. Servizi erogati in co-progettazione','ADMIN   ','2024-01-26 10:24:59.333347','ADMIN   ','2024-01-26 10:24:59.333347','',0,NULL,NULL),
	 (78,'TIPOLFAS','5. Aiuti de minimis','ADMIN   ','2024-01-26 10:24:59.361593','ADMIN   ','2024-01-26 10:24:59.361593','',0,NULL,NULL),
	 (79,'TIPOLFAS','6. Contributi diversi dalle attività produttive','ADMIN   ','2024-01-26 10:24:59.388481','ADMIN   ','2024-01-26 10:24:59.388481','',0,NULL,NULL),
	 (80,'TIPOLFAS','7. Selezione del personale','ADMIN   ','2024-01-26 10:24:59.418416','ADMIN   ','2024-01-26 10:24:59.418416','',0,NULL,NULL),
	 (9,'AVATIPAPP','Accordo quadro standard','ADMIN   ','2022-10-07 16:50:01.869838','ADMIN   ','2022-10-07 16:50:01.869838',NULL,0,NULL,NULL),
	 (8,'AVATIPAPP','Altro','ADMIN   ','2022-10-07 16:50:01.813943','ADMIN   ','2022-10-07 16:50:01.813943',NULL,0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (81,'AVATIPAPP','Accordi tra Enti e Amministrazioni nel set. pubb.','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (82,'AVATIPAPP','Accordo quadro complesso','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (83,'AVATIPAPP','Affidamento diretto forniture','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (84,'AVATIPAPP','Affidamento diretto lavori','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (85,'AVATIPAPP','Affidamento diretto servizi','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (86,'AVATIPAPP','Affidamento in house','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (87,'AVATIPAPP','Avviso pubblico','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (88,'AVATIPAPP','Concessione','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (89,'AVATIPAPP','Concorso di progettazione','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (90,'AVATIPAPP','Contratto di concessione e traslaz. del risch. op.','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (91,'AVATIPAPP','Convenzione CONSIP/ARIA','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (92,'AVATIPAPP','Cooperazione tra Stazioni Appaltanti','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (93,'AVATIPAPP','Dialogo competitivo con bando','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (94,'AVATIPAPP','Dialogo competitivo senza bando','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (95,'AVATIPAPP','Manifestazione d''interesse','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (96,'AVATIPAPP','Partenariato per l''innovazione','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (7,'AVACRI','Alta','ADMIN   ','2022-10-07 16:50:01.674947','ADMIN   ','2022-10-07 16:50:01.674947',NULL,4,NULL,NULL),
	 (5,'AVACRI','Media','ADMIN   ','2022-10-07 16:50:01.592021','ADMIN   ','2022-10-07 16:50:01.592021',NULL,2,NULL,NULL),
	 (97,'AVATIPAPP','Partenariato pubblico privato','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (98,'AVATIPAPP','Piattaforme telematiche di negoziazione (MEPA)','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (99,'AVATIPAPP','Procedura aperta sopra soglia','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (100,'AVATIPAPP','Procedura aperta sotto soglia','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (101,'AVATIPAPP','Procedura competitiva con negoziaz. con bando','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (102,'AVATIPAPP','Procedura competitiva con negoziaz. senza bando','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (103,'AVATIPAPP','Procedura informale-rich. prev.','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (104,'AVATIPAPP','Procedura informale-rich. prev.(solo sotto soglia)','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (105,'AVATIPAPP','Procedura negoziata con bando','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (106,'AVATIPAPP','Procedura negoziata senza bando','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (107,'AVATIPAPP','Procedura ristretta sopra soglia','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (108,'AVATIPAPP','Procedura ristretta sotto soglia','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (109,'AVATIPAPP','Scorrimento graduatoria','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (110,'AVATIPAPP','Selez. del sog. attuat. l''inter. con bando/avv pub','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (111,'AVATIPAPP','Sistemi acquisizione forniture','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (112,'AVATIPAPP','Sistemi acquisizione servizi','ADMIN   ','2024-01-30 15:12:06.757023','ADMIN   ','2024-01-30 15:12:06.757023','',0,NULL,NULL),
	 (157,'FASCONDET','finanza di progetto','ADMIN   ','2024-03-29 15:58:03.027417','ADMIN   ','2024-03-29 15:58:03.027417','FINPRO',4,'TIPCON','FINPRO'),
	 (156,'FASCONDET','proc. di somma urgenza','ADMIN   ','2024-03-29 15:58:02.995989','ADMIN   ','2024-03-29 15:58:02.995989','PROSOM',4,'TIPCON','PROSOM'),
	 (155,'FASCONDET','sponsorizzazione','ADMIN   ','2024-03-29 15:58:02.965105','ADMIN   ','2024-03-29 15:58:02.965105','SPONSO',4,'TIPCON','SPONSO'),
	 (154,'FASCONDET','esecuzione','ADMIN   ','2024-03-29 15:58:02.933922','ADMIN   ','2024-03-29 15:58:02.933922','ESETUT',3,'FASCON','ESE'),
	 (153,'FASCONDET','affidamento','ADMIN   ','2024-03-29 15:58:02.904162','ADMIN   ','2024-03-29 15:58:02.904162','AFFTUT',2,'FASCON','AFF'),
	 (152,'FASCONDET','pubblicazione','ADMIN   ','2024-03-29 15:58:02.87402','ADMIN   ','2024-03-29 15:58:02.87402','PUBTUT',1,'FASCON','PUB');
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (143,'TIPCON','lavori','ADMIN   ','2024-03-29 15:58:02.554506','ADMIN   ','2024-03-29 15:58:02.554506','LAVORI',1,'',''),
	 (144,'TIPCON','servizi','ADMIN   ','2024-03-29 15:58:02.597575','ADMIN   ','2024-03-29 15:58:02.597575','SERVIZ',2,'',''),
	 (145,'TIPCON','forniture','ADMIN   ','2024-03-29 15:58:02.643155','ADMIN   ','2024-03-29 15:58:02.643155','FORNIT',3,'',''),
	 (146,'TIPCON','sponsorizzazione','ADMIN   ','2024-03-29 15:58:02.679974','ADMIN   ','2024-03-29 15:58:02.679974','SPONSO',4,'',''),
	 (147,'TIPCON','proc. di somma urgenza','ADMIN   ','2024-03-29 15:58:02.718824','ADMIN   ','2024-03-29 15:58:02.718824','PROSOM',5,'',''),
	 (148,'TIPCON','finanza di progetto','ADMIN   ','2024-03-29 15:58:02.750225','ADMIN   ','2024-03-29 15:58:02.750225','FINPRO',4,'',''),
	 (149,'FASCON','pubblicazione','ADMIN   ','2024-03-29 15:58:02.782174','ADMIN   ','2024-03-29 15:58:02.782174','PUB',1,'',''),
	 (150,'FASCON','affidamento','ADMIN   ','2024-03-29 15:58:02.813267','ADMIN   ','2024-03-29 15:58:02.813267','AFF',2,'',''),
	 (151,'FASCON','esecuzione','ADMIN   ','2024-03-29 15:58:02.844099','ADMIN   ','2024-03-29 15:58:02.844099','ESE',3,'',''),
	 (158,'TIPDIBPUB','dibattito facoltativo','ADMIN   ','2024-03-29 15:58:03.081541','ADMIN   ','2024-03-29 15:58:03.081541','DIBFAC',1,'','');
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (159,'TIPDIBPUB','dibattito obbligatorio','ADMIN   ','2024-03-29 15:58:03.119029','ADMIN   ','2024-03-29 15:58:03.119029','DIBOBB',2,'',''),
	 (162,'MODASS','Ausili finanziari alle imprese','ADMIN   ','2024-05-28 12:09:11.822247','ADMIN   ','2024-05-28 12:09:11.822247','',1,'',''),
	 (163,'MODASS','Contributi','ADMIN   ','2024-05-28 12:09:11.870827','ADMIN   ','2024-05-28 12:09:11.870827','',2,'',''),
	 (164,'MODASS','Riconoscimento di un vantaggio economico indiretto','ADMIN   ','2024-05-28 12:09:11.907758','ADMIN   ','2024-05-28 12:09:11.907758','',3,'',''),
	 (165,'MODASS','Sovvenzioni','ADMIN   ','2024-05-28 12:09:11.9358','ADMIN   ','2024-05-28 12:09:11.9358','',4,'',''),
	 (166,'MODASS','Sponsorizzazioni finanziarie','ADMIN   ','2024-05-28 12:09:11.96661','ADMIN   ','2024-05-28 12:09:11.96661','',5,'',''),
	 (167,'MODASS','Sussidi','ADMIN   ','2024-05-28 12:09:11.994303','ADMIN   ','2024-05-28 12:09:11.994303','',1,'',''),
	 (168,'TITATT','DGR XI/2064 del 30/07/2019 - Contributo Regionale di Solidarietà 2019','ADMIN   ','2024-05-28 12:09:12.023203','ADMIN   ','2024-05-28 12:09:12.023203','',2,'',''),
	 (169,'TITATT','DGR XI/2608 del 09/12/2019','ADMIN   ','2024-05-28 12:09:12.052578','ADMIN   ','2024-05-28 12:09:12.052578','',3,'',''),
	 (170,'TITATT','DGR XI/3035 del 06/04/2020 - Contributo Regionale di Solidarietà 2020','ADMIN   ','2024-05-28 12:09:12.080702','ADMIN   ','2024-05-28 12:09:12.080702','',4,'','');
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (171,'TITATT','Legge n. 104/1992','ADMIN   ','2024-05-28 12:09:12.117765','ADMIN   ','2024-05-28 12:09:12.117765','',5,'',''),
	 (172,'TITATT','Legge n. 23/1996','ADMIN   ','2024-05-28 12:09:12.152359','ADMIN   ','2024-05-28 12:09:12.152359','',6,'',''),
	 (173,'TITATT','Legge n. 285/1997','ADMIN   ','2024-05-28 12:09:12.18383','ADMIN   ','2024-05-28 12:09:12.18383','',7,'',''),
	 (174,'TITATT','Legge regionale n. 12/2005','ADMIN   ','2024-05-28 12:09:12.219465','ADMIN   ','2024-05-28 12:09:12.219465','',8,'',''),
	 (175,'TITATT','Legge regionale n. 19/2007','ADMIN   ','2024-05-28 12:09:12.249649','ADMIN   ','2024-05-28 12:09:12.249649','',9,'',''),
	 (176,'TITATT','Legge regionale n. 27/2009','ADMIN   ','2024-05-28 12:09:12.283542','ADMIN   ','2024-05-28 12:09:12.283542','',10,'',''),
	 (177,'TITATT','Legge regionale n. 16/2016','ADMIN   ','2024-05-28 12:09:12.318785','ADMIN   ','2024-05-28 12:09:12.318785','',11,'',''),
	 (178,'TITATT','Regolamento Canone Unico Patrimoniale e Canone di Concessione dei Mercati','ADMIN   ','2024-05-28 12:09:12.364657','ADMIN   ','2024-05-28 12:09:12.364657','',12,'',''),
	 (179,'TITATT','Regolamento per gli interventi e servizi sociali','ADMIN   ','2024-05-28 12:09:12.405632','ADMIN   ','2024-05-28 12:09:12.405632','',13,'',''),
	 (180,'TITATT','Regolamento Comunale per la concessione di contributi ed altre erogazioni economiche','ADMIN   ','2024-05-28 12:09:12.441104','ADMIN   ','2024-05-28 12:09:12.441104','',14,'','');
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (181,'TITATT','Regolamento Immobili di proprietà comunale: disciplina per l''uso e l''assegnazione','ADMIN   ','2024-05-28 12:09:12.469733','ADMIN   ','2024-05-28 12:09:12.469733','',15,'',''),
	 (182,'TITATT','Statuto dell''ente/associazione','ADMIN   ','2024-05-28 12:09:12.501803','ADMIN   ','2024-05-28 12:09:12.501803','',16,'',''),
	 (183,'TITATT','Regolamento della Disciplina generale dei rapporti tra il Comune di Milano ed Enti del Terzo Settore','ADMIN   ','2024-05-28 12:10:57.229244','ADMIN   ','2024-05-28 12:10:57.229244','',17,'',''),
	 (184,'FASCONDET','sussidio','ADMIN   ','2024-05-28 14:14:04.504623','ADMIN   ','2024-05-28 14:14:04.504623','SUSSID',1,'',''),
	 (185,'TIPOLFAS','8. Accordo tra PPAA ai sensi dell''art. 15 L. 241/90','ADMIN   ','2024-07-29 08:42:58.389253','ADMIN   ','2024-07-29 08:42:58.389253','',0,NULL,NULL),
	 (186,'TIPOLFAS','9. Affidamento in House','ADMIN   ','2024-07-29 08:42:58.482214','ADMIN   ','2024-07-29 08:42:58.482214','',0,NULL,NULL),
	 (187,'STATIPFIN','in corso','ADMIN   ','2024-08-01 10:01:49.63691','ADMIN   ','2024-08-01 10:01:49.63691','ATT',0,NULL,NULL),
	 (188,'STATIPFIN','concluso','ADMIN   ','2024-08-01 10:01:49.674585','ADMIN   ','2024-08-01 10:01:49.674585','CHI',0,NULL,NULL),
	 (189,'STATIPFIN','revocato','ADMIN   ','2024-08-01 10:01:49.723312','ADMIN   ','2024-08-01 10:01:49.723312','ANN',0,NULL,NULL),
	 (6,'AVACRI','Medio alta','ADMIN   ','2022-10-07 16:50:01.630843','ADMIN   ','2022-10-07 16:50:01.630843',NULL,3,NULL,NULL);
INSERT INTO dmifonpro.pro_lisval (id,tiplis,vallis,usr_create,dt_create,usr_lstupd,dt_lstupd,codlis,ordlis,tipliscol,codliscol) VALUES
	 (190,'AVACRI','Bassa','ADMIN   ','2024-08-19 17:06:15.219631','ADMIN   ','2024-08-19 17:06:15.219631','',1,NULL,NULL),
	 (195,'RUOENT','Partner Associato','ADMIN   ','2024-10-23 14:12:44.631307','ADMIN   ','2024-10-23 14:12:44.631307','',0,NULL,NULL),
	 (196,'RUOENT','Lettera di supporto','ADMIN   ','2024-10-24 14:37:12.997436','ADMIN   ','2024-10-24 14:37:12.997436','',0,NULL,NULL),
	 (197,'TIPDDR','Anticipazione/acconto','ADMIN   ','2024-12-04 12:58:00.542569','davide.dellagodenza','2025-02-10 11:41:45.89','A',0,NULL,NULL),
	 (201,'TIPDETCON','Libero','ADMIN   ','2025-02-13 14:56:11.44797','ADMIN   ','2025-02-13 14:56:11.44797','',0,NULL,NULL),
	 (202,'TIPDETCON','Avanzo','ADMIN   ','2025-02-13 14:56:11.44797','ADMIN   ','2025-02-13 14:56:11.44797','',0,NULL,NULL),
	 (194,'STAIND','in ritardo','ADMIN   ','2024-08-27 15:06:59.877817','ADMIN   ','2024-08-27 15:06:59.877817','',3,NULL,NULL),
	 (193,'STAIND','completato','ADMIN   ','2024-08-27 15:06:59.85191','ADMIN   ','2024-08-27 15:06:59.85191','',4,NULL,NULL),
	 (192,'STAIND','in corso','ADMIN   ','2024-08-27 15:06:59.819817','ADMIN   ','2024-08-27 15:06:59.819817','',2,NULL,NULL),
	 (191,'STAIND','da avviare','ADMIN   ','2024-08-27 15:06:59.778823','ADMIN   ','2024-08-27 15:06:59.778823','',1,NULL,NULL);

INSERT INTO dmifonpro.pro_staban (id,dessta,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (1,'Attivo','ADMIN   ','2022-09-15 09:34:50.528055','ADMIN   ','2022-09-15 09:34:50.528055'),
	 (2,'Bando non ancora pubblicato','ADMIN   ','2022-09-15 09:34:50.684739','ADMIN   ','2022-09-15 09:34:50.684739'),
	 (3,'Non di interesse per il Comune','ADMIN   ','2022-09-15 09:34:50.811811','ADMIN   ','2022-09-15 09:34:50.811811'),
	 (4,'Scaduto','ADMIN   ','2022-09-15 09:34:50.908964','ADMIN   ','2022-09-15 09:34:50.908964'),
	 (5,'Attivita'' chiuse','ADMIN   ','2023-10-04 12:54:02.813941','ADMIN   ','2023-10-04 12:54:02.813941');

INSERT INTO dmifonpro.pro_stafin (id,dessta,usr_create,dt_create,usr_lstupd,dt_lstupd,tipsta,ordsta) VALUES
	 (1,'1. Candidato','ADMIN   ','2022-07-28 17:00:07.477039','ADMIN   ','2022-07-28 17:00:07.477039','P',0),
	 (2,'2. In fase di valutazione post candidatura','ADMIN   ','2022-07-28 17:00:07.503024','ADMIN   ','2022-07-28 17:00:07.503024','P',0),
	 (3,'3. Approvato','ADMIN   ','2022-07-28 17:00:07.529124','ADMIN   ','2022-07-28 17:00:07.529124','A',0),
	 (5,'4. Ammesso al finanziamento','ADMIN   ','2022-07-28 17:00:07.579306','ADMIN   ','2022-07-28 17:00:07.579306','A',0),
	 (6,'5. In corso (o in fase di realizzazione)','ADMIN   ','2023-03-06 11:26:26.580035','ADMIN   ','2023-03-06 11:26:26.580035','A',0),
	 (8,'6. Concluso','ADMIN   ','2023-03-06 11:26:26.655958','ADMIN   ','2023-03-06 11:26:26.655958','A',0),
	 (4,'7. Non finanziato','ADMIN   ','2022-07-28 17:00:07.555254','ADMIN   ','2022-07-28 17:00:07.555254','N',0),
	 (9,'8. Annullato/Revocato','ADMIN   ','2023-03-06 11:26:26.691842','ADMIN   ','2023-03-06 11:26:26.691842','N',0);

INSERT INTO dmifonpro.pro_stapro (id,dessta,usr_create,dt_create,usr_lstupd,dt_lstupd,tipsta,ordsta) VALUES
	 (2,'2. Gara/Avviso/Co-Progettazione','ADMIN   ','2022-07-28 16:58:13.287234','ADMIN   ','2022-07-28 16:58:13.287234','A',0),
	 (7,'3. Progettazione post agg. o In conseg./Da avviare','ADMIN   ','2023-05-16 10:53:15.507992','ADMIN   ','2023-05-16 10:53:15.507992','A',0),
	 (3,'4. Lavori in corso/Esecuz. ser. o for./Erog. contr','ADMIN   ','2022-07-28 16:58:13.338472','ADMIN   ','2022-07-28 16:58:13.338472','A',0),
	 (4,'5. Lavori Terminati/Chiusura attività/Fornitura','ADMIN   ','2022-07-28 16:58:13.364345','ADMIN   ','2022-07-28 16:58:13.364345','A',0),
	 (6,'7. Cancellato','ADMIN   ','2022-07-28 16:58:13.414443','ADMIN   ','2022-07-28 16:58:13.414443','N',0),
	 (9,'6. Sospeso','ADMIN   ','2024-01-26 11:09:33.294864','ADMIN   ','2024-01-26 11:09:33.294864','N',0),
	 (1,'1. Progettazione/Selezione','ADMIN   ','2022-07-28 16:58:13.25927','ADMIN   ','2022-07-28 16:58:13.25927','A',0);

INSERT INTO dmifonpro.pro_tipfas (id,desfas,tipcon,ordfas,usr_create,dt_create,usr_lstupd,dt_lstupd,tipfas) VALUES
	 (4,'LP - DIP (Documento di indirizzo alla progettaz.)','N',2,'ADMIN   ','2022-10-07 17:05:03.718596','ADMIN   ','2022-10-07 17:05:03.718596','P'),
	 (3,'LP - PFTE (Progetto di fattib. tecnico economica)','N',3,'ADMIN   ','2022-10-07 17:05:03.515605','ADMIN   ','2022-10-07 17:05:03.515605','P'),
	 (7,'LP - Progettazione esecutiva','N',6,'ADMIN   ','2022-10-07 17:05:03.820562','ADMIN   ','2022-10-07 17:05:03.820562','P'),
	 (8,'LP - Conferenza dei servizi','N',4,'ADMIN   ','2022-10-07 17:05:03.868764','ADMIN   ','2022-10-07 17:05:03.868764','P'),
	 (9,'LP - Predisposizione capitolato e bando di gara','N',7,'ADMIN   ','2022-10-07 17:05:03.909706','ADMIN   ','2022-10-07 17:05:03.909706','G'),
	 (10,'LP - Pubblicazione bando di gara','N',8,'ADMIN   ','2022-10-07 17:05:03.94442','ADMIN   ','2022-10-07 17:05:03.94442','G'),
	 (11,'LP - Aggiudicazione','N',9,'ADMIN   ','2022-10-07 17:05:03.979168','ADMIN   ','2022-10-07 17:05:03.979168','G'),
	 (12,'LP - Stipula contratto/convenzione/accordo','N',10,'ADMIN   ','2022-10-07 17:05:04.021523','ADMIN   ','2022-10-07 17:05:04.021523','G'),
	 (13,'LP - Esecuzione lavori','N',12,'ADMIN   ','2022-10-07 17:05:04.068178','ADMIN   ','2022-10-07 17:05:04.068178','L'),
	 (14,'LP - Collaudo','N',13,'ADMIN   ','2022-12-05 12:17:58.14345','ADMIN   ','2022-12-05 12:17:58.14345','C');
INSERT INTO dmifonpro.pro_tipfas (id,desfas,tipcon,ordfas,usr_create,dt_create,usr_lstupd,dt_lstupd,tipfas) VALUES
	 (33,'LP - Cantiere avviato','N',11,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','L'),
	 (15,'SER - Progettazione','N',3,'ADMIN   ','2023-03-06 11:33:42.559512','ADMIN   ','2023-03-06 11:33:42.559512','P'),
	 (16,'SER - Procedura di aff. e indiv. del soggetto att.','N',4,'ADMIN   ','2023-03-06 11:33:42.598409','ADMIN   ','2023-03-06 11:33:42.598409','G'),
	 (1,'LP - DOC.F.A.P. (Doc. di fattib. delle alt. prog.)','N',1,'ADMIN   ','2022-10-07 17:05:03.375664','ADMIN   ','2022-10-07 17:05:03.375664','P'),
	 (17,'SER - Realizzazione del servizio','N',7,'ADMIN   ','2023-03-06 11:33:42.641431','ADMIN   ','2023-03-06 11:33:42.641431','L'),
	 (18,'SER - Collaudo/chiusura attività','N',8,'ADMIN   ','2023-03-06 11:33:42.681476','ADMIN   ','2023-03-06 11:33:42.681476','C'),
	 (5,'LP - Progetto definitivo (D.lgs 50/2016)','N',5,'ADMIN   ','2022-10-07 17:05:03.750145','ADMIN   ','2022-10-07 17:05:03.750145','P'),
	 (34,'SER - DOC.F.A.P. (Documento di fat. alter. prog.)','N',1,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','P'),
	 (35,'SER - DIP (Documento di indirizzo alla proget.)','N',2,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','P'),
	 (36,'SER - Aggiudicazione','N',5,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G');
INSERT INTO dmifonpro.pro_tipfas (id,desfas,tipcon,ordfas,usr_create,dt_create,usr_lstupd,dt_lstupd,tipfas) VALUES
	 (37,'SER - Stipula contratto/convenzione/accordo','N',6,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (38,'FOR - DOC.F.A.P. (Documento di fat. alter. prog.)','N',1,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','P'),
	 (39,'FOR - DIP (Documento di indirizzo alla proget.)','N',2,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','P'),
	 (40,'FOR - Progettazione','N',3,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','P'),
	 (41,'FOR - Procedura di affidamento e ind. del sog. att','N',4,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (42,'FOR - Aggiudicazione','N',5,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (43,'FOR - Stipula contratto/convenzione/accordo','N',6,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (44,'FOR - Esecuzione fornitura','N',7,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','L'),
	 (45,'FOR - Collaudo/chiusura fornitura','N',8,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','C'),
	 (46,'CO-P - Avvio procedura evidenza pubblica','N',1,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G');
INSERT INTO dmifonpro.pro_tipfas (id,desfas,tipcon,ordfas,usr_create,dt_create,usr_lstupd,dt_lstupd,tipfas) VALUES
	 (47,'CO-P - Selezione pubblica(soggetti amm. alla co-p)','N',2,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (48,'CO-P - Tavolo di co-progettazione','N',3,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (49,'CO-P - Elaborazione progetto definitivo','N',4,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (50,'CO-P - Convenzione','N',5,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (51,'CO-P - Avvio/realizzazione attività','N',6,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','L'),
	 (52,'CO-P - Chiusura attività','N',7,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','C'),
	 (53,'A-CON - Avviso in pubblicazione','N',1,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (54,'A-CON - Avviso pubblicato','N',2,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (55,'A-CON - Graduatoria pubblicata','N',3,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (56,'A-CON - Aggiudicazione','N',4,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G');
INSERT INTO dmifonpro.pro_tipfas (id,desfas,tipcon,ordfas,usr_create,dt_create,usr_lstupd,dt_lstupd,tipfas) VALUES
	 (57,'A-CON - Stipula contratto/convenzione/accordo','N',5,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (58,'A-CON - Realizzazione attività','N',6,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','L'),
	 (59,'A-CON - Chiusura attività','N',7,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','C'),
	 (60,'PERS - Pubblicazione avviso','N',1,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (61,'PERS - Valutazione candidature e approv. graduat.','N',2,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (62,'PERS - Stipula contratto/ordine di servizio','N',3,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','G'),
	 (63,'PERS - Realizzazione attività','N',4,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','L'),
	 (64,'PERS - Chiusura attività','N',5,'ADMIN   ','2024-01-26 16:06:40.696608','ADMIN   ','2024-01-26 16:06:40.696608','C'),
	 (68,'ACCPA - Approvazione schema','N',1,'ADMIN   ','2024-07-29 08:51:05.830452','ADMIN   ','2024-07-29 08:51:05.830452','P'),
	 (69,'ACCPA - Definizione progetto','N',2,'ADMIN   ','2024-07-29 08:51:05.858511','ADMIN   ','2024-07-29 08:51:05.858511','P');
INSERT INTO dmifonpro.pro_tipfas (id,desfas,tipcon,ordfas,usr_create,dt_create,usr_lstupd,dt_lstupd,tipfas) VALUES
	 (70,'ACCPA - Sottoscrizione Accordo','N',3,'ADMIN   ','2024-07-29 08:51:05.8873','ADMIN   ','2024-07-29 08:51:05.8873','P'),
	 (71,'ACCPA - Definizione progetto esec. sog. Attuatori','N',4,'ADMIN   ','2024-07-29 08:51:05.916188','ADMIN   ','2024-07-29 08:51:05.916188','P'),
	 (72,'ACCPA - Sottoscrizione Accordo di II° livello','N',5,'ADMIN   ','2024-07-29 08:51:05.947209','ADMIN   ','2024-07-29 08:51:05.947209','G'),
	 (73,'ACCPA - Esecuzione attività','N',6,'ADMIN   ','2024-07-29 08:51:05.977307','ADMIN   ','2024-07-29 08:51:05.977307','L'),
	 (74,'ACCPA - Chiusura attività','N',7,'ADMIN   ','2024-07-29 08:51:06.00816','ADMIN   ','2024-07-29 08:51:06.00816','C'),
	 (75,'HOUSE - Affidamento incarico','N',1,'ADMIN   ','2024-07-29 08:51:06.041451','ADMIN   ','2024-07-29 08:51:06.041451','G'),
	 (76,'HOUSE - Sottoscrizione disciplinare','N',2,'ADMIN   ','2024-07-29 08:51:06.075525','ADMIN   ','2024-07-29 08:51:06.075525','G'),
	 (77,'HOUSE - Esecuzione attività','N',3,'ADMIN   ','2024-07-29 08:51:06.103249','ADMIN   ','2024-07-29 08:51:06.103249','L'),
	 (78,'HOUSE - Chiusura attività','N',4,'ADMIN   ','2024-07-29 08:51:06.136207','ADMIN   ','2024-07-29 08:51:06.136207','C');

INSERT INTO dmifonpro.pro_tipfin (id,livuno,livdue,livtre,livqua,livcin,codtipfin,destipfin,usr_create,dt_create,usr_lstupd,dt_lstupd,livsei,impdot,id_lisvalstatipfin) VALUES
	 (1,1,0,0,0,0,'EU','FINANZIAMENTO EUROPEO','ADMIN   ','2023-05-10 12:21:22.078684','davide.dellagodenza','2024-09-03 14:42:14.804',0,10000000.730,187);
	 
INSERT INTO dmifonpro.pro_tipimp (id,destipimp,flgtipimp,flgdicui,ordtipimp,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (1,'RIS. DEL FONDO','F','N',1,'ADMIN   ','2022-06-29 15:42:37.952818','ADMIN   ','2022-06-29 15:42:37.952818'),
	 (2,'RIS. ENTE','N','N',2,'ADMIN   ','2022-06-29 15:42:37.989892','ADMIN   ','2022-06-29 15:42:37.989892'),
	 (3,'ALTRE RIS. PA','N','N',3,'ADMIN   ','2022-06-29 15:42:38.027767','ADMIN   ','2022-06-29 15:42:38.027767'),
	 (4,'ALTRE RIS. PRIV.','N','N',4,'ADMIN   ','2022-06-29 15:42:38.058134','ADMIN   ','2022-06-29 15:42:38.058134'),
	 (5,'ALTRI FIN.','N','N',5,'ADMIN   ','2022-06-29 15:42:38.09081','ADMIN   ','2022-06-29 15:42:38.09081'),
	 (6,'RIS. PRO. DEL FON.','N','S',6,'ADMIN   ','2022-06-29 15:42:38.12366','ADMIN   ','2022-06-29 15:42:38.12366'),
	 (7,'PREMIALITA''','N','S',7,'ADMIN   ','2022-06-29 15:42:38.19467','ADMIN   ','2022-06-29 15:42:38.19467'),
	 (8,'COVID','N','S',8,'ADMIN   ','2022-06-29 15:42:38.230709','ADMIN   ','2022-06-29 15:42:38.230709'),
	 (9,'LEGALITA''','N','S',9,'ADMIN   ','2022-06-29 15:42:38.291751','ADMIN   ','2022-06-29 15:42:38.291751'),
	 (10,'RIMODUL.','N','S',10,'ADMIN   ','2022-06-29 15:42:38.327598','ADMIN   ','2022-06-29 15:42:38.327598');
INSERT INTO dmifonpro.pro_tipimp (id,destipimp,flgtipimp,flgdicui,ordtipimp,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (11,'POC','N','S',11,'ADMIN   ','2022-09-30 13:23:58.981475','ADMIN   ','2022-09-30 13:23:58.981475'),
	 (12,'POC DI CUI LIB.','N','S',12,'ADMIN   ','2022-09-30 13:23:59.154263','ADMIN   ','2022-09-30 13:23:59.154263'),
	 (13,'PON','N','S',13,'ADMIN   ','2022-09-30 13:23:59.246594','ADMIN   ','2022-09-30 13:23:59.246594'),
	 (14,'STIMA AUM. PREZZI','N','S',14,'ADMIN   ','2022-09-30 13:23:59.282609','ADMIN   ','2022-09-30 13:23:59.282609'),
	 (15,'IPOTESI ECONOMIA','N','S',15,'ADMIN   ','2022-09-30 13:23:59.318381','ADMIN   ','2022-09-30 13:23:59.318381'),
	 (16,'IPOTESI SURPLUS','N','S',16,'ADMIN   ','2022-09-30 13:23:59.35648','ADMIN   ','2022-09-30 13:23:59.35648'),
	 (17,'RISORSE DA LIBERARE','N','S',17,NULL,NULL,NULL,NULL),
	 (18,'OVERBOOKING','N','S',18,NULL,NULL,NULL,NULL),
	 (19,'FOI','N','S',19,'ADMIN   ','2023-05-18 10:39:14.892049','ADMIN   ','2023-05-18 10:39:14.892049'),
	 (27,'RIS. DEL FONDO','F','A',1,'ADMIN   ','2024-08-01 11:06:18.704834','ADMIN   ','2024-08-01 11:06:18.704834');
INSERT INTO dmifonpro.pro_tipimp (id,destipimp,flgtipimp,flgdicui,ordtipimp,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (28,'RIS. ENTE','N','A',2,'ADMIN   ','2024-08-01 11:06:18.790588','ADMIN   ','2024-08-01 11:06:18.790588'),
	 (29,'ALTRE RIS. PA','N','A',3,'ADMIN   ','2024-08-01 11:06:18.820644','ADMIN   ','2024-08-01 11:06:18.820644'),
	 (30,'ALTRE RIS. PRIV.','N','A',4,'ADMIN   ','2024-08-01 11:06:18.855438','ADMIN   ','2024-08-01 11:06:18.855438'),
	 (31,'ALTRI FIN.','N','A',5,'ADMIN   ','2024-08-01 11:06:18.888883','ADMIN   ','2024-08-01 11:06:18.888883'),
	 (32,'RISORSE SISTEMA MILANO','N','S',20,'ADMIN   ','2024-09-30 16:03:07.791567','ADMIN   ','2024-09-30 16:03:07.791567'),
	 (33,'RISORSE NON MILANO','N','S',21,'ADMIN   ','2024-09-30 16:03:07.845799','ADMIN   ','2024-09-30 16:03:07.845799');

INSERT INTO dmifonpro.pro_tipres (id,destipres,ordtipres,usr_create,dt_create,usr_lstupd,dt_lstupd) VALUES
	 (1,'RUP/RIO',1,'ADMIN   ','2022-09-20 16:17:58.665001','ADMIN   ','2022-09-20 16:17:58.665001'),
	 (2,'Referente Operativo',2,'ADMIN   ','2022-09-20 16:17:58.77897','ADMIN   ','2022-09-20 16:17:58.77897'),
	 (3,'Ref. progr./Owner proc.',3,'ADMIN   ','2022-09-20 16:17:58.917023','ADMIN   ','2022-09-20 16:17:58.917023'),
	 (4,'Dir. Sovraordinato',4,'ADMIN   ','2022-09-20 16:17:58.971045','ADMIN   ','2022-09-20 16:17:58.971045'),
	 (5,'Ref. Amministrativo',5,'ADMIN   ','2022-09-20 16:17:59.071824','ADMIN   ','2022-09-20 16:17:59.071824'),
	 (6,'Ref. Bilancio',6,'ADMIN   ','2022-09-20 16:17:59.122996','ADMIN   ','2022-09-20 16:17:59.122996');
	 

