package com.company;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

public class RR {
    static timer cpu=new timer();

    private static int NumOfP=0;
    private static int Quantum=0;

    static Process[] Processes ;
    static Queue<Process> completed = new LinkedList<Process>();
    static Queue<Process> ReadyQue = new LinkedList<Process>();
    static int ContextSwitching;


    public static void runRR() {

        TakeInputs();
        OrderFirstCome();


        int Time=0;
        int Co_Sw=0;
        while (completed.size()!=NumOfP)
        {
            ReceiveAt(Time);


            if(cpu.Timer==0&&cpu.CurrentP.Burst>0&&ReadyQue.size()!=0)
            {
                Co_Sw=ContextSwitching;

                while (Co_Sw!=0)
                {
                    Co_Sw--;
                    IncreaseWaitedTime();
                    Time++;
                    ReceiveAt(Time);
                }
            }


            if(cpu.CurrentP.Burst==0)
            {
                AddToCompletedQue(cpu.CurrentP,Time);
                cpu.Timer=0;
            }


            if(cpu.Timer==0)
            {

                if(cpu.CurrentP.Burst>0) {
                    AddToReadyQue(cpu.CurrentP);
                }

                if(ReadyQue.size()!=0) {

                    cpu.Timer = Quantum;
                    cpu.setCurrentP(ReadyQue.poll());
                }
                else {
                    cpu.CurrentP=new Process();
                    Time++;
                    continue;
                }

            }

            cpu.CurrentP.Burst--;
            cpu.Timer--;

            IncreaseWaitedTime();
            Time++;
        }


        System.out.println("");
        for (Process p:completed) {
            System.out.println(p.name);
        }
        outputs();
    }



    static void outputs()
    {
        for (Process p:completed) {
            System.out.print(p.name+" Waited for "+p.Waited);
            System.out.println(" with turnaround time of "+p.Turnaround);
        }
        System.out.println("Average Waiting Time: ");
        System.out.println(" ="+AverageWaitingTime());
        System.out.println("Average Turnaround Time: ");
        System.out.println(" ="+AverageTurnaround());
    }

    static float AverageTurnaround()
    {
        float sum=0;
        for (Process p:completed) {

            sum+=p.Turnaround;
        }

        return (float)sum/NumOfP;
    }

    static float AverageWaitingTime()
    {
        float sum=0;
        for (Process p:completed) {

            sum+=p.Waited;
        }

        return (float)sum/NumOfP;
    }

    static void IncreaseWaitedTime()
    {
        int n=ReadyQue.size();
        for (Process p:ReadyQue) {
            p.Waited++;
        }
    }

    static void AddToCompletedQue(Process p,int T)
    {
        completed.offer(p);
        p.Completion=T;
        p.setTurnaround();
    }

    static void ReceiveAt(int arrival_T)
    {

        for (int i = 0; i <NumOfP ; i++) {
            if(Processes[i].Arrival==arrival_T)
            {
                AddToReadyQue(Processes[i]);

            }
        }
    }

    static void AddToReadyQue(Process p)
    {
        ReadyQue.offer(p);
    }

    static void Swap(int i, int j) {
        Process temp;
        temp=Processes[i];
        Processes[i]=Processes[j];
        Processes[j]=temp;
    }

    private static void OrderFirstCome() {

        for (int i = 0; i <NumOfP-1 ; i++) {
            for (int j = 0; j <NumOfP-1 ; j++) {

                if(Processes[j].Arrival>Processes[j+1].Arrival)
                    Swap(j,j+1);
            }
        }
    }


    static void Initialize()
    {
        Processes = new Process[NumOfP];
        for (int i = 0; i < NumOfP ; i++) {
            Processes[i]=new Process();
        }
    }

    static void TakeInputs()
    {
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter number of Processes: ");
        NumOfP = scan.nextInt();
        System.out.println("Quantum Time: ");
        Quantum= scan.nextInt();
        System.out.println("Context Switching: ");
        ContextSwitching=scan.nextInt();


        Initialize();




        scan.nextLine();
        System.out.println("Enter processes names: ");
        for (int i = 0; i < NumOfP; i++) {
            Processes[i].name = scan.next();
        }

        System.out.println("Enter processes Arrival times: ");
        for (int i = 0; i < NumOfP; i++) {

            Processes[i].Arrival = scan.nextInt();
        }

        System.out.println("Enter processes  Burst times: ");
        for (int i = 0; i < NumOfP; i++) {
            Processes[i].Burst = scan.nextInt();
        }



    }
}
