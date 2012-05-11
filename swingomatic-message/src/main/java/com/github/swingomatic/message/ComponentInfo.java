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
    
}
