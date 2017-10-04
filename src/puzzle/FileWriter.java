/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.collections.ObservableList;

/**
 *
 * @author zpppppp
 */
public class FileWriter {

    private int order;
    private File file;
    public ObservableList<RecorderData> data;

    public FileWriter() {
        file = new File("src/data/data.dat");
    }

    public FileWriter(ObservableList<RecorderData> data, int order) {
        this.data = data;
        this.order = order;
        if (this.order == 3) {
            file = new File("src/data/data.dat");
        } else if (this.order == 4) {
            file = new File("src/data/data1.dat");
        } else {
            file = new File("src/data/data2.dat");
        }
    }

    public void write() throws IOException {
        try (ObjectOutputStream output
                = new ObjectOutputStream(new FileOutputStream(file));) {
            for (RecorderData recorderData : data) {
                Record recordData = recorderData.getRecordData();
                output.writeObject(recordData);
            }
            output.writeObject(null);
        } catch (IOException ex) {
            System.out.println("error");
        }
    }

    public ObservableList<RecorderData> getData() {
        return data;
    }
}
