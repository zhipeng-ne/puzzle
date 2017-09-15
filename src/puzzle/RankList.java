/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author zpppppp
 */
public class RankList {

    private String name;
    private int times;
    private int number;

    private Stage stage = new Stage();
    private Button threeOrderButton = new Button("3X3");
    private Button fourOrderButton = new Button("4X4");
    private Button fiveOrderButton = new Button("5X5");

    private transient TableView tableView = new TableView();

    private transient TextField addName = new TextField();
    private transient TextField addTime = new TextField();
    private transient TextField addNumber = new TextField();
    private transient Button addButton = new Button("Add");
    public transient ObservableList<RecorderData> data;

    public RankList() {
        this.name = "";
        this.times = 0;
        this.number = 0;
    }

    public RankList(String name, int times, int number) {
        this.name = name;
        this.times = times;
        this.number = number;
    }

    public void start() {
        try {
            FileReader fileReader = new FileReader();
            fileReader.read();
            data = fileReader.getData();
        } catch (ClassNotFoundException | IOException e) {

        }

        try {
            setOrderButton();
        } catch (ClassNotFoundException | IOException e) {

        }
        stage.initModality(Modality.APPLICATION_MODAL);
        threeOrderButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 15));
        fourOrderButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 15));
        fiveOrderButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 15));

        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(5, 5, 5, 5));
        topBox.setSpacing(50);
        topBox.getChildren().addAll(threeOrderButton, fourOrderButton, fiveOrderButton);

        initializeTableView();
        setRecord();

        try {
            addData();
        } catch (IOException e) {
        }

        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(5, 5, 5, 5));
        bottomBox.setSpacing(20);
        bottomBox.getChildren().addAll(addName, addTime, addNumber, addButton);

        borderPane.setTop(topBox);
        borderPane.setCenter(tableView);
        borderPane.setBottom(bottomBox);
        Scene scene = new Scene(borderPane, 600, 500);

        scene.getStylesheets().add("css/rank.css");
        stage.setScene(scene);
        stage.setTitle("Ranking List");
        stage.show();

    }

    public void setRecord() {

        tableView.setItems(data);
    }

    public void initializeTableView() {

        tableView.setEditable(false);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn timesCol = new TableColumn("Times");
        timesCol.setMinWidth(200);
        timesCol.setCellValueFactory(new PropertyValueFactory<>("Times"));

        TableColumn moveTimesCol = new TableColumn("NumberOfMovements");
        moveTimesCol.setMinWidth(200);
        moveTimesCol.setCellValueFactory(new PropertyValueFactory<>("NumberOfMovements"));

        tableView.getColumns().addAll(nameCol, timesCol, moveTimesCol);
    }

    public void addData() throws IOException {
        addName.setEditable(true);
        addTime.setEditable(false);
        addNumber.setEditable(false);

        addName.setText("Please enter your name!");
        addName.setOnMouseClicked(e -> {
            addName.setText("");
        });
        addTime.setText(String.valueOf(times));
        addNumber.setText(String.valueOf(number));
        setAddButton();
    }

    public void setAddButton() {
        try {
            FileReader fileReader = new FileReader(MainWindow.ORDER);
            fileReader.read();
            data = fileReader.getData();
            setRecord();
        } catch (ClassNotFoundException | IOException ex) {

        }
        addButton.setOnAction(e -> {
            data.add(new RecorderData(new RecordData(addName.getText(), times, number)));
            addName.clear();
            addTime.clear();
            addNumber.clear();

            try {
                FileWriter fileWriter = new FileWriter(data, MainWindow.ORDER);
                fileWriter.write();
            } catch (IOException ex) {
            }
            addButton.setDisable(true);
            addName.setEditable(false);
        });
    }

    public void disableAddFunction() {
        addButton.setDisable(true);
        addName.setDisable(true);
        addTime.setDisable(true);
        addNumber.setDisable(true);
    }

    public void setOrderButton() throws ClassNotFoundException, IOException {
        threeOrderButton.setOnMouseClicked(e -> {

            try {
                FileReader fileReader = new FileReader(3);
                fileReader.read();
                data = fileReader.getData();
                setRecord();
            } catch (ClassNotFoundException | IOException ex) {

            }
            stage.show();
        });
        fourOrderButton.setOnMouseClicked(e -> {
            try {
                FileReader fileReader = new FileReader(4);
                fileReader.read();
                data = fileReader.getData();
                setRecord();
            } catch (ClassNotFoundException | IOException ex) {

            }
            stage.show();
        });
        fiveOrderButton.setOnMouseClicked(e -> {
            try {
                FileReader fileReader = new FileReader(5);
                fileReader.read();
                data = fileReader.getData();
                setRecord();
            } catch (ClassNotFoundException | IOException ex) {

            }
            stage.show();
        });

    }
}
