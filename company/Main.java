package com.company;
import java.util.*;

import com.company.Priority;
import point4.AG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {

        //   Shortest- Job First (SJF) Scheduling
        SJR s=new SJR();
        s.runSJR();

        //  Round Robin Scheduling
        RR r=new RR();
        r.runRR();

        //   priority scheduling
          Priority pr=new Priority();
          //to run and enter input
         // pr.runP();
          //to run testcase
          pr.runP_testcase();


       //  AG scheduling
        AG schdule = new AG();
        schdule.run();


    }
}



