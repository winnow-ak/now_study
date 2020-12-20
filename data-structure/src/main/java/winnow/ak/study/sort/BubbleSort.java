package winnow.ak.study.sort;

import java.util.Arrays;

/**
 * 时间复杂度O(N^2)
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class BubbleSort {

    public static void sort(int [] arr,boolean asc){
        for (int i = 0; i <arr.length; i++) {
            for (int j = i+ 1; j <arr.length ; j++) {
                if (asc){
                    if (arr[i] > arr[j]){
                        swap(arr,i,j);
                    }
                }else {
                    if (arr[i] < arr[j]){
                        swap(arr,i,j);
                    }
                }
            }
        }
    }

    private static void swap(int[] arr,int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1,234,3,4,6,-1,0,2};

        sort(arr,true);
        System.out.println(Arrays.toString(arr));
        sort(arr,false);
        System.out.println(Arrays.toString(arr));
    }
}
