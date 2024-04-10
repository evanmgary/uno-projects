import java.io.IOException;
import java.security.SecureRandom;
import java.util.Formatter;

/**
 * Created by emg2 on 4/21/2018.
 */
public class MatMul_thread {

    private double[][] matrix1;
    private double[][] matrix2;
    private double[][] matrixProd;
    private long[][] runningTimes;
    private SecureRandom sr;

    public MatMul_thread(){
        matrix1 = new double[100][100];
        matrix2 = new double[100][100];
        matrixProd = new double[100][100];
        runningTimes = new long[11][25];
        sr = new SecureRandom();
        generateMatrices();
    }


    public void generateMatrices(){
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++){
                matrix1[i][j] = (sr.nextDouble() * 1000) - 500.0;
                matrix2[i][j] = (sr.nextDouble() * 1000) - 500.0;
            }
        }
    }

    public synchronized void setProduct(int i, int j, double result){
        matrixProd[i][j] = result;
    }

    public long makeRunThreads(int numThreads){

        Thread[] threads = new Thread[numThreads];
        long startTime = System.nanoTime();
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Multiplier(matrix1, matrix2, i*numThreads, numThreads);
        }
        for (Thread t : threads){
            t.start();
        }
        for (Thread t : threads){
            try{
                t.join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        long runTime = System.nanoTime() - startTime;
        return runTime;

    }

    public void printToCsv(){
        try {
            Formatter out = new Formatter("exe_time.csv");
            for (int i = 0; i < 11; i++) {
                out.format("(%d,", i*10);
                long total = 0;
                for (long l:runningTimes[i]){
                    total += l;
                }
                out.format(" %d)\n", total/25);

            }
            out.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        MatMul_thread exec = new MatMul_thread();
        exec.generateMatrices();
        long runTime;

        for (int n = 0 ; n < 25; n++) {


            for (int i = 0; i <= 100; i += 10) {

                if (i == 0) {
                    runTime = exec.makeRunThreads(1);

                } else {
                    runTime = exec.makeRunThreads(i);
                }
                exec.runningTimes[i/10][n] = runTime;

            }


        }
        exec.printToCsv();
    }


    class Multiplier extends Thread{

        private double[][] matrix1;
        private double[][] matrix2;
        private int start;
        private int numThreads;

        public Multiplier(double[][] matrix1, double[][] matrix2, int start, int numThreads) {
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.start = start;
            this.numThreads = numThreads;
        }


        public void run(){


            int i = 0;
            int j = 0;
            for(int entry = start; entry <= 99; entry += numThreads){
                double temp = 0.0;
                i = entry / 100;
                j = entry % 100;
                for (int n = 0; n < 100; n++){
                    temp += matrix1[i][n] * matrix2[n][j];
                }
                setProduct(i,j,temp);

            }

        }//end run
    }//end Multiplier


}
