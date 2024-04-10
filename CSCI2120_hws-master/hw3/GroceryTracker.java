import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * GroceryTracker code for Homework 3.
 * App that uses the GroceryList class.
 *
 * @author Evan Gary
 * @version 16 February 2017
 */
public class GroceryTracker {

    private static Scanner input = new Scanner(System.in);
    private static boolean isRunning = true; //Used in the loop to keep the program running
    private static boolean listChanged = false; //Used to see if the list changed when quit is selected

    /**
     * Main method, reads the object from the file and contains the main program loop.
     *
     * @param args Command line arguments, needed to get path
     */
    public static void main(String[] args){

        Path path = Paths.get(args[0]);
        GroceryList theList = readFromFile(path.toString());

        // Main loop
        while (isRunning == true){
            displayMenu(path);
            doUserChoice(theList, path);
        }
    }

    /**
     * Prints the menu for the user. Also prints the absolute path of the file.
     * @param path Path to the file.
     */
    public static void displayMenu(Path path){
        System.out.println("\nWelcome to Grocery Tracker!");
        System.out.println("Current File: " + path.toAbsolutePath());
        System.out.println("Please select an option: ");
        System.out.println("   1) View List");
        System.out.println("   2) Add an Item");
        System.out.println("   3) Remove an Item");
        System.out.println("   4) Save");
        System.out.println("   5) Quit");

    }

    /**
     * Contains the logic for the user selection. Allows the user to pick a choice from 1-5 then
     * runs the appropriate method or quits the program. The quit option uses a tracker variable to
     * track changes.
     *
     * @param theList The list used by the program, read from the file or created new.
     * @param path The path to the serialized file.
     */
    public static void doUserChoice(GroceryList theList, Path path){
        int userChoice;
        //try block to check for bad input (throws unchecked InputMismatchException)
        try{

            System.out.printf("Please enter your choice: ");
            userChoice = input.nextInt();
            input.nextLine(); //flush the scanner so it works right on the next input
            //View List
            if (userChoice == 1){
                viewList(theList);
            }
            //Add Item
            else if (userChoice == 2){
                addAnItem(theList);
            }
            //Remove Item
            else if (userChoice == 3) {
                removeAnItem(theList);
            }
            //Save
            else if(userChoice == 4){
                writeToFile(theList, path);
            }
            //Quit
            else if (userChoice == 5) {
                if (listChanged == true){
                    System.out.print("Changes have been made. Would you like to save? \n Enter Y to save and quit or N to discard changes and quit: ");
                    String quitInput = input.nextLine();
                    if (quitInput.equalsIgnoreCase("y")){
                        writeToFile(theList, path);
                        isRunning = false;
                    }
                    else if (quitInput.equalsIgnoreCase("n")) {
                        isRunning = false;
                    }
                    else{
                        System.out.println("Invalid entry.");
                    }
                }
                else {
                    isRunning = false;
                }
            }
            else {
                System.out.println("Invalid entry (out of range). Please enter a number from 1-5.");
            }
        }
        catch (InputMismatchException e){
            System.out.print("Invalid entry. Not a number. Please enter a number from 1-5.");
            input.nextLine(); //flush the scanner
        }
    }

    /**
     * Prints the list to the user.
     *
     * @param theList The list used by the program, read from the file or created new.
     */
    public static void viewList(GroceryList theList){
            System.out.println(theList.toString());
    }

    /**
     * Adds an item to the list using the addAnItem method. Checks to see if the item is already in the list
     * and does not add it if that is the case.
     *
     * @param theList The list used by the program, read from the file or created new.
     */
    public static void addAnItem(GroceryList theList){
        System.out.print("Please enter an item to add: ");
        String itemToAdd = input.nextLine();
        itemToAdd = normalize(itemToAdd); //put the input into canonical form for comparison
        boolean isItemInList = false;
        for (int i = 1; i <= theList.getItemCount(); i++) {
            if (itemToAdd.equals(theList.getItemAtIndex(i - 1))) {
                isItemInList = true;
                break;
            }
        }

        if (isItemInList == false && (itemToAdd.equals("") == false)){
                theList.addItem(itemToAdd);
                System.out.println("Item successfully added.");
                listChanged = true;
        }

    }

    /**
     * Removes an item from the list using the removeAnItem() method in GroceryList.
     * Takes input based on 1 as the first index, and converts it to the proper index.
     *
     * @param theList The list used by the program, read from the file or created new.
     */
    public static void removeAnItem(GroceryList theList){
        System.out.println("Please enter the index of the item you would like to remove: ");
        int indexToRemove = 0;
        try {
            indexToRemove = input.nextInt();
        }
        catch (IllegalArgumentException e){
            System.out.println("That is not valid input.");
        }
        if (indexToRemove > 0 && indexToRemove <= theList.getItemCount()){
            theList.removeItemAtIndex(indexToRemove - 1);
            System.out.println("Item successfully removed.");
            listChanged = true;
        }
        else {
            System.out.println("That is not a valid index.");
        }
    }

    /**
     * Converts a string to canonical form using the same action performed by the get method in GroceryList.
     * Splits the string into lowercase words, gets the first character, makes it uppercase, and replaces it with the
     * uppercase character.
     *
     * @param stringToFix Input string to put in canonical form.
     * @return Returns string in canonical form.
     */
    public static String normalize(String stringToFix) {
        String temp = stringToFix;
        temp = temp.trim();
        String tempArray[] = temp.split("\\s+"); //Split the array using whitespace
        String newString = "";

        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = tempArray[i].toLowerCase(); //Make the temp string lowercase
            Character firstChar = tempArray[i].charAt(0); //Pull the first character
            tempArray[i] = tempArray[i].replaceFirst("\\w", firstChar.toString().toUpperCase()); //Make it uppercase
            newString = newString.concat(tempArray[i] + " "); //Put the string back together
        }
        return newString.trim();
    }

    /**
     * Writes the object to a file using object serialization and the ObjectOutputStream class.
     *
     * @param obj The object to be written to the file.
     * @param path The path to the serialized file.
     */
    public static void writeToFile(Object obj, Path path){
        //try with resources to prevent the need to close
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))){
            out.writeObject(obj);
            listChanged = false; //change list tracker since file was saved
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads the file containing the list and returns the and makes an empty list if a file does not exist.
     *
     * @param filename The path to the file to be read.
     * @return Returns the list from the file (or the new empty list).
     */
    public static GroceryList readFromFile(String filename){
        GroceryList theList = null;
        //try with resources to prevent need to close
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            Object obj = in.readObject();
            if (obj instanceof GroceryList){
                theList = (GroceryList) obj;
            }
        }
        catch (FileNotFoundException e){
            theList = new GroceryList();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return theList;
    }
}//end class
