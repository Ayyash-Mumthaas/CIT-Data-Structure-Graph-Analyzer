package data;

public class LinkedList {

    private static class Node {
        private int value;
        private Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    // O(1) time complexity - inserts at the head
    public void insert(int value) {
        Node newNode = new Node(value);

        newNode.next = head;
        head = newNode;

        size++;
    }

    // O(n) time complexity - may traverse the list
    public boolean delete(int value) {
        if (head == null) {
            return false;
        }

        if (head.value == value) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;

        while (current.next != null) {
            if (current.next.value == value) {
                current.next = current.next.next;
                size--;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // O(n) time complexity
    public boolean search(int value) {
        Node current = head;

        while (current != null) {
            if (current.value == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // O(n) time complexity
    public void display() {
        Node current = head;

        while (current != null) {
            System.out.print(current.value);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println();
    }

    // O(1) time complexity
    public boolean isEmpty() {
        return head == null;
    }

    // O(1) time complexity
    public int size() {
        return size;
    }
}
