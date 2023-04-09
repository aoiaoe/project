package geekbang.algo.graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author jzm
 * @date 2023/4/4 : 15:25
 */
public class GraphTest {

    @Test
    public void testUndirectedGraph(){
        UndirectedGraph graph = new UndirectedGraph(6);
        for (int i = 0; i < 6; i++) {
            graph.insertVertex(i,i);
        }
        graph.insertEdge(0,1);
        graph.insertEdge(0,2);
        graph.insertEdge(0,3);
        graph.insertEdge(1,4);
        graph.insertEdge(1,3);
        graph.insertEdge(2,3);
        graph.insertEdge(3,5);
        graph.insertEdge(4,5);

        System.out.println("广度优先");
        int res[] = graph.bfs(0,5);
        System.out.println(Arrays.toString(res));
        graph.bfsByGeekTime(0, 5);
        System.out.println("深度优先");
        res = graph.dfs(0, 4);
        System.out.println(Arrays.toString(res));
        graph.dfsGeekTime(0, 4);
    }
}
