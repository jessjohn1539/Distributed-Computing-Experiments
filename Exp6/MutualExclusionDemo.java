import java.util.*;
import java.util.concurrent.*;

public class MutualExclusionDemo {

    static class MaekawaProcess implements Runnable {
        private int id;
        private Set<Integer> votingSet;
        private static final Map<Integer, Boolean> votes = new ConcurrentHashMap<>();
        private static final Object lock = new Object();

        MaekawaProcess(int id, Set<Integer> votingSet) {
            this.id = id;
            this.votingSet = votingSet;
        }

        public void run() {
            try {
                System.out.println("[Maekawa] Process " + id + " requesting critical section...");
                synchronized (lock) {
                    for (int pid : votingSet) {
                        if (!votes.getOrDefault(pid, true)) {
                            System.out.println("[Maekawa] Process " + id + " denied by " + pid);
                            return;
                        }
                    }
                    for (int pid : votingSet) {
                        votes.put(pid, false); // mark vote taken
                    }
                    System.out.println("[Maekawa] Process " + id + " entering critical section.");
                }

                Thread.sleep(1000); // simulate CS

                synchronized (lock) {
                    for (int pid : votingSet) {
                        votes.put(pid, true); // release vote
                    }
                    System.out.println("[Maekawa] Process " + id + " exiting critical section.");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class LamportProcess implements Runnable {
        private int id;
        private int timestamp = 0;
        private static final int TOTAL_PROCESSES = 3;

        public void run() {
            try {
                timestamp++;
                System.out.println("[Lamport] Process " + id + " sends request at timestamp: " + timestamp);

                Thread.sleep(1000); // simulate wait

                System.out.println("[Lamport] Process " + id + " enters critical section at timestamp: " + timestamp);
                Thread.sleep(1000); // simulate CS

                System.out.println("[Lamport] Process " + id + " exits critical section and sends release.");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LamportProcess(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Maekawa Algorithm Simulation
        System.out.println("\n==== Maekawa's Algorithm ====");
        Set<Integer> vs1 = Set.of(1, 2);
        Set<Integer> vs2 = Set.of(0, 2);
        Set<Integer> vs3 = Set.of(0, 1);
        Thread m1 = new Thread(new MaekawaProcess(0, vs1));
        Thread m2 = new Thread(new MaekawaProcess(1, vs2));
        Thread m3 = new Thread(new MaekawaProcess(2, vs3));
        m1.start(); m2.start(); m3.start();
        m1.join(); m2.join(); m3.join();

        // Lamport Algorithm Simulation
        System.out.println("\n==== Lamport's Algorithm ====");
        Thread l1 = new Thread(new LamportProcess(0));
        Thread l2 = new Thread(new LamportProcess(1));
        Thread l3 = new Thread(new LamportProcess(2));
        l1.start(); l2.start(); l3.start();
        l1.join(); l2.join(); l3.join();
    }
}
