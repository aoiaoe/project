package niukewang.search_sort;

public class _22_CompareVersion {

    public static void main(String[] args) {
        System.out.println(compare("134.105.202.15.33.83.60.151.38.150.82.113.141.168.7.24.78.1.80.1","134.105.202.15.33.83.60.151.38.150.82.113.141.168.7.94.26.39.167.186.105.132"));
    }
    public static int compare (String version1, String version2) {
        // write code here
        String[] arr = version1.replaceAll("\\.0+", ".").split("\\.");
        String[] arr1 = version2.replaceAll("\\.0+", ".").split("\\.");
        int end = arr.length < arr1.length ? arr.length: arr1.length;
        int res;
        for (int i = 0; i < end; i++) {
            if(arr[i].length() != arr1[i].length()){
              return arr[i].length() > arr1[i].length() ? 1 : -1;
            } if((res = arr[i].compareTo(arr1[i])) != 0){
                return res > 0 ? 1 : -1;
            }
        }
        return arr.length == arr1.length ? 0 : arr.length > arr1.length ? 1 : -1;
    }

}
