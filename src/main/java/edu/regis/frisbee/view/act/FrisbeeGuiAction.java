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
package edu.regis.frisbee.view.act;

import edu.regis.frisbee.util.ImgFactory;
import java.awt.Image;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 * Abstract root for all GUI actions in the Frisbee application.
 * 
 * Provides support for loading image icons and assigning the GUI controller.
 * 
 * @author rickb
 */
public abstract class FrisbeeGuiAction extends AbstractAction {
    public FrisbeeGuiAction(String name) {
        super(name);
    }
    
    /**
     * Load and return the image icon specified by the given image file name,
     * as found in the resources directory on the CLASSPATH.
     * 
     * @param imageFileName name of file e.g., "Save16.gif"
     * @param altText
     * @return 
     */
    protected ImageIcon loadIcon(String imageFileName, String altText) {        
        Image img = ImgFactory.createImage(imageFileName);
        
        return new ImageIcon(img, altText);
    }
}
