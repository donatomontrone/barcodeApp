package org.doazz.barcode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.doazz.barcode.constant.Path;
import org.doazz.barcode.constant.Title;
import org.doazz.barcode.constant.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    private static Stage primaryStage;
    private static final List<Stage> secondaryStages = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(View.HOME.getPath()));
        Parent content = loader.load();

        TitleBarMinimal titleBar = new TitleBarMinimal(primaryStage, Title.APP_TITLE.getValue());

        VBox root = new VBox(titleBar, content);
        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/application.css")).toExternalForm());

        primaryStage.initStyle(StageStyle.UNDECORATED);
        getStage(primaryStage, scene);
        Main.primaryStage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void openSecondaryStage(Stage stage) {
        secondaryStages.add(stage);
    }

    private void getStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle(Title.APP_TITLE.getValue());
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Path.ICON.getPath()))));
        primaryStage.setOnCloseRequest(event -> closeAllStages());
        primaryStage.show();
    }

    private void closeAllStages() {
        for (Stage stage : secondaryStages) {
            if (stage.isShowing()) {
                stage.close();
            }
        }
        primaryStage.close();
    }
}
