import java.util.*;

/**
 * Linked List class for homework 2.
 * @author Evan Gary, Christopher Summa
 * @version 28 June 2017
 */

public class LinkedList<AnyType> {

    private Node first;
    private Node last;
    private int   size;

    public LinkedList() {
        first = null;
        last  = null;
        size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Insert an element using the iterator.
     * The element will be added ahead of the iterator node, then the iterator node will advance to the inserted node.
     * @param value
     * @param iter
     */
    public void insert(AnyType value, LinkedListIterator iter) {

        Node n = new Node(value);

        if (isEmpty() && !iter.hasNext()) {
            this.first = n;
            this.last = n;
            size++;
        }

        else {
            if (iter.whereIAm == first){
                n.setNext(this.first);
                this.first = n;
                iter.whereIAm = n.getNext();
            }

            else if (iter.whereIAm == last){
                this.last.setNext(n);
                this.last = n;
                iter.whereIAm = n.getNext();
            }

            else {
                n.setNext(iter.whereIAm.getNext());
                iter.whereIAm.setNext(n);
                iter.whereIAm = iter.whereIAm.getNext();
            }
            size++;
        }
    }

    /**
     * Insert a value using an index.
     * @param value
     * @param index
     */
    public void insert(AnyType value, int index) {

    Node n = new Node(value);

    if (isEmpty() && index == 0) {
        this.first = n;
        this.last = n;
        size++;
    }        
    else if (size >= index && !isEmpty()) {
        if (index == 0 ) { // inserting at beginning
            n.setNext(this.first);
            this.first = n;
        }
        else if (index == size ) { // inserting at end
            this.last.setNext(n);
            this.last = n;
        }
        else if (index < size) { // inserting in middle
            // first, find the node "before" where we want to insert n
            Node before = first;
            int nodeNum = 0;
            while (nodeNum < index-1) {
                before = before.getNext();
                nodeNum++;
            }
            // when we get to this point, before should have a reference to the node at index-1
            System.out.println("NodeNum: " + nodeNum);
            System.out.println("Node value: " + before.getElement());

            n.setNext(before.getNext());
            before.setNext(n);

        }
        size++;

    }
    else
        System.err.println("Error: cannot insert at that point");
    }

    /**
     * Delete a value using an index and decrement the size variable.
     *
     * @param index
     */
    public void delete(int index) {
        if (isEmpty()){
            throw new NoSuchElementException("Can't delete from an empty list.");
        }

        if (index == 0) { //remove first element
            this.first = this.first.getNext();
            size--;
        }

        else if (index <= size ){ //remove a later element
            Node before = first;
            int nodeNum = 0;
            while (nodeNum < index - 1){
                before = before.getNext();
                nodeNum++;
            }

            if (index < size - 1) { //if not removing the last element
                before.setNext(before.getNext().getNext());
            }
            else if(index == size) { //if removing the last element
                before.setNext(null);
                this.last = before;
            }
            size--;
        }

        else {
            System.err.println("Error: cannot remove at that point");
        }

    }

    /**
     * Delete a value using iterator.
     * The last value returned is removed. This method cannot be called consecutively.
     * @param iter
     */
    public void delete(LinkedListIterator iter) {
        iter.remove();
    }

    /**
     * Get the value at the given index.
     * @param index
     * @return
     */
    public AnyType get(int index) {
        Node result = first;
        int nodeNum = 0;
        while (nodeNum < index) {
            result = result.getNext();
            nodeNum++;
        }
        return result.getElement();
    }

    /**
     * Get the value at the iterator node.
     * @param iter
     * @return
     */
    public AnyType get(LinkedListIterator iter) {
        return iter.whereIAm.getElement();
    }

    public LinkedListIterator iterator() {
        return new LinkedListIterator();
    }

    public int size() {
        return this.size;
    }

    /**
     * Overriden toString method.
     * Returns every element in the list's string (from that class's toString) separated by a spaces.
     * @return
     */
    @Override
    public String toString(){
        String toReturn = "";
        if (!this.isEmpty()) {
            Node before = this.first;
            for (int i = 0; i < size; i++) {
                toReturn = toReturn + " " + before.getElement().toString();
                before = before.getNext();
            }
        }
        return toReturn;
    }

    public class LinkedListIterator implements java.util.Iterator<AnyType>{

        private Node whereIAm;
        //nextCalled holds whether or not next() has been called since an element was removed
        private boolean nextCalled;

        public LinkedListIterator() {
            whereIAm = first;
            nextCalled = false;
        }

        /** next returns the next element in the list, and increments
        *  the iterator
         */
        public AnyType next() {
            if (nextCalled && hasNext()) {
                AnyType value = whereIAm.getNext().getElement();
                whereIAm = whereIAm.getNext();
                return value;

            }
            // if a remove was called, do NOT increment the iterator since whereIAm now points to the "next" node
            else if (!nextCalled && whereIAm != null){
                AnyType value = whereIAm.getElement();
                nextCalled = true;
                return value;
            }
            else {
                throw new java.util.NoSuchElementException();
            }
        }

        /**
         * Check whether or not there is a node after the iterator node.
         * @return
         */
        public boolean hasNext() {
            if (whereIAm == null) return false;
            if (whereIAm.getNext() != null )
                return true;
            else
                return false;

        }

        /**
         * Removes the last element returned by the iterator.
         * As per the specifications of Iterator, this method cannot be called consecutively or else an exception is called
         * @throws IllegalStateException
         */
        public void remove() {
            if (nextCalled) {
                Node before = first;
                //Get the node before whereIAm
                while (before.getNext() != whereIAm && whereIAm != before) {
                    before = before.getNext();
                }
                //set the current node to the next node
                whereIAm = whereIAm.getNext();
                //set the previous node to the new whereIAm
                before.setNext(whereIAm);
                size--;
                nextCalled = false;
            }

            //Can't remove more than once per next() call
            else
                throw new IllegalStateException("Cannot call remove more than once on an element.");
        }
    }    // end class Iterator

    private class Node {

        private AnyType myDataElement;
        private Node next;

        public Node(AnyType data) {
            myDataElement = data;
            next = null;
        }

        public Node(AnyType data, Node next) {
            myDataElement = data;
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }

        public AnyType getElement() {
            return this.myDataElement;
        }

        public void setNext(Node next) {
            this.next = next;
        }
            
        public void setElement(AnyType data) {
            this.myDataElement = data;
        }

    } // end class Node

} // end class LinkedList
