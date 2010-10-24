package rolecoll.ui;

import rolecoll.beans.NoteBean;
import rolecoll.beans.dao.NoteBeanController;
import rolecoll.ui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 9, 2007
 * Time: 4:36:56 PM
 */

/**
 * Note containing typed text
 */
public class TextNote extends JTextPane implements ActionListener {
    private NoteBean noteInfo;
    protected short last_x, last_y;                // Coordinates of last click.
    protected PopupMenu popup;                     // The popup menu.
    protected Color current_color = Color.black;   // Current drawing color.

    /** This constructor requires a Frame and a desired size */
    public TextNote() {
        this.setForeground(current_color);
        // We handle scribbling with low-level events, so we must specify
        // which events we are interested in.
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

        // Create the popup menu using a loop.  Note the separation of menu
        // "action command" string from menu label.  Good for internationalization.
        String[] labels;
        String[] commands;
        labels = new String[] {"Save", "Cut", "Copy", "Paste"};
        commands = new String[] {"save", "cut", "copy", "paste"};

        popup = new PopupMenu();                   // Create the menu
        for(int i = 0; i < labels.length; i++) {
            MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
            mi.setActionCommand(commands[i]);        // Set its action command.
            mi.addActionListener(this);              // And its action listener.
            popup.add(mi);                           // Add item to the popup menu.
        }
        Menu colors = new Menu("Color");           // Create a submenu.
        popup.add(colors);                         // And add it to the popup.
        String[] colornames = new String[] { "Black", "Red", "Green", "Blue"};
        for(int i = 0; i < colornames.length; i++) {
            MenuItem mi = new MenuItem(colornames[i]);  // Create the submenu items
            mi.setActionCommand(colornames[i]);         // in the same way.
            mi.addActionListener(this);
            colors.add(mi);
        }
        // Finally, register the popup menu with the component it appears over
        this.add(popup);
    }

    /**
     * update popup for notes already saved in the database
     */
    private void updatePopupMenu(){
        this.remove(popup);
        String[] labels;
        String[] commands;
        if(noteInfo != null && noteInfo.getNoteID() != 0){
            labels = new String[] {"Update", "Delete", "Cut", "Copy", "Paste"};
            commands = new String[] {"save", "delete", "cut", "copy", "paste"};
        }
        else{
            labels = new String[] {"Save", "Cut", "Copy", "Paste"};
            commands = new String[] {"save", "cut", "copy", "paste"};
        }
        popup = new PopupMenu();                   // Create the menu
        for(int i = 0; i < labels.length; i++) {
            MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
            mi.setActionCommand(commands[i]);        // Set its action command.
            mi.addActionListener(this);              // And its action listener.
            popup.add(mi);                           // Add item to the popup menu.
        }
        Menu colors = new Menu("Color");           // Create a submenu.
        popup.add(colors);                         // And add it to the popup.
        String[] colornames = new String[] { "Black", "Red", "Green", "Blue"};
        for(int i = 0; i < colornames.length; i++) {
            MenuItem mi = new MenuItem(colornames[i]);  // Create the submenu items
            mi.setActionCommand(colornames[i]);         // in the same way.
            mi.addActionListener(this);
            colors.add(mi);
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
        else if (command.equals("cut")) cut();
        else if (command.equals("copy")) copy();
        else if (command.equals("paste")) paste();
        else if (command.equals("Black")){
            current_color = Color.black;
            this.setForeground(current_color);
        }
        else if (command.equals("Red")){
            current_color = Color.red;
            this.setForeground(current_color);
        }
        else if (command.equals("Green")){
            current_color = Color.green;
            this.setForeground(current_color);
        }
        else if (command.equals("Blue")){
            current_color = Color.blue;
            this.setForeground(current_color);
        }
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

    public NoteBean getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(NoteBean noteInfo) {
        this.noteInfo = noteInfo;
    }

    /**
     * save note to the database
     */
    public void save() {
        // get internal frame
        JComponent internalFrame = (JInternalFrame)this.getParent().getParent().getParent();
        Point p = internalFrame.getLocation();
        Dimension d = internalFrame.getSize();
        noteInfo.setNoteClass(this.getClass().getName());
        noteInfo.setNoteLocationX(p.x + MainFrame.xAdjustment);
        noteInfo.setNoteLocationY(p.y + MainFrame.yAdjustment);
        noteInfo.setNoteHeight(d.height - MainFrame.heightAdjustment);
        noteInfo.setNoteWidth(d.width - MainFrame.widthAdjustment);
        noteInfo.setNoteContent(this.getText());
        noteInfo.setNoteFont(this.getFont().getFontName());
        Color color = this.getForeground();
        noteInfo.setNoteColor(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
        // commit to the database
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
        // update popup
        updatePopupMenu();
    }

    /**
     * delete note from the database
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
     * load note from the database
     */
    public void load() {
        updatePopupMenu();
        this.setLocation((int)noteInfo.getNoteLocationX(), (int)noteInfo.getNoteLocationY());
        this.setSize((int)noteInfo.getNoteWidth(), (int)noteInfo.getNoteHeight());
        this.setText(noteInfo.getNoteContent());
        this.setFont(new Font(noteInfo.getNoteFont(), 1, 12));
        this.setForeground(noteInfo.getRGBColor());
    }
}
