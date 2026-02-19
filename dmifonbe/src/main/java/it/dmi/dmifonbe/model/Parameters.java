package it.dmi.dmifonbe.model;

public enum Parameters {
    RICERCAROWSONLY("RicercaRowsOnly"),
    RICERCACAPECAPU("RicercaCapeCapu"),
    RICERCAMAND("RicercaMand"),
    RICERCAREV("RicercaReve"),
    PREVISIONEBLOCCATA("PrevisioneBloccata"),
    WSENTE("WSEnte"),
    WS("WS"),
    WSENDPOINT("WSEndPoint"),
    WSCODICEUTENTE("WSCodiceUtente"),
    RICERCAASSE("RicercaAsse"),
    RICERCASETT("RicercaSett"),
    RICERCAIMPEACCE("RicercaImpeAcce"),
    APICLIENTID("APIClientId"),
    APICLIENTSECRET("APIClientSecret"),
    APIURITOKEN("APIUriToken"),
    APIENDPOINT("APIEndPoint"),
    ACCESSTOKENKEY("access_token"),
    SEMAFORO_NON_DEFINITO("Non definito"),
    SEMAFORO_TERMINATO("Fatto"),
    SEMAFORO_DAIMPLEMENTARE("Da Fare"),
    SEMAFORO_INCORSO("In corso"),
    SEMAFORO_INSCADENZA("In scadenza"),
    TIPENTMAN("MAND"),
    TIPENTIMPE("IMPE"),
    TIPENTACCE("ACCE"),
    TIPENTCRON("CRON"),
    URI_FINAL_STRING_IMPE("impegni"),
    URI_FINAL_STRING_ACCE("accertamenti"),
    URI_FINAL_STRING_CRONO("crono"),
    URI_FINAL_STRING_CAPE("capitoliEntrata"),
    URI_FINAL_STRING_CAPU("capitoliUscita"),
    SETTORI("settori"),
    ASSESSORATI("assessorati"),
    MANDATI("mandati"),
    REVERSALI("reversali"),
    SEMAFOROGIALLOFASEPERCENTUALE("SemaforoGialloFasePercentuale"),
    SEMAFOROGIALLOMILESTONEGIORNI("SemaforoGialloMilestoneGiorni"),
    AVAFASINT("AVAFASINT"),
    AVACRI("AVACRI"),
    AVATIPAPP("AVATIPAPP"),
    AVASTAANT("AVASTAANT"),
    TIPOLFAS("TIPOLFAS"),
    CAPE("CAPE"),
    CAPU("CAPU"),
    CALCIMP("IMPE"),
    CALCACCE("ACCE"),
    PDF("JVBERi0"),
    XlsxDocxZip("UEsDBB"),
    XlsDoc("0M8R4KGxGuE"),
    JPG("/9j/4A");

    private String value;

    Parameters(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
