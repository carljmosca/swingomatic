package com.github.swingomatic.server;

import java.awt.Component;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.swing.tree.DefaultMutableTreeNode;

public class ComponentObject extends DefaultMutableTreeNode {
    private Component component = null;
    private String name;

    public ComponentObject(String name) {
        super();
        setUserObject(this);
        this.name = name;
    }

    public ComponentObject(Component component) {
        super();
        setUserObject(this);
        this.component = component;
        if (component instanceof Accessible) {
	    AccessibleContext ac = ((Accessible) component).getAccessibleContext();
	    if ((ac != null) 
		&& (ac.getAccessibleRole() == AccessibleRole.UNKNOWN)) {
                name = '!' + ac.getAccessibleName();
	    } else {
		if (ac != null) {
		    name = '*' + ac.getAccessibleName();
		} else {
		    name = component.getName();
		}
	    }
        } else {
	    name = component.getName();
        }
        name = name + " (" + component.getClass().toString() + ")";
    }

    public String toString() {
        return name;
    }

    public Component getComponent() {
        return component;
    }

    public String getName() {
        return name;
    }

}
