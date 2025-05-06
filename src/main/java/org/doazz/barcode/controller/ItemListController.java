package org.doazz.barcode.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.doazz.barcode.Main;
import org.doazz.barcode.constant.Message;
import org.doazz.barcode.constant.Title;
import org.doazz.barcode.constant.View;
import org.doazz.barcode.dao.ItemDAO;
import org.doazz.barcode.model.Item;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.doazz.barcode.util.Util.showAlert;

@SuppressWarnings("unused")
public class ItemListController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> columnBarcode;
    @FXML
    private TableColumn<Item, String> columnName;
    @FXML
    private TableColumn<Item, String> columnPrice;
    @FXML
    private TableColumn<Item, Void> columnAction;


    private final ItemDAO dao = new ItemDAO();


    void loadItemList() {
        ObservableList<Item> items = FXCollections.observableArrayList(dao.findAll());
        itemTable.setItems(items);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        for (TableColumn<?, ?> column : itemTable.getColumns()) {
            column.setReorderable(false);
        }
        itemTable.setFixedCellSize(40);
        columnBarcode.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEAN()));
        columnName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName()));
        columnPrice.setCellValueFactory(data ->
                new SimpleStringProperty(String.format("€ %.2f", data.getValue().getPrice())));
        loadItemList();
        addActionButtons();
    }

    @FXML
    private void addActionButtons() {
        columnAction.setCellFactory(col -> new TableCell<>() {

            private final Button editButton = new Button(Title.EDIT.getValue());
            private final Button deleteButton = new Button();

            {
                editButton.getStyleClass().addAll("base-button", "custom-edit-button");
                editButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    openEditModal(item);
                });

                // Delete button styling and icon
                deleteButton.getStyleClass().addAll("base-button", "custom-delete-button");
                Image trashIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trash.png")));
                ImageView imageView = new ImageView(trashIcon);
                imageView.setFitHeight(16);
                imageView.setFitWidth(16);
                deleteButton.setGraphic(imageView);
                deleteButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());

                    ButtonType yesButton = new ButtonType("Sì", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

                    Alert alert = showAlert(Alert.AlertType.CONFIRMATION, Title.CONFIRM_DEL.getValue(),
                            String.format(Message.CONFIRM_DELETE.getValue(), item.getName()),
                            true, yesButton, noButton);

                    alert.showAndWait().ifPresent(result -> {
                        if (result == yesButton) {
                            dao.delete(item.getEAN());
                            itemTable.getItems().setAll(dao.findAll());
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(5, editButton, deleteButton);
                    setGraphic(box);
                }
            }
        });
    }


    @FXML
    public void onAddItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(View.ADD.getPath()));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(Title.NEW.getValue());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            Main.openSecondaryStage(stage);
            stage.showAndWait();
            itemTable.getItems().setAll(dao.findAll());
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

    @FXML
    private void openEditModal(Item item) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(View.EDIT.getPath()));
            Parent root = loader.load();
            EditItemController controller = loader.getController();
            controller.setItemToEdit(item);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(Title.EDIT_ITEM.getValue());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            Main.openSecondaryStage(stage);
            stage.showAndWait();
            itemTable.getItems().setAll(dao.findAll());
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }
}
