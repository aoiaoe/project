package niukewang.binary_tree;

import java.util.Arrays;
import java.util.Stack;

/**
 * 给定一棵树的前序遍历和中序遍历, 重建二叉树
 */
public class _40_ReconstructTree {

    public static void main(String[] args) {
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] vin = {4,7,2,1,5,3,8,6};
        TreeNode root = reConstructBinaryTree(pre, vin);
        System.out.println("1");
    }

    /**
     * 递归
     * @param pre
     * @param vin
     * @return
     */
    public static TreeNode reConstructBinaryTree(int [] pre,int [] vin) {
        if(pre.length == 0 || vin.length == 0){
            return null;
        }
        // 前序遍历的第一个节点就是根节点
        TreeNode root = new TreeNode(pre[0]);
        for (int i = 0; i < vin.length; i++) {
            // 在中序遍历中找到头结点
            // 因为在中序遍历中, 头结点左边就是左子树， 右边是右子树
            // 在前序遍历中, 头结点的下一个到中序遍历的头结点位置就是左子树, 后面就是右子树
            // 例:
            // 前序:[1,2,4,7,3,5,6,8] 中序:[4,7,2,1,5,3,8,6]
            // 最开始根节点为: 1, 前序遍历中索引为0, 中序遍历中索引为3
            // 则根据中序遍历头结点所在索引3,
            // 根节点左子树的中序遍历为索引3的前面部分0-2 -> [4,7,2], 右子树中序遍历索引为3的后面部分 4-5 -> [5,3,8,6]
            // 同样的,根据根节点在中序遍历中的索引3, 并且索引0已经确定为根节点，
            // 确定左子树的前序遍历为前序遍历数组中索引 1到3 -> [2,4,7], 右子树前序比那里索引为4-7 -> [3,5,6,8]
            if(vin[i] == pre[0]){
                // 左子节点，递归从左边的前序遍历和中序遍历中找
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i+1), Arrays.copyOfRange(vin, 0, i));
                // 右子节点同理
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i+1, pre.length), Arrays.copyOfRange(vin, i+1, vin.length));
                break;
            }
        }
        return root;
    }

    /**
     * 复杂，较难理解，还没自己深入了解
     * @param pre
     * @param vin
     * @return
     */
    public static TreeNode reConstructBinaryTreeByStack(int [] pre,int [] vin) {
        int n = pre.length;
        int m = vin.length;
        //每个遍历都不能为0
        if(n == 0 || m == 0)
            return null;
        Stack<TreeNode> s = new Stack<TreeNode>();
        //首先建立前序第一个即根节点
        TreeNode root = new TreeNode(pre[0]);
        TreeNode cur = root;
        for(int i = 1, j = 0; i < n; i++){
            //要么旁边这个是它的左节点
            if(cur.val != vin[j]){
                cur.left = new TreeNode(pre[i]);
                s.push(cur);
                //要么旁边这个是它的右节点，或者祖先的右节点
                cur = cur.left;
            }else{
                j++;
                //弹出到符合的祖先
                while(!s.isEmpty() && s.peek().val == vin[j]){
                    cur = s.pop();
                    j++;
                }
                //添加右节点
                cur.right = new TreeNode(pre[i]);
                cur = cur.right;
            }
        }
        return root;
    }
}
