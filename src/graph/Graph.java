package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Undirected graph stored as an adjacency list.
 */
public class Graph {

    private final Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a vertex if it is not already present.
     *
     * @return true if the vertex was added, false if it already existed
     */
    public boolean addVertex(int vertex) {
        if (adjacencyList.containsKey(vertex)) {
            return false;
        }
        adjacencyList.put(vertex, new ArrayList<>());
        return true;
    }

    /**
     * Adds an undirected edge. Missing vertices are created automatically.
     * Duplicate edges are rejected. A self-loop is stored once.
     *
     * @return true if the edge was added, false if it already existed
     */
    public boolean addEdge(int source, int destination) {
        addVertex(source);
        addVertex(destination);

        if (containsEdge(source, destination)) {
            return false;
        }

        insertNeighborSorted(source, destination);
        if (source != destination) {
            insertNeighborSorted(destination, source);
        }
        return true;
    }

    public boolean containsVertex(int vertex) {
        return adjacencyList.containsKey(vertex);
    }

    public boolean containsEdge(int source, int destination) {
        List<Integer> neighbors = adjacencyList.get(source);
        if (neighbors == null || !adjacencyList.containsKey(destination)) {
            return false;
        }
        return neighbors.contains(destination);
    }

    /**
     * Prints the adjacency list with vertices in sorted order.
     */
    public void display() {
        if (adjacencyList.isEmpty()) {
            System.out.println("Graph is empty.");
            return;
        }
        System.out.println("Adjacency List:");
        for (int vertex : new TreeSet<>(adjacencyList.keySet())) {
            System.out.println(vertex + " -> " + adjacencyList.get(vertex));
        }
    }

    /**
     * Queue-based BFS of the connected component reachable from {@code startVertex}.
     * Neighbors are visited in sorted order. Returns an empty list if the start
     * vertex is not in the graph.
     */
    public List<Integer> bfs(int startVertex) {
        List<Integer> visitOrder = new ArrayList<>();
        if (!containsVertex(startVertex)) {
            return visitOrder;
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();

        visited.add(startVertex);
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int current = queue.remove();
            visitOrder.add(current);

            for (int neighbor : adjacencyList.get(current)) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        return visitOrder;
    }

    /**
     * Recursive DFS of the connected component reachable from {@code startVertex}.
     * Neighbors are visited in sorted order. Returns an empty list if the start
     * vertex is not in the graph.
     */
    public List<Integer> dfs(int startVertex) {
        List<Integer> visitOrder = new ArrayList<>();
        if (!containsVertex(startVertex)) {
            return visitOrder;
        }
        dfsVisit(startVertex, new HashSet<>(), visitOrder);
        return visitOrder;
    }

    private void dfsVisit(int current, Set<Integer> visited, List<Integer> visitOrder) {
        visited.add(current);
        visitOrder.add(current);
        for (int neighbor : adjacencyList.get(current)) {
            if (!visited.contains(neighbor)) {
                dfsVisit(neighbor, visited, visitOrder);
            }
        }
    }

    /**
     * Returns a defensive copy of the vertex set.
     */
    public Set<Integer> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    private void insertNeighborSorted(int vertex, int neighbor) {
        List<Integer> neighbors = adjacencyList.get(vertex);
        int index = Collections.binarySearch(neighbors, neighbor);
        if (index < 0) {
            neighbors.add(-(index + 1), neighbor);
        }
    }

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        System.out.println("=== Graph tests ===");

        // 1. Empty graph operations
        Graph empty = new Graph();
        int[] r;
        r = record(passed, failed, "empty vertices", empty.getVertices().isEmpty());
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "empty containsVertex", !empty.containsVertex(1));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "empty containsEdge", !empty.containsEdge(1, 2));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "empty bfs", empty.bfs(1).isEmpty());
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "empty dfs", empty.dfs(1).isEmpty());
        passed = r[0];
        failed = r[1];
        System.out.println("-- display() on empty graph --");
        empty.display();

        // 2. Adding single and multiple vertices
        Graph vertices = new Graph();
        r = record(passed, failed, "addVertex first", vertices.addVertex(1));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "addVertex second", vertices.addVertex(2));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "addVertex third", vertices.addVertex(3));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "addVertex duplicate", !vertices.addVertex(1));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "getVertices {1,2,3}", vertices.getVertices().equals(Set.of(1, 2, 3)));
        passed = r[0];
        failed = r[1];

        // 3. Edges, duplicates, and self-loops
        Graph edges = new Graph();
        r = record(passed, failed, "add first edge", edges.addEdge(1, 2));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "reject duplicate edge", !edges.addEdge(1, 2));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "reject reverse duplicate", !edges.addEdge(2, 1));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "auto-add missing vertices",
                edges.addEdge(3, 4) && edges.containsVertex(3) && edges.containsVertex(4));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "add self-loop", edges.addEdge(5, 5));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "reject duplicate self-loop", !edges.addEdge(5, 5));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "contains self-loop", edges.containsEdge(5, 5));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "undirected containsEdge",
                edges.containsEdge(1, 2) && edges.containsEdge(2, 1));
        passed = r[0];
        failed = r[1];

        // 4. Missing vertices / invalid start vertices
        Graph missing = new Graph();
        missing.addVertex(1);
        r = record(passed, failed, "bfs invalid start", missing.bfs(99).isEmpty());
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "dfs invalid start", missing.dfs(99).isEmpty());
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "missing edge lookups",
                !missing.containsEdge(1, 2) && !missing.containsEdge(8, 9));
        passed = r[0];
        failed = r[1];

        // 5. Deterministic graph:
        // 1 -- 2
        // |    |
        // 3 -- 4 -- 5
        // disconnected: 6
        Graph g = sampleGraph();
        System.out.println("-- display() sample graph --");
        g.display();

        List<Integer> expectedBfs = List.of(1, 2, 3, 4, 5);
        List<Integer> expectedDfs = List.of(1, 2, 4, 3, 5);
        r = record(passed, failed, "bfs(1) order " + expectedBfs, expectedBfs.equals(g.bfs(1)));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "dfs(1) order " + expectedDfs, expectedDfs.equals(g.dfs(1)));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "bfs(6) disconnected", List.of(6).equals(g.bfs(6)));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "dfs(6) disconnected", List.of(6).equals(g.dfs(6)));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "component excludes 6",
                !g.bfs(1).contains(6) && !g.dfs(1).contains(6));
        passed = r[0];
        failed = r[1];

        System.out.println("BFS(1): " + g.bfs(1));
        System.out.println("DFS(1): " + g.dfs(1));
        System.out.println("BFS(6): " + g.bfs(6));
        System.out.println("DFS(6): " + g.dfs(6));

        // 6. Cycle handling and determinism
        r = record(passed, failed, "bfs deterministic", g.bfs(1).equals(g.bfs(1)));
        passed = r[0];
        failed = r[1];
        r = record(passed, failed, "dfs deterministic", g.dfs(1).equals(g.dfs(1)));
        passed = r[0];
        failed = r[1];
        Graph withLoop = sampleGraph();
        withLoop.addEdge(4, 4);
        r = record(passed, failed, "self-loop does not break traversal",
                withLoop.bfs(1).equals(expectedBfs) && withLoop.dfs(1).equals(expectedDfs));
        passed = r[0];
        failed = r[1];

        int total = passed + failed;
        System.out.println("=== Results: " + passed + " passed, " + failed + " failed, " + total + " total ===");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static Graph sampleGraph() {
        Graph g = new Graph();
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addVertex(6);
        return g;
    }

    private static int[] record(int passed, int failed, String name, boolean condition) {
        System.out.println((condition ? "PASS: " : "FAIL: ") + name);
        if (condition) {
            return new int[] {passed + 1, failed};
        }
        return new int[] {passed, failed + 1};
    }
}
