package geekbang.algo.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 有向无环图
 * @author jzm
 * @date 2023/4/12 : 16:18
 */
public class DAG {

    private List<Integer> vertexes;

    private List<LinkedList<Integer>> edges;

    public DAG(){
        vertexes = new ArrayList<>();
        edges = new ArrayList<LinkedList<Integer>>();
    }

    public void insertVertex(int vertex){
        vertexes.add(vertex);
        if(edges.size() < vertexes.size()){
            edges.add(new LinkedList<Integer>());
        }
    }

    /**
     *
     * @param from  出度在顶点列表中索引
     * @param to    入度在顶点列表中的索引
     */
    public void insertEdge(int from, int to){
        edges.get(from).add(to);
    }

    /**
     * khan算法求解拓扑排序
     *
     *
     */
    public void topologicalSortByKhan(){
        // 入度数组，数组索引为顶点在定点列表中的索引, 值为入度
        int[] inDeg = new int[vertexes.size()];
        // 计算每一个顶点的入度
        for (LinkedList<Integer> edge : edges) {
            for (Integer integer : edge) {
                inDeg[integer] ++;
            }
        }
        Queue<Integer> q = new LinkedList<>();
        // 将所有入度为0的顶点索引加入队列
        for (int i = 0; i < inDeg.length; i++) {
            if(inDeg[i] == 0){
                q.add(i);
            }
        }
        while (!q.isEmpty()){
            int index = q.poll();
            System.out.print(vertexes.get(index) + "\t -> \t");
            // 计算当前顶点出度的所有顶点，入度为0的顶点加入队列
            for (Integer vet : edges.get(index)) {
                // 将顶点索引的入度-1， 如果为0则加入队列
                inDeg[vet]--;
                if(inDeg[vet] == 0){
                    q.add(vet);
                }
            }
        }
    }


    /**
     * 深度优先遍历算法求解拓扑排序
     */
    public void topologicalSortByDFS(){
        // 建立逆邻接矩阵， a -> b 转化为 b -> a
        List<List<Integer>> inverseEdge = new ArrayList<>();
        for (int i = 0; i < vertexes.size(); i++) {
            inverseEdge.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < edges.size(); i++) {
            for (Integer integer : edges.get(i)) {
                inverseEdge.get(integer).add(i);
            }
        }
        System.out.println();
        boolean visited[] = new boolean[vertexes.size()];
        for (int i = 0; i < inverseEdge.size(); i++) { // 深度优先遍历图
            if(!visited[i]) {
                visited[i] = true;
                dfs(i, inverseEdge.get(i), visited);
            }
        }
    }

    public void dfs(int vertexIndex, List<Integer> edges, boolean visited[]){
        for (int i = 0; i < edges.size(); i++) {
            int vi = edges.get(i);
            // 判断某个节点是否已经访问过
            if(visited[vi]){
                continue;
            }
            visited[vi] = true;
            dfs(vi, edges, visited);
        }
        System.out.print(vertexes.get(vertexIndex) + "\t -> \t");
    }
}
