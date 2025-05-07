package org.doazz.barcode.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.doazz.barcode.constant.Message;
import org.doazz.barcode.constant.Title;
import org.doazz.barcode.dao.ItemDAO;
import org.doazz.barcode.model.Item;

import java.net.URL;
import java.util.ResourceBundle;

import static org.doazz.barcode.util.Util.*;

public class EditItemController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField barcodeField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private final ItemDAO itemDAO = new ItemDAO();
    private Item itemToEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameField.requestFocus();
        tabCondition(nameField, priceField, cancelButton);
        tabCondition(priceField, saveButton, nameField);
        tabCondition(saveButton, cancelButton, priceField);
        cancelButton.setOnAction(e -> closeWindow(saveButton));
        saveButton.setOnAction(e -> handleSave());
    }

    @FXML
    public void setItemToEdit(Item item) {
        this.itemToEdit = item;
        nameField.setText(item.getName());
        priceField.setText(String.valueOf(item.getPrice()));
        barcodeField.setText(item.getEAN());
        barcodeField.setDisable(true);
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText().trim();
        String priceStr = priceField.getText().trim();
        Window owner = saveButton.getScene().getWindow();

        if (name.isEmpty() || priceStr.isEmpty()) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    Message.REQUIRED.getValue(), true, owner);
            alert.showAndWait();
            return;
        }

        Item itemWIthName = itemDAO.findByName(name);
        if (itemWIthName != null && !itemWIthName.getEAN().equals(itemToEdit.getEAN())) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    String.format(Message.ALREADY_NAME_REGISTERED.getValue(), itemWIthName.getName()),
                    true, owner);
            alert.showAndWait();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    Message.INVALID_PRICE.getValue(), true, owner);
            alert.showAndWait();
            return;
        }

        itemToEdit.setName(name);
        itemToEdit.setPrice(price);

        try {
            itemDAO.update(itemToEdit);
            closeWindow(saveButton);
        } catch (Exception e) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    String.format(Message.SAVE_ERROR.getValue(), e.getMessage()), true, owner);
            alert.showAndWait();
        }
    }
}
