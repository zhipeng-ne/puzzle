/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.Random;

/**
 *这是一个工具类
 * @author zpppppp
 */
public class RandomArray {
      /*
    生成n个不重复的逆序数为偶数的数字
     */
    public static int[] getEvenPermutation(int num) {
        int[] ran = new int[num];
        boolean flag = true;
        while (flag) {
            int[] temp = generateRandomArray(num);
            int numberOfInvesions = getNumberOfInversions(temp);
            if ((numberOfInvesions % 2 == 0) && (numberOfInvesions != 0)){
                ran = temp;
                flag = false;
            }
        }
        return ran;
    }
    
    /*
    生成n个不重复数
     */
    public static int[] generateRandomArray(int num) {
        int r[] = new int[num+1];

        Random random = new Random();
        for (int i = 0; i < num; i++) {
            r[i] = random.nextInt(num);
            for (int j = 0; j < i; j++) {
                while (r[i] == r[j]) {
                    i--;
                    break;
                }
            }
        }
        r[r.length-1]=num;
        return r;
    }
    
     /*
    求逆序数
     */
    public static int getNumberOfInversions(int[] num) {
        int sum = 0;
        for (int i = 0; i < num.length - 1; i++) {
            for (int j = i + 1; j < num.length; j++) {
                if (num[i] > num[j]) {
                    sum++;
                }
            }
        }
        return sum;
    }
}
