/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author zpppppp
 */
public class ReferPicture {

    private Image image;
    
    public ReferPicture(Image image) {
        this.image = image;

    }

    public ImageView getPicture() {
        ImageView referPicture = new ImageView(image);
        referPicture.setFitHeight(300);
        referPicture.setFitWidth(300);
        return referPicture;
    }
}
