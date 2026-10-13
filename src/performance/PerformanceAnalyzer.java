package performance;

import searching.SearchAlgorithms;
import searching.SearchResult;

/**
 * PerformanceAnalyzer provides empirical benchmarking and theoretical complexity
 * comparison between Linear Search and Binary Search on equivalent datasets.
 * 
 * Features:
 * - Deterministic dataset generation (avoiding random noise)
 * - Measurement of deterministic metrics (exact element comparisons)
 * - Measurement of empirical metrics (average execution time in nanoseconds via System.nanoTime())
 * - Warm-up iterations to ensure JIT compilation stability
 * - Multi-size benchmark tables across diverse dataset scales
 * - Clear separation between theoretical Big-O complexity and experimental timings
 * 
 * Author: Ayyash Mumthaas (23DA2-1033)
 */
public class PerformanceAnalyzer {

    // Number of iterations for JIT warm-up and timed trials
    private static final int DEFAULT_WARMUP_TRIALS = 300;
    private static final int DEFAULT_BENCHMARK_TRIALS = 1000;
    private static final int LARGE_DATASET_BENCHMARK_TRIALS = 200;

    /**
     * Private constructor to prevent direct instantiation of utility class.
     */
    private PerformanceAnalyzer() {
    }

    /**
     * Generates a deterministic sorted integer array: [0, 1, 2, ..., size - 1].
     * Deterministic data ensures reproducible comparisons across algorithms.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * 
     * @param size Number of elements to generate (must be >= 0).
     * @return A newly allocated sorted integer array.
     * @throws IllegalArgumentException if size is negative.
     */
    public static int[] generateSortedDataset(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Dataset size cannot be negative.");
        }
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = i;
        }
        return data;
    }

    /**
     * Analyzes Linear Search performance on the provided dataset and target.
     * Reuses {@link SearchAlgorithms#linearSearch(int[], int)}.
     * 
     * @param dataset Array to search (will not be mutated).
     * @param target  Value to search for.
     * @return PerformanceResult containing comparison count, average time, and complexity.
     */
    public static PerformanceResult analyzeLinearSearch(int[] dataset, int target) {
        int trials = dataset.length >= 10000 ? LARGE_DATASET_BENCHMARK_TRIALS : DEFAULT_BENCHMARK_TRIALS;
        int warmup = dataset.length >= 10000 ? 50 : DEFAULT_WARMUP_TRIALS;

        // Warm-up phase to trigger JIT optimization
        int dummy = 0;
        for (int i = 0; i < warmup; i++) {
            dummy += SearchAlgorithms.linearSearch(dataset, target).getIndex();
        }

        // Timed benchmark trials
        SearchResult result = null;
        long start = System.nanoTime();
        for (int i = 0; i < trials; i++) {
            result = SearchAlgorithms.linearSearch(dataset, target);
            dummy += result.getIndex(); // Prevent JIT dead code elimination
        }
        long elapsed = System.nanoTime() - start;
        long avgTimeNs = trials > 0 ? elapsed / trials : 0;

        // Prevent dummy variable from being optimized away
        if (dummy == Integer.MIN_VALUE) {
            System.out.print("");
        }

        int index = result != null ? result.getIndex() : -1;
        int comparisons = result != null ? result.getComparisons() : 0;

        return new PerformanceResult("Linear Search", dataset.length, target,
                index, comparisons, avgTimeNs, "O(n)");
    }

    /**
     * Analyzes Binary Search performance on the provided sorted dataset and target.
     * Reuses {@link SearchAlgorithms#binarySearch(int[], int)}.
     * 
     * @param sortedDataset Sorted array to search (will not be mutated).
     * @param target        Value to search for.
     * @return PerformanceResult containing comparison count, average time, and complexity.
     */
    public static PerformanceResult analyzeBinarySearch(int[] sortedDataset, int target) {
        int trials = sortedDataset.length >= 10000 ? LARGE_DATASET_BENCHMARK_TRIALS : DEFAULT_BENCHMARK_TRIALS;
        int warmup = sortedDataset.length >= 10000 ? 50 : DEFAULT_WARMUP_TRIALS;

        // Warm-up phase to trigger JIT optimization
        int dummy = 0;
        for (int i = 0; i < warmup; i++) {
            dummy += SearchAlgorithms.binarySearch(sortedDataset, target).getIndex();
        }

        // Timed benchmark trials
        SearchResult result = null;
        long start = System.nanoTime();
        for (int i = 0; i < trials; i++) {
            result = SearchAlgorithms.binarySearch(sortedDataset, target);
            dummy += result.getIndex(); // Prevent JIT dead code elimination
        }
        long elapsed = System.nanoTime() - start;
        long avgTimeNs = trials > 0 ? elapsed / trials : 0;

        // Prevent dummy variable from being optimized away
        if (dummy == Integer.MIN_VALUE) {
            System.out.print("");
        }

        int index = result != null ? result.getIndex() : -1;
        int comparisons = result != null ? result.getComparisons() : 0;

        return new PerformanceResult("Binary Search", sortedDataset.length, target,
                index, comparisons, avgTimeNs, "O(log n)");
    }

    /**
     * Compares Linear Search and Binary Search on the same dataset and target,
     * printing formatted results to the console.
     * 
     * @param dataset Array to test.
     * @param target  Value to locate.
     */
    public static void compareAndDisplay(int[] dataset, int target) {
        PerformanceResult linear = analyzeLinearSearch(dataset, target);
        PerformanceResult binary = analyzeBinarySearch(dataset, target);

        System.out.println("==================================================");
        System.out.println("         SEARCH PERFORMANCE COMPARISON");
        System.out.println("==================================================");
        System.out.println("Dataset Size : " + dataset.length);
        System.out.println("Target Value : " + target);
        System.out.println();
        System.out.println("Linear Search");
        System.out.println("-------------");
        System.out.println("Result Index : " + linear.getResultIndex());
        System.out.println("Comparisons  : " + linear.getComparisons());
        System.out.println("Time         : " + linear.getExecutionTimeNs() + " ns");
        System.out.println("Complexity   : " + linear.getTheoreticalComplexity());
        System.out.println();
        System.out.println("Binary Search");
        System.out.println("-------------");
        System.out.println("Result Index : " + binary.getResultIndex());
        System.out.println("Comparisons  : " + binary.getComparisons());
        System.out.println("Time         : " + binary.getExecutionTimeNs() + " ns");
        System.out.println("Complexity   : " + binary.getTheoreticalComplexity());
        System.out.println("==================================================");
    }

    /**
     * Runs multi-size benchmarks across standard dataset sizes (10, 100, 1000, 10000, 100000)
     * using a target near the end of each dataset (size - 2) to demonstrate scale.
     */
    public static void runStandardBenchmarkSuite() {
        int[] standardSizes = {10, 100, 1000, 10000, 100000};
        runMultiSizeBenchmark(standardSizes);
    }

    /**
     * Executes benchmarks across a specified array of dataset sizes and prints
     * a clean, formatted comparison table.
     * 
     * @param sizes Array of dataset sizes to evaluate.
     */
    public static void runMultiSizeBenchmark(int[] sizes) {
        System.out.println("==========================================================================================");
        System.out.println("                           MULTI-SIZE SEARCH PERFORMANCE TABLE");
        System.out.println("==========================================================================================");
        System.out.printf("| %-12s | %-12s | %-14s | %-14s | %-12s | %-12s |\n",
                "Dataset Size", "Target", "Linear Comps", "Binary Comps", "Linear Time", "Binary Time");
        System.out.println("+--------------+--------------+----------------+----------------+--------------+--------------+");

        for (int size : sizes) {
            if (size <= 0) continue;
            int[] data = generateSortedDataset(size);
            int target = size > 2 ? size - 2 : size - 1;

            PerformanceResult linear = analyzeLinearSearch(data, target);
            PerformanceResult binary = analyzeBinarySearch(data, target);

            System.out.printf("| %-12d | %-12d | %-14d | %-14d | %-9d ns | %-9d ns |\n",
                    size, target, linear.getComparisons(), binary.getComparisons(),
                    linear.getExecutionTimeNs(), binary.getExecutionTimeNs());
        }

        System.out.println("==========================================================================================");
        System.out.println("Note: Comparisons are deterministic. Execution times reflect JVM averages on this machine.");
    }

    /**
     * Displays a comprehensive theoretical complexity summary and explains
     * the distinction between theoretical Big-O complexity and experimental timings.
     */
    public static void printComplexitySummary() {
        System.out.println("================================================================================");
        System.out.println("                         ALGORITHM COMPLEXITY SUMMARY");
        System.out.println("================================================================================");
        System.out.println("1. Linear Search (Sequential Scan)");
        System.out.println("   - Best Case       : O(1)      (Target found at index 0)");
        System.out.println("   - Average Case    : O(n)      (Target found midway on average)");
        System.out.println("   - Worst Case      : O(n)      (Target at last position or absent)");
        System.out.println("   - Auxiliary Space : O(1)      (Iterative constant space)");
        System.out.println();
        System.out.println("2. Binary Search (Divide and Conquer on Sorted Data)");
        System.out.println("   - Best Case       : O(1)      (Target found at middle on first probe)");
        System.out.println("   - Average Case    : O(log n)  (Search space halved each step)");
        System.out.println("   - Worst Case      : O(log n)  (Bisected down to single element)");
        System.out.println("   - Auxiliary Space : O(1)      (Iterative constant space, no recursion stack)");
        System.out.println();
        System.out.println("3. Theoretical Complexity vs. Experimental Execution Time");
        System.out.println("   - Theoretical Big-O characterizes how resource growth scales with input size (n).");
        System.out.println("   - Experimental nanoseconds measure physical CPU clock time, which fluctuates");
        System.out.println("     due to JVM JIT compilation, CPU frequency scaling, OS context switching,");
        System.out.println("     and cache effects.");
        System.out.println("   - Therefore, element comparisons provide the rigorous deterministic metric,");
        System.out.println("     while execution times illustrate practical real-world wall-clock behavior.");
        System.out.println("================================================================================");
    }
}
