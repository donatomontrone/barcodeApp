package org.doazz.barcode.util;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.doazz.barcode.Main;
import org.doazz.barcode.constant.Path;
import org.doazz.barcode.constant.StyleClass;

import java.util.Objects;

import static org.doazz.barcode.Main.getPrimaryStage;

public class Util {
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static final String EURO_FORMAT = "€ %.2f";

    private Util() {
    }

    public static Alert showAlert(Alert.AlertType type, String title, String message, boolean isModal, Window owner, ButtonType... buttons) {
        Alert alert;
        if (buttons != null) {
            alert = new Alert(type, title, buttons);
        } else {
            alert = new Alert(type, title);
        }

        alert.initOwner(owner);
        alert.setWidth(300);
        alert.setHeight(150);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(false);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        showModalCentered(stage, owner);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(Util.class.getResource(Path.MAIN_CSS.getPath())).toExternalForm());
        dialogPane.getStyleClass().add(StyleClass.ALERT.getName());

        moveModal(dialogPane, stage, getPrimaryStage());

        alert.initStyle(StageStyle.UNDECORATED);
        for (ButtonType b : dialogPane.getButtonTypes()) {
            Button button = (Button) dialogPane.lookupButton(b);
            if (button != null) {
                if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    button.getStyleClass().addAll(StyleClass.BASE_BTN.getName(), StyleClass.OK_BTN.getName());
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
                            button.getStyleClass().addAll(StyleClass.BASE_BTN.getName(), StyleClass.OK_BTN.getName());
                        } else if (bt.getButtonData() == ButtonBar.ButtonData.NO) {
                            button.getStyleClass().addAll(StyleClass.BASE_BTN.getName(), StyleClass.CANCEL_BTN.getName());
                        }
                    }
                }
            });
        }

        Main.openSecondaryStage(stage);

        if (isModal) {
            alert.initModality(Modality.WINDOW_MODAL);
        }

        return alert;
    }

    public static boolean isNotNumeric(String str) {
        return str == null || !str.matches("\\d+");
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

    public static void showModalCentered(Stage modal, Window owner) {
        modal.initOwner(owner);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setOnShown(e -> {
            double ownerX = owner.getX();
            double ownerY = owner.getY();
            double ownerW = owner.getWidth();
            double ownerH = owner.getHeight();
            double modalW = modal.getWidth();
            double modalH = modal.getHeight();
            modal.setX(ownerX + (ownerW - modalW) / 2);
            modal.setY(ownerY + (ownerH - modalH) / 2);
        });
    }

    public static void addTooltip(Control control, org.doazz.barcode.constant.Tooltip value) {
        Tooltip tooltip = new Tooltip(value.getName());
        control.setTooltip(tooltip);
    }

    public static void moveModal(Parent scene, Stage stage, Stage primaryStage) {
        scene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            scene.setCursor(Cursor.CLOSED_HAND);
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        scene.setOnMouseReleased(e -> scene.setCursor(Cursor.DEFAULT));
        scene.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));

        if (primaryStage != null) {
            stage.setOnShown(e -> {
                double xPosition = primaryStage.getX() + (primaryStage.getWidth() - stage.getWidth()) / 2.8;
                double yPosition = primaryStage.getY() + (primaryStage.getHeight() - stage.getHeight()) / 2;
                stage.setX(xPosition);
                stage.setY(yPosition);
            });
        }
    }
}
