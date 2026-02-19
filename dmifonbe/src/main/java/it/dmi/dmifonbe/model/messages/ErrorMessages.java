package it.dmi.dmifonbe.model.messages;

public enum ErrorMessages {
    BAD_REQUEST(400, "Richiesta non corretta"),
    NO_DATA_FOUND(500, "La ricerca non ha prodotto risultati"),
    EMAIL_ERROR(201, "Errore nell'invio delle mail di notifica. Le modifiche richieste sono state comunque apportate."),
    USER_NOT_PRESENT(500, "Utente non previsto per l'applicativo"),
    USER_NOT_CORRESPONDING(500, "L'utente che ha effettuato l'accesso non corrisponde con quello richiesto dalla chiamata"),
    NO_FUNCTION_ASSOCIATED(500, "La funzione richiamata non è associata al ruolo dell'utente"),
    USER_WITHOUT_ROLES(500, "L'utente non ha ruoli assegnati"),
    USER_NOT_ABILITATE(500, "L'utente non è abilitato"),
    PREVISION_BLOCKED(500, "Le previsioni sono bloccate. Impossibile effettuare modifiche, inserimenti, eliminazioni"),
    PREV_PRO_DATA_AlREADY_EXIST(500, "Esiste già per il progetto e la data una previsione inserita"),
    LISVAL_NO_DATA_FOUND(500, "La ricerca su LisVal non ha prodotto risultati"),
    NO_DATA_FOUND_CAP(570, "La ricerca non ha prodotto risultati"),
    NO_DATA_FOUND_PARENT_PRO(500, "La ricerca per il progetto padre specificato non ha prodotto risultati"),
    NO_DATA_FOUND_PRO(500, "La ricerca per il progetto specificato non ha prodotto risultati"),
    NO_DATA_FOUND_AVA(500, "La ricerca per l'avanzamento specificato non ha prodotto risultati"),
    NO_DATA_FOUND_DDR(500, "La ricerca per La domanda di rimborso specificata non ha prodotto risultati"),
    NO_DATA_FOUND_PROFAS(500, "La ricerca per la fase specificata non ha prodotto risultati"),
    PARAMETERS_API_NOT_FOUND(500, "Non è stato possibile recuperare i parametri per la chiamata al servizio di contabilità"),
    ACCOUNTING_API_ERROR(500, "Errore nella chiamata al servizio di contabilità"),
    TIPFIN_DELETE_NOT_ALLOWED(500, "Non è possibile eliminare il tipo finanziamento selezionato. Sono presenti dipendenze con l'entità: "),
    TIPFIN_DELETE_WITH_SONS_NOT_ALLOWED(
        500,
        "Non è possibile eliminare il tipo finanziamento selezionato. Sono presenti Tipi Finanziamento con livello più basso"
    ),
    TIPENT_NOT_ALLOWED(500, "Inserire la tipologia di entità corretta"),
    TIPIMP_NOT_DELETABLE(500, "Non puoi cancellare il dato, poichè il tipo è associato ad un importo."),
    TIPINF_NOT_DELETABLE(500, "Non puoi cancellare il dato, poichè il tipo è associato ad un' informazione aggiuntiva"),
    PROPRO_DELETE_WITH_SONS_NOT_ALLOWED(
        500,
        "Non è possibile eliminare il Progetto selezionato. Sono presenti Progetti ad esso collegati."
    ),
    PROPRO_DELETE_WITH_DDR_NOT_ALLOWED(
        500,
        "Non è possibile eliminare il Progetto selezionato. Sono presenti domande di rimborso ad esso collegate."
    ),
    PROPRO_DELETE_WITH_RIC_NOT_ALLOWED(
        500,
        "Non è possibile eliminare il Progetto selezionato. Sono presenti richieste ad esso collegate."
    ),
    PROPRO_DELETE_WITH_AVA_NOT_ALLOWED(
        500,
        "Non è possibile eliminare il Progetto selezionato. Sono presenti avanzamenti ad esso collegati."
    ),
    PROFAS_DELETE_NOT_ALLOWED(500, "Non è possibile eliminare la fase selezionata. Sono presenti milestone collegate all'entità."),
    PROAVA_DELETE_NOT_ALLOWED(500, "Non è possibile eliminare l'avanzamento selezionato. Sono presenti fasi collegate all'entità."),
    DETCON_NOT_FOUND(500, "Il dettaglio contabile specificato non è stato trovato"),
    DETCON_AlREADY_EXIST(500, "Il dettaglio contabile specificato è già esistente"),
    PROTEM_AlREADY_EXIST(500, "Il codice inserito per la tematica specificata è già esistente"),
    LIVPROTEM_AlREADY_EXIST(500, "La combinazione di livelli inserita per la tematica specificata è già esistente"),
    PROAVA_AlREADY_EXIST(500, "L'avanzamento inserito è già esistente"),
    PROAVA_PREV_VERSION_EDIT(500, "Non è possibile modificare una versione precedente dell'avanzamento"),
    PRODDR_EDIT_NO_ID(500, "Non è stato definito l'id per la domanda di rimborso da modificare"),
    PROPRE_EDIT_NO_ID(500, "Non è stato definito l'id per la Previsione da modificare"),
    ALLEGATO_EDIT_NO_ID(500, "Non è stato definito l'id per l'allegato da modificare"),
    ALLEGATO_INSERT_WITH_ID(500, "Non è possibile definire un id per l'inserimento di un nuovo allegato"),
    ALLEGATO_ALREADY_PRESENT(
        500,
        "Un allegato con lo stesso nome è già presente per il progetto, l'avanzamento o la domanda di rimborso specificata"
    ),
    ALLEGATO_EXTENSION_NOT_VALID(500, "Tipo di file non valido. E' possibile caricare file PDF, JPG, formato MS OFFICE e ZIP "),
    PRODDRA_EDIT_NO_ID(500, "Non è stato definito l'id per la domanda di rimborso aggregata da modificare"),
    PRODDR_INSERT_WITH_ID(500, "Non è possibile definire un id per una domanda di rimborso da inserire"),
    PROPRE_INSERT_WITH_ID(500, "Non è possibile definire un id per una Previsione da inserire"),
    PRODDRA_INSERT_WITH_ID(500, "Non è possibile definire un id per una domanda di rimborso aggregata da inserire"),
    PROJECT_NOT_VALID(500, "L'id del progetto specificato non è valido "),
    PRODDR_DDRA_NOT_VALID(500, "L'id della domanda di rimborso aggregata non è valido "),
    PRODDRA_DDR_PRESENT(500, "Non è possibile cancellare la domanda di rimborso aggregata. Vi sono domande di rimborso correlate"),
    WSENTE_NOT_FOUND(500, "Non è stato possibile recuperare il parametro wsEnte"),
    AUTOCOMPLETE_BLANK(400, "Il parametro autocomplete è vuoto"),
    DESSTAAVA_BLANK(500, "Il campo descrizione stato avanzamento è vuoto"),
    WSCODICEUTENTE_NOT_FOUND(500, "Non è stato possibile recuperare il parametro wsCodiceUtente"),
    WSENDPOINT_NOT_FOUND(500, "Non è stato possibile recuperare il parametro wsEndPoint"),
    APIENDPOINT_NOT_FOUND(500, "Non è stato possibile recuperare il parametro APIEndPoint"),
    NO_ID_PROVIDED(500, "Non è stato specificato nessun id per l'oggetto da modificare"),
    DELETE_NO_ID_PROVIDED(500, "Non è stato specificato nessun id per l'oggetto da eliminare"),
    QUERY_MAX_ROW_WARNING(205, "La ricerca ha prodotto troppi risultati. Affinare la ricerca."),
    USERNAME_NOT_EDITABLE(500, "L'username non può essere modificato"),
    ALLEGATO_NOT_EDITABLE(500, "L'allegato specificato non può essere modificato con i parametri passati in ingresso"),
    UPLOAD_MAXSIZE(500, "L'allegato da caricare è più grande di "),
    USER_NOT_FOUND(500, "L'utente specificato non è stato trovato"),
    COD_ALREADY_EXIST(500, "Il codice specificato è già in uso"),
    FAS_ALREADY_EXIST(500, "Le fasi per questo avanzamento sono state già create"),
    COD_IDTIPFINDA_ALREADY_EXIST(500, "Il codice specificato ed il 'Tipo Finanziamento da' sono già in uso"),
    LIVPROTIPFIN_AlREADY_EXIST(500, "La combinazione di livelli inserita per il tipo finanziamento specificata è già esistente"),
    INVALID_TIPLIV(500, "Il tipo livello specificato nel filtro non è valido"),
    TIPLIS_NOT_DELETABLE(500, "La lista valore non è cancellabile poichè è collegato ad un avanzamento."),
    TIPFAS_NOT_DELETABLE(500, "Non puoi cancellare il tipo fase se esiste una fase collegata ad esso"),
    INVALID_TIPLIS(500, "Il tipo valore specificato non è valido"),
    TIPLIS_NOT_EDITABLE(500, "Non puoi modificare il tipo valore"),
    INVALID_LIV_FILTER(500, "Non è possibile filtrare per un livello superiore al tipo livello indicato"),
    NEGATIVE_LIV_FILTER(500, "Non è possibile filtrare per un valore di livello negativo"),
    USER_ALREADY_EXIST(500, "L'username specificato è già in uso"),
    IDRICHIESTA_NOT_ALLOWED(500, "Non è possibile specificare l'id di una nuova richiesta. L'id è generato automaticamente"),
    IDRICHIESTA_REQUIRED(500, "E' necessario specificare l'id di una richiesta da modificare"),

    IDUSER_NOT_ALLOWED(500, "Non è possibile specificare l'id di un nuovo utente. L'id è generato automaticamente"),
    IDRUOLO_NOT_ALLOWED(500, "Non è possibile specificare l'id di un nuovo ruolo. L'id è generato automaticamente"),
    ID_NOT_ALLOWED(500, "Non è possibile specificare l'id di un nuovo oggetto. L'id è generato automaticamente"),
    USER_DISABLE_NOT_ALLOWED(500, "L'utente ha già configurato lo stato di abilitazione scelto"),
    AMMUTERUO_NOT_PRESENT(403, "L'associazione utente-ruolo-direzione specificata non esiste"),
    ID_AMMUTERUO_NOT_PRESENT(500, "L'id della associazione utente-ruolo-direzione specificata non esiste"),
    AMMUTERUO_ALREADY_PRESENT(500, "Associazione utente-ruolo-direzione è già presente per l'utente specificato"),
    AMMPER_NOT_PRESENT(500, "L'associazione ruolo-funzione specificata non esiste"),
    MACROPROGETTI_NOT_DELETABLE(500, "Non è possibile eliminare il Macro Progetto specificato perchè vi sono Progetti collegati ad esso"),
    MUNICIPIO_NOT_DELETABLE(500, "Non è possibile eliminare il Municipio poichè vi sono Progetti collegati ad esso"),
    MAC_BLACK_OR_NULL(500, "Non è possibile inserire il Macro Progetto perchè codice o descrizione non sono valorizzati"),
    BANDO_NOT_DELETABLE(500, "Non è possibile eliminare il Bando specificato perchè vi sono Progetti collegati ad esso"),
    TIPFINREF(500, "Il tipo finanziamento inserito non è stato trovato"),
    MUN_DESC_BLANK(500, "Il campo descrizione del municipio è obbligatorio"),
    MUN_ALREADY_EXISTS(500, "Il municipio specificato, ha la stessa descrizione di un altro"),
    TIPFAS_ALREADY_EXISTS(500, "Il tipo fase specificato, ha la stessa descrizione di un altro"),
    COD_NIL_ALREADY_EXISTS(500, "Il codice è già esistente"),
    TEMREF(500, "La tematica inserita non è stata trovata"),
    DESC_BLANK(500, "il campo descrizione è obbligatorio"),
    TIPLIS_BLANK(500, "il campo tipo valore è obbligatorio"),
    DESC_EQUALS(500, "La descrizione è uguale a quella già inserita"),
    COD_NIL_BLANK(500, "il campo codice è obbligatorio"),
    MACPROREF(500, "Il Macro Progetto inserito non è stato trovato"),
    STAPROREF(500, "Lo stato progetto inserito non è stato trovato"),
    STAFINREF(500, "Il stato finanziamento inserito non è stato trovato"),
    DIRREF(500, "La direzione inserita non è stata trovata"),
    BANREF(500, "Il bando inserito non è stato trovato"),
    MUNREF(500, "Il Municipio inserito non è stato trovato"),
    PROPADREF(500, "Il progetto padre inserito non è stato trovato"),
    TIPPROREF(500, "Il tipo progetto inserito non è corretto"),
    FLGOPEAVVREF(500, "Il flag operazioni avviate e non completato non è corretto"),
    MANDATORY_IDPRO(500, "Il campo id progetto è obbligatorio"),
    MANDATORY_TIPRES(500, "Il campo tipo responsabile è obbligatorio"),
    TIPRES_NOT_FOUND(500, "Il tipo responsabile specificato non è valido"),
    MANDATORY_CODPRO(500, "Il codice progetto è obbligatorio"),
    MANDATORY_DESPRO(500, "La descrizione del progetto è obbligatoria"),
    MANDATORY_TIPFIN(500, "Il Tipo Finanziamento è obbligatorio"),
    MANDATORY_TIPRIC(500, "Il Tipo Richiesta è obbligatorio"),
    MANDATORY_STARIC(500, "Lo Stato Richiesta è obbligatorio"),
    WRONG_STARIC(500, "Lo Stato Richiesta inserito non è corretto"),
    MANDATORY_DTASCA(500, "La Data Scadenza è obbligatoria"),
    MANDATORY_RISRIC(500, "La Risposta Richiesta è obbligatoria"),
    MANDATORY_RICUTE(500, "GLI utenti per la richiesta sono obbligatori"),
    WRONG_TIPRIC(500, "Il Tipo Richiesta non è corretto"),
    MANDATORY_DESRIC(500, "La descrizione della richiesta è obbligatoria"),
    MANDATORY_MACPRO(500, "Il Macro Progetto è obbligatorio"),
    MANDATORY_STAPRO(500, "Lo stato progetto è obbligatorio"),
    MANDATORY_STAFIN(500, "Lo stato finanziamento è obbligatorio"),
    MANDATORY_FIELD(500, "Esistono campi obbligatori vuoti"),
    MANDATORY_DIR(500, "La Direzione è obbligatoria"),
    MISMATCH_TIPLIS(500, "Incongruenza tra valore e lista di riferimento"),
    MANDATORY_CODENTCON(500, "Il codice entità contabile è obbligatorio"),
    MANDATORY_IDRIC(500, "L'id della richiesta è obbligatorio"),
    DUPLICATE_DETCONT(500, "Esistono dettagli contabili duplicati o con lo stesso codice all'interno della tabella"),
    DUPLICATE_CAP_INPUT(500, "Esistono capitoli duplicati nella lista in ingresso"),
    DUPLICATE_MILESTONE_INPUT(500, "Esistono milestone duplicate nella lista in ingresso"),
    RICERCA_ID_NOT_VALID(500, "L'id progetto specificato per la ricerca non è valido"),
    RICERCA_TIPENTCON_NOT_VALID(500, "Il tipo entità contabile specificato per la ricerca non è valido"),
    DELETE_NO_CORRECT_VERSION(500, "Si sta tentando di eliminare una versione diversa dall'ultima"),
    MODIFY_USER_DISABLE_NOT_ALLOWED(
        500,
        "Non è possibile modificare l'abilitazione utente in modalità modifica, utilizzare la funzione per abilitare o disabilitare l'utente"
    );

    private int code;
    private String userMessage;

    ErrorMessages(int code, String userMessage) {
        this.code = code;
        this.userMessage = userMessage;
    }

    public int getCode() {
        return code;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
