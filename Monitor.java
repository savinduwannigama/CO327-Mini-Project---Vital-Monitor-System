/**
 * This class implements Runnable, and allows to wait in a different thread for a TCP connection
 * Will be called from the VitalMonitor class 
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class Monitor implements Serializable, Runnable {
    private final InetAddress ip;
    private final String monitorID;
    private final int port;

    public InetAddress getIp() {
        return ip;
    }

    public String getMonitorID() {
        return monitorID;
    }

    public int getPort() {
        return port;
    }

    public String monitor_str() {
        return "Monitor ID: " + ip + " IP: " + monitorID + " PORT:" + port;
    }

    public Monitor(InetAddress ip, String monitorID, int port) {
        this.ip = ip;
        this.monitorID = monitorID;
        this.port = port;
    }

    public void waitForGatewayConnection() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        PrintWriter out = null;
        int maxIncomingConnections = 10;

        try {
            serverSocket = new ServerSocket(this.port, maxIncomingConnections, this.ip);
            System.out.printf("starting up on %s port %s\n", this.ip, this.port);

            //Wait for a connection
            System.out.println("waiting for a connection");
            clientSocket = serverSocket.accept();
            System.out.println("connection from " + clientSocket);

            while (true) {
                //Receive the data in small chunks and retransmit it
                System.out.println("sending data  to the gateway");
                String message = "Hello from Vital Monitor: " + this.monitorID;
                //Send data
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(message);
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.waitForGatewayConnection();
    }
}
