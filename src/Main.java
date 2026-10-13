import data.ArrayManager;
import data.LinkedList;
import data.Stack;
import data.Queue;
import graph.Graph;
import searching.SearchAlgorithms;
import searching.SearchResult;
import performance.PerformanceAnalyzer;

import java.util.List;
import java.util.Scanner;

/**
 * CIT300 Data Structure & Graph Analyzer
 * Main Application Orchestrator
 *
 * Group Members & Responsibilities:
 * - M.M.M. Ayyash (23DA2-1033): Array, Searching, Performance, Integration
 * - M.A.J. Hafsa  (23DA2-1058): Stack, Queue
 * - M.T. Aathifa  (23DA2-0916): Linked List
 * - M.F.F. Aaysha (23DA2-0834): Graph (BFS, DFS)
 */
public class Main {

    private static final Scanner INPUT = new Scanner(System.in);

    private static final ArrayManager ARRAY = new ArrayManager();
    private static final LinkedList LINKED_LIST = new LinkedList();
    private static final Stack STACK = new Stack();
    private static final Queue QUEUE = new Queue();
    private static final Graph GRAPH = new Graph();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printHeader();
            System.out.println("1. Array Operations");
            System.out.println("2. Linked List Operations");
            System.out.println("3. Stack Operations");
            System.out.println("4. Queue Operations");
            System.out.println("5. Graph Operations (BFS / DFS)");
            System.out.println("6. Searching Algorithms");
            System.out.println("7. Performance Analysis");
            System.out.println("0. Exit");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> arrayMenu();
                case 2 -> linkedListMenu();
                case 3 -> stackMenu();
                case 4 -> queueMenu();
                case 5 -> graphMenu();
                case 6 -> searchingMenu();
                case 7 -> performanceMenu();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using CIT300 Data Structure & Graph Analyzer.");
                }
                default -> System.out.println("Invalid option. Please choose from the menu.");
            }

            if (running) {
                pause();
            }
        }

        INPUT.close();
    }

    private static void printHeader() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("       CIT300 DATA STRUCTURE & GRAPH ANALYZER");
        System.out.println("==================================================");
    }

    private static void printSubmenu(String title) {
        System.out.println();
        System.out.println("--------------- " + title + " ---------------");
        System.out.println("0. Back to Main Menu");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = INPUT.nextLine().trim();

            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Please enter a number greater than zero.");
        }
    }

    private static void pause() {
        System.out.println();
        System.out.print("Press Enter to continue...");
        INPUT.nextLine();
    }

    private static void arrayMenu() {
        boolean back = false;

        while (!back) {
            printSubmenu("ARRAY OPERATIONS");
            System.out.println("1. Insert value");
            System.out.println("2. Delete value");
            System.out.println("3. Search value");
            System.out.println("4. Display array");
            System.out.println("5. Show size and capacity");
            System.out.println("6. Access value by index");
            System.out.println("7. Clear array");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0 -> back = true;
                case 1 -> {
                    int value = readInt("Enter value to insert: ");
                    ARRAY.insert(value);
                    System.out.println("Value inserted successfully.");
                }
                case 2 -> {
                    int value = readInt("Enter value to delete: ");
                    if (ARRAY.delete(value)) {
                        System.out.println("Value deleted successfully.");
                    } else {
                        System.out.println("Value not found.");
                    }
                }
                case 3 -> {
                    int value = readInt("Enter value to search: ");
                    int index = ARRAY.search(value);
                    System.out.println(index >= 0
                            ? "Value found at index " + index
                            : "Value not found.");
                    System.out.println("Comparisons: " + ARRAY.getLastSearchComparisons());
                }
                case 4 -> ARRAY.display();
                case 5 -> {
                    System.out.println("Size: " + ARRAY.size());
                    System.out.println("Capacity: " + ARRAY.capacity());
                    System.out.println("Empty: " + ARRAY.isEmpty());
                }
                case 6 -> {
                    if (ARRAY.isEmpty()) {
                        System.out.println("The array is empty.");
                    } else {
                        int index = readInt("Enter index (0 to "
                                + (ARRAY.size() - 1) + "): ");
                        if (index >= 0 && index < ARRAY.size()) {
                            System.out.println("Value: " + ARRAY.get(index));
                        } else {
                            System.out.println("Index out of range.");
                        }
                    }
                }
                case 7 -> {
                    ARRAY.clear();
                    System.out.println("Array cleared.");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void linkedListMenu() {
        boolean back = false;

        while (!back) {
            printSubmenu("LINKED LIST OPERATIONS");
            System.out.println("1. Insert value");
            System.out.println("2. Delete value");
            System.out.println("3. Search value");
            System.out.println("4. Display list");
            System.out.println("5. Show size");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0 -> back = true;
                case 1 -> {
                    LINKED_LIST.insert(readInt("Enter value to insert: "));
                    System.out.println("Value inserted. This implementation inserts at the head.");
                }
                case 2 -> {
                    int value = readInt("Enter value to delete: ");
                    System.out.println(LINKED_LIST.delete(value)
                            ? "Value deleted successfully."
                            : "Value not found.");
                }
                case 3 -> {
                    int value = readInt("Enter value to search: ");
                    System.out.println(LINKED_LIST.search(value)
                            ? "Value found."
                            : "Value not found.");
                }
                case 4 -> LINKED_LIST.display();
                case 5 -> {
                    System.out.println("Size: " + LINKED_LIST.size());
                    System.out.println("Empty: " + LINKED_LIST.isEmpty());
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void stackMenu() {
        boolean back = false;

        while (!back) {
            printSubmenu("STACK OPERATIONS (LIFO)");
            System.out.println("1. Push");
            System.out.println("2. Pop");
            System.out.println("3. Peek");
            System.out.println("4. Display stack");
            System.out.println("5. Show size");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0 -> back = true;
                case 1 -> {
                    STACK.push(readInt("Enter value to push: "));
                    System.out.println("Value pushed onto stack.");
                }
                case 2 -> {
                    if (STACK.isEmpty()) {
                        System.out.println("Cannot pop: stack is empty.");
                    } else {
                        System.out.println("Popped value: " + STACK.pop());
                    }
                }
                case 3 -> {
                    if (STACK.isEmpty()) {
                        System.out.println("Cannot peek: stack is empty.");
                    } else {
                        System.out.println("Top value: " + STACK.peek());
                    }
                }
                case 4 -> STACK.display();
                case 5 -> {
                    System.out.println("Size: " + STACK.size());
                    System.out.println("Empty: " + STACK.isEmpty());
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void queueMenu() {
        boolean back = false;

        while (!back) {
            printSubmenu("QUEUE OPERATIONS (FIFO)");
            System.out.println("1. Enqueue");
            System.out.println("2. Dequeue");
            System.out.println("3. Peek at front");
            System.out.println("4. Display queue");
            System.out.println("5. Show size");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0 -> back = true;
                case 1 -> {
                    QUEUE.enqueue(readInt("Enter value to enqueue: "));
                    System.out.println("Value added to queue.");
                }
                case 2 -> {
                    if (QUEUE.isEmpty()) {
                        System.out.println("Cannot dequeue: queue is empty.");
                    } else {
                        System.out.println("Dequeued value: " + QUEUE.dequeue());
                    }
                }
                case 3 -> {
                    if (QUEUE.isEmpty()) {
                        System.out.println("Cannot peek: queue is empty.");
                    } else {
                        System.out.println("Front value: " + QUEUE.peek());
                    }
                }
                case 4 -> QUEUE.display();
                case 5 -> {
                    System.out.println("Size: " + QUEUE.size());
                    System.out.println("Empty: " + QUEUE.isEmpty());
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void graphMenu() {
        boolean back = false;

        while (!back) {
            printSubmenu("GRAPH OPERATIONS");
            System.out.println("1. Add vertex");
            System.out.println("2. Add undirected edge");
            System.out.println("3. Display adjacency list");
            System.out.println("4. Breadth-First Search (BFS)");
            System.out.println("5. Depth-First Search (DFS)");
            System.out.println("6. List vertices");
            System.out.println("7. Check edge");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0 -> back = true;
                case 1 -> {
                    int vertex = readInt("Enter vertex value: ");
                    System.out.println(GRAPH.addVertex(vertex)
                            ? "Vertex added."
                            : "Vertex already exists.");
                }
                case 2 -> {
                    int source = readInt("Enter source vertex: ");
                    int destination = readInt("Enter destination vertex: ");
                    System.out.println(GRAPH.addEdge(source, destination)
                            ? "Undirected edge added."
                            : "Edge already exists.");
                }
                case 3 -> GRAPH.display();
                case 4 -> runGraphTraversal(true);
                case 5 -> runGraphTraversal(false);
                case 6 -> System.out.println("Vertices: " + GRAPH.getVertices());
                case 7 -> {
                    int source = readInt("Enter source vertex: ");
                    int destination = readInt("Enter destination vertex: ");
                    System.out.println(GRAPH.containsEdge(source, destination)
                            ? "Edge exists."
                            : "Edge does not exist.");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void runGraphTraversal(boolean breadthFirst) {
        if (GRAPH.getVertices().isEmpty()) {
            System.out.println("The graph is empty. Add vertices or edges first.");
            return;
        }

        int start = readInt("Enter starting vertex: ");

        if (!GRAPH.containsVertex(start)) {
            System.out.println("Starting vertex does not exist.");
            return;
        }

        List<Integer> order = breadthFirst ? GRAPH.bfs(start) : GRAPH.dfs(start);
        System.out.println((breadthFirst ? "BFS" : "DFS") + " visit order: " + order);
        System.out.println("Note: traversal visits only the connected component reachable from the start vertex.");
    }

    private static void searchingMenu() {
        boolean back = false;

        while (!back) {
            printSubmenu("SEARCHING ALGORITHMS");
            System.out.println("1. Linear search on current array");
            System.out.println("2. Binary search on current array");
            System.out.println("3. Check whether current array is sorted");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0 -> back = true;
                case 1 -> performSearch(false);
                case 2 -> performSearch(true);
                case 3 -> System.out.println(SearchAlgorithms.isSorted(ARRAY.toArray())
                        ? "The array is sorted in ascending order."
                        : "The array is not sorted in ascending order.");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void performSearch(boolean binary) {
        int[] values = ARRAY.toArray();

        if (values.length == 0) {
            System.out.println("The array is empty. Insert values first.");
            return;
        }

        if (binary && !SearchAlgorithms.isSorted(values)) {
            System.out.println("Binary search requires the array to be sorted in ascending order.");
            System.out.println("Use Array Operations to manage the data, or choose linear search.");
            return;
        }

        int target = readInt("Enter target value: ");
        SearchResult result = binary
                ? SearchAlgorithms.binarySearch(values, target)
                : SearchAlgorithms.linearSearch(values, target);

        System.out.println("Algorithm: " + (binary ? "Binary Search" : "Linear Search"));
        System.out.println(result.isFound()
                ? "Target found at index " + result.getIndex()
                : "Target not found.");
        System.out.println("Comparisons: " + result.getComparisons());
    }

    private static void performanceMenu() {
        boolean back = false;

        while (!back) {
            printSubmenu("PERFORMANCE ANALYSIS");
            System.out.println("1. Compare linear and binary search on generated sorted data");
            System.out.println("2. Run standard benchmark suite");
            System.out.println("3. Run benchmark across multiple dataset sizes");
            System.out.println("4. Display complexity summary");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0 -> back = true;
                case 1 -> {
                    int size = readPositiveInt("Enter dataset size: ");
                    int target = readInt("Enter target value: ");
                    int[] dataset = PerformanceAnalyzer.generateSortedDataset(size);
                    PerformanceAnalyzer.compareAndDisplay(dataset, target);
                }
                case 2 -> PerformanceAnalyzer.runStandardBenchmarkSuite();
                case 3 -> PerformanceAnalyzer.runMultiSizeBenchmark(
                        new int[] {100, 1000, 10000});
                case 4 -> PerformanceAnalyzer.printComplexitySummary();
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
