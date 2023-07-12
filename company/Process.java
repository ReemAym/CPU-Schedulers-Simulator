package com.company;

public class Process {
    Process()
    {
        name="";
        Waited=0;
        Turnaround=0;

        Arrival=-1;
        Burst=-1;
        Completion=-1;


        starving=0;

    }

    public  String name;
    public  int Arrival;
    public  int Burst;
    public  int Completion;
    public  int Turnaround;
    public  int Waited;
    public  int starving;



    public void setTurnaround() {
        Turnaround = Completion-Arrival;
    }
}
