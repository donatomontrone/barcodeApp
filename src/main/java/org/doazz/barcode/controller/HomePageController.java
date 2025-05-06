package org.doazz.barcode.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.doazz.barcode.Main;
import org.doazz.barcode.constant.Btn;
import org.doazz.barcode.constant.Message;
import org.doazz.barcode.constant.Title;
import org.doazz.barcode.constant.View;
import org.doazz.barcode.dao.ItemDAO;
import org.doazz.barcode.model.Item;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.doazz.barcode.util.Util.isNumeric;
import static org.doazz.barcode.util.Util.showAlert;

public class HomePageController implements Initializable {

    @FXML
    private Button itemButton;
    @FXML
    private Button homeButton;
    @FXML
    private TextField barcodeText;
    @FXML
    private BorderPane rootPane;
    private javafx.scene.Node originalCenter;

    private final ItemDAO itemDAO = new ItemDAO();

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        originalCenter = rootPane.getCenter();
        Platform.runLater(() -> barcodeText.requestFocus());

        homeButton.setOnAction(e -> {
            rootPane.setCenter(originalCenter);
            Platform.runLater(() -> barcodeText.requestFocus());
        });

        itemButton.setOnAction(e -> loadPage(View.ITEMS.getPath()));

        barcodeText.setOnAction(e -> {
            String code = barcodeText.getText().trim();
            if (!code.isEmpty()) {
                if (!isNumeric(code)) {
                    Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(),
                            String.format(Message.BARCODE.getValue(), code),
                            true);
                    alert.showAndWait();
                    barcodeText.clear();
                    return;
                }
                handleBarcode(code);
                barcodeText.clear();
            }
        });
    }

    @FXML
    private void handleBarcode(String barcode) {
        Item item = itemDAO.findByBarcode(barcode);
        if (item != null) {
            String title = String.format(Title.ITEM_NAME.getValue(), barcode);
            String message = String.format(Message.INFO.getValue(), item.getName(), String.format("€ %.2f", item.getPrice()));
            Alert alert = showAlert(Alert.AlertType.INFORMATION, title, message,
                    true,  ButtonType.OK);
            alert.showAndWait();
        } else {
            String message = String.format(Message.NOT_FOUND.getValue(), barcode);

            ButtonType addType = new ButtonType(Btn.ADD.getName());
            ButtonType backType = new ButtonType(Btn.BACK.getName());

            Alert alert = showAlert(Alert.AlertType.WARNING, Title.WARNING.getValue(), message,
                    true,  addType, backType);

            Button addButton = (Button) alert.getDialogPane().lookupButton(addType);
            Button backButton = (Button) alert.getDialogPane().lookupButton(backType);
            addButton.getStyleClass().addAll("base-button", "custom-add-button");
            backButton.getStyleClass().addAll("base-button", "custom-back-button");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get().getText().equals(Btn.ADD.getName())) {
                openAddItemForm(barcode);
            }
        }
    }

    @FXML
    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();
            rootPane.setCenter(newContent);
        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
    }

    @FXML
    public void updateProductList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(View.ITEMS.getPath()));
            Parent newContent = loader.load();
            rootPane.setCenter(newContent);
            ItemListController itemListController = loader.getController();
            itemListController.loadItemList();
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

    @FXML
    private void openAddItemForm(String barcode) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(View.ADD_FAST.getPath()));
            Parent root = loader.load();
            AddFastItemController controller = loader.getController();
            controller.setHomePageController(this);
            controller.setBarcode(barcode);
            stage.setTitle(Title.ADD.getValue());
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setWidth(300);
            stage.setHeight(300);
            Main.openSecondaryStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            Alert alert = showAlert(Alert.AlertType.ERROR, Title.ERROR.getValue(), Message.NOT_OPEN.getValue(), true);
            alert.showAndWait();
        }
    }

}
