package niukewang.binary_tree;

import java.util.Stack;

/**
 *              1
 *          2       3
 *       4     5         6
 *         res  9
 *       stack  3
 *    substack  1 2
 *         sum  1+2
 * 树中是否存在一个和为n的路径, 路径最后一个节点必须为叶子节点
 */
public class _29_HasPathNum {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
//        node2.left = node4;
//        node2.right = node5;
//        node3.left = node6;
//        node3.right = node7;
        root.left = node2;
//        root.right = node3;
        System.out.println(hasPathSum(root, 1));

    }

    /**
     * 递归
     *      将问题划分为小问题
     *
     * 既然是检查从根到叶子有没有一条等于目标值的路径，那肯定需要从根节点遍历到叶子，
     * 我们可以在根节点每次往下一层的时候，将sum减去节点值，最后检查是否完整等于0.
     * 而遍历的方法我们可以选取二叉树常用的递归前序遍历，因为每次进入一个子节点，
     * 更新sum值以后，相当于对子树查找有没有等于新目标值的路径，
     * 因此这就是子问题，递归的三段式为：
     *
     * 终止条件： 每当遇到节点为空，意味着过了叶子节点，返回。每当检查到某个节点没有子节点，它就是叶子节点，此时sum减去叶子节点值刚好为0，说明找到了路径。
     * 返回值： 将子问题中是否有符合新目标值的路径层层往上返回。
     * 本级任务： 每一级需要检查是否到了叶子节点，如果没有则递归地进入子节点，同时更新sum值减掉本层的节点值。
     * @param root
     * @param sum
     * @return
     */
    public static boolean hasPathSumByReversal(TreeNode root, int sum) {
        // write code here
        if(root == null){
            return false;
        } else if(root.val == sum && root.left == null && root.right == null){
            // 如果和就为当前节点, 并且为叶子节点
            return true;
        }
        // 如果走到这里，说明还未找到. 则递归查找当前叶子节点的子节点，并且划分为子问题
        // 也即是子树的和 = 当前和 - 当前节点的值
        return hasPathSumByReversal(root.left, sum - root.val) | hasPathSumByReversal(root.right, sum - root.val);
    }


    public static boolean hasPathSum (TreeNode root, int sum) {
        // write code here
        if(root == null){
            return false;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> sumStack = new Stack<>();
        stack.push(root);
        sumStack.push(root.val);
        int curVal;
        TreeNode curNode;
        while (!stack.empty()){
            curNode = stack.pop();
            curVal = sumStack.pop();
            if(curNode.left == null && curNode.right == null && curVal == sum){
                return true;
            }
            if(curNode.left != null){
                stack.push(curNode.left);
                sumStack.push(curVal + curNode.left.val);
            }
            if(curNode.right != null){
                stack.push(curNode.right);
                sumStack.push(curVal + curNode.right.val);
            }

        }
        return false;
    }
}
