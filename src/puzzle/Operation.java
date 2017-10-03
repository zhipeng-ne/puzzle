/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author zpppppp
 */
public class Operation {
    public static int[] getArray(ArrayList<Cell> list) {
        int[] array = new int[list.size()];
        int i = 0;
        for (Cell a : list) {
            array[i++] = a.getCurrentIndex();
        }
        return array;
    }
    public static boolean checkedSolved(ArrayList<Cell> cells) {
        boolean allSolved = true;
        for (Cell cell : cells) {
            if(!cell.isSolved()){
                allSolved=false;
                break;
            }
        }
        return allSolved;
    }



    public static void swap(Cell cell1, Cell cell2) {
        ImageView temp = cell1.getImageView();
        cell1.setImageView(cell2.getImageView());
        cell2.setImageView(temp);

        int tmp = cell1.getCurrentIndex();
        cell1.setCurrentIndex(cell2.getCurrentIndex());
        cell2.setCurrentIndex(tmp);
    }
 
    
    
}
