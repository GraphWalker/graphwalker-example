package com.company.observers;

import org.graphwalker.core.event.EventType;
import org.graphwalker.core.event.Observer;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Vertex;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Nils Olsson
 */
public class WebSocketObserver extends org.java_websocket.server.WebSocketServer implements Observer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketObserver.class);
    private Element lastElement = null;
    private Context lastContext = null;

    private Set<WebSocket> sockets;

    public WebSocketObserver(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        sockets = new HashSet<>();
    }

    public WebSocketObserver(InetSocketAddress address) {
        super(address);
        sockets = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake) {
        sockets.add(socket);
        logger.info(socket.getRemoteSocketAddress().getAddress().getHostAddress() + " is now connected");
    }

    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean remote) {
        sockets.remove(socket);
        logger.info(socket.getRemoteSocketAddress().getAddress().getHostAddress() + " has disconnected");
    }

    @Override
    public void onMessage(WebSocket socket, String message) {
        logger.debug("Received message from: "
                + socket.getRemoteSocketAddress().getAddress().getHostAddress()
                + " : "
                + message);
    }

    @Override
    public void onError(WebSocket socket, Exception ex) {
        ex.printStackTrace();
        if (socket != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void update(Machine machine, Element element, EventType eventType) {
        if (EventType.BEFORE_ELEMENT.equals(eventType)) {
            JSONObject jsonObject = new JSONObject();

            if (element instanceof Vertex.RuntimeVertex) {
                Vertex.RuntimeVertex vertex = (Vertex.RuntimeVertex) element;
                jsonObject.put("type", "node");
                jsonObject.put("id", getId(machine.getCurrentContext(), vertex));
                jsonObject.put("name", vertex.getName());
                jsonObject.put("visitedCount", machine.getProfiler().getVisitCount(element));
                sendElement(jsonObject);

                if (vertex.hasSharedState() &&
                        lastElement instanceof Vertex.RuntimeVertex &&
                        ((Vertex.RuntimeVertex) lastElement).hasSharedState() &&
                        machine.getCurrentContext() != lastContext) {
                    jsonObject = new JSONObject();
                    jsonObject.put("type", "edge");
                    jsonObject.put("id", getId(lastContext, lastElement) + getId(machine.getCurrentContext(), vertex));
                    jsonObject.put("name", "");
                    jsonObject.put("source", getId(lastContext, lastElement));
                    jsonObject.put("target", getId(machine.getCurrentContext(), vertex));
                    sendElement(jsonObject);
                }
            } else {
                Edge.RuntimeEdge edge = (Edge.RuntimeEdge) element;
                if (edge.getSourceVertex() != null &&
                        edge.getTargetVertex() != null) {
                    jsonObject.put("type", "edge");
                    jsonObject.put("id", getId(machine.getCurrentContext(), edge));
                    jsonObject.put("name", edge.getName());
                    jsonObject.put("visitedCount", machine.getProfiler().getVisitCount(element));
                    jsonObject.put("source", getId(machine.getCurrentContext(), edge.getSourceVertex()));
                    jsonObject.put("target", getId(machine.getCurrentContext(), edge.getTargetVertex()));
                    sendElement(jsonObject);
                }
            }

            lastElement = element;
            lastContext = machine.getCurrentContext();
        }
    }

    private void sendElement(JSONObject jsonObject) {
        for (WebSocket socket : sockets) {
            logger.debug("Sending: " + jsonObject.toString());
            socket.send(jsonObject.toString());
        }
    }

    private String getId(Context context, Element element) {
        return context.toString() + element.getId();
    }
}
