import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Radix sorter class for Bonus Homework.
 * Contains static radix sorter method
 * @author Evan Gary
 * @version 31 July 2017
 */
public class RadixSorter{

    /**
     * Radix sorter method. Takes an ArrayList of Strings and sorts them.
     * All strings must be the same length.
     * @param list the list to sort
     * @return the sorted list
     */
    public static ArrayList<String> sort(ArrayList<String> list) {

        checkLength(list);
        queueSet[] buckets = new queueSet[256];
        for (int i = 0; i < 256; i++) {
            buckets[i] = new queueSet(i);
        }

        for (int i = list.get(0).length() - 1; i >= 0; i--) {

            for (int j = 0; j < list.size(); j++) {

                buckets[list.get(j).charAt(i)].enqueue(list.get(j));

            }

            list.clear();
            for (int k = 0; k < 256; k++) {
                for (int l = 0; l < buckets[k].size(); l++) {
                    list.add(buckets[k].dequeue());
                }
            }

        }
        return list;
    }

    /**
     * Method to make sure all the strings are the same length.
     * @param list the list to check
     * @return true if the strings are the same length
     * @throws IllegalArgumentException
     */
    private static boolean checkLength(ArrayList<String> list){
        int stringSize = list.get(0).length();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).length() != stringSize){
                throw new IllegalArgumentException ("The strings in the list must all be the same size.");
                }
            }
        return true;
        }

    }

    class queueSet {
        private Queue<String> queue;
        private char theChar;
        private int ascii;

        public queueSet(int charid) {
            queue = new LinkedList<>();
            theChar = (char) charid;
            this.ascii = ascii;
        }

        public void enqueue(String toOffer) {
            queue.offer(toOffer);
        }

        public String dequeue(){
            return queue.poll();
        }

        public void clear() {
            queue.clear();
        }

        public int size(){
            return queue.size();
        }

    }
