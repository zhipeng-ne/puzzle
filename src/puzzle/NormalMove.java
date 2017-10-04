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
import static puzzle.AutoBoard.syncMovement;
import static puzzle.AutoBoard.syncArray;
import static puzzle.AutoBoard.syncisMove;

/**
 *
 * @author zpppppp
 */
public class NormalMove extends Move {

    public NormalMove(ArrayList<Cell> cellsList, CountBoard countBoard, int CELLSIZE, double offsetX, double offsetY) {
        super(cellsList, countBoard, CELLSIZE, offsetX, offsetY);
    }

    public void move(Node node) {
        Cell currentCell = getCurrentCell(cellsList, node);
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

        pathTransition.setOnFinished(actionEvent -> {
            swap(currentCell, emptyCell);
            syncData();
            if (checkedSolved(cellsList)) {
                countBoard.stopCounting();
                countBoard.setDisableButton();
                AlertWindow alertWindow = new AlertWindow(countBoard.getUsedTimes(), countBoard.getNumberOfMovements());
                alertWindow.start();
            }
        });
    }

    private Cell getCurrentCell(ArrayList<Cell> list, Node node) {
        Cell currentCell = null;
        for (Cell tempCell : list) {
            if (tempCell.getImageView() == node) {
                currentCell = tempCell;
                break;
            }
        }
        return currentCell;
    }

    public void syncData() {
        countBoard.updateNumberOfMovements();
        AutoMove move = new AutoMove(cellsList, countBoard, super.getCELLSIZE(), super.getOffsetX(), super.getOffsetY());
        syncMovement(move);
        syncArray(Operation.getArray(cellsList));
        syncisMove(Boolean.TRUE);
    }
}
