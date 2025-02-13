import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int priority; // not used in sjf
    int completionTime = 0;
    int turnaroundTime = 0;
    int waitingTime = 0;
    int remainingTime;

    Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
}

public class SJFPreemptive {
    public static List<Process> sjfPreemptive(List<Process> processes) {
        int time = 0;
        int completed = 0;
        int n = processes.size();

        while (completed != n) {
            List<Process> availableProcesses = new ArrayList<>();
            for (Process p : processes) {
                if (p.arrivalTime <= time && p.remainingTime > 0) {
                    availableProcesses.add(p);
                }
            }

            if (!availableProcesses.isEmpty()) {
                Process shortest = availableProcesses.stream()
                        .min(Comparator.comparingInt(p -> p.remainingTime))
                        .orElseThrow();
                shortest.remainingTime -= 1;
                if (shortest.remainingTime == 0) {
                    completed++;
                    shortest.completionTime = time + 1;
                    shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
                    shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
                }
            }
            time++;
        }
        return processes;
    }

    public static double[] calculateAverages(List<Process> processes) {
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;

        for (Process p : processes) {
            totalTurnaroundTime += p.turnaroundTime;
            totalWaitingTime += p.waitingTime;
        }

        double avgTurnaroundTime = totalTurnaroundTime / processes.size();
        double avgWaitingTime = totalWaitingTime / processes.size();

        return new double[]{avgTurnaroundTime, avgWaitingTime};
    }

    public static void displayResults(List<Process> processes, String algorithmName) {
        System.out.printf("\n%s:\n", algorithmName);
        System.out.printf("%-10s%-10s%-10s%-10s%-15s%-15s%-10s\n", "PID", "Arrival", "Burst", "Priority", "Completion", "Turnaround", "Waiting");

        for (Process p : processes) {
            System.out.printf("%-10d%-10d%-10d%-10d%-15d%-15d%-10d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.priority, p.completionTime, p.turnaroundTime, p.waitingTime);
        }

        double[] averages = calculateAverages(processes);
        System.out.printf("\nAverage Turnaround Time: %.2f\n", averages[0]);
        System.out.printf("Average Waiting Time: %.2f\n", averages[1]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter priority for process " + (i + 1) + " (optional, enter 0 if not applicable): ");
            int priority = scanner.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime, priority));
        }

        // SJF Preemptive Scheduling
        List<Process> sjfResult = sjfPreemptive(new ArrayList<>(processes));
        displayResults(sjfResult, "Shortest Job First (SJF Preemptive)");
        scanner.close();
    }
}