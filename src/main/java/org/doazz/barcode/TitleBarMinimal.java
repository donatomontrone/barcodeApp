package org.doazz.barcode;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.doazz.barcode.constant.Btn;
import org.doazz.barcode.constant.Path;
import org.doazz.barcode.constant.StyleClass;
import org.doazz.barcode.constant.Tooltip;

import java.util.Objects;

import static org.doazz.barcode.Main.getPrimaryStage;
import static org.doazz.barcode.util.Util.addTooltip;
import static org.doazz.barcode.util.Util.moveModal;

public class TitleBarMinimal extends HBox {

    public TitleBarMinimal(Stage stage, String title) {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource(Path.TITLEBAR_CSS.getPath())).toExternalForm());
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().add(StyleClass.TITLE_BAR.getName());
        ImageView iconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Path.ICON.getPath()))));
        iconView.setFitWidth(16);
        iconView.setFitHeight(16);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add(StyleClass.TITLE_BAR_LABEL.getName());
        Button minimizeButton = new Button(Btn.MINIMIZE.getName());
        Button closeButton = new Button(Btn.CLOSE.getName());
        minimizeButton.setOnAction(e -> stage.setIconified(true));
        closeButton.setOnAction(e -> stage.close());
        addTooltip(minimizeButton, Tooltip.MINIMIZE);
        addTooltip(closeButton, Tooltip.CLOSE);
        for (Button btn : new Button[] { minimizeButton, closeButton }) {
            btn.setPrefWidth(30);
            btn.setPrefHeight(24);
            btn.setFocusTraversable(false);
            btn.getStyleClass().add(StyleClass.TITLE_BAR_BUTTON.getName());
            btn.setCursor(Cursor.HAND);
        }
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        HBox leftGroup = new HBox(8, iconView, titleLabel);
        leftGroup.setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(leftGroup, spacer, minimizeButton, closeButton);
        moveModal(this, stage, getPrimaryStage());
    }
}
