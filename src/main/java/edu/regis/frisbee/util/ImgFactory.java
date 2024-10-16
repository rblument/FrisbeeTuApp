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
package edu.regis.frisbee.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Factory for loading images from the resources directory (e.g., Save16.gif). 
 * 
 * @author rickb
 */
public class ImgFactory {
    /**
     * Directory in the Resource path where the images are located.
     */
    private static final String DIRECTORY = "/";
    
    /**
     * Create an Image Icon by loading its corresponding image file.
     *
     * @param fileName the name of the image file (e.g., Save16.gif).
     * @param altText alternate text describing the image.
     * @return ImageIcon with the associated image specified in the file.
     */
    public static ImageIcon createIcon(String fileName, String altText) {
        return new ImageIcon(createImage(fileName), altText);
    }
    
    /**
     *
     * @param fileName name of an image file in the resources directory 
     *                 (e.g., Save16.gif)
     * @return Buffered image with the associated image
     */
    public static BufferedImage createImage(String fileName) {
        String path = DIRECTORY + fileName;
 
        try {
            return ImageIO.read(ImgFactory.class.getResourceAsStream(path));
            
        } catch (IOException e) {
            System.err.println("Couldn't find image file: " + path);  
            return null;
        }
    }
}