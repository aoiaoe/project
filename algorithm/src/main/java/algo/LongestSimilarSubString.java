package algo;

/**
 * 最长相似子串
 */
public class LongestSimilarSubString {

    public static void main(String[] args) {
        String sharedStr = getSharedString("今天的天气有一点好", "天气有一点好");
        System.out.println(sharedStr);
    }

    public static String getSharedString(String mainStr, String subStr) {
        int mainLength = mainStr.length();
        int subLength = subStr.length();
        int[][] array = new int[mainLength][subLength];
        int maxLength = 0;
        int maxEnd = 0;
 
        //对两个字符串进行遍历
        for (int i = 0; i < mainLength; i++) {
            for (int j = 0; j < subLength; j++) {
                if (mainStr.charAt(i) == subStr.charAt(j)) {
                    if (i == 0 || j == 0) {
                        array[i][j] = 1;
                    } else {
                        array[i][j] = array[i - 1][j - 1] + 1;
                    }
                }
 
                //在遍历的过程中，把array数组里最大的元素及该元素所在的行数记录下来
                if (array[i][j] > maxEnd) {
                    maxLength = array[i][j];
                    maxEnd = i;
                }
            }
        }
        return mainStr.substring(maxEnd - maxLength + 1, maxEnd + 1);
    }
 
 

}