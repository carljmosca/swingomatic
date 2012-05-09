/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.http;

import java.io.DataOutputStream;

/**
 *
 * @author Carl J. Mosca
 */
public class SessionInfo {
    
    private String message;
    private String response;
    private DataOutputStream output;

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

           
}
