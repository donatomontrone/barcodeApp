package org.doazz.barcode.constant;

public enum Message {
    NOT_FOUND("Il codice a barre %s non è presente!\nVuoi aggiungere questo articolo?"),
    NOT_OPEN("Impossibile aprire il form di aggiunta."),
    INFO("Nome: %s \nPrezzo: %s"),
    CONFIRM_DELETE("Sei sicuro di voler eliminare l'articolo: %s?"),
    BARCODE("Codice a barre  '%s' non valido"),
    ALREADY_EAN_REGISTERED("Il codice a barre: %s è già registrato!"),
    ALREADY_NAME_REGISTERED("Il nome: %s è già registrato!"),
    REQUIRED("Tutti i campi sono obbligatori!"),
    INVALID_PRICE("Il prezzo deve essere un numero valido!"),
    SAVE_ERROR("Errore durante il salvataggio: %s"),
    ADD_SUCCESS("Articolo aggiunto correttamente!"),
    ERROR_ITEM_LIST("Errore durante il caricamento della lista articoli!"),;

    private final String value;

    Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
