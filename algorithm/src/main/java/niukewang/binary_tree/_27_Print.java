package niukewang.binary_tree;

import java.util.*;

/**
 * 按之字形打印树
 *       1
 *    2     3
 *          4  5
 *    打印成  [[1], [3,2, [4,5]]
 */
public class _27_Print {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        root.left = node2;
        root.right = node3;
        System.out.println(Print(root));
    }

    /**
     * 双栈
     * 用标志位控制层数，控制是左节点先入栈还是右节点
     * 循环完毕判断栈1是否为空, 如果为空则与栈2交换, 参考jvm幸存者区
     * @param pRoot
     * @return
     */
    public static ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        // write code here
        if(pRoot == null){
            return new ArrayList<>();
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // 分别使用两个栈填充每一层的节点
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        // 将根节点加入第一个栈
        stack1.add(pRoot);
        ArrayList<Integer> list = new ArrayList<>();
        TreeNode pop = null;
        boolean flag = true;
        // 始终只判断第一个栈是否为空
        while (!stack1.isEmpty()){
            // 弹栈
            pop = stack1.pop();
            if(pop == null){
                continue;
            }
            // 将值加入结果子列表
            list.add(pop.val);
            // 用flag区分层数
            if(flag) {
                // 偶数层,则先加左节点
                if (pop.left != null) {
                    stack2.push(pop.left);
                }
                if (pop.right != null) {
                    stack2.push(pop.right);
                }
            } else {
                // 奇数层，则先加右节点
                if (pop.right != null) {
                    stack2.push(pop.right);
                }
                if (pop.left != null) {
                    stack2.push(pop.left);
                }
            }
            // 交换, 谁空谁就当栈2.代表当前层已经遍历完
            if(stack1.isEmpty()){
                flag = !flag;
                stack1 = stack2;
                stack2 = new Stack<>();
                res.add(list);
                list = new ArrayList<>();
            }
        }
        return res;
    }

    public ArrayList<ArrayList<Integer> > PrintByQueue(TreeNode pRoot) {
        TreeNode head = pRoot;
        ArrayList<ArrayList<Integer> > res = new ArrayList<ArrayList<Integer>>();
        if(head == null)
            //如果是空，则直接返回空list
            return res;
        //队列存储，进行层次遍历
        Queue<TreeNode> temp = new LinkedList<TreeNode>();
        temp.offer(head);
        TreeNode p;
        boolean flag = true;
        while(!temp.isEmpty()){
            //记录二叉树的某一行
            ArrayList<Integer> row = new ArrayList<Integer>();
            int n = temp.size();
            //奇数行反转，偶数行不反转
            flag = !flag;
            //因先进入的是根节点，故每层节点多少，队列大小就是多少
            for(int i = 0; i < n; i++){
                p = temp.poll();
                row.add(p.val);
                //若是左右孩子存在，则存入左右孩子作为下一个层次
                if(p.left != null)
                    temp.offer(p.left);
                if(p.right != null)
                    temp.offer(p.right);
            }
            //奇数行反转，偶数行不反转
            if(flag)
                Collections.reverse(row);
            res.add(row);
        }
        return res;
    }
}
