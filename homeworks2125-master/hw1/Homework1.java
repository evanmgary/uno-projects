import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Runner file for homework 1. Contains the preprocessor and method for the Maximum Subsequence Sum.
 * Takes a command line argument providing the file to process for #include
 * @author Evan Gary
 * @version 12 June 2017
 */

public class Homework1{

    //Runner main method
    public static void main(String[] args){

    Path filename = Paths.get(args[0]);

    // See if resultFile already exists; if so, delete it
    if (Files.exists(Paths.get("resultFile"))) {
            try {
                Files.delete(Paths.get("resultFile"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    preprocess(filename);
    }

    /**
     * Method to calculate the maximum subsequence sum and starting and ending indices of an integer sequence
     * Adapted from the code in Weiss's book with O(N) running time
     * @param a The data set containing the integer sequence
     * @return MSPAnswer object containing the sum and indices
     */
    public static MSPAnswer maxSubSum4Mod(int [] a){
        int maxSum = 0;
        int thisSum = 0;
        int first = 0;
        int last = 0;
        int consec = 0;

        for (int j = 0; j < a.length; j++){
            thisSum += a[j];

            // To find the first index, count the number of consecutive times the program did NOT reset thisSum.
            // Then, subtract the number of consecutive runs without resetting from the last index (and add 1).
            if (thisSum > maxSum) {
                maxSum = thisSum;
                last = j;
                consec++;
                first = last - consec + 1;
            }
            else if (thisSum < 0){

                thisSum = 0;
                consec = 0;

            }
            else{
                consec++;
            }
        }
        return new MSPAnswer(first, last, maxSum, a);
    }


    /**
     * Method to processor #include lines in text files
     * @param path1 Path of the file to process
     * @param existingPaths ArrayList of already processed paths
     */
    private static void preprocess(Path path1, List<Path> existingPaths){

        // Use try with resources to make a buffered writer
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("resultFile", true))) {
            // When a path is read, add it to the existing paths array so that the includes are not duplicated
            existingPaths.add(path1);
            // Read the file to an ArrayList of strings
            List<String> fileContents = Files.readAllLines(path1);


            for(int i = 0; i < fileContents.size(); i++) {
                if (fileContents.get(i).startsWith("#include")) {
                    // the ninth character is the start of the filename
                    String filepath = fileContents.get(i).trim().substring(9);
                    // Quotes, as in "file1.h" need to be removed before using Paths.get
                    filepath = filepath.replaceAll("\"","");
                    Path path2 = Paths.get(filepath);
                    fileContents.remove(i--);
                    // ONLY run the recursion if the path found using the include statement has not already been processed
                    if (!existingPaths.contains(path2)){
                        preprocess(path2, existingPaths);
                    }

                }// end if
            }// end for

           // BufferedWriter writer = new BufferedWriter(new FileWriter("newFile.txt", true));
            for(int i = 0; i < fileContents.size(); i++){
                writer.write(fileContents.get(i));
                writer.newLine();
            }

        }// end try block
        catch(IOException e){
            e.printStackTrace();
        }
    }// end method preprocess

    //Wrapper method to enter the empty existingPaths array as parameter.
    public static void preprocess(Path path1){
        preprocess(path1, new ArrayList<Path>());
    }
}