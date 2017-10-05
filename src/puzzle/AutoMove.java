package puzzle;

import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.shape.Path;

/**
 *
 * @author zpppppp
 */
public class AutoMove extends Move {

    private PathTransition pathTransition;
    
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
        pathTransition = getPathTransition(currentCell, path);
        setPathTransition(currentCell, emptyCell);
    }

    //获取当前需要移动的Cell
    private Cell getCurrentCell(int currentEmptyCellIndex, char nextDirection) {
        int index = findCurrentCellIndex(currentEmptyCellIndex, nextDirection);
        return cellsList.get(index);
    }

    //获取需要移动的Cell的下标
    private int findCurrentCellIndex(int currentEmptyCellIndex, char nextDirection) {
        int nextEmptyCellIndex = currentEmptyCellIndex;
        int ORDER = (int) Math.sqrt(cellsList.size());  //上下左右分别为减加阶数和减加1
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

    private void setPathTransition(Cell currentCell,Cell emptyCell) {
        pathTransition.setOnFinished((ActionEvent actionEvent) -> {
            swap(currentCell, emptyCell);
            countBoard.updateNumberOfMovements();
            if (checkedSolved(cellsList)) {
                stopCountAndPopupWindow();
            }
        });
    }
}
