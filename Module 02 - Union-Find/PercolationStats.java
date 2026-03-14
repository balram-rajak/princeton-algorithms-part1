
// import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private double sqrtT;

    private double[] stats;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        // Square root of trials is used in confidence interval calculation instead of n
        sqrtT = Math.sqrt(trials);

        this.stats = new double[trials];

        while (trials > 0) {

            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {

                // randomly opening a site using row and col
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);

                perc.open(row, col);

            }

            // StdDraw.pause(1000);
            --trials;

            stats[trials] = 1d * perc.numberOfOpenSites() / (n * n);
        }
    }

    /**
     * Functions for some debugging
     */
    // private static void print(Object o) {
    // System.out.println(o);
    // }

    // private static void print(double[] arr) {

    // for (double e : arr) {
    // System.out.print(e + " ");
    // }
    // System.out.println();
    // }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        if (this.stats.length > 0)
            return StdStats.stddev(this.stats);
        else
            return Double.NaN;

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean() - CONFIDENCE_95 * (stddev() / sqrtT);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return mean() + CONFIDENCE_95 * (stddev() / sqrtT);
    }

    public static void main(String[] args) {

        // Stopwatch stopwatch = new Stopwatch();

        // turn on animation mode
        // StdDraw.enableDoubleBuffering();

        /*
         * This piece of code is used to run #PercolationStats using `ctrl+shift+b`
         * which automatically reads input from inputf.in file in root directory
         */
        /*
         * Scanner sc = new Scanner(System.in);
         * int _n_ = sc.nextInt();
         * sc.nextLine();
         * int trials = sc.nextInt();
         * PercolationStats percolationStats = new PercolationStats(_n_, 1);
         */

        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        // Percolation[] percolationObjects = new Percolation[percolationStats.trials];

        // for (int i = 0; i < percolationStats.trials; i++) {
        // percolationObjects[i] = new Percolation(percolationStats.n);
        // }

        // int n = percolationStats.n;

        // int totalSites = n * n;

        // while (percolationStats.trials > 0) {

        // // Using Quick Find datastructure
        // // Percolation_QuickFindUF perc = new Percolation_QuickFindUF(_n_);

        // // Using weighted Quick union
        // Percolation perc = percolationObjects[percolationStats.trials - 1];

        // // Using weighted Quick union + path compression
        // // PercolationWeightedPathCompression perc = new
        // // PercolationWeightedPathCompression(_n_);

        // // PercolationVisualizer.draw(perc, _n_);
        // // Animation for the open sites
        // // StdDraw.show();
        // // StdDraw.pause(DELAY);

        // while (!perc.percolates()) {

        // // randomly opening a site using row and col
        // int row = StdRandom.uniformInt(1, n + 1);
        // int col = StdRandom.uniformInt(1, n + 1);

        // perc.open(row, col);

        // // Automated testing
        // // perc.open(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));

        // // PercolationVisualizer.draw(perc, _n_);
        // // StdDraw.show();
        // // StdDraw.pause(DELAY);
        // }

        // // StdDraw.pause(1000);
        // --percolationStats.trials;

        // percolationStats.stats[percolationStats.trials] = 1d *
        // perc.numberOfOpenSites() / totalSites;
        // }

        // print("-------------------stats-------------");

        System.out.println(String.format(
                "mean                    = %s\nstddev                  = %s\n95%% confidence interval = [%s, %s]",
                percolationStats.mean(), percolationStats.stddev(),
                percolationStats.confidenceLo(), percolationStats.confidenceHi()));

        // print("ElaspedTime: %s".formatted(stopwatch.elapsedTime()));
        // sc.close();

    }
}
