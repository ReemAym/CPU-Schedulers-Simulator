package point4;
import java.util.ArrayList;

public class prcoess {
    private String name;
    private int ArrivalTime;
    private int BurstTime;
    private int WaitingTime =0 ;
    private int TurnAroundTime =0 ;
    private int priority = 0;
    private int QuantumTime=0;
    private int tqt = 0; // template quantum time
    private int completion = 0;
    private double AverageWaitingTime =0.0;

    private double AverageTurnAroundTime = 0.0;


    prcoess(){
        this.name = "";
        this.ArrivalTime =0;
        this.BurstTime = 0;
        this.WaitingTime = 0;
        this.TurnAroundTime = 0;
        this.AverageWaitingTime = 0.0;
        this.AverageTurnAroundTime = 0.0;
        this.priority = 0;
        this.QuantumTime =0;
    }
    prcoess(int ArrivalTime , int BurstTime , String name){
        this.name = name;
        this.ArrivalTime =ArrivalTime;
        this.BurstTime = BurstTime;
        this.WaitingTime = 0;
        this.TurnAroundTime = 0;
        this.AverageWaitingTime = 0.0;
        this.AverageTurnAroundTime = 0.0;
    }
    prcoess(int ArrivalTime , int BurstTime , String name , int priority , int QuantumTime){
        this.name = name;
        this.ArrivalTime =ArrivalTime;
        this.BurstTime = BurstTime;
        this.WaitingTime = 0;
        this.TurnAroundTime = 0;
        this.AverageWaitingTime = 0.0;
        this.AverageTurnAroundTime = 0.0;
        this.priority = priority;
        this.QuantumTime =QuantumTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        TurnAroundTime = turnAroundTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.ArrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        BurstTime = burstTime;
    }

    public void setWaitingTime(int waitingTime) {
        WaitingTime = waitingTime;
    }

    public void setQuantumTime(int quantumTime) {
        QuantumTime = quantumTime;
    }
    public static int TotalTurnAround(ArrayList<prcoess> p, int n)
    {
        int result = 0;
        for(int i = 0 ; i < n ; i++)
        {
            result += p.get(i).getTurnAroundTime();
        }
        return result;
    }
    public static int TotalWaitnig(ArrayList<prcoess> p, int n)
    {
        int result = 0;
        for(int i = 0 ; i < n ; i++)
        {
            result += p.get(i).getWaitingTime();
        }
        return result;
    }

    public static int calculate_turnaround_time(ArrayList<prcoess> processe){
        int result = 0;
        for(prcoess i : processe){
            i.setTurnAroundTime(i.getCompletion()  - i.getArrivalTime());
            result += (i.TurnAroundTime);
        }
        return result;
    }
    public static int calculate_waiting_time(ArrayList<prcoess>processes){
        int result = 0 ;
        for(prcoess i : processes){
            i.setWaitingTime(i.TurnAroundTime - i.BurstTime);
            result += (i.WaitingTime);
        }
        return result;
    }


    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return ArrivalTime;
    }

    public int getBurstTime() {
        return BurstTime;
    }

    public int getWaitingTime() {
        return WaitingTime;
    }

    public int getTurnAroundTime() {
        return TurnAroundTime;
    }

    public double getAverageWaitingTime() {
        return AverageWaitingTime;
    }

    public double getAverageTurnAroundTime() {
        return AverageTurnAroundTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getQuantumTime() {
        return QuantumTime;
    }


    public int getTqt() {
        return tqt;
    }

    public void setTqt(int tqt) {
        this.tqt = tqt;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }
}
