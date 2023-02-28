package niukewang.binary_tree;

import java.util.*;

/**
 * 合并两颗二叉树
 * 已知两颗二叉树，将它们合并成一颗二叉树。合并规则是：都存在的结点，就将结点值加起来，否则空的位置就由另一个树的结点来代替
 */
public class _32_MergeTrees {

    public static void main(String[] args) {
        TreeNode root1 = TreeNode.create(new int[]{1,10,8,-1,1,-1,-1, -1, -1,3});
        TreeNode root2 = TreeNode.create(new int[]{9,4,2,-1,4,9});

        root1 = mergeTrees(root1, root2);
    }

    public static TreeNode mergeTrees (TreeNode t1, TreeNode t2) {
        //若只有一个节点返回另一个，两个都为null自然返回null
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;
        //根左右的方式递归
        TreeNode head = new TreeNode(t1.val + t2.val);
        head.left = mergeTrees(t1.left, t2.left);
        head.right = mergeTrees(t1.right, t2.right);
        return head;
    }

    public static TreeNode mergeTreesByDepthOrder(TreeNode t1, TreeNode t2) {
        //若只有一个节点返回另一个，两个都为null自然返回null
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;
        //合并根节点
        TreeNode head = new TreeNode(t1.val + t2.val);
        //连接后的树的层次遍历节点
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        //分别存两棵树的层次遍历节点
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q2 = new LinkedList<TreeNode>();
        q.offer(head);
        q1.offer(t1);
        q2.offer(t2);
        while (!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode node = q.poll();
            TreeNode node1 = q1.poll();
            TreeNode node2 = q2.poll();
            TreeNode left1 = node1.left;
            TreeNode left2 = node2.left;
            TreeNode right1 = node1.right;
            TreeNode right2 = node2.right;
            if(left1 != null || left2 != null){
                //两个左节点都存在
                if(left1 != null && left2 != null){
                    TreeNode left = new TreeNode(left1.val + left2.val);
                    node.left = left;
                    //新节点入队列
                    q.offer(left);
                    q1.offer(left1);
                    q2.offer(left2);
                    //只连接一个节点
                }else if(left1 != null)
                    node.left = left1;
                else
                    node.left = left2;
            }
            if(right1 != null || right2 != null){
                //两个右节点都存在
                if(right1 != null && right2 != null) {
                    TreeNode right = new TreeNode(right1.val + right2.val);
                    node.right = right;
                    //新节点入队列
                    q.offer(right);
                    q1.offer(right1);
                    q2.offer(right2);
                    //只连接一个节点
                }else if(right1 != null)
                    node.right = right1;
                else
                    node.right = right2;
            }
        }
        return head;
    }
}
