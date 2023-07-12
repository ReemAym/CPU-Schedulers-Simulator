package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Priority{

    int n=100;               //num of process
    int[] priority= new int[n+1];
    int[] arrival_time= new int[n];
    int[] burst_time= new int[n];
    int[] x= new int[n];            //take copy of burst time
    int[] y= new int[n];            //take copy of priority
    int[] waiting_time= new int[n];
    int[] turnaround_time= new int[n];
    int[] completion_time=new int[n];
    String[] name= new String[n];
    double avr_waiting=0;
    double avr_turnaround=0;
    int small_element;
    int end;            //end of each process
    int count;         //number of completion process

    void input(int num){
        this.n=num;
        for(int i = 0 ; i< n ; i++)
        {
            Scanner sc =new Scanner(System.in);
            System.out.println("this the data of the process number "+(i+1));

            System.out.println("please enter the arrival time ");
            arrival_time[i] = sc.nextInt();
            System.out.println("please enter the burst time of the process");
            burst_time[i] = sc.nextInt();
            System.out.println("please enter the name of the process");
            name[i] =sc.next();
            System.out.println("please enter the priority of the process");
            priority[i] = sc.nextInt();

        }
//        for (int i = 0; i < n; i++)
//            x[i] = burst_time[i];
//
//        for (int i = 0; i < n; i++)
//            y[i] = priority[i];
//        priority[n]=10000;
    }


    void solve(){

        for (int i = 0; i < n; i++)
            x[i] = burst_time[i];

        for (int i = 0; i < n; i++)
            y[i] = priority[i];
        priority[n]=10000;

        //without solving of starvation
        for (int time = 0; count != n; time++)
        {
            small_element = n;
            for (int i = 0; i < n; i++)
            {
                if (arrival_time[i] <= time && priority[i] < priority[small_element] && burst_time[i] > 0)
                    small_element = i;
            }
            burst_time[small_element]-=1;
            if (burst_time[small_element] == 0)
            {
                count+=1;
                end = time + 1;
                completion_time[small_element] = end;
                waiting_time[small_element] = end - arrival_time[small_element] - x[small_element];
                turnaround_time[small_element] = end - arrival_time[small_element];
            }
        }

        //with starvation

//        for (int time = 0; count != n; time++)
//        {
//            small_element = n;
//            for (int i = 0; i < n; i++)
//            {
//                if (arrival_time[i] <= time && priority[i] < priority[small_element] && burst_time[i] > 0) {
//                    small_element = i;
//                }
//                //decrease priority of process
//                else if(time-arrival_time[i]>3 &&burst_time[i]>0){
//                    priority[i]-=1;
//                }
//            }
//            burst_time[small_element]-=1;
//            if (burst_time[small_element] == 0)
//            {
//                count+=1;
//                end = time + 1;
//                completion_time[small_element] = end;
//                waiting_time[small_element] = end - arrival_time[small_element] - x[small_element];
//                turnaround_time[small_element] = end - arrival_time[small_element];
//            }
//        }


                //Sort process acording excution order
            for (int i = 0; i <n-1 ; i++) {
                for (int j = 0; j <n-1 ; j++) {

                    if(completion_time[j] > completion_time[j + 1])
                    {
                        int temp1=completion_time[j];
                        completion_time[j]=completion_time[j+1];
                        completion_time[j+1]=temp1;

                        int temp2=y[j];
                        y[j]=y[j+1];
                        y[j+1]=temp2;

                        int temp3=arrival_time[j];
                        arrival_time[j]=arrival_time[j+1];
                        arrival_time[j+1]=temp3;

                        int temp4=x[j];
                        x[j]=x[j+1];
                        x[j+1]=temp4;

                        String temp5=name[j];
                        name[j]=name[j+1];
                        name[j+1]=temp5;

                        int temp6=waiting_time[j];
                        waiting_time[j]=waiting_time[j+1];
                        waiting_time[j+1]=temp6;

                        int temp7=turnaround_time[j];
                        turnaround_time[j]=turnaround_time[j+1];
                        turnaround_time[j+1]=temp7;

                    }

                }
            }




    }

    void display(){
        System.out.println("Process"+"\t\t"+"Priority"+"\t"+"Arrival time"+"\t"+"burset time"+"\t\t"+"Waiting time"+"\t"+"Turnaround time"+"\t"+"Completion time");

        for(int i=0;i<n;i++){
            System.out.println("\t"+name[i]+"\t\t\t"+y[i]+"\t\t\t"+arrival_time[i]+"\t\t\t\t"+x[i]+"\t\t\t\t"+waiting_time[i]+"\t\t\t\t\t"+turnaround_time[i]+"\t\t\t\t"+completion_time[i]);

            avr_waiting = avr_waiting + waiting_time[i];
            avr_turnaround = avr_turnaround +turnaround_time[i];
        }

        System.out.println("Average waiting time = "+avr_waiting/n);
        System.out.println("Average Turnaround time = "+avr_turnaround/n);

    }

    void runP(){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter number of process");
        int n=sc.nextInt();

        input(n);
        solve();
        display();
    }

    void runP_testcase(){
         n=7;
         priority= new int[]{2,6,3,5,4,10,9,10000};
         arrival_time=new int[]{0,1,2,3,4,5,15};
         burst_time=new int[]{1,7,3,6,5,15,8};
         name=new String[]{"p1","p2","p3","p4","p5","p6","p7"};
         solve();
         display();
    }

}

