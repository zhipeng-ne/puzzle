package puzzle;

import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.shape.Path;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zpppppp
 */
public class AutoMove extends Move {

    public AutoMove(ArrayList<Cell> cellsList, CountBoard countBoard, int CELLSIZE, double offsetX, double offsetY) {
        super(cellsList, countBoard, CELLSIZE, offsetX, offsetY);

    }

    public void move(char nextDirection) {
        Cell emptyCell = findEmptyCell(cellsList);
        if (emptyCell == null) {
            return;
        }
        int emptyCellIndex = emptyCell.getValidIndex();

        Cell currentCell = getCurrentCell(emptyCellIndex, nextDirection);
        if (currentCell == null) {
            return;
        }

        Path path = getPath(currentCell, emptyCell);
        PathTransition pathTransition = getPathTransition(currentCell, path);

        pathTransition.setOnFinished((ActionEvent actionEvent) -> {
            swap(currentCell, emptyCell);
            countBoard.updateNumberOfMovements();
            countBoard.setIsPause(Boolean.TRUE);
            if (checkedSolved(cellsList)) {
                countBoard.stopCounting();
                countBoard.setDisableButton();
                AlertWindow alertWindow = new AlertWindow();
                alertWindow.start();
            }
        });
    }

    private Cell getCurrentCell(int currentEmptyCellIndex, char nextDirection) {
        int index = findCurrentCellIndex(currentEmptyCellIndex, nextDirection);
        return cellsList.get(index);
    }

    private int findCurrentCellIndex(int currentEmptyCellIndex, char nextDirection) {
        int nextEmptyCellIndex = currentEmptyCellIndex;
        int ORDER = (int) Math.sqrt(cellsList.size());
        switch (nextDirection) {
            case 'u':
                nextEmptyCellIndex = currentEmptyCellIndex - ORDER;
                break;
            case 'd':
                nextEmptyCellIndex = currentEmptyCellIndex + ORDER;
                break;
            case 'l':
                nextEmptyCellIndex = currentEmptyCellIndex - 1;
                break;
            case 'r':
                nextEmptyCellIndex = currentEmptyCellIndex + 1;
                break;
            default:
                break;
        }
        return nextEmptyCellIndex;
    }

}
