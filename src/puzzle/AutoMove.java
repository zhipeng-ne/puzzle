package puzzle;

import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.shape.Path;
import puzzle.Cell;
import puzzle.CountBoard;
import puzzle.Move;
import static puzzle.Operation.checkedSolved;
import static puzzle.Operation.swap;

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

        Cell currentCell = findCurrentCell(cellsList, emptyCellIndex, nextDirection);
        if (currentCell == null) {
            return;
        }

        Path path = getPath(currentCell, emptyCell);

        PathTransition pathTransition = getPathTransition(currentCell, path);

        final Cell cellA = currentCell;
        final Cell cellB = emptyCell;

        pathTransition.setOnFinished((ActionEvent actionEvent) -> {
            swap(cellA, cellB);
            countBoard.updateNumberOfMovements();
            if (checkedSolved(cellsList)) {
                countBoard.stopCounting();
                AlertWindow alertWindow = new AlertWindow();
                alertWindow.start();
            }
        });
    }

    private Cell findCurrentCell(ArrayList<Cell> list, int currentEmptyCellIndex, char nextDirection) {
        int order = (int) Math.sqrt(list.size());
        Cell currentCell = null;
        int nextEmptyCellIndex = currentEmptyCellIndex;

        switch (nextDirection) {
            case 'u':
                nextEmptyCellIndex = currentEmptyCellIndex - order;
                break;
            case 'd':
                nextEmptyCellIndex = currentEmptyCellIndex + order;
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
        currentCell = getCurrentCell(list, nextEmptyCellIndex);
        return currentCell;
    }

    private Cell getCurrentCell(ArrayList<Cell> list, int index) {
        return list.get(index);
    }
}
