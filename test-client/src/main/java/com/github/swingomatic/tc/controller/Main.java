/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc.controller;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import com.github.swingomatic.tc.Client;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Carl J. Mosca
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public void init(boolean show) {
        Client client = new Client();
        ApplicationCommand ac = new ApplicationCommand();
        ac.setCommand("list-components");
        try {
            ac = client.execute("http://localhost:8088", ac);
            List<ComponentInfo> list = ac.getComponents();
            for (ComponentInfo componentInfo : list) {
                System.out.println(componentInfo.toString());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        
    }
    
}
