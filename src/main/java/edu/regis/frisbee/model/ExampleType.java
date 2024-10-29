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
package edu.regis.frisbee.model;

/**
 * The legal types of practice examples that a student can request.
 * 
 * @author rickb
 */
public enum ExampleType {
   
    STEP_COMPLETION_REPLY("Step Completion Reply"),
    
    /**
     * The initial default value in a NewExampleRequest
     */
    DEFAULT("Unknown");
    
     /**
     * The name used by the server to identify this request.
     */
    private final String requestName;
    
    /**
     * Initialize this enum object with the given title.
     * 
     * @param requestName 
     */
    ExampleType(String requestName) {
        this.requestName = requestName;
    }
    
    /**
     * Return the request name that is used by the server.
     * 
     * @return a String 
     */
    public String getRequestName() {
        return requestName;
    }
    
    /**
     * Return the request name that is used by the server
     * 
     * @return a String
     */
    @Override
    public String toString() {
        return requestName;
    }
}