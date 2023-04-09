package niukewang.binary_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * 二叉树前序遍历
 * 思路:
 *  1、递归遍历
 *  2、栈
 */
public class _23_PreorderTraversal {

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 20; i++) {

            System.out.println(r.nextInt(100));
        }
    }
    public static int[] preorderTraversal (TreeNode root) {
        // write code here
        List<Integer> res = new ArrayList<>();
        preorderTraversal(root, res);
        return res.stream().mapToInt(e -> e.intValue()).toArray();
    }

    public static void preorderTraversal(TreeNode root, List<Integer> res){
        if(root == null){
            return;
        }
        res.add(root.val);
        preorderTraversal(root.left, res);
        preorderTraversal(root.right, res);
    }

    /**
     * 利用栈进行遍历
     * @param root
     * @return
     */
    public int[] preorderTraversalByStack(TreeNode root) {
        //添加遍历结果的数组
        List<Integer> list = new ArrayList();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        //空树返回空数组
        if(root == null)
            return new int[0];
        //根节点先进栈
        stack.push(root);
        while(!stack.isEmpty()){
            //每次栈顶就是访问的元素
            TreeNode node = stack.pop();
            list.add(node.val);
            //如果右边还有右子节点进栈
            if(node.right != null)
                stack.push(node.right);
            //如果左边还有左子节点进栈
            if(node.left != null)
                stack.push(node.left);
        }
        //返回的结果
        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++)
            res[i] = list.get(i);
        return res;
    }
}

