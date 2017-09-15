/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.io.Serializable;

/**
 *
 * @author zpppppp
 */
public class RecordData implements Serializable{
    
    private String name;
    private int times;
    private int numberOfMovements;

    public RecordData() {
        name="";
        times=0;
        numberOfMovements=0;
    }

    public RecordData(String name, int times, int numberOfMovements) {
        this.name = name;
        this.times = times;
        this.numberOfMovements = numberOfMovements;
    }

    public String getName() {
        return name;
    }

    public int getTimes() {
        return times;
    }

    public int getNumberOfMovements() {
        return numberOfMovements;
    }
    
    
}
