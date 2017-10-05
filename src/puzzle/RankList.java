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
import static puzzle.MainWindow.ORDER;

/**
 *
 * @author zpppppp
 */
public class RankList {

    private transient TableView tableView = new TableView();

    private int times;
    private int number;

    private Stage stage = new Stage();
    private Button threeOrderButton = new Button("3X3");
    private Button fourOrderButton = new Button("4X4");
    private Button fiveOrderButton = new Button("5X5");

    private transient TextField nameField = new TextField();
    private transient TextField timeField = new TextField();
    private transient TextField numberField = new TextField();
    private transient Button btAdd = new Button("Add");

    public transient ObservableList<RecorderData> data;             //读入记录的数据流

    public RankList() {
        this.times = 0;
        this.number = 0;
    }

    public RankList(int times, int number) {
        this.times = times;
        this.number = number;
    }

    public void start() {
        readData(ORDER);
        try {
            setOrderButton();
        } catch (ClassNotFoundException | IOException e) {
        }

        initializeTableView();
        displayAddPane();
        try {
            setAddButton();
        } catch (IOException e) {
        }

        BorderPane borderPane = createBorderPane();
        
        stage.initModality(Modality.APPLICATION_MODAL);
//        threeOrderButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 15));
//        fourOrderButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 15));
//        fiveOrderButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 15));
        Scene scene = new Scene(borderPane, 600, 500);
        scene.getStylesheets().add("css/rank.css");
        stage.setScene(scene);
        stage.setTitle("Ranking List");
        stage.show();

    }

    private BorderPane createBorderPane() {
        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(5, 5, 5, 5));
        topBox.setSpacing(50);
        topBox.getChildren().addAll(threeOrderButton, fourOrderButton, fiveOrderButton);

        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(5, 5, 5, 5));
        bottomBox.setSpacing(20);
        bottomBox.getChildren().addAll(nameField, timeField, numberField, btAdd);

        borderPane.setTop(topBox);
        borderPane.setCenter(tableView);
        borderPane.setBottom(bottomBox);
        return borderPane;
    }

    private void setRecord() {
        tableView.setItems(data);
    }

    private void initializeTableView() {

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

    private void displayAddPane() {
        nameField.setEditable(true);
        timeField.setEditable(false);
        numberField.setEditable(false);

        nameField.setText("Please enter your name!");
        nameField.setOnMouseClicked(e -> {
            nameField.setText("");
        });
        timeField.setText(String.valueOf(times));
        numberField.setText(String.valueOf(number));

    }

    private void setAddButton() throws IOException {
        //为了将记录添加到对应的文件，要将对应的文件先读一遍，将数据流刷新一遍
        //然后将新记录加到数据流中，最后再写进文件中
        readData(ORDER);
        btAdd.setOnAction(e -> {
            writeRecordInFile();
            nameField.clear();
            timeField.clear();
            numberField.clear();
            btAdd.setDisable(true);
            nameField.setEditable(false);
        });
    }

    //读取数据
    private void readData(int order) {
        try {
            FileReader fileReader = new FileReader(order);
            fileReader.read();
            data = fileReader.getData();
            setRecord();
        } catch (ClassNotFoundException | IOException ex) {
        }
    }

    //将记录写入文件
    private void writeRecordInFile() {
        data.add(new RecorderData(new Record(nameField.getText(), times, number)));
        try {
            FileWriter fileWriter = new FileWriter(data, ORDER);
            fileWriter.write();
        } catch (IOException ex) {
        }
    }

    private void setOrderButton() throws ClassNotFoundException, IOException {
        threeOrderButton.setOnMouseClicked(e -> {
            readData(3);
        });
        fourOrderButton.setOnMouseClicked(e -> {
            readData(4);
        });
        fiveOrderButton.setOnMouseClicked(e -> {
            readData(5);
        });
    }

    public void disableAddFunction() {
        btAdd.setDisable(true);
        nameField.setDisable(true);
        timeField.setDisable(true);
        numberField.setDisable(true);
    }

}
