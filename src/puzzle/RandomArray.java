/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.Random;

/**
 *
 * @author zpppppp
 */
public class RandomArray {

    //    生成num个不重复的逆序数为偶数的排列
    public static int[] getEvenPermutation(int num) {
        int[] ran = generateRandomArray(num);
        if (getNumberOfInversions(ran) % 2 != 0) {
            int temp = ran[0];         //如果不是偶排列则交换下标为0和1的数，当然其他也可以
            ran[0] = ran[1];
            ran[1] = temp;
        }    
        return ran;
    }

    //    生成num个不重复数
    public static int[] generateRandomArray(int num) {
        int r[] = new int[num];
        Random random = new Random();
        for (int i = 0; i < r.length -1; i++) {
            r[i] = random.nextInt(num -1);
            for (int j = 0; j < i; j++) {
                if (r[i] == r[j]) {
                    i--;
                    break;
                }
            }
        }    //这里把最大的数放在了排列的末尾，一开始是为了美观，后来发现这样好像可以降低搜索难度—_—!
        r[r.length - 1] = num - 1;  
        return r;
    }

    //    求逆序数
    public static int getNumberOfInversions(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    sum++;
                }
            }
        }
        return sum;
    }
}
