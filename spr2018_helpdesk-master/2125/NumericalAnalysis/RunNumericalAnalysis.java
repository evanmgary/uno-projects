public class RunNumericalAnalysis{
    public static void main(String[]  args){
        if(args.length != 2){
            System.out.println("Usage: java RunNumericalAnalysis low high");
        }
        double low = Double.parseDouble(args[0]);
        double high = Double.parseDouble(args[1]);
        NumericalAnalysis myTool = new NumericalAnalysis();
        double ans = myTool.solveForZero(new Square(-1), low, high);
        System.out.println("The answer is: " + ans);
    }
}
