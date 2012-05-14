package com.github.swingomatic.tc.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientWrapper {

    /**
     * The Constant ENCONDING.
     */
    private static final String ENCONDING = HTTP.DEFAULT_CONTENT_CHARSET;
    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = Logger.getLogger(HttpClientWrapper.class.getName());

    /**
     *
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public String post(final String url, final String data) throws Exception {

        HttpClient client = null;
        String response = null;

        try {

            LOGGER.log(Level.INFO, "Posting data to: " + url);

            client = new DefaultHttpClient();
            // Setup the request headers

            client.getParams().setParameter(
                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            HttpPost post = new HttpPost(url);

            MultipartEntity entity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);


            // For File parameters
            if (StringUtils.isNotEmpty(data)) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
                nameValuePairs.add(new BasicNameValuePair("data", data));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

//                ByteArrayBody bab = new ByteArrayBody(data.getBytes(), "");
//                entity.addPart("data", bab);
//                post.setEntity(entity);

            }

            // Here we go!
            response = EntityUtils.toString(client.execute(post).getEntity(),
                    ENCONDING);

            return response;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occured posting request", e);
            throw e;
        } finally {
            try {
                client.getConnectionManager().shutdown();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE,
                        "Error occured shutting down HTTP client", e);
                /*
                 * No Op
                 */
            }
        }

    }

    public static String doCall(String hostAddress, String path, String query) {
        boolean isSecure = false;
        StringBuilder result = new StringBuilder();
        try {
            Socket socket = null;
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
            String l = "";
            while (true) {
                int r = br.read(charBuffer, 0, 1024);
                if (r > 0) {
                    l = l + new String(charBuffer, 0, r);
                }
                if (r < 0) {
                    break;
                }
            }
            result.append(l);
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
