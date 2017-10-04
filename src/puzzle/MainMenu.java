/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author zpppppp
 */
public class MainMenu {

    private Menu menu1 = new Menu("Game");
    private MenuItem menuReturn = new MenuItem("Return");
    private MenuItem menuRestart = new MenuItem("Restart");

    private MenuItem menuRank = new MenuItem("Ranking List");
    private MenuItem menuExit = new MenuItem("Exit");
    private Menu menu2 = new Menu("About");
    private MenuItem menuAbout = new MenuItem("About");
    private MenuBar menuBar = new MenuBar();
    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public MenuBar createMenuBar() {

        menuExit.setOnAction(e -> {
            this.stage.close();
        }
        );
        menuReturn.setOnAction(e -> {
            InitialWindow initialWindow = new InitialWindow();
            initialWindow.start(stage);
        });

        menuRestart.setOnAction(e -> {
            MainWindow mainWindow = new MainWindow();

            mainWindow.setOrder(MainWindow.ORDER);
            mainWindow.start(stage);
        });
        menuRank.setOnAction(e -> {
            RankList rankList = new RankList();
            rankList.start();
            rankList.disableAddFunction();
        });
        menuAbout.setOnAction(e -> {
            Stage stage = new Stage();
            Text text = new Text();
            text.setText("A puzzle game with automatic puzzle solver");
            text.setFont(Font.font("Arial Narrow", FontWeight.BOLD, FontPosture.ITALIC, 20));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(text);

            Scene scene = new Scene(stackPane, 400, 300);
            scene.getStylesheets().add("css/initial.css");
            stage.setScene(scene);
            stage.setTitle("About");
            stage.show();

        });

        menu1.getItems().addAll(menuReturn, menuRestart, menuRank, menuExit);
        menu2.getItems().addAll(menuAbout);

        menuBar.getMenus().addAll(menu1, menu2);
        return menuBar;
    }
}
