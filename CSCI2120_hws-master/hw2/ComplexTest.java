import org.junit.Before; 
import org.junit.Test; 
import static org.junit.Assert.*;

/**
 * This is the JUnit test class for the ComplexNumber and RealNumber classes.
 *
 * @author Evan Gary
 */
public class ComplexTest{
    ComplexNumber cNum1 = new ComplexNumber(1.0,2.0);
    ComplexNumber cNum2 = new ComplexNumber(3.0,4.0);// try with floats
    ComplexNumber cNum3 = new ComplexNumber(-1.0,-2.0);
    ComplexNumber cNum4 = new ComplexNumber(3.0,0.0);
    ComplexNumber cNum5 = new ComplexNumber(0.0,3.0);
    ComplexNumber cNum6 = new ComplexNumber(1.0,2.0); // should be the same as cNum1
    ComplexNumber cNum7 = new ComplexNumber(-5.0,-6.0);
    ComplexNumber cNum8 = new ComplexNumber(0.5,-1.5); //some decimal values
    ComplexNumber cNum0 = new ComplexNumber(0.0,0.0);
    RealNumber rNum1 = new RealNumber(2.0);
    RealNumber rNum2 = new RealNumber(4.0);

    @Before
    public void initialNumbers(){
        ComplexNumber cNum1 = new ComplexNumber(1.0,2.0);
        ComplexNumber cNum2 = new ComplexNumber(3.0,4.0);// try with floats
        ComplexNumber cNum3 = new ComplexNumber(-1.0,-2.0);
        ComplexNumber cNum4 = new ComplexNumber(3.0,0.0);
        ComplexNumber cNum5 = new ComplexNumber(0.0,3.0);
        ComplexNumber cNum6 = new ComplexNumber(1.0,2.0); // should be the same as cNum1 
        ComplexNumber cNum7 = new ComplexNumber(-5.0,-6.0);
        ComplexNumber cNum8 = new ComplexNumber(0.5,-1.5); //some decimal values
        ComplexNumber cNum0 = new ComplexNumber(0.0,0.0);
        RealNumber rNum1 = new RealNumber(2.0);
        RealNumber rNum2 = new RealNumber(4.0);
    }

    /**
     * This is the test for the constructor. It only tests whether the input values are appropriately assigned.
     */
    @Test
    public void testConstructor(){
        assertEquals(1.0, cNum1.getReal(), 0.001);
        assertEquals(2.0, cNum1.getImag(), 0.001);
    }

    /**
     * This is the test for toString. It tests whether the string is formatted correctly.
     */
    @Test
    public void testToString(){
        
        assertEquals("1.000 + 2.000i", cNum1.toString()); //standard test
        assertEquals("0.000 + 0.000i", cNum0.toString()); //try zero
        assertEquals("-1.000 + -2.000i", cNum3.toString()); //try negatives
    }

    /**
     * This is the test for Equals. It tests to see if the method can correctly compare two complex number objects.
     */
    @Test
    public void testEquals(){
        String testString = "abc";
        assertEquals(false, cNum1.equals(testString));
        assertEquals(true, cNum1.equals(new ComplexNumber(1,2)));
        assertEquals(true, cNum2.equals(new ComplexNumber(3.0,4.0))); //try comparing with a new number
        assertEquals(true, cNum1.equals(cNum6)); //try comparing with an existing number
        assertEquals(false, cNum1.equals(cNum2)); //try a false comparison
    }

    /**
     * This is the test for the add method. It tests the addition of complex numbers. Both toString output and actual values are tested.
     */
    @Test
    public void testAdd(){
        assertEquals("4.000 + 6.000i", cNum1.add(cNum2).toString()); //standard addition
        assertEquals("1.000 + 2.000i", cNum1.add(cNum0).toString()); //try adding zero
        assertEquals("0.000 + 0.000i", cNum1.add(cNum3).toString()); //try making the result zero
        assertEquals("-1.000 + 1.000i", cNum3.add(cNum5).toString()); //try making one part
        assertEquals("1.500 + 0.500i", cNum1.add(cNum8).toString()); //try adding floats
        assertEquals(2.0, cNum2.add(cNum3).getReal(),0.001);//test without using toString
        assertEquals(2.0, cNum2.add(cNum3).getImag(),0.001);
    }
    /**
     * This is the test for the subtract method. It tests the subtraction of complex numbers. Both toString output and actual values are tested.
     */
    @Test
    public void testSubtract(){
        assertEquals("-2.000 + -2.000i", cNum1.subtract(cNum2).toString()); //standard subtraction

        assertEquals("1.000 + 2.000i", cNum1.subtract(cNum0).toString()); //try subtracting zero
        assertEquals("0.000 + 0.000i", cNum1.subtract(cNum6).toString()); //try making the result zero
        assertEquals("3.000 + -3.000i", cNum4.subtract(cNum5).toString()); //try making one part negative
        assertEquals("0.500 + 3.500i", cNum1.subtract(cNum8).toString()); //try subtracting floats
        assertEquals(4.0, cNum2.subtract(cNum3).getReal(),0.001);//test without using toString
        assertEquals(6.0, cNum2.subtract(cNum3).getImag(),0.001);
    }
    /**
     * This is the test for the multiply method. It tests the multiplication of complex numbers. Both toString output and actual values are tested.
     */
    @Test
    public void testMultiply(){
        assertEquals("-5.000 + 10.000i", cNum1.multiply(cNum2).toString()); //standard multiplication
        assertEquals("0.000 + 0.000i", cNum1.multiply(cNum0).toString()); //try multiplying zero
        assertEquals("7.000 + -16.000i", cNum1.multiply(cNum7).toString()); //try multiplying positive by negative
        assertEquals("3.500 + -0.500i", cNum1.multiply(cNum8).toString()); //try multiplying floats
        assertEquals(5.0, cNum2.multiply(cNum3).getReal(),0.001);//test without using toString
        assertEquals(-10.0, cNum2.multiply(cNum3).getImag(),0.001);
    }

    /**
     * This is the test for the divide method. It tests the division of complex numbers. Both toString output and actual values are tested.
     * It also tests the arithmetic exception for division by zero.
     */
    @Test
    public void testDivide(){
        assertEquals("0.440 + 0.080i", cNum1.divide(cNum2).toString()); //standard division
        assertEquals("0.279 + 0.066i", cNum3.divide(cNum7).toString()); //try dividing negatives, test rounding
        assertEquals("-0.279 + -0.066i", cNum1.divide(cNum7).toString()); //try dividing positive by negative
        assertEquals("-1.000 + 1.000i", cNum1.divide(cNum8).toString()); //try dividing floats
        assertEquals(-2.2, cNum2.divide(cNum3).getReal(),0.001);//test without using toString
        assertEquals(0.4, cNum2.divide(cNum3).getImag(),0.001);
        try{
            cNum1.divide(cNum0); //try dividing zero
        }
        catch (ArithmeticException e){
            assertEquals("Cannot divide by zero.", e.getMessage()); //test exception
        }
    }

    /**
     * This is the test for the isGreaterThan method for real numbers. It tests both true and false cases.
     */
    @Test
    public void testIsGreater(){
        assertTrue(rNum2.isGreaterThan(rNum1));
        assertFalse(rNum1.isGreaterThan(rNum2));
        assertFalse(rNum1.isGreaterThan(rNum1));//test case where numbers are the same
    }
    /**
     * This is the test for the isLessThan method for real numbers. It tests both true and false cases.
     */
    @Test
    public void testIsLess(){
        assertTrue(rNum1.isLessThan(rNum2));
        assertFalse(rNum2.isLessThan(rNum1));
        assertFalse(rNum1.isLessThan(rNum1));//test case where numbers are the same
    }
    
}