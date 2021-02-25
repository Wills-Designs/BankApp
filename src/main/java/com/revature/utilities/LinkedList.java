package com.revature.utilities;
import com.revature.users.AppUser;

/** custom implementation of a linkedlist,
 * does not rely on java.util */
public class LinkedList<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    /**
     * finds first node in the list
     * @param data
     * @return
     */
    public T findFirst(T data) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.nextNode) {
            if (currentNode.data.equals(data)) {
                return currentNode.data;
            }
        }
        return null;
    }

    /**
     * takes data and inserts into a new node,
     *  by moving tail foward one spot and setting new data as the new tail
     * @param data
     */
    public void insert(T data) {
        if (data == null) {
            return;
        }

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            tail = head = newNode;
        } else {
            tail.nextNode = newNode;
            newNode.prevNode = tail;
            tail = newNode;
        }
        size++;
    }

    /** pulls value of head node and removes from list */
    public T pop() {
        if (head == null) {
            return null;
        }

        T popped = head.data;
        head = head.nextNode;

        if (head != null) {
            head.prevNode = null;
        }
        size--;
        return popped;
    }

    /** only checks value of head node */
    public T peek() {
        return (head == null) ? null : head.data;
    }

    /**
     * loops through list to check if the data being input is in the list or not
     * returns a boolean depending on if it was found or not
     * @param data
     * @return
     */
    public boolean contains(T data) {
        if (data == null) {
            return false;
        }
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.nextNode) {
            if (currentNode.data.equals(data)) {
                return true;
            }
        }
        return false;
    }

    /** returns current size of list */
    public int size() {
        return size;
    }

    /** loops through list and prints out nodes starting at the head */
    public void printList() {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.nextNode) {
            System.out.println(currentNode.data);
        }
    }

    private static class Node<T> {
        T data;
        Node<T> nextNode;
        Node<T> prevNode;

        Node(T data) {
            this.data = data;
        }

        Node(T data, Node<T> nextNode, Node<T> prevNode) {
            this(data);
            this.nextNode = nextNode;
            this.prevNode = prevNode;
        }
    }

}