package org.doazz.barcode.constant;

public enum Tooltip {
    MINIMIZE("Riduci a icona"),
    CLOSE("Chiudi programma"),
    ADD("Aggiungi articolo"),
    EDIT("Modifica articolo"),
    DELETE("Elimina articolo"),
    ;

    private final String name;

    Tooltip(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
