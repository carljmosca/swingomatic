package com.github.swingomatic.tc.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocketFactory;


public class HttpClientWrapper {

    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = Logger.getLogger(HttpClientWrapper.class.getName());

    public static String doCall(String hostAddress, String path, String query) {
        boolean isSecure = false;
        StringBuilder result = new StringBuilder();
        try {
            int port = 80;
            if (hostAddress.startsWith("http://")) {
                hostAddress = hostAddress.substring(7);
            } else if (hostAddress.startsWith("https://")) {
                hostAddress = hostAddress.substring(8);
                isSecure = true;
            }
            String address = hostAddress;
            if (hostAddress.indexOf(":") >= 0) {
                int p = hostAddress.indexOf(":");
                address = hostAddress.substring(0, p);
                try {
                    port = Integer.parseInt(hostAddress.substring(p + 1));
                } catch (NumberFormatException e) {
                    port = 80;
                }
            }
            //open socket
            Socket socket;
            if (isSecure) {
                if (port == 80) {
                    port = 443;
                }
                socket = SSLSocketFactory.getDefault().createSocket(address, port);

            } else {
                socket = new Socket(address, port);
            }
            //send query
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream pw = new PrintStream(socket.getOutputStream());
            pw.print("POST " + path + " HTTP/1.0\n");
            pw.print("Content-Type: application/x-www-form-urlencoded\n");
            pw.print("User-Agent: java/socket\n");
            pw.print("Content-Length:" + query.length() + "\n\n");
            pw.print(query);
            //get result
            char[] charBuffer = new char[1024];
            String data = "";
            while (true) {
                int r = br.read(charBuffer, 0, 1024);
                if (r > 0) {
                    data = data + new String(charBuffer, 0, r);
                }
                if (r < 0) {
                    break;
                }
            }
            result.append(data);
            pw.close();
            br.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(HttpClientWrapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClientWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result.toString();
    }
}
