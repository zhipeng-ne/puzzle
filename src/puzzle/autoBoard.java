/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author zpppppp
 */
public class AutoBoard {

    GridPane gridPane = new GridPane();

    private Button getPathButton = new Button("Get Path");
    private Label numberLabel = new Label("Number of Movements :");
    private Label pathLabel = new Label("Movement Routine :");
    private Label timeLabel = new Label("Search Times :");
    private Button autoButton = new Button("Auto Puzzle");
    private Label calculate = new Label("");

    private Text pathText = new Text("");
    private Text numberText = new Text("");
    private Text timeText = new Text("");

    private static int[] array;
    private int directionIndex;
    private IDAStar iDAStar;
    private static AutoMove movement;
    private static boolean isMove;
    private static String path;
    public AutoBoard(int[] array) {
        this.array = array;
        init();
    }

    private void init() {
        setPathButton();
        setAutoButton();
    }

    private void setPathButton() {

        getPathButton.setOnMouseClicked(e -> {
            calculate.setText("Calculating!");
            double startTime = System.currentTimeMillis();

//            Runnable iDAStar = new IDAStar(array);
//            Thread thread = new Thread(iDAStar);
//            thread.start();
            iDAStar = new IDAStar(array);
            iDAStar.init();
            double endTime = System.currentTimeMillis();
            calculate.setText("");

            System.out.println(iDAStar.getPath());

            pathText.setText(iDAStar.getPath());
            numberText.setText(String.valueOf(iDAStar.getPath().length()));
            timeText.setText(String.valueOf(endTime - startTime) + " ms");
            directionIndex = 0;
            isMove = false;
        });

    }

    private void setAutoButton() {
        EventHandler<ActionEvent> eventHandler = e -> {
            movement.move(iDAStar.getPath().charAt(directionIndex));
            directionIndex++;
        };

        autoButton.setOnMouseClicked(e -> {
            if (!isMove) {
                Timeline animation = new Timeline(new KeyFrame(Duration.millis(300), eventHandler));
                animation.setCycleCount(iDAStar.getPath().length());
                animation.play();
                autoButton.setDisable(true);
                getPathButton.setDisable(true);
            }
            
        });
    }

    protected GridPane createBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(2);

        gridPane.add(calculate, 0, 0);
        gridPane.add(pathLabel, 0, 1);
        gridPane.add(pathText, 1, 1);
        gridPane.add(numberLabel, 0, 2);
        gridPane.add(numberText, 1, 2);
        gridPane.add(timeLabel, 0, 3);
        gridPane.add(timeText, 1, 3);
        gridPane.add(getPathButton, 0, 4);
        gridPane.add(autoButton, 1, 4);

        return gridPane;

    }

    public static void syncMovement(AutoMove move) {
        movement = move;
    }

    public static void syncArray(int[] list) {
        array = list;
    }

    public static void syncisMove(boolean move) {
        isMove = move;
    }

}
