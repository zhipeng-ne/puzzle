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

    private Image image = new Image("image/witcher09.png", 600, 600, false, true);

    private Pane pane = new Pane();
    private ImageView referPicture;
    private CountBoard countBoard = new CountBoard();
    private GridPane countPane;
    private AutoBoard autoBoard;
    private GridPane autoPane;
    private MenuBar menuBar;

    private final double SCENE_WUDTH = 1024;
    private final double SCENE_HEIGHT = 640;
    public static final double offsetX = 0;
    public static final double offsetY = 30;
    ArrayList<Cell> cellsList = new ArrayList<>();
    public static int ORDER;
    public int CELLSIZE = 100;
    public static int directionIndex = 0;
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

    public void start(Stage stage) {
        CELLSIZE = (int) image.getWidth() / ORDER;
        initialize();
        addMounseEventInCell();
        setReferPicture();
        setCountBoard();
        setAutoBoard();
        setMenu(stage);

        Scene scene = new Scene(pane, SCENE_WUDTH, SCENE_HEIGHT);
        stage.setResizable(true);
        scene.getStylesheets().add("css/main.css");
        stage.setTitle("Puzzle Game");
        stage.setScene(scene);
        stage.show();

    }

    private void initialize() {
        int[] ran = RandomArray.getEvenPermutation(ORDER * ORDER);
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

    }

    private void addMounseEventInCell() {
        for (int i = 0; i < cellsList.size(); i++) {
            Cell currentCell = cellsList.get(i);
            Node imageView = currentCell.getImageView();
            if (imageView == null) {
                continue;
            }
            imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                NormalMove movement = new NormalMove(cellsList, countBoard, CELLSIZE, offsetX, offsetY);
                movement.move((Node) mouseEvent.getSource());
            });
            ImageView currentImageView = currentCell.getImageView();
            imageView.relocate(currentCell.getX() * CELLSIZE + offsetX, currentCell.getY() * CELLSIZE + offsetY);
            pane.getChildren().add(currentImageView);
        }
    }

    private void setReferPicture() {
        ReferPicture refer = new ReferPicture(image);
        referPicture = refer.getPicture();
        referPicture.relocate(CELLSIZE * ORDER + offsetX + 30, offsetY);
        pane.getChildren().add(referPicture);
    }

    private void setCountBoard() {
        countPane = countBoard.createBoard();
        countPane.relocate(CELLSIZE * ORDER + offsetX + 30, 340);
        pane.getChildren().add(countPane);
    }

    private void setAutoBoard() {
        AutoMove movement = new AutoMove(cellsList, countBoard, CELLSIZE, offsetX, offsetY);
        autoBoard = new AutoBoard(getArray(cellsList));
        AutoBoard.syncMovement(movement);

        autoPane = autoBoard.createBoard();
        autoPane.relocate(CELLSIZE * ORDER + offsetX + 30, 450);
        pane.getChildren().add(autoPane);
    }

    private void setMenu(Stage stage) {
        MainMenu mainMenu = new MainMenu(stage);
        menuBar = mainMenu.createMenuBar();
        pane.getChildren().add(menuBar);
    }

}
