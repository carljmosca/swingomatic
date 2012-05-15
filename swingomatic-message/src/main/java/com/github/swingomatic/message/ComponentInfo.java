/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.message;

/**
 *
 * @author Carl J. Mosca
 */
public class ComponentInfo {
    private String name;
    private String clazz;
    private String ofLabel;
    private String text;
    private String command;
    private String toolTipText;
    private String caption;
    private int xCoordinate;
    private int yCoordinate;
    private int delay;
    private int retries;
    private boolean selected;
    
    public ComponentInfo() {
        
    }
    
    public ComponentInfo(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public ComponentInfo(String name, String clazz, String ofLabel, String text) {
        this.name = name;
        this.clazz = clazz;
        this.ofLabel = ofLabel;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getOfLabel() {
        return ofLabel;
    }

    public void setOfLabel(String ofLabel) {
        this.ofLabel = ofLabel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public String toString() {
        return "name: " + name + " clazz: " + clazz + " ofLabel: " + ofLabel
                + " text: " + text + " command: " + command + " toolTipText: " +
                toolTipText;
    }
    
}
