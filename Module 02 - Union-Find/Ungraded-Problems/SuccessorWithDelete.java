import java.util.Scanner;

import edu.princeton.cs.algs4.In;

/**
 * Question:
 * This Successor with delete. Given a set of n integers S = {0, 1, ... , n - 1}
 * and a sequence of requests of the following form:
 * 
 * Remove x from S
 * 
 * Find the successor of x: the smallest y in S such that y ≥ x.
 * 
 * design a data type so that all operations (except construction) take
 * logarithmic time or better in the worst
 * case.
 * 
 * Solution: https://stackoverflow.com/a/52564894/8501093
 */

public class SuccessorWithDelete {

    private int[] actualList;

    private int[] size;

    private int[] parent;

    private int gridLength;

    public SuccessorWithDelete(int n) {

        this.gridLength = n;

        actualList = new int[gridLength];

        size = new int[gridLength];

        parent = new int[gridLength];

        for (int i = 0; i < gridLength; i++) {

            actualList[i] = i;

            size[i] = 1;

            parent[i] = i;
        }

    }

    private static void print(int[] arr) {

        for (int e : arr) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {

            parent[p] = parent[parent[p]];

            p = parent[p];
        }
        return p;
    }

    public void union(int p, int q) {

        validate(q);

        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;

        // make smaller root point to larger one
        if (size[rootP] <= size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
            actualList[rootP] = rootQ;
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
            actualList[rootP] = rootQ;
        }

    }

    public void delete(int i) {

        validate(i);

        union(i, i + 1);
    }

    public int successor(int x) {

        return actualList[find(x + 1)];
    }

    public static void main(String[] args) {

        In in = new In(new Scanner(System.in));
        int n = in.readInt();
        SuccessorWithDelete successorWithDelete = new SuccessorWithDelete(n);

        successorWithDelete.delete(4);

        successorWithDelete.delete(5);

        successorWithDelete.delete(7);

        successorWithDelete.delete(6);

        System.out.println(successorWithDelete.successor(8));

        System.out.println(successorWithDelete.successor(7));

        print(successorWithDelete.actualList);
        print(successorWithDelete.parent);
    }
}
