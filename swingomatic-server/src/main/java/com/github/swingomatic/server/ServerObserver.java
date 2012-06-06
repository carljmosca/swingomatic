/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.server;

import com.github.swingomatic.http.Server;
import com.github.swingomatic.http.ServerUtil;
import com.github.swingomatic.http.SessionInfo;
import com.github.swingomatic.message.ApplicationCommand;
import com.thoughtworks.xstream.XStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Observable;
import java.util.Observer;
import org.apache.log4j.Logger;

/**
 *
 * @author Carl J. Mosca
 */
public class ServerObserver implements Observer {

    private static Logger logger = Logger.getLogger(ServerObserver.class);
    private Swingomatic swingomatic;
    private Server server;
    private ServerSocket serverSocket;
    private String listenAddress;

    public ServerObserver(Swingomatic swingomatic, ServerSocket serverSocket,
            String listenAddress) {
        this.swingomatic = swingomatic;
        this.serverSocket = serverSocket;
        this.listenAddress = listenAddress;
        createServer();
    }

    private void createServer() {
        server = new Server(serverSocket, listenAddress);
        server.addObserver(this);
        new Thread(server).start();
    }

    public void update(Observable o, Object arg) {
        SessionInfo sessionInfo = (SessionInfo) arg;
        if (sessionInfo.getOutput() != null) {
            XStream xstream = new XStream();
            logger.debug("Message from server: " + sessionInfo.getMessage() + " from XML");
            String message = sessionInfo.getMessage();
            ApplicationCommand ac = (ApplicationCommand) xstream.fromXML(message);
            ac.setResult(swingomatic.getUsage() + " \r\nreceived: " + ac.getCommand());
            if ("list-components".equalsIgnoreCase(ac.getCommand())) {
                ac = swingomatic.listComponents();
            } else if ("execute".equalsIgnoreCase(ac.getCommand())) {
                ac.setLastProcessedComponent(0);
                boolean ok = true;
                while (ok || swingomatic.componentsWereAdded) {
                    logger.debug("calling execute: " + ac.getLastProcessedComponent()
                            + " " + swingomatic.componentsWereAdded);
                    ok = swingomatic.executeCommand(ac);
                    logger.debug("execute result - lastProcessedComponent: " + ac.getLastProcessedComponent()
                            + " componentsWereAdded: " + swingomatic.componentsWereAdded
                            + " result: " + ok);
                    if (ok) {
                        if (ac.getLastProcessedComponent() >= ac.getComponents().size()) {
                            break;
                        }
                    } else {
                        if (ac.getUiRefreshTimeout() > 0) {
                            logger.debug("did not complete: waiting " + ac.getUiRefreshTimeout() + " milliseconds...");
                            try {
                                Thread.sleep(ac.getUiRefreshTimeout());
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
            sessionInfo.setResponse(xstream.toXML(ac));
            sendResponseToClient(sessionInfo);
            ((Server) o).terminate();
            createServer();
        }
    }

    public void sendResponseToClient(SessionInfo sessionInfo) {
        try {
            logger.debug("sendResponseToClient:" + sessionInfo.getResponse());
            sendMessage(sessionInfo.getOutput(),
                    ServerUtil.construct_http_header(200, 4,
                    sessionInfo.getResponse()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    void sendMessage(DataOutputStream out, String msg) {
        try {
            msg = msg + '\n';
            out.write(msg.getBytes());
            out.flush();
            logger.debug("sendMessage: " + msg);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
    }
}
