package niukewang.search_sort;

/**
 * 判断一个数是否存在于二维数组中
 * 前置条件: 二维数组中，每个数组长度相同，都升序排列，且每个数组中，索引相同的位置的数字升序排列
 * int arr[][] = {
 *     {1, 2, 3, 4},
 *     {2, 3, 4, 5}
 * }
 */
public class _18_TwoDimensionArrayFind {

    public static void main(String[] args) {
        int arr[][] = {
                {1,2,3,4,5},
                {6,7,8,9,10},
                {11,12,13,14,15},
                {16,17,18,19,20}
        };
        System.out.println(Find(111, arr));
    }
    /**
     * 利用二维数组的有效性
     * 例如:
     *  int arr[][] = {
     *                 {1,2,3,4,5},
     *                 {6,7,8,9,10},
     *                 {11,12,13,14,15},
     *                 {16,17,18,19,20}
     *         };
     *         如果要找10这个数组
     *         先找到二位数组长度length, 二维数组中每个数组的长度length2
     *         从左下角16开始,坐标为 arr[length-1][0], 16>10, 则将length--;
     *         得到arr[length-2][0] = 11 > 10, 继续将length--;
     *         得到arr[length-3][0] = 6 < 10, 则将length2++;
     *         得到arr[length-3][1] = 7 < 10, 继续lenght2++;
     *         知道arr[length-3][4] = 10 == 10, 返回true;
     *
     *         因为从左下角开始, 代表的是最后一个一维数组,
     *         因为是升序,如果大于目标,则目标一定在前面一个一维数组中, 所以将length--;
     *         如果小于目标， 则目标一定在当前一维数组的后面, 所以将length2++;
     * @param target
     * @param array
     * @return
     */
    public static boolean Find(int target, int [][] array) {
        if(array == null || array.length == 0 || array[0] == null){
            return false;
        }
        int length = array.length;
        int length2 = array[0].length;
        int index = length - 1, index2 = 0;
        while (index >= 0 && index2 < length2){
            if(array[index][index2] > target){
                index--;
            } else if(array[index][index2] < target){
                index2++;
            } else
                return true;
        }
        return false;
    }
}
