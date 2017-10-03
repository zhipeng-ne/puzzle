/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author zpppppp
 */
public class AlertWindow {

    private int times = 0;
    private int numberOfMovements = 0;

    private Button btClose = new Button("CLOSE");
    private Button btRecord = new Button("RECORD");

    private Stage alertStage = new Stage();

    AlertWindow() {

    }

    public AlertWindow(int times, int numberOfMovements) {
        this.times = times;
        this.numberOfMovements = numberOfMovements;
    }

    public void start() {
        alertStage.setTitle("Message");
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setMinWidth(300);
        alertStage.setMinHeight(150);

        BorderPane borderPane = new BorderPane();
        Text text = new Text("Congratulation!");
        text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 25));

        setButtonAction();

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(btRecord, btClose);
        hBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(text);
        borderPane.setBottom(hBox);
        hBox.setPadding(new Insets(0, 0, 50, 0));
        Scene scene = new Scene(borderPane, 400, 300);
        scene.getStylesheets().add("css/initial.css");
        alertStage.setScene(scene);
        alertStage.show();

    }

    private void setButtonAction() {
        btClose.setOnAction(e -> alertStage.close());
        btRecord.setOnAction(e -> {
            alertStage.close();
            RankList list = new RankList("", times, numberOfMovements);
            list.start();
        });
    }
}
