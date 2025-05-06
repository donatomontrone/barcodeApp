package org.doazz.barcode.constant;

public enum Title {
    APP_TITLE("Lettore di Codici a barre"),
    ADD("Aggiungi articolo"),
    NEW("Nuovo articolo"),
    ERROR("Errore"),
    WARNING("Attenzione"),
    INFO("Info"),
    ITEM_NAME("Articolo: %s"),
    EDIT("Modifica"),
    DELETE("Elimina"),
    CONFIRM_DEL("Conferma eliminazione"),
    EDIT_ITEM("Modifica articolo"),
    ITEM_LIST("Lista articoli"),
    ;

    private final String value;

    Title(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
