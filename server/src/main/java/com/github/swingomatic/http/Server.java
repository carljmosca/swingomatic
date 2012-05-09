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
 * This class is a modified version of an HTTP server written by Jon Berg.
 * Thank you Jon.
 * Original work is available at http://fragments.turrlemeat.com/javawebserver.php
 */

public class Server extends Observable implements Runnable {

    private static Logger logger = Logger.getLogger(Server.class);
    private int port;

    public Server(int listen_port) {
        port = listen_port;
    }

    public void run() {
        ServerSocket serversocket;
        try {
            logger.debug("Trying to bind to localhost on port " + Integer.toString(port) + "...");
            //make a ServerSocket and bind it to given port,
            serversocket = new ServerSocket(port);
        } catch (Exception e) { //catch any errors and print errors to gui
            sendMessage(null, "Fatal Error:" + e.getMessage());
            logger.error("Fatal error: " + e.getMessage());
            return;
        }
        //go in a infinite loop, wait for connections, process request, send response
        while (true) {
            logger.debug("Ready, Waiting for requests...");
            try {
                //this call waits/blocks until someone connects to the port we
                //are listening to
                Socket connectionsocket = serversocket.accept();
                InetAddress client = connectionsocket.getInetAddress();
                // TODO: add test for localhost or configurable address
                logger.debug("Client address: " + client);
                //and print it to gui
                sendMessage(null, client.getHostName() + " connected to server.\n");
                //Read the http request from the client from the socket interface
                //into a buffer.
                
                BufferedReader input = new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
                //Prepare a outputstream from us to the client,
                //this will be used sending back our response
                //(header + requested file) to the client.
                DataOutputStream output = new DataOutputStream(connectionsocket.getOutputStream());

                http_handler(input, output);
            } catch (Exception e) { //catch any errors, and print them
                sendMessage(null, "\nError:" + e.getMessage());
            }

        } //go back in loop, wait for next request
    }

    private void http_handler(BufferedReader input, DataOutputStream output) {
        String http = new String(); //a bunch of strings to hold
        String path = new String(); //the various things, what http v, what path,
        try {
            //This is the two types of request we can handle
            //GET /index.html HTTP/1.0
            //HEAD /index.html HTTP/1.0
            String tmp = input.readLine(); //read from the stream
            String tmp2 = new String(tmp);
            tmp.toUpperCase(); //convert it to uppercase

            int start = 0;
            int end = 0;
            for (int a = 0; a < tmp2.length(); a++) {
                if (tmp2.charAt(a) == ' ' && start != 0) {
                    end = a;
                    break;
                }
                if (tmp2.charAt(a) == ' ' && start == 0) {
                    start = a;
                }
            }
            path = tmp2.substring(start + 2, end); //fill in the path
            sendMessage(output, path);
            return;
        } catch (Exception e) {
            sendMessage(output, "error" + e.getMessage());
        }

        sendMessage(output, "\nClient requested:" + path + "\n");
        try {
            output.writeBytes(ServerUtil.construct_http_header(404, 0, ""));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        try {
            output.writeBytes(ServerUtil.construct_http_header(200, 5, ""));
            //clean up the files, close open handles
            output.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void sendMessage(DataOutputStream output, String message) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setOutput(output);
        sessionInfo.setMessage(message);        
        setChanged();
        notifyObservers(sessionInfo);
    }

}
