package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox content = new HBox();
        content.setSpacing(10);
        Label label = new Label("Username:");
        TextField input = new TextField();
        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(label, input);

        Button goButton = new Button("Go!");
        goButton.setOnAction(event -> {
            primaryStage.setScene(new MenuScene(new BorderPane()));
        });

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(content);
        mainPane.setBottom(goButton);
        BorderPane.setAlignment(goButton, Pos.CENTER);
        BorderPane.setMargin(goButton, new Insets(0, 12, 12, 12));

        Scene startScene = new Scene(mainPane);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("bi.cr");
        primaryStage.setWidth(400);
        primaryStage.setHeight(200);
        primaryStage.show();
    }
}
