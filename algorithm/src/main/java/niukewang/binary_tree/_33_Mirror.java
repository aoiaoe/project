package niukewang.binary_tree;

import java.util.Stack;

/**
 * 镜像二叉树
 */
public class _33_Mirror {

    public static void main(String[] args) {
        TreeNode root = TreeNode.create(new int[]{8,6,10,5,7,9,11});
        root = Mirror(root);
        System.out.println("1");
    }

    public static TreeNode MirrorByStack(TreeNode pRoot) {
        // write code here
        if(pRoot == null){
            return null;
        }
        Stack<TreeNode> s = new Stack<>();
        // 根节点入栈
        s.push(pRoot);
        TreeNode node = null;
        TreeNode temp = null;
        while (!s.empty()){
            node = s.pop();
            if(node == null){
                continue;
            }
            // 交换当前节点的左右节点
            temp = node.left;
            node.left = node.right;
            node.right = temp;
            // 左右节点在入栈
            s.push(node.left);
            s.push(node.right);
        }
        return pRoot;
    }

    public static TreeNode Mirror(TreeNode pRoot) {
        // write code here
        if(pRoot == null){
            return null;
        }
        TreeNode temp = pRoot.left;
        pRoot.left = pRoot.right;
        pRoot.right = temp;
        Mirror(pRoot.left);
        Mirror(pRoot.right);
        return pRoot;
    }

}
