/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import org.apache.log4j.Logger;

/**
 *
 * This class is a modified version of an HTTP server written by Jon Berg. Thank
 * you Jon. Original work is available at
 * http://fragments.turrlemeat.com/javawebserver.php
 */
public class Server extends Observable implements Runnable {

    private static Logger logger = Logger.getLogger(Server.class);
    private int listenPort;
    private String listenAddress;
    private final String END_OF_MESSAGE_TAG = "</com.github.swingomatic.message.ApplicationCommand>";

    public Server(String listenAddress, int listenPort) {
        this.listenAddress = listenAddress;
        this.listenPort = listenPort;
    }

    public void run() {
        ServerSocket serversocket;
        try {
            logger.debug("Trying to bind to localhost on port " + Integer.toString(listenPort) + "...");
            //make a ServerSocket and bind it to given listenPort,
            serversocket = new ServerSocket(listenPort);
        } catch (Exception e) { //catch any errors and print errors to gui
            sendMessage(null, null, "Fatal Error:" + e.getMessage());
            logger.error("Fatal error: " + e.getMessage());
            return;
        }
        //go in a infinite loop, wait for connections, process request, send response
        while (true) {
            logger.debug("Ready, Waiting for requests...");
            Socket connectionsocket = null;
            if (connectionsocket != null && !connectionsocket.isClosed()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    logger.error(ex.getMessage());
                }
            } else {
                try {
                    //this call waits/blocks until someone connects to the listenPort we
                    //are listening to
                    connectionsocket = serversocket.accept();
                    InetAddress client = connectionsocket.getInetAddress();
                    // test for listen address
                    logger.debug("Client address: " + client.getHostAddress());
                    if (!listenAddress.equalsIgnoreCase(client.getHostAddress())) {
                        logger.error("Listening to " + listenAddress
                                + "...ignoring client address: " + client.getHostAddress());
                        break;
                    }
                    sendMessage(null, null, client.getHostName() + " connected to server.\n");
                    //Read the http request from the client from the socket interface
                    //into a buffer.
                    //BufferedReader input = new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
                    //Prepare a outputstream from us to the client,
                    //this will be used sending back our response
                    //(header + requested file) to the client.
                    DataOutputStream output = new DataOutputStream(connectionsocket.getOutputStream());

                    http_handler(connectionsocket, connectionsocket.getInputStream(), output);
                    //input.close();
                    //connectionsocket.getInputStream().close();
                } catch (Exception e) { //catch any errors, and print them
                    sendMessage(null, null, "\nError:" + e.getMessage());
                }
            }
        } //go back in loop, wait for next request
    }

//    private void http_handler(BufferedReader input, DataOutputStream output) {
    private void http_handler(Socket socket, InputStream input, DataOutputStream output) {
        //String http = new String(); //a bunch of strings to hold
        //String path = new String(); //the various things, what http v, what path,
        String data = "";
        try {
            //This is the two types of request we can handle
            //GET /index.html HTTP/1.0
            //HEAD /index.html HTTP/1.0

            data = readInput(input);
            //String data = "";
//            char[] buffer = new char[1024];
//            try {
//                while (true) {
//                    int r = input.read(buffer, 0, 1024);
//                    path = path + new String(buffer);
//                    if (r != 1024) {
//                        break;
//                    }
//                }
//            } catch (IOException ioe) {
//                logger.error(ioe.getMessage());
//            }
            if (data.indexOf("<") >= 0) {
                data = data.substring(data.indexOf("<"));
            }
            int p = data.indexOf(END_OF_MESSAGE_TAG);
            if (p >= 0) {
                data = data.substring(0, p + END_OF_MESSAGE_TAG.length());
            }
            logger.debug("data to sendMessage:" + data);
            sendMessage(socket, output, data);
            return;
        } catch (Exception e) {
            sendMessage(socket, output, "error" + e.getMessage());
        }

        sendMessage(socket, output, "\nClient requested:" + data + "\n");
        try {
            output.writeBytes(ServerUtil.construct_http_header(404, 0, ""));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        try {
            output.writeBytes(ServerUtil.construct_http_header(200, 5, ""));
            //clean up the files, close open handles
            //output.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void sendMessage(Socket socket,
            DataOutputStream output, String message) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setOutput(output);
        sessionInfo.setSocket(socket);
        sessionInfo.setMessage(message);
        setChanged();
        notifyObservers(sessionInfo);
    }

    private String readInput(InputStream input) {
        String result = "";
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                int r = input.read(buffer, 0, 1024);
                result = result + new String(buffer);
                if ((r < 0) || (r != 1024)) {
                    break;
                }
            }
            //input.close();
            logger.debug("readInput: " + result);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
        return result;
    }
}
