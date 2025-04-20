import java.util.*;

public class ClockSync {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Step 1: Initialize master and slave clocks
        System.out.print("Enter number of slave nodes: ");
        int n = scanner.nextInt();
        int[] slaveTimes = new int[n];

        System.out.print("Enter master time: ");
        int masterTime = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter time for Slave " + (i + 1) + ": ");
            slaveTimes[i] = scanner.nextInt();
        }

        // Step 4: Compute average time
        int total = masterTime;
        for (int time : slaveTimes) {
            total += time;
        }
        int avgTime = total / (n + 1);

        System.out.println("\nAverage Time: " + avgTime);

        // Step 5 & 6: Calculate adjustments and adjust slave clocks
        for (int i = 0; i < n; i++) {
            int adjustment = avgTime - slaveTimes[i];
            System.out.println("Adjustment for Slave " + (i + 1) + ": " + adjustment);
            slaveTimes[i] += adjustment;
        }

        // Step 7: Show final times
        System.out.println("\nSynchronized Times:");
        System.out.println("Master: " + avgTime);
        for (int i = 0; i < n; i++) {
            System.out.println("Slave " + (i + 1) + ": " + slaveTimes[i]);
        }
    }
}
