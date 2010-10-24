package rolecoll.ui;

import rolecoll.beans.NoteBean;
import rolecoll.beans.dao.NoteBeanController;
import rolecoll.ui.frames.MainFrame;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 9, 2007
 * Time: 4:36:56 PM
 */

/**
 * This is used to display pictures with in notes
 */
public class ImageNote extends JLabel implements ActionListener {
    private NoteBean noteInfo;
    protected short last_x, last_y;                // Coordinates of last click.
    protected PopupMenu popup;                     // The popup menu.

    /** This constructor requires a Frame and a desired size */
    public ImageNote() {
        // We handle scribbling with low-level events, so we must specify
        // which events we are interested in.
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

        // Create the popup menu using a loop.  Note the separation of menu
        // "action command" string from menu label.  Good for internationalization.
        String[] labels;
        String[] commands;
        labels = new String[] {"Save"};
        commands = new String[] {"save"};

        popup = new PopupMenu();                   // Create the menu
        for(int i = 0; i < labels.length; i++) {
            MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
            mi.setActionCommand(commands[i]);        // Set its action command.
            mi.addActionListener(this);              // And its action listener.
            popup.add(mi);                           // Add item to the popup menu.
        }
        // Finally, register the popup menu with the component it appears over
        this.add(popup);
    }

    private void updatePopupMenu(){
        this.remove(popup);
        String[] labels;
        String[] commands;
        if(noteInfo != null && noteInfo.getNoteID() != 0){
            labels = new String[] {"Update", "Delete"};
            commands = new String[] {"save", "delete"};
        }
        else{
            labels = new String[] {"Save"};
            commands = new String[] {"save"};
        }
        popup = new PopupMenu();                   // Create the menu
        for(int i = 0; i < labels.length; i++) {
            MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
            mi.setActionCommand(commands[i]);        // Set its action command.
            mi.addActionListener(this);              // And its action listener.
            popup.add(mi);                           // Add item to the popup menu.
        }
        // Finally, register the popup menu with the component it appears over
        this.add(popup);
    }

    /** This is the ActionListener method invoked by the popup menu items */
    public void actionPerformed(ActionEvent event) {
        // Get the "action command" of the event, and dispatch based on that.
        // This method calls a lot of the interesting methods in this class.
        String command = event.getActionCommand();
        if (command.equals("save")) save();
        else if(command.equals("delete")) delete();
    }

    /**
     * This is the low-level event-handling method called on mouse events
     * that do not involve mouse motion.  Note the use of isPopupTrigger()
     * to check for the platform-dependent popup menu posting event, and of
     * the show() method to make the popup visible.  If the menu is not posted,
     * then this method saves the coordinates of a mouse click or invokes
     * the superclass method.
     */
    public void processMouseEvent(MouseEvent e) {
        if (e.isPopupTrigger())                               // If popup trigger,
            popup.show(this, e.getX(), e.getY());               // pop up the menu.
        else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            last_x = (short)e.getX(); last_y = (short)e.getY(); // Save position.
        }
        else super.processMouseEvent(e);  // Pass other event types on.
    }

    /**
     * Retrive note information
     * @return NoteBean
     */
    public NoteBean getNoteInfo() {
        return noteInfo;
    }

    /**
     * Set note information
     * @param noteInfo - note information
     */
    public void setNoteInfo(NoteBean noteInfo) {
        this.noteInfo = noteInfo;
    }

    /**
     * Persist note information to the database
     */
    public void save() {
        // get internal frame
        JComponent internalFrame = (JInternalFrame)this.getParent().getParent().getParent();
        Point p = internalFrame.getLocation();
        Dimension d = internalFrame.getSize();
        // set note type
        noteInfo.setNoteClass(this.getClass().getName());
        // set location NOTE: reletive to the screen size
        noteInfo.setNoteLocationX(p.x + MainFrame.xAdjustment);
        noteInfo.setNoteLocationY(p.y + MainFrame.yAdjustment);
        // set note size
        noteInfo.setNoteHeight(d.height - MainFrame.heightAdjustment);
        noteInfo.setNoteWidth(d.width - MainFrame.widthAdjustment);
        // save to the database
        if(noteInfo.getNoteID() == 0){
            if(NoteBeanController.addNote(noteInfo)){
                JOptionPane.showMessageDialog(this,
                        "Note successfully saved to the database",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Error saving note to the database",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        // if the note already exists update it
        else{
            if(NoteBeanController.updateNote(noteInfo)){
                JOptionPane.showMessageDialog(this,
                        "Note successfully updated to the database",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Error updating note to the database",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        // update popup menu
        updatePopupMenu();
    }

    /**
     * Delete note from the database
     */
    private void delete(){
        if(NoteBeanController.removeNote(noteInfo)){
            ((JInternalFrame)this.getParent().getParent().getParent()).dispose();
            JOptionPane.showMessageDialog(this,
                    "Note successfully deleted from the database",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,
                    "Error deleting note from the database",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initilize and display a note on a document
     */
    public void load() {
        updatePopupMenu();
        // set the location
        this.setLocation((int)noteInfo.getNoteLocationX(), (int)noteInfo.getNoteLocationY());
        // set size
        this.setSize((int)noteInfo.getNoteWidth(), (int)noteInfo.getNoteHeight());
        // load the image
        BufferedImage image = null;
        try {
            image = ImageIO.read(ImageNote.class.getClassLoader().getResource(noteInfo.getNoteContent()));
        } catch(IOException e) {
            System.out.println("read error: " + e.getMessage());
        }
        ImageIcon icon = new ImageIcon(image);
        this.setIcon(icon);
    }
}
