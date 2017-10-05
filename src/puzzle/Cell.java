/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import javafx.scene.image.ImageView;

/**
 *
 * @author zpppppp
 */
public class Cell {

    private int x;                  //在nxn的矩阵中的x坐标
    private int y;                  //在nxn的矩阵中的y坐标
    private int validIndex;         //图块的应该在的正确位置
    private int currentIndex;       //图块的当前位置
    private ImageView ImageView;    //图块的图像

    public Cell(int x, int y, ImageView initialImageView,int validIndex,int currentIndex) {
       
        this.x = x;
        this.y = y;
        this.ImageView = initialImageView;
        this.validIndex=validIndex;
        this.currentIndex=currentIndex;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public ImageView getImageView() {
        return this.ImageView;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getValidIndex() {
        return validIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setImageView(ImageView imageView){
        this.ImageView=imageView;
    }

    public boolean isEmpty(){
        return this.ImageView==null;
    }

    public boolean isSolved(){
        return this.validIndex==this.currentIndex;
    }
    public String toString(){
        return "x: "+this.x+" y: "+this.y;
    }
    
}
    