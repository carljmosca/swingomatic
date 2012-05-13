/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
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
        ServerSocket serverSocket = null;
        Socket connection = null;
        DataOutputStream out = null;
        BufferedReader in = null;
        String data = "";
        try {
            logger.debug("Trying to bind to localhost on port " + Integer.toString(listenPort) + "...");
            //make a ServerSocket and bind it to given listenPort,
            serverSocket = new ServerSocket(listenPort, 1024);
            connection = serverSocket.accept();
            InetAddress client = connection.getInetAddress();
            // test for listen address
            logger.debug("Client address: " + client.getHostAddress());
            if (!listenAddress.equalsIgnoreCase(client.getHostAddress())) {
                logger.error("Listening to " + listenAddress
                        + "...ignoring client address: " + client.getHostAddress());
                //break;
            }

            out = new DataOutputStream(connection.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            do {
                try {
                    char[] charData = new char[1024];
                    int r = 1;
                    while (r > 0) {
                        r = in.read(charData, 0, 1024);
                        if (r > 0) {
                            data = data + new String(charData, 0, r);
                            logger.debug("data:: " + data);
                        }
                        if (r < 1024) {
                            break;
                        }
                    }
                    logger.debug("read: " + data);
                    data = processMessage(out, data);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    break;
                }
            } while (true);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        } finally {
            //4: Closing connection
            try {
                in.close();
                out.close();
                serverSocket.close();
            } catch (IOException ioe) {
                logger.error(ioe.getMessage());
            }
        }

    }

    private String processMessage(DataOutputStream out, String data) {
        String result = data;
        if (data.indexOf("<") >= 0) {
            data = data.substring(data.indexOf("<"));

            int p = data.indexOf(END_OF_MESSAGE_TAG);
            if (p >= 0) {
                data = data.substring(0, p + END_OF_MESSAGE_TAG.length());
                logger.debug("data to sendMessage:" + data);
                sendMessage(out, data);
                result = "";
            }
        }
        return result;
    }

    private void sendMessage(DataOutputStream output, String message) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setOutput(output);
        sessionInfo.setMessage(message);
        setChanged();
        notifyObservers(sessionInfo);
    }

    private String readInput(BufferedReader input) {
        String result = "";
        char[] buffer = new char[1024];
        try {
            while (true) {
                int r = input.read(buffer, 0, 1024);
                if (r > 0) {
                    result = result + new String(buffer, 0, r);
                }
                logger.debug(r + " characters read");
                if (r < 1024) {
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
