import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Coursera - Algorithms Part I
 * Week 1 - Interview Questions - Union Find
 * <p>
 * Question 1: Social network connectivity
 * <p>
 * Given a social network containing n members and a log file containing m timestamps
 * at which times pairs of members formed friendships, design an algorithm to determine
 * the earliest time at which all members are connected (i.e., every member is a friend
 * of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp
 * and that friendship is an equivalence relation. The running time of your algorithm
 * should be mlogn or better and use extra space proportional to n.
 * <p>
 * Solution:
 * <p>
 * Use a union-find data structure with each site representing a social network
 * member. Add unions between sites in time order of friendships being formed.
 * After each union is added, check the number of connected components
 * within the union-find data structure. If only one, all members are connected.
 * <p>
 * Must keep track of number of unique components. Decreases when a union occurs
 * between different components.
 * <p>
 * Solution:
 * <p>
 * Use an union-find data structure with each site representing a social network
 * member. Add unions between sites in time order of friendships being formed.
 * After each union is added, check the number of connected components
 * within the union-find data structure. If only one, all members are connected.
 * <p>
 * Must keep track of number of unique components. Decreases when a union occurs
 * between different components.
 * <p>
 * @author srikanth
 * @since 05/01/2023
 */
public class SocialNetworkUF {

    private int[] parent;
    private int[] size;
    private int count;

    public SocialNetworkUF(int n) {
        parent = new int[n];
        size = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = i;
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
        if (size[pRoot] < size[qRoot]) {
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            parent[qRoot] = pRoot;
            size[pRoot] = qRoot;
        }
        count--;
    }

    private int getCount() {
        return count;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        SocialNetworkUF sn = new SocialNetworkUF(n);
        String date;
        String time;
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            date = StdIn.readString();
            time = StdIn.readString();

            if (sn.connected(p, q)) {
                continue;
            }
            sn.union(p, q);

            if (sn.getCount() == 1) {
                StdOut.println("All members are connected at " + date + " " + time);
            }
        }
    }
}
