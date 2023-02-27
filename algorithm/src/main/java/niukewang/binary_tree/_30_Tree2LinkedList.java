package niukewang.binary_tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 将树转换为一个双向链表
 * 左指针指向前继  右指针指向后继
 */
public class _30_Tree2LinkedList {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode root = new TreeNode(5);
        root.left = node4;
        node4.left = node3;
        node3.left = node2;
        node2.left = node1;
        TreeNode head = Convert(root);
        System.out.println("");
    }
    public static TreeNode Convert(TreeNode pRootOfTree){
        if(pRootOfTree == null){
            return pRootOfTree;
        }
        if(pRootOfTree.left == null && pRootOfTree.right == null){
            return pRootOfTree;
        }
        TreeNode head = null;
        TreeNode cur = null;
        TreeNode sentinel = null;
        Queue<TreeNode> queue = new LinkedList<>();
        Convert2Stack(pRootOfTree, queue);
        if(!queue.isEmpty()){
            head = queue.remove();
            head.left = null;
            sentinel = head;
            while (!queue.isEmpty()){
                cur = queue.remove();
                cur.left = null;
                cur.right = null;
                sentinel.right = cur;
                cur.left = sentinel;
                sentinel = sentinel.right;
            }
        }
        return head;
    }

    public static void Convert2Stack(TreeNode root, Queue<TreeNode> queue){
        if(root == null){
            return;
        }
        Convert2Stack(root.left, queue);
        queue.add(root);
        Convert2Stack(root.right, queue);
    }
}
