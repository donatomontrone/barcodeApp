package org.doazz.barcode.constant;

public enum View {
    HOME("/views/homepage.fxml"),
    ITEMS("/views/item_list.fxml"),
    ADD_FAST("/views/add_fast_item.fxml"),
    EDIT("/views/edit_item.fxml"),
    ADD("/views/add_item.fxml");

    private final String path;

    View(String path) {
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
