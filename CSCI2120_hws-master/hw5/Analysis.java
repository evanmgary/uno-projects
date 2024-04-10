import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Class used to perform data analysis for the assignment.
 * @author Evan Gary
 * @version 7 April 2017
 */
public class Analysis{
    private static InsertionSorter insSorter = new InsertionSorter();
    private static SelectionSorter selSorter = new SelectionSorter();
    private static MergeSorter merSorter = new MergeSorter();
    private static LinearSearcher linSearcher = new LinearSearcher();
    private static BinarySearcher binSearcher = new BinarySearcher();
    static SecureRandom rng = new SecureRandom();
    static ArrayList<Integer> n10sorted = new ArrayList();
    static ArrayList<Integer> n10reversed = new ArrayList();
    static ArrayList<Integer> n10random = new ArrayList();
    static ArrayList<Integer> n20sorted = new ArrayList();
    static ArrayList<Integer> n20reversed = new ArrayList();
    static ArrayList<Integer> n20random = new ArrayList();
    static ArrayList<Integer> n50sorted = new ArrayList();
    static ArrayList<Integer> n50reversed = new ArrayList();
    static ArrayList<Integer> n50random = new ArrayList();
    static ArrayList<Integer> n100sorted = new ArrayList();
    static ArrayList<Integer> n100reversed = new ArrayList();
    static ArrayList<Integer> n100random = new ArrayList();
    static ArrayList<Integer> n500sorted = new ArrayList();
    static ArrayList<Integer> n500reversed = new ArrayList();
    static ArrayList<Integer> n500random = new ArrayList();
    static ArrayList<Integer> n1000sorted = new ArrayList();
    static ArrayList<Integer> n1000reversed = new ArrayList();
    static ArrayList<Integer> n1000random = new ArrayList();

    public static void main(String[] args){


        int[] n10ops = new int[11];
        int[] n20ops= new int[11];
        int[] n50ops= new int[11];
        int[] n100ops= new int[11];
        int[] n500ops= new int[11];
        int[] n1000ops= new int[11];
        String[] outputs = {"Linear Search: ", "Binary Search: ", "Sel on Sorted: ", "Sel on Rverse: ",
        "Sel on Random: ", "Ins on Sorted: ", "Int on Rverse: ", "Ins on Random: ", "Mer on Sorted: ",
        "Mer on Rverse: ", "Mer on Random: "};
        initialize();


        //Perform the searches and sorts, storing the number of operations
        //After each set of sorts, the data sets need to be reinitialized
        n10ops[0] = linSearcher.search(n10sorted, 5)[1];
        n10ops[1] = binSearcher.search(n10sorted, 5)[1];
        n10ops[2] = selSorter.sort(n10sorted);
        n10ops[3] = selSorter.sort(n10reversed);
        n10ops[4] = selSorter.sort(n10random);
        initialize();
        n10ops[5] = insSorter.sort(n10sorted);
        n10ops[6] = insSorter.sort(n10reversed);
        n10ops[7] = insSorter.sort(n10random);
        initialize();
        n10ops[8] = merSorter.sort(n10sorted);
        n10ops[9] = merSorter.sort(n10reversed);
        n10ops[10] = merSorter.sort(n10random);

        n20ops[0] = linSearcher.search(n20sorted, 5)[1];
        n20ops[1] = binSearcher.search(n20sorted, 5)[1];
        n20ops[2] = selSorter.sort(n20sorted);
        n20ops[3] = selSorter.sort(n20reversed);
        n20ops[4] = selSorter.sort(n20random);
        initialize();
        n20ops[5] = insSorter.sort(n20sorted);
        n20ops[6] = insSorter.sort(n20reversed);
        n20ops[7] = insSorter.sort(n20random);
        initialize();
        n20ops[8] = merSorter.sort(n20sorted);
        n20ops[9] = merSorter.sort(n20reversed);
        n20ops[10] = merSorter.sort(n20random);

        n50ops[0] = linSearcher.search(n50sorted, 5)[1];
        n50ops[1] = binSearcher.search(n50sorted, 5)[1];
        n50ops[2] = selSorter.sort(n50sorted);
        n50ops[3] = selSorter.sort(n50reversed);
        n50ops[4] = selSorter.sort(n50random);
        initialize();
        n50ops[5] = insSorter.sort(n50sorted);
        n50ops[6] = insSorter.sort(n50reversed);
        n50ops[7] = insSorter.sort(n50random);
        initialize();
        n50ops[8] = merSorter.sort(n50sorted);
        n50ops[9] = merSorter.sort(n50reversed);
        n50ops[10] = merSorter.sort(n50random);

        n100ops[0] = linSearcher.search(n100sorted, 5)[1];
        n100ops[1] = binSearcher.search(n100sorted, 5)[1];
        n100ops[2] = selSorter.sort(n100sorted);
        n100ops[3] = selSorter.sort(n100reversed);
        n100ops[4] = selSorter.sort(n100random);
        initialize();
        n100ops[5] = insSorter.sort(n100sorted);
        n100ops[6] = insSorter.sort(n100reversed);
        n100ops[7] = insSorter.sort(n100random);
        initialize();
        n100ops[8] = merSorter.sort(n100sorted);
        n100ops[9] = merSorter.sort(n100reversed);
        n100ops[10] = merSorter.sort(n100random);

        n500ops[0] = linSearcher.search(n500sorted, 5)[1];
        n500ops[1] = binSearcher.search(n500sorted, 5)[1];
        n500ops[2] = selSorter.sort(n500sorted);
        n500ops[3] = selSorter.sort(n500reversed);
        n500ops[4] = selSorter.sort(n500random);
        initialize();
        n500ops[5] = insSorter.sort(n500sorted);
        n500ops[6] = insSorter.sort(n500reversed);
        n500ops[7] = insSorter.sort(n500random);
        initialize();
        n500ops[8] = merSorter.sort(n500sorted);
        n500ops[9] = merSorter.sort(n500reversed);
        n500ops[10] = merSorter.sort(n500random);

        n1000ops[0] = linSearcher.search(n1000sorted, 5)[1];
        n1000ops[1] = binSearcher.search(n1000sorted, 5)[1];
        n1000ops[2] = selSorter.sort(n1000sorted);
        n1000ops[3] = selSorter.sort(n1000reversed);
        n1000ops[4] = selSorter.sort(n1000random);
        initialize();
        n1000ops[5] = insSorter.sort(n1000sorted);
        n1000ops[6] = insSorter.sort(n1000reversed);
        n1000ops[7] = insSorter.sort(n1000random);
        initialize();
        n1000ops[8] = merSorter.sort(n1000sorted);
        n1000ops[9] = merSorter.sort(n1000reversed);
        n1000ops[10] = merSorter.sort(n1000random);


        System.out.println("For n= 10: ");
        for (int i = 0; i < 11; i++){
            System.out.println(outputs[i] + n10ops[i]);
        }
        System.out.println("\nFor n= 20: ");
        for (int i = 0; i < 11; i++){
            System.out.println(outputs[i] + n20ops[i]);
        }

        System.out.println("\nFor n= 50: ");
        for (int i = 0; i < 11; i++){
            System.out.println(outputs[i] + n50ops[i]);
        }

        System.out.println("\nFor n= 100: ");
        for (int i = 0; i < 11; i++){
            System.out.println(outputs[i] + n100ops[i]);
        }
        System.out.println("\nFor n= 500: ");
        for (int i = 0; i < 11; i++){
            System.out.println(outputs[i] + n500ops[i]);
        }
        System.out.println("\nFor n= 1000: ");
        for (int i = 0; i < 11; i++){
            System.out.println(outputs[i] + n1000ops[i]);
        }


    }

    /**
     * Generate the data sets for the searchers and sorters to use.
     * For sorted sets, generate a sequential set of integers from 0 to n
     * For reversed sets, generate a reversed set of integers from n to 1
     * For random sets, generate n integers randomly chosen from 0 to 999
     */
    public static void initialize(){
        n10sorted.clear();
        n10reversed.clear();
        n10random.clear();
        n20sorted.clear();
        n20reversed.clear();
        n20random.clear();
        n50sorted.clear();
        n50reversed.clear();
        n50random.clear();
        n100sorted.clear();
        n100reversed.clear();
        n100random.clear();
        n500sorted.clear();
        n500reversed.clear();
        n500random.clear();
        n1000sorted.clear();
        n1000reversed.clear();
        n1000random.clear();


        for (int i = 0; i < 10; i++){
            n10sorted.add(i);
            n10reversed.add(10-i);
            n10random.add(rng.nextInt(1000));
        }
        for (int i = 0; i < 20; i++){
            n20sorted.add(i);
            n20reversed.add(20-i);
            n20random.add(rng.nextInt(1000));
        }
        for (int i = 0; i < 50; i++){
            n50sorted.add(i);
            n50reversed.add(50-i);
            n50random.add(rng.nextInt(1000));
        }
        for (int i = 0; i < 100; i++){
            n100sorted.add(i);
            n100reversed.add(100-i);
            n100random.add(rng.nextInt(1000));
        }
        for (int i = 0; i < 500; i++){
            n500sorted.add(i);
            n500reversed.add(500-i);
            n500random.add(rng.nextInt(1000));
        }
        for (int i = 0; i < 1000; i++){
            n1000sorted.add(i);
            n1000reversed.add(1000-i);
            n1000random.add(rng.nextInt(1000));
        }
    }//end initialize method
}//end analysis class