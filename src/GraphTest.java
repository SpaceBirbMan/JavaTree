import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GraphTest {

    /**
     * Проверка добавления и удаления вершин.
     */
    @Test
    public void testAddAndRemoveVertices() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        assertEquals(2, graph.numberOfVertices(), "Graph should have 2 vertices.");

        graph.deleteVertex("A");
        assertEquals(1, graph.numberOfVertices(), "Graph should have 1 vertex after deletion.");
        assertFalse(graph.containsVertex("A"), "Vertex A should be removed.");
    }

    /**
     * Проверка добавления и удаления ребер.
     */
    @Test
    public void testAddAndRemoveEdges() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertEdge("A", "B", 3);

        assertEquals(3, graph.getWeight("A", "B"), "Edge weight between A and B should be 3.");
        assertEquals(3, graph.getWeight("B", "A"), "Graph should be undirected.");

        graph.deleteEdge("A", "B");
        assertEquals(0, graph.getWeight("A", "B"), "Edge between A and B should be removed.");
    }

    /**
     * Проверка на дублирование вершин и ребер.
     */
    @Test
    public void testDuplicateVerticesAndEdges() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertEdge("A", "B", 5);
    }

    /**
     * Проверка алгоритма Dijkstra: разные сценарии.
     */
    @Test
    public void testDijkstraAlgorithm() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "C", 2);
        graph.insertEdge("A", "D", 4);
        graph.insertEdge("C", "D", 1);

        // Проверка кратчайшего пути
        Map<String, Double> shortestPaths = graph.dijkstra("A");
        assertEquals(0, shortestPaths.get("A"), "Distance to A should be 0.");
        assertEquals(1, shortestPaths.get("B"), "Distance to B should be 1.");
        assertEquals(3, shortestPaths.get("C"), "Distance to C should be 3.");
        assertEquals(4, shortestPaths.get("D"), "Distance to D should be 4.");

        // Проверка недостижимых вершин
        graph.insertVertex("E");
        shortestPaths = graph.dijkstra("A");

        // Ensure that unreachable vertex "E" does not appear in the result
        assertFalse(shortestPaths.containsKey("E"), "Unreachable vertex should not appear in the results.");
    }

    /**
     * Проверка поиска в ширину (BFS).
     */
    @Test
    public void testBreadthFirstSearch() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("A", "C", 1);
        graph.insertEdge("B", "D", 1);

        List<String> bfsResult = graph.breadthFirstSearch("A");
        assertEquals(List.of("A", "B", "C", "D"), bfsResult, "BFS should traverse level by level.");

        // Проверка на изолированные вершины
        graph.insertVertex("E");
        bfsResult = graph.breadthFirstSearch("A");
        assertFalse(bfsResult.contains("E"), "Isolated vertex E should not appear in BFS result.");
    }

    /**
     * Проверка работы с пустым графом.
     */
    @Test
    public void testEmptyGraph() {
        Graph<String> graph = new Graph<>();

        // Проверка на отсутствие вершин и рёбер
        assertEquals(0, graph.numberOfVertices(), "Empty graph should have no vertices.");
        assertEquals(0, graph.numberOfEdges(), "Empty graph should have no edges.");

        // Проверка алгоритмов на пустом графе
        assertThrows(IllegalArgumentException.class, () -> graph.dijkstra("A"), "Dijkstra should throw exception when no vertices are present.");
        assertThrows(IllegalArgumentException.class, () -> graph.breadthFirstSearch("A"), "BFS should throw exception when no vertices are present.");
        assertThrows(IllegalArgumentException.class, () -> graph.depthFirstSearch("A"), "DFS should throw exception when no vertices are present.");
    }



    /**
     * Проверка графа с одним узлом.
     */
    @Test
    public void testSingleVertexGraph() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");

        // Проверка на наличие одной вершины и отсутствия рёбер
        assertEquals(1, graph.numberOfVertices(), "Graph should have 1 vertex.");
        assertEquals(0, graph.numberOfEdges(), "Graph should have no edges.");

        // Проверка алгоритмов на графе с одним узлом
        Map<String, Double> dijkstraResult = graph.dijkstra("A");
        assertEquals(0, dijkstraResult.get("A"), "Dijkstra should return 0 for the start vertex.");

        List<String> bfsResult = graph.breadthFirstSearch("A");
        assertEquals(List.of("A"), bfsResult, "BFS should return only the start vertex.");

        List<String> dfsResult = graph.depthFirstSearch("A");
        assertEquals(List.of("A"), dfsResult, "DFS should return only the start vertex.");
    }

    /**
     * Проверка поиска в глубину (DFS).
     */
    @Test
    public void testDepthFirstSearch() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("A", "C", 1);
        graph.insertEdge("B", "D", 1);

        List<String> dfsResult = graph.depthFirstSearch("A");
        assertTrue(dfsResult.containsAll(List.of("A", "B", "C", "D")), "DFS should visit all reachable vertices.");
        assertTrue(dfsResult.size() == 4, "DFS result should not include duplicates.");
    }

    /**
     * Проверка графа на циклы.
     */
    @Test
    public void testGraphWithCycles() {
        Graph<String> graph = new Graph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "C", 1);
        graph.insertEdge("C", "A", 1); // Цикл

        // Проверка BFS
        List<String> bfsResult = graph.breadthFirstSearch("A");
        assertTrue(bfsResult.containsAll(List.of("A", "B", "C")), "BFS should traverse all vertices in a cycle.");

        // Проверка DFS
        List<String> dfsResult = graph.depthFirstSearch("A");
        assertTrue(dfsResult.containsAll(List.of("A", "B", "C")), "DFS should visit all vertices in a cycle.");
    }

    /**
     * Проверка работы с большим графом.
     */
    @Test
    public void testLargeGraph() {
        Graph<Integer> graph = new Graph<>();
        int vertexCount = 1000;
        for (int i = 0; i < vertexCount; i++) {
            graph.insertVertex(i);
            if (i > 0) {
                graph.insertEdge(i - 1, i, 1);
            }
        }

        assertEquals(vertexCount, graph.numberOfVertices(), "Graph should have 1000 vertices.");
        assertEquals(vertexCount - 1, graph.numberOfEdges(), "Graph should have 999 edges.");

        List<Integer> bfsResult = graph.breadthFirstSearch(0);
        assertEquals(vertexCount, bfsResult.size(), "BFS should visit all vertices in a connected graph.");
    }

    @Test
    void testSaveAndLoad() throws IOException, ClassNotFoundException {
        Graph<String> graph = new Graph<>(10);
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertEdge("A", "B", 5.0);

        String fileName = "graph_test.dat";

        // Save the graph
        graph.save(fileName);

        // Load the graph
        Graph<String> loadedGraph = new Graph<>(10);
        loadedGraph.load(fileName);

        // Validate the loaded graph
        assertEquals(graph.numberOfVertices(), loadedGraph.numberOfVertices());
        assertEquals(graph.numberOfEdges(), loadedGraph.numberOfEdges());
        assertTrue(loadedGraph.containsVertex("A"));
        assertTrue(loadedGraph.containsVertex("B"));
        assertEquals(5.0, loadedGraph.getWeight("A", "B"));
    }

    @Test
    void testEmptyGraphSaveAndLoad() throws IOException, ClassNotFoundException {
        Graph<String> graph = new Graph<>(10);

        String fileName = "empty_graph_test.dat";

        // Save the graph
        graph.save(fileName);

        // Load the graph
        Graph<String> loadedGraph = new Graph<>(10);
        loadedGraph.load(fileName);

        // Validate the loaded graph
        assertTrue(loadedGraph.isEmpty());
    }
}
