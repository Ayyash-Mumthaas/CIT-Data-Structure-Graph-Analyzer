package data;

/**
 * An array-backed LIFO stack of integers.
 * Empty pop() and peek() operations return -1.
 * push() is amortized O(1), pop(), peek(), isEmpty(), and size() are O(1),
 * and display() is O(n).
 */
public class Stack {
    private static final int INITIAL_CAPACITY = 8;

    private int[] elements = new int[INITIAL_CAPACITY];
    private int size = 0;

    public Stack() {
    }

    /** Adds a value to the top of the stack in amortized O(1) time. */
    public void push(int value) {
        if (size == elements.length) {
            resize(elements.length * 2);
        }

        elements[size] = value;
        size++;
    }

    /** Removes and returns the top value in O(1) time, or -1 if empty. */
    public int pop() {
        if (isEmpty()) {
            return -1;
        }

        size--;
        int value = elements[size];
        elements[size] = 0;
        return value;
    }

    /** Returns the top value in O(1) time, or -1 if empty. */
    public int peek() {
        if (isEmpty()) {
            return -1;
        }

        return elements[size - 1];
    }

    /** Prints the stack from top to bottom in O(n) time. */
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }

        System.out.println("Stack (top to bottom):");
        for (int index = size - 1; index >= 0; index--) {
            System.out.println(elements[index]);
        }
    }

    /** Returns whether the stack is empty in O(1) time. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of values in the stack in O(1) time. */
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        int[] expanded = new int[capacity];
        System.arraycopy(elements, 0, expanded, 0, size);
        elements = expanded;
    }
}
