import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author srikanth
 * @since 14/01/2023
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == items.length) {
            resize(2 * items.length);
        }
        if (n == 0) {
            items[n++] = item;
            return;
        }
        int randomIndex = StdRandom.uniformInt(n);
        Item temp = items[randomIndex];
        items[randomIndex] = item;
        items[n++] = temp;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (n == items.length / 4) {
            resize(items.length / 2);
        }
        int randomIndex = StdRandom.uniformInt(n);
        Item item = items[randomIndex];
        items[randomIndex] = items[--n];
        items[n] = null;            // to prevent loitering
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniformInt(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private int i;
        private final int[] randomIndices;

        public ArrayIterator() {
            i = 0;
            randomIndices = new int[n];
            for (int j = 0; j < n; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[randomIndices[i++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        StdOut.println(queue.size());
        for (Integer i : queue) {
            System.out.println(i);
        }
        StdOut.println("Sample:" + queue.sample());
        StdOut.println("Dequeue");
        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
        StdOut.println(queue.size());
    }
}
