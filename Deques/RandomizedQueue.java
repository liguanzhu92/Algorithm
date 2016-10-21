import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int len;
    private Item[] q;
        public RandomizedQueue() {
        // construct an empty randomized queue
        len = 0;
        q = (Item[]) new Object[2];
    }
    private void reSize(int cap) {
        Item[] newQ = (Item[]) new Object[cap];
        // copy
        for (int i = 0; i < len; i++) {
            newQ[i] = q[i];
        }
        q = newQ;
    }

    public boolean isEmpty() {
        // is the queue empty?
        return len == 0;
    }
    
    public int size() {
        // return the number of items on the queue
        return len;
    }
    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new NullPointerException();
        }
        // exceed size, resize the array;
        if (len == q.length) reSize(2*len);
        q[len] = item;
        len++;
    }
    public Item dequeue() {
        // remove and return a random item
        if (len == 0) {
            throw new NoSuchElementException();
        }            
        int idx = StdRandom.uniform(len);
        Item ret = q[idx];
        if (idx != len - 1) {
            q[idx] = q[len-1];
            q[len - 1] = null;
        }
        len--;
        if (len <= q.length/4) reSize(q.length/2);
        return ret;
    }
    
    public Item sample() {
        // return (but do not remove) a random item
        if (len == 0) {
            throw new NoSuchElementException();
        }            
        int idx = StdRandom.uniform(len);
        return q[idx];
        
    }
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        // build a new random iterator
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<Item> {
        private int n = q.length;
        private Item[] newQ;
        public ArrayIterator() {
            // initial the new array;
            newQ = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                newQ[i] = q[i];
            }
        }
        public boolean hasNext() {
            return n != 0;
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = (StdRandom.uniform(n));  
            Item item = newQ[index];  
            if (index != n-1) {  
                newQ[index] = newQ[n-1];  
            }  
            newQ[n-1] = null; // to avoid loitering  
            n--;  
            return item; 
            
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args) {
        // unit testing
    }
}