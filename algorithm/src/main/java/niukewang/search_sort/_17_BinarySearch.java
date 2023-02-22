package niukewang.search_sort;

public class _17_BinarySearch {

    public static void main(String[] args) {
        int[] arr = {-1,0,3,4,6,10,13,14};
        arr = new int[]{-1, 1};
        System.out.println(search(arr, 0));
    }
    public static int search (int[] nums, int target) {
        if(nums == null || nums.length == 0){
            return -1;
        }

        return binarySearch(nums, target, 0, nums.length - 1);
    }

    public static int binarySearch(int[] nums, int target, int start, int end){
        if(start > end){
            return -1;
        }
        // write code here
        int mid = (end + start) / 2;
        if(nums[mid] == target){
            return mid;
        } else if(nums[mid] > target){
            return binarySearch(nums, target, start, mid-1);
        }
        return binarySearch(nums, target, mid + 1, end);
    }

}
