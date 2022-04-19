import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Gateway {
    // ArrayList to store the list of monitors with which TCP connections were already made
    static private List<String> rcvdMonitors = new ArrayList<String>();

    public static void main(String[] args) {
        // UDP packet receiving port
        int UDP_RCV_PORT = 6000;

        // create a datagram socket to receive broadcast messages
        DatagramSocket recvSocket = createRecvSocket(UDP_RCV_PORT);

        // keep receiving broadcast messages and create TCP connections with monitors
        while (true) {
            // receive broadcast messages
            DatagramPacket recvPacket = recvPacket(recvSocket);

            // get the monitor object from the received packet
            Monitor monitor = getMonitor(recvPacket);

            // get the ip address and port of the monitor
            InetAddress ipAddress = monitor.getIp();
            int port = monitor.getPort();


            // check if the monitor is already in the list
            if (!monitors.contains(ipAddress + ":" + port)) {
                // add the monitor to the list
                monitors.add(ipAddress + ":" + port);

                // print the monitor information
                System.out.println("Establishing connection to monitor at: " + ipAddress + ":" + port);

                // create tcp thread to connect to the monitor
                Thread tcpConnection = new Thread(new TCPConnection(ipAddress, monitor.getMonitorID(), port));

                // start the tcp connection thread
                tcpConnection.start();
            }
        }

    }

    private static Monitor getMonitor(DatagramPacket recvPacket) {
        return null;
    }

    private static DatagramPacket recvPacket(DatagramSocket recvSocket) {
        return null;
    }

    private static DatagramSocket createRecvSocket(int uDP_RCV_PORT) {
        return null;
    }
    
}
