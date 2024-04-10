import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bathrooms {

    //These are synchronization counters to see how many men and women are in the bathroom
    static AtomicInteger numMen;
    static AtomicInteger numWomen;
    //List used to create and start the threads
    static ArrayList<Thread> persons = new ArrayList<>();
    //false = unlocked, true = locked
    static Semaphore mutex;
    static Semaphore wMutex;
    static Semaphore mMutex;

    static Lock lock = new ReentrantLock();
    //static Lock womenLock = new ReentrantLock();
    static Condition allowMen = lock.newCondition();
    static Condition allowWomen = lock.newCondition();

    public static void main(String[] args) {


        int numPersons;
        if (args.length == 0) {
            numPersons = 20;
        } else if (args.length == 1) {
            numPersons = Integer.parseInt(args[0]);
        } else {
            System.err.println("Usage: number_of_people");
            System.exit(1);
        }

        Bathrooms bathroom = new Bathrooms();
        numMen = new AtomicInteger(0);
        numWomen = new AtomicInteger(0);
        //Use a binary semaphore for synchronization
        mutex = new Semaphore(1);
        //numPersons is the number of person threads to generate


        //each person has a 50% chance to be a man or woman
        for (numPersons = 20; numPersons > 0; numPersons--) {
            if (Math.random() < 0.5) {
                persons.add(bathroom.new Man());

            } else {
                persons.add(bathroom.new Woman());
            }

        }

        //Start all the threads (makes the "line" to get in the bathroom)
        for (Thread person : persons) {
            person.start();
        }

    }

    class Man extends Thread {

        @Override
        public void run() {
            lock.lock();
            try {

                manWantsToEnter();
                Thread.sleep(1000);
                mutex.acquire();//enter critical region
                manLeaves();
                mutex.release();//leave critical region
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            finally{
                lock.unlock();
            }

        }

        public void manWantsToEnter() throws InterruptedException{

            /*
            while (numWomen.get() > 0 ) {
                try {
                    //I used this for testing, but printf isn't actually thread safe, so it is commented out here
                    //It actually does work as expected in spite of this
                    //Remove the comment if you want to see
                    //The other printf statements are part of the critical regions
                    //System.out.printf("Man can't enter. Men:%d Women:%d%n", numMen, numWomen);
                    Thread.sleep(10);

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            */
            while(numWomen.get() > 0){
                allowMen.await();
            }

            mutex.acquire();//enter critical region
            if (numWomen.get() == 0) {
                numMen.incrementAndGet();
                System.out.printf("Man enters. Men:%d Women:%d%n", numMen.get(), numWomen.get());
            }
            mutex.release();//leave critical region

        }

        //note that this is always called as part of a critical region
        public void manLeaves() throws InterruptedException {
            if (numMen.get() > 0) {
                numMen.decrementAndGet();
                System.out.printf("Man leaves. Men:%d Women:%d%n", numMen.get(), numWomen.get());
                if (numMen.get() == 0){
                    allowWomen.signalAll();
                }
            }
        }

    }

    //Methods for Woman work the same as those for Man
    class Woman extends Thread {

        @Override
        public void run(){
            lock.lock();
            try {
                womanWantsToEnter();
                Thread.sleep(1000);
                mutex.acquire();
                womanLeaves();
                mutex.release();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            finally{
                lock.unlock();
            }
        }

        public void womanWantsToEnter() throws InterruptedException{
            //women can't enter when there are men
            while (numMen.get() > 0) {
                /*
                try {
                    //System.out.printf("Woman can't enter. Men:%d Women:%d%n", numMen, numWomen);
                    Thread.sleep(10);

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                */
                allowWomen.await();
            }
            mutex.acquire();
            numWomen.incrementAndGet();
            System.out.printf("Woman enters. Men:%d Women:%d%n", numMen.get(), numWomen.get());
            mutex.release();

        }

        public void womanLeaves() throws InterruptedException {

            if (numWomen.get() > 0) {
                numWomen.decrementAndGet();
                System.out.printf("Woman leaves. Men:%d Women:%d%n", numMen.get(), numWomen.get());
                if (numWomen.get() == 0){
                    allowMen.signalAll();
                }
            }
        }

    }
}

