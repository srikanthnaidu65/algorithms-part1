import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * @author srikanth
 * @since 06/01/2023
 */
public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int experimentsCount;
    private final double[] fractions;

    /**
     * perform t independent trials on an n-by-n grid
     */
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("Either n <= 0 or t <= 0");
        }
        experimentsCount = t;
        fractions = new double[experimentsCount];
        for (int exp = 0; exp < experimentsCount; exp++) {
            Percolation percolation = new Percolation(n);
            int openSites = 0;
            while (!percolation.percolates()) {
                int i = StdRandom.uniformInt(1, n + 1);
                int j = StdRandom.uniformInt(1, n + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    openSites++;
                }
            }
            double fraction = (double) openSites / (n * n);
            fractions[exp] = fraction;
        }
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(fractions);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(experimentsCount));
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(experimentsCount));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        Stopwatch stopwatch = new Stopwatch();
        StdOut.println("Initial time: " + stopwatch.elapsedTime());

        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");

        StdOut.println("Finish time: " + stopwatch.elapsedTime());
    }
}
