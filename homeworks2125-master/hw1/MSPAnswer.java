import java.util.Arrays;

/**
 * MSPAnswer object for Homework 1
 * @author Evan Gary
 * @version 12 June 2017
 */

public class MSPAnswer {
    private int first;
    private int last;
    private int[] data;
    private int sum;

    /**
     * Constructor for the object. Sets all instance variables.
     * @param first
     * @param last
     * @param sum
     * @param data
     */
    public MSPAnswer(int first, int last, int sum, int[] data){
        this.first = first;
        this.last = last;
        this.sum = sum;
        this.data = data;
    }

    public int getFirst(){
        return first;
    }

    public int getLast(){
        return last;
    }

    public int getSum(){
        return sum;
    }

    /**
     * Overriden ToString method to return the indices, sum, and data set
     * @return The string containing the object information
     */
    @Override
    public String toString(){
        if (sum > 0) {
            return String.format("First index: %d %nSecond index: %d%nSum: %d%nData set:%n%s", first, last, sum, Arrays.toString(data));
        }
        else {
            return "Sum: 0. The maximum subsequence is the null sequence.";
        }
    }
}
