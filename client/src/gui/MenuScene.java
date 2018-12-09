package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.util.*;

public class MenuScene extends Scene {

    public MenuScene(BorderPane menuPane) {
        super(menuPane);
        List<String> test = new ArrayList<>();
        test.add("debate1");
        test.add("debate2");
        test.add("debate3");
        test.add("debate4");
        test.add("debate5");
        test.add("debate6");
        test.add("debate7");
        test.add("debate8");
        test.add("debate9");
        test.add("debate10");
        test.add("debate11");
        test.add("debate12");

        ObservableList<String> observableList = FXCollections.observableArrayList(test);
        ListView<String> debates = new ListView<>(observableList);
        menuPane.setCenter(debates);
        BorderPane.setMargin(debates, new Insets(12, 12, 12, 12));

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(12);
        buttonBox.setAlignment(Pos.CENTER);
        Button newDebate = new Button("New Debate");
        Button joinDebate = new Button("Join Debate");
        buttonBox.getChildren().addAll(joinDebate, newDebate);

        menuPane.setBottom(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(12, 12, 12, 12));
    }
}
