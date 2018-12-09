package gui;

import backend.Client;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ChatScene {

    Stage primaryStage;
    Client client;

    public ChatScene(Client client, String debate) {
        this.client = client;
        BorderPane main = new BorderPane();
        HBox chatOutput = new HBox();
        chatOutput.setSpacing(12);
        chatOutput.setAlignment(Pos.CENTER);
        TextField input = new TextField();
        Button send = new Button("Send");
        send.setOnAction(event -> {
            if(!input.getText().equals("")) {
                client.sendMessageDebate(input.getText());
                input.clear();
            }
        });

        Button quit = new Button("Quit");
        quit.setOnAction(event -> {
            client.sendExit();
        });

        chatOutput.getChildren().addAll(input, send, quit);
        main.setBottom(chatOutput);

        TextArea messages = new TextArea();
        main.setCenter(messages);
        Scene mainScene = new Scene(main);
        primaryStage = new Stage();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle(debate);
        primaryStage.show();
    }

    private void update(TextArea text) {

    }
}
