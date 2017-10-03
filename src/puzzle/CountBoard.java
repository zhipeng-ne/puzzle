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
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * 计数面板
 *
 * @author zpppppp
 */
public class CountBoard {

    private Label label1 = new Label("Used times: ");
    private Label label2 = new Label("Moved times: ");
    private Text text1 = new Text();
    private Text text2 = new Text();
    private Button btPause = new Button("Pause");
    private Button btStart = new Button("Start");
    private Boolean isPause = false;
    private Timeline timeLine1;
    private Timeline timeLine2;

    private int usedTimes = 0;
    private int numberOfMovements = 0;

    /**
     * 初始化计数面板
     */
    public CountBoard() {
        init();
    }

    private void init() {

        btPause.setOnMouseClicked(e -> {
            stopCounting();
            isPause = true;
        });
        btStart.setOnMouseClicked(e -> {
            startCounting();
            isPause = false;
        });
        text1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
        text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));

        EventHandler<ActionEvent> eventHandler1 = e -> {
            text1.setText(String.format("%d s", usedTimes++));
        };
        timeLine1 = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler1));
        timeLine1.setCycleCount(Timeline.INDEFINITE);
        timeLine1.play();

        EventHandler<ActionEvent> eventHandler2 = e -> {
            text2.setText(String.format("%d", numberOfMovements));
        };
        timeLine2 = new Timeline(new KeyFrame(Duration.millis(100), eventHandler2));
        timeLine2.setCycleCount(Timeline.INDEFINITE);
        timeLine2.play();
    }

    /**
     * 暂停记时
     */
    public void stopCounting() {
        timeLine1.pause();
        timeLine2.pause();

    }

    /**
     * 开始记时
     */
    public void startCounting() {
        timeLine1.play();
        timeLine2.play();
    }

    public void setDisableButton() {
        btPause.setDisable(true);
        btStart.setDisable(true);
    }

    /**
     *
     * @return usedTimes
     */
    public int getUsedTimes() {
        return usedTimes;
    }

    /**
     *
     * @return numberOfMovements
     */
    public int getNumberOfMovements() {
        return numberOfMovements;
    }

    /**
     * 更新移动次数
     *
     * @param num 移动次数
     */
    public void updateNumberOfMovements() {
        this.numberOfMovements++;
    }

    /**
     *
     * @return isPause
     */
    public Boolean getIsPause() {
        return isPause;
    }

    public void setIsPause(Boolean isPause) {
        this.isPause = isPause;
    }

    /**
     *
     * @return 一个计数面板
     */
    public GridPane createBoard() {
        GridPane gridPane = new GridPane();

        gridPane.add(label1, 0, 0);
        gridPane.add(label2, 0, 1);
        gridPane.add(text1, 1, 0);
        gridPane.add(text2, 1, 1);
        gridPane.add(btPause, 0, 2);
        gridPane.add(btStart, 1, 2);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        return gridPane;
    }
}
