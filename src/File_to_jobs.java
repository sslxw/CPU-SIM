
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class File_to_jobs extends Thread{
	static PCB[] ArrayOfProcesses = new PCB[30]; // 30 is the maximum input
	static Queue<PCB> job_queue = new LinkedList<>();
    static int counter = 0; // Actual Size
    public void run() {
        try {
			File file = new File("C:\\Users\\saleh\\Desktop\\job.txt");
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) { 
				String line = scan.nextLine();
				if(line.contains("Job")){
	                continue;

	           }
				int burstTime = Integer.parseInt(line.split(", ")[1]); 
				int memorySize = Integer.parseInt(line.split(", ")[2]);
				PCB p = new PCB(line.split(", ")[0], burstTime, memorySize);
				ArrayOfProcesses[counter++] = p;

			}
			for (int i = 0; i < counter; i++) {
				job_queue.add(ArrayOfProcesses[i]);
			}

		} catch (FileNotFoundException e) {
			System.out.println("invalid file path");
		} catch (NumberFormatException e) {
			System.out.println("invalid file format");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("invalid process must be less than or equal 30");
		}
	}
}