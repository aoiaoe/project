package niukewang.binary_tree;

import java.util.LinkedList;
import java.util.Queue;

public class _35_IsCompleteTree {

    public static void main(String[] args) {
        TreeNode root = TreeNode.create(new int[]{1, 2,-1});
        System.out.println(isCompleteTreeByLevelOrder(root));
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

    /**
     * 层序遍历, 遇到空节点则记录标志位,
     * 如果后面节点不全为空,则一定不是完全二叉树
     * @param root
     * @return
     */
    public static boolean isCompleteTreeByLevelOrder(TreeNode root) {
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
