import java.util.*;

class DeadlockDetection {
private final int numProcesses;
private final List<List<Integer>> waitForGraph;

public DeadlockDetection(int numProcesses) {
this.numProcesses = numProcesses;
this.waitForGraph = new ArrayList<>(numProcesses);
for (int i = 0; i < numProcesses; i++) {
waitForGraph.add(new ArrayList<>());
}
}

// Add an edge to represent a process waiting for another process
public void addDependency(int from, int to) {
waitForGraph.get(from).add(to);
}

// Detect cycles in the wait-for graph using DFS
public boolean detectDeadlock() {
boolean[] visited = new boolean[numProcesses];
boolean[] recursionStack = new boolean[numProcesses];

for (int i = 0; i < numProcesses; i++) {
if (!visited[i]) {
if (dfs(i, visited, recursionStack)) {
return true;
}
}
}
return false;
}


// Depth-First Search (DFS) to detect cycles in the wait-for graph
private boolean dfs(int process, boolean[] visited, boolean[] recursionStack) {
visited[process] = true;
recursionStack[process] = true;

for (int neighbor : waitForGraph.get(process)) {
if (!visited[neighbor] && dfs(neighbor, visited, recursionStack)) {
return true;
} else if (recursionStack[neighbor]) {
return true; // Cycle detected
}
}

recursionStack[process] = false;
return false;
}

public static void main(String[] args) {
Scanner sc = new Scanner(System.in);

System.out.println("Enter number of processes:");
int numProcesses = sc.nextInt();
DeadlockDetection system = new DeadlockDetection(numProcesses);

System.out.println("Enter number of dependencies (edges in wait-for graph):");
int numEdges = sc.nextInt();

System.out.println("Enter dependencies (process waiting for another process):");
for (int i = 0; i < numEdges; i++) {
int from = sc.nextInt();
int to = sc.nextInt();
system.addDependency(from, to);
}

if (system.detectDeadlock()) {
System.out.println("Deadlock Detected! Resolving by aborting a process.");
} else {
System.out.println("No Deadlock Detected.");
}

sc.close();
}
}
