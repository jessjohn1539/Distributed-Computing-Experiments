import java.util.*;

public class BullyElection {
    static class Process {
        int id;
        boolean isActive;

        Process(int id) {
            this.id = id;
            this.isActive = true;
        }
    }

    public static void main(String[] args) {
        int totalProcesses = 5;
        Process[] processes = new Process[totalProcesses];

        for (int i = 0; i < totalProcesses; i++) {
            processes[i] = new Process(i);
        }

        // Randomly fail a process
        Random random = new Random();
        int failed = random.nextInt(totalProcesses);
        processes[failed].isActive = false;
        System.out.println("Process " + failed + " has failed.");

        // Process 2 initiates the election
        int initiator = 2;
        System.out.println("\nProcess " + initiator + " initiates election...");

        int coordinator = -1;
        for (int i = initiator + 1; i < totalProcesses; i++) {
            if (processes[i].isActive) {
                System.out.println("Process " + i + " responds to election message.");
                coordinator = i;
            }
        }

        // If no higher process is active, the initiator becomes coordinator
        if (coordinator == -1 && processes[initiator].isActive) {
            coordinator = initiator;
        }

        // Inform all active processes
        System.out.println("\nProcess " + coordinator + " is elected as Coordinator.");
        for (int i = 0; i < totalProcesses; i++) {
            if (i != coordinator && processes[i].isActive) {
                System.out.println("Process " + i + " acknowledges coordinator " + coordinator);
            }
        }
    }
}
