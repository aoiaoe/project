package niukewang.binary_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉搜索树的最近公共祖先
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * 1.对于该题的最近的公共祖先定义:对于有根树T的两个节点p、q，最近公共祖先LCA(T,p,q)表示一个节点x，满足x是p和q的祖先且x的深度尽可能大。在这里，一个节点也可以是它自己的祖先.
 * 2.二叉搜索树是若它的左子树不空，则左子树上所有节点的值均小于它的根节点的值； 若它的右子树不空，则右子树上所有节点的值均大于它的根节点的值
 * 3.所有节点的值都是唯一的。
 * 4.p、q 为不同节点且均存在于给定的二叉搜索树中。
 */
public class _37_LowestCommonAncestor {

    public static void main(String[] args) {
        TreeNode root = TreeNode.create(new int[]{7,1,12,0,4,11,14,-1,-1,3,5});
        int res = lowestCommonAncestorByPath(root, 12, 11);
    }

    /**
     * 递归
     * 思路:
     *      利用二叉搜索树左边子节点 < 根节点 && 右边子节点大于根节点特性
     * @param root
     * @param p
     * @param q
     * @return
     */
    public static int lowestCommonAncestorByReversal(TreeNode root, int p, int q) {
        if(root == null){
            return -1;
        }
        // 如果两个值 都小于根节点, 则去左边子树找
        if(root.val > p && root.val > q ){
            return lowestCommonAncestorByReversal(root.left, p, q);
        } else if(root.val < p && root.val < q){
            // 如果两个值 > 右边根节点, 则去右边子树找
            return lowestCommonAncestorByReversal(root.right, p, q);
        }
        return root.val;
    }


    public static int lowestCommonAncestorByPath(TreeNode root, int p, int q){
        if(root == null){
            return -1;
        }
        List<Integer> res1 = getPath(root, p);
        List<Integer> res2 = getPath(root, q);
        int size = res1.size() > res2.size() ? res2.size() : res1.size();
        int i = 0;
        for ( ; i < size; i++) {
            if(res1.get(i).equals(res2.get(i))){
                continue;
            }
            return res1.get(i - 1);
        }
        return res1.get(i-1);
    }

    public static List<Integer> getPath(TreeNode root, int target){
        List<Integer> res = new ArrayList<>();
        while (root != null){
            res.add(root.val);
            if(root.val == target) {
                break;
            }
            if(root.val > target){
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return res;
    }
}
