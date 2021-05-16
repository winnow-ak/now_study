package winnow.ak.study.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class QuickStor {
    public static void quickSort(int a[], int l, int r) {
        if (l >= r)
            return;

        int i = l;
        int j = r;
        int key = a[l];//选择第一个数为key

        while (i < j) {

            while (i < j && a[j] >= key) {//从右向左找第一个小于key的值
                j--;
            }
            //  0 6,
            if (i < j) {
                a[i] = a[j];
                i++;
            }
            while (i < j && a[i] < key) {//从左向右找第一个大于key的值
                i++;
            }
            //1,6
            if (i < j) {
                a[j] = a[i];
                j--;
            }
            //1.5
            System.out.println(i + ":" + j + ",result = " + Arrays.toString(a));
        }
        //i == j
        a[i] = key;
        System.out.println(Arrays.toString(a));
        quickSort(a, l, i - 1);//递归调用
        quickSort(a, i + 1, r);//递归调用
    }

    public static void main(String[] args) {
        int arr[] = {2, 3, 5, 7, 9, 0, 1, 6, 8};
        System.out.println("start = " + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("rsult = " + Arrays.toString(arr));
    }
}
