/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import javafx.scene.image.ImageView;

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

}
