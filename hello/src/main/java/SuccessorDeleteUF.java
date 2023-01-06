import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

/**
 * Coursera - Algorithms Part I
 * Week 1 - Interview Questions - Union Find
 * <p>
 * Question 2: Successor with delete
 * Given a set of N integers S={0,1,...,N−1} and a sequence of requests of the following form:
 * - Remove x from S
 * - Find the successor of x: the smallest y in S such that y≥x.
 * Design a data type so that all operations (except construction) should take
 * logarithmic time or better.
 * <p>
 * Solution:
 * Use union find data structure to mark successor of each integer. When an
 * integer is removed, the union find data structure connects to the next
 * integer in the list (which may be farther up the list). Must be careful with
 * optimizations since connected component id is the successor integer.
 * <p>
 *
 * @author srikanth
 * @since 05/01/2023
 */
public class SuccessorDeleteUF {

    private QuickUnionUF uf;

    public SuccessorDeleteUF(int n) {
        uf = new QuickUnionUF(n);
    }

    public void remove(int x) {
        uf.union(x, x + 1);
    }

    public int successor(int x) {
        return uf.find(x);
    }

    public static void main(String[] args) {
        SuccessorDeleteUF sd = new SuccessorDeleteUF(50);
        StdOut.println(sd.successor(10));
        sd.remove(11);
        sd.remove(13);
        sd.remove(12);
        sd.remove(10);
        sd.remove(9);
        sd.remove(15);
        StdOut.println(sd.successor(8));
        StdOut.println(sd.successor(9));
        StdOut.println(sd.successor(10));
    }
}
