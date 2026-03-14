import edu.princeton.cs.algs4.In;

/**
 * Social network connectivity. Given a social network containing n members and a log file containing 
 * m timestamps at which times pairs of members formed friendships, 
 * design an algorithm to determine the earliest time at which all members are connected 
 * (i.e., every member is a friend of a friend of a friend ... of a friend). 
 * Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. 
 * The running time of your algorithm should be mlogn or better and use extra space proportional to n.
 * 
 * Check the assignment at https://www.coursera.org/learn/algorithms-part1/assignment-submission/SCxqJ/interview-questions-union-find-ungraded/view-feedback
 * Here in code n = sqrt(n)
 */
public class SocialNetworkConnectivity {

    int n;

    private int grid[];

    private int sz[];

    private int gridSize;

    // private int getIndex(int row, int col){

    //     return --row * n + (--col);
    // }
    public SocialNetworkConnectivity(int n) {

        this.n = n;

        this.gridSize = n * n;

        grid = new int[gridSize];

        sz = new int[gridSize];

        for (int i = 0; i < gridSize; ++i) {

            grid[i] = i;

            sz[i] = 1;
        }
    }

    private boolean isValidSite(int i) {

        return i > -1 && i < gridSize;
    }

    public int root(int i) {

        while (i != grid[i]) {

            i = grid[grid[i]];
        }

        return i;
    }

    public boolean union(int p, int q) {

        if (!(isValidSite(p) || isValidSite(q))) {

            throw new IllegalArgumentException("index out of range");
        }

        int i = root(p);
        int j = root(q);

        if (sz[i] > sz[j]) {

            grid[j] = i;

            sz[i] += sz[j];
        } else {

            grid[i] = j;

            sz[j] += sz[i];
        }

        if (sz[i] == gridSize || sz[j] == gridSize)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {

        In in = new In(args[0]); // input file
        int n = in.readInt(); // n-by-n percolation system

        SocialNetworkConnectivity perc = new SocialNetworkConnectivity(n);

        int timeIndex =0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();

            timeIndex++;

            if(perc.union(i, j)){
                System.out.println(timeIndex);
                System.exit(0);
            }
        }

    }
}
