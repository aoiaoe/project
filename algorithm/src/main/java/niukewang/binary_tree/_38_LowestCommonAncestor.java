package niukewang.binary_tree;

import java.util.*;

/**
 * 二叉树的最近公共父节点
 * 给定一棵二叉树(保证非空)以及这棵树上的两个节点对应的val值 o1 和 o2，请找到 o1 和 o2 的最近公共祖先节点。
 * 注：本题保证二叉树中每个节点的val值均不相同。
 */
public class _38_LowestCommonAncestor {

    public static void main(String[] args) {
        TreeNode root = TreeNode.create(new int[]{9,-1,4,-1,1,3,8,-1,7,-1,-1,0,6,10,2,-1,-1,11,-1,-1,-1,5,-1,12});
        int res = lowestCommonAncestor(root, 5, 9);
        System.out.println(res);
    }
    /**
     * 遍历 + 将子节点到父节点的路径记录到hashMap
     * 再反向查找最近公共父节点
     * @param root
     * @param o1
     * @param o2
     * @return
     */
    public static int lowestCommonAncestor (TreeNode root, int o1, int o2) {
        // write code here
        if(root == null){
            return -1;
        }
        if(o1 == o2){
            return o1;
        }
        // 记录子节点的值 -> 父节点的映射
        // 因为题目是每个节点值不会冲否
        // 否则应该是 子节点 -> 父节点的映射
        HashMap<Integer, TreeNode> map =  new HashMap<>();
        // 因为根节点没有父节点,所以值为null
        map.put(root.val, null);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode temp;
        // 记录需要查找的值的节点
        TreeNode node1 = null;
        TreeNode node2 = null;
        while (!queue.isEmpty()){
            temp = queue.poll();
            if(temp.val == o1){
                node1 = temp;
            } else if(temp.val == o2){
                node2 = temp;
            }
            // 如果找到了两个值的节点,退出循环
            if(node1 != null && node2 != null){
                break;
            }
            // 左边不为空,记录左边子节点值 -> 当前节点的映射
            // 然后再入队
            if(temp.left != null){
                map.put(temp.left.val, temp);
                queue.add(temp.left);
            }
            // 右边相同
            if(temp.right != null){
                map.put(temp.right.val, temp);
                queue.add(temp.right);
            }
        }
        HashSet<Integer> set = new HashSet<>();
        set.add(node1.val);
        temp = node1;
        while ((temp = map.get(temp.val)) != null){
            set.add(temp.val);
        }
        temp = node2;
        while ((temp = map.get(temp.val)) != null){
            if(set.contains(temp.val)){
                return temp.val;
            }
        }

        return -1;
    }

    public static int lowestCommonAncestorByDFS(TreeNode root, int o1, int o2) {
        ArrayList<Integer> path1 = new ArrayList<Integer>();
        ArrayList<Integer> path2 = new ArrayList<Integer>();
        //求根节点到两个节点的路径
        dfs(root, path1, o1);
        //重置flag，查找下一个
        flag = false;
        dfs(root, path2, o2);
        int res = 0;
        //比较两个路径，找到第一个不同的点
        for(int i = 0; i < path1.size() && i < path2.size(); i++){
            int x = path1.get(i);
            int y = path2.get(i);
            if((res = x) == y)
                //最后一个相同的节点就是最近公共祖先
                continue;
            else
                break;
        }
        return res;
    }

    //记录是否找到到o的路径
    public static boolean flag = false;
    // 深度优先搜索(前序遍历)求得根节点到目标节点的路径
    public static void dfs(TreeNode root, ArrayList<Integer> path, int o){
        if(flag || root == null)
            return;
        path.add(root.val);
        //节点值都不同，可以直接用值比较
        if(root.val == o){
            flag = true;
            return;
        }
        //dfs遍历查找
        dfs(root.left, path, o);
        dfs(root.right, path, o);
        //找到
        if(flag)
            return;
        //回溯
        path.remove(path.size() - 1);
    }
}
