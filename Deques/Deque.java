import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node rear;
    private int len;
    private class Node {
        private Item it;
        private Node prev;
        private Node next;  
        private Node(Item item) {
            this.it = item;
            this.prev = null;
            this.next = null;
        }
    }
    public Deque() {
        // construct an empty deque
        len = 0;
        front = null;
        rear = null;
    }
    
    public boolean isEmpty() {
        // is the deque empty?
        return len == 0;
    }
    
    public int size() {
        // return the number of items on the deque
        return len;
    }
    
    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) {
            throw new NullPointerException();
        }
        Node addNode = new Node(item);
        if (len == 0) {
            front = addNode;
            rear = addNode;
        } else {
            addNode.next = front;
            front.prev = addNode;
            front = addNode;
        }           
        len++;
    }
    
    public void addLast(Item item) {
        // add the item to the end
        if (item == null) {
            throw new NullPointerException();
        }
        Node addNode = new Node(item);
        if (len == 0) {
            front = addNode;
            rear = addNode;
        } else {
            rear.next = addNode;
            addNode.prev = rear;
            rear = addNode;
        }
        len++;
    }
    public Item removeFirst() {
        // remove and return the item from the front
        if (len == 0) {
            throw new NoSuchElementException();
        }
        Item first = front.it;
        front = front.next;
        len--;
        if (len != 0) {
            front.prev = null;
        } else {
            // the list should be empty
            front = null;
            rear = null;
        }
        return first;
    }
    
    public Item removeLast() {
        // remove and return the item from the end
        if (len == 0) {
            throw new NoSuchElementException();
        }
        Item last = rear.it;
        rear = rear.prev;
        len--;
        if (len != 0) {
            rear.next = null;
        } else {
            front = null;
            rear = null;
        }
       return last;
    }
    
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional  
    private class ListIterator implements Iterator<Item> {  
        private Node current = front;  
        public boolean hasNext() {
            return current != null;
        }  
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }  
  
        public Item next() {  
            if (!hasNext()) 
                throw new java.util.NoSuchElementException();  
            Item item = current.it;  
            current = current.next;  
            return item;  
        }  
    }
    public static void main(String[] args) {
        // unit testing

    }
}