import java.util.Scanner;

public class ComplexRunner{
    
    
    public static void main(String[] args){
        Scanner input  = new Scanner(System.in);
        
        System.out.println("Enter the real part of the first number: ");
        double a = input.nextDouble();
        System.out.println("Enter the imaginary part of the first number: ");
        double b = input.nextDouble();
        System.out.println("Enter the real part of the second number: ");
        double c = input.nextDouble();
        System.out.println("Enter the imaginary part of the second number: ");
        double d = input.nextDouble();
        System.out.println("Enter the third (real) number: ");
        double e = input.nextDouble();
        System.out.println("Enter the fourth (real) number: ");
        double f = input.nextDouble();
        
        ComplexNumber first = new ComplexNumber(a,b);
        ComplexNumber second = new ComplexNumber(c,d);
        RealNumber third = new RealNumber(e);
        RealNumber fourth = new RealNumber(f);
        
        System.out.println(first.add(second));
        System.out.println(first.subtract(second));
        System.out.println(first.multiply(second));
        System.out.println(first.divide(second));
        System.out.println(first.equals(second));
        System.out.println(third.isGreaterThan(fourth));
        System.out.println(third.isLessThan(fourth));
    }
}