import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A test for the GroceryList class.
 */
public class GroceryListTest {

    /** An initially empty list. */
    private GroceryList initiallyEmpty;

    /** A list with Eggs, Paper Towels, and Bread. */
    private GroceryList simpleList;

    @Before
    public void setupFixtures(){
        // Setup empty list.
        this.initiallyEmpty = new GroceryList();

        // Setup simple list.
        this.simpleList = new GroceryList();
        this.simpleList.addItem("eggs");
        this.simpleList.addItem("  paper towels ");
        this.simpleList.addItem("  BREAD        ");
    }

    @Test
    public void newListShouldBeEmpty(){
        assertTrue(this.initiallyEmpty.getItemCount() == 0);
    }

    @Test
    public void listsShouldBeSerializable(){
        assertTrue(this.initiallyEmpty instanceof java.io.Serializable);
    }

    @Test
    public void getItemAtIndexShouldWork(){
        assertEquals("Eggs",this.simpleList.getItemAtIndex(0));
        assertEquals("Paper Towels",this.simpleList.getItemAtIndex(1));
        assertEquals("Bread",this.simpleList.getItemAtIndex(2));
    }

    @Test
    public void getItemCountShouldWork(){
        // Initially empty.
        assertEquals(0,this.initiallyEmpty.getItemCount());
        assertEquals(3,this.simpleList.getItemCount());

        // Add some items.
        this.simpleList.addItem("Milk");
        assertEquals(4,this.simpleList.getItemCount());
        this.simpleList.addItem("Cereal");
        assertEquals(5,this.simpleList.getItemCount());
        this.simpleList.addItem("Yogurt");
        assertEquals(6,this.simpleList.getItemCount());

        // Remove some items.
        this.simpleList.removeItemAtIndex(0);
        assertEquals(5,this.simpleList.getItemCount());
        this.simpleList.removeItemAtIndex(0);
        assertEquals(4,this.simpleList.getItemCount());
        this.simpleList.removeItemAtIndex(0);
        assertEquals(3,this.simpleList.getItemCount());
    }

    public void addItemShouldWork(){
        // Should not be able to add if already in the list.
        this.simpleList.addItem("eggs"); // No effect.
        assertEquals(3,this.simpleList.getItemCount());
        assertEquals("Eggs",this.simpleList.getItemAtIndex(0)); // Should still be in this position.
        this.simpleList.addItem("paper towels"); // No effect.
        assertEquals(3,this.simpleList.getItemCount());
        assertEquals("Paper Towels",this.simpleList.getItemAtIndex(1)); // Should still be in this position.
        this.simpleList.addItem("      bread"); // No effect.
        assertEquals(3,this.simpleList.getItemCount());
        assertEquals("Bread",this.simpleList.getItemAtIndex(2)); // Should still be in this position.

        // Can't add empty text.
        this.simpleList.addItem("  "); // No effect.
        assertEquals(3,this.simpleList.getItemCount());

        this.simpleList.addItem("EGGS"); // No effect.
        assertEquals(3,this.simpleList.getItemCount());
        this.simpleList.addItem(" bread"); // No effect.
        assertEquals(3,this.simpleList.getItemCount());

        // Should always add to end of list.
        this.simpleList.addItem("beans");
        assertEquals("Beans",this.simpleList.getItemAtIndex(3));
        this.simpleList.addItem("mustard");
        assertEquals("Mustard",this.simpleList.getItemAtIndex(4));
        this.simpleList.addItem("    hot SAUCE");
        assertEquals("Hot Sauce",this.simpleList.getItemAtIndex(5));
    }

    @Test
    public void removeItemAtIndexShouldWork(){
        this.simpleList.removeItemAtIndex(0);
        assertEquals(2,this.simpleList.getItemCount());
        assertEquals("Paper Towels",this.simpleList.getItemAtIndex(0));
        this.simpleList.removeItemAtIndex(0);
        assertEquals(1,this.simpleList.getItemCount());
        assertEquals("Bread",this.simpleList.getItemAtIndex(0));
        this.simpleList.removeItemAtIndex(0);
        assertEquals(0,this.simpleList.getItemCount());
    }

    @Test
    public void toStringShouldWork(){
        assertEquals("(Empty List)",this.initiallyEmpty.toString());
        assertEquals("1. Eggs\n2. Paper Towels\n3. Bread",this.simpleList.toString());
        this.simpleList.addItem("Mustard");
        assertEquals("1. Eggs\n2. Paper Towels\n3. Bread\n4. Mustard",this.simpleList.toString());
    }

    @Test
    public void getItemAtIndexShouldThrowProperException(){
        try{
            String item = this.initiallyEmpty.getItemAtIndex(0);
            fail("Should not be able to get an item from an empty list.");
        } catch (IllegalArgumentException e){
            // Good.
        } catch (Exception e){
            fail("getItemAtIndex did not throw proper Exception type.");
        }
    }

    @Test
    public void removeItemAtIndexShouldThrowProperException(){
        try{
            this.initiallyEmpty.removeItemAtIndex(0);
            fail("Should not be able to get an item from an empty list.");
        } catch (IllegalArgumentException e){
            // Good.
        } catch (Exception e){
            fail("removeItemAtIndex did not throw proper Exception type.");
        }
    }

}
