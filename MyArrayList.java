//********************************************************************
//  ArrayList.java       Authors: Lewis/Chase
//                       Mods   : JCD
//  Represents an array implementation of a list. The front of
//  the list is kept at array index 0. This class will be extended
//  to create a specific kind of list.
//********************************************************************
import java.util.Iterator;

public class MyArrayList implements ListADT
{
    private final int DEFAULT_CAPACITY = 100;
    private final int NOT_FOUND = -1;
    protected int rear;
    protected Object[] list;

    //-----------------------------------------------------------------
    //  Creates an empty list using the default capacity.
    //-----------------------------------------------------------------
    public MyArrayList()
    {
        rear = 0;
        list = new Object[DEFAULT_CAPACITY];
    }

    //-----------------------------------------------------------------
    //  Creates an empty list using the specified capacity.
    //-----------------------------------------------------------------
    public MyArrayList (int initialCapacity)
    {
        rear = 0;
        list = new Object[initialCapacity];
    }

    //-----------------------------------------------------------------
    //  Removes and returns the last element in the list.
    //-----------------------------------------------------------------
    public Object removeLast ()
    {
        Object result;

        if (isEmpty())
            System.err.println("Empty collection exception");

        rear--;
        result = list[rear];
        list[rear] = null;

        return result;
    }

    //-----------------------------------------------------------------
    //  Removes and returns the first element in the list.
    //-----------------------------------------------------------------
    public Object removeFirst()
    {
        if (isEmpty())
            System.err.println("Empty collection exception");

        Object result = list[0];
        rear--;
        // shift the elements
        for (int scan=0; scan < rear; scan++)
            list[scan] = list[scan+1];


        list[rear] = null;

        return result;
    }

    //-----------------------------------------------------------------
    //  Removes and returns the specified element.
    //-----------------------------------------------------------------
    public Object remove (Object element)
    {
        Object result;
        int index = find (element);

        if (index == NOT_FOUND)
            System.err.println("Empty collection exception");

        result = list[index];
        rear--;
        // shift the appropriate elements
        for (int scan=index; scan < rear; scan++)
            list[scan] = list[scan+1];


        list[rear] = null;

        return result;
    }


    //-----------------------------------------------------------------
    //  Returns a reference to the element at the front of the list.
    //  The element is not removed from the list.  Throws an
    //  EmptyCollectionException if the list is empty.
    //-----------------------------------------------------------------
    public Object first()
    {
        if (isEmpty())
            System.err.println("Empty collection exception");

        return list[0];
    }

    //-----------------------------------------------------------------
    //  Returns a reference to the element at the rear of the list.
    //  The element is not removed from the list.  Throws an
    //  EmptyCollectionException if the list is empty.
    //-----------------------------------------------------------------
    public Object last()
    {
        if (isEmpty())
            System.err.println("Empty collection exception");

        return list[rear-1];
    }

    //-----------------------------------------------------------------
    //  Returns true if this list contains the specified element.
    //-----------------------------------------------------------------
    public boolean contains (Object target)
    {
        return (find(target) != NOT_FOUND);
    }

    //-----------------------------------------------------------------
    //  Returns the array index of the specified element, or the
    //  constant NOT_FOUND if it is not found.
    //-----------------------------------------------------------------
    private int find (Object target)
    {
        int scan = 0, result = NOT_FOUND;
        boolean found = false;

        if (! isEmpty())
            while (! found && scan < rear)
                if (target.equals(list[scan]))
                    found = true;
                else
                    scan++;

        if (found)
            result = scan;

        return result;
    }

    //-----------------------------------------------------------------
    //  Returns true if this list is empty and false otherwise.
    //-----------------------------------------------------------------
    public boolean isEmpty()
    {
        return (rear == 0);
    }

    //-----------------------------------------------------------------
    //  Returns the number of elements currently in this list.
    //-----------------------------------------------------------------
    public int size()
    {
        return rear;
    }

    //-----------------------------------------------------------------
    //  Returns an iterator for the elements currently in this list.
    //-----------------------------------------------------------------
    public Iterator iterator()
    {
        return new ArrayIterator(list, rear);
    }

    //-----------------------------------------------------------------
    //  Returns a string representation of this list.
    //-----------------------------------------------------------------
    public String toString()
    {
        String result = "";

        for (int scan=0; scan < rear; scan++)
            result = result + list[scan].toString() + "\n";

        return result;
    }

    //-----------------------------------------------------------------
    //  Creates a new array to store the contents of the list with
    //  twice the capacity of the old one.
    //-----------------------------------------------------------------
    protected void expandCapacity()
    {
        Object[] larger = new Object[list.length*2];

        for (int scan=0; scan < list.length; scan++)
            larger[scan] = list[scan];

        list = larger;
    }
}

