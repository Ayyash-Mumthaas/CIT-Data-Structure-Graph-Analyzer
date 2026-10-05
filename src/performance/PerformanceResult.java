package performance;

/**
 * PerformanceResult encapsulates both theoretical complexity information and
 * empirical benchmarking metrics (element comparisons and execution time)
 * for a search algorithm execution.
 * 
 * Author: Ayyash Mumthaas (23DA2-1033)
 */
public class PerformanceResult {
    private final String algorithmName;
    private final int datasetSize;
    private final int target;
    private final int resultIndex;
    private final int comparisons;
    private final long executionTimeNs;
    private final String theoreticalComplexity;

    /**
     * Constructs a PerformanceResult instance.
     * 
     * @param algorithmName        Name of the evaluated algorithm.
     * @param datasetSize          Number of elements in the tested dataset.
     * @param target               Target value searched for.
     * @param resultIndex          Index returned by search (-1 if not found).
     * @param comparisons          Total element comparisons performed.
     * @param executionTimeNs      Measured average execution time in nanoseconds.
     * @param theoreticalComplexity Theoretical Big-O time complexity notation.
     */
    public PerformanceResult(String algorithmName, int datasetSize, int target,
                             int resultIndex, int comparisons, long executionTimeNs,
                             String theoreticalComplexity) {
        this.algorithmName = algorithmName;
        this.datasetSize = datasetSize;
        this.target = target;
        this.resultIndex = resultIndex;
        this.comparisons = comparisons;
        this.executionTimeNs = executionTimeNs;
        this.theoreticalComplexity = theoreticalComplexity;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getDatasetSize() {
        return datasetSize;
    }

    public int getTarget() {
        return target;
    }

    public int getResultIndex() {
        return resultIndex;
    }

    public int getComparisons() {
        return comparisons;
    }

    public long getExecutionTimeNs() {
        return executionTimeNs;
    }

    public String getTheoreticalComplexity() {
        return theoreticalComplexity;
    }

    public boolean isFound() {
        return resultIndex != -1;
    }

    @Override
    public String toString() {
        return String.format("%s [Size: %d, Target: %d, Index: %d, Comps: %d, Time: %d ns, Complexity: %s]",
                algorithmName, datasetSize, target, resultIndex, comparisons, executionTimeNs, theoreticalComplexity);
    }
}
