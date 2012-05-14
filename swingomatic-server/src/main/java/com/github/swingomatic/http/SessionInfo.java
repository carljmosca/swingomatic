/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.http;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Carl J. Mosca
 */
public class SessionInfo {
    
    private String message;
    private String response;
    private DataOutputStream output;
    private Socket socket;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
          
}
