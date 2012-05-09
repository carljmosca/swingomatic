/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.http;

/**
 *
 * This class is a from an HTTP server written by Jon Berg.
 * Thank you Jon.
 * Original work is available at http://fragments.turrlemeat.com/javawebserver.php
 */

public class ServerUtil {
    //this method makes the HTTP header for the response
    //the headers job is to tell the browser the result of the request
    //among if it was successful or not.

    public static String construct_http_header(int return_code, int file_type, String message) {
        String s = "HTTP/1.0 ";
        //you probably have seen these if you have been surfing the web a while
        switch (return_code) {
            case 200:
                s = s + "200 OK";
                break;
            case 400:
                s = s + "400 Bad Request";
                break;
            case 403:
                s = s + "403 Forbidden";
                break;
            case 404:
                s = s + "404 Not Found";
                break;
            case 500:
                s = s + "500 Internal Server Error";
                break;
            case 501:
                s = s + "501 Not Implemented";
                break;
        }

//        s = s + "<p>"; //other header fields,
//        s = s + "Connection: close" + System.getProperty("line.separator"); //we can't handle persistent connections
//        s = s + "Server: SimpleHTTPtutorial" + System.getProperty("line.separator"); //server name

        //Construct the right Content-Type for the header.
        //This is so the browser knows what to do with the
        //file, you may know the browser dosen't look on the file
        //extension, it is the servers job to let the browser know
        //what kind of file is being transmitted. You may have experienced
        //if the server is miss configured it may result in
        //pictures displayed as text!
        switch (file_type) {
            //plenty of types for you to fill in
            case 0:
                break;
            case 1:
                s = s + "Content-Type: image/jpeg\r\n";
                break;
            case 2:
                s = s + "Content-Type: image/gif\r\n";
            case 3:
                s = s + "Content-Type: application/x-zip-compressed\r\n";
            case 4:
                s = s + "Content-Type: application/xml<?xml version='1.0' encoding='ISO-8859-1'?>\r\n";
                break;
            default:
                s = s + "Content-Type: text/html\r\n";
                break;
        }

        ////so on and so on......
        s = s + "\r\n"; //this marks the end of the httpheader
        //and the start of the body
        //ok return our newly created header!
        s = s + message;
        return s;
    }
}
