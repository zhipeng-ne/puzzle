/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.shape.Path;
import static puzzle.AutoBoard.update;

import static puzzle.Operation.checkedSolved;
import static puzzle.Operation.swap;

/**
 *
 * @author zpppppp
 */
public class NormalMove extends Move {

    public NormalMove(ArrayList<Cell> cellsList, CountBoard countBoard, int CELLSIZE, double offsetX, double offsetY) {
        super(cellsList, countBoard, CELLSIZE, offsetX, offsetY);
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

        Path path = getPath(currentCell, emptyCell);

        PathTransition pathTransition = getPathTransition(currentCell, path);

        final Cell cellA = currentCell;
        final Cell cellB = emptyCell;

        pathTransition.setOnFinished(actionEvent -> {

            //test(getArray(cellsList));
            swap(cellA, cellB);
            countBoard.updateNumberOfMovements();
            
            updateData();
            //test(getArray(cellsList));
            //countBoard.updateNumberOfMovements(numberOfMovements++);
            if (checkedSolved(cellsList)) {
                countBoard.stopCounting();
                AlertWindow alertWindow = new AlertWindow(countBoard.getUsedTimes(), countBoard.getNumberOfMovements());
                alertWindow.start();
            }
        });
    }

    private Cell findCurrentCell(ArrayList<Cell> list, Node node) {
        Cell currentCell = null;
        for (Cell tempCell : list) {
            if (tempCell.getImageView() == node) {
                currentCell = tempCell;
                break;
            }
        }
        return currentCell;
    }

    public void updateData() {
        AutoMove move = new AutoMove(cellsList, countBoard, super.getCELLSIZE(), super.getOffsetX(), super.getOffsetY());
        update(move);
        AutoBoard.updateArray(Operation.getArray(cellsList));
    }
}
