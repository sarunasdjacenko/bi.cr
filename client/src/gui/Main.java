package gui;

import backend.Client;
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

    private Client client;

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
            if(validateUsername(input.getText())) {
                System.out.println(input.getText());
                client = new Client(input.getText(), "10.40.152.192", 7777);
                new Thread(()-> client.listen()).start();
                primaryStage.setScene(new MenuScene(new BorderPane(), client));
            } else {
                input.setText("Please enter a name!");
            }
        });

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(content);
        mainPane.setBottom(goButton);
        BorderPane.setAlignment(goButton, Pos.CENTER);
        BorderPane.setMargin(goButton, new Insets(0, 12, 12, 12));

        Scene startScene = new Scene(mainPane);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("bi.cr");
        primaryStage.setWidth(600);
        primaryStage.setHeight(300);
        primaryStage.show();
    }

    private boolean validateUsername(String username) {
        return(!username.equals("") && !(username == null));
    }
}
