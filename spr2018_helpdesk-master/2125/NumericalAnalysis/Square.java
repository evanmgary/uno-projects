package NumericalAnalysis;
public class Square implements Function{
    double translate;
    public Square(double _translate){
        translate = _translate;
    }

    public double f(double x){
        return x*x + translate;
    }
}
