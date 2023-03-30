package geekbang.algo.tree;

public class Tree {
    int val = 0;
    Tree left = null;
    Tree right = null;

    public Tree(int val) {
        this.val = val;
    }

    public static Tree create(int[] ints) {
        Tree[] list = new Tree[ints.length];
        for (int i = 0; i < ints.length; i++) {
            if(ints[i] != -1){
                Tree node = new Tree(ints[i]);
                list[i] = node;
            } else {
                list[i] = null;
            }
        }
        int index = -1;
        for (int i = 0; i < ints.length; i++) {
            Tree node = list[i];
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

    @Override
    public String toString() {
        return "Tree{" +
                "val=" + val +
                '}';
    }
}