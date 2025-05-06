package org.doazz.barcode.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.doazz.barcode.constant.Message;
import org.doazz.barcode.constant.Title;
import org.doazz.barcode.dao.ItemDAO;
import org.doazz.barcode.model.Item;

import static org.doazz.barcode.util.Util.*;

@SuppressWarnings("unused")
public class AddFastItemController {

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
    @FXML
    private ListView<Item> itemListView;

    private final ItemDAO itemDAO = new ItemDAO();
    private String barcode;
    private HomePageController homePageController;

    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
        barcodeField.setText(barcode);
    }

    @FXML
    public void initialize() {
        firstOnFocus(nameField);
        tabCondition(nameField, priceField, cancelButton);
        tabCondition(priceField, saveButton, nameField);
        tabCondition(saveButton, cancelButton, priceField);
        cancelButton.setOnAction(e -> closeWindow(cancelButton));
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String priceText = priceField.getText();

        if (name.isEmpty() || priceText.isEmpty() || barcode.isEmpty()) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    Message.REQUIRED.getValue(), true);
            alert.showAndWait();
            return;
        }

        Item itemWithName = itemDAO.findByName(name);
        if (itemWithName != null) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    String.format(Message.ALREADY_NAME_REGISTERED.getValue(), itemWithName.getName()),
                    true);
            alert.showAndWait();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                    Message.INVALID_PRICE.getValue(), true);
            alert.showAndWait();
            return;
        }

        Item newItem = new Item(barcode, name, price);
        itemDAO.save(newItem);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

        if (homePageController != null) {
            homePageController.updateProductList();
        }
    }


    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
