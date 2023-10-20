package leetcode;

public class _4_FindMedianSortedArrays {

    public static void main(String[] args) {
        int[] m = {};
        int[] n = {1,2,3};
        System.out.println(findMedianSortedArrays(m, n));
    }


    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        boolean odd = (m + n) % 2 == 0;
        int mid = (m + n) >> 1 + (odd ? 1 : 0);

        // 记录循环数
        int count = 0;
        int i = 0, j = 0;
        int x = 0,y = 0;
        while (count < mid){
//            if(i++ < m)
        }
        return 0.0;
    }


}