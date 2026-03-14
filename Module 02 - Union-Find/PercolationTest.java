import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Test client for the {@link Percolation} model.
 *
 * <p>Reads an n-by-n grid size and a sequence of (row, col) site-open commands
 * from standard input, opens each site one at a time, and draws the resulting
 * percolation grid after every step using {@link StdDraw} animation.
 *
 * <p>After each site is opened the client prints whether that site is
 * full (connected to the top row). Total elapsed time is printed at the end.
 *
 * <p><b>Input format:</b><br>
 * Line 1 – integer {@code n} (grid dimension)<br>
 * Remaining lines – pairs of integers {@code row col} (1-indexed)
 *
 * <p><b>Usage:</b>
 * <pre>
 *   java PercolationTest &lt; input.txt
 * </pre>
 *
 * @see Percolation
 * @see PercolationVisualizer
 * @see PercolationStats
 */
public class PercolationTest extends PercolationVisualizer {

    /**
     * Entry point for the test client.
     *
     * <p>Reads grid input from standard input, animates each union-find step,
     * and reports elapsed wall-clock time upon completion.
     *
     * @param args command-line arguments (not used; input is read from stdin)
     */
    public static void main(String[] args) {

        /* Start wall-clock timer to measure total execution time. */
        Stopwatch stopwatch = new Stopwatch();

        /* Wrap System.in with algs4's In so we can use readInt() conveniently. */
        In in = new In(new Scanner(System.in));

        // First integer in the stream is the grid dimension n (n-by-n grid).
        int n = in.readInt();

        /* Enable double-buffering so intermediate draw calls don't flicker
         * on screen; StdDraw.show() commits the frame atomically. */
        StdDraw.enableDoubleBuffering();

        /*
         * ---------------------------------------------------------------
         * Alternative entry point (commented out):
         * The block below can be used to run PercolationStats directly via
         * `ctrl+shift+b`, which feeds inputf.in from the project root.
         * ---------------------------------------------------------------
         *
         * Scanner sc = new Scanner(System.in);
         * int _n_ = sc.nextInt();
         * sc.nextLine();
         * int trials = sc.nextInt();
         * PercolationStats percolationStats = new PercolationStats(_n_, 1);
         */

        // Initialise the percolation model and render the initial (all-closed) grid.
        Percolation perc = new Percolation(n);
        draw(perc, n);
        StdDraw.show();
        StdDraw.pause(DELAY); // brief pause so the initial state is visible

        /* Main loop: read (row, col) pairs and open sites one by one. */
        while (!in.isEmpty()) {

            int i = in.readInt();  // row index (1-indexed)
            int j = in.readInt();  // column index (1-indexed)

            // Open the site at (i, j) in the percolation model.
            perc.open(i, j);

            // Print whether the newly opened site is full (connected to top row).
            System.out.println(perc.isFull(i, j));

            /* Redraw the grid and advance the animation frame. */
            draw(perc, n);
            StdDraw.show();
            StdDraw.pause(DELAY);
        }

        // Report total elapsed time after all sites have been processed.
        System.out.println("ElapsedTime: %s".formatted(stopwatch.elapsedTime()));
    }
}
