import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test class for StringMethods.
 */
public class StringMethodsTest {

    private NCString empty, a, aa, aba, abba, ab, aabb, A, Aa,  AA, ABA, AABB, ABBA, aba_aba, nathan;

    @Before
    public void setupFixtures(){
        empty = new NCString("");
        a = new NCString("a");
        aa = new NCString("aa");
        aba = new NCString("aba");
        aabb = new NCString("aabb");
        abba = new NCString("abba");
        Aa = new NCString("Aa");
        ab = new NCString("abba");
        ab = new NCString("aabb");
        A = new NCString("A");
        AA = new NCString("AA");
        ABA = new NCString("ABA");
        AABB = new NCString("AABB");
        ABBA = new NCString("ABBA");
        aba_aba = new NCString("aba aba");
        nathan = new NCString("nathan");
    }

    /**
     * Tests whether the areEquals() method works.
     */
    @Test
    public void testAreEquals(){
        // ARE EQUAL
        assertTrue(StringMethods.areEqual(empty,empty));
        assertTrue(StringMethods.areEqual(a,new NCString("a")));
        assertTrue(StringMethods.areEqual(aa,new NCString("aa")));
        assertTrue(StringMethods.areEqual(aba,new NCString("aba")));
        assertTrue(StringMethods.areEqual(aabb,new NCString("aabb")));
        assertTrue(StringMethods.areEqual(nathan,new NCString("nathan")));

        // ARE NOT EQUAL
        assertFalse(StringMethods.areEqual(empty,a));
        assertFalse(StringMethods.areEqual(empty,A));
        assertFalse(StringMethods.areEqual(a,A));
        assertFalse(StringMethods.areEqual(a,aa));
        assertFalse(StringMethods.areEqual(aa,Aa));
        assertFalse(StringMethods.areEqual(Aa,aa));
        assertFalse(StringMethods.areEqual(a,empty));
        assertFalse(StringMethods.areEqual(empty,a));
        assertFalse(StringMethods.areEqual(aabb,AABB));
        assertFalse(StringMethods.areEqual(aba,aa));
    }

    /**
     * Tests whether the isPalindrome() method works.
     */
    @Test
    public void testIsPalindrome(){
        // ARE PALINDROMES
        assertTrue(StringMethods.isPalindrome(empty));
        assertTrue(StringMethods.isPalindrome(a));
        assertTrue(StringMethods.isPalindrome(aa));
        assertTrue(StringMethods.isPalindrome(aba));
        assertTrue(StringMethods.isPalindrome(AA));
        assertTrue(StringMethods.isPalindrome(abba));
        assertTrue(StringMethods.isPalindrome(ABA));
        assertTrue(StringMethods.isPalindrome(ABBA));
        assertTrue(StringMethods.isPalindrome(aba_aba));

        // ARE NOT PALINDROMES
        assertFalse(StringMethods.isPalindrome(aabb));
        assertFalse(StringMethods.isPalindrome(ab));
        assertFalse(StringMethods.isPalindrome(nathan));
        assertFalse(StringMethods.isPalindrome(Aa));
    }

    @Test
    public void testIsEvenOdd(){
        //Even numbers
        assertTrue(StringMethods.isEven(12));
        assertTrue(StringMethods.isEven(2));
        assertFalse(StringMethods.isOdd(2));
        assertFalse(StringMethods.isOdd(14));

        //Odd Numbers
        assertFalse(StringMethods.isEven(1));
        assertTrue(StringMethods.isOdd(11));
        assertFalse(StringMethods.isEven(13));

        //number out of range

    }

    @Test
    public void testMultiply(){
        assertEquals(0, StringMethods.multiply(0, 5));
        assertEquals(0, StringMethods.multiply(5, 0));
        assertEquals(20, StringMethods.multiply(4,5));
        assertEquals(4, StringMethods.multiply(4,1));
        assertEquals(121, StringMethods.multiply(11,11));
        assertEquals(-60, StringMethods.multiply(-10, 6));
        assertEquals(-60, StringMethods.multiply(10, -6));
        assertEquals(60, StringMethods.multiply(-10, -6));

    }

    @Test
    public void testCountCharacter(){
        assertEquals(2, StringMethods.countCharacter(nathan, 'a') );
        assertEquals(1, StringMethods.countCharacter(nathan, 't') );
        assertEquals(0, StringMethods.countCharacter(ABBA, 'a') );
        assertEquals(1, StringMethods.countCharacter(aba_aba, ' ') );
        assertEquals(0, StringMethods.countCharacter(empty, 'a') );
        assertEquals(1, StringMethods.countCharacter(a, 'a') );
        assertEquals(2, StringMethods.countCharacter(aa, 'a') );

    }

}
