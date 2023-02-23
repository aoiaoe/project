package niukewang.binary_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树中序遍历
 * 思路:
 *  1、递归遍历
 *  2、栈
 */
public class _24_InorderTraversal {

    public static void main(String[] args) {


    }
    public static int[] inorderTraversal (TreeNode root) {
        if(root == null){
            return new int[0];
        }
        // write code here
        List<Integer> res = new ArrayList<>();
        inorderTraversal(root, res);
        int[] resArr = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            resArr[i] = res.get(i);
        }
        return resArr;
//        return res.stream().mapToInt(e -> e.intValue()).toArray();
    }

    public static void inorderTraversal(TreeNode root, List<Integer> res){
        if(root == null){
            return;
        }
        inorderTraversal(root.left, res);
        res.add(root.val);
        inorderTraversal(root.right, res);
    }

    /**
     * 利用栈进行遍历
     * @param root
     * @return
     */
    public int[] inorderTraversalByStack(TreeNode root) {
        //添加遍历结果的数组
        List<Integer> list = new ArrayList();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        //空树返回空数组
        if(root == null)
            return new int[0];
        //当树节点不为空或栈中有节点时
        while(root != null || !stack.isEmpty()){
            //每次找到最左节点
            while(root != null){
                stack.push(root);
                root = root.left;
            }
            //访问该节点
            TreeNode node = stack.pop();
            list.add(node.val);
            //进入右节点
            root = node.right;
        }
        //返回的结果
        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++)
            res[i] = list.get(i);
        return res;
    }
}

