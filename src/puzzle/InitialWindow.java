/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author zpppppp
 */
public class InitialWindow extends Application {

    private final String[] difficultyList = {"3 X 3", "4 X 4", "5 X 5"};

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();

        MainWindow mainInterface = new MainWindow();

        GridPane gridPane = new GridPane();
        Label label1 = new Label("Difficulity: ");
        Label label2 = new Label("Image: ");

        ObservableList<String> items = FXCollections.observableArrayList(difficultyList);
        ComboBox<String> comboBox = new ComboBox<>(items);

        comboBox.setValue(difficultyList[0]);

        TextField textField = new TextField();
        textField.setText("Default(Click to choose)");
        textField.setEditable(false);
        textField.setOnMouseClicked(e -> {
            ImageChooser imageChooser = new ImageChooser();
            imageChooser.start(new Stage());
            String path = imageChooser.getImagePath();
            if (path != "") {
                mainInterface.setImage(new Image(path, 600, 600, false, true));
                textField.setText(path.substring(path.lastIndexOf("/") + 1));
            }
        });

        Button btStart = new Button("Start");
        btStart.setOnAction(e -> {
            mainInterface.setOrder((items.indexOf(comboBox.getValue()) + 3));
            mainInterface.start(primaryStage);
        });
        Button btExit = new Button("Exit");
        btExit.setOnAction(e -> {
            primaryStage.close();
        });

        gridPane.add(label1, 0, 0);
        gridPane.add(comboBox, 1, 0);
        gridPane.add(label2, 0, 1);
        gridPane.add(textField, 1, 1);
        gridPane.add(btStart, 0, 2);
        gridPane.add(btExit, 1, 2);

        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        pane.getChildren().add(gridPane);

        Scene scene = new Scene(pane, 500, 350);
        scene.getStylesheets().add("css/initial.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Puzzle Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
