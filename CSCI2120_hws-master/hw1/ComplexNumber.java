/**
 * This is the ComplexNumber class for Homework 1.
 * @author Evan Gary
 */
 
public class ComplexNumber{

    private final double real;
    private final double imag;
    
    /**
     * This is the constructor for the ComplexNumber class.
     * 
     * It takes two double values as parameters and uses them to fill the two parts of the number.
     * 
     * @param real The double that is the real part of the number.
     * @param imag The double that is the imaginary part of the number.
     */
    
    public ComplexNumber(double real, double imag){
        this.real = real;
        this.imag = imag;
        
    }
    
    /**
     * This is the getter method for the real part of the ComplexNumber object.
     * Returns the real part of the object as a double.
     * 
     * @return real The double value that is the real part of the number.
     */
    public double getReal(){
        return this.real;
    }
    
    /**
     * This is the getter method for the imaginary part of the ComplexNumber object.
     * Returns the imaginary part of the object as a double.
     * 
     * @return imag The double value that is the real part of the number.
     */
    public double getImag(){
        return this.imag;
    }
    
    /**
     * This is the addition method for the ComplexNumber class.
     * 
     * It takes another complex number and adds it to the current one.
     * 
     * @param other The other ComplexNumber object to be added.
     * @return newNum The result of the addition.
     */
     
    public ComplexNumber add(ComplexNumber other){
        double newReal = this.real + other.getReal();
        double newImag = this.imag + other.getImag();
        ComplexNumber newNum = new ComplexNumber(newReal, newImag);
        return newNum;
    }
    
    /**
     * This is the subtraction method for the ComplexNumber class.
     * 
     * It takes another complex number and subtracts it from the current one.
     * 
     * @param other The other ComplexNumber object to be subtracted.
     * @return newNum The result of the subtraction.
     */
     
    public ComplexNumber subtract(ComplexNumber other){
        double newReal = this.real - other.getReal();
        double newImag = this.imag - other.getImag();
        ComplexNumber newNum = new ComplexNumber(newReal, newImag);
        return newNum;
    }
    
    /**
     * This is the multiplication method for the ComplexNumber class.
     * 
     * It takes another complex number and multiplies it by the current one.
     * 
     * @param other The other ComplexNumber object to be multiplied.
     * @return newNum The result of the multiplication.
     */
     
    public ComplexNumber multiply(ComplexNumber other){
        double newReal = (this.real * other.getReal()) - (this.imag * other.getImag());
        double newImag = (this.imag * other.getReal()) + (this.real * other.getImag());
        ComplexNumber newNum = new ComplexNumber(newReal, newImag);
        return newNum;
    }
    
    /**
     * This is the subtraction method for the ComplexNumber class.
     * 
     * It takes another complex number and divides the current number by the other number.
     * 
     * 
     * @param other The other ComplexNumber object to be divided.
     * @return newNum The result of the division.
     * @throws ArithmeticException This method involves a division step. The dividend in this step cannot be zero.
     */
     
    public ComplexNumber divide(ComplexNumber other){
        
        double divValue = Math.pow(other.getReal(), 2) + Math.pow(other.getImag(), 2);
        if (divValue == 0){
            throw new ArithmeticException("Cannot divide by zero.");
        }
        double newReal = ((this.real * other.getReal()) + (this.imag * other.getImag())) / divValue;
        double newImag = ((this.imag * other.getReal()) - (this.real * other.getImag())) / divValue;
        ComplexNumber newNum = new ComplexNumber(newReal, newImag);
        return newNum;
    }
    
    /**
     * This is the equals method to compare two ComplexNumber objects.
     * 
     * It overrides the equals method in class Object.
     * 
     * @param other The other number to be compared.
     * @return equals The boolean result of the comparison.
     */
     
    public boolean equals(ComplexNumber other){
        return (this.real == other.getReal()) && (this.imag == other.getImag());
    }
    
    /**
     * This is thetoString method to output a ComplexNumber object as a string. 
     * 
     * The string takes the form a + bi, for example 2 + 3i
     * It overrides toString method in class Object.
     * 
     * @return string The formatted output for the object.
     */
    public String toString(){
        return String.format("%.3f + %.3fi", this.real, this.imag);
    }
    
}