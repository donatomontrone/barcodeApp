package org.doazz.barcode.constant;

public enum Path {
    ICON("/images/icon.png"),
    TRASH("/images/trash.png"),
    MAIN_CSS("/application.css"),
    TABLE_CSS("/table.css"),
    TITLEBAR_CSS("/title-bar.css"),;

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
