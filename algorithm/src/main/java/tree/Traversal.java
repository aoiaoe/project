package tree;


public class Traversal {

    public static void main(String[] args) {

    }

    /**
     * 前序遍历
     */
    public static void preorderTraversal(TreeNode root){
        if(root == null){
            return;
        }
        System.out.println(root.getVal());
        preorderTraversal(root.getLeft());
        preorderTraversal(root.getRight());
    }
}



