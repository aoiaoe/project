package niukewang.binary_tree;

import java.util.LinkedList;
import java.util.Queue;

public class _39_Serialization {

    public static void main(String[] args) {
//        TreeNode root = TreeNode.create(new int[]{5,4,-1,3,-1,2});
//        String s = Serialize(root);
//        System.out.println(s);
//        root = Deserialize(s);
//        System.out.println("1");
//        TreeNode root1 = TreeNode.create(new int[]{1,2,3,-1,4-1,5,-1,-1,-1,6,-1,-1,-1,7});
        TreeNode root1 = TreeNode.create(new int[]{1,2,5,3,4,6,7});
        System.out.println(Serialize(root1));
    }

    public static String Serialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q1 = new LinkedList<>();
        Queue<TreeNode> q2 = new LinkedList<>();
        Queue<TreeNode> q3;
        q1.add(root);
        TreeNode tmp = null;
        // 记录q2中是否存在非空节点
        boolean flag = false;
        while (!q1.isEmpty()) {
            tmp = q1.poll();
            // 如果当前节点为空，则其子节点也为空
            if (tmp == null) {
                q2.add(null);
                q2.add(null);
                // 序列化为#,代表当前节点为空
                sb.append("#").append(",");
            } else {
                sb.append(tmp.val).append(",");
                q2.add(tmp.left);
                q2.add(tmp.right);
                // 如果左右子节点有一个不为空，则q2中存在非空节点
                if (tmp.left != null) {
                    flag = true;
                }
                if (tmp.right != null) {
                    flag = true;
                }
            }
            // 如果q1空了,并且q2中还存在非空节点
            if (q1.isEmpty() && flag) {
                q3 = q2;
                q2 = q1;
                q1 = q3;
                // 重置标志位
                flag = false;
            }
        }
        // 去除最后一个分隔符
        return sb.substring(0, sb.length() - 1);
    }

    public static TreeNode Deserialize(String str) {
        if (str == null || str.charAt(0) == '#') {
            return null;
        }
        // 分隔符切割为数组
        String[] strArr = str.split(",");
        int length = strArr.length;
        TreeNode[] arr = new TreeNode[length];
        String ch = "#";
        // 将所有字符反序列化为节点
        for (int i = 0; i < length; i++) {
            if (!(ch = strArr[i]).equals("#")) {
                arr[i] = new TreeNode(Integer.valueOf(ch));
            } else {
                arr[i] = null;
            }
        }
        TreeNode cur = null;
        int index = -1;
        // 从第一个节点开始,连接其子节点
        for (int i = 0; i < length; i++) {
            cur = arr[i];
            if (cur != null) {
                // 左节点索引
                index = (i + 1) * 2 - 1;
                // 大于数组长度，则其是null
                cur.left = index < length ? arr[index] : null;
                // 右节点索引
                index = (i + 1) * 2;
                cur.right = index < length ? arr[index] : null;
            }
        }
        // 返回头结点
        return arr[0];
    }


    //序列的下标
    public int index = 0;
    //处理序列化的功能函数（递归）
    private static void SerializeFunction(TreeNode root, StringBuilder str){
        //如果节点为空，表示左子节点或右子节点为空，用#表示
        if(root == null){
            str.append('#');
            return;
        }
        //根节点
        str.append(root.val).append('!');
        //左子树
        SerializeFunction(root.left, str);
        //右子树
        SerializeFunction(root.right, str);
    }

    public static String SerializeByPreOrder(TreeNode root) {
        //处理空树
        if(root == null)
            return "#";
        StringBuilder res = new StringBuilder();
        SerializeFunction(root, res);
        //把str转换成char
        return res.toString();
    }
    //处理反序列化的功能函数（递归）
    private TreeNode DeserializeFunction(String str){
        //到达叶节点时，构建完毕，返回继续构建父节点
        //空节点
        if(str.charAt(index) == '#'){
            index++;
            return null;
        }
        //数字转换
        int val = 0;
        //遇到分隔符或者结尾
        while(str.charAt(index) != '!' && index != str.length()){
            val = val * 10 + ((str.charAt(index)) - '0');
            index++;
        }
        TreeNode root = new TreeNode(val);
        //序列到底了，构建完成
        if(index == str.length())
            return root;
        else
            index++;
        //反序列化与序列化一致，都是前序
        root.left = DeserializeFunction(str);
        root.right = DeserializeFunction(str);
        return root;
    }

    public TreeNode DeserializeByPreOrder(String str) {
        //空序列对应空树
        if(str == "#")
            return null;
        TreeNode res = DeserializeFunction(str);
        return res;
    }

}
