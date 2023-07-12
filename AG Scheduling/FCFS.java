package point4;
import java.util.ArrayList;
import java.util.Scanner;
public class FCFS {

    public static void FcfsShcedule(ArrayList<prcoess>p , int n )
    {
        FCFS.findWaitingTime(p,n);
        FCFS.findTurnAroundTime(p,n);
        FCFS.display(p,n);
    }

    public static void findWaitingTime(ArrayList<prcoess> p, int n ) {
        //wait = new ArrayList<>(n);
        for(int i =0 ;  i< n ; i++)
        {
            if(i == 0)
                p.get(i).setWaitingTime(0);
            else
                p.get(i).setWaitingTime(p.get(i-1).getBurstTime() + p.get(i-1).getWaitingTime());
            // wait.set(i,  p.get(i-1).getBurstTime()+ wait.get(i - 1));
            //p.get(i).setWaitingTime(wait.get(i));
        }
    }

    public static void findTurnAroundTime(ArrayList<prcoess> p, int n){
        for(int i = 0 ; i < n ; i++)
        {
            int t = 0;
            t = p.get(i).getWaitingTime() + p.get(i).getBurstTime();
            p.get(i).setTurnAroundTime(t);
            //TotalTurnaround.set(i, wait.get(i) + burst.get(i));
            //p.get(i).setTurnAroundTime(TotalTurnaround.get(i));
        }
    }


    public static void display(ArrayList<prcoess> p, int n )
    {
        int  twt = prcoess.TotalWaitnig(p,n), t_tat = prcoess.TotalTurnAround(p,n);

        for (int  i=0; i<n; i++)
        {
            System.out.print("process " + p.get(i).getName() +" has burst time equal " + p.get(i).getBurstTime() +" and waiting time equal ");
            System.out.println(p.get(i).getWaitingTime()+" and turn around time equal " + p.get(i).getTurnAroundTime()+" ");
        }
        System.out.println("Total waiting time = " + (double)twt /(double) n);
        System.out.println("Total turn around  time = " + (double)t_tat/(double)n);
    }

    public static void run_FCFC()
    {
        Scanner sc =new Scanner(System.in);
        int n , at = 0 , bt = 0;
        System.out.println("please enter the number of processes");
        n = sc.nextInt();
        ArrayList<prcoess>p = new ArrayList<>();
        String name = "";
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0 ; i< n ; i++)
        {
            System.out.println("this the data of the process number "+(i+1));

            System.out.println("please enter the arrival time ");
            at = sc.nextInt();
            System.out.println("please enter the burst time of the process");
            bt = sc.nextInt();
            System.out.println("please enter the name of the process");
            sc.nextLine();
            name =sc.nextLine();
            prcoess pro =new prcoess(at,bt ,name);
            p.add(pro);
        }
        FCFS.FcfsShcedule(p,n);
    }
}
