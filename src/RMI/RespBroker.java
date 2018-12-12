package RMI;

import Shared.NetworkUtil;
import org.thavam.util.concurrent.BlockingHashMap;

import java.util.concurrent.TimeUnit;

public class RespBroker {
    int port;
    private BlockingHashMap<Integer, String> queue;

    public RespBroker(BlockingHashMap<Integer, String> queue, int port) {
        this.queue = queue;
        this.port = port;
    }

    public String waitForResponse(String json, Integer msgid) {
        // FIXME: have a maximum number of retries and send invalid request back to client
        // FIXME: Invalid request is always null
        String response = null;
        int tries = 0;
        int retries = 0;
        final int MAX_TRIES = 2;
        final int MAX_RETRIES = 2;
        final int TOO_MUCH_TIME = 500; // Half a second

        while(response == null) {
            try {
                response = queue.take(msgid, TOO_MUCH_TIME, TimeUnit.MILLISECONDS);
                if (response == null)
                    tries++;
                else
                    break;

                if (tries == MAX_TRIES) {
                    NetworkUtil.multicastSend(port, json);
                    tries = 0;
                    retries++;
                }

                if (retries == MAX_RETRIES) {
                    return null; // invalid request
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return response;
    }
}
