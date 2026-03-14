import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Selects a word/ string randomly using bernoulli distribution where the 
 * probablity for a word is represented using 1/i, i is the index of the word
 * in the input
 */
public class RandomWord {

    public RandomWord() {
    }

    public static void main(String[] args) {

        int i = 0;
        String win = "";

        do {
            String s = StdIn.readString();
            i++;
            if (StdRandom.bernoulli(1.0 / i)) {
                win = s;
            }
        } while (!StdIn.isEmpty());
        System.out.println(win);

    }
}
