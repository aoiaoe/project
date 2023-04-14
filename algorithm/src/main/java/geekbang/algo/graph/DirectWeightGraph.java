package geekbang.algo.graph;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 有向有权图
 *
 * @author jzm
 * @date 2023/4/13 : 15:56
 */
public class DirectWeightGraph {

    private LinkedList<Edge> adj[]; // 领接表， 索引代表顶点， 值为其出度顶点

    private int v; // 顶点个数

    public DirectWeightGraph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            this.adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t, int w) { // 添加一条边
        this.adj[s].add(new Edge(s, t, w));
    }

    private class Edge {
        public int sid; // 边的起始顶点编号， 就是顶点索引
        public int tid; // 边的终止顶点编号
        public int w; // 权重

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }

        @Override
        public String toString() {
            return "{" +
                    "sid=" + sid +
                    ", tid=" + tid +
                    ", w=" + w +
                    '}';
        }
    }

    // 下面这个类是为了dijkstra实现用的
    private class Vertex {
        public int id; // 顶点编号ID
        public int dist; // 从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", dist=" + dist +
                    '}';
        }
    }


    // 因为Java提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个
    private class PriorityQueue { // 根据vertex.dist构建小顶堆
        private Vertex[] nodes;
        private int count = 0;

        public PriorityQueue(int v) {
            this.nodes = new Vertex[v + 1];
//            this.count = v;
        }

        public Vertex poll() { // TODO: 留给读者实现...
            if(count == 0){
                return null;
            }
            Vertex v = nodes[1];
            nodes[1] = nodes[count];
            nodes[count--] = null;
            int i = 1;
            while (i < count){
                int min;
                if((i * 2) > count){
                    break;
                }
                min = i * 2;
                if((min + 1) < count && nodes[min].dist < nodes[min + 1].dist){
                    min = min+1;
                }
                if(nodes[min].dist > nodes[i].dist){
                    break;
                }
                swap(i, min);
                i = min;
            }
            return v;
        }

        public void add(Vertex vertex) { // TODO: 留给读者实现.
            if(count > nodes.length){
                throw new RuntimeException("Out of range");
            }
            nodes[++count] = vertex;
            update(vertex);
        }

        // 更新结点的值，并且从下往上堆化，重新符合堆的定义。时间复杂度O(logn)。
        public void update(Vertex vertex) { // TODO: 留给读者实现...
            int i = 1;
            for (i = 1;  i <= count; i++) {
                if(vertex.id == nodes[i].id)
                    break;
            }
            while (i > 1){
                if(nodes[i].dist > nodes[i / 2].dist)
                    break;
                swap(i, i/2);
                i = i / 2;
            }
        }

        public boolean isEmpty() { // TODO: 留给读者实现...
            return count == 0;
        }

        public void swap(int idx1, int idx2){
            nodes[0] = nodes[idx1];
            nodes[idx1] = nodes[idx2];
            nodes[idx2] = nodes[0];
            nodes[0] = null;
        }
    }

    public void dijkstraShortestPath(int s, int t){
        // 声明一个顶点数组，索引：顶点， 值：当前定到到起始顶点的距离
        // 例如： vertices[2] = new Vertex(2, 10); 代表2这个顶点到起始顶点的距离为10
        Vertex[] vertices = new Vertex[v];
        for (int i = 0; i < v; i++) {
            // 初始化当前顶点的距离到起始顶点的距离为无限远
            vertices[i] = new Vertex(i, Integer.MAX_VALUE);
        }
        // 因为起始顶点也在顶点数组，将起始顶点距离初始化为0， 自身的距离肯定为0
        vertices[s].dist = 0;
        // 声明优先级队列，每次都选择离起始顶点距离最近的顶点出队
        PriorityQueue queue = new PriorityQueue(v);
        // 声明标志数组，记录哪一个索引的顶点已经如果队列了， 如果为true，就是更新对节点的值，而不是新增堆节点
        boolean inQueue[] = new boolean[v];
        queue.add(vertices[0]);
        inQueue[s] = true;
        // 声明路径数组，用于记录和回推顶点
        int[] preVertex = new int[v];

        while (!queue.isEmpty()){
            // 将当前从起始节点路径最短的顶点出队， 就是堆顶的顶点
            Vertex minPathV = queue.poll();
            // 如果当前顶点等于终止顶点，找到退出循环
            if(minPathV.id == t) break;
            // 遍历当前顶点的所有邻接边
            for (int i = 0; i < adj[minPathV.id].size(); i++) {
                // 取一个与当前顶点相连的边
                Edge edge = adj[minPathV.id].get(i);
                // 根据邻接边找到下一个邻接顶点
                // s  --dist-->  minPathV --w--> nextV
                Vertex nextV = vertices[edge.tid];
                // 如果当前顶点到起始顶点距离 + 到下一个顶点的距离 < 下一个顶点到起始顶点的距离
                if(minPathV.dist + edge.w < nextV.dist){
                    nextV.dist = minPathV.dist + edge.w;
                    // 记录下一个顶点的上一个顶点为当前顶点
                    preVertex[nextV.id] = minPathV.id;
                    if(inQueue[nextV.id]){
                        queue.update(nextV);
                    } else {
                        queue.add(nextV);
                        inQueue[nextV.id] = true;
                    }
                }
            }
        }
        System.out.println("手写最短路径算法结果: " + Arrays.toString(preVertex));
        printResult(s, t, preVertex);
        System.out.println();

    }
    public void printResult(int s, int t, int[] pre){
        if(s == t){
            System.out.print(s + "->");
            return;
        }
        printResult(s, pre[t], pre);
        System.out.print(t + "->");
    }


    // ----------------极客时间------------

    public void dijkstraGeekTIme(int s, int t) { // 从顶点s到顶点t的最短路径
        int[] predecessor = new int[this.v]; // 用来还原最短路径
        Vertex[] vertexes = new Vertex[this.v];
        for (int i = 0; i < this.v; ++i) {
            vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
        }
        PriorityQueue queue = new PriorityQueue(this.v);// 小顶堆
        boolean[] inqueue = new boolean[this.v]; // 标记是否进入过队列
        vertexes[s].dist = 0;
        queue.add(vertexes[s]);
        inqueue[s] = true;
        while (!queue.isEmpty()) {
            Vertex minVertex = queue.poll(); // 取堆顶元素并删除
            if (minVertex.id == t) break; // 最短路径产生了
            for (int i = 0; i < adj[minVertex.id].size(); ++i) {
                Edge e = adj[minVertex.id].get(i); // 取出一条minVetex相连的边
                Vertex nextVertex = vertexes[e.tid]; // minVertex-->nextVertex
                if (minVertex.dist + e.w < nextVertex.dist) { // 更新next的dist
                    nextVertex.dist = minVertex.dist + e.w;
                    predecessor[nextVertex.id] = minVertex.id;
                    if (inqueue[nextVertex.id] == true) {
                        queue.update(nextVertex); // 更新队列中的dist值
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
            }
        }
        // 输出最短路径
        System.out.print(s);
        print(s, t, predecessor);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.print("->" + t);
    }
}
