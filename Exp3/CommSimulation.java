import java.util.concurrent.*;
import java.util.*;

public class CommSimulation {
    private static final Object lock = new Object();
    private static boolean dataReady = false;
    private static String receivedMessage = "";

    // Unicast
    public static void unicastReceiver() {
        synchronized (lock) {
            while (!dataReady) {
                try { lock.wait(); } catch (InterruptedException ignored) {}
            }
            System.out.println("Unicast Receiver received message: " + receivedMessage);
        }
    }

    public static void unicastSender() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        synchronized (lock) {
            receivedMessage = "Unicast message from sender!";
            dataReady = true;
            lock.notifyAll();
        }
    }

    // Broadcast
    public static void broadcastReceiver(int id) {
        synchronized (lock) {
            while (!dataReady) {
                try { lock.wait(); } catch (InterruptedException ignored) {}
            }
            System.out.println("Broadcast Receiver " + id + " received message: " + receivedMessage);
        }
    }

    public static void broadcastSender() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        synchronized (lock) {
            receivedMessage = "Broadcast message from sender!";
            dataReady = true;
            lock.notifyAll();
        }
    }

    // Multicast
    public static void multicastReceiver(int id, boolean isSubscriber) {
        synchronized (lock) {
            while (!dataReady) {
                try { lock.wait(); } catch (InterruptedException ignored) {}
            }
            if (isSubscriber) {
                System.out.println("Multicast Receiver " + id + " received message: " + receivedMessage);
            } else {
                System.out.println("Multicast Receiver " + id + " did not receive the message.");
            }
        }
    }

    public static void multicastSender() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        synchronized (lock) {
            receivedMessage = "Multicast message from sender!";
            dataReady = true;
            lock.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // --- Unicast ---
        System.out.println("\nUnicast Simulation:");
        dataReady = false;
        Thread unicastReceiver = new Thread(() -> unicastReceiver());
        Thread unicastSender = new Thread(() -> unicastSender());
        unicastReceiver.start();
        unicastSender.start();
        unicastReceiver.join();
        unicastSender.join();

        // --- Broadcast ---
        System.out.println("\nBroadcast Simulation:");
        dataReady = false;
        List<Thread> broadcastReceivers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            broadcastReceivers.add(new Thread(() -> broadcastReceiver(id)));
        }
        Thread broadcastSender = new Thread(() -> broadcastSender());
        broadcastReceivers.forEach(Thread::start);
        broadcastSender.start();
        broadcastSender.join();
        for (Thread t : broadcastReceivers) t.join();

        // --- Multicast ---
        System.out.println("\nMulticast Simulation:");
        dataReady = false;
        List<Thread> multicastReceivers = new ArrayList<>();
        boolean[] isSubscriber = {true, false, true, false, true};
        for (int i = 0; i < isSubscriber.length; i++) {
            final int id = i + 1;
            final boolean subscribed = isSubscriber[i];
            multicastReceivers.add(new Thread(() -> multicastReceiver(id, subscribed)));
        }
        Thread multicastSender = new Thread(() -> multicastSender());
        multicastReceivers.forEach(Thread::start);
        multicastSender.start();
        multicastSender.join();
        for (Thread t : multicastReceivers) t.join();

        System.out.println();
    }
}
