/*
 * Frisbee: Formal Logic Tutor
 * 
 *  (C) Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.frisbee.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A mediator between the GUI and the SHA-256 algorithm.
 * 
 * @author rickb
 */
public class GuiController {
    /**
     * The singleton instance of this controller.
     */
    private static GuiController SINGLETON;
    
    static {
        SINGLETON = new GuiController();
    }
    
    /**
     * Return the singleton instanced of this controller.
     * 
     * @return GuiController
     */
    public static GuiController instance() {
        return SINGLETON;
    }
    


    /**
     * Utility reference used to convert between Java and JSon. 
     */
    private final Gson gson;
    



  /**
     * If not already initialed, initialize the SHA-256 algorithm.
     */
    private GuiController() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        
  
    }
}

