import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int count;
    private Percolation perc;
    private double[] fractions;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        count = trials;
        fractions = new double[count];
        for (int i = 0; i < count; i++) {
            perc = new Percolation(n);
            int opened = 0;
            while (!perc.percolates()) {
                int j = StdRandom.uniformInt(1, n + 1);
                int k = StdRandom.uniformInt(1, n + 1);
                if (!perc.isOpen(j, k)) {
                    perc.open(j, k);
                    opened++;
                }
            }
            double fraction = (double) opened / (n * n);
            fractions[i] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(count));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(count));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
