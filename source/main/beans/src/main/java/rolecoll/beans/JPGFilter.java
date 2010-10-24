/*
 * JPGFilter.java
 *
 * Created on November 25, 2006, 6:19 PM
 */

package rolecoll.beans;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filters all files except .jpg files from the file dialog box
 */
public class JPGFilter extends FileFilter {
    
    /**
     * @param file - file in question
     * @return boolean - the filtered status of a file
     */
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".jpg");
    }
    
    /**
     * @return String - discription of the filter for the file dialog box
     */
    public String getDescription() {
        return "*.jpg";
    }    
}
