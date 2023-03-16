package niukewang.binary_tree;

import java.util.*;

/**
 * 判断一棵树是否对称, 节点同时为空, 或者不为空，且数字相同
 */
public class _31_IsSymmetrical {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node22 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node33 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node44 = new TreeNode(4);
        root.left = node2;
        root.right = node22;
        node2.left = node3;
        node2.right = node4;
        node22.left = node44;
        System.out.println(isSymmetricalByReversal(root));
    }

    /**
     * 顺中序遍历左子树, 入队列,
     * 逆中序遍历右子树，并弹栈+出队比较
     * @param pRoot
     * @return
     */
    public static boolean isSymmetrical(TreeNode pRoot) {
        if(pRoot == null){
            return true;
        }
        if(pRoot.left != null && pRoot.right == null){
            return false;
        } else if(pRoot.left == null && pRoot.right != null){
            return false;
        }
        // 顺中序遍历[中左右]左子树, 将所有节点入队
        Stack<TreeNode> stack = new Stack<>();
        Queue<TreeNode> list = new LinkedList<>();
        stack.push(pRoot.left);
        TreeNode temp = null;
        while (!stack.empty()){
            temp = stack.pop();
            list.add(temp);
            if(temp != null){
                if(temp.left != null || temp.right != null){
                    stack.push(temp.right);
                    stack.push(temp.left);
                }
            }
        }
        stack.push(pRoot.right);
        TreeNode temp2 = null;
        // 在逆中序遍历[中右左]右子树, 并以此和队列中的值比较
        while (!stack.empty()){
            temp = stack.pop();
            temp2 = list.remove();
            if(!equals(temp, temp2)){
                return false;
            }
            if(temp != null){
                if(temp.left != null || temp.right != null){
                    stack.push(temp.left);
                    stack.push(temp.right);
                }
            }
        }

        return true;
    }

    public static boolean equals(TreeNode node, TreeNode node2){
        if(node != null && node2 != null && node.val == node2.val){
            return true;
        } else if(node == null && node2 == null){
            return true;
        }
        return false;
    }

    /**
     * 双栈比较
     * @param pRoot
     * @return
     */
    public static boolean isSymmetricalByDoubleStack(TreeNode pRoot) {
        //空树为对称的
        if(pRoot == null)
            return true;
        Stack<TreeNode> left = new Stack<>();
        Stack<TreeNode> right = new Stack<>();
        left.push(pRoot.left);
        right.push(pRoot.right);
        TreeNode leftNode = null;
        TreeNode rightNode = null;
        while (!left.empty() && !right.empty()){
            leftNode = left.pop();
            rightNode = right.pop();
            //都为空暂时对称
            if(leftNode == null && rightNode == null)
                continue;
            if(!equals(leftNode, rightNode)){
                return false;
            }
            left.push(leftNode.right);
            left.push(leftNode.left);

            right.push(rightNode.left);
            right.push(rightNode.right);
        }

        return true;
    }

    /**
     * 双队列比较
     * @param pRoot
     * @return
     */
    public static boolean isSymmetricalByDoubleQueue(TreeNode pRoot) {
        //空树为对称的
        if(pRoot == null)
            return true;
        //辅助队列用于从两边层次遍历
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q2 = new LinkedList<TreeNode>();
        q1.offer(pRoot.left);
        q2.offer(pRoot.right);
        while(!q1.isEmpty() && !q2.isEmpty()){
            //分别从左边和右边弹出节点
            TreeNode left = q1.poll();
            TreeNode right = q2.poll();
            //都为空暂时对称
            if(left == null && right == null)
                continue;
            //某一个为空或者数字不相等则不对称
            if(left == null || right == null || left.val != right.val)
                return false;
            //从左往右加入队列
            q1.offer(left.left);
            q1.offer(left.right);
            //从右往左加入队列
            q2.offer(right.right);
            q2.offer(right.left);
        }
        //都检验完都是对称的
        return true;
    }

    /**
     * 递归比较
     * @param pRoot
     * @return
     */
    public static boolean isSymmetricalByReversal(TreeNode pRoot){
        if(pRoot == null){
            return true;
        }
        return reversal(pRoot.left, pRoot.right);
    }

    public static boolean reversal(TreeNode node1, TreeNode node2){
        if(node1 == null && node2 == null){
            return true;
        }
        if(!equals(node1, node2)){
            return false;
        }
        return reversal(node1.left, node2.right) && reversal(node1.right, node2.left);
    }
}
