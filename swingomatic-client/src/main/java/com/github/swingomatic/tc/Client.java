/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import com.github.swingomatic.tc.http.HttpClientWrapper;
import com.thoughtworks.xstream.XStream;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author moscac
 */
public class Client {

    public ApplicationCommand execute(String serverURL,
            ApplicationCommand applicationCommand) throws Exception {
        
        XStream xstream = new XStream();
        String message = xstream.toXML(applicationCommand);
        String response = HttpClientWrapper.doCall(serverURL, "/", message);
        int p = response.indexOf("?>");
        if (p >= 0) {
            response = response.substring(p + 2);
        }
        return (ApplicationCommand) xstream.fromXML(response);
    }
    
    public ObservableList<ComponentInfo> getComponentInfoList(ApplicationCommand applicationCommand) {
        ObservableList<ComponentInfo> list = FXCollections.observableList(new ArrayList<ComponentInfo>(0));
        for (Object object : applicationCommand.getComponents()) {
            if (object instanceof ComponentInfo) {
                list.add((ComponentInfo)object);
            }
        }
        return list;
    }
}
