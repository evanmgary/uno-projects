package observer;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.*;

public class DogTester{
    DogWalker bob;
    DogWalker rick;
    DogWalker morty;

    Dog butch;
    Dog fifi;
    Dog spike;

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setup(){
        bob = new DogWalker("Bob");
        rick = new DogWalker("Rick");
        morty = new DogWalker("Morty");

        butch = new Dog("Butch");
        fifi = new Dog("Fifi");
        spike = new Dog("Spike");

        butch.addObserver(bob);
        fifi.addObserver(bob);
        fifi.addObserver(rick);
        spike.addObserver(rick);
        spike.addObserver(morty);

        System.setOut(new PrintStream(output));
    }

    @Test
    public void testButchPoop(){
        butch.poop();
        assertEquals("I'm pooping!!\r\nHey, Bob has been notified that Butch has pooped!\r\n", output.toString());
    }

    @Test
    public void testFifiPoop(){
        fifi.poop();
        assertEquals("I'm pooping!!\r\nHey, Bob has been notified that Fifi has pooped!\r\nHey, Rick has been notified that Fifi has pooped!\r\n", output.toString());
    }

    @Test
    public void testSpikePoop(){
        spike.poop();
        assertEquals("I'm pooping!!\r\nHey, Rick has been notified that Spike has pooped!\r\nHey, Morty has been notified that Spike has pooped!\r\n", output.toString());
    }

    @After
    public void resetStreams(){
        System.setOut(System.out);
    }

}