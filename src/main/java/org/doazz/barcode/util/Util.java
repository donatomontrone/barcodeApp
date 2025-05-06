package org.doazz.barcode.util;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.doazz.barcode.Main;

import java.util.Objects;

import static org.doazz.barcode.Main.getPrimaryStage;

public class Util {

    private Util() {
    }

    public static Alert showAlert(Alert.AlertType type, String title, String message, boolean isModal, ButtonType... buttons) {


        Alert alert;
        if (buttons != null) {
            alert = new Alert(type, title, buttons);
        } else {
            alert = new Alert(type, title);
        }

        alert.setWidth(300);
        alert.setHeight(150);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(false);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(Util.class.getResource("/application.css")).toExternalForm());
        dialogPane.getStyleClass().add("alert");

        alert.initStyle(StageStyle.UNDECORATED);
        for (ButtonType b : dialogPane.getButtonTypes()) {
            Button button = (Button) dialogPane.lookupButton(b);
            if (button != null) {
                if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    button.getStyleClass().add("custom-ok-button");
                }
            }
        }

        boolean hasYes = dialogPane.getButtonTypes().stream()
                .anyMatch(bt -> bt.getButtonData() == ButtonBar.ButtonData.YES);
        boolean hasNo = dialogPane.getButtonTypes().stream()
                .anyMatch(bt -> bt.getButtonData() == ButtonBar.ButtonData.NO);

        if (hasYes && hasNo) {
            Platform.runLater(() -> {
                for (ButtonType bt : dialogPane.getButtonTypes()) {
                    Button button = (Button) dialogPane.lookupButton(bt);
                    if (button != null) {
                        if (bt.getButtonData() == ButtonBar.ButtonData.YES) {
                            button.getStyleClass().add("custom-edit-button");
                        } else if (bt.getButtonData() == ButtonBar.ButtonData.NO) {
                            button.getStyleClass().add("custom-back-button");
                        }
                    }
                }
            });
        }



        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Main.openSecondaryStage(stage);
        stage.getIcons().add(new Image(Objects.requireNonNull(Util.class.getResourceAsStream("/images/icon.png"))));

        Stage primaryStage = getPrimaryStage();
        if (primaryStage != null) {
            stage.setOnShown(e -> {
                double xPosition = primaryStage.getX() + (primaryStage.getWidth() - alert.getWidth()) / 2.8;
                double yPosition = primaryStage.getY() + (primaryStage.getHeight() - alert.getHeight()) / 2;

                stage.setX(xPosition);
                stage.setY(yPosition);
            });
        }

        if (isModal) {
            alert.initModality(Modality.APPLICATION_MODAL);
        }

        return alert;
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    public static void tabCondition(Control actual, Control next, Control previous) {
        actual.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.TAB) {
                if (event.isShiftDown() && previous != null) {
                    previous.requestFocus();
                } else if (next != null) {
                    next.requestFocus();
                }
            }
        });
    }
    public static void firstOnFocus(TextField field) {
        Platform.runLater(field::requestFocus);
    }

    public static void closeWindow(Control control) {
        Stage stage = (Stage) control.getScene().getWindow();
        stage.close();
    }
}
