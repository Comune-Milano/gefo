package it.dmi.dmifonbe.model;

import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;

public enum GerarchiaSemafori {
    BIANCO(4),
    ROSSO(3),
    GIALLO(2),
    VERDE(1);

    private Integer value;

    GerarchiaSemafori(int value) {
        this.value = value;
    }

    public static Integer assegnaValoreColore(String colore) throws MicroServicesException {
        switch (colore) {
            case "bianco":
                return GerarchiaSemafori.BIANCO.getValue();
            case "rosso":
                return GerarchiaSemafori.ROSSO.getValue();
            case "giallo":
                return GerarchiaSemafori.GIALLO.getValue();
            case "verde":
                return GerarchiaSemafori.VERDE.getValue();
            default:
                throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        }
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
