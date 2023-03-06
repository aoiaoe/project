package niukewang.binary_tree;

import java.util.*;

/**
 * 一个二叉树的右视图: 每一层的最右边的节点
 */
public class _41_TreeRightView {

    public static void main(String[] args) {
        int[] pre = {1,2,4,5,6,3};
        int[] vin = {5,4,6,2,1,3};
        int[] res = solve(pre, vin);
        System.out.println(Arrays.toString(res));
    }
    public static int[] solve (int[] xianxu, int[] zhongxu) {
        // write code here
        TreeNode root = _40_ReconstructTree.reConstructBinaryTree(xianxu, zhongxu);
        if(root == null)
            return null;
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int size;
        TreeNode tmp = null;
        while (!q.isEmpty()){
            size = q.size();
            for (int i = 0; i < size; i++) {
                tmp = q.poll();
                if((i+1) == size){
                    list.add(tmp.val);
                }
                if(tmp.left != null){
                    q.add(tmp.left);
                }
                if(tmp.right != null){
                    q.add(tmp.right);
                }
            }
        }
        if(list.isEmpty()){
            return null;
        }
        int[] rightView = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rightView[i] = list.get(i);
        }
        return rightView;
    }
}
