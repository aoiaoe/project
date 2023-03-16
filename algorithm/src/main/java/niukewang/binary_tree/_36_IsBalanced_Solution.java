package niukewang.binary_tree;

/**
 * 是否是平衡二叉树
 * 平衡二叉树（Balanced Binary Tree），具有以下性质：
 * 它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 */
public class _36_IsBalanced_Solution {

    public static void main(String[] args) {
        TreeNode root = TreeNode.create(new int[]{1, 2, -1, 3, -1, 4, -1, 5});
        boolean res = IsBalanced_SolutionDFS(root);
        System.out.println(res);
    }

    /**
     * 深度优先搜索
     *
     * @param root
     * @return
     */
    public static boolean IsBalanced_SolutionDFS(TreeNode root) {
        if (root == null) return true;
        //判断左子树和右子树是否符合规则，且深度不能超过2
        return IsBalanced_SolutionDFS(root.left)
                && IsBalanced_SolutionDFS(root.right)
                && Math.abs(deepDFS(root.left) - deepDFS(root.right)) < 2;
    }

    //判断二叉树深度
    public static int deepDFS(TreeNode root) {
        if (root == null) return 0;
        return Math.max(deepDFS(root.left), deepDFS(root.right)) + 1;
    }

    public static boolean IsBalanced_Solution(TreeNode root) {
        if (deep(root) == -1)
            return false;
        return true;
    }

    public static int deep(TreeNode node) {
        if (node == null) return 0;
        int left = deep(node.left);
        if (left == -1)
            return -1;
        int right = deep(node.right);
        if (right == -1)
            return -1;

        //两个子节点深度之差大于一
        if (Math.abs(left - right) > 1) {
            return -1;
        }
        //子节点需要向自己的父节点报告自己的深度
        return Math.max(left, right) + 1;
    }

}
