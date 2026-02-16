import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    
    public RandomWord() {
    }
    
    public static void main(String[] args) {
        
        int i=0;
        String win;
        String s = StdIn.readString();
        win = s;
        while(!StdIn.isEmpty()){
            i++;
            if(StdRandom.bernoulli(1.0/i)){
                win = s;
            }
            s = StdIn.readString();
        }
        System.out.println(win);

    }
}
