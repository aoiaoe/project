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

    public static TreeNode ConvertByStack(TreeNode pRootOfTree){
        if(pRootOfTree == null){
            return pRootOfTree;
        }
        // 头指针
        TreeNode head = null;
        TreeNode cur = null;
        // 是否第一次到左边, 如果是第一次，则最后一个叶子节点就是头结点
        // 因为是二叉搜索树, 左子节点 < 父节点 < 右节点
        boolean flag = true;
        Stack<TreeNode> stack = new Stack<>();
        while (pRootOfTree != null || !stack.empty()){
            // 这里的根节点, 可以是真的根节点, 也可以是右子树的根节点
            // 从根一直往左边叶子节点遍历,
            // 将根节点及其左子节点入栈
            while (pRootOfTree != null){
                stack.push(pRootOfTree);
                pRootOfTree = pRootOfTree.left;
            }
            pRootOfTree = stack.pop();
            if(flag){
                // 经过上面的循环，栈顶的一定是最小的节点，也是头结点
                head = pRootOfTree;
                cur = head;
                flag = false;
            } else {
                // 弹出栈顶节点，并加入链表
                cur.right = pRootOfTree;
                pRootOfTree.left = cur;
                cur = pRootOfTree;
            }
            // 将根节点赋值为右节点
            // 遍历右子树
            pRootOfTree = pRootOfTree.right;
        }

        return head;
    }
}
