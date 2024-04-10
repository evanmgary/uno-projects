package NumericalAnalysis;
public class NumericalAnalysis
{
    public double solveForZero(Function func, double low, double high)
    {
        double epsilon = 1;
        double mid = 0;
        while(Math.abs(epsilon) > 0.00000001)
        {
            mid = low + ((high - low) / 2);
            epsilon = func.f(mid);
            if(epsilon > 0){
                high = mid;
            } else if (epsilon < 0) {
                low = mid;
            }
        }
        return mid;
    }
}
