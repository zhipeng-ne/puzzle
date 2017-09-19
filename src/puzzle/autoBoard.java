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
import static puzzle.MainWindow.directionIndex;

/**
 *
 * @author zpppppp
 */
public class autoBoard {

    GridPane gridPane = new GridPane();

    private Button getPathButton = new Button("Get Path");
    private Label numberLabel = new Label("Number of Movements :");
    private Label pathLabel = new Label("Movement Routine :");
    private Label timeLabel = new Label("Search Times :");
//    private Button previousButton = new Button("Previous");
//    private Button nextButton = new Button("Next");
    private Button autoButton = new Button("Auto Puzzle");
    
    
    private Text pathText = new Text("");
    private Text pathRecommend = new Text();
    private Text numberText = new Text("");
    private Text timeText = new Text("");
    
    private int [] array;
    private int directionIndex;
    private IDAStar iDAStar;
    
    public autoBoard(int[] array) {
        this.array = array;
    }
    private void init(){
        GridPane gridPane = guiSetting();
        
        
        
    }
    
    private void buttonSetting(){
            getPathButton.setOnMouseClicked(e -> {
            double startTime = System.currentTimeMillis();
            iDAStar = new IDAStar(array);
            iDAStar.init();
            double endTime = System.currentTimeMillis();
            
            System.out.println(iDAStar.getPath());
            
            numberText.setText(String.valueOf(iDAStar.getPath().length));
            timeText.setText(String.valueOf(endTime - startTime) + " ms");
            directionIndex =0 ;
        });
        EventHandler<ActionEvent> eventHandler = e -> {
            
//                move(routine.charAt(directionIndex), false);
                directionIndex++;

        };
        EventHandler<ActionEvent> enableButton = e -> {
//            nextButton.setDisable(false);
//            previousButton.setDisable(false);
        };
        autoButton.setOnMouseClicked(e -> {
//            nextButton.setDisable(true);
//            previousButton.setDisable(true);
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(300), eventHandler));
            animation.setCycleCount(iDAStar.getPath().length);
            animation.setOnFinished(enableButton);
            animation.play();
        });
        
        
    }
    
    private GridPane guiSetting(){     
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        
        gridPane.add(getPathButton, 0, 0);
        gridPane.add(pathRecommend, 1, 0);
        gridPane.add(pathLabel, 0, 1);
        gridPane.add(pathText, 1, 1);
        gridPane.add(numberLabel, 0, 2);
        gridPane.add(numberText, 1, 2);
        gridPane.add(timeLabel, 0, 3);
        gridPane.add(timeText, 1, 3);
//        gridPane.add(previousButton, 0, 4);
//        gridPane.add(nextButton, 1, 4);
        gridPane.add(autoButton, 0, 5);
        
        return gridPane;
        
    }
    
}
