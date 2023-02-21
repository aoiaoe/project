package niukewang.search_sort;

/**
 * 寻找峰值,
 * 如输入[2,4,1,2,7,8,4]时，会形成两个山峰，一个是索引为1，峰值为4的山峰，另一个是索引为5，峰值为8的山峰
 */
public class _19_FindPeakElement {
    public static void main(String[] args) {
        int[] arr = {1,2,1,3,5,6,4};
        System.out.println(findPeakElement2(arr));
    }

    /**
     * 因为题目将数组边界看成最小值，而我们只需要找到其中一个波峰，
     * 因此只要不断地往高处走，一定会有波峰。那我们可以每次找一个标杆元素，
     * 将数组分成两个区间，每次就较高的一边走，因此也可以用分治来解决，而标杆元素可以选择区间中点。
     * @param nums
     * @return
     */
    public static int findPeakElement2 (int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        //二分法
        while(left < right){
            int mid = (left + right) / 2;
            //右边是往下，不一定有坡峰
            if(nums[mid] > nums[mid + 1])
                right = mid;
                //右边是往上，一定能找到波峰
            else
                left = mid + 1;
        }
        //其中一个波峰
        return right;
    }
    public static int findPeakElement (int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        } else if(nums.length == 1){
            return 0;
        } else if(nums.length == 2){
            return nums[0] > nums[1] ? 0 : 1;
        }
        // write code here
        int end = nums.length -1;
        for (int i = 0; i < end; i++) {
            if(i == 0 && nums[i] > nums[i + 1]){
                return 0;
            }else if(i+1 == end && nums[end] > nums[i]){
                return end;
            } else {
                if(nums[i] > nums[i-1] && nums[i] > nums[i + 1]){
                    return i;
                }
            }
        }
        return -1;
    }
}
