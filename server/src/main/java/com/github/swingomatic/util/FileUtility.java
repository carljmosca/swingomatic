package com.github.swingomatic.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtility {

    public static void close(FileInputStream fileInputStream) {
        try {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (IOException ioe) {
        }
    }

    public static void close(FileOutputStream fileOutputStream) {
        try {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException ioe) {
        }
    }  
    
    
}
