import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Test client for {@link PercolationStats}.
 *
 * <p>Reads {@code n} (grid dimension) and {@code trials} (number of Monte Carlo
 * experiments) from standard input, delegates the simulation to
 * {@link PercolationStats}, then prints the mean, standard deviation, and 95%
 * confidence interval of the percolation threshold.
 *
 * <p><b>Input format:</b><br>
 * Line 1 – integer {@code n} (grid dimension)<br>
 * Line 2 – integer {@code trials} (number of independent experiments)
 *
 * <p><b>Usage:</b>
 * <pre>
 *   java PercolationStatsTest &lt; input.txt
 * </pre>
 *
 * @see PercolationStats
 * @see Percolation
 */
public class PercolationStatsTest extends PercolationStats {

    /**
     * Delegates directly to {@link PercolationStats#PercolationStats(int, int)}.
     *
     * @param n      grid dimension (n-by-n)
     * @param trials number of independent Monte Carlo experiments
     */
    public PercolationStatsTest(int n, int trials) {
        super(n, trials);
    }

    /**
     * Entry point for the test client.
     *
     * <p>Reads grid parameters from stdin, runs the simulation, and prints
     * statistical results along with elapsed wall-clock time.
     *
     * @param args command-line arguments (not used; input is read from stdin)
     */
    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();

        /*
         * Reads from stdin so the client can be driven by `ctrl+shift+b`
         * via inputf.in in the project root, rather than requiring command-line args.
         */
        In in = new In(new Scanner(System.in));
        int n = in.readInt();
        int trials = in.readInt();

        PercolationStatsTest percolationStats = new PercolationStatsTest(n, trials);

        // Alternative: pass n and trials as command-line arguments instead of stdin.
        // PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        // Previous manual trial loop (now handled internally by PercolationStats):
        //
        // int totalSites = n * n;
        // while (trials > 0) {
        //     Percolation perc = new Percolation(n);           // weighted quick-union
        //     // Percolation_QuickFindUF perc = ...            // quick-find variant
        //     // PercolationWeightedPathCompression perc = ...  // path-compression variant
        //
        //     while (!perc.percolates()) {
        //         int row = StdRandom.uniformInt(1, n + 1);
        //         int col = StdRandom.uniformInt(1, n + 1);
        //         perc.open(row, col);
        //     }
        //     percolationStats.stats[--trials] = 1d * perc.numberOfOpenSites() / totalSites;
        // }

        System.out.println(String.format(
                "mean                    = %s\nstddev                  = %s\n95%% confidence interval = [%s, %s]",
                percolationStats.mean(), percolationStats.stddev(),
                percolationStats.confidenceLo(), percolationStats.confidenceHi()));

        System.out.println("ElapsedTime: %s".formatted(stopwatch.elapsedTime()));
    }
}
