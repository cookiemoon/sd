package ws;

import org.apache.struts2.interceptor.SessionAware;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws")
public class WebSocketAnnotation implements SessionAware {
    private Map<String, Object> session;
    private Session connection;
    private static final ConcurrentHashMap<String, WebSocketAnnotation> onlineUsers = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session connection) {
        this.connection = connection;
    }

    @OnClose
    public void onClose() {
        // clean up once the WebSocket connection is closed
        onlineUsers.remove(this.connection);
    }

    @OnMessage
    public void onMessage(String message) {
        onlineUsers.put(message, this);
    }

    private void sendMessage(String text) {
        // uses *this* object's session to call sendText()
        try {
            this.connection.getBasicRemote().sendText(text);
        } catch (IOException e) {
            // clean up once the WebSocket connection is closed
            try {
                this.connection.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @OnError
    public void handleError(Throwable t) {
    	t.printStackTrace();
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
