public class PercolationWeightedPathCompression {
    
    private int n_grid[];

    private boolean site_state[];

    private int sz[];

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

        // Virtual site are either the top, bottom or the last 2 extra index of grid representing virtual sites
        return i < n ^ i > grids - n- 1;
    }

    private boolean isValidSite(int i) {

        return i > -1 && i < grids;
    }

    // creates n-by-n grid, with all sites initially blocked
    public PercolationWeightedPathCompression(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n ≤ 0");
        }

        this.n = n;

        this.grids = n * n;
        
        this.grid_length = grids+2;
        n_grid = new int[grids + 2];

        this.site_state= new boolean[grids];

        // Commenting my custom solution for weighted quick union
        sz = new int[grids+2];

        // initializing virtual sites
        n_grid[grids + 1] = grids + 1;
        n_grid[grids] = grids;

        for (int i = 0; i < grids; i++) {

            n_grid[i] = i;
            site_state[i] = false;
            sz[i] = 1;
        }
    }

    // My custom function: Find root of a site
    private int root(int i) {

        if (i < 0 && i > grids - 1)
            return -1;

        // int temp = n_grid[i];

        while (i != n_grid[i]) {
        // while (i != wQU.find(i)){
            // halve path compression
            n_grid[i] = n_grid[n_grid[i]];

            // i = wQU.find(i);
            i = n_grid[i];
        }

        // Complete path compression
        /**
        int root = i;

        i = temp;

        while (i != n_grid[i]) {

            temp = n_grid[i];

            n_grid[i] = root;

            i = temp;
        }
        */

        return i;
    }

    private void union(int p, int q) {

        // site p is assumed to be valid and open
        if (isValidSite(q) && site_state[q]) {

            
            // print(String.format("p: %s -> %s | q: %s -> %s",p,i,q,j));
            int i = root(p);
            int j = root(q);
            if (i == j)
                return;

            if ( sz[j] > sz[i]) {

                n_grid[i] = j;
                sz[j] += sz[i];

            }
            else {

                n_grid[j] = i;
                sz[i] += sz[j];

            }

            // Using university library class to suffice the specification
            // wQU.union(p, q);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        // i represents site of indexed in n_grids
        int i = mapIndex(row,col);

        if (!isValidSite(i)) {
            throw new IllegalArgumentException("index %s out of range".formatted(i));
        }

        // if site is already open
        if (site_state[i])
            return;
        else // open the site i
            site_state[i] = true;

        if (i < n) {
            n_grid[i] = grids;
            // Also using university library class to suffice the specification
            // wQU.union(i, grids);
        } else if (i > grids - n - 1) {
            n_grid[i] = grids + 1;
            // Also using university library class to suffice the specification
            // wQU.union(i, grids+1);
        }

        /*************************************
         * connect with near by sites
         * (col-1, row = i-1) | left element
         * (col+1, row) = i+1 | Right element
         * (col-1, row) = i-n | up element
         * (col-1, row) = i+n | down element
         *************************************/

        for (int site : getNearbySites(i)) {
            union(i, site);

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
            if (isValidSite(site) && site_state[site] && isVirtualSite(root(site)))
                return true;
        }

        return isVirtualSite(root(i));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return openSites;
        // return wQU.count();
    }

    // does the system percolate?
    public boolean percolates() {

        int length = grid_length;
        // is top virtual site connected to bottom
        return root(length - 1) == root(length - 2);

        // Using university library class to suffice the specification
        // return wQU.find(length-1) == wQU.find(length-2);
    }

    public static void main(String[] args) {

        PercolationWeightedPathCompression p3 = new PercolationWeightedPathCompression(3);

        p3.open(2,2);
        print(p3.isFull(3,2));

        p3.open(1,2);
        print(p3.isFull(1,2));
        print("Open Sites:"+p3.openSites);

        p3.open(3,2);
        print(p3.isFull(1,2));
        print(p3.percolates());

        PercolationWeightedPathCompression p4 = new PercolationWeightedPathCompression(4);

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
