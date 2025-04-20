import java.rmi.*;

public class RMI_Client {
    public static void main(String[] args) {
        try {
            // Lookup the remote object
            Hello h = (Hello) Naming.lookup("rmi://localhost/HelloService");
            String response = h.sayHello();
            System.out.println("Client: Received -> " + response);
        } catch (Exception e) {
            System.out.println("Client exception: " + e);
        }
    }
}
