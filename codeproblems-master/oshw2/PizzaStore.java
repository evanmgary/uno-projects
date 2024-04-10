import java.lang.annotation.Documented;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pizza store program for problem 1.
 * @author Evan Gary
 */
public class PizzaStore {


    private static LinkedList<Pizza> ordered;

    private static StoreResource oven;
    private static StoreResource qcDesk;

    private static Semaphore orderedMutex;
    private static Semaphore ovenMutex;
    private static Semaphore qcMutex;


    private static int orderedPizzas;
    private static AtomicInteger deliveredPizzas;


    public static void main(String[] args){
        //Required to build the inner class objects from the static main method
        PizzaStore store = new PizzaStore();

        if (args.length == 0){
            orderedPizzas = 20;
        }
        else if(args.length == 1){
            orderedPizzas = Integer.parseInt(args[0]);
        }
        else{
            System.err.println("Usage: number_of_pizzas");
            System.exit(1);
        }

        //The store workers are the threads
        Thread pA = store.new PersonA();
        Thread pB = store.new PersonB();
        Thread pC = store.new PersonC();

        //Store Resources: the order list, the oven, and the QC desk
        //The order list can have any number of pizzas
        //The oven and QC desk can store only one
        ordered = new LinkedList<>();
        oven = store.new StoreResource();
        qcDesk = store.new StoreResource();

        //Semaphores controlling access to the resources
        //Because the person threads and oven/desk resources can only work with one pizza at a time, they are binary
        orderedMutex = new Semaphore(1);
        ovenMutex = new Semaphore(1);
        qcMutex = new Semaphore(1);


        orderedPizzas = 20;
        deliveredPizzas = new AtomicInteger(0);
        //Add the pizzas to the queue
        for (int i = 1; i <= orderedPizzas; i++){
            ordered.add(store.new Pizza(i));
        }

        //Start the worker threads
        pA.start();
        pB.start();
        pC.start();

        try {
            pA.join();
            pB.join();
            pC.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    // The oven and QC desk are made from this class. It holds one pizza and can only accept, check, or give out
    // that pizza. It is the shared resource controlled by the mutex semaphores.
    class StoreResource{

       private Pizza pizza;

       public StoreResource(){
           pizza = null;
       }

       public void givePizza(Pizza p){
           pizza = p;
       }

       public Pizza getPizza(){
           Pizza temp = pizza;
           pizza = null;
           return temp;
       }

       public boolean hasPizza(){
           if (pizza == null){
               return false;
           }
           else{
               return true;
           }
       }

    }

    class PersonA extends Thread{

        @Override
        public void run(){
            //Stop once all pizzas are delivered
            while(deliveredPizzas.get() < orderedPizzas){
                try {
                    //In order to use the shared resources, the relevant locks need to be acquired
                    ovenMutex.acquire();
                    orderedMutex.acquire();
                    doWork();
                    //Once the work is done they can be released
                    orderedMutex.release();
                    ovenMutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //The critical region
        public void doWork(){
            if (!oven.hasPizza() && !ordered.isEmpty()) {

                oven.givePizza(ordered.poll());
                System.out.println("Pizza " + oven.pizza.num + " put in oven.");
            }
        }
    }//end PersonA thread

    //Logic for persons B and C is largely the same as A
    class PersonB extends Thread{

        @Override
        public void run(){
            while(deliveredPizzas.get() < orderedPizzas){

                try {
                    ovenMutex.acquire();
                    qcMutex.acquire();
                    doWork();
                    ovenMutex.release();
                    qcMutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public void doWork(){
            if (oven.hasPizza() && !qcDesk.hasPizza()) {

                qcDesk.givePizza(oven.getPizza());
                System.out.println("Pizza " + qcDesk.pizza.num + " given to QC.");
            }
        }

    }


    class PersonC extends Thread{

        @Override
        public void run(){
            while(deliveredPizzas.get() < orderedPizzas){

                try {
                    qcMutex.acquire();
                    doWork();
                    qcMutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public void doWork(){
            if (qcDesk.hasPizza()) {
                Pizza delivered = qcDesk.getPizza();
                deliveredPizzas.incrementAndGet();
                System.out.println(delivered.getNum() + " is ready.");
            }
        }

    }

    //Pizza objects are passed through the program, each holds an order number for uniqueness
    class Pizza{

        private int num;

        public Pizza(int num){

            this.num = num;
        }

        public int getNum(){
            return num;
        }
    }
}


