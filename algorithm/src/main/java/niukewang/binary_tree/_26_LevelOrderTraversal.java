package niukewang.binary_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 层序遍历
 */
public class _26_LevelOrderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode left1 = new TreeNode(4);
        TreeNode right2 = new TreeNode(5);
        left.left = left1;
        right.right = right2;
        root.left = left;
        root.right = right;
        System.out.println(levelOrderByQueue(root));

    }

    /**
     * 使用队列进行程序遍历
     * @param root
     * @return
     */
    public static ArrayList<ArrayList<Integer>> levelOrderByQueue(TreeNode root) {
        // write code here
        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            //记录二叉树的某一行
            ArrayList<Integer> row = new ArrayList();
            int n = queue.size();
            //因先进入的是根节点，故每层节点多少，队列大小就是多少
            for(int i = 0; i < n; i++){
                TreeNode cur = queue.poll();
                row.add(cur.val);
                //若是左右孩子存在，则存入左右孩子作为下一个层次
                if(cur.left != null)
                    queue.add(cur.left);
                if(cur.right != null)
                    queue.add(cur.right);
            }
            //每一层加入输出
            res.add(row);
        }
        return res;
    }

    /**
     * 双链表交替存储每层节点遍历
     * 两个列表分别为当前层链表,下一层链表（谁空谁切换为下一层, 取自jvm幸存者区思想）
     * @param root
     * @return
     */
    public static ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        // write code here
        if(root == null){
            return new ArrayList<>();
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // 分别使用两个列表填充每一层的节点
        ArrayList<TreeNode> list1 = new ArrayList<>();
        ArrayList<TreeNode> list2 = new ArrayList<>();
        // 将根节点加入第一个链表
        list1.add(root);
        ArrayList<Integer> list = new ArrayList<>();
        TreeNode pop = null;
        // 始终只判断第一个列表是否为空
        while (!list1.isEmpty()){
            // 从列表头开始获取节点
            pop = list1.remove(0);
            if(pop == null){
                continue;
            }
            // 将值加入结果子列表
            list.add(pop.val);
            // 将左右节点加入到第二个列表
            if(pop.left != null) {
                list2.add(pop.left);
            }
            if(pop.right != null) {
                list2.add(pop.right);
            }
            // 交换, 谁空谁就当列表2.代表当前层已经遍历完
            if(list1.isEmpty()){
                list1 = list2;
                list2 = new ArrayList<>();
                res.add(list);
                list = new ArrayList<>();
            }
        }
        return res;
    }
}
