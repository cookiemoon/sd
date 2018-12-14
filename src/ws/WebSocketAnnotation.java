package ws;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.Session;

@ServerEndpoint(value = "/ws")
public class WebSocketAnnotation {

    public WebSocketAnnotation() {

    }

    @OnOpen
    public void onOpen(Session session) {

    }

    @OnClose
    public void onClose() {

    }

    @OnMessage
    public void onMessage(String message) {

    }

    @OnError
    public void handleError(Throwable t) {
    	t.printStackTrace();
    }

}
