/**
 * This is the RealNumber class for Homework 1.
 * @author Evan Gary
 */
 
public class RealNumber extends ComplexNumber {

	/**
	* This is the constructor for the real number class.
	* It takes one double value to fill the real part of the complex number. The imaginary part is always zero.
	*
	* @param real Double value that represents the real number.
	*/
    public RealNumber(double real){
        super(real, 0);
    }
    
	/**
	* This is the method that compares real numbers to see which is greater.
	* It compares a real number object to another.
	*
	* @param other The real number that is being compared.
	* @return The boolean value stating whether or not the real number is greater than the other real number.
	*/
    public boolean isGreaterThan(RealNumber other){
        return (this.getReal() > other.getReal());
    }
    
	/**
	* This is the method that compares real numbers to see which is less.
	* It compares a real number object to another.
	*
	* @param other The real number that is being compared.
	* @return The boolean value stating whether or not the real number is less than the other real number.
	*/
    public boolean isLessThan(RealNumber other){
        return (this.getReal() < other.getReal());
    }
}
 