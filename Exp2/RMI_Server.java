import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class RMI_Server {
    public static void main(String[] args) {
        try {
            // Create and export a remote object
            HelloImpl obj = new HelloImpl();
            Naming.rebind("HelloService", obj);
            System.out.println("Server: HelloService bound in registry");
        } catch (Exception e) {
            System.out.println("Server exception: " + e);
        }
    }
}

// Remote interface
interface Hello extends Remote {
    String sayHello() throws RemoteException;
}

// Implementation of remote interface
class HelloImpl extends UnicastRemoteObject implements Hello {
    HelloImpl() throws RemoteException {}
    public String sayHello() {
        return "Hello from the Server!";
    }
}
