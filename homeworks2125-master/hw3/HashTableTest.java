import static org.junit.Assert.*;
import org.junit.*;

/**
 * Junit test class for HashTable. Tests both the static and non-static methods.
 * @author Evan Gary
 * @version 14 July 2017
 */

public class HashTableTest{
    private String testStr1 = "ABCDEFGHIJKL";
    private String testStr2 = "NABCXYZABCXYZV";

    @Test
    public void testStaticFindPosition(){
        assertEquals(1,HashTable.findPosition("BCDE", testStr1));
        assertEquals(4,HashTable.findPosition("XYZ", testStr2));
        assertEquals(-1,HashTable.findPosition("BCX", testStr1));
        assertEquals(0, HashTable.findPosition("A", "A"));
    }

    @Test
    public void testManualFindPosition(){
        HashTable table = new HashTable(testStr1);
        HashTable table2 = new HashTable(testStr2);
        assertEquals(0,table.findPatternPosition("A"));
        assertEquals(0,table.findPatternPosition("ABCDEFGHIJKL"));
        assertEquals(11, table.findPatternPosition("L"));
        assertEquals(0,table.findPatternPosition("A"));
        assertEquals(1,table.findPatternPosition("BCDE"));
        assertEquals(1,table2.findPatternPosition("ABC"));
    }
}