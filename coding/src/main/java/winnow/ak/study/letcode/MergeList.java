package winnow.ak.study.letcode;

import java.util.Arrays;

/**
 * @Author: Winyu
 * @Date: 2021/6/27
 */
public class MergeList {
    public static void main(String[] args) {

        int[] nums = new int[]{1, 11, 7, 3, 2};
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));

        int[] nums1 = new int[]{1, 3, 7, 0, 0, 0};
        int[] nums2 = new int[]{2, 5, 6};
//        merge(nums1, 3, nums2, 3);
        System.out.println();
//        merge1(nums1, 3, nums2, 3);
        merge2(nums1, 3, nums2, 3);

    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2 == null) {
            return;
        }
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        Arrays.sort(nums1);
        for (int i = 0; i < nums1.length; i++) {
            System.out.print(nums1[i]);
        }
    }

    public static void merge1(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
        for (int i = 0; i < nums1.length; i++) {
            System.out.print(nums1[i]);
        }
    }

    public static void merge2(int[] nums1, int m, int[] nums2, int n) {
        int len1 = m - 1;
        int len2 = n - 1;
        int len = m + n - 1;
        while(len1 >= 0 && len2 >= 0) {
            // 注意--符号在后面，表示先进行计算再减1，这种缩写缩短了代码
            nums1[len--] = nums1[len1] > nums2[len2] ? nums1[len1--] : nums2[len2--];
        }
        for (int i = 0; i < nums1.length; i++) {
            System.out.print(nums1[i]);
        }
    }
}
