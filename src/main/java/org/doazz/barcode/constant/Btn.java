package org.doazz.barcode.constant;

public enum Btn {
    ADD("Aggiungi"),
    BACK("Annulla");

    private final String name;

    Btn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
