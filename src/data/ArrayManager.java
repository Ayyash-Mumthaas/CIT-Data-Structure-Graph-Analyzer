package data;

/**
 * ArrayManager demonstrates fundamental array operations using a native fixed-size 
 * integer array with controlled dynamic resizing.
 * 
 * Demonstrates:
 * - Insertion (with dynamic capacity doubling)
 * - Deletion (with element shifting)
 * - Linear search (with comparison tracking)
 * - Element display and state inspection
 * 
 * Author: Ayyash Mumthaas (23DA2-1033)
 */
public class ArrayManager {
    private static final int DEFAULT_CAPACITY = 10;

    private int[] data;
    private int size;
    private int lastSearchComparisons;

    /**
     * Default constructor initializing internal array with DEFAULT_CAPACITY (10).
     */
    public ArrayManager() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Parameterized constructor allowing a custom initial capacity.
     * 
     * @param initialCapacity Initial capacity of the array (must be > 0).
     */
    public ArrayManager(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = DEFAULT_CAPACITY;
        }
        this.data = new int[initialCapacity];
        this.size = 0;
        this.lastSearchComparisons = 0;
    }

    /**
     * Inserts a value at the end of the array.
     * If the array is full, capacity is doubled automatically.
     * 
     * Time Complexity:
     * - Normal case: O(1)
     * - Amortized: O(1)
     * - Worst case (during resize): O(n)
     * 
     * @param value The integer value to insert.
     */
    public void insert(int value) {
        if (size == data.length) {
            resize();
        }
        data[size] = value;
        size++;
    }

    /**
     * Doubles the capacity of the internal array and copies existing elements.
     * 
     * Time Complexity: O(n)
     */
    private void resize() {
        int newCapacity = data.length * 2;
        int[] newData = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    /**
     * Deletes the first occurrence of the specified value.
     * Shifts all subsequent elements left to maintain array contiguity.
     * 
     * Time Complexity: O(n) due to search scan and element shifting.
     * 
     * @param value The value to remove.
     * @return true if the element was found and deleted, false otherwise.
     */
    public boolean delete(int value) {
        if (isEmpty()) {
            return false;
        }

        int targetIndex = -1;
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            return false;
        }

        // Shift subsequent elements to the left
        for (int i = targetIndex; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        size--;
        return true;
    }

    /**
     * Performs a linear search for the specified target value.
     * Tracks the number of comparisons made during the search.
     * 
     * Time Complexity:
     * - Best case: O(1) (found at first index)
     * - Average case: O(n)
     * - Worst case: O(n) (found at last index or absent)
     * 
     * @param value The value to search for.
     * @return The 0-based index of the target, or -1 if not found.
     */
    public int search(int value) {
        lastSearchComparisons = 0;
        for (int i = 0; i < size; i++) {
            lastSearchComparisons++;
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the comparison count from the most recent search operation.
     * 
     * @return Number of comparisons in the last search.
     */
    public int getLastSearchComparisons() {
        return lastSearchComparisons;
    }

    /**
     * Displays all currently stored elements, logical size, and total capacity.
     * 
     * Time Complexity: O(n)
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Array is empty.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Array: [");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
        System.out.println("Size: " + size);
        System.out.println("Capacity: " + data.length);
    }

    /**
     * Checks if the array has no elements.
     * 
     * Time Complexity: O(1)
     * 
     * @return true if size == 0, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements currently stored.
     * 
     * Time Complexity: O(1)
     * 
     * @return Current logical size.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the current internal capacity of the array.
     * 
     * Time Complexity: O(1)
     * 
     * @return Total capacity before next resize is required.
     */
    public int capacity() {
        return data.length;
    }

    /**
     * Returns the element at the specified index.
     * 
     * Time Complexity: O(1)
     * 
     * @param index 0-based index.
     * @return The element at index.
     * @throws IndexOutOfBoundsException if index is invalid.
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
        return data[index];
    }

    /**
     * Returns a copy of the active elements as a primitive array.
     * Ensures encapsulation without exposing internal array reference.
     * 
     * Time Complexity: O(n)
     * 
     * @return A new int array containing active elements.
     */
    public int[] toArray() {
        int[] copy = new int[size];
        for (int i = 0; i < size; i++) {
            copy[i] = data[i];
        }
        return copy;
    }

    /**
     * Resets the array to empty state without reallocating memory.
     * 
     * Time Complexity: O(1)
     */
    public void clear() {
        this.size = 0;
        this.lastSearchComparisons = 0;
    }
}
