/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import static puzzle.Operation.checkedSolved;
import static puzzle.Operation.getArray;
import static puzzle.Operation.swap;

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
    public static final double offsetX = 30;
    public static final double offsetY = 30;
    //每行每列的格子数及格子大小
    public static int ORDER;
    public int cellSize = 100;
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
        cellSize = (int) image.getWidth() / ORDER;
        int[] ran = RandomArray.getEvenPermutation(ORDER * ORDER);

//测试        
        System.out.println("");
        for (int i : ran) {
            System.out.print(i + " ");
        }
        System.out.println();
//
        for (int i = 0; i < ran.length; i++) {
            ImageView imageBlock = new ImageView(image);

            int minX = ran[i] % ORDER;
            int minY = ran[i] / ORDER;
            Rectangle2D rectangle2D = new Rectangle2D(cellSize * minX,
                    cellSize * minY, cellSize, cellSize);
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
                move((Node) mouseEvent.getSource());
            });

            ImageView currentImageView = currentCell.getImageView();
            imageView.relocate(currentCell.getX() * cellSize + offsetX, currentCell.getY() * cellSize + offsetY);
            pane.getChildren().add(currentImageView);
        }

        ReferPicture refer = new ReferPicture(image, cellSize, ORDER);
        ImageView referPicture = refer.getPicture();
        referPicture.relocate(cellSize * ORDER + offsetX + 30, offsetY);
        pane.getChildren().add(referPicture);

        GridPane gridPane = countBoard.createBoard();
        gridPane.relocate(cellSize * ORDER + offsetX + 30, 340);
        pane.getChildren().add(gridPane);

        GridPane autoPane = createAutoPuzzleBoard();
        autoPane.relocate(cellSize * ORDER + offsetX + 30, 450);
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

    public GridPane createAutoPuzzleBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(2);

        Button getPathButton = new Button("Get Path");
        Text pathRecommend = new Text();
        Label pathLabel = new Label("Movement Routine :");
        Text pathText = new Text("");

        Label numberLabel = new Label("Number of Movements :");
        Text numberText = new Text("");

        Label timeLabel = new Label("Search Times :");
        Text timeText = new Text("");

        Button previousButton = new Button("Previous");
        Button nextButton = new Button("Next");
        Button autoButton = new Button("Auto Puzzle");
        pathRecommend.setStyle("-fx-fill: red;");
        if (cellsList.size() == 25) {
            pathRecommend.setText("It is no recommended!");
        }
        gridPane.add(getPathButton, 0, 0);
        gridPane.add(pathRecommend, 1, 0);
        gridPane.add(pathLabel, 0, 1);
        gridPane.add(pathText, 1, 1);
        gridPane.add(numberLabel, 0, 2);
        gridPane.add(numberText, 1, 2);
        gridPane.add(timeLabel, 0, 3);
        gridPane.add(timeText, 1, 3);
        gridPane.add(previousButton, 0, 4);
        gridPane.add(nextButton, 1, 4);
        gridPane.add(autoButton, 0, 5);

        StringBuilder routine = new StringBuilder();

        getPathButton.setOnMouseClicked(e -> {
            double startTime = System.currentTimeMillis();
            IDAStar iDAStar = new IDAStar(getArray(cellsList));
            iDAStar.init();
            double endTime = System.currentTimeMillis();

            System.out.println(iDAStar.getPath());
            routine.append(String.copyValueOf(iDAStar.getPath()));
            pathText.setText(String.copyValueOf(iDAStar.getPath()));
            numberText.setText(String.valueOf(iDAStar.getPath().length));
            timeText.setText(String.valueOf(endTime - startTime) + " ms");

            directionIndex = 0;
        });

        previousButton.setOnMouseClicked(e -> {
//            if (routine.length() > 0 && directionIndex <= routine.length() && directionIndex > 0) {
//                directionIndex--;
//                move(routine.charAt(directionIndex), true);
//            }
        });

        nextButton.setOnMouseClicked(e -> {
//            if (routine.length() > 0 && directionIndex < routine.length()) {
//                move(routine.charAt(directionIndex), false);
//                directionIndex++;
//            }
        });

        EventHandler<ActionEvent> eventHandler = e -> {
                move(routine.charAt(directionIndex), false);
                directionIndex++;

        };
        EventHandler<ActionEvent> enableButton = e -> {
            nextButton.setDisable(false);
            previousButton.setDisable(false);
        };
        autoButton.setOnMouseClicked(e -> {
            nextButton.setDisable(true);
            previousButton.setDisable(true);
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(300), eventHandler));
            animation.setCycleCount(routine.length());
            animation.setOnFinished(enableButton);
            animation.play();
        });
        return gridPane;
    }

    public void move(char nextDirection, boolean isPrevious) {
        Cell emptyCell = findEmptyCell(cellsList);
        if (emptyCell == null) {
            return;
        }
        int emptyCellIndex = emptyCell.getValidIndex();

        Cell currentCell = findCurrentCell(cellsList, emptyCellIndex, nextDirection, isPrevious);
        if (currentCell == null) {
            return;
        }

        Path routine = new Path();
        routine.getElements().add(new Operation.MoveToAbs(currentCell.getImageView(),
                currentCell.getX() * cellSize + offsetX, currentCell.getY() * cellSize + offsetY));
        routine.getElements().add(new Operation.LineToAbs(currentCell.getImageView(),
                emptyCell.getX() * cellSize + offsetX, emptyCell.getY() * cellSize + offsetY));

        PathTransition pathTransition = getPathTransition(currentCell, routine);

        final Cell cellA = currentCell;
        final Cell cellB = emptyCell;

        pathTransition.setOnFinished(actionEvent -> {

            swap(cellA, cellB);
            if (checkedSolved(cellsList)) {
                countBoard.stopCounting();
                AlertWindow alertWindow = new AlertWindow();
                alertWindow.start();
            }
        });
    }

    public Cell findCurrentCell(ArrayList<Cell> list, int currentEmptyCellIndex, char nextDirection, boolean isPrevious) {
        int order = (int) Math.sqrt(list.size());
        Cell currentCell = null;
        int nextEmptyCellIndex = currentEmptyCellIndex;
        if (isPrevious) {
            switch (nextDirection) {
                case 'u':
                    nextEmptyCellIndex = currentEmptyCellIndex + order;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                case 'd':
                    nextEmptyCellIndex = currentEmptyCellIndex - order;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                case 'l':
                    nextEmptyCellIndex = currentEmptyCellIndex + 1;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                case 'r':
                    nextEmptyCellIndex = currentEmptyCellIndex - 1;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                default:
                    break;
            }
        } else {
            switch (nextDirection) {
                case 'u':
                    nextEmptyCellIndex = currentEmptyCellIndex - order;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                case 'd':
                    nextEmptyCellIndex = currentEmptyCellIndex + order;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                case 'l':
                    nextEmptyCellIndex = currentEmptyCellIndex - 1;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                case 'r':
                    nextEmptyCellIndex = currentEmptyCellIndex + 1;
                    currentCell = getCurrentCell(list, nextEmptyCellIndex);
                    break;
                default:
                    break;
            }
        }
        return currentCell;
    }

    public Cell getCurrentCell(ArrayList<Cell> list, int index) {
        Cell currentCell = null;
        int i = 0;
        for (Cell tempCell : list) {
            if (i == index) {
                currentCell = tempCell;
                break;
            }
            i++;
        }
        return currentCell;
    }

    public void move(Node node) {

        Cell currentCell = findCurrentCell(cellsList, node);
        if (currentCell == null) {
            return;
        }
        //获取空Cell
        Cell emptyCell = findEmptyCell(cellsList);
        if (emptyCell == null) {
            return;
        }
        //因为只有与空格子相邻的，才可以移动，所以坐标相差为1
        int steps = (int) (Math.abs(currentCell.getX() - emptyCell.getX())
                + Math.abs(currentCell.getY() - emptyCell.getY()));
        if (steps != 1) {
            return;
        }
        if (countBoard.getIsPause() || checkedSolved(cellsList)) {
            return;
        }
        Path path = new Path();
        path.getElements().add(new Operation.MoveToAbs(currentCell.getImageView(),
                currentCell.getX() * cellSize + offsetX, currentCell.getY() * cellSize + offsetY));
        path.getElements().add(new Operation.LineToAbs(currentCell.getImageView(),
                emptyCell.getX() * cellSize + offsetX, emptyCell.getY() * cellSize + offsetY));

        PathTransition pathTransition = getPathTransition(currentCell, path);

        final Cell cellA = currentCell;
        final Cell cellB = emptyCell;

        pathTransition.setOnFinished(actionEvent -> {
            swap(cellA, cellB);
            countBoard.updateNumberOfMovements(numberOfMovements++);
            if (checkedSolved(cellsList)) {
                countBoard.stopCounting();
                AlertWindow alertWindow = new AlertWindow(countBoard.getUsedTimes(), countBoard.getNumberOfMovements());
                alertWindow.start();
            }
        });

    }

    public PathTransition getPathTransition(Cell currentCell, Path routine) {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(100));
        pathTransition.setNode(currentCell.getImageView());
        pathTransition.setPath(routine);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();

        return pathTransition;
    }

    public Cell findCurrentCell(ArrayList<Cell> list, Node node) {
        Cell currentCell = null;
        for (Cell tempCell : list) {
            if (tempCell.getImageView() == node) {
                currentCell = tempCell;
                break;
            }
        }
        return currentCell;
    }

    public Cell findEmptyCell(ArrayList<Cell> list) {
        Cell emptyCell = null;
        for (Cell tempCell : list) {
            if (tempCell.isEmpty()) {
                emptyCell = tempCell;
                break;
            }
        }
        return emptyCell;
    }

}
