package geekbang.algo.graph;

import java.util.LinkedList;

public class DWGraph { // 有向有权图的邻接表表示
    private LinkedList<Edge> adj[]; // 邻接表
    private int v; // 顶点个数

    public DWGraph(int v) {
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
        public int sid; // 边的起始顶点编号
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
        private int count;

        public PriorityQueue(int v) {
            this.nodes = new Vertex[v + 1];
//            this.count = v;
        }

        public Vertex poll() {
            if (count == 0) {
                throw new RuntimeException("Heap is empty!");
            }
            Vertex node = nodes[1];
            swap(1, count);
            nodes[count--] = null;
            update(nodes[1]);
            return node;
        }

        public void add(Vertex vertex) {
            if (count == nodes.length) {
                throw new RuntimeException("Heap is full!");
            }
            nodes[++count] = vertex;
            int i = count;
            while (i > 1){
                if(nodes[i/2].dist < nodes[i].dist){
                    break;
                }
                swap(i/2, i);
            }
        }

        // 更新结点的值，并且从下往上堆化，重新符合堆的定义。时间复杂度O(logn)。
        public void update(Vertex vertex) {
            int i;
            for (i = 1; i <= count; i++) {
                if (nodes[i].id == vertex.id)
                    break;
            }
            while (i < count) {
                int pos;
                if (2 * i > count) {
                    break;
                }
                pos = 2 * i;
                if (pos < count && nodes[pos + 1].dist < nodes[pos].dist) {
                    pos = 2 * i + 1;
                }
                if(nodes[pos].dist > nodes[i].dist){
                    break;
                }
                swap(i, pos);
                i = pos;
            }
        }

        public boolean isEmpty() {
            return count == 0;
        }

        private void swap(int x, int y) {
            Vertex node = nodes[x];
            nodes[x] = nodes[y];
            nodes[y] = node;
        }
    }

    public void dijkstra(int s, int t) { // 从顶点s到顶点t的最短路径
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