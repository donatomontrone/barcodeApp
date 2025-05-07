package org.doazz.barcode.constant;

public enum StyleClass {
    BASE_BTN("base-button"),
    ALERT("alert"),
    OK_BTN("custom-ok-button"),
    ADD_BTN("custom-add-button"),
    FAST_ADD_BTN("custom-add-fast-button"),
    EDIT_BTN("custom-edit-button"),
    CANCEL_BTN("custom-back-button"),
    DELETE_BTN("custom-delete-button"),
    TITLE_BAR("title-bar-minimal"),
    TITLE_BAR_LABEL("label-minimal"),
    TITLE_BAR_BUTTON("button-minimal"),;

    private final String name;

    StyleClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
