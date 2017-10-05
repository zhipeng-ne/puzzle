/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *读文件
 * @author zpppppp
 */
public class FileReader {

    private int ORDER;    //游戏的难度
    private File file;
    
    public ObservableList<RecorderData> data = FXCollections.observableArrayList();

    public FileReader() {
        file = new File("src/data/data.dat");
    }

    public FileReader(int order) {
        this.ORDER = order;
        if (this.ORDER == 3) {
            file = new File("src/data/data.dat");
        } else if (this.ORDER == 4) {
            file = new File("src/data/data1.dat");
        } else {
            file = new File("src/data/data2.dat");
        }

    }

    public void read() throws ClassNotFoundException, IOException {
        try (ObjectInputStream input
                = new ObjectInputStream(new FileInputStream(file));) {
            Record record;
            while ((record = (Record) input.readObject()) != null) {
                data.add(new RecorderData(record));
            }
        } catch (EOFException e) {
        } catch (ClassNotFoundException | IOException ex) {
        }
    }

    public ObservableList<RecorderData> getData() {
        return data;
    }
}
