package observer;
import java.util.*;

public class Dog extends MyObservable {

    private String name;
    private boolean hasPooped;

    public Dog(String name) {

        this.name = name;

    }

    public String getName() {
        return this.name;
    }

    public void poop() {
        System.out.println("I'm pooping!!");
        hasPooped = true;
        setChanged();
        notifyObservers(name + " has pooped!");
    }

}
