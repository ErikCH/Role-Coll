/*
 * AllImagesFilter.java
 *
 * Created on November 25, 2006, 6:19 PM
 *
 */

package rolecoll.beans;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * This filter will show all jpg, gif, and png files aswell as all folders in 
 * the directory.
 */
public class AllImagesFilter extends FileFilter {
    
    /**
     * This method will return the accepted status of an input file.
     * @param file - input file
     * @return boolean - returns the accepted status of a file
     */
    public boolean accept(File file) {        
        String filename = file.getName();                
        if(filename.endsWith(".jpg") || filename.endsWith(".gif") ||
                filename.endsWith(".png") || file.isDirectory()){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * This method returns the decription of the filter for the file dialog
     * drop down menu.
     * @return String - Description
     */
    public String getDescription() {
        return "All Images";
    }    
}
