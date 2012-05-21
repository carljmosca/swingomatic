/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    
    public ComponentInfo() {
    }
    
    public ComponentInfo readResolve() {
        propertyChangeSupport = new PropertyChangeSupport(this);      
        return this;
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
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, name);
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        String oldClazz = this.clazz;
        this.clazz = clazz;
        propertyChangeSupport.firePropertyChange("clazz", oldClazz, clazz);
    }

    public String getOfLabel() {
        return ofLabel;
    }

    public void setOfLabel(String ofLabel) {
        String oldOfLabel = this.ofLabel;
        this.ofLabel = ofLabel;
        propertyChangeSupport.firePropertyChange("ofLabel", oldOfLabel, ofLabel);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        String oldText = this.text;
        this.text = text;
        propertyChangeSupport.firePropertyChange("text", oldText, text);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        String oldCommand = this.command;
        this.command = command;
        propertyChangeSupport.firePropertyChange("command", oldCommand, command);
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        String oldToolTipText = this.toolTipText;
        this.toolTipText = toolTipText;
        propertyChangeSupport.firePropertyChange("toolTipText", oldToolTipText, toolTipText);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        int oldDelay = this.delay;
        this.delay = delay;
        propertyChangeSupport.firePropertyChange("delay", oldDelay, delay);
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        int oldRetries = this.retries;
        this.retries = retries;
        propertyChangeSupport.firePropertyChange("retries", oldRetries, retries);
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        int oldXCoordinate = this.xCoordinate;
        this.xCoordinate = xCoordinate;
        propertyChangeSupport.firePropertyChange("xCoordinate", oldXCoordinate, xCoordinate);
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        int oldYCoordinate = this.yCoordinate;
        this.yCoordinate = yCoordinate;
        propertyChangeSupport.firePropertyChange("yCoordinate", oldYCoordinate, yCoordinate);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        String oldCaption = this.caption;
        this.caption = caption;
        propertyChangeSupport.firePropertyChange("caption", oldCaption, caption);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldSelected = this.selected;
        this.selected = selected;
        propertyChangeSupport.firePropertyChange("selected", oldSelected, selected);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public String toString() {
        return "name: " + name + " clazz: " + clazz + " ofLabel: " + ofLabel
                + " text: " + text + " command: " + command + " toolTipText: " +
                toolTipText;
    }
    
}
