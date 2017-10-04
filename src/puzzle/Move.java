/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 *
 * @author zpppppp
 */
public class Move {

    public ArrayList<Cell> cellsList;
    public CountBoard countBoard;
    private int CELLSIZE;
    private double offsetX;
    private double offsetY;

    public Move(ArrayList<Cell> cellsList, CountBoard countBoard, int CELLSIZE, double offsetX, double offsetY) {
        this.cellsList = cellsList;
        this.countBoard = countBoard;
        this.CELLSIZE = CELLSIZE;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Path getPath(Cell currentCell, Cell emptyCell) {
        Path path = new Path();
        path.getElements().add(new MoveToAbs(currentCell.getImageView(),
                currentCell.getX() * CELLSIZE + offsetX, currentCell.getY() * CELLSIZE + offsetY));
        path.getElements().add(new LineToAbs(currentCell.getImageView(),
                emptyCell.getX() * CELLSIZE + offsetX, emptyCell.getY() * CELLSIZE + offsetY));
        return path;
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

    public int getCELLSIZE() {
        return CELLSIZE;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void test(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();

    }

    public static class MoveToAbs extends MoveTo {

        public MoveToAbs(Node node) {
            super(node.getLayoutBounds().getWidth() / 2, node.getLayoutBounds().getHeight() / 2);
        }

        public MoveToAbs(Node node, double x, double y) {
            super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2,
                    y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
        }
    }

    public static class LineToAbs extends LineTo {

        public LineToAbs(Node node, double x, double y) {
            super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2,
                    y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
        }
    }

    public void swap(Cell cell1, Cell cell2) {
        ImageView temp = cell1.getImageView();
        cell1.setImageView(cell2.getImageView());
        cell2.setImageView(temp);

        int tmp = cell1.getCurrentIndex();
        cell1.setCurrentIndex(cell2.getCurrentIndex());
        cell2.setCurrentIndex(tmp);
    }

    public boolean checkedSolved(ArrayList<Cell> cells) {
        boolean allSolved = true;
        for (Cell cell : cells) {
            if (!cell.isSolved()) {
                allSolved = false;
                break;
            }
        }
        return allSolved;
    }
}
