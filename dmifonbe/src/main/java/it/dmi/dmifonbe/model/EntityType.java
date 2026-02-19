package it.dmi.dmifonbe.model;

public enum EntityType {
    PROTIPFIN("ProTipFin", "Tipo Finanziamento"),
    AMMDIR("AmmDir", "Direzione"),
    MACPRO("ProMacpro", "Macro Progetto"),
    PRO("ProPro", "Progetto"),
    PROTEM("ProTem", "Tematiche"),
    MUN("ProMum", "Municipio"),
    NIL("ProNil", "NIL"),
    BAN("ProBan", "Bando");

    private String value;

    private String desc;

    EntityType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
