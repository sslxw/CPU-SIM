
import java.util.*;

public class Scheduler {
	
	public static LinkedList<PCB> terminatedList= new LinkedList<>();
	
	public static void Ganttchart(PCB p, int st, int ft) {
		System.out.println("|_________________________|");
		System.out.println("|       ( p" + p.pid + " )            |");
		System.out.println("      "+st + " ---> " + ft);
	}
	
	public static void Table(Queue<PCB> Q) {
		System.out.println("_________________________________________________________________________________________________________________________________________________");
		System.out.println("| PID  | Burst Time | Turnaround-Time (TT) | Waiting-Time (WT) | Completion-Time (CT) | Memory |    Status    |");
		int totalWaitTime = 0;
		int totalTurnaroundTime = 0;
		int totalCompletionTime = 0;
		int numOfProcceses = 0;
		int size = terminatedList.size();
		for(int i=0; i<size; i++) {
			numOfProcceses +=1;
			totalWaitTime += terminatedList.peek().waitingTime;
			totalTurnaroundTime += terminatedList.peek().turnAroundTime;
			totalCompletionTime += terminatedList.peek().completionTime;
			System.out.println("   " + terminatedList.peek().pid + "\t     " + terminatedList.peek().burstTime + "\t\t\t" + terminatedList.peek().turnAroundTime + "\t \t  " + terminatedList.peek().waitingTime + "\t\t\t" + terminatedList.peek().completionTime + "\t \t   " + terminatedList.peek().memory + "\t   " + terminatedList.peek().state.toString());
			terminatedList.remove();
		}

		System.out.println("_________________________________________________________________________________________________________________________________________________\n");
		System.out.println("Total Waiting Time: " + (totalWaitTime));
		System.out.println("Total Turnaround Time: " + (totalTurnaroundTime));
		System.out.println("Total Completion Time: " + (totalCompletionTime));
		System.out.println("_________________________________________________________________________________________________________________________________________________");
		System.out.println("Average Waiting Time: " + ((double)totalWaitTime/numOfProcceses));
		System.out.println("Average Turnaround Time: "+ ((double)totalTurnaroundTime/numOfProcceses));
		System.out.println("Average Completion Time: "+ ((double)totalCompletionTime/numOfProcceses));

	}
	
	public static void FCFS(Queue<PCB> Q) {
		int start = 0; // starting burst time
		
		while(!Q.isEmpty()) {
			Ganttchart(Q.peek(), start, start+Q.peek().remainingBurstTime);
			Q.peek().state = STATE.Running;
			
			Q.peek().completionTime = start + Q.peek().remainingBurstTime;

			Q.peek().turnAroundTime = Q.peek().completionTime - Q.peek().arrivalTime;

			Q.peek().waitingTime = Q.peek().turnAroundTime - Q.peek().burstTime;
			
			start += Q.peek().burstTime;
			Q.peek().remainingBurstTime = 0;
			System.out.println("p"+Q.peek().pid +" Memory usage: "+ Q.peek().memory);

			Q.peek().state = STATE.Terminated;
			Main.memory += Q.peek().memory;
			terminatedList.add(Q.remove());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Table(terminatedList);
		
	}
	
	
	public static void SJF(Queue<PCB> Q) {
		int size = Q.size();
		for(int i=0; i<size; i++) {
			Q.remove();
		}
		
		int counter = File_to_jobs.counter;
		
		PCB temp;
		
		// sorting to get the shortest job (BubbleSort)
		for (int i=0; i < counter; i++) {
			
			for(int j=i+1; j < counter; j++) {
				
				if(File_to_jobs.ArrayOfProcesses[i].burstTime > File_to_jobs.ArrayOfProcesses[j].burstTime) {
					temp = File_to_jobs.ArrayOfProcesses[i];
					File_to_jobs.ArrayOfProcesses[i] = File_to_jobs.ArrayOfProcesses[j];
					File_to_jobs.ArrayOfProcesses[j] = temp;
				}					
			}
		}
		int start = 0; // burst time start
		
		for(int i=0; i<counter; i++) {
			if (File_to_jobs.ArrayOfProcesses[i].memory < 8192)
			Q.add(File_to_jobs.ArrayOfProcesses[i]);
		}
		while(!Q.isEmpty()) {
			
			Q.peek().state = STATE.Ready;
			Ganttchart(Q.peek(), start, start+Q.peek().remainingBurstTime);
			Q.peek().state = STATE.Running;
			
			Q.peek().completionTime = start + Q.peek().remainingBurstTime;

			Q.peek().turnAroundTime = Q.peek().completionTime - Q.peek().arrivalTime;

			Q.peek().waitingTime = Q.peek().turnAroundTime - Q.peek().burstTime;
			
			start += Q.peek().burstTime;
			Q.peek().remainingBurstTime = 0;
			System.out.println("p"+Q.peek().pid +" Memory usage: "+ Q.peek().memory);

			Q.peek().state = STATE.Terminated;
			Main.memory += Q.peek().memory;
			terminatedList.add(Q.remove());
		}
		
		Table(terminatedList);
	}
	
	
 
	public static void RR(Queue<PCB> Q, int quantum) {
		int start = 0; // starting burst time
		while(!Q.isEmpty()) {

			PCB job = Q.remove();
			job.state = STATE.Running;
			

			if(job.remainingBurstTime > quantum) {  //if remaining burst time is bigger than quantum then finish it and put it pack in the Queue 
				job.remainingBurstTime -= quantum;
				Ganttchart(job, start, start+quantum);
				System.out.println("p"+job.pid +" Memory usage: "+ job.memory);
				start += quantum;
				job.state = STATE.Ready;
				Q.add(job);
			
			}
			else { //if remaining burst time is smaller than quantum then finish it and terminate it 
				Ganttchart(job, start, start+job.remainingBurstTime);
				System.out.println("p"+job.pid +" Memory usage: "+ job.memory);
				job.completionTime = start + job.remainingBurstTime;
				start += job.remainingBurstTime;
				
				job.turnAroundTime = job.completionTime - job.arrivalTime;
				
				job.waitingTime = job.turnAroundTime - job.burstTime;
				
				job.remainingBurstTime -= job.remainingBurstTime;
				
				job.state = STATE.Terminated;
				
				Main.memory += job.memory;
				terminatedList.add(job);
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
		Table(terminatedList);
	
}
}