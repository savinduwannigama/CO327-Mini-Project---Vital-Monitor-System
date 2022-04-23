import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.SocketException;


public class Gateway {
    // ArrayList to store the list of monitors with which TCP connections were already made
    static private List<String> rcvdMonitors = new ArrayList<String>();

    public static void main(String[] args) {
        // UDP packet receiving port
        int UDP_RCV_PORT = 6000;

        // create a datagram socket to receive broadcast messages
        DatagramSocket recvSocket = createRecvSocket(UDP_RCV_PORT);
        System.out.println("Gateway Running listening to broadcast port 6000 ...");

        // keep receiving broadcast messages and create TCP connections with monitors
        // The gateway will run until it is manually terminated
        while (true) {
            // Following methods waits for a new UDP packet
            // System.out.println('1');
            DatagramPacket recvPacket = recvPacket(recvSocket);  // waits inside the recvPacket method until a UDP packet is received
            // System.out.println('2');

            // reading the monitor onject from the received byte stream
            Monitor monitor = getMonitor(recvPacket);

            // get the ip address and port of the monitor
            InetAddress ipAddress = monitor.getIp();
            int port = monitor.getPort();


            // check if the monitor is already in the list
            if (!rcvdMonitors.contains(ipAddress + ":" + port)) {
                // add the monitor to the list
                rcvdMonitors.add(ipAddress + ":" + port);

                // print the monitor information
                System.out.println("Establishing connection to monitor at: " + ipAddress + ":" + port);

                // create tcp thread to connect to the monitor
                Thread tcpConnection = new Thread(new TCPConnection(ipAddress, monitor.getMonitorID(), port));

                // start the tcp connection thread
                tcpConnection.start();
            }
        }

    }

    // this methods gets the monitor object from the data stream received
    private static Monitor getMonitor(DatagramPacket recvPacket) {
        Monitor monitor = null;
        try {
            // get the monitor object from the received packet
            InputStream in = new ByteArrayInputStream(recvPacket.getData());
            ObjectInputStream ois = new ObjectInputStream(in);
            monitor = (Monitor) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monitor;
    }


    // this methods receives a single datagram packet on the socket given as the argument
    private static DatagramPacket recvPacket(DatagramSocket recvSocket) {
        DatagramPacket recvPacket = null;
        try {
            // creating a byte array as a receiving buffer
            byte[] recvBuf = new byte[1024];

            // creates a new datagram packet to receive data from the UDP socket
            recvPacket = new DatagramPacket(recvBuf, recvBuf.length);

            // process will wait here until it gets a new UDP packet
            recvSocket.receive(recvPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // returns the received UDP packet
        return recvPacket;
    }

    // this method creates the Datagram socket to receive all the broadcast messages
    private static DatagramSocket createRecvSocket(int UDP_RCV_PORT) {
        DatagramSocket recvSocket = null;
        try {
            recvSocket = new DatagramSocket(UDP_RCV_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return recvSocket;
    }
}
