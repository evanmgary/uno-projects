package observer;

import java.util.ArrayList;

public class MyObservable{

    private ArrayList<MyObserver> list = new ArrayList<>();
    private boolean hasChanged = false;

    void addObserver(MyObserver o){
        list.add(o);
    }

    protected void clearChanged(){
        hasChanged = false;
    }

    int countObservers(MyObserver o){
        return list.size();
    }

    void deleteObserver(MyObserver o){
        list.remove(o);
    }

    void deleteObservers(){
        list.clear();
    }

    boolean hasChanged(){
        return hasChanged;
    }

    void notifyObservers(){
        if (hasChanged()) {
            for (int i= 0; i < list.size(); i++) {
                list.get(i).update(this, null);
            }
            clearChanged();
        }
    }

    void notifyObservers(Object arg){
        for(MyObserver observers:list){
            observers.update(this, arg);
        }
    }

    protected void setChanged(){
        hasChanged = true;
    }

}