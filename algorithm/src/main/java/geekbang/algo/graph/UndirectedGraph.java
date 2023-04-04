package geekbang.algo.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 无向图
 *  邻接表实现
 *  邻接表存储的是节点的边
 * @author jzm
 * @date 2023/4/4 : 15:05
 */
public class UndirectedGraph {

    // 顶点个数
    private int count;
    // 邻接表，边
    private LinkedList<Integer> adj[];
    // 顶点值
    private Integer[] vals;

    public UndirectedGraph(int count){
        this.count = count;
        this.vals = new Integer[count];
        this.adj = new LinkedList[count];
        for (int i = 0; i < count; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void insertVertex(int index, Integer val){
        if(index < 0 || index > count){
            throw new RuntimeException("index out of range");
        }
        // 如果改索引存在值，则充值该值的邻接表
        if(vals[index] != null){
            adj[index] = new LinkedList<>();
        }
        vals[index] = val;
    }

    /**
     * 无向图，边需要在两个定点的邻接表中存储
     * @param s
     * @param t
     */
    public void insertEdge(int s, int t){
        adj[s].add(t);
        adj[t].add(s);
    }

    /**
     * 广度优先算法
     *  地毯式搜索， 可以用于查找两个节点之间的最短路径
     *  针对下面的无向图
     *  0——————1——————4
     *  |  \   |      |
     *  |    \ |      |
     *  2——————3——————5
     *  假设图中数字为当前顶点的索引，
     *  则存在以下边:
     *  0 -> adj[0] = [1,2,3]
     *  1 -> adj[1] = [0,3,4]
     *  2 -> adj[2] = [0,3]
     *  3 -> adj[3] = [0,1,2,5]
     *  4 -> adj[4] = [1,5]
     *  5 -> adj[5] = [3,4]
     *  查找0到5之间的最短路径
     *      0到5之间的路径有：0-1-3-5、0-1-4-5、0-3-5、0-2-3-5等
     *  使用广度有限算法,输入0, 5, 输出[-1, 0, 0, 0, 1, 3]
     *  输出数组的每一个索引位置上的值代表其上一个顶点的索引位置，如果为-1则代表无上一个顶点
     *  根据输入0，5，去输出[-1, 0, 0, 0, 1, 3]，
     *  因为终点为索引5，则使用5作为索引去输出中找到上一个索引为3，
     *                  使用3作为索引去输出中找到上一个索引为0，
     *                  使用0作为索引去输出中找到上一个索引为-1，无上一个节点
     *                  所以倒推，0-5之间的最短路径为0-3-5
     * @param s 起点索引
     * @param t 终点索引
     * @return
     */
    public int[] bfs(int s, int t){
        if(s == t){
            return null;
        }
        // 保存对应索引是否被访问过，也就是遍历过
        boolean visited[] = new boolean[count];
        visited[s] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        // 结果索引数组，某一个索引里面对应的值，代表其上一个顶点
        // -1代表无节点
        int res[] = new int[count];
        for (int i = 0; i < count; i++) {
            res[i] = -1;
        }
        while (queue.size() != 0){
            int w = queue.poll();
            // 遍历当前顶点的邻接表
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                // 如果邻接顶点未被访问过
                if(!visited[q]) {
                    // 当前邻接顶点的前一个节点等于当前顶点
                    res[q] = w;
                    if (q == t) {
                       return res;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
        return res;
    }



    public void bfsByGeekTime(int s, int t) {
        if (s == t) return;
        boolean[] visited = new boolean[count];
        visited[s]=true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        int[] prev = new int[count];
        for (int i = 0; i < count; ++i) {
            prev[i] = -1;
        }
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    private void print(int[] prev, int s, int t) { // 递归打印s->t的路径
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }
}
