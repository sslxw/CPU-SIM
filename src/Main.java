

import java.util.Scanner;

public class Main {
	public static int memory = 8192;
	public static void main(String args[]) throws InterruptedException {
		File_to_jobs thread = new File_to_jobs();
		Jobs_to_ready thread2 = new Jobs_to_ready();
		thread.start();
		thread.join(); //wait for reading to complete..
		thread2.start();

	      Scanner s = new Scanner(System.in);
	      System.out.println("Please choose the desired scheduling algorithm: \n1- First-Come-First-Serve (FCFS) \n2- Shortest-Job-First (SJF) \n3- Round-Robin (RR) – quantum = 3ms \n4- Round-Robin (RR) - quantum = 5ms \n5- Exit");
	      String input = s.next();
	      boolean flag = true;
	      do {
	        switch (input) {
	          case "1":
	            System.out.println("FCFS");
	            Scheduler.FCFS(Jobs_to_ready.ready_queue);
	            flag = false;
	            break;
	          case "2":
	            System.out.println("SJF");
	            Scheduler.SJF(Jobs_to_ready.ready_queue);
	            flag = false;
	            break;
	          case "3":
	            System.out.println("RR Quantum = 3");
	            Scheduler.RR(Jobs_to_ready.ready_queue, 3);
	            flag = false;
	            break;
	          case "4":
	        	System.out.println("RR Quantum = 5");
		        Scheduler.RR(Jobs_to_ready.ready_queue, 5);
		        flag = false;
	          case "5":
		            flag = false;
		            System.out.println("Have a nice day!");
		            System.exit(0);
	          default:
	            System.out.println("Please enter an existing number");
	            input = s.next();
	        }
	      } while (flag);
	      s.close();
	    } 

	  

	}

