/*
 * Frisbee: Formal Logic Tutor
 * 
 *  (C) Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibted.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */

package edu.regis.frisbee;

import edu.regis.frisbee.util.ResourceMgr;
import edu.regis.frisbee.view.MainFrame;
import edu.regis.frisbee.view.SplashFrame;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author rickb
 */
public class FrisbeeApp {
    /**
     * Property file located on the CLASSPATH, which is used to configure the LOGGER.
     */
    private static final String LOGGER_PROPERTIES = "/Logging.properties";

    /**
     * Events of interest occurring in this class are logged to this logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FrisbeeApp.class.getName());
    
    /**
     * Configure the LOGGER with the properties found in the LOGGER_PROPERTIES 
     * file found on the CLASSPATH.
     */
    static {
        final InputStream strm = 
            FrisbeeApp.class.getResourceAsStream(LOGGER_PROPERTIES);
        
        try {
            LogManager.getLogManager().readConfiguration(strm);
        } catch (IOException e) {
        
            LOGGER.severe("Error loading ./logging.properties");
            LOGGER.severe(e.getMessage());
        }        
    }
    
    /**
     * Main entry point for the ShaTut application, which will display the UI.
     * 
     * @param args ignored
     */
    public static void main(String[] args) {
        LOGGER.info("ShaTuApp Initializing...:");
        
        // Initialize the logging properties from the logging properties files
        try {
            final InputStream strm = FrisbeeApp.class.getResourceAsStream(LOGGER_PROPERTIES);
        
            LogManager.getLogManager().readConfiguration(strm);
            
            LOGGER.info("Message logging initialization completed.");
        } catch (IOException e) {
        
            LOGGER.severe("Error loading ./logging.properties");
            LOGGER.severe(e.getMessage());
        }      
        
        // Initializes the application properties from, which also sets the locale.
        ResourceMgr.instance();
        
        LOGGER.info("ShaTu properties initialization completed.");
        
        System.out.println("Finished initializing");
        
        // Create the server and then initialize the GUI client (see ntoes).
        //try {
            LOGGER.info(" Starting ShaTu Server (Tutoring Service)...");
            // ToDo: Separate the initialization of client and server
            // Start the socket server for the ShaTu tutor.
            
            //(new Thread(new ShaTuServer())).start();
            
            // ToDo: This puts the main client UI thread to sleep to give the 
            // server a chance to finish starting. This won't be required once 
            // we separate the server into its own application that executes
            // on a different host from the GUI client since the server should 
            // "always" be running.
            //Thread.sleep(4000);
                
            //LOGGER.info(" Server is running.");
                
            LOGGER.info(" Starting Client GUI...");
            
            // Force the creation of the MainFrame singleton, which is not
            // made visible to the user until after they sign-in.
            MainFrame.instance();

            // Force the creation of the SplashFrame, which is displayed and
            // allows the user to sign-in or create a new student account.
            // If sign-in is successful the MainFrame is displayed.
            SplashFrame.instance();            
                
            LOGGER.info("ShaTu Initialization successful.");
                
       // } catch (InterruptedException ex) {
       //         Logger.getLogger(FrisbeeApp.class.getName()).log(Level.SEVERE, null, ex);
   
        //}
    }
}
