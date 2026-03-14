import edu.princeton.cs.algs4.In;
import java.util.Scanner;

/**
 * Union-find with specific canonical element. Add a method find() to the union-find data type so 
 * that find(i) returns the largest element in the connected component containing i. The
 * operations, union(), connected(), and find() should all take logarithmic time or better.
 * 
 * For example, if one of the connected components is {1, 2, 6, 9}, then the find() method should
 * return 9 for each of the four elements in the connected components.
 */

public class UnionFindCanonicalGreatestElement {
    
    private int gridSize;

    private int[] greatestElement;

    private int[] sz;

    private int[] grid;

    public UnionFindCanonicalGreatestElement(int n){
        
        // if input maxelement = n
        gridSize = n+1;

        grid = new int[gridSize];

        greatestElement = new int[gridSize];

        sz = new int[gridSize];

        for(int i=0; i<gridSize; i++){

            greatestElement[i] = i;
            
            sz[i] = i;
        }
        
    }

    public static void print(int[] arr){

        for(var e : arr){
            System.out.print(e);
        }

        System.out.println();
    }

    private int max(int x, int y){

        return x > y ? x : y;
    }

    public int root(int i) {

        while (i != grid[i]) {

            i = grid[grid[i]];
        }

        return i;
    }

    private boolean isValidSite(int i) {

        return i > -1 && i < gridSize;
    }

    public int find(int i){

        return greatestElement[root(i)];
    }

    public void union(int p, int q){

        if(!(isValidSite(p) || isValidSite(q))){
            throw new IllegalArgumentException("index out of range");
        }

        int i = root(p);
        int j = root(q);

        if(sz[i] > sz[j]){
            
            greatestElement[i] = max(greatestElement[i], q);

            grid[j] = i;
            sz[i] += sz[j];

        }
        else{

            greatestElement[j] = max(p,greatestElement[j]);

            grid[i] = j;
            sz[j] += sz[i];
        }

    }

    public static void main(String[] args){

        In in = new In(new Scanner(System.in)); // input file
        int n = in.readInt(); // n-by-n percolation system

        UnionFindCanonicalGreatestElement perc = new UnionFindCanonicalGreatestElement(n);

        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();

            perc.union(i, j);

            System.out.println(String.format("greates Element in i: %s",perc.find(i)));
            System.out.println(String.format("greates Element in j: %s",perc.find(j)));
        }

        print(perc.grid);
    }
    
}
