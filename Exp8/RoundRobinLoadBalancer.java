import java.util.*;

public class RoundRobinLoadBalancer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input servers
        System.out.println("Enter number of servers:");
        int numServers = sc.nextInt();
        sc.nextLine(); // consume newline
        List<String> servers = new ArrayList<>();
        System.out.println("Enter server names:");
        for (int i = 0; i < numServers; i++) {
            servers.add(sc.nextLine());
        }

        // Input requests
        System.out.println("Enter number of requests:");
        int numRequests = sc.nextInt();
        sc.nextLine(); // consume newline
        List<String> requests = new ArrayList<>();
        System.out.println("Enter request names:");
        for (int i = 0; i < numRequests; i++) {
            requests.add(sc.nextLine());
        }

        // Round-robin assignment
        Map<String, String> assignments = new LinkedHashMap<>();
        int serverIndex = 0;
        for (String request : requests) {
            String assignedServer = servers.get(serverIndex);
            assignments.put(request, assignedServer);
            serverIndex = (serverIndex + 1) % servers.size();
        }

        // Output assignments
        System.out.println("\nRequest Assignments:");
        for (Map.Entry<String, String> entry : assignments.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        sc.close();
    }
}
