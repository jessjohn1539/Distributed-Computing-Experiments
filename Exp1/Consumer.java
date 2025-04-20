import java.io.*;
import java.net.*;

public class Consumer {
    public static void main(String[] args) {
        try {
            // Step 1: Create a server socket on port 5000
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Consumer: Waiting for Producer...");

            // Step 2: Accept connection from producer
            Socket socket = serverSocket.accept();
            System.out.println("Consumer: Connected to Producer");

            // Step 3: Read message from producer
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = in.readLine();
            System.out.println("Consumer: Received - " + message);

            // Step 4: Optional Acknowledgement
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("ACK: Message Received");

            // Step 5: Cleanup
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
