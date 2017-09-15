/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author zpppppp
 */
public class ReferPicture {

    private Image image;
    private int size;
    private int order;

    public ReferPicture(Image image, int size, int order) {
        this.image = image;
        this.size = size;
        this.order = order;
    }
    
    public ImageView getPicture() {
        ImageView referPicture = new ImageView(image);
        Rectangle2D rect = new Rectangle2D(0, 0, size * order, size * order);
        referPicture.setViewport(rect);
        referPicture.setFitHeight(300);
        referPicture.setFitWidth(300);
        return referPicture;
    }
}
