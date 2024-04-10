/**
 * Evan Gary-HW3
 * Problem 2 Runner class
 */

public class ScheduleRunner {

    public static void main(String[] args){
        Scheduler fcfs = new Scheduler(1);
        Scheduler sjf = new Scheduler(2);
        Scheduler srtn = new Scheduler(3);


        fcfs.addProcess(0, new SimProcess(0,10));
        fcfs.addProcess(1, new SimProcess(1,29));
        fcfs.addProcess(2, new SimProcess(2,8));
        fcfs.addProcess(3, new SimProcess(3,4));

        sjf.addProcess(0, new SimProcess(0,10));
        sjf.addProcess(1, new SimProcess(1,29));
        sjf.addProcess(2, new SimProcess(2,8));
        sjf.addProcess(3, new SimProcess(3,4));

        srtn.addProcess(0, new SimProcess(0,10));
        srtn.addProcess(1, new SimProcess(1,29));
        srtn.addProcess(2, new SimProcess(2,8));
        srtn.addProcess(3, new SimProcess(3,4));

        System.out.println("FCFS algorithm starting.");
        fcfs.start();
        System.out.println("Average turnaround for FCFS:" + fcfs.getAvgTurnaround());
        System.out.println("SJF algorithm starting.");
        sjf.start();
        System.out.println("Average turnaround for SJF:" + sjf.getAvgTurnaround());
        System.out.println("SRTN algorithm starting.");
        srtn.start();
        System.out.println("Average turnaround for SRTN:" + srtn.getAvgTurnaround());
    }
}
