package org.graphwalker.example;


import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.*;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class GraphWalkerWebSocketClient {

    private WebSocketClient cc;

    private enum RX_STATE {NONE, HASNEXT, LOADMODEL, START, GETNEXT, GETDATA};

    public boolean connected = false;
    public RX_STATE rxState = RX_STATE.NONE;
    public boolean cmd = false;
    public boolean hasNext = false;

    public GraphWalkerWebSocketClient(String location) throws URISyntaxException {
        cc = new WebSocketClient(new URI(location), new Draft_17()) {

            @Override
            public void onMessage( String message ) {
                System.out.println("got: " + message);
                JSONObject response = new JSONObject();
                JSONObject root = null;
                try {
                    root = new JSONObject(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                rxState = RX_STATE.NONE;
                cmd = false;
                String type = root.getString("type").toUpperCase();
                if (type.equals("HASNEXT")) {
                    hasNext = false;
                    rxState = RX_STATE.HASNEXT;
                    if ( root.getBoolean("success")) {
                        cmd = true;
                        if ( root.getBoolean("hasNext")) {
                            hasNext = true;
                        }
                    }
                }
                else if (type.equals("LOADMODEL")) {
                    rxState = RX_STATE.LOADMODEL;
                    if ( root.getBoolean("success")) {
                        cmd = true;
                    }
                }
                else if (type.equals("START")) {
                    rxState = RX_STATE.START;
                    if ( root.getBoolean("success")) {
                        cmd = true;
                    }
                }
                else if (type.equals("GETNEXT")) {
                    rxState = RX_STATE.GETNEXT;
                    if ( root.getBoolean("success")) {
                        cmd = true;
                    }
                }
                else if (type.equals("GETDATA")) {
                    rxState = RX_STATE.GETDATA;
                    if ( root.getBoolean("success")) {
                        cmd = true;
                    }
                }
            }

            @Override
            public void onOpen( ServerHandshake handshake ) {
                System.out.println("You are connected to ChatServer: " + getURI());
                connected = true;
            }

            @Override
            public void onClose( int code, String reason, boolean remote ) {
                System.out.println("You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason);
                connected = false;
            }

            @Override
            public void onError( Exception ex ) {
                System.out.println("Exception occured ...\n" + ex);
                ex.printStackTrace();
            }
        };
        cc.connect();
    }

    public void loadModel() {
        cc.send("{\n" +
            "    \"type\": \"loadModel\",\n" +
            "    \"model\": {\n" +
            "        \"name\": \"Small model\",\n" +
            "        \"generator\": \"random(edge_coverage(100))\",\n" +
            "        \"vertices\": [\n" +
            "            {\n" +
            "                \"name\": \"v_VerifySomeAction\",\n" +
            "                \"id\": \"n0\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"v_VerifySomeOtherAction\",\n" +
            "                \"id\": \"n1\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"edges\": [\n" +
            "            {\n" +
            "                \"name\": \"e_FirstAction\",\n" +
            "                \"id\": \"e0\",\n" +
            "                \"dstVertexId\": \"n0\",\n" +
            "                \"startElement\": \"true\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"e_AnotherAction\",\n" +
            "                \"id\": \"e1\",\n" +
            "                \"srcVertexId\": \"n0\",\n" +
            "                \"dstVertexId\": \"n1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"e_SomeOtherAction\",\n" +
            "                \"id\": \"e2\",\n" +
            "                \"srcVertexId\": \"n1\",\n" +
            "                \"dstVertexId\": \"n1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"e_SomeOtherAction\",\n" +
            "                \"id\": \"e3\",\n" +
            "                \"srcVertexId\": \"n1\",\n" +
            "                \"dstVertexId\": \"n0\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}\n");
    }

    public void close() {
        cc.close();
    }

    public void start() {
        cc.send( "{\n" +
            "    \"type\": \"start\"\n" +
            "}\n");
    }

    public void getNext() {
        cc.send( "{\n" +
            "    \"type\": \"getNext\"\n" +
            "}\n" );
    }

    public void hasNext() {
        cc.send( "{\n" +
            "    \"type\": \"hasNext\"\n" +
            "}\n");
    }

    public void getData() {
        cc.send( "{\n" +
            "    \"type\": \"getData\"\n" +
            "}\n");
    }

    static public void wait(GraphWalkerWebSocketClient client, RX_STATE state) throws InterruptedException {
        while (client.rxState != state) {
            Thread.sleep(100);
        }
        if (!client.cmd) {
            throw new RuntimeException("Failed to execute command!");
        }
    }


    static public void main(String[] args) throws InterruptedException {
        WebSocketImpl.DEBUG = true;
        String location;
        if (args.length != 0) {
            location = args[0];
            System.out.println("Default server url specified: \'" + location + "\'");
        } else {
            location = "ws://localhost:8887";
            System.out.println("Default server url not specified: defaulting to \'" + location + "\'");
        }
        try {
            GraphWalkerWebSocketClient graphWalkerWebSocketClient = new GraphWalkerWebSocketClient(location);
            while (!graphWalkerWebSocketClient.connected) {
                Thread.sleep(100);
            }

            graphWalkerWebSocketClient.loadModel();
            wait(graphWalkerWebSocketClient, RX_STATE.LOADMODEL);

            graphWalkerWebSocketClient.start();
            wait(graphWalkerWebSocketClient, RX_STATE.START);

            graphWalkerWebSocketClient.hasNext();
            wait(graphWalkerWebSocketClient, RX_STATE.HASNEXT);

            while (graphWalkerWebSocketClient.hasNext) {
                graphWalkerWebSocketClient.getNext();
                wait(graphWalkerWebSocketClient, RX_STATE.GETNEXT);

                graphWalkerWebSocketClient.getData();
                wait(graphWalkerWebSocketClient, RX_STATE.GETDATA);

                graphWalkerWebSocketClient.hasNext();
                wait(graphWalkerWebSocketClient, RX_STATE.HASNEXT);
            }
            graphWalkerWebSocketClient.close();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}