package it.dmi.dmifonbe.model.messages;

public enum SuccessMessages {
    SUCCESS_BATCH(200, "La procedura di aggiornamento delle entità contabili si è conclusa correttamente"),
    SUCCESS_INSERT(200, "L'inserimento è avvenuto correttamente"),
    SUCCESS_INSERT_PROGETTO_FIGLIO(200, "L'inserimento del progetto figlio è avvenuto correttamente"),
    SUCCESS_INSERT_FASE(200, "L'inserimento della Fase è avvenuto correttamente"),
    SUCCESS_EDIT(200, "La modifica è avvenuta correttamente"),
    SUCCESS_DELETE(200, "L'eliminazione è avvenuta correttamente");

    private int code;
    private String userMessage;

    SuccessMessages(int code, String userMessage) {
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
