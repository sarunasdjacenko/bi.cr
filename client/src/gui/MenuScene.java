package gui;

import backend.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import jdk.nashorn.api.tree.TryTree;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MenuScene extends Scene {

    public MenuScene(BorderPane menuPane, Client client) {
        super(menuPane);
        client.requestDebates();
        try {
            Thread.sleep(200);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(client.getDebates());
        ListView<String> debates = new ListView<>(observableList);
        menuPane.setCenter(debates);
        BorderPane.setMargin(debates, new Insets(12, 12, 12, 12));

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(12);
        buttonBox.setAlignment(Pos.CENTER);
        Button newDebate = new Button("New Debate");
        newDebate.setOnAction(event -> {
            CreateDebate popup = new CreateDebate(client);
        });

        Button joinDebate = new Button("Join Debate");
        joinDebate.setOnAction(event -> {
            String selected = debates.getSelectionModel().getSelectedItem();
            if(selected != null) {
                JoinDebate popup = new JoinDebate(client, debates.getSelectionModel().getSelectedItem());
            }
        });

        Button refresh = new Button("Refresh List");
        refresh.setOnAction(event -> {
            client.requestDebates();
        });
        buttonBox.getChildren().addAll(joinDebate, newDebate, refresh);

        menuPane.setBottom(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(12, 12, 12, 12));
    }

    private class CreateDebate extends Popup {
        public CreateDebate(Client client) {
            super();
            Stage popup = new Stage();
            BorderPane parent = new BorderPane();

            HBox container = new HBox();
            container.setAlignment(Pos.CENTER);
            container.setSpacing(12);

            Label debateTopic = new Label("Debate topic: ");
            TextField topicField = new TextField();

            Label isFor = new Label("Are you for or against?");
            ComboBox<String> selector = new ComboBox<>();
            selector.setItems(FXCollections.observableArrayList("For", "Against"));

            Button submit = new Button("Submit");
            submit.setOnAction(event -> {
                boolean value = selector.getValue().equals("For");
                client.sendCreateDebate(topicField.getText(), value);
            });

            container.getChildren().addAll(debateTopic, topicField, isFor, selector);
            parent.setCenter(container);
            BorderPane.setMargin(container, new Insets(12, 12, 12, 12));
            parent.setBottom(submit);
            BorderPane.setMargin(submit, new Insets(0, 12, 12, 12));
            BorderPane.setAlignment(submit, Pos.CENTER);

            Scene scene = new Scene(parent);
            popup.setScene(scene);
            popup.setTitle("Create Debate");
            popup.show();
        }
    }

    private class JoinDebate extends Popup {
        public JoinDebate(Client client, String debate) {
            super();
            Stage popup = new Stage();
            BorderPane parent = new BorderPane();

            HBox container = new HBox();
            container.setAlignment(Pos.CENTER);
            container.setSpacing(12);

            Label debateTopic = new Label("Debate topic: ");
            Label topicField = new Label(debate);

            Label isFor = new Label("Are you for or against?");
            ComboBox<String> selector = new ComboBox<>();
            selector.setItems(FXCollections.observableArrayList("For", "Against"));

            Button submit = new Button("Submit");
            submit.setOnAction(event -> {
                boolean value = selector.getValue().equals("For");
                client.sendJoinDebate(debate, value);
            });

            container.getChildren().addAll(debateTopic, topicField, isFor);
            parent.setCenter(container);
            BorderPane.setMargin(container, new Insets(12, 12, 12, 12));
            parent.setBottom(submit);
            BorderPane.setMargin(submit, new Insets(0, 12, 12, 12));
            BorderPane.setAlignment(submit, Pos.CENTER);

            Scene scene = new Scene(parent);
            popup.setScene(scene);
            popup.setTitle("Join " + debate);
            popup.show();
        }
    }
}
