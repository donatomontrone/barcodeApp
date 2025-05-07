package org.doazz.barcode.constant;

public enum Btn {
    ADD("Aggiungi"),
    BACK("Annulla"),
    YES("Sì"),
    NO("No"),
    MINIMIZE("–"),
    CLOSE("✕");

    private final String name;

    Btn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
