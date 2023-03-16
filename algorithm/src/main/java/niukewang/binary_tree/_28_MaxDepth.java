package niukewang.binary_tree;

import java.util.ArrayList;

/**
 * 树最大深度
 */
public class _28_MaxDepth {

    public int maxDepth (TreeNode root) {
        // write code here
        if(root == null){
            return 0;
        }
        int depth = 0;
        // 分别使用两个列表填充每一层的节点
        ArrayList<TreeNode> list1 = new ArrayList<>();
        ArrayList<TreeNode> list2 = new ArrayList<>();
        // 将根节点加入第一个链表
        list1.add(root);
        TreeNode pop = null;
        // 始终只判断第一个列表是否为空
        while (!list1.isEmpty()){
            // 从列表头开始获取节点
            pop = list1.remove(0);
            if(pop == null){
                continue;
            }
            // 将左右节点加入到第二个列表
            if(pop.left != null) {
                list2.add(pop.left);
            }
            if(pop.right != null) {
                list2.add(pop.right);
            }
            // 交换, 谁空谁就当列表2.代表当前层已经遍历完
            if(list1.isEmpty()){
                list1 = list2;
                list2 = new ArrayList<>();
                depth++;
            }
        }
        return depth;
    }
}
