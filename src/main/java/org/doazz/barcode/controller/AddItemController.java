package org.doazz.barcode.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.doazz.barcode.constant.Message;
import org.doazz.barcode.constant.Title;
import org.doazz.barcode.dao.ItemDAO;
import org.doazz.barcode.model.Item;

import static org.doazz.barcode.util.Util.*;

public class AddItemController {

    @FXML
    private TextField barcodeField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private final ItemDAO itemDAO = new ItemDAO();

    public void initialize() {
        firstOnFocus(barcodeField);
        tabCondition(barcodeField, nameField, cancelButton);
        tabCondition(nameField, priceField, barcodeField);
        tabCondition(priceField, saveButton, nameField);
        tabCondition(saveButton, cancelButton, priceField);
        tabCondition(cancelButton, barcodeField, saveButton);
        cancelButton.setOnAction(e -> closeWindow(cancelButton));
        saveButton.setOnAction(e -> onSave());
    }

    @FXML
    public void onSave() {
            String barcode = barcodeField.getText().trim();
            String name = nameField.getText().trim();
            String price = priceField.getText().trim();

            if (barcode.isEmpty() || name.isEmpty() || priceField.getText().isEmpty()) {
                Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                        Message.REQUIRED.getValue(), true);
                alert.showAndWait();
                return;
            }

            if (!isNumeric(barcode)) {
                Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                        String.format(Message.BARCODE.getValue(), barcode),
                        true);
                alert.showAndWait();
                return;
            }

            Item itemWIthName = itemDAO.findByName(name);
            if (itemWIthName != null) {
                Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                        String.format(Message.ALREADY_NAME_REGISTERED.getValue(), itemWIthName.getName()),
                        true);
                alert.showAndWait();
                return;
            }

            Item itemWithBarcode = itemDAO.findByBarcode(barcode);
            if (itemWithBarcode != null) {
                Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                        String.format(Message.ALREADY_EAN_REGISTERED.getValue(), itemWithBarcode.getEAN()),
                        true);
                alert.showAndWait();
                return;
            }

        try {
            double priceValue = Double.parseDouble(price);
            itemDAO.save(new Item(barcode, name, priceValue));
            closeWindow(barcodeField);
        } catch (NumberFormatException e) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    Message.INVALID_PRICE.getValue(), true);
            alert.showAndWait();
        }
    }

    @FXML
    public void onCancel() {
        closeWindow(barcodeField);
    }
}
