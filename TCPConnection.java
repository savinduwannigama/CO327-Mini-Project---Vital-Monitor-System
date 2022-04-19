/**
 * This class implements a TCP connection.
 * Will be called by the Gateway class to create a new TCP connection run on a separate thread
 */


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;


public class TCPConnection implements Runnable{
    private final InetAddress ip;
    private final String monitorID;
    private final int port;


    public TCPConnection (InetAddress ip, String monitorID, int port) {
        this.ip = ip;
        this.monitorID = monitorID;
        this.port = port;
    }

    public void createTCPConnection() {
        try {
            // save ip address, port number and id of the monitor
            // InetAddress ip = monitor.getIp();
            // int port = monitor.getPort();
            // String id = monitor.getMonitorID();

            // create a client socket to connect to the monitor
            Socket clientSocket = new Socket(this.ip, this.port);

            // decalre streams to send and receive data
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write("Requesting data from " + this.monitorID + "\n");
            osw.flush();    // request sent

            // declaring input stream
            InputStream is = clientSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            // buffer to store data received
            StringBuffer buffer = new StringBuffer();

            // keep reading data from the monitor
            while (true) {
                // read data from the monitor
                int data = isr.read();
                while (data != '\n') {
                    buffer.append((char) data);
                    data = isr.read();
                }

                // break if the monitor sends "end"
                if (buffer.toString().equals("end")) {
                    break;
                }

                // print data received and thread id
                System.out.println(buffer.toString() + " :: Thread ID: " + Thread.currentThread().getId());
                
                // clear buffer
                buffer.delete(0, buffer.length());
            }

            // close the socket
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // implementing the run() method inherited from the Runnable interface
    @Override
    public void run() {
        this.createTCPConnection();
    }
}
