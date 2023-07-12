package point4;
import java.util.*;

import static java.lang.Math.ceil;
public class AG {
    private int totaltime;
    private prcoess CurrentProcess;
    private static int temp = 0;// size or ready queue
    Queue<prcoess>readyqueu , terminatedqueue;
    public AG(){
        this.totaltime = 0;
        CurrentProcess = new prcoess();
        CurrentProcess =null;
        readyqueu = new LinkedList<>();
        terminatedqueue = new LinkedList<>();
    }

    public void run(){
        Scanner sc =new Scanner(System.in);
        int n , at = 0 , bt = 0,prio ,quan;
        System.out.println("please enter the number of processes");
        n = sc.nextInt();
        ArrayList<prcoess>p = new ArrayList<>();
        String name = "";
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0 ; i< n ; i++)
        {
            System.out.println("this the data of the process number "+(i+1));
            System.out.println("please enter the name of the process");
            sc.nextLine();
            name =sc.nextLine();

            System.out.println("please enter the burst time of the process");
            bt = sc.nextInt();

            System.out.println("please enter the arrival time ");
            at = sc.nextInt();


            System.out.println("please enter the priority of the process ");

            prio = sc.nextInt();
            System.out.println("please enter the quntum time of the process ");
            quan = sc.nextInt();
            prcoess pro =new prcoess(at,bt ,name,prio ,quan);
            p.add(pro);
        }
        AG schdule = new AG();
        schdule.AgShcedule(p);
        // System.out.println("the average turn around time equal: " + (double)(prcoess.calculate_turnaround_time(p) / p.size()));
        // System.out.println("the average waiting time equal: " + (double)(prcoess.calculate_waiting_time(p) / p.size()));
//        for (prcoess i:p) {
//            System.out.println("the waiting time of processes " + i.getName() + " = " + i.getWaitingTime());
//            System.out.println("the turn around time of process " + i.getName() + " = " + i.getTurnAroundTime());
//        }
        this.ShowFinalAllWaitingTime(p);
        this.ShowFinalAllTurnaroundTime(p);
    }

    public void AgShcedule(ArrayList<prcoess> processes)
    {

        if(CurrentProcess == null && readyqueu.isEmpty())
        {
            CurrentProcess = processes.get(0);
            ApplyFcfs(CurrentProcess,processes);
            //ApplyProority(CurrentProcess,readyqueu , processes);
            //ApplyShortestJobFirst(CurrentProcess , readyqueu ,processes);
        }

        /*for(prcoess i : processes)
        {
            if(CurrentProcess == null && readyqueu.isEmpty()){
                CurrentProcess = i;
                ApplyFcfs(i,processes);
                //check_arrival_time();
                ApplyProority(i,readyqueu , processes);
                ApplyShortestJobFirst(i , readyqueu ,processes);
            }
            else if( CurrentProcess == i)
            {
                ApplyFcfs(i ,processes);
                //check_arrival_time();
                ApplyProority(i,readyqueu , processes);
                ApplyShortestJobFirst(i , readyqueu,processes);
            }
            else if( CurrentProcess != null && CurrentProcess != i && !readyqueu.contains(i)){
                readyqueu.add(i); temp++;
            }
        }
*/

    }
    public void active_current_process(prcoess active , ArrayList<prcoess>processes)
    {
        if(terminatedqueue.size() == processes.size())return;
        ApplyFcfs(active,processes);
    }

    private void check_arrival_time1(ArrayList<prcoess> processes , int current_time)
    {
        for( prcoess i : processes)
        {
            if(i.getArrivalTime() < current_time && !readyqueu.contains(i) && CurrentProcess != i && !terminatedqueue.contains(i) ) readyqueu.add(i);
        }


    }

    private void check_arrival_time2(ArrayList<prcoess> processes , int current_time)
    {
        for( prcoess i : processes)
        {
            if(i.getArrivalTime() == current_time && !readyqueu.contains(i)) readyqueu.add(i);
        }

    }

    private void set_all_proccesses_waiting_time(ArrayList<prcoess> processes , int t)
    {
        if(!readyqueu.isEmpty())
        {
            for ( prcoess i: processes) {
                if(readyqueu.contains(i))i.setWaitingTime(i.getWaitingTime() + (totaltime - t));
            }
        }
    }

    public void ApplyFcfs(prcoess current ,ArrayList<prcoess> processes)
    {
        if(CurrentProcess != null){
            int t =totaltime;
            int qt;
            qt = calculate_FCFC_time(current.getQuantumTime());
            if(current.getQuantumTime() >= current.getBurstTime() && qt >= current.getBurstTime()){
                totaltime += current.getBurstTime();
                current.setBurstTime(0);
                current.setTqt(current.getTqt() - current.getBurstTime());
            }
            else {
                current.setBurstTime(current.getBurstTime() - qt);
                totaltime += qt;
                current.setTqt(current.getQuantumTime()-qt);
            }

            System.out.println("process " + current.getName()+" has executed from second " + t +" to second " + totaltime);
            set_all_proccesses_waiting_time(processes,t);
            check_arrival_time1(processes,totaltime);
            if(check_if_finished(current))AfterChecking(current,processes);

            if(current.getBurstTime() > 0 )ApplyProority(current,readyqueu , processes);

        }

    }

    public void ApplyProority(prcoess current , Queue<prcoess>ReadyQueue , ArrayList<prcoess> processes)
    {
        if(CurrentProcess != null){
            int target = Integer.MAX_VALUE;
            if(!readyqueu.isEmpty())
                target = GetMaxPriorityProcess(ReadyQueue);
            int qt;
            check_arrival_time2(processes,totaltime);
            if( current.getPriority() > target ){

           /* qt = calculate_FCFC_time(current.getQuantumTime());
            current.setBurstTime(current.getBurstTime() - qt);
            totaltime += qt;
            current.setTqt(current.getQuantumTime() - qt);*/
                System.out.println("the quantum time of process " + current.getName()+" was : " + current.getQuantumTime());
                // System.out.println("the variable Quantum time equal: " + current.getTqt());
                double k = ceil((double)current.getTqt()/2);
                current.setQuantumTime((int) (current.getQuantumTime() +k));
                System.out.println("the Quantum time of process " + current.getName() +" became : " + current.getQuantumTime());
                if(check_if_finished(current))AfterChecking(current,processes);
                else{
                    readyqueu.add(current);
                    temp++;
                    CurrentProcess = Search_priority(target , ReadyQueue);
                    readyqueu.remove(Search_priority(target , ReadyQueue));
                }
                active_current_process(CurrentProcess , processes);

            }
            else if(current.getPriority() < target)
            {
                int t = totaltime;
                qt = calculate_FCFC_time(current.getQuantumTime());
                int qt2 = calculate_priority_time(current.getQuantumTime());

                if(current.getQuantumTime() >= current.getBurstTime() && (qt2 - qt) >= current.getBurstTime()){
                    totaltime += current.getBurstTime();
                    current.setBurstTime(0);
                    current.setTqt(current.getTqt() - current.getBurstTime());
                }
                else{
                    if((current.getTqt()>= (qt2-qt))  )
                    {totaltime -= qt;
                        totaltime += qt2;
                        current.setBurstTime(current.getBurstTime() + qt);
                        current.setBurstTime(current.getBurstTime() - qt2);
                        current.setTqt(current.getQuantumTime()-qt2);}
                    else{
                        readyqueu.add(current);
                        temp++;
                        current.setQuantumTime(current.getQuantumTime() + 2);
                        CurrentProcess = readyqueu.peek();
                        readyqueu.remove(readyqueu.peek());
                        ApplyFcfs(CurrentProcess,processes);
                    }
                }

                System.out.println("process " + current.getName()+" has executed from second " + t +" to second " + totaltime);
                set_all_proccesses_waiting_time(processes,t);
                if(check_if_finished(current))AfterChecking(current,processes);

            /* if((qt + qt) == qt2) {
                totaltime += qt;
                current.setBurstTime(current.getBurstTime() - qt);
                current.setTqt(current.getQuantumTime()-qt);
            }
            else {*/
            }
            check_arrival_time1(processes,totaltime);
            if(current.getBurstTime() > 0)ApplyShortestJobFirst(current,readyqueu , processes);

        }
    }

    public void ApplyShortestJobFirst(prcoess current , Queue<prcoess>ReadyQueue, ArrayList<prcoess> processes)
    {
        if(CurrentProcess != null){
            int target = Integer.MAX_VALUE;
            if(!readyqueu.isEmpty())
                target = GetShortestJob(ReadyQueue);
            int qt;
            check_arrival_time2(processes,totaltime);
            if( current.getBurstTime() >= target ){

            /*qt = calculate_priority_time(current.getQuantumTime());
            current.setBurstTime(current.getBurstTime() - qt);
            totaltime += qt;
            current.setTqt(current.getQuantumTime() - qt);
            */
                System.out.println("the quantum time of process " + current.getName()+" was : " + current.getQuantumTime());
                current.setQuantumTime(current.getQuantumTime() + current.getTqt());
                System.out.println("the Quantum time of process " + current.getName() +" became : " + current.getQuantumTime());
                if(check_if_finished(current))AfterChecking(current,processes);
                else{
                    readyqueu.add(current);
                    temp++;
                    CurrentProcess = Search_shortest_job(target , ReadyQueue);
                    readyqueu.remove(Search_shortest_job(target , ReadyQueue));
                }
                active_current_process(CurrentProcess,processes);
            }
            else
            {

                int n = temp;
                int tracker = totaltime;
                int s = current.getTqt();
                int g = current.getBurstTime();
                boolean flag = true;
                while(s>0 && g > 0 && flag)
                {
                    s--;
                    g--;
                    totaltime++;
                    for(prcoess i : processes)
                    {
                        if(CurrentProcess != i && CurrentProcess != null && !readyqueu.contains(i) && totaltime == i.getArrivalTime() && !terminatedqueue.contains(i)){
                            if(current.getBurstTime() >=i.getBurstTime())
                            {
                                current.setBurstTime(current.getBurstTime() - (totaltime - tracker));
                                current.setTqt(current.getTqt() - ( totaltime - tracker));
                                current.setQuantumTime(current.getQuantumTime() + s);
                                System.out.println("process " + current.getName()+" has executed from second " + tracker +" to second " + totaltime);
                                flag = false;
                                readyqueu.add(current);
                                temp++;
                                CurrentProcess = Search_shortest_job(i.getBurstTime() , readyqueu);
                                readyqueu.remove(Search_shortest_job(i.getBurstTime() , readyqueu));
                                ApplyFcfs(i,processes);
                            }
                            else readyqueu.add(i);
                        }
                        if(!flag)break;
                    }

                }
                if(flag){
                    current.setBurstTime(g);
                    current.setTqt(s);
                    //set_all_proccesses_waiting_time(processes,tracker);
                    System.out.println("process " + current.getName()+" has executed from second " + tracker +" to second " + totaltime);
                    if(check_if_finished(current))AfterChecking(current,processes);
                    else if(current.getBurstTime() >0 && s <= 0 ) {
                        System.out.println("the quantum time of process " + current.getName()+" was : " + current.getQuantumTime());
                        current.setQuantumTime(current.getQuantumTime() + 2 );
                        System.out.println("the Quantum time of process " + current.getName() +" became : " + current.getQuantumTime());
                        readyqueu.add(current);
                        CurrentProcess = readyqueu.peek();
                        readyqueu.remove(readyqueu.peek());
                    }
                }
                //check_updates(current,n,processes , tracker);
            }

        }


    }

    private void check_updates(prcoess current,int n ,ArrayList<prcoess> processes , int tracker )
    {
        while(check_ready_queue(n) || (totaltime - tracker == CurrentProcess.getTqt()) )
        {
            current.setTqt(current.getTqt()-1);
            totaltime++;
            for(prcoess i : processes)
            {
                if(CurrentProcess != i && CurrentProcess != null && !readyqueu.contains(i) && totaltime == i.getArrivalTime() && !terminatedqueue.contains(i)){
                    readyqueu.add(i);
                    if(current.getBurstTime() >=i.getBurstTime())
                    {
                        current.setBurstTime(current.getBurstTime() - (totaltime - tracker));
                        current.setTqt(current.getTqt() - ( totaltime - tracker));
                        current.setQuantumTime(current.getQuantumTime() + current.getTqt());
                        System.out.println("process " + current.getName()+" has executed from second " + tracker +" to second " + totaltime);
                        set_all_proccesses_waiting_time(processes,tracker);
                        if(check_if_finished(current))AfterChecking(current,processes);
                        else{
                            if( current.getTqt() <=0){
                                current.setQuantumTime(current.getQuantumTime() + 2);
                                readyqueu.add(current);
                                //CurrentProcess = null;
                                CurrentProcess = readyqueu.peek();
                                readyqueu.remove(readyqueu.peek());
                            }
                            else
                            {
                                readyqueu.add(current);
                                temp++;
                                CurrentProcess = Search_shortest_job(i.getBurstTime() , readyqueu);
                                readyqueu.remove(Search_shortest_job(i.getBurstTime() , readyqueu));
                            }
                        }
                        active_current_process(CurrentProcess,processes);

                    }


                }
            }


        }
    }

    private boolean check_ready_queue(int n)
    {
        return n != readyqueu.size();
    }

    public prcoess Search_priority(int target , Queue<prcoess>readyQueue)
    {
        for( prcoess i : readyQueue)
        {
            if( i.getPriority() == target) return i;
        }
        return null;
    }

    public prcoess Search_shortest_job(int target , Queue<prcoess>readyQueue)
    {
        for( prcoess i : readyQueue)
        {
            if( i.getBurstTime() == target) return i;
        }
        return null;
    }
    public int GetMaxPriorityProcess( Queue<prcoess>readyQueue)
    {
        int min = Integer.MAX_VALUE;
        for( prcoess i : readyQueue)
        {
            if(min > i.getPriority()) min = i.getPriority();
        }
        return min;
    }

    public int GetShortestJob(Queue<prcoess>readyQueue)
    {
        int min = Integer.MAX_VALUE;
        for( prcoess i : readyQueue)
        {
            if(min > i.getBurstTime()) min = i.getBurstTime();
        }
        return min;
    }
    public int calculate_FCFC_time(int QuantumTime)
    {
        int result =0;
        result = (int) ceil(QuantumTime * 0.25);
        return result;
    }

    public int calculate_priority_time(int QuantumTime)
    {
        int result =0;
        result = (int) ceil(QuantumTime * 0.5);
        return result;
    }

    public boolean check_if_finished(prcoess current)
    {
        if(current.getBurstTime() <=0  && current.getName() == CurrentProcess.getName()) {
            return true;
        }
        return false;
    }

    public void AfterChecking(prcoess current , ArrayList<prcoess>processes){
        if(!readyqueu.isEmpty())
        {
            terminatedqueue.add(current);
            current.setCompletion(totaltime);
            CurrentProcess = readyqueu.peek();
            readyqueu.remove(readyqueu.peek());
            active_current_process(CurrentProcess,processes);
        }
        if((CurrentProcess == current) && (readyqueu.isEmpty()) && (terminatedqueue.size() == processes.size()-1)) {
            terminatedqueue.add(current);
            current.setCompletion(totaltime);
            CurrentProcess = null;
            return;
        }
        if(CurrentProcess == null && readyqueu.isEmpty())return;

    }

    public int getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(int totaltime) {
        this.totaltime = totaltime;
    }
    public void ShowFinalAllWaitingTime(ArrayList<prcoess>processes)
    {
        int sum=0;
        for(prcoess i : processes)
        {
            System.out.println("Waiting Time of "+i.getName()+" -> "+i.getWaitingTime());
            sum+=i.getWaitingTime();
        }
        System.out.println("Average of waiting time -> "+(sum/processes.size()));
    }
    public void ShowFinalAllTurnaroundTime(ArrayList<prcoess>processes)
    {
        double sum=0.0;
        for(prcoess i : processes)
        {
            int r=i.getCompletion()-i.getTurnAroundTime();
            System.out.println("Turnaround Time of "+i.getName()+" -> "+r);
            sum+=r;
        }
        System.out.println("Average of Turnaround Time -> "+(sum/processes.size()));
    }

}
