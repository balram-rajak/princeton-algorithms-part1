
// Using Row major mapping for 2D -> 1D Array mapping
public class Percolation {

    public int n_grid[];

    private boolean site_state[];

    private int sz[];

    private int openSites = 0;

    private int n;

    private int grids;

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

        return i > grids - 1;
    }

    private boolean isValidSite(int i) {

        return i > -1 && i < grids - 1;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n ≤ 0");
        }

        this.n = n;

        this.grids = n * n;
        n_grid = new int[grids + 2];

        this.site_state= new boolean[grids];

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

    private int root(int i) {

        if (i < 0 && i > grids - 1)
            return -1;

        int temp = n_grid[i];
        while (i != n_grid[i]) {

            n_grid[i] = n_grid[n_grid[i]];

            i = n_grid[i];

        }

        return i;
    }

    private void union(int p, int q) {

        // site p is assumed to be valid and open
        if (isValidSite(q) && site_state[q]) {
            int i = root(p);
            int j = root(q);

            // print(String.format("p: %s -> %s | q: %s -> %s",p,i,q,j));
            if (i == j)
                return;

            // if root of q is a virtual site than that will become the root
            if (isVirtualSite(j)  || (!isVirtualSite(i) && sz[j] > sz[i])) {

                n_grid[i] = j;
                sz[j] += sz[i];
            }
            // if p belongs to virtual root or size of p > size of q
            else {

                n_grid[j] = i;
                sz[i] += sz[j];
            }
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
            n_grid[i] = grids;
        } else if (i > grids - n - 1) {
            n_grid[i] = grids + 1;
        }

        // print(String.format("i: %s -> %s",i,n_grid[i]));
        // connect with near by sites

        // (col-1, row = i-1) | left element
        // (col+1, row) = i+1 | Right element
        // (col-1, row) = i-n | up element
        // (col-1, row) = i+n | down element

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

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        int i = mapIndex(row,col);

        if (!isValidSite(i)) {
            throw new IllegalArgumentException("i out of range");
        }

        if (!site_state[i])
            return false;

        for (int site : getNearbySites(i)) {

            // check the site is valid, open & root is virtual site
            if (isValidSite(site) && site_state[site] && isVirtualSite(n_grid[site]))
                return true;
        }

        return isVirtualSite(n_grid[i]);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {

        int length = n_grid.length;
        // is top virtual site connected to bottom
        return root(length - 1) == root(length - 2);
    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation p = new Percolation(3);

        // p.print(p.n_grid);
        p.open(2,2);
        print(p.isFull(3,2));
        // p.print(p.n_grid);

        p.open(1,2);
        print(p.isFull(1,2));
        print("Open Sites:"+p.openSites);
        // p.print(p.n_grid);

        p.open(3,2);
        p.print(p.n_grid);
        print(p.percolates());

    }

}
