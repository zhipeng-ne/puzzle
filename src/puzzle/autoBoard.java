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

    private Label numberLabel = new Label("Number of Movements :");
    private Label pathLabel = new Label("Movement Routine :");
    private Label timeLabel = new Label("Search Times :");
    private Button btGetPath = new Button("Get Path");
    private Button btAutoPuzzle = new Button("Auto Puzzle");
    private Label calculate = new Label("");

    private Text pathText = new Text("");
    private Text numberText = new Text("");
    private Text timeText = new Text("");

    private static int[] array;        //拼图的排列
    private int directionIndex;        //方向字符串数组的下标
    private IDAStar iDAStar;           //
    private static AutoMove movement;  //
    private static boolean isMove;     //在获得路径之后，判断拼图是否移动过

    public AutoBoard(int[] array) {
        this.array = array;
        init();
    }

    private void init() {
        setPathButton();
        setAutoButton();
    }

    private void setPathButton() {
        btGetPath.setOnMouseClicked(e -> {
            calculate.setText("Calculating!");
            double startTime = System.currentTimeMillis();

            iDAStar = new IDAStar(array);
            iDAStar.init();
            double endTime = System.currentTimeMillis();
            calculate.setText("");

            //System.out.println(iDAStar.getPath());

            pathText.setText(iDAStar.getPath());
            numberText.setText(String.valueOf(iDAStar.getPath().length()));
            timeText.setText(String.valueOf(endTime - startTime) + " ms");
            directionIndex = 0;
            isMove = false;
        });

    }
    //自动拼图实际上就是播放动画，在获得路径后，按路径进行移动
    private void setAutoButton() {
        EventHandler<ActionEvent> eventHandler = e -> {
            movement.move(iDAStar.getPath().charAt(directionIndex));
            directionIndex++;
        };

        btAutoPuzzle.setOnMouseClicked(e -> {
            if (!isMove && iDAStar.getPath().length()>0) {
                Timeline animation = new Timeline(new KeyFrame(Duration.millis(300), eventHandler));
                animation.setCycleCount(iDAStar.getPath().length());    //设置timeline动画的轮数为路径长度
                animation.play();
                btAutoPuzzle.setDisable(true);
                btGetPath.setDisable(true);
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
        gridPane.add(btGetPath, 0, 4);
        gridPane.add(btAutoPuzzle, 1, 4);

        return gridPane;

    }
    //同步数据，这里能同步到是因为变量是静态的，下同（这样似乎不太科学，暂时想不到别的就这样啦）
    public static void syncMovement(AutoMove move) {
        movement = move;
    }

    public static void syncArray(int[] list) {
        array = list;
    }

    public static void syncIsMove(boolean move) {
        isMove = move;
    }

}
