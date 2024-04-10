import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Test file for the Maximum Subsequence Sum
 * @author Evan Gary
 */
public class MSTest {

    int[] seq1 = {1, 2, -1, 4, -5, -6, 1, -2};
    int[] seq2 = {1, 2, 3, 4, -100, 4, 7};
    int[] seq3 = {0, -1, -3, 5, -6, -10};
    int[] seq4 = {-2, -3, -4, -5, -6};


    @Test
    public void maxSubSumTest(){
        assertEquals(0, Homework1.maxSubSum4Mod(seq1).getFirst());
        assertEquals(3, Homework1.maxSubSum4Mod(seq1).getLast());
        assertEquals(6, Homework1.maxSubSum4Mod(seq1).getSum());
        assertEquals(5, Homework1.maxSubSum4Mod(seq2).getFirst());
        assertEquals(6, Homework1.maxSubSum4Mod(seq2).getLast());
        assertEquals(11, Homework1.maxSubSum4Mod(seq2).getSum());
        assertEquals(3, Homework1.maxSubSum4Mod(seq3).getFirst());
        assertEquals(3, Homework1.maxSubSum4Mod(seq3).getLast());
        assertEquals(5, Homework1.maxSubSum4Mod(seq3).getSum());
        assertEquals(0, Homework1.maxSubSum4Mod(seq4).getFirst());
        assertEquals(0, Homework1.maxSubSum4Mod(seq4).getLast());
        assertEquals(0, Homework1.maxSubSum4Mod(seq4).getSum());

    }

}
