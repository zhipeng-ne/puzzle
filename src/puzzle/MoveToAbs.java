/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.scene.Node;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author zpppppp
 */
public class MoveToAbs extends MoveTo {

    public MoveToAbs(Node node) {
        super(node.getLayoutBounds().getWidth() / 2, node.getLayoutBounds().getHeight() / 2);
    }

    public MoveToAbs(Node node, double x, double y) {
        super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2,
                y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
    }
}
