import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by emg2 on 3/12/2018.
 */
public class Runner {

    private static TourneyTree tree;
    private static ArrayList<String> winnerList;

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        if (Files.exists(Paths.get("blank.ser"))){
            readFile();
        }
        else{
            tree = new TourneyTree();
            parse();
        }

        winnerList = new ArrayList<>();
        System.out.print("Options:\nR)perform simulation with probability\nA)Use all data\nF)Use 538 data\n");
        System.out.print("V)Use vb data\nK)Use ken data\nEnter all applicable: ");
        String choice = in.nextLine();

        runMatches(choice);
        displayWinners();

    }

    private static void readFile(){
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("blank.ser"))){
            tree = (TourneyTree) input.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static void parse(){
        Parser.readData();
        Parser.generateTeams(tree);
        Parser.buildTournament(tree);
    }

    private static void runMatches(String choice){
        for (int i = 63; i >= 1; i--){

            winnerList.add(tree.doMatch(i, choice));
        }
    }

    private static void displayWinners(){

        System.out.println();
        for (int i = 31; i >= 0; i--){
            System.out.println(winnerList.get(i));
        }
        System.out.println();
        for (int i = 47; i >= 32; i--){
            System.out.println(winnerList.get(i));
        }
        System.out.println();
        for (int i = 55; i >= 48; i--){
            System.out.println(winnerList.get(i));
        }
        System.out.println();
        for (int i = 59; i >= 56; i--){
            System.out.println(winnerList.get(i));
        }
        System.out.println();
        for (int i = 61; i >= 60; i--){
            System.out.println(winnerList.get(i));
        }
        System.out.println();
        System.out.println(winnerList.get(62));
    }
}
