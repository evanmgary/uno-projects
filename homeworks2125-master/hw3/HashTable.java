import java.util.LinkedList;
import java.util.List;

/**
 * Hash table class for Homework 3. Uses separate chaining.
 * Creates a hash table to store string fragments and contains methods to calculate the position of a pattern string.
 * @author Evan Gary
 * @version 14 July 2017
 */

public class HashTable {

    private List<StringFragment>[] table;
    private String mainString;
    private static final int P = 7; //Can be any prime number
    private static final int P2 = 3571; //Can be any large prime number
    private int patternStringLength;

    /**
     * Constructor for HashTable.
     * Creates a hash table with a table size twice that of the string length, for a load factor less than 0.5
     * @param mainString
     */
    public HashTable(String mainString){
        this.mainString = mainString;
        table = new LinkedList[mainString.length() *2];
        //Make linked lists for each index
        for (int i = 0; i < table.length; i++){
            table[i]= new LinkedList<StringFragment>();
        }
        patternStringLength = 0;
    }

    /**
     * Adds an element to the hash table. Calls LinkedList's add method.
     * @param toAdd
     */
    private void addElement(StringFragment toAdd){
        table[getIndex(toAdd)].add(toAdd);
    }

    /**
     * Gets the StringFragment object from the hash table. Iterates through the linked list at the hash index.
     * @param hashCode
     * @return the string, position, and hash
     */
    public StringFragment getElement(int hashCode){
        for (int i = 0; i < table[getIndex(hashCode)].size(); i++){
            if (hashCode == table[getIndex(hashCode)].get(i).hashCode()){
                return table[getIndex(hashCode)].get(i);
            }

        }
        return null;
    }

    /**
     * Contains the hash function to get the index from a StringFragment object
     * @param s
     * @return a hash table index
     */
    private int getIndex(StringFragment s){
        return (s.hash % P2) % table.length; //the hash function
    }

    /**
     * Contains the hash function to get the index from a hash value.
     * Overloaded method.
     * @param hash
     * @return a hash table index
     */
    private int getIndex(int hash){
        return (hash % P2) % table.length;
    }

    /**
     * Iteratively calculates the hash value for a string using the function
     * s[0]P^(n-1) + s[1]P^(n-2) + ... + s[n-1]
     * @param str
     * @return the hash value for a string.
     */
    public static int stringHash(String str){
        int hash = 0;
        //see the String class hashCode() for a very similar calculation
        if (str.length() > 0){
            for (int i = 0; i < str.length() ; i++){
                hash = (hash*P) + str.charAt(i);
            }
        }
        return hash;
    }

    /**
     * Fills the hash table with the various string fragments from the main string.
     * Calculates using Method 3 from the homework .docx file.
     * @param patternString
     */
    public void fillHashTable(String patternString){
        int k = patternString.length();
        //calculate the first string fragment
        int firstHash = HashTable.stringHash(mainString.substring(0, k));
        int newHash = firstHash;
        addElement(new StringFragment(firstHash, 0, mainString.substring(0, k)));
        //calculate the rest of the pattern-length string fragments
        for (int i = 1; i < mainString.length() - k + 1; i++){
            newHash = ((newHash * P) + mainString.charAt(k + i - 1)) - (mainString.charAt(i-1)* (int)Math.pow(P, k));
            addElement(new StringFragment(newHash, i, mainString.substring(i, i+k)));
        }
    }

    /**
     * Uses the hash table to find the position of the pattern string in the main string.
     * @param patternString
     * @return the position of the first appearance of the pattern string, or -1 if not found
     */
    public int findPatternPosition(String patternString){
        if (patternString.length() > mainString.length()){
            throw new IllegalArgumentException("Pattern string cannot be longer than string to search.");
        }
        //Make sure to reset the hash table if the hash table is empty or the pattern string length changes
        if (patternStringLength == 0 || (patternStringLength != patternString.length())){
            for (int i = 0; i < table.length; i++){
                table[i]= new LinkedList<StringFragment>();
            }
            fillHashTable(patternString);
        }
        int patternHash = (stringHash(patternString));
        int patternIndex = getIndex(patternHash);
        //Look through the linked list at the proper index to get the correct string fragment
        for (int i = 0; i < table[getIndex(patternHash)].size(); i++) {
            if (table[patternIndex].get(i).hash == patternHash) {
                if (checkString(patternString, table[patternIndex].get(i).fragment)) {
                    return table[patternIndex].get(i).position;
                }
                else{
                    System.err.println("Hashes match but strings do not.");
                    break;
                }
            }
        }
        return -1;
    }

    /**
     * Checks if strings are equal by comparing characters
     * @param patternString
     * @param toCheck
     * @return whether the strings are equal
     */
    private boolean checkString(String patternString, String toCheck)   {
        for (int i = 0; i < patternString.length(); i++){
            if (patternString.charAt(i) != (toCheck.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Method to find the position of a pattern in a string without having to worry about making HashTable instances
     * @param patternString
     * @param mainString
     * @return the first position of the pattern string in the main string
     */
    public static int findPosition(String patternString, String mainString){
        HashTable theTable = new HashTable(mainString);
        return theTable.findPatternPosition(patternString);
    }

    /**
     * Inner class to associate string fragments with their position in the main string.
     */
    private class StringFragment{

        private int position;
        private int hash;
        private String fragment;

        public StringFragment(int hash, int position, String fragment){
            this.hash = hash;
            this.position = position;
            this.fragment = fragment;
        }


    }//end inner class StringFragment
}//end class

