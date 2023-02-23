package niukewang.binary_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树后序遍历
 * 思路:
 *  1、递归遍历
 *  2、栈
 */
public class _25_PostorderTraversal {

    public static void main(String[] args) {
        

    }
    public static int[] postorderTraversal (TreeNode root) {
        if(root == null){
            return new int[0];
        }
        // write code here
        List<Integer> res = new ArrayList<>();
        postorderTraversal(root, res);
        int[] resArr = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            resArr[i] = res.get(i);
        }
        return resArr;
//        return res.stream().mapToInt(e -> e.intValue()).toArray();
    }

    public static void postorderTraversal(TreeNode root, List<Integer> res){
        if(root == null){
            return;
        }
        postorderTraversal(root.left, res);
        postorderTraversal(root.right, res);
        res.add(root.val);
    }

    /**
     * 利用栈进行遍历
     * @param root
     * @return
     */
    public int[] postorderTraversalByStack(TreeNode root) {
        //添加遍历结果的数组
        List<Integer> list = new ArrayList();
        Stack<TreeNode> s = new Stack<TreeNode>();
        TreeNode pre = null;
        while(root != null || !s.isEmpty()){
            //每次先找到最左边的节点
            while(root != null){
                s.push(root);
                root = root.left;
            }
            //弹出栈顶
            TreeNode node = s.pop();
            //如果该元素的右边没有或是已经访问过
            if(node.right == null || node.right == pre){
                //访问中间的节点
                list.add(node.val);
                //且记录为访问过了
                pre = node;
            }else{
                //该节点入栈
                s.push(node);
                //先访问右边
                root = node.right;
            }
        }
        //返回的结果
        int[] res = new int[list.size()];
        for(int i = 0; i < list.size(); i++)
            res[i] = list.get(i);
        return res;
    }
}

