package com.github.swingomatic.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.github.swingomatic.server.Swingomatic;
import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyManager.
 */
public class PropertyManager {

    /** Property file key names */
    private static final String SERVERPORT_KEY = "ServerPort";
    private static final String CLIENTADDRESS_KEY = "ClientAddress";
    private static final String MAXCONNECTIONS_KEY = "MaxConnections";
    
    /** The Constant SWINGOMATIC_PROPERTIES.     */
    private static final String SWINGOMATIC_PROPERTIES = "swingomatic.properties";
    
    /** The Constant logger.     */
    private static Logger logger = Logger.getLogger(Swingomatic.class);
    
    /** The properties.      */
    private static Properties properties = null;
    
    /** The instance.     */
    private static PropertyManager instance;
    private static int serverPort;
    private static String clientAddress = "";
    private static int maxConnections;

    /**
     * Instantiates a new property manager.
     */
    private PropertyManager() {
        try {
            String propFilename = getPropertiesFile();
            File propFile = new File(propFilename);
            if (!propFile.exists()) {
                logger.info("Creating properties file with default values: " + propFilename);
                setServerPort(8088);
                setClientAddress("127.0.0.1");
                setMaxConnections(2);
                saveSettings();
            }

            logger.info("Loading properties file from: " + propFilename);
            loadFromFile(propFilename);
        } catch (Exception e) {
            logger.error("Error occurred loading property file", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    // service methods
    /**
     * Gets the single instance of PropertyManager.
     *
     * @return single instance of PropertyManager
     */
    public final static synchronized PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the property
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Returns the IP-address/hostname of the client that is allowed to make
     * requests from the Swingomatic server.  Requests from any other client
     * will be rejected.
     * 
     * @return 
     */
    public String getClientAddress() {
        return clientAddress;
    }

    public final void setClientAddress(String clientAddress) {
        PropertyManager.clientAddress = clientAddress;
    }

    public static int getMaxConnections() {
        return maxConnections;
    }

    public static void setMaxConnections(int maxConnections) {
        PropertyManager.maxConnections = maxConnections;
    }

    /**
     * Returns the port number on which the Swingomatic http server is listening.
     * @return 
     */
    public int getServerPort() {
        return serverPort;
    }

    public final void setServerPort(int serverPort) {
        PropertyManager.serverPort = serverPort;
    }    
    

    // private methods
    /**
     * Load from file.
     *
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void loadFromFile(String file) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(file));
        setServerPort(Integer.parseInt(getProperty(SERVERPORT_KEY)));
        setClientAddress(getProperty(CLIENTADDRESS_KEY));
        setMaxConnections(Integer.parseInt(getProperty(MAXCONNECTIONS_KEY)));
    }

    /**
     * Gets the properties file.
     *
     * @return the properties file
     */
    private String getPropertiesFile() {
        StringBuffer propertyFile = new StringBuffer(
                System.getProperty("user.dir"));
        propertyFile.append(System.getProperty("file.separator"));
        propertyFile.append(SWINGOMATIC_PROPERTIES);

        return propertyFile.toString();
    }


    private void saveSettings() throws FileNotFoundException, IOException {
        properties = new Properties();
        properties.put(SERVERPORT_KEY, Integer.toString(getServerPort()));
        properties.put(CLIENTADDRESS_KEY, getClientAddress());
        properties.put(MAXCONNECTIONS_KEY, Integer.toString(getMaxConnections()));
        FileOutputStream fos = new FileOutputStream(getPropertiesFile());
        properties.store(fos, "Swingomatic Properties");
        FileUtility.close(fos);
    }
}
