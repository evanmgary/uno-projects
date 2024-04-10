import java.util.HashMap;
import java.util.LinkedList;

/**
 * Evan Gary-HW3
 * Problem 2 Scheduler and Process classes
 */
public class Scheduler {

    private int counter;
    private LinkedList<SimProcess> procQueue;
    private SimProcess currentProcess;
    private int algo;
    //Limitation that only one process can be added at any given time.
    //It works fine to test the algorithms
    //To fix I could make it a hashmap of linked lists that contain processes, but this is not necessary now
    private HashMap<Integer, SimProcess> toAdd;
    //These are used for calculating turnaround
    private HashMap<Integer, Integer> timeAdded;
    private HashMap<Integer, Integer> timeCompleted;
    private LinkedList<Integer> turnaroundTimes;

    /**
     * Constructor, initializes counter and lists.
     * @param algo The algorithm this scheduler uses.
     */
    public Scheduler(int algo) {
        //counter represents time
        counter = 0;
        procQueue = new LinkedList<>();
        currentProcess = null;
        if (algo < 1 || algo > 3) {
            throw new IllegalArgumentException();
        }
        this.algo = algo;
        toAdd = new HashMap<>();
        timeAdded = new HashMap<>();
        timeCompleted = new HashMap<>();
        turnaroundTimes = new LinkedList<>();
    }

    /**
     * Start the scheduler at time 0.
     *
     */
    public void start() {
        //An actual scheduler would want to run indefinitely, but this one ends when there are no more processes left to work on
        while (!procQueue.isEmpty() || !toAdd.isEmpty() || currentProcess != null) {
            // Add process to process queue if one arrives at the counter's time
            //procAdded used only in srtn
            boolean procAdded = false;
            if (toAdd.containsKey(counter)) {
                timeAdded.put(toAdd.get(counter).getPid(), counter);
                //When srtn is used, the scheduler needs to know that a process was added
                //so that it runs the algorithm even when the current process is empty
                if (algo == 3){
                    procAdded = true;
                }
                procQueue.offerLast(toAdd.get(counter));
                System.out.printf("At time %d process %d arrives and is added to queue:%s%n", counter, toAdd.get(counter).getPid(), printQueue());
                //Remove process from add hashmap. Necessary for the while loop to terminate when all everything is done
                toAdd.remove(counter);


            }

            //Give the current process "CPU time"
            if (currentProcess != null) {
                currentProcess.progress();
                // Remove the process from the scheduler if it completes
                if (currentProcess.isDone()) {
                    //Calculate turnaround when process is done
                    timeCompleted.put(currentProcess.getPid(), counter);
                    int turnaround = timeCompleted.get(currentProcess.getPid()) - timeAdded.get(currentProcess.getPid());
                    System.out.printf("At time %d process %d completed (turnaround: %d)%n", counter, currentProcess.getPid(), turnaround);
                    turnaroundTimes.add(turnaround);
                    currentProcess = null;
                }
            }

            //Decide which algorithm to use
            switch (algo) {
                case 1:
                    fcfsAlgo();
                    break;
                case 2:
                    sjfAlgo();
                    break;
                case 3:
                    if (!procQueue.isEmpty() && (currentProcess == null || procAdded)) {
                        srtnAlgo();
                    }
                    break;
                default:
                    throw new IllegalStateException();
            }
            //Increment the counter on every iteration
            counter++;
        }
    }

    /**
     * First come first served algorithm.
     * Uses the queue property to manage processes. Runs when there is no process running.
     */
    private void fcfsAlgo(){
        if (currentProcess == null && !procQueue.isEmpty()) {
            currentProcess = procQueue.poll();
            System.out.printf("At time %d process %d started. Queue:%s%n", counter, currentProcess.getPid(), printQueue());
        }
    }

    /**
     * Shortest job first algorithm.
     * Iterates through the queue(list) to find the shortest process, starts it, then removes it from the queue.
     */
    private void sjfAlgo(){
        if (currentProcess == null && !procQueue.isEmpty()) {
            SimProcess shortest = procQueue.get(0);
            for(SimProcess p:procQueue){
                if(p.getTimeLeft() < shortest.getTimeLeft()){
                    shortest = p;
                }
            }
            //"Start" the process
            currentProcess = shortest;
            procQueue.remove(shortest);
            System.out.printf("At time %d process %d started. Queue:%s%n", counter, currentProcess.getPid(), printQueue());
        }
    }

    /**
     * Shortest remaining time next algorithm.
     * Iterates through the queue comparing remaining time for each process.
     * Compare shortest from with queue with current process when applicable.
     * If current process is not shortest, replace it.
     */
    private void srtnAlgo(){
        SimProcess shortest = procQueue.peekFirst();
        for(SimProcess p:procQueue){
            if(p.getTimeLeft() < shortest.getTimeLeft()){
                shortest = p;
            }
        }
        if (currentProcess != null && currentProcess.getTimeLeft() < shortest.getTimeLeft()){
            shortest = currentProcess;
        }
        if (currentProcess == null){
            currentProcess = shortest;
            procQueue.remove(shortest);
            System.out.printf("At time %d process %d started with %d time remaining. Queue:%s%n", counter, currentProcess.getPid(),
                    currentProcess.getTimeLeft(), printQueue());

        }
        else if (shortest != currentProcess){
            procQueue.offer(currentProcess);
            System.out.printf("At time %d process %d returned to queue with %d time remaining. Queue:%s%n", counter, currentProcess.getPid(),
                    currentProcess.getTimeLeft(),printQueue());
            currentProcess = shortest;
            procQueue.remove(shortest);
            System.out.printf("At time %d process %d started with %d time remaining. Queue: %s%n", counter, currentProcess.getPid(),
                    currentProcess.getTimeLeft(),printQueue());
        }

    }

    /**
     * Fill the toAdd hashmap with processes
     * @param startTime The start time of the process
     * @param newProc The process to add
     */
    public void addProcess(int startTime, SimProcess newProc){
        toAdd.put(startTime, newProc);
    }

    /**
     * Print the queue as a string.
     * @return The queue.
     */
    private String printQueue(){
        StringBuilder s = new StringBuilder();
        s.append("[");
        for(int i = procQueue.size(); i > 0; i--){
            s.append(procQueue.get(i-1).getPid());
            if (i > 1)
                s.append(" ");
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Method to get the average turnaround.
     * @return The average turnaround for all processes added to this scheduler.
     */
    public double getAvgTurnaround(){
        double totalTurnaround = 0.0;
        for (Integer val:turnaroundTimes){
            totalTurnaround += val;
        }
        return totalTurnaround / turnaroundTimes.size();
    }

}

/**
 * Process class to simulate a process.
 * The intention of this is to make the processes as dumb as possible.
 * The scheduler should handle most of the details.
 * All the process knows about is its runtime, remaining time, and id.
 */
class SimProcess{

    private int pid;
    private boolean isDone;
    private int runTime;
    private int timeLeft;

    public SimProcess(int pid, int runTime){
        this.pid = pid;
        isDone = false;
        this.runTime = runTime;
        timeLeft = runTime;
    }

    public void progress(){
        timeLeft--;
        if (timeLeft == 0){
            isDone = true;
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public int getPid() {
        return pid;
    }

    public int getRunTime() {
        return runTime;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}

