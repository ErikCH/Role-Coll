/*
 * BackgroundPanel.java
 *
 * Created on November 25, 2006, 1:46 PM
 */

package rolecoll.ui.frames;

import javax.swing.*;
import javax.imageio.ImageIO;

import rolecoll.beans.GUIBean;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 */
/**
 * Background for all panels 
 */
public class BackgroundPanel extends javax.swing.JPanel {

    /** Creates new form BackgroundPanel */
    public BackgroundPanel() {
        try{
            // Set the Skin Look And Feel
            UIManager.setLookAndFeel(GUIBean.getLookAndFeel());
        } catch(Exception e){
            e.printStackTrace();
        }

        initComponents();
    }    

    /** This method is called from within the constructor to
     *  initialize the form.
     */
    private void initComponents() {
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(0, 278, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(0, 129, Short.MAX_VALUE)
        );
    }
}
