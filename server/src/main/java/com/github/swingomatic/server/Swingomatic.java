/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.server;

import com.github.swingomatic.http.Server;
import com.github.swingomatic.http.ServerUtil;
import com.github.swingomatic.http.SessionInfo;
import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import com.sun.java.accessibility.util.EventQueueMonitor;
import com.sun.java.accessibility.util.GUIInitializedListener;
import com.sun.java.accessibility.util.SwingEventMonitor;
import com.sun.java.accessibility.util.Translator;
import com.thoughtworks.xstream.XStream;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;

public class Swingomatic implements
        ActionListener, KeyListener, GUIInitializedListener, DocumentListener {

    private JTree accessibleTree;
    private static int depth = 0;
    private static final int MAX_DEPTH = 100;
    private static Logger logger = Logger.getLogger(Swingomatic.class);
    private Server server;

    public static void main(String s[]) {
        Swingomatic app = new Swingomatic();
    }

    public Swingomatic() {
        initialize();
        logger.debug("Swingomatic server....");
    }

    private void initialize() {
        EventQueueMonitor.addGUIInitializedListener(this);
        createGUI();
        //TODO: get listen address and port port from parameter file
        server = new Server("127.0.0.1", 8088);
        server.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                SessionInfo sessionInfo = (SessionInfo) arg;
                if (sessionInfo.getOutput() != null) {
                    XStream xstream = new XStream();
                    logger.debug("Message from server: " + sessionInfo.getMessage());
                    sessionInfo.setResponse(getUsage() + " \r\nreceived: " + sessionInfo.getMessage());
                    if ((sessionInfo.getMessage() != null) && sessionInfo.getMessage().trim().length() >= 0) {
                        if ("list-components".equalsIgnoreCase(sessionInfo.getMessage())) {
                            ApplicationCommand ac = createComponentTree();
                            sessionInfo.setResponse(xstream.toXML(ac));
                        } else if (sessionInfo.getMessage().toLowerCase().startsWith("execute")) {
                            sessionInfo.setResponse(executeCommand(sessionInfo.getMessage().substring(7)));
                        }
                    }
                    sendResponseToClient(sessionInfo);
                }
            }
        });
        new Thread(server).start();
    }

    private String getUsage() {
        String result = "Usage:\r\n"
                + "list-components\r\n"
                + "execute<XML>";
        return result;
    }
    
    private String executeCommand(String xml) {
        String result = "";
        XStream xstream = new XStream();
        Object o = xstream.fromXML(xml);
        o.toString();
        return result;
    }

    private void sendResponseToClient(SessionInfo sessionInfo) {
        if ((sessionInfo.getOutput() == null) && (sessionInfo.getResponse() != null)) {
            return;
        }
        try {
            sessionInfo.getOutput().writeBytes(ServerUtil.construct_http_header(200, 4,
                    sessionInfo.getResponse()));
            sessionInfo.getOutput().close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        logger.debug("ActionEvent: " + e.getActionCommand());
//        if ("Show Input Dialog".equals(e.getActionCommand())) {
//            createComponentTree();
//        }
    }

    public void keyTyped(KeyEvent e) {
        logger.debug("Key typed KeyEvent: " + e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void guiInitialized() {
        createGUI();
    }

    private void createGUI() {
        WindowListener l = new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        createComponentTree();

        accessibleTree = createAccessibleTree();

        SwingEventMonitor.addKeyListener(this);
        SwingEventMonitor.addDocumentListener(this);
        SwingEventMonitor.addActionListener(this);
        SwingEventMonitor.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                logger.debug("PropertyChange: " + evt.getPropertyName());
            }
        });
        SwingEventMonitor.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                logger.debug("StateChanged: " + e.getSource().toString());
            }
        });
        SwingEventMonitor.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if ("class com.ams.momentum.widgets.ComboBox".equals(e.getComponent().getClass().toString())) {
                    createComponentTree();
                }
                logger.debug("focusGained: " + e.getComponent().getClass().toString());
            }

            public void focusLost(FocusEvent e) {
            }
        });
        SwingEventMonitor.addContainerListener(new ContainerListener() {

            public void componentAdded(ContainerEvent e) {
                if ("seeThroughGlassPane".equals(e.getChild().getName())
                        || "null.glassPane".equals(e.getChild().getName())) {
                    createComponentTree();

                }
                logger.debug("ComponentAdded: " + e.getChild().getName());
            }

            public void componentRemoved(ContainerEvent e) {
            }
        });
    }

    private ApplicationCommand createComponentTree() {
        ApplicationCommand ac = new ApplicationCommand();
        ac.setResult("OK");
        ac.setComponents(new ArrayList(0));
        Window[] wins = EventQueueMonitor.getTopLevelWindows();
        ComponentObject root = new ComponentObject("Component Tree");
        for (int i = 0; i < wins.length; i++) {
            addComponentNodes(wins[i], root, ac.getComponents());
        }
        return ac;
    }

    private List addComponentNodes(Component c, ComponentObject root, List result) {
        String name;
        ComponentObject me;
        me = new ComponentObject(c);
        root.add(me);
        if (c instanceof Container) {
            int count = ((Container) c).getComponentCount();
            for (int i = 0; i < count; i++) {
                Component comp = ((Container) c).getComponent(i);
                result.add(new ComponentInfo(comp.getName(), comp.getClass().toString()));
                if (comp instanceof JLabel) {
                    JLabel jLabel = (JLabel) comp;
                    ((ComponentInfo) result.get(result.size() - 1)).setText(jLabel.getText());
                    if (jLabel.getLabelFor() != null) {
                        Component ofComponent = jLabel.getLabelFor();
                        result.add(new ComponentInfo(ofComponent.getName(),
                                ofComponent.getClass().toString(),
                                jLabel.getText(),
                                ""));
                    }
                    if ("Document Title:".equals(jLabel.getText())) {
                        if (jLabel.getLabelFor() != null) {
                            ((JTextField) jLabel.getLabelFor()).setText("hello----");
                        }
                    }
                }
                addComponentNodes(((Container) c).getComponent(i), me, result);
            }
        }
        return result;
    }

    private void showComponent(Component c) {
        logger.debug("Component - Name: " + c.getName()
                + " Class: " + c.getClass().toString());
    }

    private JTree createAccessibleTree() {
        final JTree t;
        Window[] wins = EventQueueMonitor.getTopLevelWindows();

        AccessibleObject root = new AccessibleObject("Accessible Tree");
        Accessible accessible;
        for (int i = 0; i < wins.length; i++) {
            accessible = Translator.getAccessible(wins[i]);
            if (accessible != null) {
                addAccessibleNodes(accessible, root);
            }
        }

        t = new JTree(root);

        MouseListener ml = new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
                    TreePath selPath = t.getPathForLocation(e.getX(), e.getY());
                    if (selPath != null) {
                        Object o = selPath.getLastPathComponent();
                        if (o instanceof AccessibleObject) {
                            Accessible a = ((AccessibleObject) o).getAccessible();
                        }
                    }
                }
            }
        };

        t.addMouseListener(ml);
        return t;
    }

    private void addAccessibleNodes(Accessible a, AccessibleObject root) {

        if (depth >= MAX_DEPTH) {
            return;
        }
        depth++;

        AccessibleObject me;
        me = new AccessibleObject(a);
        root.add(me);

        AccessibleContext ac = me.getAccessible().getAccessibleContext();
        if (ac != null) {
            Accessible ap = ac.getAccessibleParent();
            if (ap != root.getAccessible()) {
                logger.debug("Accessible parent of " + a
                        + " should be " + root.getAccessible() + " but it is "
                        + ap);
            }
            int count = ac.getAccessibleChildrenCount();
            for (int i = 0; i < count; i++) {
                addAccessibleNodes(ac.getAccessibleChild(i), me);
            }
        }
        depth--;
    }

    public void insertUpdate(DocumentEvent e) {
        logger.debug("DocumentEvent insertUpdate" + e.toString());
    }

    public void removeUpdate(DocumentEvent e) {
        logger.debug("DocumentEvent removeUpdate" + e.getDocument().toString());
    }

    public void changedUpdate(DocumentEvent e) {
        logger.debug("DocumentEvent changedUpdate" + e.toString());
    }
}
