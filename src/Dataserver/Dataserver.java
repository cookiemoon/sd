package Dataserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Dataserver {
    static final int CAPACITY = 1000;
    /**
     * Here we got the configuration for the Multicast network
     * The address is specified by MULT_ADDR and its PORT
     * The UUID is used to identify this process. It is statistically improbable to have a UUID collision, thus this should be unique by all pratical purposes.
     */
    private String id = UUID.randomUUID().toString();
    private MulticastSocket socket = null;
    private String MULTICAST_ADDRESS = "224.3.2.1";
    private int REQUEST_PORT = 4321;
    private int BUF_SIZE = 4096; // 4KB
    private int WORKERS = 10;
    ArrayBlockingQueue<String> workQueue;

    public Dataserver(ArrayBlockingQueue<String> q) {
        workQueue = q;
    }

    public int getWorkers() { return WORKERS; }

    public String getID() {
        return id;
    }

    public static void main (String[] args) {
        ConcurrentHashMap<String, String> sqlCommands = new ConcurrentHashMap<>();
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(CAPACITY);

        new SQLWatcher(sqlCommands);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Dataserver server = new Dataserver(q);

//        Runtime.getRuntime().addShutdownHook(new DeadHook(server.getId()).hook());

        Db database = new Db();
        Worker[] w = new Worker[server.getWorkers()];

        for (int i = 0; i < server.getWorkers(); i++) {
            w[i] = new Worker(q, sqlCommands, server.getID(), database);
        }
        server.listen();
    }


    public void listen () {
        System.out.println(id + " Dataserver listening on " + REQUEST_PORT);
        try {
            socket = new MulticastSocket(REQUEST_PORT);
            InetAddress mult_net_group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(mult_net_group);
            while(true) {
                byte[] buffer = new byte[BUF_SIZE];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Blocks until a datagram is received

                String json = new String(packet.getData(), 0, packet.getLength());

                try {
                    workQueue.put(json);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("IOException happened");
            e.printStackTrace();
        } finally { // Always executes when the try block exits
            socket.close();
        }
    }
}
