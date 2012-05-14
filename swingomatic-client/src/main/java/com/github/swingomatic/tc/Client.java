/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.tc.http.HttpClientWrapper;
import com.thoughtworks.xstream.XStream;

/**
 *
 * @author moscac
 */
public class Client {

    public ApplicationCommand testApplicationCommandExecute(String serverURL,
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
}
