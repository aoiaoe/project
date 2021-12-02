package geekbang.algo.sort;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * 三个原地排序的算法性能比较
 *
 * @author jzm
 * @date 2021/12/1 : 2:07 下午
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 4)
@Measurement(iterations = 3, time = 3, batchSize = 3)
@Fork(2)
@Threads(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SortTest {

    int arr[] = null;

    @Setup(Level.Trial)
    public void setUp(){
        arr = ArrayCreator.createArray(10, 150);
    }


    @Benchmark
    public void bubble() {
        _1_BubbleSort.bubbleSort(arr);
    }
}
