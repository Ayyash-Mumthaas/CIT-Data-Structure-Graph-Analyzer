package data;

/**
 * An array-backed FIFO queue of integers using circular indexing.
 * Empty dequeue() and peek() operations return -1.
 * enqueue() is amortized O(1) (O(n) when resizing), dequeue(), peek(),
 * isEmpty(), and size() are O(1), and display() is O(n).
 */
public class Queue {
    private static final int INITIAL_CAPACITY = 8;

    private int[] elements = new int[INITIAL_CAPACITY];
    private int front = 0;
    private int size = 0;

    public Queue() {
    }

    /** Adds a value to the rear of the queue in amortized O(1) time. */
    public void enqueue(int value) {
        if (size == elements.length) {
            resize(elements.length * 2);
        }

        int rear = (front + size) % elements.length;
        elements[rear] = value;
        size++;
    }

    /** Removes and returns the front value in O(1) time, or -1 if empty. */
    public int dequeue() {
        if (isEmpty()) {
            return -1;
        }

        int value = elements[front];
        elements[front] = 0;
        front = (front + 1) % elements.length;
        size--;
        if (size == 0) {
            front = 0;
        }
        return value;
    }

    /** Returns the front value in O(1) time, or -1 if empty. */
    public int peek() {
        if (isEmpty()) {
            return -1;
        }

        return elements[front];
    }

    /** Prints the queue from front to rear in O(n) time. */
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.print("Queue (front to rear): ");
        for (int index = 0; index < size; index++) {
            int position = (front + index) % elements.length;
            System.out.print(elements[position]);
            if (index < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /** Returns whether the queue is empty in O(1) time. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of values in the queue in O(1) time. */
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        int[] expanded = new int[capacity];
        for (int index = 0; index < size; index++) {
            expanded[index] = elements[(front + index) % elements.length];
        }
        elements = expanded;
        front = 0;
    }
}
