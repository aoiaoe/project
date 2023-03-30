package geekbang.algo.tree;

/**
 * 平衡二叉树
 */
public class AvlTree {

    public static void main(String[] args) {
        Tree tree = Tree.create(new int[]{6,1,7,-1,5,-1,9,-1,-1,3,-1,-1,-1,8,10,-1,-1,-1,-1,2,4});
        AvlTree ins = new AvlTree();
        ins.insert(tree, 11);
        System.out.println("前驱");
        System.out.println(ins.preNode(tree, 4));
        System.out.println(ins.preNode(tree, 2));
        System.out.println(ins.preNode(tree, 6));
        System.out.println(ins.preNode(tree, 8));

        System.out.println("后驱");
        System.out.println(ins.sucNode(tree, 7));
        System.out.println(ins.sucNode(tree, 5));
        System.out.println(ins.sucNode(tree, 2));
        System.out.println(ins.sucNode(tree, 6));

    }

    /**
     * 寻找节点的前驱节点
     * 前驱节点定义: 节点值小于给定节点中的最大的一个节点
     * 方法:
     * 如果该节点
     * 1、有左子树,则前驱节点就是左子树中的最大值节点(因为当前节点值肯定大于其所有左子树节点)
     * 2、没有左子树, 如果它是父节点的
     *      a、右节点, 则父节点为前驱节点
     *      b、左节点, 则需要向上找到其祖先节点P, 祖先节点的父节点Q, 如果P是Q的右节点, 则Q就是其前驱节点
     *          (因为P作为Q的右节点, 则P所有节点值都大于Q节点，并且当前节点作为左节点，
     *          肯定是小于其所有P及其下面祖先节点的)
     * @return
     */
    public Tree preNode(Tree tree, int x) {
        // 先获取节点
        Tree targetNode = findNode(tree, x);
        if (targetNode == null) {
            return null;
        }
        // 判断是否有左子树
        if(targetNode.left != null){
            // 寻找左子树中最大的节点
            return findSupremeNode(targetNode.left, true);
        } else {
            // 寻找节点的父节点
            Tree pNode = findPNode(tree, targetNode);
            // 如果节点是父节点的右子树, 则父节点是前驱
            if(pNode.right == targetNode){
                return pNode;
            } else {
                // 如果节点是父节点左子树， 则向上找到一个祖先节点P作为节点Q的右子树, 前驱节点为Q
                Tree ppNode = pNode;
                while (pNode != null){
                    pNode = ppNode;
                    ppNode = findPNode(tree, ppNode);
                    if(ppNode.right == pNode){
                        return ppNode;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 寻找节点的后驱节点
     * 前驱节点定义: 节点值大于给定节点中的最小的一个节点
     * 方法:
     * 如果该节点
     * 1、有右子树,则前驱节点就是左子树中的最小值节点(因为当前节点值肯定小于其所有右子树节点)
     * 2、没有右子树, 如果它是父节点的
     *      a、左节点, 则父节点为后驱节点
     *      b、右节点, 则需要向上找到其祖先节点P, 祖先节点的父节点Q, 如果P是Q的左节点, 则Q就是其后驱节点
     *          (因为P作为Q的左节点, 则P所有节点值都小于Q节点，并且当前节点作为右节点，
     *          肯定是大于其所有P及其下面祖先节点的)
     * @return
     */
    public Tree sucNode(Tree tree, int x) {
        // 先获取节点
        Tree targetNode = findNode(tree, x);
        if (targetNode == null) {
            return null;
        }
        // 判断是否有右子树
        if(targetNode.right != null){
            // 寻找右子树中最小的节点
            return findSupremeNode(targetNode.right, false);
        } else {
            // 寻找节点的父节点
            Tree pNode = findPNode(tree, targetNode);
            // 如果节点是父节点的左子树, 则父节点是后驱
            if(pNode.left == targetNode){
                return pNode;
            } else {
                // 如果节点是父节点左子树， 则向上找到一个祖先节点P作为节点Q的右子树, 前驱节点为Q
                Tree ppNode = pNode;
                while (pNode != null){
                    pNode = ppNode;
                    ppNode = findPNode(tree, ppNode);
                    if(ppNode.left == pNode){
                        return ppNode;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 寻找指定值的节点
     * @param tree
     * @param x
     * @return
     */
    public Tree findNode(Tree tree, int x) {
        Tree node = tree;
        while (node != null) {
            if (node.val == x) {
                return node;
            } else if (node.val > x) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * 寻找指定值的父节点
     * @param tree
     * @param node
     * @return
     */
    public Tree findPNode(Tree tree, Tree node) {
        Tree tmp = tree;
        // 根节点没有父节点
        if(tree == node){
            return null;
        }
        while (tmp != null) {
            if(tmp.left == node || tmp.right == node){
                return tmp;
            }else if (tmp.val > node.val) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        return null;
    }

    /**
     * 找到极端节点
     * @param tree
     * @return
     */
    public Tree findSupremeNode(Tree tree, boolean left){
        if(left) {
            while (tree != null) {
                if (tree.right == null) {
                    return tree;
                }
                tree = tree.right;
            }
        } else {
            while (tree != null) {
                if (tree.left == null) {
                    return tree;
                }
                tree = tree.left;
            }
        }
        return null;
    }

    /**
     * 添加节点
     * @param root
     * @param x
     * @return
     */
    public Tree insert(Tree root, int x){
        if(root == null){
            return new Tree(x);
        }
        Tree tmp = root;
        while (tmp != null){
            if(x <= tmp.val){
                if(tmp.left == null){
                    tmp.left = new Tree(x);
                    break;
                }
                tmp = tmp.left;
            } else {
                if(tmp.right == null){
                    tmp.right = new Tree(x);
                    break;
                }
                tmp = tmp.right;
            }
        }
        return root;
    }
}
