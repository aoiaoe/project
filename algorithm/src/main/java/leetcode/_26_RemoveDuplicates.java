package leetcode;

import java.util.Arrays;

public class _26_RemoveDuplicates {
    public static void main(String[] args) {
        int[] arr = {1,1,2};
//        int[] arr = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(Arrays.toString(arr));
        System.out.println(removeDuplicates(arr));
    }

    public static int removeDuplicates(int[] nums) {

        int index = 0;
        int length = nums.length;
        int i = 1;
        while (i < length){
            if(nums[i] != nums[index]){
                nums[++index] = nums[i];
            }
            i++;
        }
        System.out.println(Arrays.toString(nums));
        return index + 1;
    }

}
