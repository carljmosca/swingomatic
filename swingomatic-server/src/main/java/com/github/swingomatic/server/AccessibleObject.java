package com.github.swingomatic.server;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.swing.tree.DefaultMutableTreeNode;

public class AccessibleObject extends DefaultMutableTreeNode {
    private Accessible accessible = null;
    private String name;

    public AccessibleObject(String name) {
        super();
        this.setUserObject(this);
        this.name = name;
    }

    public AccessibleObject(Accessible accessible) {
        super();
        this.setUserObject(this);
        this.accessible = accessible;
	AccessibleContext ac = accessible.getAccessibleContext();
        if (ac != null) {
	    if (ac.getAccessibleRole() == AccessibleRole.UNKNOWN) {
                name = '!' + ac.getAccessibleName();
	    } else {
                name = '*' + ac.getAccessibleName();
	    }
	}
        name = name + " (" + accessible.getClass().toString() + ")";
    }

    public String toString() {
        return name;
    }

    public Accessible getAccessible() {
        return accessible;
    }

    public String getName() {
        return name;
    }
    
}
