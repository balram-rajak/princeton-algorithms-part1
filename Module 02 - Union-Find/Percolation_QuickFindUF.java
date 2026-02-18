
import edu.princeton.cs.algs4.QuickFindUF;

// Using Row major mapping for 2D -> 1D Array mapping
public class Percolation_QuickFindUF {

    private QuickFindUF qf;

    // private int n_grid[];

    private boolean site_state[];

    // private int sz[];

    private int openSites = 0;

    private int n;

    private int grids;

    private int grid_length;

    private void print(int[] arr){

        for(int e : arr){
            System.out.print(e+" ");
        }
        System.out.println();
    }

    private int mapIndex(int row, int col){
        return --row * n + --col;
    }
    
    private static void print(Object o){
        System.out.println(o);
    }

    private int[] getNearbySites(int i) {
        int sites[] = { i - 1, i + 1, i - n, i + n };
        return sites;
    }

    private boolean isVirtualSite(int i) {

        return i < n ^ i > grids - n-1;
    }

    private boolean isValidSite(int i) {

        return i > -1 && i < grids - 1;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation_QuickFindUF(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n ≤ 0");
        }

        this.n = n;

        this.grids = n * n;
        
        this.grid_length = grids+2;
        // university library usage
        qf = new QuickFindUF(grid_length);

        // n_grid = new int[grids + 2];

        this.site_state= new boolean[grids];

        // Commenting my custom solution for weighted quick union
        // sz = new int[grids+2];

        // initializing virtual sites
        // n_grid[grids + 1] = grids + 1;
        // n_grid[grids] = grids;

        for (int i = 0; i < grids; i++) {

            // n_grid[i] = i;
            site_state[i] = false;
            // sz[i] = 1;
        }
    }

    //  My custom function: Find root of a site
    /*
    private int root(int i) {

        if (i < 0 && i > grids - 1)
            return -1;

        // int temp = n_grid[i];

        while (i != qf.find(i)) {

            // halve path compression
            // n_grid[i] = n_grid[n_grid[i]];

            i = qf.find(i);

        }

        // Complete path compression
        int root = i;

        i = temp;

        while (i != n_grid[i]) {

            temp = n_grid[i];

            n_grid[i] = root;

            i = temp;
        }

        return i;
    }

    */
    

    private void union(int p, int q) {

        // site q is assumed to be valid and open
        if (isValidSite(p) && site_state[p]) {

            
            // print(String.format("p: %s -> %s | q: %s -> %s",p,i,q,j));
            /* My custom solution
            int i = root(p);
            int j = root(q);
            if (i == j)
                return;

            if ( sz[j] > sz[i]) {

                n_grid[i] = j;
                sz[j] += sz[i];

            }
            // if p belongs to virtual root or size of p > size of q
            else {

                n_grid[j] = i;
                sz[i] += sz[j];

            }
            */

            // Using university library class to suffice the specification
            qf.union(p, q);

            // print(String.format("p: %s -> %s | q: %s -> %s",p,n_grid[i],q,n_grid[j]));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        // i represents site of indexed in n_grids
        int i = mapIndex(row,col);

        // print(String.format("i: %s -> %s",i,n_grid[i]));
        if (!isValidSite(i)) {
            throw new IllegalArgumentException("i out of range");
        }

        // if site is already open
        if (site_state[i])
            return;
        else // open the site i
            site_state[i] = true;

        if (i < n) {
            // n_grid[i] = grids;
            // Also using university library class to suffice the specification
            qf.union(i, grids);
        } else if (i > grids - n - 1) {
            // n_grid[i] = grids + 1;
            // Also using university library class to suffice the specification
            qf.union(i, grids+1);
        }
        // print(String.format("i: %s -> %s",i,n_grid[i]));
        // connect with near by sites

        // (col-1, row = i-1) | left element
        // (col+1, row) = i+1 | Right element
        // (col-1, row) = i-n | up element
        // (col-1, row) = i+n | down element

        for (int site : getNearbySites(i)) {
            union(site, i);

        }

        ++openSites;

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        int i = mapIndex(row,col);
        if (!isValidSite(i)) {
            throw new IllegalArgumentException("i out of range");
        }

        return site_state[i];
    }

    // is the site is open and near top/bottom site?
    public boolean isFull(int row, int col) {

        int i = mapIndex(row,col);

        if (!isValidSite(i)) {
            throw new IllegalArgumentException("i out of range");
        }

        // If site is open
        if (!site_state[i])
            return false;

        // If the nearby sites are top or bottom sites (Root of Top/Bottom site is a virtual site)
        for (int site : getNearbySites(i)) {

            // check the site is valid, open & root is virtual site
            // if (isValidSite(site) && site_state[site] && isVirtualSite(n_grid[site]))
            if (isValidSite(site) && site_state[site] && isVirtualSite(qf.find(site)))
                return true;
        }

        // return isVirtualSite(n_grid[i]);
        return isVirtualSite(qf.find(i));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        // return openSites;
        return qf.count();
    }

    // does the system percolate?
    public boolean percolates() {

        int length = grid_length;
        // is top virtual site connected to bottom
        // return root(length - 1) == root(length - 2);

        // Also using university library class to suffice the specification
        return qf.find(length-1) == qf.find(length-2);
    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation_QuickFindUF p3 = new Percolation_QuickFindUF(3);

        p3.open(2,2);
        print(p3.isFull(3,2));

        p3.open(1,2);
        // print(p3.root(p3.mapIndex(1, 2)));
        print(p3.isFull(1,2));
        print("Open Sites:"+p3.openSites);

        p3.open(3,2);
        // p3.print(p3.n_grid);
        print(p3.percolates());
        print(p3.isFull(1,2));

        Percolation_QuickFindUF p4 = new Percolation_QuickFindUF(4);

        p4.open(2,2);
        p4.open(2,3);
        p4.open(3,2);
        p4.open(3,3);

        p4.open(1,1);
        p4.open(2,1);
        p4.open(4,3);

        // p4.print(p4.n_grid);
        print(p4.percolates());
    }

}
