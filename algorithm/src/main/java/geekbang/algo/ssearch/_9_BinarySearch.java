package geekbang.algo.ssearch;

/**
 * 二分查找，有重复元素的数组， 且从小到大排序
 * 求中间节点的时候：
 *    不使用 mid = (start + end ) >> 1，而采用mid = start + ((end - start) >> 1);
 *    是因为如果start和end如果都特别大的话，那么可能会溢出变为负数
 *    不过这是一个极端情况，很少有如此多的数据
 *
 * @author jzm
 * @date 2023/3/29 : 11:12
 */
public class _9_BinarySearch {

    public static void main(String[] args) {
        int arr[] = {1, 3, 3, 3, 4, 4, 4, 6, 6, 7};
        int target = 1;
        System.out.println(searchFirstEq(arr, target));
        System.out.println(searchLastEq(arr, target));
        System.out.println(searchFirstGt(arr, 3));
        System.out.println(searchLastLt(arr, 5));
    }

    // -------------------------自己实现

    /**
     * 查找第一个等于给定值的元素
     *
     * @return
     */
    public static int searchFirstEq(int arr[], int num) {
        int start = 0, end = arr.length - 1;
        int tmpIndex = -1;
        int mid;
        while (start <= end) {
            mid = start + ((end - start) >> 1);
            if (arr[mid] == num) {
                tmpIndex = mid;
                end = mid - 1;
                // 优化
                if(mid == 0 || arr[mid-1] != num){
                    break;
                }
            } else if (arr[mid] > num) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return tmpIndex;
    }

    /**
     * 查找最后一个等于给定值的元素
     *
     * @param arr
     * @param num
     * @return
     */
    public static int searchLastEq(int arr[], int num) {
        int start = 0, end = arr.length - 1;
        int tmpIndex = -1;
        int mid;
        while (start <= end) {
            mid = start + ((end - start) >> 1);
            if (arr[mid] == num) {
                tmpIndex = mid;
                start = mid + 1;
                // 优化
                if(mid == arr.length - 1 || arr[mid+1] != num){
                    break;
                }
            } else if (arr[mid] > num) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return tmpIndex;
    }

    /**
     * 查找第一个大于等于给定值的元素
     *
     * @param arr
     * @param num
     * @return
     */
    public static int searchFirstGt(int arr[], int num) {
        int start = 0, end = arr.length - 1;
        int tmpIndex = -1;
        int mid;
        while (start <= end) {
            mid = start + ((end - start) >> 1);
            if (arr[mid] < num) {
                start = mid + 1;
            } else {
                end = mid - 1;
                tmpIndex = mid;
                // 优化
                if(mid == 0 || arr[mid-1] < num){
                    break;
                }
            }
        }
        return tmpIndex;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     *
     * @param arr
     * @param num
     * @return
     */
    public static int searchLastLt(int arr[], int num) {
        int start = 0, end = arr.length - 1;
        int tmpIndex = -1;
        int mid;
        while (start <= end) {
            mid = start + ((end - start) >> 1);
            if (arr[mid] <= num) {
                start = mid + 1;
                tmpIndex = mid;
                // 优化
                if(mid == arr.length - 1 || arr[mid+1] > num){
                    break;
                }
            } else {
                end = mid - 1;
            }
        }
        return tmpIndex;
    }


    // --------------极客时间

    /**
     * 查找第一个值等于给定值的元素
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int bsearch(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] >= value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (low < n && a[low] == value) return low;
        else return -1;
    }
}
