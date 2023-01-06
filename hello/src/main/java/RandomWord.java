import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @author Srikanth
 * @since 01/01/2023
 */
public class RandomWord {

    public static void main(String[] args) {

        String champion = "";

        for (double i = 1; !StdIn.isEmpty(); i++) {
            String input = StdIn.readString();
            if (StdRandom.bernoulli(1 / i)) {
                champion = input;
            }
        }
        StdOut.println(champion);
    }
}
