package geekbang.algo.graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author jzm
 * @date 2023/4/4 : 15:25
 */
public class GraphTest {

    @Test
    public void testUndirectedGraph() {
        UndirectedGraph graph = new UndirectedGraph(6);
        for (int i = 0; i < 6; i++) {
            graph.insertVertex(i, i);
        }
        graph.insertEdge(0, 1);
        graph.insertEdge(0, 2);
        graph.insertEdge(0, 3);
        graph.insertEdge(1, 4);
        graph.insertEdge(1, 3);
        graph.insertEdge(2, 3);
        graph.insertEdge(3, 5);
        graph.insertEdge(4, 5);

        System.out.println("广度优先");
        int res[] = graph.bfs(0, 5);
        System.out.println(Arrays.toString(res));
        graph.bfsByGeekTime(0, 5);
        System.out.println("深度优先");
        res = graph.dfs(0, 4);
        System.out.println(Arrays.toString(res));
        graph.dfsGeekTime(0, 4);
    }

    @Test
    public void testDAGTopologicalSort(){
        DAG dag = new DAG();

        for (int i = 0; i < 6; i++) {
            dag.insertVertex((i + 1) * (i + 1));
        }
        dag.insertEdge(1, 2);
        dag.insertEdge(0, 2);
        dag.insertEdge(2, 3);
        dag.insertEdge(3, 5);
        dag.insertEdge(3, 4);

        dag.topologicalSortByKhan();
        dag.topologicalSortByDFS();
    }

    @Test
    public void testDAGGeekTime(){
        DAGraphGeekTime dag = new DAGraphGeekTime(6);
        dag.addEdge(0, 2);
        dag.addEdge(1, 2);
        dag.addEdge(2, 3);
        dag.addEdge(3, 4);
        dag.addEdge(3, 5);
        System.out.println("Khan算法拓扑排序:");
        dag.topoSortByKahn();
        System.out.println();
        System.out.println("深优先遍历算法拓扑排序:");
        dag.topoSortByDFS();
    }

    @Test
    public void testDWGDijkstra(){
        DirectWeightGraph dwg = new DirectWeightGraph(7);
        dwg.addEdge(0,1,2);
        dwg.addEdge(1,2,7);
        dwg.addEdge(1,3,4);
        dwg.addEdge(1,4,6);
        dwg.addEdge(2,5,6);
        dwg.addEdge(3,5,8);
        dwg.addEdge(4,6,9);
        dwg.addEdge(5,6,1);

        dwg.dijkstraShortestPath(0, 6);
        System.out.println("极客时间算法结果：");
        dwg.dijkstraGeekTIme(0, 6);
    }

    @Test
    public void testDWGDijkstra2(){
        DirectWeightGraph dwg = new DirectWeightGraph(6);
        dwg.addEdge(0,1,10);
        dwg.addEdge(0,4,15);
        dwg.addEdge(1,2,15);
        dwg.addEdge(1,3,2);
        dwg.addEdge(3,2,1);
        dwg.addEdge(2,5,5);
        dwg.addEdge(3,5,12);
        dwg.addEdge(4,5,10);

        dwg.dijkstraShortestPath(0, 5);
        System.out.println("极客时间算法结果：");
        dwg.dijkstraGeekTIme(0, 5);
    }

    @Test
    public void testDWG() {
        DWGraph dwg = new DWGraph(7);
        dwg.addEdge(1, 2, 7);
        dwg.addEdge(0, 1, 2);
        dwg.addEdge(1, 3, 4);
        dwg.addEdge(1, 4, 6);
        dwg.addEdge(2, 5, 6);
        dwg.addEdge(3, 5, 8);
        dwg.addEdge(4, 6, 9);
        dwg.addEdge(5, 6, 1);
        System.out.println();
        dwg.dijkstra(0, 6);
    }
    @Test
    public void testDWG2() {
        DWGraph dwg = new DWGraph(7);
        dwg.addEdge(0,1,10);
        dwg.addEdge(0,4,15);
        dwg.addEdge(1,2,15);
        dwg.addEdge(1,3,2);
        dwg.addEdge(3,2,1);
        dwg.addEdge(2,5,5);
        dwg.addEdge(3,5,12);
        dwg.addEdge(4,5,10);
        System.out.println();
        dwg.dijkstra(0, 5);
    }
}
