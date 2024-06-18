
enum STATE { New, Ready, Running, Waiting, Terminated }

public class PCB {
	public String pid;
	public STATE state = STATE.New;
	public int burstTime;
	public int remainingBurstTime;
	public int memory;
	public int arrivalTime;
	public int waitingTime;
	public int turnAroundTime;
	public int completionTime;
	
	PCB(String id, int burstTime, int memory){
		this.pid = id;
		this.burstTime = burstTime;
		this.memory = memory;
		this.remainingBurstTime = burstTime;
	}
	
	
}
