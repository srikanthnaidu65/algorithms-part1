import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation. Given a composite systems comprised of randomly distributed insulating
 * and metallic materials: what fraction of the materials need to be metallic so that
 * the composite system is an electrical conductor? Given a porous landscape with water
 * on the surface (or oil below), under what conditions will the water be able to drain
 * through to the bottom (or the oil to gush through to the surface)? Scientists have
 * defined an abstract process known as percolation to model such situations.
 * <p>
 * The model. We model a percolation system using an n-by-n grid of sites. Each site is
 * either open or blocked. A full site is an open site that can be connected to an open
 * site in the top row via a chain of neighboring (left, right, up, down) open sites.
 * We say the system percolates if there is a full site in the bottom row. In other words,
 * a system percolates if we fill all open sites connected to the top row and that process
 * fills some open site on the bottom row. (For the insulating/metallic materials example,
 * the open sites correspond to metallic materials, so that a system that percolates has a
 * metallic path from top to bottom, with full sites conducting. For the porous substance
 * example, the open sites correspond to empty space through which water might flow, so that
 * a system that percolates lets water fill open sites, flowing from top to bottom.)
 * <p>
 * Refer: <a href="https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php">...</a>
 *
 * @author srikanth
 * @since 06/01/2023
 */
public class Percolation {

    private static final int VIRTUAL_TOP = 0;
    private int virtualBottom;
    private int size;
    private boolean[][] opened;
    private int openSites;
    private WeightedQuickUnionUF uf;

    /**
     * creates n-by-n grid, with all sites initially blocked
     *
     * @param n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        virtualBottom = n * n + 1;
        openSites = 0;
        opened = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    /**
     * Throw an IllegalArgumentException if row, col are outside its prescribed range
     *
     * @param row
     * @param col
     */
    private void validate(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Return the 2-dimensional index
     *
     * @param row
     * @param col
     * @return
     */
    private int getIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    /**
     * opens the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        validate(row, col);
        opened[row - 1][col - 1] = true;
        ++openSites;

        // Edge Case: If any of the first row boxes are opened then Union(box, top)
        if (row == 1) {
            uf.union(VIRTUAL_TOP, getIndex(row, col));
        }

        // Edge Case: If any of the last row boxes are opened then Union(box, bottom)
        if (row == size) {
            uf.union(getIndex(row, col), virtualBottom);
        }

        // If any of the boxes in the middle rows (except first and last) are opened
        // then check for neighbouring unions (top, bottom, left, right)
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(getIndex(row, col), getIndex(row - 1, col));
        }

        if (row < size && isOpen(row + 1, col)) {
            uf.union(getIndex(row, col), getIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(getIndex(row, col), getIndex(row, col - 1));
        }

        if (col < size && isOpen(row, col + 1)) {
            uf.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return opened[row - 1][col - 1];
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.find(VIRTUAL_TOP) == uf.find(getIndex(row, col));
    }

    /**
     * returns the number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return uf.find(VIRTUAL_TOP) == uf.find(virtualBottom);
    }
}
