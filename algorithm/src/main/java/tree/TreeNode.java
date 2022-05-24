package tree;

/**
 * @author jzm
 * @date 2022/5/17 : 10:14
 */
class TreeNode {

    private int val;

    private TreeNode left;

    private TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this(val);
        this.left = left;
        this.right = right;
    }

    public int getVal() {
        return val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }
}
