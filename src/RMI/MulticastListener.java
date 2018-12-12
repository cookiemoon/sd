package RMI;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.thavam.util.concurrent.BlockingHashMap;

import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MulticastListener extends Thread {
    /**
     * Here we got the configuration for the Multicast network
     * The address is specified by MULT_ADDR and its RESPONSE_PORT
     * The UUID is used to identify this process. It is statistically improbable to have a UUID collision, thus this should be unique by all pratical purposes.
     */
    private String THREAD_START_MSG = "RMI Multicast Listener Thread ready";
    private MulticastSocket socket = null;
    private String MULT_ADDR = "224.3.2.1";
    private int RESPONSE_PORT = 5432; // RESPONSE RESPONSE_PORT
    private int BUF_SIZE = 4096; // 4KB
    BlockingHashMap<Integer, String> queue;

    public MulticastListener(BlockingHashMap<Integer, String> queue) {
        this.queue = queue;
        this.start();
    }

    private Integer getMessageID(String json) {
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        return Integer.parseInt(obj.get("msgid").getAsString());
    }

    public DatagramPacket receivePacket() {
        byte[] buffer = new byte[BUF_SIZE];

        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet); // Blocks until a datagram is received
        } catch (IOException e) {
            e.getStackTrace();
        }

        return packet;
    }

    public void pPacket(DatagramPacket packet) {
        String msg = new String(packet.getData(), 0, packet.getLength());
        System.out.print("[" + packet.getAddress().getHostAddress() + " | "+ packet.getPort() + " ]:");
        System.out.println(msg);
    }

    public void run () {
        System.out.println(THREAD_START_MSG);

        try {
            // Bind a multicast socket to a port
            socket = new MulticastSocket(RESPONSE_PORT);
            InetAddress mult_net_group = InetAddress.getByName(MULT_ADDR);
            socket.joinGroup(mult_net_group);

            while(true) {
                DatagramPacket packet = receivePacket();
                String json = new String(packet.getData(), 0, packet.getLength());
                pPacket(packet);
                Integer msgid = getMessageID(json);

                // FIXME: change listening responses to other port
                if (!json.contains("request"))
                    queue.put(msgid, json);
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            socket.close();
        }
    }
}