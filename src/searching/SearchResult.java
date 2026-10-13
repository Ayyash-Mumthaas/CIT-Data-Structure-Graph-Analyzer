package searching;

/**
 * Immutable value object representing the outcome of a search operation.
 * Encapsulates the target index (or -1 if absent) and the total number of
 * element comparisons performed during the search.
 * 
 * Author: Ayyash Mumthaas (23DA2-1033)
 */
public class SearchResult {
    private final int index;
    private final int comparisons;

    /**
     * Constructs a SearchResult instance.
     * 
     * @param index The 0-based index of the found element, or -1 if not found.
     * @param comparisons The total number of element comparisons performed.
     */
    public SearchResult(int index, int comparisons) {
        this.index = index;
        this.comparisons = comparisons;
    }

    /**
     * Returns the index where the target was found, or -1 if absent.
     * 
     * @return 0-based index or -1.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the number of element comparisons made during the search.
     * 
     * @return Total comparisons.
     */
    public int getComparisons() {
        return comparisons;
    }

    /**
     * Indicates whether the target element was found in the data structure.
     * 
     * @return true if index != -1, false otherwise.
     */
    public boolean isFound() {
        return index != -1;
    }

    @Override
    public String toString() {
        return "SearchResult[index=" + index + ", comparisons=" + comparisons + ", found=" + isFound() + "]";
    }
}
