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
package edu.regis.frisbee.err;

/**
 * Root of all checked Frisbee application (logging is done in subclasses). 
 * 
 * @author Rickb
 */
public abstract class FrisbeeException extends Exception {
    /**
     * Initialize this new instance with the given message.
     *
     * @param msg a string describing the cause of this exception.
     */
    public FrisbeeException(String msg) {
	super(msg);
    }

    /**
     * Initialize this new instance with the given message and the underlying
     * Java exception that caused this Frisbee exception.
     *
     * @param msg a string describing the cause of this exception.
     * @param cause the Java exception that caused this Frisbee exception.
     */
    public FrisbeeException(String msg, Throwable cause) {
	super(msg, cause);
    }
}

