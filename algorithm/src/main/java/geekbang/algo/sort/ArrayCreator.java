package geekbang.algo.sort;

import java.util.Random;

public class ArrayCreator {

    public static int[] createArray(int n, int bound){
        int arr[] = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(bound);
        }
        return arr;
    }
}
