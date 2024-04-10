/**
 * The main part of HW4. Implement both of these methods.
 *
 * @author Evan Gary
 * @version 22 Feb 2017
 */
public class StringMethods {

    /** No reason to instantiate this class, so the constructor is private. */
    private StringMethods(){ }

    /*
     * TODO: Fill in the areEqual and isPalindrome methods below with recursive implementations.
     *
     * These methods are recursive, so they either solve the problem right away (the base case),
     * or reduce the input to a simpler problem before calling themselves again (the recursive case).
     */

    ///////////////////////////////////////////////////////////
    // PART ONE
    ///////////////////////////////////////////////////////////

    /**
     * Returns whether two NCStrings are equal. The comparison respects case (so 'test' != 'Test').
     *
     * @return Whether two NCStrings are equal.
     */
    public static boolean areEqual(final NCString a, final NCString b){
        if (a.getLength() != b.getLength()) {
			return false;
		}
		
		if (a.getLength() == 0){
			return true;
		}
		
		else if (a.getLength() == 1){
			return (a.getFirstChar() == b.getLastChar());
		}
		
		else if (a.getLength() > 1){
			if ((a.getFirstChar() == b.getFirstChar()) && (a.getLastChar() == b.getLastChar())){

				//System.out.println(a + "   "  + b);
				return areEqual(a.getMiddleChars(), b.getMiddleChars());
			}
			else {
				return false;
			}
		}
		return false;
    }

    ///////////////////////////////////////////////////////////
    // PART TWO
    ///////////////////////////////////////////////////////////

    /**
     * Returns whether two NCStrings are palindromes. A String is a palindrome if it reads the
     * same forwards and backwards, respecting case and whitespace. E.g.:
     *
     * "racecar"
     * "level"
     * "a"
     * "aa"
     * "mom"
     * "radar radar"
     * ""           <-- the empty string.
     *
     * The following would not be considered palindromes:
     *
     * "example"
     * "top spot"   <-- because of the space
     * "Mom"        <-- because of the capitalization
     *
     * @param s The NCString to examine. Can be assumed to be non-null.
     * @return Whether the argument is a palindrome, as defined above.
     */
    public static boolean isPalindrome(final NCString s)
	{
        if (s.getLength() == 0){
        	return true;
		}

		else if (s.getLength() == 1){
        	return true;
		}

		else if (s.getLength() > 1){
			if (s.getFirstChar() == s.getLastChar()){
				//System.out.println(s);

				return isPalindrome(s.getMiddleChars());
			}
			else {
				return false;
			}
		}
		return false;
    }

	/**
	 * Performs multiplication on two integer values without the use of the * operator.
	 *
	 * @param a The first integer to be multiplied.
	 * @param b The second integer to be multiplied.
	 * @return The result of the multiplication operation.
	 */
    public static int multiply(int a, int b){
    	//System.out.println(a + " " + b);
    	if (b == 0){
    		return 0;
		}

		else if (b == 1){
    		return a;
		}

		else if (b == -1){
			return -a;
		}

		else if (b > 1){
			return a + multiply(a, b-1);
		}

		else if (b < -1){
			return -a + multiply(a, b+1);
		}
		else {
		 	return 0;
		}
	}

	/**
	 * Counts the number of a certain character in a specified string.
	 *
	 * @param a The string to be examined.
	 * @param b The character to be counted.
	 * @return The integer representing the number of the specified character in the string.
	 */
	public static int countCharacter(NCString a, char b){
    	int count = 0;
    	if (a.getLength() == 0){
    		return 0;
		}

    	if (a.getFirstChar() == b) {
    		count++;
		}
		if (a.getLastChar() == b && a.getLength() >1){
    		count++;
		}
		if (a.getLength () == 1 || a.getLength() == 2){
			return count;
		}
		if (a.getLength() > 2){
			return count + countCharacter(a.getMiddleChars(), b);
		}
		return 0;
	}

	/**
	 * Returns whether a positive interger is even or odd.
	 * Indirect recursive implementation.
	 *
	 * @param n value to be evaluated
	 * @return true for even, false for odd
	 */
	public static boolean isEven(int n){
		if (n == 1){
			return false;
		}

		if (n == 2){
			return true;
		}
		else if (n > 2){
			return isOdd(n-1);
		}
		else {
			throw new IllegalArgumentException("n must be positive");
		}


	}

	/**
	 * Returns whether a positive interger is even or odd.
	 * Indirect recursive implementation.
	 *
	 * @param n value to be evaluated
	 * @return true for odd, false for even
	 */
	public static boolean isOdd(int n){
		if (n == 1){
			return true;
		}

		if (n ==2){
			return false;
		}
		else if (n > 2){
			return isEven(n-1);
		}
		else {
			throw new IllegalArgumentException("n must be positive");
		}
	}

	public static int Fibonacci(int n){

		int m;
		if (n == 0){
			m = 0;
		}
		else if (n == 1){
			m = 1;
		}
		else {
			 m = Fibonacci(n-1) + Fibonacci(n - 2);
		}
		return m;
	}

	public static void main(String[] args){
		System.out.println(Fibonacci(8));
	}
}
