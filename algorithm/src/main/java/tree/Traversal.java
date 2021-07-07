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



class TreeNode{

    private int val;

    private TreeNode left;

    private TreeNode right;

    public TreeNode(){}

    public TreeNode(int val){this.val = val;}

    public TreeNode(int val, TreeNode left, TreeNode right){this(val); this.left = left; this.right = right;}

    public int getVal(){
        return val;
    }
    public TreeNode getLeft(){
        return left;
    }

    public TreeNode getRight(){
        return right;
    }
}
