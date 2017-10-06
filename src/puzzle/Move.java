/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
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

    public int getCELLSIZE() {
        return CELLSIZE;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
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

        return pathTransition;
    }
    //寻找空Cell
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

    //交换图块
    public void swap(Cell cell1, Cell cell2) {
        ImageView temp = cell1.getImageView();
        cell1.setImageView(cell2.getImageView());
        cell2.setImageView(temp);

        int tmp = cell1.getCurrentIndex();
        cell1.setCurrentIndex(cell2.getCurrentIndex());
        cell2.setCurrentIndex(tmp);
    }

    //检查拼图是否完成
    public boolean checkedSolved(ArrayList<Cell> cells) {
        for (Cell cell : cells) {
            if (!cell.isSolved()) {
                return false;
            }
        }
        return true;
    }

    public void stopCountAndPopupWindow() {
        countBoard.stopCounting();     //如果完成，停止计数
        countBoard.setDisableButton(); //将按钮设为不可见
        AlertWindow alertWindow = new AlertWindow(countBoard.getUsedTimes(), countBoard.getNumberOfMovements());
        alertWindow.start();           //弹出提示窗口
    }

    public void test(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();

    }
}
