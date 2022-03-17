/*
 * Partners: Nadine Eldallal and Jeremy Foss
 *
 * Name: Nadine Eldallal
 * Class: CSC 172
 * Project 3
 * neldalla@u.rochester.edu
 * 12/11/2021
 *
 * Name: Jeremy Foss
 * Class: CSC 172
 * Project 3
 * jfoss3@u.rochester.edu
 * 12/11/2021
 *
 *
 * This class is only used for this project to implement a queue in the
 * keys() and values() function in the MyHashTable class to return all keys in a table.
 *
 * */


/** Citation:
 * The framework for this class is referenced from the Princeton University Textbook
 * https://introcs.cs.princeton.edu/java/home/
 * (see references in README file)
 * */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<E> implements Iterable<E> {
    private MyNode2<E> head;    // beginning of queue
    private MyNode2<E> tail;     // end of queue
    private int count;            // number of elements on queue

    // helper node class
    private static class MyNode2<E> {
        private E item;
        private MyNode2<E> next;
    }

    public MyQueue() {
        head = null;
        tail  = null;
        count = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return count;
    }


    public void enqueue(E item) {
        MyNode2<E> oldTail = tail;
        tail = new MyNode2<E>();
        tail.item = item;
        tail.next = null;
        if (isEmpty()) head = tail;
        else           oldTail.next = tail;
        count++;
    }

    public String toString() {
        StringBuilder st = new StringBuilder();
        for (E it : this) {
            st.append(it);
            st.append(' ');
        }
        return st.toString();
    }

    public Iterator<E> iterator()  {
        return new LinkedIterator(head);
    }

    private class LinkedIterator implements Iterator<E> {
        private MyNode2<E> currNode;

        public LinkedIterator(MyNode2<E> head) {
            currNode = head;
        }

        public boolean hasNext()  { return currNode != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E it2 = currNode.item;
            currNode = currNode.next;
            return it2;
        }
    }

}
