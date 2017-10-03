/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import static puzzle.Operation.getArray;

/**
 *
 * @author zpppppp
 */
public class MainWindow {

    public static int directionIndex = 0;
    ArrayList<Cell> cellsList = new ArrayList<>();

    CountBoard countBoard = new CountBoard();

    private Image image = new Image("image/witcher09.png", 600, 600, false, true);
//窗口大小
    private final double SCENE_WUDTH = 1024;
    private final double SCENE_HEIGHT = 640;
    public static final double offsetX = 0;
    public static final double offsetY = 30;
    //每行每列的格子数及格子大小
    public static int ORDER;
    public int CELLSIZE = 100;
//移动次数及用时
    private int numberOfMovements = 1;

    public void setOrder(int num) {
        this.ORDER = num;
    }

    public int getOrder() {
        return ORDER;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    public void init(Stage primaryStage) {
        CELLSIZE = (int) image.getWidth() / ORDER;
        int[] ran = RandomArray.getEvenPermutation(ORDER * ORDER);

//测试        
        test(ran);
//
        for (int i = 0; i < ran.length; i++) {
            ImageView imageBlock = new ImageView(image);

            int minX = ran[i] % ORDER;
            int minY = ran[i] / ORDER;
            Rectangle2D rectangle2D = new Rectangle2D(CELLSIZE * minX,
                    CELLSIZE * minY, CELLSIZE, CELLSIZE);
            imageBlock.setViewport(rectangle2D);

            if (ran[i] == ORDER * ORDER - 1) {
                imageBlock = null;
            }

            cellsList.add(new Cell(i % ORDER, i / ORDER, imageBlock, i, ran[i]));
        }

        Pane pane = new Pane();

        for (int i = 0; i < cellsList.size(); i++) {
            Cell currentCell = cellsList.get(i);

            Node imageView = currentCell.getImageView();
            if (imageView == null) {
                continue;
            }

            imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                NormalMove movement = new NormalMove(cellsList, countBoard, CELLSIZE, offsetX, offsetY);
                movement.move((Node) mouseEvent.getSource());
                // move((Node) mouseEvent.getSource());
            });

            ImageView currentImageView = currentCell.getImageView();
            imageView.relocate(currentCell.getX() * CELLSIZE + offsetX, currentCell.getY() * CELLSIZE + offsetY);
            pane.getChildren().add(currentImageView);
        }

        ReferPicture refer = new ReferPicture(image);
        ImageView referPicture = refer.getPicture();
        referPicture.relocate(CELLSIZE * ORDER + offsetX + 30, offsetY);
        pane.getChildren().add(referPicture);

        GridPane gridPane = countBoard.createBoard();
        gridPane.relocate(CELLSIZE * ORDER + offsetX + 30, 340);
        pane.getChildren().add(gridPane);
///
        AutoMove movement = new AutoMove(cellsList, countBoard, CELLSIZE, offsetX, offsetY);
        AutoBoard autoBoard = new AutoBoard(getArray(cellsList));
        AutoBoard.update(movement);
        
        GridPane autoPane = autoBoard.createBoard();
///

        autoPane.relocate(CELLSIZE * ORDER + offsetX + 30, 450);
        pane.getChildren().add(autoPane);

        MainMenu mainMenu = new MainMenu(primaryStage);

        MenuBar menuBar = mainMenu.createMenuBar();
        pane.getChildren().add(menuBar);
        Scene scene = new Scene(pane, SCENE_WUDTH, SCENE_HEIGHT);
        primaryStage.setResizable(true);

        scene.getStylesheets().add("css/main.css");
        primaryStage.setTitle("Puzzle Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void test(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();

    }
}
