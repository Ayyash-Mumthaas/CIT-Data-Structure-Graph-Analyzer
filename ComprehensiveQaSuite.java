import data.ArrayManager;
import data.LinkedList;
import data.Stack;
import data.Queue;
import graph.Graph;
import searching.SearchAlgorithms;
import searching.SearchResult;
import performance.PerformanceAnalyzer;
import performance.PerformanceResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class ComprehensiveQaSuite {

    private static int totalTests = 0;
    private static int failedTests = 0;
    private static final List<String> failures = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Starting Comprehensive QA Suite for CIT300 Data Structure & Graph Analyzer...");

        testArrayManager();
        testLinkedList();
        testStack();
        testQueue();
        testGraph();
        testSearching();
        testPerformance();
        testMainIntegration();

        System.out.println("\n=======================================================");
        System.out.printf("Test Suite Summary: %d total tests, %d failed.\n", totalTests, failedTests);
        System.out.println("=======================================================");

        if (failedTests > 0) {
            System.out.println("FAILURES ENCOUNTERED:");
            for (String f : failures) {
                System.out.println(f);
            }
            System.exit(1);
        } else {
            System.out.println("ALL TESTS PASSED.");
        }
    }

    private static void assertCondition(String module, String testName, String expected, String actual, boolean condition, String evidence) {
        totalTests++;
        if (!condition) {
            failedTests++;
            String record = String.format("[FAILED] [%s] Test: %s | Expected: %s | Actual: %s | Evidence: %s",
                    module, testName, expected, actual, evidence);
            failures.add(record);
            System.out.println(record);
        }
    }

    // -------------------------------------------------------------
    // MODULE 3: ARRAY MANAGER
    // -------------------------------------------------------------
    private static void testArrayManager() {
        System.out.println("\n--- Testing ArrayManager ---");

        // 1. Empty array behaviour
        ArrayManager arr = new ArrayManager();
        assertCondition("ARRAY", "empty array size", "0", String.valueOf(arr.size()), arr.size() == 0, "size() != 0 on new ArrayManager");
        assertCondition("ARRAY", "empty array isEmpty", "true", String.valueOf(arr.isEmpty()), arr.isEmpty(), "isEmpty() returned false on new ArrayManager");
        assertCondition("ARRAY", "empty array capacity", "10", String.valueOf(arr.capacity()), arr.capacity() == 10, "default capacity != 10");
        assertCondition("ARRAY", "empty array delete", "false", String.valueOf(arr.delete(5)), !arr.delete(5), "delete on empty array returned true");
        assertCondition("ARRAY", "empty array search", "-1", String.valueOf(arr.search(5)), arr.search(5) == -1, "search on empty array returned index >= 0");
        assertCondition("ARRAY", "empty array comparisons", "0", String.valueOf(arr.getLastSearchComparisons()), arr.getLastSearchComparisons() == 0, "comparisons != 0 on empty search");
        assertCondition("ARRAY", "empty array toArray length", "0", String.valueOf(arr.toArray().length), arr.toArray().length == 0, "toArray() did not return empty array");

        // Access invalid index on empty array
        boolean caughtEmpty = false;
        try {
            arr.get(0);
        } catch (IndexOutOfBoundsException e) {
            caughtEmpty = true;
        }
        assertCondition("ARRAY", "empty array get(0) throws", "IndexOutOfBoundsException", caughtEmpty ? "caught" : "not caught", caughtEmpty, "get(0) on empty array did not throw IndexOutOfBoundsException");

        // 2. Insert, negative values, and -1
        arr.insert(10);
        arr.insert(-1);
        arr.insert(-50);
        arr.insert(0);
        assertCondition("ARRAY", "size after 4 inserts", "4", String.valueOf(arr.size()), arr.size() == 4, "size mismatch");
        assertCondition("ARRAY", "get valid indices", "[10, -1, -50, 0]",
                Arrays.toString(arr.toArray()),
                arr.get(0) == 10 && arr.get(1) == -1 && arr.get(2) == -50 && arr.get(3) == 0,
                "Elements do not match inserted values");

        // Search -1 and negative values
        int idxNeg1 = arr.search(-1);
        assertCondition("ARRAY", "search(-1)", "1", String.valueOf(idxNeg1), idxNeg1 == 1, "Failed to find -1 at index 1");
        assertCondition("ARRAY", "search(-1) comparison count", "2", String.valueOf(arr.getLastSearchComparisons()), arr.getLastSearchComparisons() == 2, "Comparisons count mismatch for index 1");

        int idxNeg50 = arr.search(-50);
        assertCondition("ARRAY", "search(-50)", "2", String.valueOf(idxNeg50), idxNeg50 == 2, "Failed to find -50 at index 2");
        assertCondition("ARRAY", "search(-50) comparison count", "3", String.valueOf(arr.getLastSearchComparisons()), arr.getLastSearchComparisons() == 3, "Comparisons count mismatch for index 2");

        // Search nonexistent
        int idxAbsent = arr.search(999);
        assertCondition("ARRAY", "search absent", "-1", String.valueOf(idxAbsent), idxAbsent == -1, "Found nonexistent element");
        assertCondition("ARRAY", "search absent comparisons", "4", String.valueOf(arr.getLastSearchComparisons()), arr.getLastSearchComparisons() == 4, "Comparisons mismatch for absent element");

        // Delete nonexistent value
        boolean delNonExistent = arr.delete(999);
        assertCondition("ARRAY", "delete nonexistent", "false", String.valueOf(delNonExistent), !delNonExistent, "delete(999) returned true");
        assertCondition("ARRAY", "size after delete nonexistent", "4", String.valueOf(arr.size()), arr.size() == 4, "size changed after failed delete");

        // Delete -1 (middle element)
        boolean delNeg1 = arr.delete(-1);
        assertCondition("ARRAY", "delete(-1)", "true", String.valueOf(delNeg1), delNeg1, "delete(-1) failed");
        assertCondition("ARRAY", "size after delete(-1)", "3", String.valueOf(arr.size()), arr.size() == 3, "size mismatch after delete");
        assertCondition("ARRAY", "elements shifted after delete(-1)", "[10, -50, 0]", Arrays.toString(arr.toArray()),
                arr.get(0) == 10 && arr.get(1) == -50 && arr.get(2) == 0, "Elements not shifted correctly");

        // Invalid index bounds
        boolean caughtNegIndex = false;
        try { arr.get(-1); } catch (IndexOutOfBoundsException e) { caughtNegIndex = true; }
        assertCondition("ARRAY", "get(-1) throws", "IndexOutOfBoundsException", caughtNegIndex ? "caught" : "not caught", caughtNegIndex, "get(-1) did not throw");

        boolean caughtOOB = false;
        try { arr.get(3); } catch (IndexOutOfBoundsException e) { caughtOOB = true; }
        assertCondition("ARRAY", "get(3) throws (size=3)", "IndexOutOfBoundsException", caughtOOB ? "caught" : "not caught", caughtOOB, "get(3) did not throw");

        // Resizing test
        ArrayManager resizeArr = new ArrayManager(2);
        assertCondition("ARRAY", "custom initial capacity", "2", String.valueOf(resizeArr.capacity()), resizeArr.capacity() == 2, "custom capacity mismatch");
        resizeArr.insert(1);
        resizeArr.insert(2);
        assertCondition("ARRAY", "capacity before doubling", "2", String.valueOf(resizeArr.capacity()), resizeArr.capacity() == 2, "premature resize");
        resizeArr.insert(3);
        assertCondition("ARRAY", "capacity doubled to 4", "4", String.valueOf(resizeArr.capacity()), resizeArr.capacity() == 4, "resize capacity != 4");
        resizeArr.insert(4);
        resizeArr.insert(5);
        assertCondition("ARRAY", "capacity doubled to 8", "8", String.valueOf(resizeArr.capacity()), resizeArr.capacity() == 8, "resize capacity != 8");
        assertCondition("ARRAY", "elements intact after resize", "[1, 2, 3, 4, 5]", Arrays.toString(resizeArr.toArray()),
                Arrays.equals(resizeArr.toArray(), new int[]{1, 2, 3, 4, 5}), "data corrupted during resize");

        // Clear test
        resizeArr.clear();
        assertCondition("ARRAY", "clear size", "0", String.valueOf(resizeArr.size()), resizeArr.size() == 0, "size != 0 after clear");
        assertCondition("ARRAY", "clear isEmpty", "true", String.valueOf(resizeArr.isEmpty()), resizeArr.isEmpty(), "isEmpty false after clear");
        assertCondition("ARRAY", "clear capacity preserved", "8", String.valueOf(resizeArr.capacity()), resizeArr.capacity() == 8, "capacity lost after clear");

        // Display test without exceptions
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);
        resizeArr.display();
        arr.display();
        System.setOut(oldOut);
        assertCondition("ARRAY", "display executes without exception", "true", "true", true, "");
    }

    // -------------------------------------------------------------
    // MODULE 4: LINKED LIST
    // -------------------------------------------------------------
    private static void testLinkedList() {
        System.out.println("\n--- Testing LinkedList ---");

        LinkedList list = new LinkedList();
        assertCondition("LINKED_LIST", "empty list size", "0", String.valueOf(list.size()), list.size() == 0, "size != 0 on new list");
        assertCondition("LINKED_LIST", "empty list isEmpty", "true", String.valueOf(list.isEmpty()), list.isEmpty(), "isEmpty false on new list");
        assertCondition("LINKED_LIST", "empty list delete", "false", String.valueOf(list.delete(10)), !list.delete(10), "delete on empty list returned true");
        assertCondition("LINKED_LIST", "empty list search", "false", String.valueOf(list.search(10)), !list.search(10), "search on empty list returned true");

        // Display on empty list
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        list.display();
        System.setOut(oldOut);
        assertCondition("LINKED_LIST", "display on empty list executes", "true", "true", true, "");

        // Insert elements (Head insertion)
        list.insert(10);
        list.insert(-1);
        list.insert(20);
        list.insert(-1); // duplicate -1
        assertCondition("LINKED_LIST", "size after 4 inserts", "4", String.valueOf(list.size()), list.size() == 4, "size mismatch");
        assertCondition("LINKED_LIST", "search present 10", "true", String.valueOf(list.search(10)), list.search(10), "search(10) failed");
        assertCondition("LINKED_LIST", "search present -1", "true", String.valueOf(list.search(-1)), list.search(-1), "search(-1) failed");
        assertCondition("LINKED_LIST", "search present 20", "true", String.valueOf(list.search(20)), list.search(20), "search(20) failed");
        assertCondition("LINKED_LIST", "search absent 99", "false", String.valueOf(list.search(99)), !list.search(99), "search(99) returned true");

        // Delete head duplicate (-1 was inserted last, so it is head)
        boolean delHead = list.delete(-1);
        assertCondition("LINKED_LIST", "delete head duplicate -1", "true", String.valueOf(delHead), delHead, "delete(-1) failed");
        assertCondition("LINKED_LIST", "size after delete head", "3", String.valueOf(list.size()), list.size() == 3, "size mismatch after delete head");
        // Still has one -1
        assertCondition("LINKED_LIST", "search second -1 after deleting first", "true", String.valueOf(list.search(-1)), list.search(-1), "search(-1) failed after deleting head duplicate");

        // Delete tail (10 was inserted first, so it is at tail)
        boolean delTail = list.delete(10);
        assertCondition("LINKED_LIST", "delete tail 10", "true", String.valueOf(delTail), delTail, "delete(10) failed");
        assertCondition("LINKED_LIST", "size after delete tail", "2", String.valueOf(list.size()), list.size() == 2, "size mismatch after delete tail");
        assertCondition("LINKED_LIST", "search deleted 10", "false", String.valueOf(list.search(10)), !list.search(10), "search(10) returned true after deletion");

        // Delete missing value
        boolean delMissing = list.delete(999);
        assertCondition("LINKED_LIST", "delete missing", "false", String.valueOf(delMissing), !delMissing, "delete(999) returned true");
        assertCondition("LINKED_LIST", "size after failed delete", "2", String.valueOf(list.size()), list.size() == 2, "size changed on failed delete");

        // Delete remaining
        list.delete(20);
        list.delete(-1);
        assertCondition("LINKED_LIST", "size after deleting all", "0", String.valueOf(list.size()), list.size() == 0, "size != 0 after clearing list");
        assertCondition("LINKED_LIST", "isEmpty after deleting all", "true", String.valueOf(list.isEmpty()), list.isEmpty(), "isEmpty false after clearing list");
    }

    // -------------------------------------------------------------
    // MODULE 5: STACK
    // -------------------------------------------------------------
    private static void testStack() {
        System.out.println("\n--- Testing Stack ---");

        Stack stack = new Stack();
        assertCondition("STACK", "empty stack size", "0", String.valueOf(stack.size()), stack.size() == 0, "size != 0 on new stack");
        assertCondition("STACK", "empty stack isEmpty", "true", String.valueOf(stack.isEmpty()), stack.isEmpty(), "isEmpty false on new stack");
        assertCondition("STACK", "empty stack pop returns -1", "-1", String.valueOf(stack.pop()), stack.pop() == -1, "pop() != -1 on empty stack");
        assertCondition("STACK", "empty stack peek returns -1", "-1", String.valueOf(stack.peek()), stack.peek() == -1, "peek() != -1 on empty stack");
        assertCondition("STACK", "empty stack size stays 0", "0", String.valueOf(stack.size()), stack.size() == 0, "size changed on empty pop");

        // Push, negative values, and storing -1
        stack.push(-1);
        assertCondition("STACK", "size after pushing -1", "1", String.valueOf(stack.size()), stack.size() == 1, "size mismatch");
        assertCondition("STACK", "isEmpty after pushing -1", "false", String.valueOf(stack.isEmpty()), !stack.isEmpty(), "isEmpty true when holding -1");
        assertCondition("STACK", "peek() when holding -1", "-1", String.valueOf(stack.peek()), stack.peek() == -1, "peek != -1");
        assertCondition("STACK", "size after peek", "1", String.valueOf(stack.size()), stack.size() == 1, "size changed on peek");
        int poppedNeg1 = stack.pop();
        assertCondition("STACK", "pop() when holding -1", "-1", String.valueOf(poppedNeg1), poppedNeg1 == -1, "popped value != -1");
        assertCondition("STACK", "size after popping -1", "0", String.valueOf(stack.size()), stack.size() == 0, "size != 0 after pop");
        assertCondition("STACK", "isEmpty after popping -1", "true", String.valueOf(stack.isEmpty()), stack.isEmpty(), "isEmpty false after pop");

        // LIFO order test
        Stack s2 = new Stack();
        s2.push(10);
        s2.push(20);
        s2.push(30);
        assertCondition("STACK", "LIFO peek", "30", String.valueOf(s2.peek()), s2.peek() == 30, "peek mismatch");
        int p1 = s2.pop();
        int p2 = s2.pop();
        int p3 = s2.pop();
        assertCondition("STACK", "LIFO sequence", "[30, 20, 10]", "[" + p1 + ", " + p2 + ", " + p3 + "]",
                p1 == 30 && p2 == 20 && p3 == 10, "LIFO order violated");

        // Resizing test (INITIAL_CAPACITY = 8)
        Stack bigStack = new Stack();
        for (int i = 0; i < 20; i++) {
            bigStack.push(i);
        }
        assertCondition("STACK", "size after 20 pushes", "20", String.valueOf(bigStack.size()), bigStack.size() == 20, "size != 20 after resize");
        boolean lifoIntact = true;
        for (int i = 19; i >= 0; i--) {
            if (bigStack.pop() != i) {
                lifoIntact = false;
                break;
            }
        }
        assertCondition("STACK", "elements intact across resize", "true", String.valueOf(lifoIntact), lifoIntact, "elements corrupted during resize");
        assertCondition("STACK", "size 0 after popping all resized", "0", String.valueOf(bigStack.size()), bigStack.size() == 0, "size != 0");

        // Display test
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        bigStack.display(); // empty
        s2.push(100);
        s2.display();
        System.setOut(oldOut);
        assertCondition("STACK", "display executes", "true", "true", true, "");
    }

    // -------------------------------------------------------------
    // MODULE 6: QUEUE
    // -------------------------------------------------------------
    private static void testQueue() {
        System.out.println("\n--- Testing Queue ---");

        Queue queue = new Queue();
        assertCondition("QUEUE", "empty queue size", "0", String.valueOf(queue.size()), queue.size() == 0, "size != 0 on new queue");
        assertCondition("QUEUE", "empty queue isEmpty", "true", String.valueOf(queue.isEmpty()), queue.isEmpty(), "isEmpty false on new queue");
        assertCondition("QUEUE", "empty queue dequeue returns -1", "-1", String.valueOf(queue.dequeue()), queue.dequeue() == -1, "dequeue() != -1 on empty queue");
        assertCondition("QUEUE", "empty queue peek returns -1", "-1", String.valueOf(queue.peek()), queue.peek() == -1, "peek() != -1 on empty queue");

        // Enqueue -1 and negative values
        queue.enqueue(-1);
        assertCondition("QUEUE", "size after enqueuing -1", "1", String.valueOf(queue.size()), queue.size() == 1, "size mismatch");
        assertCondition("QUEUE", "isEmpty after enqueuing -1", "false", String.valueOf(queue.isEmpty()), !queue.isEmpty(), "isEmpty true when holding -1");
        assertCondition("QUEUE", "peek() when holding -1", "-1", String.valueOf(queue.peek()), queue.peek() == -1, "peek != -1");
        int deqNeg1 = queue.dequeue();
        assertCondition("QUEUE", "dequeue() when holding -1", "-1", String.valueOf(deqNeg1), deqNeg1 == -1, "dequeued != -1");
        assertCondition("QUEUE", "size after dequeue -1", "0", String.valueOf(queue.size()), queue.size() == 0, "size != 0");

        // FIFO order test
        Queue q2 = new Queue();
        q2.enqueue(10);
        q2.enqueue(20);
        q2.enqueue(30);
        int f1 = q2.dequeue();
        int f2 = q2.dequeue();
        int f3 = q2.dequeue();
        assertCondition("QUEUE", "FIFO sequence", "[10, 20, 30]", "[" + f1 + ", " + f2 + ", " + f3 + "]",
                f1 == 10 && f2 == 20 && f3 == 30, "FIFO order violated");

        // Circular wrap-around and resizing test
        Queue circ = new Queue(); // INITIAL_CAPACITY = 8
        // Enqueue 5
        for (int i = 0; i < 5; i++) circ.enqueue(i);
        // Dequeue 3 (front advances to index 3)
        for (int i = 0; i < 3; i++) circ.dequeue();
        // Enqueue 6 items: indices wrap around 3,4,5,6,7, 0,1,2 -> size reaches 8
        for (int i = 100; i < 106; i++) circ.enqueue(i);
        assertCondition("QUEUE", "wrap-around size is 8", "8", String.valueOf(circ.size()), circ.size() == 8, "size mismatch on wrap-around");

        // Now trigger resize by enqueuing one more
        circ.enqueue(200);
        assertCondition("QUEUE", "size after wrap-around resize is 9", "9", String.valueOf(circ.size()), circ.size() == 9, "size mismatch after resize");

        // Check values dequeued in exact FIFO order: [3, 4, 100, 101, 102, 103, 104, 105, 200]
        int[] expected = {3, 4, 100, 101, 102, 103, 104, 105, 200};
        int[] actual = new int[9];
        for (int i = 0; i < 9; i++) {
            actual[i] = circ.dequeue();
        }
        assertCondition("QUEUE", "wrap-around FIFO integrity after resize", Arrays.toString(expected), Arrays.toString(actual),
                Arrays.equals(expected, actual), "Queue corrupted during circular buffer wrap-around resize");
        assertCondition("QUEUE", "size 0 after draining queue", "0", String.valueOf(circ.size()), circ.size() == 0, "size != 0 after drain");
        assertCondition("QUEUE", "isEmpty after drain", "true", String.valueOf(circ.isEmpty()), circ.isEmpty(), "isEmpty false after drain");

        // Display test
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        circ.display();
        q2.enqueue(55);
        q2.display();
        System.setOut(oldOut);
        assertCondition("QUEUE", "display executes", "true", "true", true, "");
    }

    // -------------------------------------------------------------
    // MODULE 7: GRAPH
    // -------------------------------------------------------------
    private static void testGraph() {
        System.out.println("\n--- Testing Graph ---");

        Graph g = new Graph();
        assertCondition("GRAPH", "empty vertices", "true", String.valueOf(g.getVertices().isEmpty()), g.getVertices().isEmpty(), "Vertices not empty");
        assertCondition("GRAPH", "empty containsVertex(1)", "false", String.valueOf(g.containsVertex(1)), !g.containsVertex(1), "Found vertex in empty graph");
        assertCondition("GRAPH", "empty containsEdge(1,2)", "false", String.valueOf(g.containsEdge(1, 2)), !g.containsEdge(1, 2), "Found edge in empty graph");
        assertCondition("GRAPH", "empty bfs(1)", "[]", g.bfs(1).toString(), g.bfs(1).isEmpty(), "BFS not empty on empty graph");
        assertCondition("GRAPH", "empty dfs(1)", "[]", g.dfs(1).toString(), g.dfs(1).isEmpty(), "DFS not empty on empty graph");

        // Display on empty graph
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        g.display();
        System.setOut(oldOut);
        assertCondition("GRAPH", "display empty graph", "true", "true", true, "");

        // Add vertices
        boolean added1 = g.addVertex(1);
        assertCondition("GRAPH", "addVertex(1)", "true", String.valueOf(added1), added1, "addVertex(1) failed");
        boolean dup1 = g.addVertex(1);
        assertCondition("GRAPH", "addVertex duplicate 1", "false", String.valueOf(dup1), !dup1, "addVertex duplicate succeeded");

        // Auto add vertices on edge
        boolean added23 = g.addEdge(2, 3);
        assertCondition("GRAPH", "addEdge auto-adds vertices", "true", String.valueOf(added23),
                added23 && g.containsVertex(2) && g.containsVertex(3), "addEdge failed to auto-add vertices");
        assertCondition("GRAPH", "undirected containsEdge", "true", String.valueOf(g.containsEdge(2, 3) && g.containsEdge(3, 2)),
                g.containsEdge(2, 3) && g.containsEdge(3, 2), "Edge is not undirected");

        // Reject duplicate and reverse duplicate edges
        boolean dupEdge = g.addEdge(2, 3);
        assertCondition("GRAPH", "reject duplicate edge", "false", String.valueOf(dupEdge), !dupEdge, "Duplicate edge accepted");
        boolean revEdge = g.addEdge(3, 2);
        assertCondition("GRAPH", "reject reverse duplicate edge", "false", String.valueOf(revEdge), !revEdge, "Reverse duplicate edge accepted");

        // Self-loop
        boolean addedSelf = g.addEdge(5, 5);
        assertCondition("GRAPH", "add self loop", "true", String.valueOf(addedSelf), addedSelf, "Self-loop failed");
        boolean dupSelf = g.addEdge(5, 5);
        assertCondition("GRAPH", "reject duplicate self loop", "false", String.valueOf(dupSelf), !dupSelf, "Duplicate self loop accepted");
        assertCondition("GRAPH", "contains self loop", "true", String.valueOf(g.containsEdge(5, 5)), g.containsEdge(5, 5), "containsEdge self loop returned false");

        // Invalid traversal start
        assertCondition("GRAPH", "bfs invalid start vertex", "[]", g.bfs(999).toString(), g.bfs(999).isEmpty(), "BFS on missing vertex did not return empty list");
        assertCondition("GRAPH", "dfs invalid start vertex", "[]", g.dfs(999).toString(), g.dfs(999).isEmpty(), "DFS on missing vertex did not return empty list");

        // Sample Graph test
        // 1-2, 1-3, 2-4, 3-4, 4-5
        Graph sample = new Graph();
        sample.addEdge(1, 2);
        sample.addEdge(1, 3);
        sample.addEdge(2, 4);
        sample.addEdge(3, 4);
        sample.addEdge(4, 5);
        sample.addVertex(6); // disconnected

        List<Integer> expectedBfs = List.of(1, 2, 3, 4, 5);
        List<Integer> expectedDfs = List.of(1, 2, 4, 3, 5);

        List<Integer> actualBfs = sample.bfs(1);
        List<Integer> actualDfs = sample.dfs(1);
        assertCondition("GRAPH", "sample graph BFS(1)", expectedBfs.toString(), actualBfs.toString(),
                expectedBfs.equals(actualBfs), "BFS visit order mismatch");
        assertCondition("GRAPH", "sample graph DFS(1)", expectedDfs.toString(), actualDfs.toString(),
                expectedDfs.equals(actualDfs), "DFS visit order mismatch");

        // Disconnected component
        List<Integer> bfs6 = sample.bfs(6);
        List<Integer> dfs6 = sample.dfs(6);
        assertCondition("GRAPH", "disconnected BFS(6)", "[6]", bfs6.toString(), List.of(6).equals(bfs6), "BFS(6) failed");
        assertCondition("GRAPH", "disconnected DFS(6)", "[6]", dfs6.toString(), List.of(6).equals(dfs6), "DFS(6) failed");
        assertCondition("GRAPH", "component 1 excludes 6", "false", String.valueOf(actualBfs.contains(6)),
                !actualBfs.contains(6) && !actualDfs.contains(6), "Traversal leaked into disconnected vertex");

        // Cycle handling and self-loop in traversal
        sample.addEdge(4, 4);
        assertCondition("GRAPH", "self-loop does not alter traversal", "true", "true",
                sample.bfs(1).equals(expectedBfs) && sample.dfs(1).equals(expectedDfs), "Self loop broke traversal");
    }

    // -------------------------------------------------------------
    // MODULE 8: SEARCHING
    // -------------------------------------------------------------
    private static void testSearching() {
        System.out.println("\n--- Testing Searching ---");

        // Null checks
        boolean caughtLinearNull = false;
        try { SearchAlgorithms.linearSearch(null, 10); } catch (IllegalArgumentException e) { caughtLinearNull = true; }
        assertCondition("SEARCHING", "linearSearch null throws", "IllegalArgumentException", caughtLinearNull ? "caught" : "not caught", caughtLinearNull, "linearSearch null did not throw");

        boolean caughtBinaryNull = false;
        try { SearchAlgorithms.binarySearch(null, 10); } catch (IllegalArgumentException e) { caughtBinaryNull = true; }
        assertCondition("SEARCHING", "binarySearch null throws", "IllegalArgumentException", caughtBinaryNull ? "caught" : "not caught", caughtBinaryNull, "binarySearch null did not throw");

        // Empty array
        int[] empty = new int[0];
        SearchResult lEmpty = SearchAlgorithms.linearSearch(empty, 5);
        assertCondition("SEARCHING", "linearSearch empty array index", "-1", String.valueOf(lEmpty.getIndex()), lEmpty.getIndex() == -1, "lEmpty index != -1");
        assertCondition("SEARCHING", "linearSearch empty array comps", "0", String.valueOf(lEmpty.getComparisons()), lEmpty.getComparisons() == 0, "lEmpty comps != 0");
        assertCondition("SEARCHING", "linearSearch empty array isFound", "false", String.valueOf(lEmpty.isFound()), !lEmpty.isFound(), "isFound true on empty");

        SearchResult bEmpty = SearchAlgorithms.binarySearch(empty, 5);
        assertCondition("SEARCHING", "binarySearch empty array index", "-1", String.valueOf(bEmpty.getIndex()), bEmpty.getIndex() == -1, "bEmpty index != -1");
        assertCondition("SEARCHING", "binarySearch empty array comps", "0", String.valueOf(bEmpty.getComparisons()), bEmpty.getComparisons() == 0, "bEmpty comps != 0");

        // Single element array
        int[] single = {42};
        SearchResult lSingleFound = SearchAlgorithms.linearSearch(single, 42);
        assertCondition("SEARCHING", "linearSearch single found", "index=0, comps=1",
                "index=" + lSingleFound.getIndex() + ", comps=" + lSingleFound.getComparisons(),
                lSingleFound.getIndex() == 0 && lSingleFound.getComparisons() == 1, "single linear found failed");

        SearchResult lSingleNotFound = SearchAlgorithms.linearSearch(single, 99);
        assertCondition("SEARCHING", "linearSearch single not found", "index=-1, comps=1",
                "index=" + lSingleNotFound.getIndex() + ", comps=" + lSingleNotFound.getComparisons(),
                lSingleNotFound.getIndex() == -1 && lSingleNotFound.getComparisons() == 1, "single linear not found failed");

        SearchResult bSingleFound = SearchAlgorithms.binarySearch(single, 42);
        assertCondition("SEARCHING", "binarySearch single found", "index=0, comps=1",
                "index=" + bSingleFound.getIndex() + ", comps=" + bSingleFound.getComparisons(),
                bSingleFound.getIndex() == 0 && bSingleFound.getComparisons() == 1, "single binary found failed");

        SearchResult bSingleNotFound = SearchAlgorithms.binarySearch(single, 99);
        assertCondition("SEARCHING", "binarySearch single not found", "index=-1, comps=1",
                "index=" + bSingleNotFound.getIndex() + ", comps=" + bSingleNotFound.getComparisons(),
                bSingleNotFound.getIndex() == -1 && bSingleNotFound.getComparisons() == 1, "single binary not found failed");

        // Multi-element sorted array with negative numbers and -1
        int[] sorted = {-50, -20, -1, 0, 5, 10, 10, 30};
        assertCondition("SEARCHING", "isSorted on sorted array", "true", String.valueOf(SearchAlgorithms.isSorted(sorted)),
                SearchAlgorithms.isSorted(sorted), "isSorted returned false on sorted array");

        // First element
        SearchResult lFirst = SearchAlgorithms.linearSearch(sorted, -50);
        assertCondition("SEARCHING", "linearSearch first element", "idx=0, comps=1", "idx=" + lFirst.getIndex() + ", comps=" + lFirst.getComparisons(),
                lFirst.getIndex() == 0 && lFirst.getComparisons() == 1, "first linear search failed");

        SearchResult bFirst = SearchAlgorithms.binarySearch(sorted, -50);
        assertCondition("SEARCHING", "binarySearch first element", "idx=0", "idx=" + bFirst.getIndex(),
                bFirst.getIndex() == 0 && bFirst.isFound(), "first binary search failed");

        // Last element
        SearchResult lLast = SearchAlgorithms.linearSearch(sorted, 30);
        assertCondition("SEARCHING", "linearSearch last element", "idx=7, comps=8", "idx=" + lLast.getIndex() + ", comps=" + lLast.getComparisons(),
                lLast.getIndex() == 7 && lLast.getComparisons() == 8, "last linear search failed");

        SearchResult bLast = SearchAlgorithms.binarySearch(sorted, 30);
        assertCondition("SEARCHING", "binarySearch last element", "idx=7", "idx=" + bLast.getIndex(),
                bLast.getIndex() == 7 && bLast.isFound(), "last binary search failed");

        // Negative value -1
        SearchResult lNeg1 = SearchAlgorithms.linearSearch(sorted, -1);
        assertCondition("SEARCHING", "linearSearch -1", "idx=2, comps=3", "idx=" + lNeg1.getIndex() + ", comps=" + lNeg1.getComparisons(),
                lNeg1.getIndex() == 2 && lNeg1.getComparisons() == 3, "linear search -1 failed");

        SearchResult bNeg1 = SearchAlgorithms.binarySearch(sorted, -1);
        assertCondition("SEARCHING", "binarySearch -1", "idx=2", "idx=" + bNeg1.getIndex(),
                bNeg1.getIndex() == 2 && bNeg1.isFound(), "binary search -1 failed");

        // Duplicate value (10 is at index 5 and 6)
        SearchResult lDup = SearchAlgorithms.linearSearch(sorted, 10);
        assertCondition("SEARCHING", "linearSearch duplicate returns first", "idx=5, comps=6", "idx=" + lDup.getIndex() + ", comps=" + lDup.getComparisons(),
                lDup.getIndex() == 5 && lDup.getComparisons() == 6, "linear search duplicate mismatch");

        SearchResult bDup = SearchAlgorithms.binarySearch(sorted, 10);
        assertCondition("SEARCHING", "binarySearch duplicate finds valid index", "true (idx 5 or 6)", "idx=" + bDup.getIndex(),
                (bDup.getIndex() == 5 || bDup.getIndex() == 6) && bDup.isFound(), "binary search duplicate failed");

        // Absent values (smaller than min, greater than max, between elements)
        SearchResult bLess = SearchAlgorithms.binarySearch(sorted, -100);
        assertCondition("SEARCHING", "binarySearch absent < min", "-1", String.valueOf(bLess.getIndex()), bLess.getIndex() == -1, "Found element < min");

        SearchResult bGreater = SearchAlgorithms.binarySearch(sorted, 100);
        assertCondition("SEARCHING", "binarySearch absent > max", "-1", String.valueOf(bGreater.getIndex()), bGreater.getIndex() == -1, "Found element > max");

        SearchResult bMid = SearchAlgorithms.binarySearch(sorted, 7);
        assertCondition("SEARCHING", "binarySearch absent middle", "-1", String.valueOf(bMid.getIndex()), bMid.getIndex() == -1, "Found absent middle element");

        // Unsorted array
        int[] unsorted = {50, 10, -5, 20, 0};
        assertCondition("SEARCHING", "isSorted on unsorted array", "false", String.valueOf(SearchAlgorithms.isSorted(unsorted)),
                !SearchAlgorithms.isSorted(unsorted), "isSorted returned true on unsorted array");

        // Linear search works on unsorted
        SearchResult lUnsorted = SearchAlgorithms.linearSearch(unsorted, -5);
        assertCondition("SEARCHING", "linearSearch on unsorted array", "idx=2, comps=3", "idx=" + lUnsorted.getIndex() + ", comps=" + lUnsorted.getComparisons(),
                lUnsorted.getIndex() == 2 && lUnsorted.getComparisons() == 3, "linear search unsorted failed");
    }

    // -------------------------------------------------------------
    // MODULE 9: PERFORMANCE ANALYSIS
    // -------------------------------------------------------------
    private static void testPerformance() {
        System.out.println("\n--- Testing Performance Analysis ---");

        // generateSortedDataset
        boolean caughtNegDataset = false;
        try { PerformanceAnalyzer.generateSortedDataset(-5); } catch (IllegalArgumentException e) { caughtNegDataset = true; }
        assertCondition("PERFORMANCE", "generateSortedDataset(-5) throws", "IllegalArgumentException", caughtNegDataset ? "caught" : "not caught",
                caughtNegDataset, "Negative dataset size did not throw");

        int[] d0 = PerformanceAnalyzer.generateSortedDataset(0);
        assertCondition("PERFORMANCE", "generateSortedDataset(0) length", "0", String.valueOf(d0.length), d0.length == 0, "length != 0");

        int[] d10 = PerformanceAnalyzer.generateSortedDataset(10);
        assertCondition("PERFORMANCE", "generateSortedDataset(10)", "[0..9]", Arrays.toString(d10),
                d10.length == 10 && d10[0] == 0 && d10[9] == 9, "d10 content mismatch");

        // PerformanceResult on present and absent targets
        PerformanceResult resLinPresent = PerformanceAnalyzer.analyzeLinearSearch(d10, 5);
        assertCondition("PERFORMANCE", "linear present target index", "5", String.valueOf(resLinPresent.getResultIndex()),
                resLinPresent.getResultIndex() == 5 && resLinPresent.isFound(), "Result index mismatch");
        assertCondition("PERFORMANCE", "linear present target comparisons", "6", String.valueOf(resLinPresent.getComparisons()),
                resLinPresent.getComparisons() == 6, "Comparisons count mismatch for index 5");

        PerformanceResult resLinAbsent = PerformanceAnalyzer.analyzeLinearSearch(d10, 999);
        assertCondition("PERFORMANCE", "linear absent target index", "-1", String.valueOf(resLinAbsent.getResultIndex()),
                resLinAbsent.getResultIndex() == -1 && !resLinAbsent.isFound(), "Found absent target");
        assertCondition("PERFORMANCE", "linear absent target comparisons", "10", String.valueOf(resLinAbsent.getComparisons()),
                resLinAbsent.getComparisons() == 10, "Comparisons count != dataset size on absent target");

        PerformanceResult resBinPresent = PerformanceAnalyzer.analyzeBinarySearch(d10, 5);
        assertCondition("PERFORMANCE", "binary present target index", "5", String.valueOf(resBinPresent.getResultIndex()),
                resBinPresent.getResultIndex() == 5 && resBinPresent.isFound(), "Binary result index mismatch");

        PerformanceResult resBinAbsent = PerformanceAnalyzer.analyzeBinarySearch(d10, 999);
        assertCondition("PERFORMANCE", "binary absent target index", "-1", String.valueOf(resBinAbsent.getResultIndex()),
                resBinAbsent.getResultIndex() == -1 && !resBinAbsent.isFound(), "Binary found absent target");

        // Multi-size benchmarks and suites (suppress console output during execution)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        try {
            PerformanceAnalyzer.compareAndDisplay(d10, 5);
            PerformanceAnalyzer.runStandardBenchmarkSuite();
            PerformanceAnalyzer.runMultiSizeBenchmark(new int[] {10, 50, 100});
            PerformanceAnalyzer.printComplexitySummary();
        } finally {
            System.setOut(oldOut);
        }
        assertCondition("PERFORMANCE", "benchmark suite execution without crash", "true", "true", true, "");
    }

    // -------------------------------------------------------------
    // MODULE 10: MAIN APPLICATION INTEGRATION
    // -------------------------------------------------------------
    private static void testMainIntegration() {
        System.out.println("\n--- Testing Main Application Integration via CLI ---");

        StringBuilder inputScript = new StringBuilder();
        // invalid inputs on main menu
        inputScript.append("invalid_text\n");
        inputScript.append("99\n\n"); // invalid choice followed by enter for pause

        // Array menu
        inputScript.append("1\n"); // enter array menu
        inputScript.append("6\n"); // get empty
        inputScript.append("2\n10\n"); // delete 10
        inputScript.append("3\n10\n"); // search 10
        inputScript.append("4\n"); // display
        inputScript.append("5\n"); // size
        inputScript.append("1\n10\n"); // insert 10
        inputScript.append("1\n-1\n"); // insert -1
        inputScript.append("1\n20\n"); // insert 20
        inputScript.append("4\n"); // display
        inputScript.append("6\n-5\n"); // get invalid index
        inputScript.append("6\n1\n"); // get index 1 (-1)
        inputScript.append("6\n99\n"); // get invalid index 99
        inputScript.append("3\n20\n"); // search 20
        inputScript.append("3\n999\n"); // search 999
        inputScript.append("2\n10\n"); // delete 10
        inputScript.append("7\n"); // clear
        inputScript.append("5\n"); // size
        inputScript.append("99\n"); // invalid submenu choice
        inputScript.append("0\n\n"); // back to main menu, enter for pause

        // Linked List menu
        inputScript.append("2\n");
        inputScript.append("4\n"); // display
        inputScript.append("5\n"); // size
        inputScript.append("2\n10\n"); // delete 10
        inputScript.append("3\n10\n"); // search 10
        inputScript.append("1\n10\n"); // insert 10
        inputScript.append("1\n-1\n"); // insert -1
        inputScript.append("4\n"); // display
        inputScript.append("3\n-1\n"); // search -1
        inputScript.append("2\n-1\n"); // delete -1
        inputScript.append("5\n"); // size
        inputScript.append("0\n\n"); // back

        // Stack menu
        inputScript.append("3\n");
        inputScript.append("2\n"); // pop empty
        inputScript.append("3\n"); // peek empty
        inputScript.append("4\n"); // display empty
        inputScript.append("5\n"); // size
        inputScript.append("1\n-1\n"); // push -1
        inputScript.append("3\n"); // peek
        inputScript.append("1\n50\n"); // push 50
        inputScript.append("4\n"); // display
        inputScript.append("2\n"); // pop
        inputScript.append("2\n"); // pop
        inputScript.append("2\n"); // pop empty
        inputScript.append("0\n\n"); // back

        // Queue menu
        inputScript.append("4\n");
        inputScript.append("2\n"); // dequeue empty
        inputScript.append("3\n"); // peek empty
        inputScript.append("4\n"); // display empty
        inputScript.append("5\n"); // size
        inputScript.append("1\n-1\n"); // enqueue -1
        inputScript.append("3\n"); // peek
        inputScript.append("1\n100\n"); // enqueue 100
        inputScript.append("4\n"); // display
        inputScript.append("2\n"); // dequeue
        inputScript.append("2\n"); // dequeue
        inputScript.append("2\n"); // dequeue empty
        inputScript.append("0\n\n"); // back

        // Graph menu
        inputScript.append("5\n");
        inputScript.append("3\n"); // display empty
        inputScript.append("4\n"); // BFS empty
        inputScript.append("5\n"); // DFS empty
        inputScript.append("6\n"); // list vertices
        inputScript.append("7\n1\n2\n"); // check edge
        inputScript.append("1\n1\n"); // add vertex 1
        inputScript.append("1\n1\n"); // duplicate vertex 1
        inputScript.append("2\n1\n2\n"); // add edge 1 2
        inputScript.append("2\n1\n3\n"); // add edge 1 3
        inputScript.append("2\n2\n4\n"); // add edge 2 4
        inputScript.append("2\n3\n4\n"); // add edge 3 4
        inputScript.append("2\n4\n5\n"); // add edge 4 5
        inputScript.append("2\n1\n2\n"); // duplicate edge
        inputScript.append("3\n"); // display
        inputScript.append("6\n"); // list vertices
        inputScript.append("7\n1\n2\n"); // check edge
        inputScript.append("7\n1\n5\n"); // check missing edge
        inputScript.append("4\n99\n"); // BFS invalid start
        inputScript.append("4\n1\n"); // BFS start 1
        inputScript.append("5\n1\n"); // DFS start 1
        inputScript.append("0\n\n"); // back

        // Searching menu (array is currently empty)
        inputScript.append("6\n");
        inputScript.append("1\n"); // linear search on empty
        inputScript.append("2\n"); // binary search on empty
        inputScript.append("3\n"); // isSorted on empty
        inputScript.append("0\n\n"); // back

        // Populate array with unsorted data to test binary search block
        inputScript.append("1\n"); // array menu
        inputScript.append("1\n20\n");
        inputScript.append("1\n5\n");
        inputScript.append("0\n\n"); // back

        inputScript.append("6\n"); // searching menu
        inputScript.append("3\n"); // check sorted -> not sorted
        inputScript.append("2\n"); // binary search -> blocked!
        inputScript.append("1\n5\n"); // linear search 5 -> found
        inputScript.append("0\n\n"); // back

        // Clear and populate with sorted data
        inputScript.append("1\n"); // array menu
        inputScript.append("7\n"); // clear
        inputScript.append("1\n10\n");
        inputScript.append("1\n20\n");
        inputScript.append("1\n30\n");
        inputScript.append("0\n\n"); // back

        inputScript.append("6\n"); // searching menu
        inputScript.append("3\n"); // check sorted -> sorted
        inputScript.append("2\n20\n"); // binary search 20 -> found
        inputScript.append("2\n999\n"); // binary search 999 -> not found
        inputScript.append("0\n\n"); // back

        // Performance menu
        inputScript.append("7\n");
        inputScript.append("1\n10\n5\n"); // compare on size 10, target 5
        inputScript.append("2\n"); // standard benchmark suite
        inputScript.append("3\n"); // multi size benchmark
        inputScript.append("4\n"); // complexity summary
        inputScript.append("0\n\n"); // back

        // Exit
        inputScript.append("0\n");

        ByteArrayInputStream in = new ByteArrayInputStream(inputScript.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        java.io.InputStream oldIn = System.in;

        boolean crashed = false;
        String crashMsg = "";
        String outputStr = "";
        try {
            System.setIn(in);
            System.setOut(new PrintStream(out));
            Main.main(new String[0]);
        } catch (Throwable t) {
            crashed = true;
            crashMsg = t.toString();
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
            outputStr = out.toString();
        }

        assertCondition("MAIN", "Main.main runs to completion without unhandled exception", "No exception",
                crashed ? crashMsg : "Completed clean", !crashed, "Exception occurred during full CLI flow: " + crashMsg);

        // Verify key messages in output
        assertCondition("MAIN", "Invalid menu input handled", "true",
                String.valueOf(outputStr.contains("Invalid input. Please enter a whole number.")),
                outputStr.contains("Invalid input. Please enter a whole number."), "Invalid text input message missing");

        assertCondition("MAIN", "Binary search blocked on unsorted array", "true",
                String.valueOf(outputStr.contains("Binary search requires the array to be sorted")),
                outputStr.contains("Binary search requires the array to be sorted"), "Unsorted binary search blocking message missing");

        assertCondition("MAIN", "Exit message present", "true",
                String.valueOf(outputStr.contains("Thank you for using CIT300")),
                outputStr.contains("Thank you for using CIT300"), "Exit message missing");
    }
}
