package geekbang.algo.recur;

/**
 * 回溯算法求解背包问题
 */
public class Bag {

    public static void main(String[] args) {
        int[] item = {0,1,2,3,4,5,6,7,8,9};
        final Bag bag = new Bag();
        bag.bag(0, 0, item, 10, 10);
        System.out.println(bag.maxBagWeight);
    }
    public int maxBagWeight = Integer.MIN_VALUE;

    /**
     * 回溯算法求解背包问题
     * 递归暴力求解，不过可以利用剪枝技巧，减少程序执行次数
     * 假设3个物品，1：代表放进背包， 0：代表不放进背包
     * 执行逻辑如下：
     * 0 0 0 更新重量
     * 0 0 1 更新重量
     * 0 1 0 更新重量
     * 0 1 1 更新重量
     * 1 0 0 更新重量
     * 1 0 1 更新重量
     * 1 1 0 更新重量
     * 1 1 1 更新重量
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
        // [1] 探测下一个物品是否能装进去
        bag(bagCount + 1, weight, items, itemCount, weightLimit);
        // 已经超过可以背包承受的重量的时候，就不要再装了
        // 剪枝技巧, 如果不满足则不执行后续分支
        // 先递归到最后一个数组
        // 然后递归回溯, 依次从倒数的索引重量一直加到最后一个索引
        if(weight + items[bagCount] <= weightLimit){
            // [2] 能装进去，再weight + items[bagCount], 真正装物品
            bag(bagCount+1, weight + items[bagCount], items, itemCount, weightLimit);
        }
    }
}
