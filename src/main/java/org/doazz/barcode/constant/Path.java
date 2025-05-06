package org.doazz.barcode.constant;

public enum Path {
    ICON("/images/icon.png"),;

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
