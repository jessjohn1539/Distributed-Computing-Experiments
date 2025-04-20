import java.io.*;
import java.net.*;

public class Producer {
    public static void main(String[] args) {
        try {
            // Step 1: Connect to the consumer (localhost, port 5000)
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Producer: Connected to Consumer");

            // Step 2: Send message
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String data = "Hello from Producer!";
            out.println(data);
            System.out.println("Producer: Sent - " + data);

            // Step 3: Optional Acknowledgement
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String ack = in.readLine();
            System.out.println("Producer: Received Acknowledgement - " + ack);

            // Step 4: Cleanup
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
