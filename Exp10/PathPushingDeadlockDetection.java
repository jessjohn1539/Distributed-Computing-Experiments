import java.util.*;

public class PathPushingDeadlockDetection {
    public static boolean detectDeadlock(int[][] allocation, int[][] request, int[] available) {
        int n = allocation.length;  // Number of processes
        int m = allocation[0].length; // Number of resource types

        boolean[] finish = new boolean[n];
        int[] work = Arrays.copyOf(available, m);

        while (true) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (!finish[i] && canAllocate(request[i], work)) {
                    for (int j = 0; j < m; j++) {
                        work[j] += allocation[i][j];
                    }
                    finish[i] = true;
                    found = true;
                }
            }
            if (!found) break;
        }

        for (boolean f : finish) {
            if (!f) return true; // Deadlock if any process is unfinished
        }
        return false;
    }

    private static boolean canAllocate(int[] req, int[] work) {
        for (int i = 0; i < req.length; i++) {
            if (req[i] > work[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] allocation = {
            {0, 1, 0},
            {2, 0, 0},
            {3, 0, 2},
            {2, 1, 1},
            {0, 0, 2},
        };

        int[][] request = {
            {0, 2, 0},   
            {1, 0, 2},
            {1, 0, 2},
            {0, 0, 0},
            {0, 0, 2},
        };

        int[] available = {0, 0, 0}; // Change to {3,2,2} to test safe sequence

        boolean isDeadlocked = detectDeadlock(allocation, request, available);
        if (isDeadlocked) {
            System.out.println("Deadlock Detected!");
        } else {
            System.out.println("No Deadlock. Safe state.");
        }
    }
}
