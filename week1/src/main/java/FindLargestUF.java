import edu.princeton.cs.algs4.StdOut;

/**
 * Coursera - Algorithms Part I
 * Week 1 - Interview Questions - Union Find
 * <p>
 * Question 2: Union-find with specific canonical element
 * <p>
 * Add a method find() to the union-find data type so that find(i) returns the largest
 * element in the connected component containing i. The operations, union(), connected(),
 * and find() should all take logarithmic time or better.
 * For example, if one of the connected components is {1,2,6,9}, then the  find() method
 * should return 9 for each of the four elements in the connected components.
 * <p>
 * Solution:
 * <p>
 * Add an extra array to contain the larger number when union 2 components, and we only
 * store the value in the root node, every time, we just need to traverse to the root node,
 * we will get the max number of components containing ie., (lgN)
 * <p>
 *
 * @author srikanth
 * @since 05/01/2023
 */
public class FindLargestUF {

    private int[] parent;
    private int[] size;
    private int[] largest;
    private int count;

    public FindLargestUF(int n) {
        parent = new int[n];
        size = new int[n];
        largest = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = i;
            largest[i] = i;
        }
    }

    private int root(int p) {
        while (parent[p] != p) {
            p = parent[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) {
            return;
        }
        int pLarge = largest[pRoot];
        int qLarge = largest[qRoot];
        if (size[pRoot] < size[qRoot]) {
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
            if (pLarge > qLarge) {
                largest[qRoot] = pLarge;
            }
        } else {
            parent[qRoot] = pRoot;
            size[pRoot] = qRoot;
            if (largest[qRoot] > pLarge) {
                largest[pRoot] = qLarge;
            }
        }
        count--;
    }

    public int findLargest(int p) {
        return largest[root(p)];
    }

    private int getCount() {
        return count;
    }

    public static void main(String[] args) {
        FindLargestUF fl = new FindLargestUF(10);
        fl.union(0, 2);
        fl.union(8, 4);
        StdOut.println(fl.findLargest(0));
        StdOut.println(fl.findLargest(4));
        fl.union(0, 4);
        StdOut.println(fl.findLargest(0));
        StdOut.println(fl.findLargest(2));
        fl.union(0, 6);
        StdOut.println(fl.findLargest(6));
        fl.union(3, 5);
        fl.union(1, 2);
        StdOut.println(fl.findLargest(4));
        StdOut.println(fl.findLargest(3));
    }
}
