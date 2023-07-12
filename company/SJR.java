
package com.company;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class SJR {

    static timer cpu=new timer();
    public static int NumOfP=0;
    static Process[] Processes ;
    static Queue<Process> ReadyQue = new LinkedList<Process>();
    static Queue<Process> terminated = new LinkedList<Process>();
    static int ContextSwitching;


    public static void runSJR() {

        TakeInputs();
        OrderFirstCome();

        int Time=0;
        int Co_Sw=0;
        while (terminated.size()!=NumOfP)
        {
            ReceiveAt(Time);

            if(ReadyQue.size()!=0)
                if(ReadyQue.peek().Burst<cpu.CurrentP.Burst && cpu.CurrentP.Burst>0)
                {
                    Co_Sw=ContextSwitching;
                    while (Co_Sw!=0)
                    {
                        Co_Sw--;
                        IncreaseWaitedTime();
                        Time++;
                        ReceiveAt(Time);
                    }
                    InsertInRQ(cpu.CurrentP);

                    cpu.CurrentP=ReadyQue.poll();
                }

            if(cpu.CurrentP.Burst<0)
            {
                if(ReadyQue.size()!=0)
                {
                    cpu.CurrentP=ReadyQue.poll();
                }
            }


            if(cpu.CurrentP.Burst==0)
            {
                AddToCompletedQue(cpu.CurrentP,Time);
                if(ReadyQue.size()==0)cpu.CurrentP=new Process();
                else cpu.CurrentP=ReadyQue.poll();
            }


            if(cpu.CurrentP.Burst>0)
            {
                cpu.CurrentP.Burst--;
                IncreaseWaitedTime();
            }

            Time++;
        }



        for (Process p: terminated) {
            System.out.println(p.name);
        }

        outputs();

    }


    static void AddToCompletedQue(Process p,int T)
    {
        terminated.offer(p);
        p.Completion=T;
        p.setTurnaround();
    }


    static void ReceiveAt(int arrival_T)
    {
        for (int i = 0; i <NumOfP ; i++) {
            if(Processes[i].Arrival==arrival_T)
            {
                InsertInRQ(Processes[i]);
            }
        }
    }

    static void InsertInRQ(Process p)
    {
        if(ReadyQue.size()==0){ReadyQue.offer(p);
            return;
        }

        Process temp;
        boolean inserted=false;
        int n=ReadyQue.size();

        for (int i=0;i<n;i++) {
            temp=ReadyQue.poll();
            if(p.Burst<temp.Burst&&!inserted)
            {
                inserted=true;
                ReadyQue.offer(p);
            }
            ReadyQue.offer(temp);
        }
        if(!inserted) {
            ReadyQue.offer(p);
        }

    }

    public static void OrderFirstCome() {

        for (int i = 0; i <NumOfP-1 ; i++) {
            for (int j = 0; j <NumOfP-1 ; j++) {

                if(Processes[j].Arrival>Processes[j+1].Arrival)
                {
                    Process temp=Processes[j];
                    Processes[j]=Processes[j+1];
                    Processes[j+1]=temp;
                }
            }
        }
    }


    static void TakeInputs()
    {
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter number of Processes: ");
        NumOfP = scan.nextInt();
        InitializeProcess();


        System.out.println("Context Switching: ");
        ContextSwitching=scan.nextInt();


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


    static void InitializeProcess()
    {
        Processes = new Process[NumOfP];
        for (int i = 0; i < NumOfP ; i++) {
            Processes[i]=new Process();
        }
    }



    static void outputs()
    {
        for (Process p: terminated) {
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
        for (Process p: terminated) {

            sum+=p.Turnaround;
        }

        return sum/NumOfP;
    }

    static float AverageWaitingTime()
    {
        float sum=0;
        for (Process p: terminated) {

            sum+=p.Waited;
        }

        return sum/NumOfP;
    }

    static void IncreaseWaitedTime()
    {

        for (Process p:ReadyQue) {
            p.Waited++;
        }
    }
}
