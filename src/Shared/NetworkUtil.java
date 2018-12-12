package Shared;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NetworkUtil implements Serializable {
    /**
     * Sends a JSON message through the specified PORT in addr 224.3.2.1
     */
    public static void multicastSend (int port, String json) {
        String MULTICAST_ADDRESS = "224.3.2.1";
        MulticastSocket socket = null;
        byte[] buffer = json.getBytes();

        try {
            socket = new MulticastSocket();  // create socket without binding it (only for sending)
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Couldn't send packet to multicast server");
            e.getStackTrace();
        } finally {
            socket.close();
        }
    }
}
