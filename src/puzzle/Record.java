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
//这个类只是为了方便读写时能将数据序列化，因为SimpleStringProperty没有提供序列化方法，
//所以用这个类来嵌套到RecorderData类中
public class Record implements Serializable{
    
    private String name;              //记录者的名字
    private int times;                //用时
    private int numberOfMovements;    //移动次数

    public Record() {
        name="";
        times=0;
        numberOfMovements=0;
    }

    public Record(String name, int times, int numberOfMovements) {
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
