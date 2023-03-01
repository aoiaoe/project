package niukewang.binary_tree;

import java.util.LinkedList;
import java.util.Queue;

public class _35_IsCompleteTree {

    public static void main(String[] args) {
        TreeNode root = TreeNode.create(new int[]{1,383,-1,886,-1,777,-1,915,-1,793,-1,335,-1,386,-1,492,-1,649,-1,421,-1,362,-1,27,-1,690,-1,59,-1,763,-1,926,-1,540,-1,426,-1,172,-1,736,-1,211,-1,368,-1,567,-1,429,-1,782,-1,530,-1,862,-1,123,-1,67,-1,135,-1,929,-1,802,-1,22,-1,58,-1,69,-1,167,-1,393,-1,456,-1,11,-1,42,-1,229,-1,373,-1,421,-1,919,-1,784,-1,537,-1,198,-1,324,-1,315,-1,370,-1,413,-1,526,-1,91,-1,980,-1,956,-1,873,-1,862,-1,170,-1,996,-1,281,-1,305,-1,925,-1,84,-1,327,-1,336,-1,505,-1,846,-1,729,-1,313,-1,857,-1,124,-1,895,-1,582,-1,545,-1,814,-1,367,-1,434,-1,364,-1,43,-1,750,-1,87,-1,808,-1,276,-1,178,-1,788,-1,584,-1,403,-1,651,-1,754,-1,399,-1,932,-1,60,-1,676,-1,368,-1,739,-1,12,-1,226,-1,586,-1,94});
        System.out.println(isCompleteTree(root));
    }

    /**
     * 复杂的思路
     * 前置: 完全二叉树(非最底层节点数为满二叉的值 = 2^(m-1)个节点)
     *      1、双队列实现层序遍历,并将当前层加入第三个队列, 用于最后判断是否存在中间的空节点
     *      2、从第一层遍历开始判断,下一层的节点是否是满二叉层数的值,
     *      3、如果不是满二叉的值，则可以认为下一层是最后一层，遍历第三个队列(也就是当前层节点)
     *      4、再次遍历当前层(第三队列), 如果存在中间空隙叶子节点,则认为非完全二叉树
     *      5、需要处理特殊节点      1
     *                          2   null
     *                        3 null  null null
     * @param root
     * @return
     */
    public static boolean isCompleteTree (TreeNode root) {
        // write code here
        Queue<TreeNode> q1 = new LinkedList<>(); // 当前层
        Queue<TreeNode> q2 = new LinkedList<>(); // 下一层
        Queue<TreeNode> q3 = new LinkedList<>(); // 再次记录当前层

        q1.add(root);
        TreeNode node = null;
        int size = 1;
        while (!q1.isEmpty()){
            // 当前层出队
            node = q1.poll();
            // 再次记录当前层
            q3.add(node);
            // 下一层入队
            if(node.left != null){
                q2.add(node.left);
            }
            if(node.right != null) {
                q2.add(node.right);
            }
            // 如果当前层为空, 则和下一层交换
            if(q1.isEmpty()){
                // 如果下一层为空,则认为是满二叉树,返回true
                if(q2.size() == 0){
                    return true;
                }
                size *= 2;
                // 如果下一层 != 满二叉数当前层数的值
                // 则认为下一层是最后一层, 退出去判断q3当前层的子节点
                if(q2.size() != size){
                    break;
                }
                // 交换遍历下一层
                q1 = q2;
                q2 = new LinkedList<>();
                // 清除q3缓存
                q3.clear();
            }
        }
        // 判断q3的除最后一个节点的所有节点
        for (int i = 0; i < q3.size() - 1; i++) {
            node = q3.poll();
            // 如果有任意一个为空,则非完全二叉
            if(!(node.left != null && node.right != null)){
                return false;
            }
        }
        // 判断最后一个节点
        node = q3.poll();
        if(node.left == null){
            if(node.right != null) {
                // 左空右不空, 非
                return false;
            }
        } else {
            // 处理特殊节点
            if(node.left.left != null || node.left.right != null){
                return false;
            }
        }
        return true;
    }

    public boolean isCompleteTreeSimple(TreeNode root) {
        //空树一定是完全二叉树
        if(root == null)
            return true;
        //辅助队列
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode cur;
        //定义一个首次出现的标记位
        boolean notComplete = false;
        while(!queue.isEmpty()){
            cur = queue.poll();
            //标记第一次遇到空节点
            if(cur == null){
                notComplete = true;
                continue;
            }
            //后续访问已经遇到空节点了，说明经过了叶子
            if(notComplete)
                return false;
            queue.offer(cur.left);
            queue.offer(cur.right);
        }
        return true;
    }
}
