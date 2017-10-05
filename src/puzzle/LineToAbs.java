/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.scene.Node;
import javafx.scene.shape.LineTo;

/**
 *
 * @author zpppppp
 */
public class LineToAbs extends LineTo {

    public LineToAbs(Node node, double x, double y) {
        super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2,
                y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
    }
}
