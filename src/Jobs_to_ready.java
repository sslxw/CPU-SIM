

import java.util.*;

public class Jobs_to_ready extends Thread{
	public static Queue<PCB> ready_queue = new LinkedList<>();
	
	public void run() {
		try {
		while(!File_to_jobs.job_queue.isEmpty()) {
			int jobmemory = File_to_jobs.job_queue.peek().memory;	
			if(Main.memory - jobmemory >= 0) { // If there is space for the process, add it to ready_queue
				Main.memory -= jobmemory;
				File_to_jobs.job_queue.peek().state = STATE.Ready;
				ready_queue.add(File_to_jobs.job_queue.remove());
			}
			if(jobmemory > 8192) { // we dismiss the jobs that are above 8192MB memory.
				File_to_jobs.job_queue.remove();
			}
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
