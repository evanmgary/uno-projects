import java.io.*;
/**
 * A class that models a list of grocery items.
 *
 * Hint: Use ArrayList to do all the heavy lifting for storing your items.
 * Does not use ArrayList or StringBuilder for bonus points.
 * @author Evan Gary, 2120 instructors
 * @version 16 February 2016
 */
public class GroceryList implements Serializable {

    private String[] theList;
    /**
     * Constructs a new GroceryList object that is initially empty.
     */
    public GroceryList(){
        theList = new String[0]; //Make an empty string array
    }

    /**
     * Returns the number of items currently in the grocery list.
     *
     * @return The number of items in the list.
     */
    public int getItemCount(){

        return theList.length;
    }

    /**
     * If the given item String is not in the list yet (ignoring capitalization
     * and leading/trailing whitespace), appends the item to the end
     * of the list. Otherwise, does nothing. A GroceryList should be able
     * to hold as many unique item names as the user desires. If the item
     * contains no actual text other than whitespace, this should not be added.
     *
     * @param item The item to add.
     */
    public void addItem(final String item){

        String[] temp = new String[theList.length + 1];
        //Copy the array into the temp array, then add the new element to the end of the temp array
        for (int i = 0; i <= theList.length - 1; i++){
            temp[i] =  theList[i];
        }
        temp[theList.length] = item;
        theList = temp;
    }

    /**
     * Removes the item at the specified index. If the index specified is
     * >= this.getItemCount(), an IllegalArgumentException should be thrown.
     * After removal of an item, the index of all items past the specified index
     * should be decremented. E.g.:
     *
     * Before:
     * 0 => Eggs
     * 1 => Milk
     * 2 => Spinach
     *
     * list.removeItemAtIndex(1);
     *
     * After:
     * 0 => Eggs
     * 1 => Spinach
     *
     * @param index The index (zero-based) to remove.
     */
    public void removeItemAtIndex(final int index){
        if (this.getItemCount() == index){
            throw new IllegalArgumentException();
        }
        String[] temp = new String[theList.length - 1];
        //Copy the array up until the specified index, then copy one index ahead into a shortened array
        for (int i = 0; i < theList.length - 1; i++){
            if (i < index){
                temp[i] = theList[i];
            }
            else {
                temp[i] = theList[i + 1];
            }
        }
        theList = temp;
    }

    /**
     * Returns the item String at the specified index. If the index specified
     * is >= this.getItemCount(), an IllegalArgumentException should be thrown. The
     * String returned should be given in "canonical" form", that is, with no leading/trailing
     * whitespace and the first letter of each individual word capitalized, regardless of
     * how the item was added initially. E.g.:
     *
     * "eggs" => "Eggs"
     * "toilet paper" => "Toilet Paper"
     * "MILK" => "Milk"
     * "  coffee " => "Coffee"
     *
     * @param index The index (zero-based) to fetch.
     * @return The grocery item text at the given index, in the canonical form specified above.
     */
    public String getItemAtIndex(final int index){
        if (this.getItemCount() == 0){
            throw new IllegalArgumentException();
        }
        String temp = theList[index];
        temp = temp.trim();
        String tempArray[] = temp.split("\\s+"); //Split the array using whitespace
        String newString = "";

        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = tempArray[i].toLowerCase(); //Make the temp string lowercase
            Character firstChar = tempArray[i].charAt(0); //Pull the first character
            tempArray[i] = tempArray[i].replaceFirst("\\w", firstChar.toString().toUpperCase()); //Make it uppercase
            newString = newString.concat(tempArray[i] + " "); //Put the string back together
        }
        return newString.trim(); //trim() eliminates the space added at the end
    }

    /**
     * @{inheritDoc}
     *
     * Returns a String representation of this list. Should use the StringBuilder class to build
     * up the result. A representation of a GroceryList is a human-readable series of lines with
     * a line number (1-based), followed by a period and space (". "), followed by the item. There
     * should be no trailing newline. If the list is empty, the words "(Empty List)" should be returned.
     *
     * E.g. for GroceryList {0 => "Eggs", 1 => "Bacon", 2 => "Bread"}, it should return:
     *
     * "1. Eggs
     * 2. Bacon
     * 3. Bread"
     */
    @Override
    public String toString(){
        if (this.getItemCount() == 0){
            return ("(Empty List)");
        }
        else {
            String combined = "";
            //Iterate through the array, putting the items in the correct form n. Item in one large multiple line string
            for (int i = 0; i < theList.length; i++) {
                combined = combined.concat((i + 1) + ". " + this.getItemAtIndex(i) + "\n");
            }

            return combined.trim();
        }
    }

}//end class
