package org.doazz.barcode;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.util.Objects;

public class TitleBarMinimal extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;

    public TitleBarMinimal(Stage stage, String title) {
        setStyle("-fx-background-color: #18947E; -fx-padding: 6 12; -fx-spacing: 8;");
        setAlignment(Pos.CENTER_LEFT);

        ImageView iconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        iconView.setFitWidth(16);
        iconView.setFitHeight(16);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Button minimizeButton = new Button("–");
        Button closeButton = new Button("✕");

        minimizeButton.setOnAction(e -> stage.setIconified(true));
        closeButton.setOnAction(e -> stage.close());

        for (Button btn : new Button[] { minimizeButton, closeButton }) {
            btn.setPrefWidth(30);
            btn.setPrefHeight(24);
            btn.setFocusTraversable(false);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 3px");
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-font-size: 14px; -fx-padding: 3px; -fx-text-fill: white; -fx-font-weight: bold;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-font-size: 14px; -fx-padding: 3px; -fx-text-fill: white; -fx-font-weight: bold;"));
            btn.setCursor(Cursor.HAND);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        HBox leftGroup = new HBox(8, iconView, titleLabel);
        leftGroup.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(leftGroup, spacer, minimizeButton, closeButton);

        // Trascinamento finestra
        setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
