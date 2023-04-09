package geekbang.algo.recur;

/**
 * 回溯算法求解背包问题
 */
public class Bag {

    public static void main(String[] args) {
        int[] item = {18,23,54,21,65,25,12,28,26,14};
        final Bag bag = new Bag();
        bag.bag(0, 0, item, 10, 100);
        System.out.println(bag.maxBagWeight);
    }
    public int maxBagWeight = Integer.MIN_VALUE;
    /**
     * 回溯算法求解背包问题
     * @param bagCount 现在背包中物品个数
     * @param weight     背包中当前总重量
     * @param items     物品重量数组
     * @param itemCount 物品个数
     * @param weightLimit 背包重量限制
     */
    public void bag(int bagCount, int weight, int[] items, int itemCount, int weightLimit){
        // 如果物品都装完了
        // 或者重量达到极限了
        if(bagCount == itemCount || weight == weightLimit ){
            if(weight > maxBagWeight){
                maxBagWeight = weight;
            }
            return;
        }
        // 探测下一个物品是否能装进去
        bag(bagCount + 1, weight, items, itemCount, weightLimit);
        // 已经超过可以背包承受的重量的时候，就不要再装了
        // 剪枝技巧, 如果不满足则不执行后续分支
        // 先递归到最后一个数组
        // 然后递归回溯, 依次从倒数的索引重量一直加到最后一个索引
        if(weight + items[bagCount] <= weightLimit){
            // 能装进去，再weight + items[bagCount], 真正装物品
            bag(bagCount+1, weight + items[bagCount], items, itemCount, weightLimit);
        }
    }
}
