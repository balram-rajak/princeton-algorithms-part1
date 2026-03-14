import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This percolation is prone to backwash, therefore a new implementation 
 * is created that is implemented using byte[] array based on status concept and neglecting 
 * virtual sites altogether
 */
// Using Row major mapping for 2D -> 1D Array mapping
public class PercolationNormal {

    private final WeightedQuickUnionUF wqu;

    private final boolean[] siteState;

    private int openSites = 0;

    private final int n;

    private final int grids;

    private final int gridLength;

    // creates n-by-n grid, with all sites initially blocked
    public PercolationNormal(final int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n ≤ 0");
        }

        this.n = n;

        this.grids = n * n;

        this.gridLength = grids + 1;
        wqu = new WeightedQuickUnionUF(gridLength);

        this.siteState = new boolean[grids];

        for (int i = 0; i < grids; i++) {

            siteState[i] = false;
        }
    }

    private int mapIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private static void print(final Object obj) {
        System.out.println(obj);
    }

    // private int[] getNearbySites(int i) {
    // int[] sites = { i - 1, i + 1, i - n, i + n };
    // return sites;
    // }

    private boolean isValidSite(final int i) {

        return i > -1 && i < grids;
    }

    private boolean isValidSite(int row, int col) {

        return col > 0 && col <= n && row > 0 && row <= n;
    }

    // private int root(int i) {

    // while (i != wQU.find(i)) {

    // i = wQU.find(i);

    // }

    // return i;
    // }

    private boolean isConnected(final int p, final int q) {
        return wqu.find(p) == wqu.find(q);
    }

    private void union(final int pRow, final int pCol, final int qRow, final int qCol) {

        final int i = mapIndex(pRow, pCol);

        if (isValidSite(qRow, qCol)) {
            final int site = mapIndex(qRow, qCol);
            union(i, site);
        }

    }

    private void union(final int p, final int q) {

        // site p is assumed to be valid and open
        if (siteState[q]) {

            wqu.union(p, q);

        }
    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {

        // i represents site of indexed in n_grids
        final int i = mapIndex(row, col);

        if (!isValidSite(row, col)) {
            throw new IllegalArgumentException(String.format("index %s out of range", i));
        }

        // if site is already open
        if (siteState[i])
            return;
        else // open the site i
            siteState[i] = true;

        if (row == 1) {
            wqu.union(i, grids);
        }

        if (row == n) {
            wqu.union(i, grids + 1);
        }

        /*************************************
         * connect with near by sites
         * (col-1, row = i-1) | left element, except for left border elements
         * (col+1, row) = i+1 | Right element, except for right border elements
         * (col-1, row) = i-n | up element
         * (col-1, row) = i+n | down element
         *************************************/
        // int site;

        union(row, col, row, col - 1);

        union(row, col, row, col + 1);

        union(row, col, row - 1, col);

        union(row, col, row + 1, col);

        ++openSites;

    }

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {

        final int i = mapIndex(row, col);
        if (!(isValidSite(row, col))) {
            throw new IllegalArgumentException(String.format("index %s out of range", i));
        }

        return siteState[i];
    }

    // is the site is open and near top/bottom site?
    public boolean isFull(final int row, final int col) {

        final int i = mapIndex(row, col);

        if (!isValidSite(row, col)) {
            throw new IllegalArgumentException(String.format("index %s out of range", i));
        }

        // If site is not open
        if (!siteState[i])
            return false;

        // If the nearby sites are top or bottom sites (Root of Top/Bottom site is
        // a virtual site
        int site;

        site = mapIndex(row, col - 1);
        // check the site is valid, open & root is virtual site
        if (isValidSite(row, col - 1) && siteState[site] && isConnected(site, grids))
            return true;

        site = mapIndex(row, col + 1);
        // check the site is valid, open & root is virtual site
        if (isValidSite(row, col + 1) && siteState[site] && isConnected(site, grids))
            return true;

        site = i - n;
        // check the site is valid, open & root is virtual site
        if (isValidSite(site) && siteState[site] && isConnected(site, grids))
            return true;

        site = i + n;
        // check the site is valid, open & root is virtual site
        if (isValidSite(site) && siteState[site] && isConnected(site, grids))
            return true;

        // check if i and any virtual site exist in same node
        return isConnected(i, grids);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {

        final int length = gridLength;
        // is top virtual site connected to bottom\
        return isConnected(length - 1, length - 2);
    }

    public static void main(final String[] args) {

        final In in = new In(args[0]); // input file
        final int n = in.readInt(); // n-by-n percolation system

        final Percolation p3 = new Percolation(n);

        while (!in.isEmpty()) {

            final int row = in.readInt();
            final int col = in.readInt();
            p3.open(row, col);
            print(String.format("%s, %s is Full:%s | percolates: %s", row, col, p3.isFull(row, col), p3.percolates()));
        }

        final Percolation p4 = new Percolation(4);

        p4.open(2, 2);
        p4.open(2, 3);
        p4.open(3, 2);
        p4.open(3, 3);

        p4.open(1, 1);
        p4.open(2, 1);
        p4.open(4, 3);

        // p4.print(p4.n_grid);
        print(p4.percolates());
    }

}
