package niukewang.binary_tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }

    public static TreeNode create(int[] ints) {
        TreeNode[] list = new TreeNode[ints.length];
        for (int i = 0; i < ints.length; i++) {
            if(ints[i] != -1){
                TreeNode node = new TreeNode(ints[i]);
                list[i] = node;
            } else {
                list[i] = null;
            }
        }
        int index = -1;
        for (int i = 0; i < ints.length; i++) {
            TreeNode node = list[i];
            if(node == null){
                continue;
            }
            index = (i + 1) * 2 - 1;
            node.left = index < ints.length ? list[index] : null;
            index = (i + 1) * 2;
            node.right = index < ints.length ? list[index] : null;
        }
        return list[0];
    }
}