package niukewang.binary_tree;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 判断是否为二叉搜索树
 */
public class _34_IsValidBST {
    int pre = Integer.MIN_VALUE;

    //中序遍历
    public boolean isValidBST (TreeNode root) {
        if (root == null)
            return true;
        //先进入左子树
        if(!isValidBST(root.left))
            return false;
        if(root.val < pre)
            return false;
        //更新最值
        pre = root.val;
        //再进入右子树
        return isValidBST(root.right);
    }

    /**
     * 利用栈进行中序遍历，将值保存进列表
     * 最后遍历列表,如果存在降序，则不是BST
     * @param root
     * @return
     */
    public static boolean isValidBSTByStack(TreeNode root){
        //设置栈用于遍历
        Stack<TreeNode> s = new Stack<TreeNode>();
        TreeNode head = root;
        //记录中序遍历的数组
        ArrayList<Integer> sort = new ArrayList<Integer>();
        while(head != null || !s.isEmpty()){
            //直到没有左节点
            while(head != null){
                s.push(head);
                head = head.left;
            }
            head = s.pop();
            //访问节点
            sort.add(head.val);
            //进入右边
            head = head.right;
        }
        //遍历中序结果
        for(int i = 1; i < sort.size(); i++){
            //一旦有降序，则不是搜索树
            if(sort.get(i - 1) > sort.get(i))
                return false;
        }
        return true;
    }
}
