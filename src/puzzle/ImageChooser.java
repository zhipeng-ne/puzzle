/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author zpppppp
 */
public class ImageChooser {

    private FileChooser fileChooser = new FileChooser();
    private File file;

    public ImageChooser() {
    }

    public void start(Stage stage) {
        fileChooser.setTitle("Image Chooser");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpeg", "*.gif", "*.bmp"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All File", "*."));
        file = fileChooser.showOpenDialog(stage);
    }

    public String getImagePath() {
        if (file != null) {
            return file.toURI().toString();
        }else {
            return "";
        }
    }
}
