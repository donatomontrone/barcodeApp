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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.doazz.barcode.Main;
import org.doazz.barcode.constant.*;
import org.doazz.barcode.constant.Tooltip;
import org.doazz.barcode.dao.ItemDAO;
import org.doazz.barcode.model.Item;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.doazz.barcode.Main.getPrimaryStage;
import static org.doazz.barcode.util.Util.*;

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
                new SimpleStringProperty(String.format(EURO_FORMAT, data.getValue().getPrice())));
        loadItemList();
        addActionButtons();
    }

    @FXML
    private void addActionButtons() {
        columnAction.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button(Title.EDIT.getValue());
            private final Button deleteButton = new Button();

            {
                addTooltip(editButton, Tooltip.EDIT);
                editButton.getStyleClass().addAll(StyleClass.BASE_BTN.getName(), StyleClass.EDIT_BTN.getName());
                editButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    openEditModal(item);
                });

                addTooltip(deleteButton, Tooltip.DELETE);
                deleteButton.getStyleClass().addAll(StyleClass.BASE_BTN.getName(), StyleClass.DELETE_BTN.getName());
                Image trashIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Path.TRASH.getPath())));
                ImageView imageView = new ImageView(trashIcon);
                imageView.setFitHeight(16);
                imageView.setFitWidth(16);
                deleteButton.setGraphic(imageView);
                deleteButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());

                    ButtonType yesButton = new ButtonType(Btn.YES.getName(), ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType(Btn.NO.getName(), ButtonBar.ButtonData.NO);
                    Alert alert = showAlert(Alert.AlertType.ERROR, Title.CONFIRM_DEL.getValue(),
                            String.format(Message.CONFIRM_DELETE.getValue(), item.getName()),
                            true, getPrimaryStage(), yesButton, noButton);

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
            moveModal(scene.getRoot(), stage, getPrimaryStage());
            showModalCentered(stage, getPrimaryStage());
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
            moveModal(scene.getRoot(), stage, getPrimaryStage());
            showModalCentered(stage, getPrimaryStage());
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
