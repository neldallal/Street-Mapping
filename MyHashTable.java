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
 * */



/** Citation:
 * The framework for this class is referenced from the Princeton University Textbook
 * https://introcs.cs.princeton.edu/java/home/
 * (see references in README file)
 * */

public class MyHashTable<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int x;       // number of key-value pairs
    private int y;       // number of chains
    private MyNode[] arr;    // array of linked-list symbol tables

    // a helper node class (specific for hash table use)
    private static class MyNode {
        private final Object key;
        private Object data;
        private MyNode next;

        public MyNode(Object key, Object data, MyNode next)  {
            this.key  = key;
            this.data  = data;
            this.next = next;
        }
    }

    public MyHashTable() {
        this(INIT_CAPACITY);
    }

    public MyHashTable(int y) {
        this.y = y;
        arr = new MyNode[y];
    }

    // resize the hash table to have the given number of chains,
    // rehashing all the keys
    @SuppressWarnings("unchecked")
    private void resize(int chains) {
        MyHashTable<Key, Value> temp = new MyHashTable<Key, Value>(chains);
        for (int i = 0; i < y; i++) {
            for (MyNode x = arr[i]; x != null; x = x.next) {
                temp.put((Key) x.key, (Value) x.data);
            }
        }

        this.y  = temp.y;
        this.x  = temp.x;
        this.arr = temp.arr;
    }

    // hash value between 0 and m-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % y;
    }

    public int size() {
        return x;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    @SuppressWarnings("unchecked")
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        for (MyNode x = arr[i]; x != null; x = x.next) {
            if (key.equals(x.key)) return (Value) x.data;
        }
        return null;
    }

    public void put(Key key, Value data) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (data == null) {
            remove(key);
            return;
        }

        // double table size if average length of list >= 10
        if (x >= 10*y)
            resize(2*y);

        int i = hash(key);
        for (MyNode x = arr[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.data = data;
                return;
            }
        }
        x++;
        arr[i] = new MyNode(key, data, arr[i]);
    }

    public void remove(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to remove() is null");

        int i = hash(key);
        arr[i] = remove(arr[i], key);

        // halve table size if average length of list <= 2
        if (y > INIT_CAPACITY && x <= 2*y) resize(y/2);
    }

    // remove key in linked list beginning at Node x
    private MyNode remove(MyNode node, Key key) {
        if (node == null) return null;
        if (key.equals(node.key)) {
            x--;
            return node.next;
        }
        node.next = remove(node.next, key);
        return node;
    }
    /**
     * This method returns all keys in the symbol table.
     *
     * @return all keys in the symbol table, as in iterable
     */
    @SuppressWarnings("unchecked")
    public Iterable<Key> keys()  {
        MyQueue<Key> q1 = new MyQueue<Key>();
        for (int i = 0; i < y; i++) {
            for (MyNode x = arr[i]; x != null; x = x.next) {
                q1.enqueue((Key) x.key);
            }
        }
        return q1;
    }

    public Iterable<Value> values()  {
        MyQueue<Value> q2 = new MyQueue<Value>();
        for (int i = 0; i < y; i++) {
            for (MyNode x = arr[i]; x != null; x = x.next) {
                q2.enqueue((Value) x.data);
            }
        }
        return q2;
    }

}