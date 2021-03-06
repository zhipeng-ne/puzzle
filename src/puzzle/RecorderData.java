/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author zpppppp
 */
//PS：序列化方法可以自己写
public class RecorderData {

    private SimpleStringProperty name;
    private SimpleIntegerProperty times;
    private SimpleIntegerProperty numberOfMovements;
    private Record recordData;

    public RecorderData() {
        this.name = new SimpleStringProperty("unknow");
        this.times = new SimpleIntegerProperty(0);
        this.numberOfMovements = new SimpleIntegerProperty(0);
        this.recordData=new Record();
    }

    public RecorderData(Record recordData) {
        this.name = new SimpleStringProperty(recordData.getName());
        this.times = new SimpleIntegerProperty(recordData.getTimes());
        this.numberOfMovements = new SimpleIntegerProperty(recordData.getNumberOfMovements());
        this.recordData=recordData;
    }

    public String getName() {
        return name.get();
    }

    public int getTimes() {
        return times.get();
    }

    public int getNumberOfMovements() {
        return numberOfMovements.get();
    }

    public Record getRecordData() {
        return recordData;
    }
}
