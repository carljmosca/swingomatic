/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.message;

import java.util.List;

/**
 *
 * @author moscac
 */
public class ApplicationCommand {
    
    private String command;
    private String parameter;
    private String result;
    private List components;
    private int returnCode;
    private int lastProcessedComponent;
    private int uiRefreshTimeout = 1000;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public List getComponents() {
        return components;
    }

    public void setComponents(List components) {
        this.components = components;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public int getLastProcessedComponent() {
        return lastProcessedComponent;
    }

    public void setLastProcessedComponent(int lastProcessedComponent) {
        this.lastProcessedComponent = lastProcessedComponent;
    }

    public int getUiRefreshTimeout() {
        return uiRefreshTimeout;
    }

    public void setUiRefreshTimeout(int uiRefreshTimeout) {
        this.uiRefreshTimeout = uiRefreshTimeout;
    }

}
