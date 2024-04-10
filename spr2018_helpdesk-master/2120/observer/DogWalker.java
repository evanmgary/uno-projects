package observer;
import java.util.*;

public class DogWalker implements MyObserver {

    private String name;

    public DogWalker(String name) {
        this.name = name;
    }

    public void update(MyObservable o, Object arg) {

        System.out.println("Hey, " + name + " has been notified that " + arg);

    }

}