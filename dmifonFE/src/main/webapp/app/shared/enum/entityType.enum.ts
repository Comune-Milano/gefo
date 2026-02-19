export enum EntityTypeEnum {
    PROTIPFIN = "ProTipFin",
    AMMDIR = "AmmDir",
    MACPRO = "ProMacpro",
    PRO = "ProPro",
    BAN = "ProBan"    
}

export const EntityTypeLabelMapping: Record<EntityTypeEnum, string> = {
    [EntityTypeEnum.PROTIPFIN]: "Tipo Finanziamento",
    [EntityTypeEnum.AMMDIR]: "Direzione",
    [EntityTypeEnum.MACPRO]: "Macro Progetto",
    [EntityTypeEnum.PRO]: "Progetto",
    [EntityTypeEnum.BAN]: "Bando"    
};