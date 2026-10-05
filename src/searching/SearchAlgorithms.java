package searching;

/**
 * SearchAlgorithms provides static implementations of fundamental search algorithms:
 * 1. Linear Search (sequential scan)
 * 2. Binary Search (iterative divide-and-conquer on sorted data)
 * 
 * Each algorithm tracks exact element comparison counts to facilitate complexity
 * analysis and performance benchmarking.
 * 
 * Theoretical Complexities:
 * ------------------------------------------------------------
 * Algorithm      | Best Case | Average Case | Worst Case | Space
 * ------------------------------------------------------------
 * Linear Search  | O(1)      | O(n)         | O(n)       | O(1)
 * Binary Search  | O(1)      | O(log n)     | O(log n)   | O(1)
 * ------------------------------------------------------------
 * 
 * Author: Ayyash Mumthaas (23DA2-1033)
 */
public class SearchAlgorithms {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private SearchAlgorithms() {
    }

    /**
     * Performs a sequential linear search across the given array.
     * Starts at index 0 and inspects each element one-by-one until either the
     * target is found or the end of the array is reached.
     * 
     * In the presence of duplicates, returns the first matching occurrence.
     * 
     * Comparison Counting:
     * - Each inspected element counts as exactly 1 comparison.
     * 
     * Complexities:
     * - Best Case: O(1) (target located at index 0)
     * - Average Case: O(n) (target located midway on average)
     * - Worst Case: O(n) (target at last index or absent)
     * - Auxiliary Space: O(1)
     * 
     * @param array  The array to search through.
     * @param target The integer value to locate.
     * @return SearchResult containing the found index (-1 if absent) and total comparisons.
     * @throws IllegalArgumentException if the array parameter is null.
     */
    public static SearchResult linearSearch(int[] array, int target) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null for linear search.");
        }

        if (array.length == 0) {
            return new SearchResult(-1, 0);
        }

        int comparisons = 0;
        for (int i = 0; i < array.length; i++) {
            comparisons++;
            if (array[i] == target) {
                return new SearchResult(i, comparisons);
            }
        }

        return new SearchResult(-1, comparisons);
    }

    /**
     * Performs an iterative binary search on an array that MUST be sorted in
     * ascending order.
     * 
     * Precondition:
     * The input array must already be sorted in non-decreasing order.
     * If an unsorted array is provided, the algorithm will not function reliably
     * and may fail to locate elements that are present. The caller is responsible
     * for ensuring the data is sorted (verify with {@link #isSorted(int[])} if needed).
     * 
     * In the presence of duplicates, returns the index hit by the first matching 'mid'.
     * 
     * Comparison Counting:
     * - Each probe of the middle element (target vs sortedArray[mid]) counts as 1 comparison.
     * 
     * Complexities:
     * - Best Case: O(1) (target is located at the initial middle position)
     * - Average Case: O(log n) (search space is halved on each comparison)
     * - Worst Case: O(log n) (repeatedly bisects down to a single element or not found)
     * - Auxiliary Space: O(1) (iterative implementation using constant pointers)
     * 
     * @param sortedArray An array sorted in ascending order.
     * @param target      The integer value to locate.
     * @return SearchResult containing the found index (-1 if absent) and total comparisons.
     * @throws IllegalArgumentException if the sortedArray parameter is null.
     */
    public static SearchResult binarySearch(int[] sortedArray, int target) {
        if (sortedArray == null) {
            throw new IllegalArgumentException("Array cannot be null for binary search.");
        }

        if (sortedArray.length == 0) {
            return new SearchResult(-1, 0);
        }

        int low = 0;
        int high = sortedArray.length - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            // Avoid integer overflow compared to (low + high) / 2
            int mid = low + (high - low) / 2;

            if (sortedArray[mid] == target) {
                return new SearchResult(mid, comparisons);
            } else if (sortedArray[mid] < target) {
                low = mid + 1; // Discard left half
            } else {
                high = mid - 1; // Discard right half
            }
        }

        return new SearchResult(-1, comparisons);
    }

    /**
     * Checks whether an array of integers is sorted in ascending (non-decreasing) order.
     * 
     * Time Complexity: O(n)
     * Auxiliary Space: O(1)
     * 
     * @param array The array to inspect.
     * @return true if the array is sorted in ascending order or has length <= 1, false otherwise.
     * @throws IllegalArgumentException if the array parameter is null.
     */
    public static boolean isSorted(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
